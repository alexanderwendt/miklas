package entity;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import userinterface.SoundManager;
import userinterface.VisualizationEvaluationInterface;
import ch.aplu.jgamegrid.GameGrid;
import condition.Condition;
import config.ActorConfig;
import config.EventConfig;
import entity.body.BodyFactory;
import entity.body.BodyInterface;
import evaluator.EvaluatorManagerMindInterface;

public class EntityFactory {

	private final GameGrid moThisGameGrid;
	private final EvaluatorManagerMindInterface moScoreManager;
	private final SoundManager moSoundManager;
	private final VisualizationEvaluationInterface visualization;
	
	private final BodyFactory bodyFactory;
	
	private static final Logger log = LoggerFactory.getLogger(BodyFactory.class);
	
	public EntityFactory(GameGrid moThisGameGrid, EvaluatorManagerMindInterface moScoreManager, SoundManager moSoundManager, VisualizationEvaluationInterface visualizationManager) {
		this.moThisGameGrid = moThisGameGrid;
		this.moScoreManager = moScoreManager;
		this.moSoundManager = moSoundManager;
		this.visualization = visualizationManager;
		
		bodyFactory = new BodyFactory(moScoreManager, moSoundManager, this.visualization);
	}

	public Entity newEntity(ActorConfig actorConfig, int entityLayer, int entityCount) throws Exception {
		
		//Create entity, allocate memory
		//Entity(GameGrid poGameEngine, String poName, String prGraphicPath, boolean pbRotateGraphicWithMovement, int pnGraphicsUsedCount, int pnGraphicUpdateInterval)
		Entity entity = null;
		try {
			String optionalActorId = String.valueOf(entityCount);
			if (actorConfig.getActorId().isEmpty()==false) {
				optionalActorId = actorConfig.getActorId();
			}
			
			entity = new Entity(moThisGameGrid, actorConfig.getActorName(), optionalActorId, actorConfig.getIconGraphicAddress(), actorConfig.isRotateGraphicWithDirection(), actorConfig.getTotalNumberOfIcons(), actorConfig.getMnGraphicStep(), entityLayer, moScoreManager, this.visualization);
			log.trace("Memory for entity {} allocated.", entity);
		} catch (Exception e) {
			log.error("Cannot create entity actor name={}, entity count = {}", actorConfig.getActorName(), entityCount, e);
			throw new Exception (e.getMessage());
		}
		
		
		//=== MAKE ALL ASSIGNMENTS FIRST ===//
		
		//Set body
		BodyInterface body;
		try {
			body = bodyFactory.createBody(entity, actorConfig.getBodyTypeConfig()); 
		} catch (Exception e) {
			log.error("Cannot create body for actor {}", actorConfig, e);
			throw new Exception(e.getMessage());
		}
		
		entity.setBody(body);
		
		try {
			//TODO make another register mechanism that does not register evertything
			if (actorConfig.isEvaluateActor()==true) {
				//Register in the sound manager agent
				moSoundManager.registerEntity(entity.getEntityIdentifier());
				log.trace("Registered entity {} in sound manager", entity.getEntityIdentifier());
				//Register in the score manager, only if it has an animate mind
			
				moScoreManager.registerEntity(entity.getEntityIdentifier(), 0);
				log.trace("Registered entity {} in score manager", entity.getEntityIdentifier());
				
			}
			
			//TODO Fix identifier for all objects
			this.visualization.registerEntity(entity, actorConfig.getNumberOfGraphicIconsUsedForInterval(), actorConfig.getMnGraphicStep(), actorConfig.getTotalNumberOfIcons());
			
		} catch (Exception e) {
			throw new Exception("Cannot register entity");
		}
		
		//Get all events
		ArrayList<EventConfig> allEvents = actorConfig.getBodyTypeConfig().getAllEvents();
		for (EventConfig event : allEvents) {
			//Register all sounds
			moSoundManager.registerSound(entity.getEntityIdentifier(), event.getEventName(), actorConfig.getEventSound(event.getEventName()));
			this.visualization.registerEventGraphic(entity.getEntityIdentifier(), event.getEventName(), actorConfig.getEventGraphic(event.getEventName()), event.isPermanentGraphicChange());
		}
		
		//=== THEN INIT ALL STRUCTURES WITH DEFAULT VARIABLES ===//
		body.init();
		
		return entity;
	}
	
}
