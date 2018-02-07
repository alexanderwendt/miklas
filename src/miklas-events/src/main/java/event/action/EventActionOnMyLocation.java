package event.action;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jgamegrid.Location;
import event.EventAction;

public class EventActionOnMyLocation extends EventAction {

	public EventActionOnMyLocation(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		//Get action inputs
		Location myLocation = this.getActionExecutor().getMyLocation();
		
		
		//Run effect on the target objects of the action taken
		this.myBody.executeActionOnEntityOnField(myLocation, this.triggerActionName, true);	//true because execute only on top entity
				
				
		//Perform change of state/action on the agent itself
		//Do nothing, only at reaction, something happens to the body
		
	}

	@Override
	protected void setIndividualCustomProperties() {
		//Do Nothing
		
	}

}
