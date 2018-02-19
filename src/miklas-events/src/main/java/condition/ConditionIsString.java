package condition;

import java.util.ArrayList;
import java.util.HashMap;

import event.Datapoint;
import event.EventVariables;

/**
 * @author wendt
 * 
 * Checks if a string from a certain property "sourceproperty" has the value of the "comparedatastructure".
 * 
 * Config file example
 * 	condition.triggeraction.name=TRIGGERACTION
 *	condition.triggeraction.classname=condition.ConditionIsString
 *	condition.triggeraction.custom.sourceproperty=triggeraction
 *	condition.triggeraction.custom.comparedatastructure=ACTIONOFCALLER
 *
 * The source property is triggeraction. An event sets the ...triggeraction=MOVE. If the local variable ACTIONOFCALLER has this value, then the condition is true.
 *
 */
public class ConditionIsString extends Condition {

	private final String SOURCEPROPERTY  = "sourceproperty";
	private final String COMPAREDATASTRUCTURE = "comparedatastructure";
	
	private ArrayList<String> sourceStringList;
	private String sourceProperty;
	private String compareDataStructureIdentifier;
	
	public ConditionIsString(String name, HashMap<String, ArrayList<String>> parameters) {
		super(name, parameters);

	}

	@Override
	public boolean testCondition() {
		boolean result = false;
		
		String actionObject="";
		try {
			Datapoint<String> dp = new Datapoint<String>(compareDataStructureIdentifier);
			boolean res = this.assignedEvent.getLocalDataStructure(dp);
			actionObject = dp.getValue();
		} catch (Exception e) {
			log.error("Datatype wrong", e);
		}		
		if (this.sourceStringList.contains(actionObject)==true) {
			result = true;
		}
		
		return result;
	}

	@Override
	protected void setCustomProperties() {
		try {
			//Get the string with the name of the property used in the events as a source for strings
			sourceProperty =  this.customParameter.getSingleParameter(SOURCEPROPERTY);
			//Get the strings of that property in the events
			sourceStringList = this.customParameter.getMultipleParameter(sourceProperty);
			
			//Get the comparestring from the data structures
			compareDataStructureIdentifier = this.customParameter.getSingleParameter(COMPAREDATASTRUCTURE);
		} catch (NullPointerException e) {
			log.error("Property is missing {}", this.customParameter, e);
			throw e;
		}
		
		
	}

	@Override
	public void initConditionWithPermanentDatastructures() {
		// TODO Auto-generated method stub
		
	}

}
