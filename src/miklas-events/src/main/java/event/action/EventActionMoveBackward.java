package event.action;

import ch.aplu.jgamegrid.Location;
import event.EventAction;

public class EventActionMoveBackward extends EventAction {

	public EventActionMoveBackward(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void setIndividualCustomProperties() {
		//Do nothing
	}

	@Override
	protected void runEffectOfEvent() {
		//Get action inputs
		Location backwardLocation = this.myBody.getLocationUtils().getAbsoluteLocationForRelativeLocation(this.getActionExecutor().getMyLocation(), this.getActionExecutor().getMyDirection(), new Location(0,-1));
		
		//Location newAbsoluteLocation = new Location(backwardLocation.x + this.myBody.getOwnerEntity().getX(), backwardLocation.y + this.myBody.getOwnerEntity().getY());
				
		//Run effect on the target objects of the action taken
		this.myBody.executeActionOnEntityOnField(backwardLocation, this.triggerActionName, false);
				
		//Perform change of state/action on the agent itself
		this.getActionExecutor().setMyLocation(backwardLocation);	
	}

}
