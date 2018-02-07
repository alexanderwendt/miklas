package entity.mind;

import java.util.ArrayList;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public abstract class AnimateMind implements AnimateMindInterface {

	private final BodyMindInterface body;
	private final EvaluatorManagerMindInterface score;
	
	private String currentAction;
	
	protected static final Logger log = LoggerFactory.getLogger(AnimateMind.class);
	
	public AnimateMind(BodyMindInterface body, EvaluatorManagerMindInterface score) {
		this.body = body;
		this.score = score;
	}
	
	@Override
	public void startCycle() {
		setup();
		this.executeMindCycle();
		endCycle();

	}
	
	private void setup() {

	}
	
	private void endCycle() {
		//Update score and evaluation
		//Get health
		this.score.updateHealth(this.body.getEntityIdentifier(), this.body.getBodyPerception().getCurrentHealth());
		
		//TEST pain
		BodyPerceptionInterface bodyInternal = this.body.getBodyPerception();
		if (bodyInternal.getPainOrPleasure()<0) {
			log.debug("Pain is felt {}", bodyInternal.getPainOrPleasure());
		} else {
			log.debug("Pleasure is felt {}", bodyInternal.getPainOrPleasure());
		}
		
		score.updateEvaluation(this.body.getEntityIdentifier(), this.body.getPerception(), this.body.getBodyPerception(), this.body.getCurrentAction());
		//Get all fields of perception
		//String perception = "";
		
	}
	
	protected ArrayList<ExternalPerceptionInterface> getExternalPerception() {
		return this.body.getPerception();
	}
	
	protected BodyPerceptionInterface getBodyPerception() {
		return this.body.getBodyPerception();
	}
	
	protected void setAction(String action) {
		try {
			currentAction = action;
			this.body.executeAction(action);
		} catch (Exception e) {
			log.error("Cannot set action {}. The simulator does not allow it", action);
		}
	}
	
	protected abstract void executeMindCycle();
	
	protected int getLastReward(boolean reset) throws Exception {
		return score.getLastReward(body.getEntityIdentifier(), reset);
	}

}
