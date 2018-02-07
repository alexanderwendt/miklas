package evaluator;

import java.util.ArrayList;

import entity.mind.BodyPerceptionInterface;
import entity.mind.ExternalPerceptionInterface;

public interface EvaluatorManagerMindInterface {
	public void updateScoreFromEvent(String poEntityName, String poEventName);	//The score management is done in the score manager
	public void updateHealth(String poEntityName, int newHealth);
	/**
	 * Put this function at the last of the decision cycle
	 * 
	 * @param perceptionValues
	 * @param bodyValues
	 */
	public void updateEvaluation(String poEntityName, ArrayList<ExternalPerceptionInterface> perceptionValues, BodyPerceptionInterface bodyValues, String currentAction);
	public void registerEntity(String poEntityName, int health) throws Exception;
	public void registerEvent(String poEventName, int pnScoreChange) throws Exception;
	public int getLastReward(String poEntityName, boolean reset) throws Exception;
}
