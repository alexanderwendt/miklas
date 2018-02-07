package condition;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import event.EventConditionInterface;

public abstract class Condition implements ConditionInterface {
	
	private final String conditionName;
	
	protected static final Logger log = LoggerFactory.getLogger(Condition.class);
	
	protected CustomParameter customParameter;
	protected EventConditionInterface assignedEvent;

	public Condition(String name, HashMap<String, ArrayList<String>> parameters) {
		this.conditionName = name;
		//Set custom properties
		//Load custom settings from event
		//event.0.condition.TEST.eventname=0
		this.customParameter = new CustomParameter(parameters);
				
		//Assign the properties to the variables
		this.setCustomProperties();
		
		log.debug("Init Condition {}, parameters={}", this.conditionName, this.customParameter);
	}
	
	public void init(EventConditionInterface event) {
		this.assignedEvent = event;
	}
	
	@Override
	public abstract boolean testCondition();
	
	protected abstract void setCustomProperties();
	
	public abstract void initConditionWithPermanentDatastructures() throws Exception;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[conditionName=");
		builder.append(conditionName);
		builder.append(", customParameter=");
		builder.append(customParameter);
		builder.append("]");
		return builder.toString();
	}
	
}
