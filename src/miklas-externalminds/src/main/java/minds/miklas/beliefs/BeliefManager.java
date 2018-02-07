package minds.miklas.beliefs;

import java.util.ArrayList;

import entity.mind.ExternalPerceptionInterface;

public interface BeliefManager {
	public void registerBelief(Belief belief);
	public void testApplicableBeliefs(ArrayList<ExternalPerceptionInterface> perception);
	public ArrayList<String> getApplicableBeliefs();
}
