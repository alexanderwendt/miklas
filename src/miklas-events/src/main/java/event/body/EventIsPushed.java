package event.body;

import ch.aplu.jgamegrid.Location;
import event.BodyEffectEvent;
import event.Datapoint;
import event.EventVariables;

public class EventIsPushed extends BodyEffectEvent {
	
	private final String TARGETLOCATION = "targetlocation";
	private final String ACTIONNAME = "executeaction";
	private String targetLocationVariableName;
	private String action;
	
	public EventIsPushed(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		//Get entity
		Location entityLocation = null;
		Datapoint<Location> dp = new Datapoint<Location>(EventVariables.LOCATIONOFCALLER.toString());
		try {
			this.getLocalDataStructure(dp);
			entityLocation = dp.getValue();
		} catch (Exception e) {
			log.error("{}> Cannot load {} in event", this.getEventName(), dp, e);
		}
		
		//Calculate force from pushing entity
		Location newLocation = this.getOppositePosition(entityLocation);
		
		Location newAbsoluteLocation = this.myBody.getLocationUtils().getAbsoluteLocationForRelativeLocation(this.myBody.getOwnerEntity().getLocation(), this.myBody.getOwnerEntity().getDirection(), newLocation);		//new Location(newLocation.x + this.myBody.getOwnerEntity().getX(), newLocation.y + this.myBody.getOwnerEntity().getY());
		
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
	
	private Location getOppositePosition(Location loc) {
		Location newLocation = new Location(loc.x*(-1), loc.y*(-1));
		
		return newLocation;
	}

	@Override
	protected void setCustomProperties() {
		targetLocationVariableName = this.customParameter.getSingleParameter(TARGETLOCATION);
		action = this.customParameter.getSingleParameter(ACTIONNAME);
	}

}
