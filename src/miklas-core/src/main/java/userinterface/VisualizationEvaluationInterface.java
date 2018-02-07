package userinterface;

import ch.aplu.jgamegrid.Location;
import entity.Entity;
import evaluator.EntityEvaluation;

public interface VisualizationEvaluationInterface {
	public void updateStats(String entityIdentifier, EntityEvaluation evaluation);
	public void registerEntity(Entity entity, int numberOfIconsContinousLoop, int iconChangeInterval, int totalNumberOfIcons);
	public void registerEventGraphic(String entityIdentifier, String poEventName, int graphicNumber, boolean isPermanentGraphicChange) throws Exception;
	public void updateEntityIcon(String entityIdentifier);
	public void updateEntityIcon(String entityIdentifier, String eventName);
	public int getTimeStamp();
	public Location updatePerceptionForEvaluation(String entityIdentifier);
}
