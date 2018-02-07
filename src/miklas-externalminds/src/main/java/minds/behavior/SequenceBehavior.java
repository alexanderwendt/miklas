package minds.behavior;

public interface SequenceBehavior {
	public String getNextAction();
	public void init();
	public String getName();
	public boolean isFinished();
}
