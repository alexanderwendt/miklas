package minds.behavior;

public class DynamicBehaviorGoto {

	public String getAction(int targetLocationX, int targetLocationY, int executeLocationY) {
		String action = "NONE";
		
		if (Math.abs(targetLocationY)>Math.abs(targetLocationX) || targetLocationX==0) {
			if (targetLocationY>executeLocationY) {
				action = Action.MOVE_FORWARD.toString();
			}
		} else if (targetLocationX<0) {
			action = Action.TURN_RIGHT.toString();
		} else if (targetLocationX>0) {
			action = Action.TURN_LEFT.toString();
		}
		
		return action;
	}
	
	public String getActionToExecuteOnEntity(int targetLocationX, int targetLocationY, Action actionToExecute, int executeLocationY) {
		String action = "NONE";
		
		int executeLocationX=0;
		executeLocationX=0;

		if (targetLocationX==executeLocationX && targetLocationY==executeLocationY) {
			action = actionToExecute.toString();
		} else {
			action = getAction(targetLocationX, targetLocationY, executeLocationY);
		}
		
		return action;
	}
}
