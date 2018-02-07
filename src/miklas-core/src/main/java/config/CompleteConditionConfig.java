package config;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteConditionConfig {
	private final String name;
	private final String className;
	
	private final HashMap<String, ArrayList<String>> parameterMap;
	
	public CompleteConditionConfig(EmptyConditionConfig conditionConfig, HashMap<String, ArrayList<String>> conditionMap) {
		this.name = conditionConfig.getName();
		this.className = conditionConfig.getClassName();
		//Set all custom properties bound to the event
		this.parameterMap = conditionMap;
		//Add custom conditions bound to the condition itself
		this.parameterMap.putAll(conditionConfig.getCustomParameterMap());
	}

	public HashMap<String, ArrayList<String>> getConditionMap() {
		return parameterMap;
	}
	
	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompleteConditionConfig [name=");
		builder.append(name);
		builder.append(", className=");
		builder.append(className);
		builder.append(", parameterMap=");
		builder.append(parameterMap);
		builder.append("]");
		return builder.toString();
	}

	
}
