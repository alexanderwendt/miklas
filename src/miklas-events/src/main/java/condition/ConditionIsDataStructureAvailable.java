package condition;

import java.util.ArrayList;
import java.util.HashMap;

import entity.body.BodyInterface;
import event.Datapoint;
import event.EventVariables;

public class ConditionIsDataStructureAvailable extends Condition {

	private final String DATASTRUCTURENAMEPROPERTY  = "datastructurename";
	private String dataStructureIdentifier;
	
	private BodyInterface body;
	
	public ConditionIsDataStructureAvailable(String name, HashMap<String, ArrayList<String>> parameters) {
		super(name, parameters);
	}

	@Override
	public boolean testCondition() {
		
		boolean result = false;
		
		boolean dataStructure = false;
		try {
			Datapoint<?> dp = new Datapoint<Object>(dataStructureIdentifier);
			dataStructure = body.getTemporarySharedVariables().getData(dp);
		} catch (Exception e) {
			log.error("Datatype does not exist");
		}
		
		//Object dataStructure = this.assignedEvent.getLocalDataStructure(dataStructureIdentifier);
		if (dataStructure==true) {
			result = true;
		}
		
		return result;
	}

	@Override
	protected void setCustomProperties() {
		dataStructureIdentifier =  this.customParameter.getSingleParameter(DATASTRUCTURENAMEPROPERTY);	
	}

	@Override
	public void initConditionWithPermanentDatastructures() throws Exception {
		//body = (BodyInterface) this.assignedEvent.getLocalDataStructure(EventVariables.MYBODY.toString(), BodyInterface.class);
		Datapoint<BodyInterface> dp = new Datapoint<BodyInterface>(EventVariables.MYBODY.toString());
		boolean res = this.assignedEvent.getLocalDataStructure(dp);
		body = dp.getValue();
	}
}
