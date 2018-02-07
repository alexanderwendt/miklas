package utils;

import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;
import entity.Entity;
import entity.EntityInterface;
import entity.body.Body;
import entity.body.BodyInteractionEngineInterface;
import entity.mind.ExternalPerception;
import entity.mind.ExternalPerceptionInterface;

public class LocationUtilities {
	
	private static final int RADIUS = 3;
	
	private final BodyInteractionEngineInterface bodyInteractionEngine;
	
	public LocationUtilities(BodyInteractionEngineInterface bodyInteractionEngine) {
		this.bodyInteractionEngine = bodyInteractionEngine;
	}
	
	private ArrayList<Location> getRelativeLocations(int offset, int radius, boolean halfRadius) {
		ArrayList<Location> result = new ArrayList<Location>();
		
		int startY = -radius;
		if (halfRadius==true) {
			startY=0;
		}
		
		//My location = 0,0
		for (int x=-radius; x<=radius; x++) {
			for (int y=startY; y<=radius; y++) {	//Start with 0 as the agent cannot look back
				if (Math.sqrt(Math.pow(x,2) + Math.pow(y,2))>=offset) { //&& (Math.abs(x)>offset || Math.abs(y)>offset)) {
					result.add(new Location(x, y));
				}
			}
		}		
		
		return result;
	}

	/**
	 * Rotation matrix for an angle
	 * 
	 * @param direction
	 * @return
	 */
	private double[][] getRotationMatrix(double direction) {
		double[][] transformationArray = new double[2][2];
		transformationArray[0][0] = Math.cos(direction/360*2*Math.PI);
		transformationArray[0][1] = -Math.sin(direction/360*2*Math.PI);
		transformationArray[1][0] = Math.sin(direction/360*2*Math.PI);
		transformationArray[1][1] = Math.cos(direction/360*2*Math.PI);
		
		return transformationArray;
	}

	/**
	 * Inverse Rotation matrix for an angle. Coordinate system is with -y and x 2D graphics. Clockwhise 
	 * 
	 * @param direction
	 * @return
	 */
	private double[][] getInverseRotationMatrix(double direction) {
		double[][] transformationArray = new double[2][2];
		transformationArray[0][0] = Math.cos(direction/360*2*Math.PI);
		transformationArray[0][1] = Math.sin(direction/360*2*Math.PI);
		transformationArray[1][0] = -Math.sin(direction/360*2*Math.PI);
		transformationArray[1][1] = Math.cos(direction/360*2*Math.PI);
		
		return transformationArray;
	}

//	/**
//	 * Add offset to relative locations, in order to transform them into absolute coordinates
//	 * 
//	 * @param locations
//	 * @param myLocation
//	 * @return
//	 */
//	private ArrayList<Location> addOffsetToRelativeLocations(ArrayList<Location> locations, Location myLocation) {
//		ArrayList<Location> result = new ArrayList<Location>();
//		
//		for (Location loc : locations) {
//			result.add(new Location(loc.x + myLocation.x, loc.y + myLocation.y));
//		}
//		
//		return result;
//	}

//	/**
//	 * Transform absolute coordinates into relative locations
//	 * 
//	 * @param locations
//	 * @param myLocation
//	 * @return
//	 */
//	private ArrayList<Location> removeOffsetFromAbsoluteLocations(ArrayList<Location> locations, Location myLocation) {
//		ArrayList<Location> result = new ArrayList<Location>();
//		
//		for (Location loc : locations) {
//			result.add(new Location(loc.x - myLocation.x, loc.y - myLocation.y));
//		}
//		
//		return result;
//	}
	
	/**
	 * For a relative location where direction has not been considered, rotate position, in order to get into another reference system
	 * If inverseRotationMatrix is false, then turn from absolute to relative coordinate system.
	 * If inverseRotationMatrix is true, then turn back from relative coordinate system to absolute coordinate system
	 * 
	 * @param location
	 * @param direction
	 * @param radius
	 * @return
	 */
	private Location getRelativeLocationForDirection(Location relativeLocation, double direction, int radius, boolean inverseRotationMatrix) {
		//x, y
		double[][] transformationArray;
		if (inverseRotationMatrix==true) {
			transformationArray = getInverseRotationMatrix(direction);
		} else {
			transformationArray = getRotationMatrix(direction);
		}
		
		double newXapprox = (transformationArray[0][0] * relativeLocation.x + transformationArray[0][1] * relativeLocation.y);
		double newYapprox = (transformationArray[1][0] * relativeLocation.x + transformationArray[1][1] * relativeLocation.y);
		
		double normedX=newXapprox;
		double normedY=newYapprox;
		
		if (radius>=0 && Math.abs(newXapprox)>0.01 && Math.abs(newYapprox)>0.01) {
			if (Math.abs(newXapprox)>Math.abs(newYapprox)) {
				normedX = newXapprox/Math.abs(newXapprox)*radius;
				normedY = newYapprox/Math.abs(newXapprox)*radius;
			} else if (newXapprox!=0) {
				normedX = newXapprox/Math.abs(newYapprox)*radius;
				normedY = newYapprox/Math.abs(newYapprox)*radius;
			}
		}
		
		int newX = (int) Math.round(normedX);
		int newY = (int) Math.round(normedY);
		
		//Correct for too big radius
		if (Math.abs(newX)>radius) {
			newX = radius*newX/Math.abs(newX);
		}
		if (Math.abs(newY)>radius) {
			newY = radius*newY/Math.abs(newY);
		}
		
		Location result = new Location(newX, newY);	
		
		return result;
	}
	
	/**
	 * Transform relative coordinates to absolute coordinates
	 * 
	 * @param myAbsoluteLocation
	 * @param myDirection
	 * @param relativeLocations
	 * @return
	 */
	private ArrayList<LocationCorrespondance> transformToAbsoluteCoordinates(Location myAbsoluteLocation, double myDirection, ArrayList<Location> relativeLocations, int visionRadius) {
		ArrayList<LocationCorrespondance> result = new ArrayList<LocationCorrespondance>();
		//Transform relative locations to absolute locations
		//Turn coordinate system back to origin direction
		
		//Problems with directions!!!!!!!
		//FIXME wo soll +90° hingefügt werden???
		double absoluteDirection = myDirection;
		
		for (Location loc : relativeLocations) {
			Location relativeLocationWithoutDirection = getRelativeLocationForDirection(loc, absoluteDirection, visionRadius, false);
			//Multiply x with -1
			//Location relativeLocationWithoutDirectionCorrected = new Location(relativeLocationWithoutDirection.x*(-1), relativeLocationWithoutDirection.y);
			
			//Add offset
			Location absoluteLocation = addLocationOffset(myAbsoluteLocation, relativeLocationWithoutDirection);
			
			//Create correspondance object
			LocationCorrespondance corr = new LocationCorrespondance(myAbsoluteLocation, absoluteDirection, absoluteLocation, loc);
			result.add(corr);
		}
		
		return result;
	}

	private Location addLocationOffset(Location myAbsoluteLocation, Location loc) {
		return new Location(loc.x + myAbsoluteLocation.x, loc.y + myAbsoluteLocation.y);
	}
	
	
	public Location getAbsoluteLocationForRelativeLocation(Location myAbsoluteLocation, double myDirection, Location relativeEntityLocation) {
		Location absoluteLocation = null;
		
		Location relativeLocationWithoutDirection;
		if (Math.abs(relativeEntityLocation.x)>Math.abs(relativeEntityLocation.y)) {
			relativeLocationWithoutDirection = getRelativeLocationForDirection(relativeEntityLocation, myDirection, Math.abs(relativeEntityLocation.x), false);
		} else {
			relativeLocationWithoutDirection = getRelativeLocationForDirection(relativeEntityLocation, myDirection, Math.abs(relativeEntityLocation.y), false);
		}
		
		//Add offset
		absoluteLocation = addLocationOffset(myAbsoluteLocation, relativeLocationWithoutDirection);	
		
		return absoluteLocation;

	}
	
	public Location getRelativeLocation(Entity myEntity, Entity externalEntity) {
		//Location result = null;
		
		Location myLocation = myEntity.getLocation();
		//Location must be turned with 90 degrees
		double myDirection = myEntity.getDirection();
		
		Location locationOfExternalEntity = externalEntity.getLocation();
		
		//Location relativeLocationOfExternalEntity = new Location(locationOfExternalEntity.x - myLocation.x, locationOfExternalEntity.y - myLocation.y);
		
		//Get correspondance matrix for my entity
		ArrayList<LocationCorrespondance> correspondance = getPerceivableRelativeLocationsAsAbsoluteLocations(myLocation, myDirection, RADIUS, false);	//Use whole radius
		Location result = null;
		for (LocationCorrespondance loc : correspondance) {
			if (locationOfExternalEntity.x == loc.getAbsoluteLocation().x && locationOfExternalEntity.y == loc.getAbsoluteLocation().y) {
				result = loc.getRelativeCorrespondingLocation();
			}
		}
		
		//Location result = getRelativeLocationsForDirection(relativeLocationOfExternalEntity, myDirection);
		
		return result;
	}

	
	/**
	 * Get all perceivable locations for a certain entity as absolute locations
	 * 
	 * @param myAbsoluteLocation
	 * @param direction
	 * @return
	 */
	public ArrayList<LocationCorrespondance> getPerceivableRelativeLocationsAsAbsoluteLocations(Location myAbsoluteLocation, double direction, int visionRadius, boolean halfY) {
		ArrayList<LocationCorrespondance> result = new ArrayList<LocationCorrespondance>();
		
		//Get vision in layers
		for (int i=0; i<=visionRadius; i++) {
			ArrayList<Location> relativeLocations = new ArrayList<Location>();
			//In agent coordinates, get vision
			relativeLocations = this.getRelativeLocations(i, i, halfY);


			ArrayList<LocationCorrespondance> absoluteLocationsForDirection = transformToAbsoluteCoordinates(myAbsoluteLocation, direction, relativeLocations, i);
			//Add layers
			result.addAll(absoluteLocationsForDirection);
		}
		
		return result;
	}
	
	
	public ArrayList<ExternalPerceptionInterface> getEntitiesForExternalPerception(Body body) {
		ArrayList<ExternalPerceptionInterface> result = new ArrayList<ExternalPerceptionInterface>();
		
		Location myLocation = body.getOwnerEntity().getLocation();
		
		//Absolute direction to coordinate system
		//double myDirection = body.getOwnerEntity().getDirection()-90;
		double myDirection = body.getOwnerEntity().getDirection();
		
		//Get perceiveable locations as abosulte coordinates
		ArrayList<LocationCorrespondance> absolutePerceivableLocations = this.getPerceivableRelativeLocationsAsAbsoluteLocations(myLocation, myDirection, RADIUS, true);
		
		for (LocationCorrespondance loc : absolutePerceivableLocations) {
			//Get all entities on that loccation
			ArrayList<EntityInterface> foundEntities = this.bodyInteractionEngine.getEntityAtLocation(loc.getAbsoluteLocation());
			
			//Transform back into relative coordinates
			for (EntityInterface entity : foundEntities) {				
				result.add(new ExternalPerception(entity.getName(), entity.getBodyType(), entity.getEntityIdentifier(), loc.getRelativeCorrespondingLocation().x, loc.getRelativeCorrespondingLocation().y));
			}
		}
		
		return result;
	}
	
	public String printPerception(ArrayList<ExternalPerceptionInterface> perception) {
		String result = "";
		final int numberOfObjectsField = 3;
		final String noneSting = "NONE";
		
		//For all fields x and y
		for (int x=-RADIUS; x<=RADIUS; x++) {
			for (int y=0; y<=RADIUS; y++) {
				String fieldResult = "";
				
				//Get all object with position (x, y)
				ArrayList<String> perceptsOnPosition = new ArrayList<String>();
				for (ExternalPerceptionInterface percept : perception) {
					if (percept.getXRelativeCoordinate()==x && percept.getYRelativeCoordinate()==y) {
						perceptsOnPosition.add(percept.getObjectName());
						fieldResult = fieldResult.concat(percept.getObjectName()).concat(",");
					}
				}
				
				//Fill sublist with NOTHING if there are no further subjects
				int numberOfNothing = numberOfObjectsField - perceptsOnPosition.size();
				if (numberOfNothing>0) {
					for (int i=0;i<numberOfNothing;i++) {
						perceptsOnPosition.add(noneSting);
						fieldResult = fieldResult.concat(noneSting).concat(",");
					}
				}
				
				result = result.concat(fieldResult);
			}
		}
		
		return result;
	}
	
}
