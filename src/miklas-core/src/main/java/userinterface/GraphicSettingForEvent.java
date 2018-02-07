package userinterface;

public class GraphicSettingForEvent {
	
	private final int graphic;
	private final boolean isPermanentGraphicChange;
	
	public GraphicSettingForEvent(int graphic, boolean isPermanentGraphicChange) {
		super();
		this.graphic = graphic;
		this.isPermanentGraphicChange = isPermanentGraphicChange;
	}
	
	public int getGraphic() {
		return graphic;
	}
	public boolean isPermanentGraphicChange() {
		return isPermanentGraphicChange;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GraphicSettingForEvent [graphic=");
		builder.append(graphic);
		builder.append(", isPermanentGraphicChange=");
		builder.append(isPermanentGraphicChange);
		builder.append("]");
		return builder.toString();
	}
	
	
}
