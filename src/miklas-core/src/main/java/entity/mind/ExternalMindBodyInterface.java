package entity.mind;

import java.util.ArrayList;

/**
 * This interface is used by the external agent to interact with the game
 * 
 * @author wendt
 *
 */
public interface ExternalMindBodyInterface {
	/**
	 * Set a string action. Only certain actions can be executed in the simulator. Therefore the actions have to be 
	 * configurable
	 * 
	 * @param action
	 */
	public void setAction(String action);
	/**
	 * Get all perceived objects
	 * 
	 * @return
	 */
	public ArrayList<ExternalPerceptionInterface> getExternalPerception();

	/**
	 * Get all body internals
	 * 
	 * @return
	 */
	public BodyPerceptionInterface getBodyPerception();
	public int getLastReward(boolean reset) throws Exception;
	
}
