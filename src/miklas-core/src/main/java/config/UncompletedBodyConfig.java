package config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UncompletedBodyConfig {
	private final static int defaultVisionRadius = 3;
	private final static String defaultVisionType = "half";
	
	private final String bodyName;
	private int visionRadius;
	private String visionType;
	private final LinkedList<String> parentBodyNames = new LinkedList<String>();
	private String mindName;
	private final ArrayList<String> eventOnActionName = new ArrayList<String>();
	private final ArrayList<String> eventOnReactionName = new ArrayList<String>();
	private final ArrayList<String> eventOnOwnActionName = new ArrayList<String>();
	private final ArrayList<String> eventOnBodyInternalsName = new ArrayList<String>();
	
	public UncompletedBodyConfig(UncompletedBodyConfig source) {
		super();
		
		this.bodyName = source.bodyName;
		this.visionRadius = source.visionRadius;
		this.visionType = source.visionType;
		this.parentBodyNames.addAll(source.parentBodyNames);
		this.mindName = source.mindName;
		this.eventOnActionName.addAll(source.eventOnActionName);
		this.eventOnReactionName.addAll(source.eventOnReactionName);
		this.eventOnOwnActionName.addAll(source.eventOnOwnActionName);
		this.eventOnBodyInternalsName.addAll(source.eventOnBodyInternalsName);
	}
	
	public UncompletedBodyConfig(String bodyTypeName, int visionRadius, String visionType, String parentBodyType, String mind, 
			ArrayList<String> eventOnActionName,
			ArrayList<String> eventOnReactionName,
			ArrayList<String> eventOnOwnActionName,
			ArrayList<String> eventOnBodyInternalsName) {
		
		super();
		
		this.bodyName = bodyTypeName;
		this.visionRadius = visionRadius;
		this.visionType = visionType;
		if (parentBodyType.isEmpty()==false) {
			this.parentBodyNames.addFirst(parentBodyType);
		}
		this.mindName = mind;
		this.eventOnActionName.addAll(eventOnActionName);
		this.eventOnReactionName.addAll(eventOnReactionName);
		this.eventOnOwnActionName.addAll(eventOnOwnActionName);
		this.eventOnBodyInternalsName.addAll(eventOnBodyInternalsName);
	}
	
	public String getBodyName() {
		return bodyName;
	}
	public List<String> getParentBodyNames() {
		return parentBodyNames;
	}
	
	public String getFirstParentName() {
		String result = "";
		if (this.getParentBodyNames().get(0)!=null) {
			result = this.getParentBodyNames().get(0);
		}
		return result;
	}
	public String getMindName() {
		return mindName;
	}
	public ArrayList<String> getEventOnActionName() {
		return eventOnActionName;
	}
	public ArrayList<String> getEventOnReactionName() {
		return eventOnReactionName;
	}
	public ArrayList<String> getEventOnOwnActionName() {
		return eventOnOwnActionName;
	}
	public ArrayList<String> getEventOnBodyInternalsName() {
		return eventOnBodyInternalsName;
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		
		if ((obj instanceof UncompletedBodyConfig==true) && (this.bodyName.equals(((UncompletedBodyConfig)obj).bodyName))==true) {
			result = true;
		}
		
		return result;
	}
	
	public void enhanceWithParent(UncompletedBodyConfig parent) {
		//Overwrite mind
		if (this.mindName.equals("")==true) {
			this.mindName=parent.mindName;
		}
		
		//If no vision radius has been set, take the vision radius of the parent
		if (visionRadius==-1) {
			if (parent.visionRadius==-1) {
				this.visionRadius = defaultVisionRadius;
			} else {
				this.visionRadius = parent.visionRadius;
			}
		}
		
		//If no vision type has been set, it shall be imported from the parent. If it cannot be imported from the parent, the default type "half" is used.
		if (this.visionType==null || this.visionType.isEmpty()) {
			if (parent.visionType==null || parent.visionType.isEmpty()) {
				this.visionType = defaultVisionType;
			} else {
				this.visionType = parent.visionType;
			}
		}
		
		//Enhance with parent bodies of the parent
		this.parentBodyNames.addAll(this.parentBodyNames.size()-1, parent.parentBodyNames);
		
		//Add all parent events
		addIfNotExist(this.eventOnActionName, parent.eventOnActionName);
		addIfNotExist(this.eventOnBodyInternalsName, parent.eventOnBodyInternalsName);
		addIfNotExist(this.eventOnOwnActionName, parent.eventOnOwnActionName);
		addIfNotExist(this.eventOnReactionName, parent.eventOnReactionName);
		
		
	}
	
	private void addIfNotExist(ArrayList<String> toList, ArrayList<String> fromList) {
		for (String newElement : fromList) {
			if (toList.contains(newElement)==false) {
				toList.add(newElement);
			}
		}
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("UncompletedBodyConfig [bodyName=");
		builder.append(bodyName);
		//builder.append("]");
		return builder.toString();
	}

	public int getVisionRadius() {
		return visionRadius;
	}

	public String getVisionType() {
		return visionType;
	}

}
