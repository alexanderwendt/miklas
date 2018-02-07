package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import userinterface.VisualizationEvaluationInterface;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import condition.Condition;
import entity.body.BodyInterface;
import entity.mind.AnimateMind;
import evaluator.EvaluatorManagerMindInterface;

public class Entity extends Actor implements EntityInterface {
	
	public final String entitiyIdentifier;
	protected BodyInterface moBody;	//Receives all input, registers it
	protected final EvaluatorManagerMindInterface moScore;
	private final VisualizationEvaluationInterface visualization;
	
	//=== Final entity settings ===//
	private final String moName;
	private final GameGrid moGameGrid;
	private final int entityLayer;
	
	protected static final Logger log = LoggerFactory.getLogger(Entity.class);
	
	/**
	 * Constructor for multiple images, which can rotate
	 * 
	 * @param pbRotateSprites
	 * @param poGameEngine
	 * @param prPath
	 * @param pnSpritesCount
	 * @param poName
	 */
	public Entity(GameGrid poGameEngine, String poName, String entityID, String prGraphicPath, boolean pbRotateGraphicWithMovement, int pnGraphicsUsedCount, int pnGraphicUpdateInterval, int entityLayer, EvaluatorManagerMindInterface score, VisualizationEvaluationInterface vis) {
		super(pbRotateGraphicWithMovement, prGraphicPath, pnGraphicsUsedCount);  // Rotatable, multiple images
		entitiyIdentifier = poName + entityID;
		//mbRotateGraphicWithMovement = pbRotateGraphicWithMovement;
		moGameGrid = poGameEngine;
		moName=poName;
		this.moScore = score;
		this.visualization = vis;
		this.entityLayer = entityLayer;
		
		//Set name
		Thread.currentThread().setName(entitiyIdentifier);
		log.debug("Init entitiy {}", poName);
		
		//MDC.put("id", this.entitiyIdentifier);
	}
	
	/* (non-Javadoc)
	 * @see entity.parts.IEntity#actionOnBody(entity.Entity, entity.parts.EAction)
	 */
	public void actionOnBody(Entity caller, String poAction) {
		//First execute blocking method on this body
		this.moBody.executeEffectOfExternActionOnMyBody(caller, poAction);
		
		//Then execute the reaction
		caller.reactionOnBody(this, poAction);
	}
	/* (non-Javadoc)
	 * @see entity.parts.IEntity#reactionOnBody(entity.Entity, entity.parts.EAction)
	 */
	public void reactionOnBody(Entity reCaller, String poAction) {
		this.moBody.executeEffectOfExternReactionMyOnBody(reCaller, poAction);
	}
	
	/**
	 * Initialize a body for the entity
	 */
	public void setBody(BodyInterface poBody) {
		this.moBody = poBody;
	}
	
	/* (non-Javadoc)
	 * @see ch.aplu.jgamegrid.Actor#act()
	 */
	public void act() {
		this.visualization.updateEntityIcon(this.getEntityIdentifier());
		
		//Call actions of the body
		this.moBody.callBodyToAct();
	}

	public GameGrid getGameGrid() {
		return moGameGrid;
	}

	@Override
	public String getName() {
		return this.moName;
	}

	@Override
	public String getBodyType() {
		return this.moBody.getBodyType();
	}
	
	@Override
	public String getParentBodyType() {
		String result ="";
		if (this.moBody.getParentBodyType().isEmpty()==false) {
			result = this.moBody.getParentBodyType().get(0);
		}
		return result;
	}

	@Override
	public int getEntityLayer() {
		return this.entityLayer;
	}

	@Override
	public void showIcon(int graphicNumber) {
		this.show(graphicNumber);
		
	}

	@Override
	public String getEntityIdentifier() {
		return this.entitiyIdentifier;
	}
	
	public String toString() {
		String oResult = this.entitiyIdentifier;
//		if (this.gameGrid.get==true) {
//			oResult += "(" + this.getLocation().x + ", " + this.getLocation().y + ")";
//		}
		return oResult;
		
	}
	
	/* (non-Javadoc)
	 * @see ch.aplu.jgamegrid.Actor#getDirection()
	 */
	public double getDirection() {
		//Overwrite the direction of super and add direction offset -90
		double result = super.getDirection() - 90;
		return result;
	}
	
	public boolean isBodyAnimate() {
		return moBody.isBodyAnimate();
	}
	
	public EvaluatorManagerMindInterface getEvaluator() {
		return moScore;
	}
}
