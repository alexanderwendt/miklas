package config;

import java.util.Arrays;

public class WorldMapFileConfig {
	
	private final int horizontalCells;
	private final int verticalcells;
	private final String[] layer;
	
	public WorldMapFileConfig(int horizontalCells, int verticalcells, String[] layers) {
		this.horizontalCells = horizontalCells;
		this.verticalcells = verticalcells;
		this.layer = layers;
	}
	
	public void setLayer(int layer, String map) {
		this.layer[layer] = map;
	}

	public int getVerticalcells() {
		return verticalcells;
	}

	public int getHorizontalCells() {
		return horizontalCells;
	}

	public String[] getLayer() {
		return layer;
	}
	
	public int getNumberOfLayers() {
		return layer.length;
	}

	@Override
	public String toString() {
		return "WorldMapConfig [horizontalCells=" + horizontalCells
				+ ", verticalcells=" + verticalcells + ", layer="
				+ Arrays.toString(layer) + "]";
	}
	

}
