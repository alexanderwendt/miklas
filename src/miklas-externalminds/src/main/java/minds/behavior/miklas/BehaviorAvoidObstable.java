package minds.behavior.miklas;

import minds.behavior.Action;
import minds.behavior.SequenceBehaviorImpl;

public class BehaviorAvoidObstable extends SequenceBehaviorImpl {

	@Override
	public String getName() {
		return "AVOID_OBSTABLE";
	}

	@Override
	protected void setBehavior() {
		//Create random action
		String[] actions = {"TURN_LEFT", "TURN_RIGHT"};
		String action = actions[(int) (actions.length*Math.random())];
		actionsForStep.add(action);
		actionsForStep.add(Action.MOVE_FORWARD.toString());
		
	}

}
