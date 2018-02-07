package minds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import utils.PerceptionUtils;

import ch.aplu.jgamegrid.Location;
import entity.mind.BodyPerceptionInterface;
import entity.mind.ExternalMindBodyInterface;
import entity.mind.ExternalMindControlInterface;
import entity.mind.ExternalPerceptionInterface;
import minds.behavior.*;

public class IntelligentMind implements ExternalMindControlInterface {

	private ExternalMindBodyInterface gameMethods;
	
	///////
	
	//General Utils
	private PerceptionUtils perceptionUtils = new PerceptionUtils();
	
	
	//Semantic knowledge about objects -> Associate the types with agent relevant groups
	//Obstacles
	private final ArrayList<String> obstacleBodies =
			new ArrayList<String>(Arrays.asList("OBSTACLE", "BREAKABLEOBSTACLE"));
	
	//Get other body players
	private final ArrayList<String> playerBodies = 
			new ArrayList<String>(Arrays.asList("HUMANPLAYER","RANDOMPLAYER",
					"ACTIONLESSPLAYER","BEHAVIORARSBODY","ARSPLAYERTYPE","ARSENHANCEDBODY"));
	
	///////
	
	//Only for health!
	private final int eat = 1;
	//Eatables
	private final ArrayList<String> eatableBodies = new ArrayList<String>(Arrays.asList("EATABLEGOOD"));
	
	public IntelligentMind(ExternalMindBodyInterface game) {
		gameMethods = game;
	}
	
	private int i = 0;
	private boolean turn = false;
	private boolean attack = false;
	
	@Override
	public void startCycle() {
		
		////////
		
		//=== Perception Track ===//
		ArrayList<ExternalPerceptionInterface> perception = gameMethods.getExternalPerception();
		
		//=== Infer Beliefs ===//
	
		Location infrontofme = new Location(0, 1);
		
		////////Obstacles
		
		//Get obstacles in the path
		ArrayList<Location> obstacleInThePathList 
			= perceptionUtils.getPositionOfEntity(this.obstacleBodies, new ArrayList<String>(), perception, false);
		
		Iterator<Location> iterator;
		
		turn = false;
		
		if (obstacleInThePathList.isEmpty()==false)
		{
			Location allofthem;
			iterator = obstacleInThePathList.iterator();
			
			while(iterator.hasNext())
			{
				allofthem = iterator.next();
				if( (allofthem.x == infrontofme.x) && (allofthem.y == infrontofme.y) )
				{
					turn = true;
				}
			}
		}
		
		////////Other Bodies
		
		ArrayList<Location> foesInThePerceptionList = 
				perceptionUtils.getPositionOfEntity(this.playerBodies, new ArrayList<String>(), perception, false);
		Location closestFoe = perceptionUtils.getClosestLocation(foesInThePerceptionList);
		
		attack = false;
		
		if(null != closestFoe)
		{
			if( (closestFoe.x == infrontofme.x) && (closestFoe.y == infrontofme.y) )
			{
				attack = true;
			}
		}
		
		//Get closest eatables and eat it
		Location closestEatableInThePerceptionList = 
				perceptionUtils.getClosestLocation(perceptionUtils.getPositionOfEntity(this.eatableBodies, new ArrayList<String>(), perception, false));

		
		///////Decision of next movement
		
		if (true == turn)
		{
			if(0==i || 1==i || 5==i || 3==i)
			{
				i=2;
			}
			/*if(3==i)
			{
				i=4;
			}*/
		}
				
		if(true == attack)
		{
			gameMethods.setAction("ATTACK");
		}

		else if(null != closestFoe)
		{			
			if(( 2 >= Math.abs(closestFoe.x) ) && ( 2 >= closestFoe.y))
			{
				if(0==closestFoe.x)
				{
					gameMethods.setAction("MOVE_FORWARD");
				}
				else if(0==closestFoe.y)
				{
					if(closestFoe.x > 0)
					{
						gameMethods.setAction("TURN_LEFT");
					}
					else
					{
						gameMethods.setAction("TURN_RIGHT");
					}
				}
				//simple else at within the area is not present, it means doing nothing, so ambushing
			}
			else // Else for outside the area
			{
				i=cruising(i);
			}
		}
		else if (closestEatableInThePerceptionList!=null) 
		{		
			//Change the comment and it will consider eating food
			//gameMethods.setAction( getActionToExecuteOnEntity(closestEatableInThePerceptionList.x, closestEatableInThePerceptionList.y, Action.EAT, 0) );
			i=cruising(i);
		}
		else
		{
			i=cruising(i);
		}
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}
	
	//Cruise around the map
	private int cruising(int i){
		switch(i) 
		{
			case 0:
				i++;
				gameMethods.setAction("MOVE_FORWARD");
				break;
				
			case 1:
				i++;
				gameMethods.setAction("MOVE_FORWARD");
				break;
				
			case 2:
				i++;
				gameMethods.setAction("TURN_LEFT");
				break;
				
			case 3:
				i++;
				gameMethods.setAction("MOVE_FORWARD");
				break;
				
			case 4:
				i++;
				gameMethods.setAction("TURN_RIGHT");
				break;
				
			case 5:
				i=0;
				gameMethods.setAction("MOVE_FORWARD");
				break;
		}
		return i;
		
	}
	
	
	public String getAction(int targetLocationX, int targetLocationY, int executeLocationY) {
		String action = "NONE";
		
		if (Math.abs(targetLocationY)>Math.abs(targetLocationX) || targetLocationX==0) {
			if (targetLocationY>executeLocationY) {
				action = Action.MOVE_FORWARD.toString();
			}
		} else if (targetLocationX<0) {
			action = Action.TURN_RIGHT.toString();
		} else if (targetLocationX>0) {
			action = Action.TURN_LEFT.toString();
		}
		
		return action;
	}
	
	public String getActionToExecuteOnEntity(int targetLocationX, int targetLocationY, Action actionToExecute, int executeLocationY) {
		String action = "NONE";
		
		int executeLocationX=0;
		executeLocationX=0;

		if (targetLocationX==executeLocationX && targetLocationY==executeLocationY) {
			action = actionToExecute.toString();
		} else {
			action = getAction(targetLocationX, targetLocationY, executeLocationY);
		}
		
		return action;
	}

}
