package config;

public class VisualizationConfig {
	private final int cellSize;
	private final int simulationPeriod;
	private final String bgImagePath;
	private final boolean isShowGrid;
	
	public VisualizationConfig(int cellSize, int simulationPeriod, String bgImagePath, boolean isShowGrid) {
		super();
		this.cellSize = cellSize;
		this.simulationPeriod = simulationPeriod;
		this.bgImagePath = bgImagePath;
		this.isShowGrid = isShowGrid;
		
	}

	public int getCellSize() {
		return cellSize;
	}

	public int getSimulationPeriod() {
		return simulationPeriod;
	}

	public String getBgImagePath() {
		return bgImagePath;
	}

	public boolean isShowGrid() {
		return isShowGrid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VisualizationConfig [cellSize=");
		builder.append(cellSize);
		builder.append(", simulationPeriod=");
		builder.append(simulationPeriod);
		builder.append(", bgImagePath=");
		builder.append(bgImagePath);
		builder.append(", isShowGrid=");
		builder.append(isShowGrid);
		builder.append("]");
		return builder.toString();
	}
	
	
}
