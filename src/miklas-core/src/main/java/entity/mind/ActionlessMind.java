package entity.mind;

import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public class ActionlessMind extends AnimateMind {

	public ActionlessMind(BodyMindInterface body, EvaluatorManagerMindInterface score) {
		super(body, score);
	}

	@Override
	protected void executeMindCycle() {
		// Do nothing
		//log.debug("Do nothing");
		
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

}
