package event.body;

import event.BodyEffectEvent;

public class EventAttackPlayer extends BodyEffectEvent {
	
	//private static final Logger log = myLogger.getLog("Event");

	public EventAttackPlayer(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		//Do nothing, as this is the attacker
		
		log.debug("Attack!!!");
		
	}

	@Override
	protected void setCustomProperties() {
		//None
		
	}

}
