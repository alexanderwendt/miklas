package entity.mind;

public class BodyPerception implements BodyPerceptionInterface {

	private final int maxHealth;
	private final int currentHealth;
	private final int pleasurePain;
	
	public BodyPerception(int maxHealth, int currentHealth, int pleasurePain) {
		super();
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
		this.pleasurePain = pleasurePain;
	}
	
	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}

	@Override
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	@Override
	public int getPainOrPleasure() {
		return this.pleasurePain;
	}
	
	public String toString() {
		return "MaxHealth=" + this.maxHealth + ", Current Health=" + this.currentHealth + ", PleasurePain=" + this.pleasurePain;
	}
	
}
