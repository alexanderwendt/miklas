package evaluator;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Location;
import entity.mind.BodyPerception;
import entity.mind.ExternalPerception;

public class EntityEvaluation {
	
	private final String agentIdentifier;
	private final Logger log = LoggerFactory.getLogger(EntityEvaluation.class);
	
	// current values
	private int health;
	private int score=0;
	private int positiveActions=0;
	private int negativeActions=0;
	private int neutralActions=0;
	private int lastReward = 0;
	
	private boolean isScoreUpdateSet = false;
	
	private Location curLocation = null;
	private ArrayList<ExternalPerception> previousPerceptionValues = new ArrayList<ExternalPerception>();
	private BodyPerception bodyValues = null;
	
	public EntityEvaluation(String agentIdentifier, int health) {
		this.agentIdentifier = agentIdentifier;
		this.health=health;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int timestamp, int health) {
		this.health = health;
		appendLogEntry(timestamp);
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPositiveActions() {
		return positiveActions;
	}
	public void setPositiveActions(int positiveActions) {
		this.positiveActions = positiveActions;
	}
	public int getNegativeActions() {
		return negativeActions;
	}
	public void setNegativeActions(int negativeActions) {
		this.negativeActions = negativeActions;
	}
	public int getNeutralActions() {
		return neutralActions;
	}
	public void setNeutralActions(int neutralActions) {
		this.neutralActions = neutralActions;
	}
	
	public void setLocation(Location l) {
		curLocation = l;
	}
	
	public Location getLocation() {
		return curLocation;
	}

	public String getEntityIdentifier() {
		return agentIdentifier;
	}
	
	public void updateScore(int timestamp, int pnAdditionalScore) {
		int nScore = this.getScore();
		int nNewScore = nScore + pnAdditionalScore;
		this.setScore(nNewScore);
		
		lastReward += pnAdditionalScore;
		
		updateActions(pnAdditionalScore);
		
		appendLogEntry(timestamp);
	}
	
	private void updateActions(int scoreChange) {
		if (scoreChange>0) {
			this.setPositiveActions(this.getPositiveActions() + 1);
			isScoreUpdateSet = true;
		} else if (scoreChange<0) {
			this.setNegativeActions(this.getNegativeActions() + 1);
			isScoreUpdateSet = true;
		}
	}
	
	public void updateActions(ArrayList<ExternalPerception> currentPerceptionValues, BodyPerception bodyValues, String currentAction) {
		//Put an eventhandler here for evaluation
		
		
	}
	
	public void updateNeutralActions() {
		if (isScoreUpdateSet==false) {
			this.setNeutralActions(this.getNeutralActions() + 1);
		}
		
		isScoreUpdateSet=false;
			
	}
	
	private void appendLogEntry(int timestamp) {
		if(curLocation != null)
			log.debug(agentIdentifier + ">" + timestamp + ", " + health + ", " + score + ", " +
					positiveActions + ", " + negativeActions + ", " + neutralActions +
					", " + curLocation.getX() + ", " + curLocation.getY());
	}
	
	/**
	 * 
	 * @param reset
	 * @return
	 */
	public int getLastReward(boolean reset) {
		int ret = lastReward;
		if(reset) lastReward = 0;
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("agentName=").append(agentIdentifier)
				.append(", health=").append(health).append(", score=")
				.append(score).append(", positiveActions=")
				.append(positiveActions).append(", negativeActions=")
				.append(negativeActions).append(", neutralActions=")
				.append(neutralActions);
		return builder.toString();
	}
	
}
