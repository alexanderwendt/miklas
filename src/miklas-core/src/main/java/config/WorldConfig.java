package config;

import ch.aplu.jgamegrid.Location;

public class WorldConfig {
	private int mnHorzCells;
	private int mnVertCells;
	//private final String[] map;
	private char[][][] moWorldArray;
	
//	private final String moMazeLayer1 =
//			"fffffffffffffff" + // 0
//			"fffffffffffffff" + // 2
//			"fffffffffffffff" + // 4
//			"fffffffffffffff" + // 6
//			"fffffffffffffff" + // 8
//			"fffffffffffffff" + // 10
//			"fffffffffffffff" + // 12
//			"fffffffffffffff" + // 14
//			"fffffffffffffff" + // 16
//			"fffffffffffffff" + // 18
//			"fffffffffffffff" + // 20
//			"fffffffffffffff" + // 22
//			"fffffffffffffff" + // 24
//			"fffffffffffffff" + // 26
//			"fffffffffffffff"; // 28
	
//	private final String moMazeLayer2 =
//			"wwwwwwwwwwwwwww" + // 0
//			"w             w" + // 2
//			"w        aabb w" + // 4
//			"w   abc       w" + // 6
//			"w   wwwwwwww  w" + // 8
//			"w   bbccwaba  w" + // 10
//			"w       wccc  w" + // 12
//			"wwwwwwwwwabcc w" + // 14
//			"w       w     w" + // 16
//			"w       w     w" + // 18
//			"w   aabbw     w" + // 20
//			"w       w     w" + // 22
//			"w       wwww  w" + // 24
//			"w    m   n    w" + // 26
//			"wwwwwwwwwwwwwww"; // 28
	
	
//	private final HashMap<Character, ActorAssignment> moGraphicMap = new HashMap<Character, ActorAssignment>();

//	private void initGraphicPaths() {
//		moGraphicMap.put('f', new ActorAssignment('f', "graphics/floor.gif"));
//		moGraphicMap.put('w', new ActorAssignment('w', "graphics/wall.gif"));
//		moGraphicMap.put('a', new ActorAssignment('a', "graphics/eis1.gif"));
//		moGraphicMap.put('b', new ActorAssignment('b', "graphics/eis2.gif"));
//		moGraphicMap.put('c', new ActorAssignment('c', "graphics/brocolli.gif"));
//		moGraphicMap.put('m', new ActorAssignment('m', "graphics/mia.gif"));
//		moGraphicMap.put('n', new ActorAssignment('n', "graphics/niklas.gif"));
//		
//	}

	public WorldConfig() {
		
	}
	
	public void generateWorld(WorldMapFileConfig conf) {
		//Define Size of world array
				//ArrayList<String> oLayer = new ArrayList<String>();
				//oLayer.add(moMazeLayer1);
				//oLayer.add(moMazeLayer2);
				//initGraphicPaths();
				
				WorldMapFileConfig map = conf;
				
				mnHorzCells = map.getHorizontalCells();
				mnVertCells = map.getVerticalcells();
				
				moWorldArray = fillArray(mnVertCells, mnHorzCells, map.getLayer());
	}
	
	/**
	 * Create map as string array
	 * 
	 * @param pnHorzCells
	 * @param pnVertCells
	 * @param poMaze
	 * @return
	 */
	private char[][][] fillArray(int pnHorzCells, int pnVertCells, String[] poLayerList) {
		char[][][] oResult = new char[pnHorzCells][pnVertCells][poLayerList.length];
		
		for (int i = 0; i < pnHorzCells; i++) {
			for (int k = 0; k < pnVertCells; k++) {
				for (int l = 0; l < poLayerList.length; l++) {
					char cSign = poLayerList[l].charAt(pnVertCells * i + k);
					oResult[i][k][l] = cSign;
				}	
			}
		}
		return oResult;
	}
	
	public int getLayerCount() {
		int result = moWorldArray[0][0].length; 
		
		return result;
	}
	

	/**
	 * Get value of particual cell
	 * 
	 * @param location
	 * @return
	 * @throws Exception 
	 */
	public char getCellValue(Location location, int pnLayer) throws Exception	{
		char cResult = '\0';
		if (location.x>mnHorzCells-1 || location.x<0) {
			throw new Exception("x value erroneus. " + pnLayer + "; Max x cells=" + mnHorzCells + "; x must be >= 0; Location=" + location);	
		} else if (location.y>mnVertCells-1 || location.y<0) {
			throw new Exception("y value erroneus. Layer=" + pnLayer + "; Max y cells=" + mnVertCells + "; y must be >= 0; Location=" + location);	
		}
		
		cResult = moWorldArray[location.y][location.x][pnLayer];
		
		return (cResult);
	}
	
//	/**
//	 * Get address for char
//	 * 
//	 * @param c
//	 * @return
//	 * @throws Exception
//	 */
//	public String getDirectoryAddressForChar(char c) throws Exception {
//		String oAddress = moGraphicMap.get(c).getGraphicAddress();
//		
//		if (oAddress==null) {
//			throw new Exception("No value found");
//		}
//		
//		return oAddress;
//	}


	public int getXDimension() {
		return this.mnHorzCells;
	}

	public int getYDimension() {
		return this.mnVertCells;
	}

}
