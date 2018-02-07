package event;

import java.util.ArrayList;

public class EventHistory {
	private final ArrayList<String> eventHistory = new ArrayList<String>();
	
	public synchronized void addEvent(String eventName) {
		this.eventHistory.add(eventName);
	}
	
	public synchronized boolean contains(String eventName) {
		boolean result = this.eventHistory.contains(eventName);
		
		return result;
	}
}
