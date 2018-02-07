package userinterface;

import entity.Entity;
import evaluator.EntityEvaluation;
import gameengine.GameEngineImpl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Location;
import config.VisualizationConfig;

public class MainWindow extends JFrame implements VisualizationEvaluationInterface {

	private JPanel contentPane;

	private static final long serialVersionUID = 1L;

	private GameEngineImpl moGameEngine;
	private final VisualizationConfig visConfig;
	private HashMap<String, GraphicSetting> eventToGraphicList = new HashMap<String, GraphicSetting>();
	
	private ArrayList<JTextField> moTextFields = new ArrayList<JTextField>();
	
	private static final Logger	log	= LoggerFactory.getLogger(MainWindow.class);
	
	public MainWindow(VisualizationConfig visConfig) {
		this.visConfig = visConfig;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setGameEngine(GameEngineImpl poGameEngine) {
		moGameEngine = poGameEngine;
	}
	
	public void init() {
		//Set cell size
		moGameEngine.getGameGrid().setCellSize(this.visConfig.getCellSize());
		//Set grid color
		moGameEngine.getGameGrid().setSimulationPeriod(this.visConfig.getSimulationPeriod());
	    
	    //Set terminat condition for pane
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    
		//setBounds(100, 100, 450, 300);

	    
	    //Container oGraphicsContainer = getContentPane();
	    //oGraphicsContainer.setLayout(new BorderLayout());
	    
	    contentPane.add(moGameEngine.getGameGrid(), BorderLayout.CENTER);
	    
	    //oGraphicsContainer.add(moGameEngine.getGameGrid(), BorderLayout.NORTH);
	    //oGraphicsContainer.add(test, BorderLayout.NORTH);
	    
	    moTextFields.add(new JTextField("Punkte f�r Mia: "));
	    //JTextField f = new JTextField("Punkte f�r Mia: ");
	    contentPane.add(moTextFields.get(0), BorderLayout.CENTER);
	    
	    
	    moTextFields.add(new JTextField("Punkte f�r Niklas: "));
	    //JTextField f2 = new JTextField("Punkte f�r Niklas: ");
	    contentPane.add(moTextFields.get(1), BorderLayout.SOUTH);
	    
	    pack();  // Must be called before actors are added!
	    
	    //Set visualization
	    moGameEngine.setVisualization(this);
	    
	    //Init entity factory
	    moGameEngine.initializeEntityFactory();
	    
	    //Add actors
	    try {
			moGameEngine.addActorsToWorld();
		} catch (Exception e) {
  
			System.exit(-1);
		}
	    
	    moGameEngine.getGameGrid().doRun();
	    
	    this.setVisible(true);
	}
	
	@Override
	public void updateStats(final String poEntityName, final EntityEvaluation evaluation) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int choice = 0;
				if (poEntityName.equals("Mia")) {
					choice=0;
				} else if (poEntityName.equals("Niklas")) {
					choice=1;
				}
				
				log.debug(evaluation.toString());
				
				moTextFields.get(choice).setText("Name: " + poEntityName
						+ "; Gesundheit: " + evaluation.getHealth() 
						+ "; Punkte: " + evaluation.getScore()
						+ "; Belohnungen: " + evaluation.getPositiveActions()
						+ "; Bestrafungen: " + evaluation.getNegativeActions()
						+ "; Neutral: " + evaluation.getNeutralActions());
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
	public void registerEventGraphic(String entityIdentifier, String poEventName, int graphicNumber, boolean x) throws Exception {
		GraphicSetting graphic = this.eventToGraphicList.get(entityIdentifier);
		if (graphic!=null) {
			try {
				graphic.registerEventGraphic(poEventName, graphicNumber, x);
			} catch (Exception e) {
				log.error("Could not register event graphic for {} of entity {}", poEventName, entityIdentifier, e);
				throw new Exception(e.getMessage());
			}
		} else {
			log.error("Could not find entity {} in the list for visualization", entityIdentifier);
			throw new NullPointerException("Could not find entity" + entityIdentifier);
		}
		
	}

	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(new VisualizationConfig(0,0, "", false));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public int getTimeStamp() {
		return moGameEngine.getGameGrid().getSimulationPeriod();
	}

	@Override
	public Location updatePerceptionForEvaluation(String entityIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}
}
