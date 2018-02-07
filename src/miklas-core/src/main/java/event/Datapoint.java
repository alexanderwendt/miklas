package event;

public class Datapoint<T> {
	
	private final String name;
	private T value;
	
	public Datapoint(String name) {
		this.name = name;
		value = null;
	}
	public Datapoint(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Datapoint [name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
	
}
