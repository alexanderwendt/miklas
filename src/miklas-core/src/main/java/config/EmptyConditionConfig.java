package config;

import java.util.ArrayList;
import java.util.HashMap;

public class EmptyConditionConfig {
	
	private final String name;
	private final String className;
	private final HashMap<String, ArrayList<String>> customParameterMap;
	
	public EmptyConditionConfig(String name, String className, HashMap<String, ArrayList<String>> parameterMap) {
		this.name = name;
		this.className = className;
		this.customParameterMap = parameterMap;
		
	}

	public String getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}
	
	public HashMap<String, ArrayList<String>> getCustomParameterMap() {
		return customParameterMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmptyConditionConfig [name=");
		builder.append(name);
		builder.append(", className=");
		builder.append(className);
		builder.append("]");
		return builder.toString();
	}
	

}
