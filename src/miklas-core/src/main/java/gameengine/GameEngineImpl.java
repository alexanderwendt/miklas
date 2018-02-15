package gameengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import userinterface.MusicManager;
import userinterface.SoundManager;
import userinterface.VisualizationEvaluationInterface;
import config.ActorConfig;
import config.ConfigLoader;
import config.EventConfig;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import entity.Entity;
import entity.EntityFactory;
import evaluator.EvaluatorManagerMindInterface;


public class GameEngineImpl implements GameEngine {
	//private final WorldCreatorInterface worldConfig;
	//private final ConfigLoader conf;
	private final GameGrid gameGrid;
	private VisualizationEvaluationInterface vis = null;
	private SoundManager soundManager = null;
	private MusicManager musicManager = null;
	private EvaluatorManagerMindInterface scoreManager;
	
	private EntityFactory entityFactory;
	
	private static int entityCount = 0;
	
	
	
	private static final Logger	log	= LoggerFactory.getLogger(GameEngineImpl.class);
	
	//GGBackground moBackGround;
	
	public GameEngineImpl() {
		Thread.currentThread().setName("GameEngine");
		gameGrid = new GameGrid(true);
	}

	@Override
	public GameGrid getGameGrid() {
		// TODO Auto-generated method stub
		return gameGrid;
	}
	
	public void init() {
		//Create world
		try {
			drawGrid();
			
			if(musicManager != null) {
				// play game music
				String relativeMusicPath = ConfigLoader.getConfig().getMusicConfig().getRelativMusicPath();
				if ( ! relativeMusicPath.equals("") )
					musicManager.playMusic(relativeMusicPath);
			}
		} catch (Exception e) {
			log.error("Cannot draw grid");
			System.exit(-1);
		}
		
		//Register all non actor specific content in the score manager
		registerEventsScore();
	}

	private void drawGrid() throws Exception  {
		gameGrid.setNbVertCells(ConfigLoader.getConfig().getWorldConfig().getYDimension());
		gameGrid.setNbHorzCells(ConfigLoader.getConfig().getWorldConfig().getXDimension());
	}
	
	private void registerEventsScore() {
		//Register event in scorehandler
		for (EventConfig eventConfig : ConfigLoader.getConfig().getAvailableEvents().values()) {
			try {
				this.scoreManager.registerEvent(eventConfig.getEventName(), eventConfig.getScoreChange());
			} catch (Exception e) {
				log.error("Cannot register event {}", e);
			}
		}
	}
	
	public void addActorsToWorld() throws Exception {
		gameGrid.removeAllActors();
		int numberOfLayers = ConfigLoader.getConfig().getWorldConfig().getLayerCount();
		for (int l=0; l < numberOfLayers; l++) {
			for (int y = 0; y < gameGrid.nbVertCells; y++) {
				for (int x = 0; x < gameGrid.nbHorzCells; x++) {
					Location location = new Location(x, y);
		    		  
		    		  char mapValue = ConfigLoader.getConfig().getWorldConfig().getCellValue(location, l);
		    		  
		    		  ActorConfig actorConfig = ConfigLoader.getConfig().getAvailableActors().get(mapValue);
		    		  if (actorConfig==null) {
		    			  if (mapValue!='_') {
		    				  log.warn("There is no actor for position ({}, {}, {}) with map value {}", x, y, l, mapValue);
		    			  }
		    		  } else {
		    			  Entity oNewEntity = this.entityFactory.newEntity(actorConfig, l, GameEngineImpl.getEntityCount());
		    			  GameEngineImpl.incrementEntityCount();
		    	    	  
			    		  gameGrid.addActor(oNewEntity, location);
			    		  oNewEntity.setOnTop();
			    		  oNewEntity.setDirection(actorConfig.getInitRotation());
			    		  gameGrid.refresh();
				 	      log.debug("Add actor {} from {}", oNewEntity, mapValue);  
		    		  }
				}
			}
	    }
	}

	@Override
	public void setVisualization(VisualizationEvaluationInterface vis) {
		this.vis = vis;
	}

	@Override
	public void setSoundManager(SoundManager sound) {
		this.soundManager = sound;
	}

	@Override
	public void initializeEntityFactory() {
		entityFactory = new EntityFactory(this.getGameGrid(), this.scoreManager, this.soundManager, this.vis);
		
	}

	@Override
	public void setScoreManager(EvaluatorManagerMindInterface score) {
		this.scoreManager = score;
	}

	private static int getEntityCount() {
		return entityCount;
	}

	private static void incrementEntityCount() {
		GameEngineImpl.entityCount++;
	}

	@Override
	public void setCellSize(int value) {
		throw new UnsupportedOperationException("Operation not supported in this implementation of the game engine");
		
	}

	@Override
	public void setNbHorzCells(int value) {
		throw new UnsupportedOperationException("Operation not supported in this implementation of the game engine");
		
	}

	@Override
	public void setNbVertCells(int value) {
		throw new UnsupportedOperationException("Operation not supported in this implementation of the game engine");
		
	}

	@Override
	public void setMusicManager(MusicManager music) {
		musicManager = music;
	}

	@Override
	public MusicManager getMusicManager() {
		return this.musicManager;
	}

}
