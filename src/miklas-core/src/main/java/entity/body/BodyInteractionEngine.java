package entity.body;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import condition.Condition;
import entity.Entity;
import entity.EntityInterface;

public class BodyInteractionEngine implements BodyInteractionEngineInterface {
	
	protected static final Logger log = LoggerFactory.getLogger(BodyInteractionEngine.class);
	
	private Body myBody;

	public BodyInteractionEngine() {
		//Nothing
	}
	
	@Override
	public void init(Body body) {
		this.myBody = body;
		
	}
	
	/**
	 * Get all actors except this one for one location in the grid
	 * 
	 * @param poLocation
	 * @return
	 */
	public ArrayList<EntityInterface> getEntityAtLocation(Location poLocation) {
		
		ArrayList<EntityInterface> oResult = new ArrayList<EntityInterface>();
		
		//log.trace("{}> Try to get actors at location {}", this.getEntityName(), poLocation);
		ArrayList<Actor> oList = this.myBody.getOwnerEntity().getGameGrid().getActorsAt(poLocation);
		//log.trace("{}> Gotten actors", this.getEntityName());
		for (Actor oActor : oList) {
			if (oActor.equals(this)==false && oActor instanceof EntityInterface) {
				oResult.add((Entity) oActor);
			}
		}
		
		return oResult;
	}
	
	public EntityInterface getTopEntity(ArrayList<EntityInterface> oEntityList) {
		EntityInterface result = null;
		
		int highestLayer=0;
		//Get initial entity at layer
		if (oEntityList.isEmpty()==false) {
			result = oEntityList.get(0);
			highestLayer = result.getEntityLayer();
			
		}
		
		//Check if that is really correct
		for (EntityInterface oEntity : oEntityList) {
			if (oEntity.equals(result)==false && oEntity.equals(this.myBody.getOwnerEntity())==false && oEntity.getEntityLayer()>=highestLayer) {
				result = oEntity;
				highestLayer = result.getEntityLayer();
			}
		}
		
		return result;
	}

	@Override
	public Location getMyLocation() {
		Location currentLocation = this.myBody.getOwnerEntity().getLocation();
		return currentLocation;
	}

	@Override
	public Location getNeighborLocationOfMyDirection() {
		double rCurrentDirection = this.getMyDirection();
		Location next = this.myBody.getOwnerEntity().getLocation().getNeighbourLocation(rCurrentDirection+90);
		return next;
	}

	@Override
	public double getMyDirection() {
		double rCurrentDirection = this.myBody.getOwnerEntity().getDirection();
		return rCurrentDirection;
	}

	@Override
	public void setMyDirection(double direction) {
		//Check if an action is executable and if a value is set, the value is deleted as it is consumed
		if (this.myBody.isActionExecutable(true)) {
			this.myBody.getOwnerEntity().setDirection(direction);					//Handlung
		} else {
			log.debug("Action cannot be executed");
		}
		
	}

	@Override
	public void setMyLocation(Location newLocation) {
		//Check if an action is executable and if a value is set, the value is deleted as it is consumed
		if (this.myBody.isActionExecutable(true)) {
        	this.myBody.getOwnerEntity().setLocation(newLocation);	//Execute move command
		}
	}

}
