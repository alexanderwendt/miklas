package evaluator;

/**
 * In this structure, only read-only values of the score can be read.
 * 
 * @author wendt
 *
 */
public interface EvaluatorMindInterface {
	public int getScore();
	public int getPositiveActions();
	public int getNegativeActions();
	public int getNeutralActions();
	
}
