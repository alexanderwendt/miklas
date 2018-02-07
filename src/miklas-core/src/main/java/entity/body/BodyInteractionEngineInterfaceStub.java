package entity.body;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import entity.EntityInterface;

public class BodyInteractionEngineInterfaceStub implements BodyInteractionEngineInterface {

	@Override
	public void init(Body body) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getMyLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getNeighborLocationOfMyDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMyDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMyDirection(double direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMyLocation(Location newLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<EntityInterface> getEntityAtLocation(Location poLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityInterface getTopEntity(ArrayList<EntityInterface> oEntityList) {
		// TODO Auto-generated method stub
		return null;
	}

}
