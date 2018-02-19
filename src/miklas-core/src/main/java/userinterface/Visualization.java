package userinterface;

import entity.Entity;
import evaluator.EntityEvaluation;
import gameengine.GameEngineImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import config.ConfigLoader;

public class Visualization extends JFrame implements VisualizationEvaluationInterface, ActionListener {
	private static final long serialVersionUID = 1L;

	private Window parent = null;
	private GameEngineImpl gameEngine;
	//private final VisualizationConfig visConfig;
	private HashMap<String, GraphicSetting> eventToGraphicList = new HashMap<String, GraphicSetting>();
	
	private static final Logger	log	= LoggerFactory.getLogger(Visualization.class);
	
	private JTree statusTree;
	private DefaultMutableTreeNode statsTop;
	
	// standard constructor
	public Visualization() {
	}
	
	public Visualization(Window p) {
		parent = p;
	}
	
	public void setGameEngine(GameEngineImpl poGameEngine) {
		gameEngine = poGameEngine;
	}
	
	/**
	 * initializes game visualization
	 */
	public void init() {
		// clean up
		//removeAll();
		
		//Generate game
		//Set cell size
		gameEngine.getGameGrid().setCellSize(ConfigLoader.getConfig().getVisualizationConfig().getCellSize());
		//Set grid color
		gameEngine.getGameGrid().setSimulationPeriod(ConfigLoader.getConfig().getVisualizationConfig().getSimulationPeriod());
		
		//Set BG Imagepath
		if (ConfigLoader.getConfig().getVisualizationConfig().getBgImagePath().equals("")==false) {
			gameEngine.getGameGrid().setBgImagePath(ConfigLoader.getConfig().getVisualizationConfig().getBgImagePath());
		}
		
		//Set if a grid shall be shown
		if (ConfigLoader.getConfig().getVisualizationConfig().isShowGrid()==true) {
			gameEngine.getGameGrid().setGridColor(Color.red);
		}

	    //Set terminal condition
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setResizable(false);
	    
	    // layout objects on frame
	    Container oGraphicsContainer = getContentPane();
	    oGraphicsContainer.setLayout(new BorderLayout());
	    
	    oGraphicsContainer.add(gameEngine.getGameGrid(), BorderLayout.CENTER);
	    
	    // create control bar
	    JPanel ctrlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    JButton ctrlPlay = new JButton("Run");
	    JButton ctrlPause = new JButton("Pause");
	    JButton ctrlStep = new JButton("Step");
	    JButton ctrlStop = new JButton("Abort game");
	    ctrlPlay.setToolTipText("Set game running");
	    ctrlPause.setToolTipText("Pause game");
	    ctrlStep.setToolTipText("Proceed one timestep in paused game");
	    ctrlStop.setToolTipText("Return to start window");
	    ctrlPlay.setActionCommand("ctrlPlay");
	    ctrlPause.setActionCommand("ctrlPause");
	    ctrlStep.setActionCommand("ctrlStep");
	    ctrlStop.setActionCommand("ctrlStop");
	    ctrlPlay.addActionListener(this);
	    ctrlPause.addActionListener(this);
	    ctrlStep.addActionListener(this);
	    ctrlStop.addActionListener(this);
	    ctrlPanel.add(ctrlPlay);
	    ctrlPanel.add(new JSeparator(JSeparator.VERTICAL));
	    ctrlPanel.add(ctrlPause);
	    ctrlPanel.add(ctrlStep);
	    ctrlPanel.add(new JSeparator(JSeparator.VERTICAL));
	    ctrlPanel.add(ctrlStop);
	    oGraphicsContainer.add(ctrlPanel, BorderLayout.SOUTH);
	    
	    // create status tree
	    statsTop = new DefaultMutableTreeNode("Actors");
	    statusTree = new JTree(statsTop);
	    oGraphicsContainer.add(statusTree, BorderLayout.EAST);
	    
	    pack();  // Must be called before actors are added!
	    
	    //Set visualization
	    gameEngine.setVisualization(this);
	    
	    //Init entity factory
	    gameEngine.initializeEntityFactory();
	    
	    //Add actors
	    try {
			gameEngine.addActorsToWorld();
		} catch (Exception e) {
			log.error("Cannot add actors to world", e);
			System.exit(-1);
		}
	    
	    gameEngine.getGameGrid().doRun();
	    
	    this.setVisible(true);
	}


	@Override
	public void updateStats(final String poEntityName, final EntityEvaluation evaluation) {
		
		// update statistics
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// if only one animate entity is left in game grid -> game over
				//=== Game over condition ===//
					if(countAnimateBodies() <= 0) {
						gameOver();
					}	
					
				// try to find actor in tree
				DefaultMutableTreeNode child = null;
				Enumeration<?> enumChildren = statsTop.children();
				while(enumChildren.hasMoreElements()) {
					DefaultMutableTreeNode temp = (DefaultMutableTreeNode)enumChildren.nextElement();
					if(poEntityName.equals(temp.getUserObject())) {
						child = temp;
						break;
					}
				}
				
				// if not found, create actor in structure
				if(child == null) {
					child = new DefaultMutableTreeNode(poEntityName);
					statsTop.add(child);
				}
				
				// update actor statistics
				child.removeAllChildren();
				child.add(new DefaultMutableTreeNode("Health: " + evaluation.getHealth()));
				child.add(new DefaultMutableTreeNode("Score: " + evaluation.getScore()));
				child.add(new DefaultMutableTreeNode("Pos. Actions: " + evaluation.getPositiveActions()));
				child.add(new DefaultMutableTreeNode("Neutral Actions: " + evaluation.getNeutralActions()));
				child.add(new DefaultMutableTreeNode("Neg. Actions: " + evaluation.getNegativeActions()));
				
				// ensure all rows are expanded and visible
				for(int i = 0; i < statusTree.getRowCount(); i ++)
					statusTree.expandRow(i);
				statusTree.updateUI();
				pack();
			}
		});
		
	}

	@Override
	public synchronized void registerEntity(Entity entity, int numberOfIconsContinousLoop, int iconChangeInterval, int totalNumberOfIcons) {
		eventToGraphicList.put(entity.getEntityIdentifier(), new GraphicSetting(entity, numberOfIconsContinousLoop, iconChangeInterval, totalNumberOfIcons));
		
	}

	@Override
	public synchronized void updateEntityIcon(String entityIdentifier) {
		GraphicSetting graphic = this.eventToGraphicList.get(entityIdentifier);
		if (graphic!=null) {
			graphic.updateRotatingGraphic();
		}
		
	}

	@Override
	public synchronized void updateEntityIcon(String entityIdentifier, String eventName) {
		GraphicSetting graphic = this.eventToGraphicList.get(entityIdentifier);
		if (graphic!=null) {
			try {
				graphic.activateEventGraphic(eventName);
			} catch (Exception e) {
				log.error("Could not activate graphic for {}, entity {}", eventName, entityIdentifier);
			}
		} else {
			log.error("Could not find entity {} in the list for visualization", entityIdentifier);
			throw new NullPointerException("Could not find entity" + entityIdentifier);
		}
		
	}

	@Override
	public void registerEventGraphic(String entityIdentifier, String poEventName, int graphicNumber, boolean isPermanentGraphicChange) throws Exception {
		GraphicSetting graphic = this.eventToGraphicList.get(entityIdentifier);
		if (graphic!=null) {
			try {
				graphic.registerEventGraphic(poEventName, graphicNumber, isPermanentGraphicChange);
			} catch (Exception e) {
				log.error("Could not register event graphic for {} of entity {}", poEventName, entityIdentifier, e);
				throw new Exception(e.getMessage());
			}
		} else {
			log.error("Could not find entity {} in the list for visualization", entityIdentifier);
			throw new NullPointerException("Could not find entity" + entityIdentifier);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// listens to actions performed on control buttons
		switch(ae.getActionCommand()) {
		case "ctrlPlay":
			gameEngine.getGameGrid().doRun();
			break;
		case "ctrlPause":
			gameEngine.getGameGrid().doPause();
			break;
		case "ctrlStep":
			gameEngine.getGameGrid().doStep();
			break;
		case "ctrlStop":
			gameOver();
			break;
		default:
			break;
		}
	}

	@Override
	public int getTimeStamp() {
		return gameEngine.getGameGrid().getNbCycles();
	}

	private void gameOver() {
		// abort current game
		log.trace("Game over");
		
		gameEngine.getGameGrid().doPause();
		gameEngine.getGameGrid().stopGameThread();
		if (this.gameEngine.getMusicManager() != null) {
			gameEngine.getMusicManager().stopMusic();
		}
		
		
		if(parent != null) {	// only if GUILauncher is used
			this.setVisible(false);
			parent.setVisible(true);
		}
		else {
			System.exit(0);
		}
			
	}

	private int countAnimateBodies() {
		ArrayList<Actor> entities = gameEngine.getGameGrid().getActors();
		int ret = 0;
		
		for(Actor entity : entities) {
			if (((Entity) entity).isBodyAnimate())
				ret ++;
		}
		
		log.debug("Updating stats: currently {} animate actors on grid", ret);
		
		return ret;
	}

	@Override
	public Location updatePerceptionForEvaluation(String entityIdentifier) {
		Location loc = null;
		
		for(Actor a : gameEngine.getGameGrid().getActors()) {
			if(((Entity)a).entitiyIdentifier.equals(entityIdentifier)) {
				loc = a.getLocation();
				break;
			}
		}
		
		return loc;
	}
}
