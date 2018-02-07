package gameengine;

import userinterface.MusicManager;
import userinterface.SoundManager;
import userinterface.VisualizationEvaluationInterface;
import ch.aplu.jgamegrid.GameGrid;
import evaluator.EvaluatorManagerMindInterface;

public class GameEngineStub implements GameEngine {
	
	GameGrid gameGrid = new GameGrid();

	@Override
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	@Override
	public void addActorsToWorld() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisualization(VisualizationEvaluationInterface poVis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSoundManager(SoundManager sound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScoreManager(EvaluatorManagerMindInterface score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeEntityFactory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellSize(int value) {
		this.gameGrid.setCellSize(value);
		
	}

	@Override
	public void setNbHorzCells(int value) {
		this.gameGrid.setNbHorzCells(value);
		
	}

	@Override
	public void setNbVertCells(int value) {
		this.gameGrid.setNbVertCells(value);
		
	}

	@Override
	public void setMusicManager(MusicManager music) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MusicManager getMusicManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
