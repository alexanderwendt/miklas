package launcher;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import userinterface.MusicManagerImpl;
import userinterface.MusicManager;
import userinterface.SoundManager;
import userinterface.SoundManagerImpl;
import userinterface.Visualization;
import config.ConfigLoader;
import evaluator.EvaluatorManager;
import evaluator.EvaluatorManagerMindInterface;
import gameengine.GameEngineImpl;

public class Launcher {
	
	private static final Logger	log	= LoggerFactory.getLogger(Launcher.class);
	
	
	public static void main(String[] args) {
		log.info("Start Miklas 1.0");
				
		//Load parameters from config file
		ConfigLoader conf = null;
		try {
			conf = ConfigLoader.getConfig();
			conf.init();
		} catch (Exception e) {
			log.error("Could not load config. Exit program", e);
			System.exit(-1);
		}
		
		//Load config
		//WorldConfig world = new WorldConfig();	
		
		//Init visualization
		final Visualization vis = new Visualization();
		
	    final GameEngineImpl gameEngine = new GameEngineImpl();
	    
		//Load score manager
		EvaluatorManagerMindInterface scoreManager = new EvaluatorManager(vis);
	    
		//Load sound manager
		SoundManager soundManager = new SoundManagerImpl(gameEngine.getGameGrid());
		
		
		//Load music manager and play music
		MusicManager musicManager = new MusicManagerImpl();
		String relativeMusicPath = conf.getMusicConfig().getRelativMusicPath();
		if (relativeMusicPath.equals("")==false) {
			musicManager.playMusic(relativeMusicPath);
		}
		
		//Set sound manager in the gameengine
		gameEngine.setSoundManager(soundManager);
		gameEngine.setScoreManager(scoreManager);
		
		//As sson as everything is assigned, init the game engine
		gameEngine.init();
	    
	    //Start Visualization
	    SwingUtilities.invokeLater(new Runnable() {
	    	public void run() {
	    		//Visualization oVis = new Visualization();
	    		vis.setGameEngine(gameEngine);
	    		vis.init();
	    		vis.setVisible(true);
	    	}
	    });
	}
}
