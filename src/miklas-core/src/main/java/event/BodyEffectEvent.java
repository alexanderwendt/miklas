package event;

import entity.body.BodyInteractionEngineInterface;
import entity.body.BodyInterface;

public abstract class BodyEffectEvent extends Event {

	//=== Local variables for this type ===//
	protected BodyInterface myBody;
	private BodyInteractionEngineInterface actionExecutor;
	
	
	public BodyEffectEvent(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void initEventWithPermanentStructures() {
		try {
			//myBody = (BodyInterface) eventHandler.getLocalDataStructureFromContainer(EventVariables.MYBODY.toString(), BodyInterface.class);
			Datapoint<BodyInterface> dp = new Datapoint<BodyInterface>(EventVariables.MYBODY.toString());
			boolean isFound = eventHandler.getLocalDataStructureFromContainer(dp);
			myBody = dp.getValue();
			
			Datapoint<BodyInteractionEngineInterface> dp2 = new Datapoint(EventVariables.INTERACTIONENGINE.toString());
			boolean res2 = eventHandler.getLocalDataStructureFromContainer(dp2);
			actionExecutor = dp2.getValue();
			
		} catch (Exception e) {
			log.error("Cannot load data structure {} from container", EventVariables.MYBODY.toString(), e);
		}
		
		try {
			this.runEventInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected void runEventInit() throws Exception {
		
	}
	
	public BodyInteractionEngineInterface getActionExecutor() {
		return actionExecutor;
	}

	@Override
	protected String getTargetIdentifier() {
		return myBody.getOwnerEntity().getEntityIdentifier();
	}


}
