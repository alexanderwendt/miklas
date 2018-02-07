package event;

public interface EventInterface {
	public boolean runEvent();
	public void runEventExit();
	public String getEventName();
	public void setEventHandler(EventHandlerInterface poEventHandler);
	public void initPermanentDataStructures();
}
