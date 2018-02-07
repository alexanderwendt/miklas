package condition;

import java.util.ArrayList;
import java.util.HashMap;

import entity.EntityInterface;

public class ConditionIsUnexecuted extends Condition {

	public ConditionIsUnexecuted(String name,
			HashMap<String, ArrayList<String>> parameters) {
		super(name, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean testCondition() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setCustomProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initConditionWithPermanentDatastructures() {
		// TODO Auto-generated method stub
		
	}

}
