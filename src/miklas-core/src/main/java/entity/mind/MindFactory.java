package entity.mind;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.MindConfig;
import entity.Entity;
import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public class MindFactory {

	private static final Logger log = LoggerFactory.getLogger(MindFactory.class);
	private final EvaluatorManagerMindInterface score;
	
	public MindFactory(EvaluatorManagerMindInterface score) {
		this.score = score;
	}
	
	public AnimateMindInterface createMind(MindConfig mindConfig, BodyMindInterface poBody, Entity poEntity) throws Exception {
		AnimateMindInterface oResult=null;
		
		if (mindConfig.getMindType().equals("INTERNAL")) {
			switch (mindConfig.getMindClass()) {
			case "HUMAN":
				oResult = new HumanMind(poEntity, poBody, score);
				break;
			case "RANDOM":
				oResult = new RandomMind(poBody, score);
				break;
			case "ACTIONLESS":
				oResult = new ActionlessMind(poBody, score);
				break;
			default:
				log.error("Could not find internal mind {}", mindConfig.getMindClass());
				throw new IllegalArgumentException("No mind could be found to internal mind type " + mindConfig.getMindClass());
			}
		} else if (mindConfig.getMindType().equals("EXTERNAL")) {
			try {
				oResult = createExternalMind(poBody, mindConfig);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | NoSuchMethodException e) {
				log.error("Could not create external mind");
				throw new Exception(e.getMessage());
			}
			
		} else {
			log.error("Could not find mind type{}. Only types INTERNAL and EXTERNAL are allowed", mindConfig.getMindType());
			throw new IllegalArgumentException("No mind could be found to mind type " + mindConfig.getMindType() + ". Only types INTERNAL and EXTERNAL are allowed");
		}
		
		return oResult;
	}
	
	public AnimateMindInterface createExternalMind(BodyMindInterface poBody, MindConfig mindConfig) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		//Create the External mind but do not set dependencies yet
		AnimateMindInterface result = null;
		ExternalMind internalMind = new ExternalMind(poBody, score);
		ExternalMindControlInterface externalMind = null;
		
		//Create the real external mind		
		String className = mindConfig.getMindClass();
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(ExternalMindBodyInterface.class);
			Object obj = constructor.newInstance(internalMind);
			if (obj instanceof ExternalMindControlInterface) {
				externalMind = (ExternalMindControlInterface) obj;
				
				//Assign the external mind here, which shall be triggered at each step
				internalMind.setExternalMind(externalMind);
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
			throw new InvocationTargetException(e, e.getMessage());
		}
		
		result = internalMind;
		
		return result;
	}
}
