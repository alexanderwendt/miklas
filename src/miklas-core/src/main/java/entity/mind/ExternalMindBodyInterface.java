package entity.mind;

import java.util.ArrayList;

import evaluator.EvaluatorManagerMindInterface;
import evaluator.EvaluatorMindInterface;

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
	
	
	/**
	 * Get the last reward
	 * 
	 * @param reset
	 * @return
	 * @throws Exception
	 */
	public int getLastReward(boolean reset) throws Exception;
	
	public String getActorId();
	
	public EvaluatorMindInterface getEvalution();
	
}
