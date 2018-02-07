package entity.mind;

import java.util.ArrayList;

import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public class ExternalMind extends AnimateMind implements ExternalMindBodyInterface {

	private ExternalMindControlInterface externalMind;
	
	public ExternalMind(BodyMindInterface body, EvaluatorManagerMindInterface score) {
		super(body, score);
		
	}
	
	public void setExternalMind(ExternalMindControlInterface externalMind) {
		this.externalMind = externalMind;
	}
	
	@Override
	public void setAction(String action) {
		super.setAction(action);
	}

	@Override
	public ArrayList<ExternalPerceptionInterface> getExternalPerception() {
		return super.getExternalPerception();
	}

	@Override
	public BodyPerceptionInterface getBodyPerception() {
		return super.getBodyPerception();
	}

	@Override
	protected void executeMindCycle() {
		this.externalMind.startCycle();
	}

	@Override
	public void killMind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLastReward(boolean reset) throws Exception {
		return super.getLastReward(reset);
	}

}
