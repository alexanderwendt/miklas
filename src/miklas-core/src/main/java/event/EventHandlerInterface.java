package event;

public interface EventHandlerInterface {
	/**
	 * Executes all events in the eventhandler, which matches some conditions
	 * 
	 * @throws Exception
	 */
	public void executeMatchingEvents() throws Exception;
	/**
	 * Add new event to event handler
	 * 
	 * @param poEvent
	 */
	public void registerEvent(EventInterface poEvent);
	/**
	 * Play event sound
	 * 
	 * @param poEntityName
	 * @param poEventName
	 */
	public void playSound(String poEntityName, String poEventName);
	/**
	 * Set event graphic
	 * 
	 * @param poEntityName
	 * @param poEventName
	 */
	public void setGraphic(String poEntityName, String poEventName);
	/**
	 * Update score based on events
	 * 
	 * @param poEntityName
	 * @param poEventName
	 */
	public void updateScore(String poEntityName, String poEventName);

	/**
	 * The registered data structures are available for manipulation by the events
	 * 
	 * @param id
	 * @param dataStructure
	 * @throws Exception 
	 */
	public void setLocalPermanentDataStructure(Datapoint<?> dataStructure) throws Exception;
	
	/**
	 * Set a temporary data structure
	 * 
	 * @param dataStructure
	 * @throws Exception
	 */
	public void setLocalTemporaryDataStructure(Datapoint<?> dataStructure) throws Exception;
	
	/**
	 * Get a data structure (permanent and temporary) from the container
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public <T extends Object> boolean getLocalDataStructureFromContainer(Datapoint<T> type) throws Exception;
	
	/**
	 * For each registered event, init all permanent data structures, which are used for identification
	 */
	public void initLocalPermanentDataStructuresInEvents();
	
	public void init(boolean setContinousOperation, boolean setExcludeAlreadyExecutedEvents);
	
	
}
