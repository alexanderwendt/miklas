package minds;

import java.util.ArrayList;
import java.util.Arrays;

import minds.behavior.SequenceBehavior;
import minds.behavior.DynamicBehaviorGoto;
import minds.behavior.Action;
import minds.behavior.ars.ARSBehaviorPanic;
import minds.behavior.ars.ARSBehaviorSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Location;
import utils.PerceptionUtils;
import entity.mind.BodyPerceptionInterface;
import entity.mind.ExternalMindBodyInterface;
import entity.mind.ExternalMindControlInterface;
import entity.mind.ExternalPerceptionInterface;
import gameengine.GameEngineImpl;

public class MiklasMind implements ExternalMindControlInterface {
	//Logger
	private static final Logger log = LoggerFactory.getLogger(MiklasMind.class);
	
	//General Utils
	private PerceptionUtils perceptionUtils = new PerceptionUtils();
	private ExternalMindBodyInterface gameMethods;
	
	//Procedural knowledge
	//Behaviors
	private final SequenceBehavior behaviorPanic = new ARSBehaviorPanic();
	private final SequenceBehavior behaviorSearch = new ARSBehaviorSearch();
	private final DynamicBehaviorGoto behaviorGoto = new DynamicBehaviorGoto();
	
	//Semantic knowledge about objects
	//Obstacles
	private final ArrayList<String> obstacleBodies = new ArrayList<String>(Arrays.asList("OBSTACLE"));
	//Get other bodo players
	private final ArrayList<String> playerBodies = new ArrayList<String>(Arrays.asList("HUMANPLAYER","RANDOMPLAYER","ACTIONLESSPLAYER","BEHAVIORARSBODY","ARSPLAYERTYPE","ARSENHANCEDBODY"));
	private final ArrayList<String> foenames = new ArrayList<String>(Arrays.asList("MIA"));
	private final ArrayList<String> friendnames = new ArrayList<String>(Arrays.asList("NIKLAS"));
	//Eatables
	private final ArrayList<String> eatableBodies = new ArrayList<String>(Arrays.asList("EATABLEGOOD"));
	
	private int panicCounter = 0;
	
	private String myName = "";
	
	public MiklasMind(ExternalMindBodyInterface game) {
		gameMethods = game;
	}
	
	@Override
	public void startCycle() {
		//Behaviors:
		//React on pain
		// - if Health < 50% then behavior flee
		// - if Health >= 50% behavior attack the attacker
		
		//Follow drives
		// - if health < 80% search for food
		// 		- if food not in Perception -> behavior search
		// 		- if Food in Perception
		//			- If Enemy && Food && Health >= 80% -> Panic
		//			- If Enemy && Food && Health <80% && Health >50% -> Attack enemy
		//			- If Enemy && Food && Health < 50% -> Goto Food
		//			- If Food -> Goto Food
		// - if health >= 80% -> search for enemy
		//		- if enemy in perception -> attack enemy 
		
		BodyPerceptionInterface bodyPerception = gameMethods.getBodyPerception();
		//=== Drive Track ===//
		double currentHealth = (double)bodyPerception.getCurrentHealth();
		double maxHealth = (double)bodyPerception.getMaxHealth();
		
		
		//=== Emotion Track ===//
		double healthChange = (double)bodyPerception.getPainOrPleasure();
			
		
		//=== Perception Track ===//
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.debug("Perception {}", perception);
		
		
		//=== Infer Beliefs ===//
		//Get obstacles in the path
		ArrayList<Location> obstacleInThePathList = perceptionUtils.getPositionOfEntity(this.obstacleBodies, new ArrayList<String>(), perception, false);
		//ArrayList<ExternalPerceptionInterface> obstacleInThePathList = perceptionUtils.getEntitiesOfPosition(0, 1, obstacleBodies, new ArrayList<String>(), perception);
		
		//Get friends in the list
		ArrayList<Location> friendsInThePerceptionList = perceptionUtils.getPositionOfEntity(this.playerBodies, friendnames, perception, false);
		
		//Get foes in perception
		ArrayList<Location> foesInThePerceptionList = perceptionUtils.getPositionOfEntity(this.playerBodies, foenames, perception, false);
		Location closestFoe = perceptionUtils.getClosestLocation(foesInThePerceptionList);
		
		//Get closest eatables
		ArrayList<Location> eatableList = perceptionUtils.getPositionOfEntity(this.eatableBodies, new ArrayList<String>(), perception, false);
		Location closestEatableInThePerceptionList = perceptionUtils.getClosestLocation(eatableList);
		
		
		//=== Match motivations with beliefs ===//
		String action = "NONE";
		String logText = "";
		
		//Avoid obstacles
		if (panicCounter>0) {
			action = this.behaviorPanic.getNextAction();
			panicCounter--;
		} else if (obstacleInThePathList.isEmpty()==false) {
			
			String[] moveactions = {Action.TURN_LEFT.toString(), Action.TURN_RIGHT.toString()};
			action = moveactions[(int) (moveactions.length*Math.random())];
			logText += "Obstacle in the path. ";
		} else if (healthChange<-1) {			//If pain and foe in list
			logText += "Pain is felt, attack. ";
			Location foe = perceptionUtils.getClosestLocation(foesInThePerceptionList);
			if (currentHealth/maxHealth<0.5) {				//if Health < 50% then panic
				action = this.behaviorPanic.getNextAction();
				logText += "Health < 50%. ";
			} else {										//if Health > 50% attack
				logText += "Health >= 50%. ";
				
				//Get next foe player
				//Location foe = perceptionUtils.getClosestLocation(foesInThePerceptionList);
				if (foe!=null) {
					logText += "Foe in perception. ";
					action = this.behaviorGoto.getActionToExecuteOnEntity(foe.x, foe.y, Action.ATTACK, 1);
					//Reset static behaviors
					resetStaticBehaviors();
					logText += "Goto enemy at location "+ foe;
				} else {
					logText += "Foe not in perception. ";
					action = this.behaviorSearch.getNextAction();
					logText += "Search for food or foe. ";
				}
			}
		} else if (currentHealth/maxHealth<0.8) {	//Search for food
			logText += "Health < 100%";
			if (closestEatableInThePerceptionList!=null) {
				if (foesInThePerceptionList.isEmpty()==false) {
					logText += "Foe in perception. ";
					if (currentHealth/maxHealth<1.0 && currentHealth/maxHealth>=0.6) {
						logText += "Health<80%. ";
						action = this.behaviorPanic.getNextAction();
						panicCounter=7;
						logText += "Panic. ";
					} else if (currentHealth/maxHealth<0.6 && currentHealth/maxHealth>0.3) {
						logText += "Health>50%, Health<60%. ";
						action = this.behaviorGoto.getActionToExecuteOnEntity(foesInThePerceptionList.get(0).x, foesInThePerceptionList.get(0).y, Action.ATTACK, 1);
						logText += "Attack foe. ";
						//Reset static behaviors
						resetStaticBehaviors();
					} else {
						logText += "Health < 50%. ";
						action = this.behaviorGoto.getActionToExecuteOnEntity(closestEatableInThePerceptionList.x, closestEatableInThePerceptionList.y, Action.EAT, 1);
						logText += "Eat. ";
						
						//Reset static behaviors
						resetStaticBehaviors();
					}
				} else {
					logText += "No foe. ";
					action = this.behaviorGoto.getActionToExecuteOnEntity(closestEatableInThePerceptionList.x, closestEatableInThePerceptionList.y, Action.EAT, 1);
					logText += "Eat. ";
				}
				
				//Reset static behaviors
				//resetStaticBehaviors();
			} else {
				//If fow in vision
				if (foesInThePerceptionList.isEmpty()==false && currentHealth/maxHealth>0.5) {
					logText += "Health>50%, Health<60%. ";
					action = this.behaviorGoto.getActionToExecuteOnEntity(foesInThePerceptionList.get(0).x, foesInThePerceptionList.get(0).y, Action.ATTACK, 1);
					logText += "Attack foe. ";
				} else {
					logText += "No food in vision";
					action = this.behaviorSearch.getNextAction();
					logText += "Set action search. ";
				}
				
			}
		} else {
			logText += "Health = 100%";
			action = this.behaviorSearch.getNextAction();
			logText += "Set action search. ";
		}
		
		log.info(logText + "Action={}", action);
		
		
		//Execute action plans
		this.gameMethods.setAction(action);
	}
	
	private void resetStaticBehaviors() {
		this.behaviorPanic.init();
		this.behaviorSearch.init();
		this.panicCounter=0;
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

}
