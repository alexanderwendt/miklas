package entity.mind;

/**
 * Interface of a data structure, which contains a perceived object
 * 
 * @author wendt
 *
 */
public interface ExternalPerceptionInterface {
	public String getObjectIdentifier();
	/**
	 * Get the unique name of the object. No attributes are used.
	 * 
	 * @return
	 */
	public String getObjectName();
	/**
	 * Get the bodytype of the object, which is a string, describing bodies, which are available, e. g. eatable bodies
	 * 
	 * @return
	 */
	public String getObjectBodyType();
	/**
	 * Get the X coordinate of the cell minus my own X coordinate
	 * 
	 * @return
	 */
	public int getXRelativeCoordinate();
	/**
	 * Get the Y coordinate of the cell minus my own Y coordinate
	 * 
	 * @return
	 */
	public int getYRelativeCoordinate();
}
