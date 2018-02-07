package minds;

import java.util.ArrayList;
import java.util.Arrays;

import minds.behavior.DynamicBehaviorGoto;
import minds.behavior.miklas.BehaviorAvoidObstable;
import minds.behavior.Action;
import minds.behavior.SequenceBehavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Location;
import utils.PerceptionUtils;
import entity.mind.ExternalMindBodyInterface;
import entity.mind.ExternalMindControlInterface;
import entity.mind.ExternalPerceptionInterface;

public class BetterThanARSBehaviorMind implements ExternalMindControlInterface {

	private static final Logger log = LoggerFactory.getLogger(BetterThanARSBehaviorMind.class);
	
	private ExternalMindBodyInterface gameMethods;
	private PerceptionUtils perceptionUtils = new PerceptionUtils();
	
	private final DynamicBehaviorGoto behaviorGoto = new DynamicBehaviorGoto();
	private final SequenceBehavior behaviorAvoidObstacle = new BehaviorAvoidObstable();
	
	private int talkforbidden=(int) (Math.random()*20);
	
	private int unsuccessfulGoto = 0;
	private int patienceLimit = 2;
	
	public BetterThanARSBehaviorMind(ExternalMindBodyInterface game) {
		gameMethods = game;
		
	}
	
	@Override
	public void startCycle() {
		
		boolean isPrioritized = false;
		//Get perception
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.info("Perception {}", perception);
		
		double relativeHealth = Double.valueOf(this.gameMethods.getBodyPerception().getCurrentHealth())/Double.valueOf(this.gameMethods.getBodyPerception().getMaxHealth());
		
		//Make beliefs, infer beliefs
		ArrayList<String> obstacleBodies = new ArrayList<String>(Arrays.asList("OBSTACLE", "BREAKABLEOBSTACLE"));
		ArrayList<ExternalPerceptionInterface> obstacleInThePathList = perceptionUtils.getEntitiesOfPosition(0, 1, obstacleBodies, new ArrayList<String>(), perception);
		ArrayList<ExternalPerceptionInterface> obstacleInTheLeftPathList = perceptionUtils.getEntitiesOfPosition(-1, 1, obstacleBodies, new ArrayList<String>(), perception);
		ArrayList<ExternalPerceptionInterface> obstacleInTheRightPathList = perceptionUtils.getEntitiesOfPosition(1, 1, obstacleBodies, new ArrayList<String>(), perception);
		
		ArrayList<String> attackableBodies = new ArrayList<String>(Arrays.asList("HUMANPLAYER","RANDOMPLAYER","ACTIONLESSPLAYER","ARSPLAYERTYPE", "ARSENHANCEDBODY", "ANNBODY", "FEEDERBODY"));
		ArrayList<ExternalPerceptionInterface> playersInThePathList = perceptionUtils.getEntitiesOfPosition(0, 1, attackableBodies, new ArrayList<String>(), perception);
		
		ArrayList<ExternalPerceptionInterface> playersInLeftThePathList = perceptionUtils.getEntitiesOfPosition(1, 0, attackableBodies, new ArrayList<String>(), perception);
		
		ArrayList<ExternalPerceptionInterface> playersInRightThePathList = perceptionUtils.getEntitiesOfPosition(-1, 0, attackableBodies, new ArrayList<String>(), perception);
		
		ArrayList<String> eatableBodies = new ArrayList<String>(Arrays.asList("EATABLEGOOD","EATABLEBAD"));
		//Get closest eatables
		Location closestEatableInThePerceptionList = perceptionUtils.getClosestLocation(perceptionUtils.getPositionOfEntity(eatableBodies, new ArrayList<String>(), perception, false));
				
		//ArrayList<ExternalPerceptionInterface> entitiesInField = perceptionUtils.getEntitiesOfPosition(0, 0, eatableBodies, new ArrayList<String>(), perception);
		
		//Decision unit
		String action = "";
		if (playersInLeftThePathList.isEmpty()==false) {
			action = "TURN_LEFT";
		} else if (playersInRightThePathList.isEmpty()==false) {
			action = "TURN_RIGHT";
			//first attack
		} else if (playersInThePathList.isEmpty()==false) {
			//Player in front of me
			double choice = Math.random();
			if (choice<0.5) {
				action = "ATTACK";
				isPrioritized=true;
			} else {
				action = "NONE";
			}		
		} else if (closestEatableInThePerceptionList!=null && closestEatableInThePerceptionList.y<2 && closestEatableInThePerceptionList.x<2 && closestEatableInThePerceptionList.x>-2) {
			action = this.behaviorGoto.getActionToExecuteOnEntity(closestEatableInThePerceptionList.x, closestEatableInThePerceptionList.y, Action.EAT, 0);
			
			if (action != "EAT") {
				unsuccessfulGoto++;
			}
			//cake at my place
			//action = "EAT";
		} else {
			//Create random action
			String[] actions = {"MOVE_FORWARD", "MOVE_FORWARD", "MOVE_FORWARD", "TURN_LEFT", "TURN_RIGHT"};
			action = actions[(int) (actions.length*Math.random())];
		}
		
		if (unsuccessfulGoto>=this.patienceLimit) {
			action = "MOVE_FORWARD";
			this.unsuccessfulGoto=0;
		}
		
		if (this.behaviorAvoidObstacle.isFinished()==false) {
			action = this.behaviorAvoidObstacle.getNextAction();
		}
		
		if (obstacleInThePathList.isEmpty()==false) {
			//Obstacle in the way
			isPrioritized=true;
			//Reset
			this.behaviorAvoidObstacle.init();
			//Get action
			action = behaviorAvoidObstacle.getNextAction();
			
			
			if (obstacleInTheLeftPathList.isEmpty()==false) {
				action="TURN_LEFT";
			} else if (obstacleInTheRightPathList.isEmpty()==false) {
				action="TURN_RIGHT";
			} else {
				String[] moveactions = {"TURN_LEFT", "TURN_RIGHT"};
				action = moveactions[(int) (moveactions.length*Math.random())];
			}
		}
		
		if (isPrioritized==false) {
			//Remove determinism and put random actions in 10% of the cases
			if (Math.random()<0.05) {
				String[] allactions = {"MOVE_FORWARD", "TURN_LEFT", "TURN_RIGHT", "EAT", "ATTACK"};
				action = allactions[(int) (allactions.length*Math.random())];
			}
			
			//Talk sometimes
			if (talkforbidden==0) {
				action = "TALK";
				
				this.talkforbidden=50;
			} else {
				if (Math.random()<0.5) {
					this.talkforbidden--;
				}
			}
		}
		
		try {
			this.gameMethods.setAction(action);
		} catch (Exception e) {
			log.error("Error in updating action {}", action, e);
		}
		
//		synchronized (this) {
//			try {
//				this.wait(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

}
