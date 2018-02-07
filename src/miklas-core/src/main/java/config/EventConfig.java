package config;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * #Events
	event.0.name=BUMPOBSTACLE
	event.0.classname=EventMoveOnObstacle.java
	event.0.triggerbodytypes=HUMANPLAYER, AIPLAYER
	event.0.triggeractionsexecuted=MOVE_FORWARD
	event.0.scorechange=-10
 * 
 * @author wendt
 *
 */
public class EventConfig {
	
	private final String eventName;
	private final String className;
	private final ArrayList<CompleteConditionConfig> completeConditionConfigs;
	private final HashMap<String, ArrayList<String>> parameterMap;
	private final int scoreChange;
	private final boolean permanentGraphicChange;
	
	public EventConfig(String eventName, String className, HashMap<String, HashMap<String, ArrayList<String>>> conditionMap, HashMap<String, EmptyConditionConfig> availableConditions, int scoreChange, boolean permanentGraphicChange, HashMap<String, ArrayList<String>> parameters) {
		super();
		this.eventName = eventName;
		this.className = className;
		//this.triggerBodyTypes = triggerBodyTypes;
		//this.triggerActionsExecuted = triggerActionsExecuted;
		this.scoreChange = scoreChange;
		//this.conditionMap = conditionMap;
		
		//Create completeconditionconfigs
		completeConditionConfigs = assignCompleteConditionConfig(availableConditions, conditionMap);
		this.permanentGraphicChange = permanentGraphicChange;
		
		this.parameterMap = parameters;
	}

	public String getEventName() {
		return eventName;
	}

	public String getClassName() {
		return className;
	}

	public int getScoreChange() {
		return scoreChange;
	}
	
	public void setConditionVariablesForEvent(String eventString) {
		
	}
	
	public ArrayList<CompleteConditionConfig> assignCompleteConditionConfig(HashMap<String, EmptyConditionConfig> availableConditions, HashMap<String, HashMap<String, ArrayList<String>>> conditionMap) {
		ArrayList<CompleteConditionConfig> result = new ArrayList<CompleteConditionConfig>(); 
		
		for (String conditionName : conditionMap.keySet()) {
			try {
				CompleteConditionConfig completeConditionConfig = new CompleteConditionConfig(availableConditions.get(conditionName), conditionMap.get(conditionName));
				result.add(completeConditionConfig);
			} catch (NullPointerException e) {
				throw new NullPointerException("Condition not found for condition name. " + conditionName + ". " + e.getMessage());
			}
			
			
		}
		
		return result;
	}

	@Override
	public String toString() {
		return "eventName=" + eventName + ", className=" + className + ", Conditions=" + completeConditionConfigs + "]";
	}

	public ArrayList<CompleteConditionConfig> getCompleteConditionConfigs() {
		return completeConditionConfigs;
	}

	public HashMap<String, ArrayList<String>> getParameterMap() {
		return parameterMap;
	}

	public boolean isPermanentGraphicChange() {
		return permanentGraphicChange;
	}

}
