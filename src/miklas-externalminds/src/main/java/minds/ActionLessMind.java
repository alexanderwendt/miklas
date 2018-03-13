package minds;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.mind.ExternalMindBodyInterface;
import entity.mind.ExternalMindControlInterface;
import entity.mind.ExternalPerceptionInterface;



public class ActionLessMind implements ExternalMindControlInterface {

	private static final Logger log = LoggerFactory.getLogger(ActionLessMind.class); 
	
	private ExternalMindBodyInterface gameMethods;
	
	public ActionLessMind(ExternalMindBodyInterface game) {
		gameMethods = game;
	}
	
	@Override
	public void startCycle() {
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.info("Test");
		log.info("Perception {}", perception);
	}

	@Override
	public void killMind() {
		log.info("Killed mind");
		
	}
}
