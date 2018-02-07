package event;

import entity.body.BodyInteractionEngineInterface;
import entity.body.BodyInterface;

public abstract class EventAction extends Event {
	
	protected BodyInterface myBody;
	private BodyInteractionEngineInterface actionExecutor;
	
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
			//myBody = (BodyInterface)eventHandler.getLocalDataStructureFromContainer(EventVariables.MYBODY.toString(), BodyInterface.class);
			//actionExecutor = (BodyInteractionEngineInterface)eventHandler.getLocalDataStructureFromContainer(EventVariables.INTERACTIONENGINE.toString(), BodyInteractionEngineInterface.class);
			Datapoint<BodyInterface> dp = new Datapoint<BodyInterface>(EventVariables.MYBODY.toString());
			Datapoint<BodyInteractionEngineInterface> dp2 = new Datapoint<BodyInteractionEngineInterface>(EventVariables.INTERACTIONENGINE.toString());
			boolean res1 = eventHandler.getLocalDataStructureFromContainer(dp);
			boolean res2 = eventHandler.getLocalDataStructureFromContainer(dp2);
			
			myBody = dp.getValue();
			actionExecutor = dp2.getValue();
			
		} catch (Exception e) {
			log.error("", e);
		}
		
	}

	public BodyInteractionEngineInterface getActionExecutor() {
		return actionExecutor;
	}



}
