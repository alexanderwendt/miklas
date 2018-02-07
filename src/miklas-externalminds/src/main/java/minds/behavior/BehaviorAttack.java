package minds.behavior;

import java.util.ArrayList;

import entity.mind.ExternalPerceptionInterface;

public class BehaviorAttack extends SequenceBehaviorImpl {

	@Override
	protected void setBehavior() {
		this.actionsForStep.add("ATTACK");
		
	}

	@Override
	public String getName() {
		return "ATTACK";
	}

}
