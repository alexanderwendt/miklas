package config;

public class MindConfig {

	private final String mindName;
	private final String mindClass;
	private final String mindType;
	
	public MindConfig(String mindName, String mindType, String mindClass) {
		this.mindName = mindName;
		this.mindClass = mindClass;
		this.mindType = mindType;
	}

	public String getMindName() {
		return mindName;
	}

	public String getMindClass() {
		return mindClass;
	}

	public String getMindType() {
		return mindType;
	}
	
	@Override
	public String toString() {
		return "mindName=" + mindName + ", mindClass=" + mindClass + ", mindType=" + mindType;
	}
	
}
