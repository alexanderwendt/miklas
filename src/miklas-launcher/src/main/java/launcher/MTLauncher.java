/**
 * Class MTLauncher
 * Multiple-Time Miklas launcher
 */

package launcher;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.ConfigLoader;
import evaluator.EvaluatorManager;
import evaluator.EvaluatorManagerMindInterface;
import gameengine.GameEngineImpl;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import userinterface.MusicManager;
import userinterface.MusicManagerImpl;
import userinterface.SoundManager;
import userinterface.SoundManagerImpl;
import userinterface.Visualization;

public class MTLauncher extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private static final int nRuns = 10;
	
	private int curRun;
	
	private ConfigLoader conf = null;
	
	private Visualization vis = null;
	private GameEngineImpl ge = null;
	private SoundManager sm = null;
	private MusicManager mm = null;
	private EvaluatorManagerMindInterface emmi = null;
	
	private static final Logger log = LoggerFactory.getLogger(MTLauncher.class);

	public MTLauncher() {
		super("Miklas");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		curRun = 0;
	}

	/**
	 * 
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		log.info("Start Miklas in auto-mode");
		
		MTLauncher launcher = new MTLauncher();
		
		try {
			launcher.conf = ConfigLoader.getConfig();
			launcher.conf.init();
		}
		catch(Exception e) {
			log.error("Configuration couldn't be loaded. Program exists.");
			System.exit(-1);
		}
		
		//launcher.init();
		launcher.setLocationRelativeTo(null);
		launcher.setSize(320, 200);
		launcher.addWindowListener(launcher);
		launcher.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		if(ge == null || !ge.getGameGrid().isRunning()) {
			emmi = null;
			sm = null;
			mm = null;
			vis = null;
			ge = null;
			
			synchronized (this) {
				try {
					this.wait(200);
				} catch (InterruptedException e) { }
			}
			
			this.setVisible(false);
			
			if(curRun++ >= nRuns)
				System.exit(0);
			
			log.info("Starting run {}", curRun);
			ge = new GameEngineImpl();
			vis = new Visualization(this);
			emmi = new EvaluatorManager(vis);
			sm = new SoundManagerImpl(ge.getGameGrid());
			mm = new MusicManagerImpl();
			
			// initialize game
			ge.setSoundManager(sm);
			ge.setScoreManager(emmi);
			ge.setMusicManager(mm);
			ge.init();
			
			// start visualization
		    SwingUtilities.invokeLater(new Runnable() {
		    	public void run() {
		    		vis.setGameEngine(ge);
		    		vis.setTitle(conf.getGameTitle());
		    		vis.init();
		    		vis.setVisible(true);
		    	}
		    });
		}
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
