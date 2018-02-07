package utils;

import ch.aplu.jgamegrid.Location;

/**
 * @author wendt
 *
 */
public class LocationCorrespondance {
	
	private final Location entityLocation;
	private final double entityDirection;
	private final Location absoluteLocation;
	private final Location relativeCorrespondingLocation;
	
	/**
	 * 
	 * 
	 * @param entityLocation
	 * @param entityDirection
	 * @param absoluteLocation
	 * @param relativeCorrespondingLocation
	 */
	public LocationCorrespondance(Location entityLocation,
			double entityDirection, Location absoluteLocation,
			Location relativeCorrespondingLocation) {
		super();
		this.entityLocation = entityLocation;
		this.entityDirection = entityDirection;
		this.absoluteLocation = absoluteLocation;
		this.relativeCorrespondingLocation = relativeCorrespondingLocation;
	}

	public Location getEntityLocation() {
		return entityLocation;
	}

	public double getEntityDirection() {
		return entityDirection;
	}

	public Location getAbsoluteLocation() {
		return absoluteLocation;
	}

	public Location getRelativeCorrespondingLocation() {
		return relativeCorrespondingLocation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LocationCorrespondance [entityLocation=");
		builder.append(entityLocation);
		builder.append(", entityDirection=");
		builder.append(entityDirection);
		builder.append(", absoluteLocation=");
		builder.append(absoluteLocation);
		builder.append(", relativeCorrespondingLocation=");
		builder.append(relativeCorrespondingLocation);
		builder.append("]");
		return builder.toString();
	}
}
