package entity.mind;

import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public class RandomMind extends AnimateMind {

	public RandomMind(BodyMindInterface body, EvaluatorManagerMindInterface score) {
		super(body, score);
	}

	@Override
	protected void executeMindCycle() {
		
		//Create random action
		String[] actions = {"MOVE_FORWARD", "TURN_LEFT", "TURN_RIGHT", "EAT", "ATTACK"};
		String action = actions[(int) (actions.length*Math.random())];
		
		try {
			this.setAction(action);
		} catch (Exception e) {
			log.error("Error in updating action {}", action, e);
		}
		
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

	
	
//	private void moveStrategy()
//	  {
//	    Location pacLocation = pacActor.getLocation();
//	    double oldDirection = getDirection();
//
//	    // Use an approach strategy
//	    Location.CompassDirection compassDir =
//	      getLocation().get4CompassDirectionTo(pacLocation);
//	    Location best = getLocation().getNeighbourLocation(compassDir);
//	    setDirection(compassDir);
//	    if (type == 0 && canMove(best))
//	    {
//	      move();
//	    }
//	    else // normal movement
//	    {
//	      setDirection(oldDirection);
//
//	      Location next = getNextMoveLocation();
//	      if (canMove(next)) // Move forward
//	        setLocation(next);
//	      else  // Move left or right at random
//	      {
//	        int sign;
//	        if (Math.random() < 0.5)
//	          sign = 1;
//	        else
//	          sign = -1;
//	        turn(sign*90);
//	        next = getNextMoveLocation();
//	        if (canMove(next))
//	          setLocation(next);
//	        else
//	        {
//	          turn(180);
//	          next = getNextMoveLocation();
//	          if (canMove(next))
//	            setLocation(next);
//	          else
//	          {
//	            turn(-sign*90); // Backward
//	            next = getNextMoveLocation();
//	            if (canMove(next))
//	              setLocation(next);
//	          }
//	        }
//	      }
//	    }
//	  }
}
