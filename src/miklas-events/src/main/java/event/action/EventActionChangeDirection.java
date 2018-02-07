package event.action;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jgamegrid.Location;
import event.EventAction;

public class EventActionChangeDirection extends EventAction {
	
	private final String DIRECTIONCHANGENAME = "directionchange";
	
	private double directionChange;

	public EventActionChangeDirection(String poEventName) {
		super(poEventName);

	}

	@Override
	protected void runEffectOfEvent() {
		//Get action inputs
		Location myLocation = this.getActionExecutor().getMyLocation();
		double currentDirection = this.getActionExecutor().getMyDirection() + 90;
		double newDirection = currentDirection + directionChange;
		
		log.debug("Turn from {} to {}", currentDirection, newDirection);
		
		//Run effect on the target objects of the action taken
		this.myBody.executeActionOnEntityOnField(myLocation, this.triggerActionName, false);
		
		//Perform change of state/action on the agent itself
		this.getActionExecutor().setMyDirection(newDirection);								//Handlung
		
	}

	@Override
	protected void setIndividualCustomProperties() {
		directionChange = Double.valueOf(this.customParameter.getSingleParameter(DIRECTIONCHANGENAME));
		
	}

}
