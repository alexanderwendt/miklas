package entity.mind;

/**
 * This interface is implemented by an agent, in order to trigger a cycle start
 * 
 * @author wendt
 *
 */
public interface ExternalMindControlInterface {
	/**
	 * Start the agent cycle. This method shall be implemented in the agent mediator
	 */
	public void startCycle();
	public void killMind();
}
