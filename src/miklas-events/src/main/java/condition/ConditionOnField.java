package condition;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jgamegrid.Location;
import entity.EntityInterface;

/**
 * The condition is true if the calling body is on the same field as target body
 * 
 * @author wendt
 *
 */
public class ConditionOnField extends Condition {
	
	private final String COMPAREDATASTRUCTURE = "bodytype";
	
	private String compareDataStructureIdentifier;

	public ConditionOnField(String name, HashMap<String, ArrayList<String>> parameters) {
		super(name, parameters);
	}

	@Override
	public boolean testCondition() {
		boolean result = false;
		
		//Get entity
		try {
			Location myLocation = this.assignedEvent.getBodyInteractionInformation().getMyLocation();
			ArrayList<EntityInterface> entitiesAtMyLocation = this.assignedEvent.getBodyInteractionInformation().getEntityAtLocation(myLocation);
			for (EntityInterface e: entitiesAtMyLocation) {
				if (compareDataStructureIdentifier.equals(e.getBodyType())) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error: Cannot compare body types on my location", e);
		}
		
		return result;
	}

	@Override
	protected void setCustomProperties() {
		try {
			//Get the comparestring from the data structures
			compareDataStructureIdentifier = this.customParameter.getSingleParameter(COMPAREDATASTRUCTURE);
		} catch (NullPointerException e) {
			log.error("Property is missing {}", this.customParameter, e);
			throw e;
		}
		
	}

	@Override
	public void initConditionWithPermanentDatastructures() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
