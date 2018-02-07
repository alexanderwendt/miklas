package event.body;

import event.BodyEffectEvent;

public class EventAdditionalActionExecute extends BodyEffectEvent {
	
	private final String ACTIONTOEXECUTENAME = "actiontoexecute";
	
	private String actionNameToExecute = "";

	public EventAdditionalActionExecute(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		try {
			this.myBody.executeAction(this.actionNameToExecute);
			log.debug("Execute action {}", actionNameToExecute);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void setCustomProperties() {
		this.actionNameToExecute = this.customParameter.getSingleParameter(ACTIONTOEXECUTENAME);
		
	}

}
