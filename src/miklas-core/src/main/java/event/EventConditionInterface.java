package event;

import condition.ConditionInterface;
import entity.body.BodyInteractionEngineConditionInterface;

public interface EventConditionInterface {
	public void registerCondition(ConditionInterface condition);
	public <T extends Object> boolean getLocalDataStructure(Datapoint<T> value) throws Exception;
	public BodyInteractionEngineConditionInterface getBodyInteractionInformation();
	
}
