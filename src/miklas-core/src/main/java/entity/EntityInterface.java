package entity;

public interface EntityInterface {
	public void actionOnBody(Entity Caller, String poAction);
	public void reactionOnBody(Entity ReCaller, String poAction);
	public String getName();
	public String getEntityIdentifier();
	public String getBodyType();
	public String getParentBodyType();
	public int getEntityLayer();
	public void showIcon(int graphicNumber);
}
