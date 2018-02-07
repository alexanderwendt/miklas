package minds.behavior;

import java.util.ArrayList;

import entity.mind.ExternalPerceptionInterface;

public class BehaviorEat extends SequenceBehaviorImpl {

	@Override
	protected void setBehavior() {
		this.actionsForStep.add("EAT");
		
	}

	@Override
	public String getName() {
		return "EAT";
	}

}
