package minds;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.mind.ExternalMindBodyInterface;
import entity.mind.ExternalMindControlInterface;
import entity.mind.ExternalPerceptionInterface;
import gameengine.GameEngineImpl;

public class RandomMind implements ExternalMindControlInterface {

	private static final Logger log = LoggerFactory.getLogger(RandomMind.class);
	
	private ExternalMindBodyInterface gameMethods;
	
	public RandomMind(ExternalMindBodyInterface game) {
		gameMethods = game;
	}
	
	@Override
	public void startCycle() {
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.info("Perception {}", perception);
		gameMethods.setAction("TURN_LEFT");
		
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

}
