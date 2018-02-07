package entity.body;

import java.util.List;

import utils.LocationUtilities;
import ch.aplu.jgamegrid.Location;
import entity.Entity;
import event.DataStructureContainer;
import event.Datapoint;

public interface BodyInterface extends BodyMindInterface {
	/**
	 * Execute Action on this body from the caller
	 * 
	 * @param Caller
	 * @param poAction
	 */
	public void executeEffectOfExternActionOnMyBody(Entity Caller, String poAction);
	
	/**
	 * Execute the reaction of the body of the caller. Therefore, the recaller is the original caller
	 * 
	 * @param ReCaller
	 * @param poAction
	 */
	public void executeEffectOfExternReactionMyOnBody(Entity ReCaller, String poAction);
	
	public boolean executeActionOnEntityOnField(Location poTargetLocation, String poAction, boolean pbExecuteOnlyOnTopEntity);
	

	
	//public void executeAction(String action) throws Exception;
	
	/**
	 * This is the stepping of the body in a turn
	 */
	public void callBodyToAct();
	
	public DataStructureContainer getTemporarySharedVariables();
	
	public DataStructureContainer getPermanentSharedVariables();
	
	public <T extends Object> boolean getSharedVariable(Datapoint<T> value) throws Exception;
	
	/**
	 * Get current health
	 * 
	 * @return
	 */
	//public int getHealth();
	
	/**
	 * Set current health
	 * 
	 * @param pnNewHealth
	 */
	//public void setHealth(int pnNewHealth);
	
	//public boolean isActionExecutable();
	
	public void killActor();
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Entity getOwnerEntity();
	
	public BodyInteractionEngineInterface getBodyInteractionEngine();
	
	/**
	 * Set if action can be executed
	 * 
	 * @param mbActionExecutable
	 */
	public void setActionExecutable(boolean mbActionExecutable);
	
	public String getBodyType();
	
	public List<String> getParentBodyType();
	
	public boolean isBodyAnimate();
	
	//public int getMaxHealth();
	
	public void init() throws Exception;
	
	//public ArrayList<ExternalPerceptionInterface> getPerception();
	
	public LocationUtilities getLocationUtils();
	
}
