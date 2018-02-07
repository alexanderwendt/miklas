package event.body;

import event.BodyEffectEvent;

public class EventDeath extends BodyEffectEvent {
	
	private int minHealth;

	public EventDeath(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		//Set minhealth
		//this.myBody.getSharedVariables().setPermanentData(new Datapoint<Integer>(this.minhealthVariableName, this.minHealth));
				
		//Set current health
		//this.myBody.getSharedVariables().setPermanentData(new Datapoint<Integer>(healthVariableName, initHealth));
		
		this.myBody.killActor();
	}

	@Override
	protected void setCustomProperties() {
		// none
		
	}

}
