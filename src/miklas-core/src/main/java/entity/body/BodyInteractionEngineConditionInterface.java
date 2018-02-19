package entity.body;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import entity.EntityInterface;

/**
 * @author wendt
 * 
 * This interface only gets the information from the body, like vision, direction.
 *
 */
public interface BodyInteractionEngineConditionInterface {
	public Location getMyLocation();
	public Location getNeighborLocationOfMyDirection();
	public double getMyDirection();
	public ArrayList<EntityInterface> getEntityAtLocation(Location poLocation);
	public EntityInterface getTopEntity(ArrayList<EntityInterface> oEntityList);
}
