package userinterface;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.EntityInterface;

public class GraphicSetting {
	
	private static final Logger	log	= LoggerFactory.getLogger(GraphicSetting.class);
	
	
	private final String entityIdentifier;
	private final int numberOfIconsContinousLoop;
	private final int totalNumberOfIcons;
	private final int iconChangeInterval;
	private HashMap<String, GraphicSettingForEvent> eventToGraphicList = new HashMap<String, GraphicSettingForEvent>();	//Event, graphicnumber
	private final EntityInterface entity;
	private int lockCount=0;
	private int intervalLockCount = 0;
	
	private static final int LOCKTIME = 3;
	
	private boolean isPermanentActivation = false;
	
	private int currentUsedGraphicNumber = 0;
	
	public GraphicSetting(EntityInterface entity, int numberOfIconsContinousLoop, int iconChangeInterval, int totalNumberOfIcons) {
		this.entity = entity;
		this.entityIdentifier = entity.getEntityIdentifier();
		this.numberOfIconsContinousLoop = numberOfIconsContinousLoop;
		this.iconChangeInterval = iconChangeInterval;
		this.totalNumberOfIcons = totalNumberOfIcons;
	}
	
	public void updateRotatingGraphic() {
		updateIcon();
	}
	
	private void updateIcon() {
		log.trace("Current graphic {}", currentUsedGraphicNumber);
		//If any rotation shall take place, then this number must be >1
		if (this.numberOfIconsContinousLoop>1) {
			
			if (intervalLockCount<=0) {
				currentUsedGraphicNumber++;
				intervalLockCount = iconChangeInterval;
			}
			
			if (currentUsedGraphicNumber == numberOfIconsContinousLoop) {
				currentUsedGraphicNumber = 0;
			}
		}
		
		if (lockCount>0) {
			//If permanent activation is false, then continue countdown, else leave current graphic
			if (isPermanentActivation==false) {
				lockCount--;
				//intervalLockCount--;
			}
		} else {
			intervalLockCount--;
			this.entity.showIcon(currentUsedGraphicNumber);
		}
	}

	public String getActorIdentifier() {
		return entityIdentifier;
	}
	
	public void registerEventGraphic(String eventName, int graphicNumber, boolean isPermanentGraphicChange) throws Exception {
		if (graphicNumber<this.totalNumberOfIcons) {
			this.eventToGraphicList.put(eventName, new GraphicSettingForEvent(graphicNumber, isPermanentGraphicChange));

			log.debug("Event graphic {} for event {} registered for {}", graphicNumber, eventName, this.entityIdentifier);
		} else {
			throw new Exception("Event name " + eventName + ", Graphic number " + graphicNumber + " does not exist. The highest graphic number is " + this.totalNumberOfIcons);
		}
		
	}
	
	public void activateEventGraphic(String eventName) throws Exception {
		try {
			int eventGraphicNumber = this.eventToGraphicList.get(eventName).getGraphic();
			//If 0, it the default graphic and it should not be shown in an event
			if (eventGraphicNumber!=0) {
				this.entity.showIcon(eventGraphicNumber);
				lockCount=LOCKTIME;
				
				this.isPermanentActivation = this.eventToGraphicList.get(eventName).isPermanentGraphicChange();
			}
			log.debug("Show graphic {} for event {} for entity {}", eventGraphicNumber, eventName, this.entityIdentifier);
		} catch (Exception e){
			log.debug("Could not find any graphic for event {} for entity {}", eventName, this.entityIdentifier);
			//throw new Exception(e.getMessage());
		}	
	}
	
	public int getTotalNumberOfIcons() {
		return totalNumberOfIcons;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("entityIdentifier=");
		builder.append(entityIdentifier);
		builder.append(", numberOfIconsContinousLoop=");
		builder.append(numberOfIconsContinousLoop);
		builder.append(", iconChangeInterval=");
		builder.append(iconChangeInterval);
		builder.append(", currentUsedGraphicNumber=");
		builder.append(currentUsedGraphicNumber);
		return builder.toString();
	}
	
	
	
	
	
}
