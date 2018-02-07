package entity.body;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import condition.Condition;
import userinterface.SoundManager;
import userinterface.VisualizationEvaluationInterface;
import config.BodyConfig;
import config.EventConfig;
import entity.Entity;
import entity.mind.AnimateMindInterface;
import entity.mind.MindFactory;
import evaluator.EvaluatorManagerMindInterface;
import event.EventFactory;
import event.EventHandler;
import event.EventInterface;
import event.EventHandlerInterface;

public class BodyFactory {
	private final EvaluatorManagerMindInterface scoreManager;
	private final SoundManager moSoundManager;
	private final VisualizationEvaluationInterface visualization;
	
	private static final Logger log = LoggerFactory.getLogger(BodyFactory.class);
	
	private final EventFactory eventFactory = new EventFactory();
	
	public BodyFactory(EvaluatorManagerMindInterface moScoreManager, SoundManager moSoundManager, VisualizationEvaluationInterface visualization) {
		this.scoreManager = moScoreManager;
		this.moSoundManager = moSoundManager;
		this.visualization = visualization;
	}
	
	
	
	/**
	 * Create body and mind as the mind is a part of the body
	 * 
	 * @return
	 * @throws Exception 
	 */
	public BodyInterface createBody(Entity ownerEntity, BodyConfig bodyTypeConfig) throws Exception {
		
		BodyInterface res=null;
		
		//BodyType
		//EBodyType bodyType = null;
//		try {
//		//	bodyType = bodyTypeConfig.getBodyType();
//		} catch (NullPointerException e) {
//			log.error("There is no or a non existing bodytype defined");
//			throw new NullPointerException(e.getMessage());
//		}
		
		
		//Get assigned events
		//Set eventhandler for actions
		EventHandlerInterface eventHandlerForActions = generateCompleteEventHandler(bodyTypeConfig.getEventOnActionNames());
		
		
		//Set eventhandler for reactions
		EventHandlerInterface eventHandlerForReactions = generateCompleteEventHandler(bodyTypeConfig.getEventOnReactionNames());
		
		//Set eventhandler for own actions
		EventHandlerInterface eventHandlerForMyActionExecutions = generateCompleteEventHandler(bodyTypeConfig.getEventOnOwnActionName());
		
		EventHandlerInterface eventHandlerForBodyInternals = generateCompleteEventHandler(bodyTypeConfig.getEventOnBodyInternalsName());
		
		
		//if (bodyType.equals(EBodyType.ANIMATEBODY)==true) {
			//Create body
			Body animateBody = new Body(bodyTypeConfig.getBodyTypeName(), bodyTypeConfig.getParentBodyType(), ownerEntity, eventHandlerForActions, eventHandlerForReactions, eventHandlerForMyActionExecutions, eventHandlerForBodyInternals);
			
			if (bodyTypeConfig.getMind()!=null) {
				//Create mind
				MindFactory mf = new MindFactory(scoreManager);
				AnimateMindInterface mind;
				try {
					mind = mf.createMind(bodyTypeConfig.getMind(), animateBody, ownerEntity);
				} catch (Exception e) {
					log.error("Could not create mind");
					throw new Exception("Error in creation of body");
				}
				
				animateBody.setMind(mind);
			}
			
			//Set result
			res = animateBody;
			
		//} else if (bodyType.equals(EBodyType.INANIMATEBODY)==true) {
		//	Body inanimateBody = new Body(bodyTypeConfig.getBodyTypeName(), ownerEntity, eventHandlerForActions, eventHandlerForReactions, eventHandlerForMyActionExecutions, eventHandlerForBodyInternals);
			
		//	res = inanimateBody;
		//}
		
		return res;
		
	}
	
	/**
	 * generate an eventhandler and register events from arraylist
	 * 
	 * @param eventConfigs
	 * @return
	 */
	private EventHandlerInterface generateCompleteEventHandler(ArrayList<EventConfig> eventConfigs) {
		EventHandlerInterface oEventHandler = new EventHandler(moSoundManager, scoreManager, this.visualization);
		
		for (EventConfig eventConfig : eventConfigs) {
			//Create event
			EventInterface event=null;
			try {
				event = eventFactory.createEvent(eventConfig);
			} catch (Exception e) {
				log.error("Cannot create eventhandler for an event {}", eventConfig);
				log.info("Exit system");
				System.exit(-1);
			}
			
			//register event in the event handler
			oEventHandler.registerEvent(event);
		}
		
		return oEventHandler;
	}

}
