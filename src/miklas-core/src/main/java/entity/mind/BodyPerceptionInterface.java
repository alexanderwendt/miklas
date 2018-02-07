package entity.mind;

/**
 * This is the interface from a data structure, which contains the body perception/drive source
 * 
 * @author wendt
 *
 */
public interface BodyPerceptionInterface {
	/**
	 * Get max health of the agent (normally 100)
	 * 
	 * @return
	 */
	public int getMaxHealth();
	/**
	 * Get current health of the agent
	 * 
	 * @return
	 */
	public int getCurrentHealth();
	/**
	 * Get a value of the pain (if negative) or pleasure (if positive), which is defined as 
	 * current health - current previous health
	 * 
	 * @return
	 */
	public int getPainOrPleasure();
	
	
}
