package entity.body;

import java.util.ArrayList;

import entity.mind.BodyPerceptionInterface;
import entity.mind.ExternalPerceptionInterface;


public interface BodyMindInterface {
	
	public ArrayList<ExternalPerceptionInterface> getPerception();
	public BodyPerceptionInterface getBodyPerception();
	public void executeAction(String poAction) throws Exception;
	public String getCurrentAction();
	public String getEntityIdentifier();
	//public int getHealth();
}
