package minds.behavior;

import java.util.ArrayList;

import utils.PerceptionUtils;
import entity.mind.ExternalPerceptionInterface;

public abstract class SequenceBehaviorImpl implements SequenceBehavior {
	protected ArrayList<String> actionsForStep = new ArrayList<String>();
	private int currentStep=0;
	private boolean isFinished = true;
	
	@Override
	public String getNextAction() {
		String action = "NONE";
		if (this.currentStep<this.actionsForStep.size()) {
			String proposedAction = this.actionsForStep.get(currentStep);
			//boolean isInterrupted = evaluatePerception(proposedAction, perception);
			//if (isInterrupted==true) {
			//	init();
			//} else {
			action = proposedAction;
			this.incrementBehavior();
			//}
		} else {
			init();
		}
		
		return action;
	}

	private void incrementBehavior() {
		this.currentStep++;
	}

	@Override
	public void init() {
		this.currentStep=0;
		actionsForStep.clear();
		this.setBehavior();
		this.setFinished(true);
	}
	
	private void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public boolean isFinished() {
		return this.isFinished;
	}
	
	public abstract String getName();
	
	protected abstract void setBehavior();
}
