package entity.body;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import entity.EntityInterface;

public interface BodyInteractionEngineInterface {
	public void init(Body body);
	public Location getMyLocation();
	public Location getNeighborLocationOfMyDirection();
	public double getMyDirection();
	public void setMyDirection(double direction);
	public void setMyLocation(Location newLocation);
	public ArrayList<EntityInterface> getEntityAtLocation(Location poLocation);
	public EntityInterface getTopEntity(ArrayList<EntityInterface> oEntityList);
}
