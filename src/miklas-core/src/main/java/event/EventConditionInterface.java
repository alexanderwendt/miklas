package event;

import condition.ConditionInterface;

public interface EventConditionInterface {
	public void registerCondition(ConditionInterface condition);
	public <T extends Object> boolean getLocalDataStructure(Datapoint<T> value) throws Exception;
	
}
