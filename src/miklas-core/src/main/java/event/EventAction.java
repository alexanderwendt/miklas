package event;

import entity.body.BodyInteractionEngineInterface;
import entity.body.BodyInterface;

public abstract class EventAction extends Event {
	
	
	protected String triggerActionName;

	public EventAction(String poEventName) {
		super(poEventName);
	}
	
	@Override
	protected void setCustomProperties() {
		triggerActionName = this.customParameter.getSingleParameter("triggeractionname");
		
		//Add individual custom properties
		this.setIndividualCustomProperties();
		
	}
	
	protected abstract void setIndividualCustomProperties();

	@Override
	protected String getTargetIdentifier() {
		return myBody.getOwnerEntity().entitiyIdentifier;
	}

	@Override
	protected void initEventWithPermanentStructures() {
		try {
			//Datapoint<BodyInterface> dp1 = new Datapoint<BodyInterface>(EventVariables.MYBODY.toString());
			//myBody = (BodyInterface)eventHandler.getLocalDataStructureFromContainer(EventVariables.MYBODY.toString(), BodyInterface.class);
			//actionExecutor = (BodyInteractionEngineInterface)eventHandler.getLocalDataStructureFromContainer(EventVariables.INTERACTIONENGINE.toString(), BodyInteractionEngineInterface.class);
			
			
			
			//eventHandler.getLocalDataStructureFromContainer(dp1);
			
			//myBody = dp1.getValue();
			
			
		} catch (Exception e) {
			log.error("", e);
		}
		
	}

	public BodyInteractionEngineInterface getActionExecutor() {
		return actionExecutor;
	}



}
