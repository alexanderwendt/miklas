package gameengine;

import userinterface.MusicManager;
import userinterface.SoundManager;
import userinterface.VisualizationEvaluationInterface;
import ch.aplu.jgamegrid.GameGrid;
import evaluator.EvaluatorManagerMindInterface;

public interface GameEngine {
	public GameGrid getGameGrid();
	public void addActorsToWorld() throws Exception;
	public void setVisualization(VisualizationEvaluationInterface poVis);
	public void setSoundManager(SoundManager sound);
	public void setScoreManager(EvaluatorManagerMindInterface score);
	public void initializeEntityFactory();
	public void init();
    public void setCellSize(int value);
    public void setNbHorzCells(int value);
    public void setNbVertCells(int value);
	public void setMusicManager(MusicManager music);
	public MusicManager getMusicManager();
}
