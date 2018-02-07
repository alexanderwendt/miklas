package condition;

import event.EventConditionInterface;

public interface ConditionInterface {
	public boolean testCondition();
	public void init(EventConditionInterface event);
	public void initConditionWithPermanentDatastructures() throws Exception;
}
