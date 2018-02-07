package event.action;

import ch.aplu.jgamegrid.Location;
import event.EventAction;

public class EventActionMoveForward extends EventAction {

	public EventActionMoveForward(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		
		//Get action inputs
		Location nextLocation = this.getActionExecutor().getNeighborLocationOfMyDirection();
		
		//Run effect on the target objects of the action taken
		this.myBody.executeActionOnEntityOnField(nextLocation, this.triggerActionName, false);
		
		//Perform change of state/action on the agent itself
		this.getActionExecutor().setMyLocation(nextLocation);
		
	}

	@Override
	protected void setIndividualCustomProperties() {
		//Do Nothing
		
	}

}
