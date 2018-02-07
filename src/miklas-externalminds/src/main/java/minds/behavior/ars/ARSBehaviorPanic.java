package minds.behavior.ars;

import java.util.ArrayList;

import entity.mind.ExternalPerceptionInterface;
import minds.behavior.Action;
import minds.behavior.SequenceBehaviorImpl;

public class ARSBehaviorPanic extends SequenceBehaviorImpl {

	@Override
	protected void setBehavior() {
		actionsForStep.add(Action.MOVE_BACKWARD.toString());
		actionsForStep.add(Action.TURN_LEFT.toString());
		actionsForStep.add(Action.TURN_LEFT.toString());
		actionsForStep.add(Action.TURN_LEFT.toString());
		actionsForStep.add(Action.TURN_LEFT.toString());
		actionsForStep.add(Action.MOVE_FORWARD.toString());
		actionsForStep.add(Action.MOVE_FORWARD.toString());
	}

	@Override
	public String getName() {
		return "PANIC";
	}

}
