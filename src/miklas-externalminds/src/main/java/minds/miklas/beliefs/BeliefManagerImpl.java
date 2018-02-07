package minds.miklas.beliefs;

import java.util.ArrayList;

import entity.mind.ExternalPerceptionInterface;

public class BeliefManagerImpl implements BeliefManager {
	private final ArrayList<Belief> availableBeliefs = new ArrayList<Belief>();
	private final ArrayList<Belief> applicableBeliefs = new ArrayList<Belief>();
	
	@Override
	public void registerBelief(Belief belief) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void testApplicableBeliefs(
			ArrayList<ExternalPerceptionInterface> perception) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<String> getApplicableBeliefs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
