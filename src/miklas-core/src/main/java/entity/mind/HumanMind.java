package entity.mind;

import java.awt.event.KeyEvent;

import ch.aplu.jgamegrid.GGKeyListener;
import ch.aplu.jgamegrid.GameGrid;
import entity.Entity;
import entity.body.BodyMindInterface;
import evaluator.EvaluatorManagerMindInterface;

public class HumanMind extends AnimateMind implements GGKeyListener {

	private final GameGrid moGameGrid;
	
	public HumanMind(Entity poPlayer, BodyMindInterface poBody, EvaluatorManagerMindInterface score) {
		super(poBody, score);
		
		moGameGrid = poPlayer.getGameGrid();
		moGameGrid.addKeyListener(this);
	}
	
	public boolean keyPressed(KeyEvent evt) {
		log.debug("Key event {} triggered", evt.getKeyCode());
		
		try {
	    	switch (evt.getKeyCode()) {
	      	case KeyEvent.VK_LEFT:
	      		this.setAction("TURN_LEFT");
	      		break;
	      	case KeyEvent.VK_UP:
	      		this.setAction("MOVE_FORWARD");
		    	  break;
	      	case KeyEvent.VK_RIGHT:
	      		this.setAction("TURN_RIGHT");
		    	  break;
	      	case KeyEvent.VK_DOWN:
	      		this.setAction("MOVE_BACKWARD");
		    	  break;
	      	case KeyEvent.VK_E:
	      		this.setAction("EAT");
	      		break;
	      	case KeyEvent.VK_A:
	      		this.setAction("ATTACK");
	      		break;
	      	case KeyEvent.VK_T:
	      		this.setAction("TALK");
	      		break;
	      	case KeyEvent.VK_P:
	      		this.setAction("PUSH");
	      		break;
	    	case KeyEvent.VK_O:
	      		this.setAction("PULL");
	      		break;
			}
	    } catch (Exception e) {
	    	log.error("Could not execute action for key event {}, {}", evt.getKeyCode(), KeyEvent.getKeyText(evt.getKeyCode()));
	    }

	    return true;
	}
	
	@Override
	public boolean keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeMindCycle() {
		// Do nothing as this is human control
		
		//TEST
		log.debug("{}>Perception: {}", "PUTAGENTHERE", this.getExternalPerception().toString());
		//log.debug(this.getBodyPerception().toString());
		
	}

	@Override
	public void killMind() {
		moGameGrid.removeKeyListener(this);
		
	}

}
