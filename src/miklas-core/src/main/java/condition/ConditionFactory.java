package condition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.CompleteConditionConfig;
import config.EventConfig;

public class ConditionFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ConditionFactory.class);
	
	public ArrayList<ConditionInterface> createConditionsForEvents(EventConfig eventConfig) throws Exception {
		ArrayList<ConditionInterface> result = new ArrayList<ConditionInterface>();
		
		for (CompleteConditionConfig conditionConfig : eventConfig.getCompleteConditionConfigs()) {
			try {
				ConditionInterface condition = this.createCondition(conditionConfig);
				result.add(condition);
			} catch (Exception e) {
				log.error("Cannot create condition {} for event {}", conditionConfig, eventConfig);
				throw e;
			}
		}
		
		return result;
	}
	
	public ConditionInterface createCondition(CompleteConditionConfig conditionConfig) throws Exception {
		ConditionInterface result = null;
		
		//Get custom parameters defined by the event
		HashMap<String, ArrayList<String>> parameters = conditionConfig.getConditionMap();
		
		String className = conditionConfig.getClassName();
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(String.class, HashMap.class);
			Object obj = constructor.newInstance(conditionConfig.getName(), parameters);
			if (obj instanceof ConditionInterface) {
				result = (ConditionInterface) obj;
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
			log.error("Cannot invoke method of class {}", className, e);
			throw new InvocationTargetException(e, "Method invoke error");
		}
		
		return result;
	}
	
//	private HashMap<String, ArrayList<String>> getCustomPropertiesFromEventConfig(EventConfig eventConfig, ConditionConfig conditionConfig) throws Exception {
//		String conditionName = conditionConfig.getName();
//		HashMap<String, ArrayList<String>> result = eventConfig.get.getConditionVariables(conditionName);
//		
//		if (result==null) {
//			throw new Exception("No parameter for condition " + conditionName);
//		}
//		
//		return result;
//	}
	
}
