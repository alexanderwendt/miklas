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

public class ARSMindSimulatedBehavior implements ExternalMindControlInterface {

	private static final Logger log = LoggerFactory.getLogger(ARSMindSimulatedBehavior.class);
	
	private PerceptionUtils perceptionUtils = new PerceptionUtils();
	private ExternalMindBodyInterface gameMethods;
	
	private final SequenceBehavior behaviorPanic;
	private final SequenceBehavior behaviorSearch;
	private final DynamicBehaviorGoto behaviorGoto;
	
	//Obstacles
	private final ArrayList<String> obstacleBodies = new ArrayList<String>(Arrays.asList("OBSTACLE"));
	//Get other bodo players
	private final ArrayList<String> playerBodies = new ArrayList<String>(Arrays.asList("HUMANPLAYER","RANDOMPLAYER","ACTIONLESSPLAYER","BEHAVIORARSBODY","ARSPLAYERTYPE","ARSENHANCEDBODY", "ANNBODY"));
	private ArrayList<String> foenames = new ArrayList<String>(Arrays.asList("CHARLIE", "ADAM", "BODO"));
	private ArrayList<String> friendnames = new ArrayList<String>(Arrays.asList("ADAM"));
	//Eatables
	private final ArrayList<String> eatableBodies = new ArrayList<String>(Arrays.asList("EATABLEGOOD"));
	
	private int panicCounter = 0;
	
	private String myName = "";
	
	public ARSMindSimulatedBehavior(ExternalMindBodyInterface game) {
		gameMethods = game;
		
		//Instantiate behaviors
		behaviorPanic = new ARSBehaviorPanic();
		behaviorSearch = new ARSBehaviorSearch();
		behaviorGoto = new DynamicBehaviorGoto();
	}
	
	@Override
	public void startCycle() {
		//Behaviors:
		//If pain
		//	- if Health < 50% then panic
		//	- if Health > 50% attack
		//If Health < 80 % search for food
		//	- if Food not in Perception -> ARS Search Behavior
		//	- if Food in Perception
		//		- If Enemy && Food && Health >= 80% -> Panic
		//		- If Enemy && Food && Health <80% && Health >50% -> Attack Bodo
		//		- If Enemy && Food && Health < 50% -> Goto Food
		//		- If Food -> Goto Food
		
		
		//=== Get perception ===//
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.debug("Perception {}", perception);
		
		//Get interesting objects
		//Get my name
		if (this.myName.equals("")==true) {
			ArrayList<ExternalPerceptionInterface> myObject = perceptionUtils.getEntitiesOfPosition(0, 0, playerBodies, new ArrayList<String>(), perception);
			this.myName = myObject.get(0).getObjectName();
			
			if (this.myName.equals("ADAM")==true) {
				foenames = new ArrayList<String>(Arrays.asList("BODO", "CHARLIE"));
				friendnames = new ArrayList<String>(Arrays.asList("ADAM"));
			} else {
				foenames = new ArrayList<String>(Arrays.asList("ADAM", "CHARLIE"));
				friendnames = new ArrayList<String>(Arrays.asList("BODO"));
			}
			
		}
		
		
		//Get obstacles in the path
		ArrayList<ExternalPerceptionInterface> obstacleInThePathList = perceptionUtils.getEntitiesOfPosition(0, 1, obstacleBodies, new ArrayList<String>(), perception);
		
		//Get friends in the list
		ArrayList<Location> friendsInThePerceptionList = perceptionUtils.getPositionOfEntity(playerBodies, friendnames, perception, false);
		
		//Get foes in perception
		ArrayList<Location> foesInThePerceptionList = perceptionUtils.getPositionOfEntity(playerBodies, foenames, perception, false);
		
		//Get closest eatables
		Location closestEatableInThePerceptionList = perceptionUtils.getClosestLocation(perceptionUtils.getPositionOfEntity(this.eatableBodies, new ArrayList<String>(), perception, false));
		
		
		
		
		//=== Get motivation ===//
		BodyPerceptionInterface bodyPerception = gameMethods.getBodyPerception();
		double currentHealth = (double)bodyPerception.getCurrentHealth();
		double maxHealth = (double)bodyPerception.getMaxHealth();
		double healthChange = (double)bodyPerception.getPainOrPleasure();
		
		
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
