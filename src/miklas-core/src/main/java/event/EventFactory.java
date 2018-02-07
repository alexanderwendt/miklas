package event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import condition.ConditionFactory;
import condition.ConditionInterface;
import config.EventConfig;
import evaluator.EntityEvaluation;

public class EventFactory {
	
	protected static final Logger log = LoggerFactory.getLogger(EventFactory.class);
	
	/**
	 * Create an event + conditions
	 * 
	 * @param eventConfig
	 * @return
	 * @throws Exception
	 */
	public Event createEvent(EventConfig eventConfig) throws Exception {
		//Get matching conditions from event, uninitialized
		ConditionFactory cf = new ConditionFactory();
		
		//Create event
		ArrayList<ConditionInterface> conditionInterfaceList = cf.createConditionsForEvents(eventConfig);
		
		Event result = null;
		try {
			result = createEventFromConfig(eventConfig, conditionInterfaceList);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			log.error("Cannot create event {}", eventConfig);
			throw e;
		}
		
		//Connect event to its conditions
		for (ConditionInterface condition : conditionInterfaceList) {
			result.registerCondition(condition);
		}
		
		log.debug("Event {} created", result);
		return result;
		
	}
	
	/**
	 * Create an event based on reflections
	 * 
	 * @param poEventConfig
	 * @param conditions
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 */
	private Event createEventFromConfig(EventConfig poEventConfig, ArrayList<ConditionInterface> conditions) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Event result=null;
		String className = poEventConfig.getClassName();
		HashMap<String,ArrayList<String>> parameters = poEventConfig.getParameterMap();
		String eventName = poEventConfig.getEventName();
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(String.class);
			Object obj = constructor.newInstance(eventName);
			if (obj instanceof EventInterface) {
				result = (Event) obj;
				result.init(parameters);
			} else {
				throw new ClassNotFoundException("created object not instance of Eventinterface");
			}
		} catch (ClassNotFoundException e) {
			log.error("Cannot find a class with the name {}", className, e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (InstantiationException e) {
			log.error("Cannot instantiate class with name {}", className, e);
			throw new InstantiationException(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error("Cannot access class {}", className, e);
			throw new IllegalAccessException(e.getMessage());
		} catch (NoSuchMethodException e) {
			log.error("Cannot initialize constructor for class {}", className, e);
			throw new NoSuchMethodException(e.getMessage());
		} catch (SecurityException e) {
			log.error("Security exception for constructor for class {}", className, e);
			throw new SecurityException(e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Wrong arguments in constrctor of class {}", className, e);
			throw new IllegalArgumentException(e.getMessage());
		} catch (InvocationTargetException e) {
			log.error("Cannot invoke constructor of class {}", className, e);
			throw new InvocationTargetException(e, e.getMessage());
		}
		
		return result;
	}
}
