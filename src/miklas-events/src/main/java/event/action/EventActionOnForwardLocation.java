package event.action;

import ch.aplu.jgamegrid.Location;
import event.EventAction;

public class EventActionOnForwardLocation extends EventAction {

	private final static String ACTIONBEFORENAME = "actionbefore";
	private final static String ACTIONAFTERNAME = "actionafter";
	
	private String actionBefore="";
	private String actionAfter="";
	private boolean isInit=false;
	
	public EventActionOnForwardLocation(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void setIndividualCustomProperties() {
		this.actionBefore = this.customParameter.getSingleParameter(ACTIONBEFORENAME);
		this.actionAfter = this.customParameter.getSingleParameter(ACTIONAFTERNAME);
		this.isInit=true;
		log.warn("Event={}, Individual properties: Action before={}, Action after={}", this.getEventName(), this.actionBefore, this.actionAfter);
		
	}

	@Override
	protected void runEffectOfEvent() {
		if (this.isInit==false) {
			log.error("Event={}, ERROR not initialized", this.getEventName());
		}
		//Get action inputs
		Location myNextLocation = this.getActionExecutor().getNeighborLocationOfMyDirection();
		
		//Run action of the owner if there is any action to take
		try {
			try {
				//Run an action before the main event
				this.myBody.executeAction(this.actionBefore);
			} catch (Exception e) {
				log.error("Cannot execute action before event {}", this.actionBefore);
				throw e;
			}
			
			
			//Run effect on the target objects of the action taken
			this.myBody.executeActionOnEntityOnField(myNextLocation, this.triggerActionName, true);	//true because execute only on top entity
			
			//Run an action after the event
			try {
				this.myBody.executeAction(this.actionAfter);
			} catch (Exception e) {
				log.error("Cannot execute action after event {}", this.actionAfter);
				throw e;
			}
			
			
		} catch (Exception e) {
			log.error("Cannot execute actions or event", e);
		}
	}

}
