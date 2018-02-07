package minds;

import java.util.ArrayList;
import java.util.Arrays;

import minds.behavior.SequenceBehavior;
import minds.behavior.DynamicBehaviorGoto;
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

public class FeederMind implements ExternalMindControlInterface {
	//Logger
	private static final Logger log = LoggerFactory.getLogger(FeederMind.class);
	
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
	private final ArrayList<String> obstacleBodies = new ArrayList<String>(Arrays.asList("OBSTACLE", "BREAKABLEOBSTACLE"));
	//Get other bodo players
	private final ArrayList<String> playerBodies = new ArrayList<String>(Arrays.asList("HUMANPLAYER","RANDOMPLAYER","ACTIONLESSPLAYER","BEHAVIORARSBODY","ARSPLAYERTYPE","ARSENHANCEDBODY"));
	private final ArrayList<String> foenames = new ArrayList<String>(Arrays.asList("MIA"));
	private final ArrayList<String> friendnames = new ArrayList<String>(Arrays.asList("NIKLAS"));
	//Eatables
	private final ArrayList<String> eatableBodies = new ArrayList<String>(Arrays.asList("EATABLEGOOD"));
	
	private int panicCounter = 0;
	
	private String myName = "";
	
	private int talkforbidden=10;
	
	public FeederMind(ExternalMindBodyInterface game) {
		gameMethods = game;
	}
	
	@Override
	public void startCycle() {
		//System inputs
		BodyPerceptionInterface bodyPerception = gameMethods.getBodyPerception();
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		log.debug("Perception {}", perception);
		
		
		
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
		
		
		//=== Drive Track ===//
		double currentHealth = (double)bodyPerception.getCurrentHealth();
		double maxHealth = (double)bodyPerception.getMaxHealth();
		
		
		//=== Emotion Track ===//
		double healthChange = (double)bodyPerception.getPainOrPleasure();
			
		
		//=== Perception Track ===//
		
		
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
		
		if (Math.random()<0.1 && closestEatableInThePerceptionList!=null) {
			if (closestEatableInThePerceptionList.equals(new Location(-1, 0))==true) {
				action = "TURN_RIGHT";
			} else if (closestEatableInThePerceptionList.equals(new Location(1, 0))==true) {
				action = "TURN_LEFT";
			} else if (closestEatableInThePerceptionList.equals(new Location(0, 1))==true) {
				action = "MOVE_FORWARD";
			} else if (closestEatableInThePerceptionList.equals(new Location(0, 0))==true) {
				action = "EAT";
			} else {
				//Create random action
				String[] actions = {"TURN_LEFT", "TURN_RIGHT"};
				action = actions[(int) (actions.length*Math.random())];
			}
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
		
		log.info(logText + "Action={}", action);
		
		//Execute action plans
		this.gameMethods.setAction(action);
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

}
