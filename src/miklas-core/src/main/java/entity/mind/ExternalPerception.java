package entity.mind;

public class ExternalPerception implements ExternalPerceptionInterface {

	private final String objectName;
	private final String objectBodyType;
	private final String objectIdentifier;
	private final int x;
	private final int y;
	
	public ExternalPerception(String objectName, String objectBodyType, String objectIdentifier, int x, int y) {
		super();
		this.objectName = objectName;
		this.objectBodyType = objectBodyType;
		this.objectIdentifier = objectIdentifier;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String getObjectName() {
		return this.objectName;
	}

	@Override
	public int getXRelativeCoordinate() {
		return this.x;
	}

	@Override
	public int getYRelativeCoordinate() {
		return this.y;
	}

	@Override
	public String getObjectBodyType() {
		return this.objectBodyType;
	}
	
	public String toString() {
		return this.objectName + ":" + this.objectBodyType + " (" + this.x + ", " + this.y + ")";
	}

	@Override
	public String getObjectIdentifier() {
		return this.objectIdentifier;
	}
	
}