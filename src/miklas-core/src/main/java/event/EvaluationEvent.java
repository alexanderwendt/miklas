package event;

public abstract class EvaluationEvent extends Event {

	public EvaluationEvent(String poEventName) {
		super(poEventName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTargetIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initEventWithPermanentStructures() {
		// TODO Auto-generated method stub
		
	}


}

