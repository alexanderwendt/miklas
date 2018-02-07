package event.body;

import ch.aplu.jgamegrid.Location;
import event.BodyEffectEvent;
import event.Datapoint;
import event.EventVariables;

public class EventIsPulled extends BodyEffectEvent {
	
	private final String TARGETLOCATION = "targetlocation";
	private final String ACTIONNAME = "executeaction";
	private String targetLocationVariableName;
	private String action;

	public EventIsPulled(String poEventName) {
		super(poEventName);

	}

	@Override
	protected void runEffectOfEvent() {
		//Get entity
		Location entityLocation = null;
		Datapoint<Location> dp = new Datapoint<Location>(EventVariables.LOCATIONOFCALLER.toString());
		try {
			this.getLocalDataStructure(dp);
			entityLocation = dp.getValue();	//Location of the entity, which is pulling
		} catch (Exception e) {
			log.error("{}> Cannot load {} in event", this.getEventName(), dp, e);
		}
				
		//Move object one step in the direction of the caller
		//Normalize the position of the pulling entity to 1
		
		Location newLocation = this.normalizeLocation(entityLocation, 1);
		
		Location newAbsoluteLocation = this.myBody.getLocationUtils().getAbsoluteLocationForRelativeLocation(this.myBody.getOwnerEntity().getLocation(), this.myBody.getOwnerEntity().getDirection(), newLocation);
		
		//set new location of pushed entity
		try {
			this.myBody.getTemporarySharedVariables().putData(new Datapoint<Location>(targetLocationVariableName, newAbsoluteLocation));
		} catch (Exception e) {
			log.error("Cannot set temporary datapoint", e);
		}
				
		//Execute action for displacement
		try {
			this.myBody.executeAction(action);
		} catch (Exception e) {
			log.error("Cannot execute action {}", action);
		}
		
	}

	@Override
	protected void setCustomProperties() {
		targetLocationVariableName = this.customParameter.getSingleParameter(TARGETLOCATION);
		action = this.customParameter.getSingleParameter(ACTIONNAME);
		
	}
	
	private Location normalizeLocation(Location location, int normRadius) {
		//Location result = location;
		
		//Get abs max of the corrdinates
		int absoluteMaxValue = Math.abs(location.x);
		if (Math.abs(location.y)>Math.abs(location.x)==true) {
			absoluteMaxValue = Math.abs(location.y);
		}
		
		Location result = new Location(location.x/absoluteMaxValue, location.y/absoluteMaxValue);
		
		return result;
	}

}
