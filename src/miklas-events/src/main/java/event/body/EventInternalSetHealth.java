package event.body;

import event.BodyEffectEvent;
import event.Datapoint;

public class EventInternalSetHealth extends BodyEffectEvent {
	
	private final String HEALTHVARIABLENAME = "healthname";
	private final String HEALTHCHANGENAME = "healthchange";
	private final String MAXHEALTHVARIABLENAME = "maxhealthname";
	private final String MINHEALTHVARIABLENAME = "minhealthname";
	
	private final String INITHEALTHNAME = "inithealth";
	private final String MAXHEALTHNAME = "maxhealth";
	private final String MINHEALTHNAME = "minhealth";
	
	private int maxHealth;
	private int minHealth;
	private int initHealth;
	
	private String healthVariableName;
	private String healthChangeVariableName;
	private String maxhealthVariableName;
	private String minhealthVariableName;

	public EventInternalSetHealth(String poEventName) {
		super(poEventName);
	}

	@Override
	protected void runEffectOfEvent() {
		//Get healthchange
		int healthChange=0;
		try {
			Datapoint<Integer> dp0 = new Datapoint<Integer>(healthChangeVariableName);
			this.myBody.getTemporarySharedVariables().getData(dp0, true, 0);
			healthChange = dp0.getValue();
		} catch (Exception e) {
			log.error("Cannot receive data for {}", healthChangeVariableName);
		}
		
		try {
			int currentBodyHealth = 0;
			//Get current body health
			Datapoint<Integer> dp1 = new Datapoint<Integer>(healthVariableName);
			boolean res1 = this.myBody.getPermanentSharedVariables().getData(dp1);
			if (res1==false) {
				throw new Exception("Variable " + healthVariableName + " does not exist. Health cannot be updated");
			}
			currentBodyHealth = dp1.getValue();
			
			//Calculate new health
			int newHealth = currentBodyHealth + healthChange;
			int setHealth;
			
			if (newHealth<=this.minHealth) {
				setHealth = this.minHealth;
				log.info("{}>Agent lost all health={}", this.myBody.getEntityIdentifier(), this.minHealth);
			} else if (newHealth>=this.maxHealth) {
				setHealth = this.maxHealth;
				log.info("{}>Agent has already its max health={}", this.myBody.getEntityIdentifier(), this.maxHealth);
			} else {
				setHealth = newHealth;
				log.debug("{}>Set new health={}", this.myBody.getEntityIdentifier(), setHealth);
			}
			
			//Set new health
			this.myBody.getPermanentSharedVariables().putData(new Datapoint<Integer>(this.healthVariableName, setHealth));

		} catch (Exception e) {
			log.error("Cannot set health", e);
		}
		
	}

	@Override
	protected void setCustomProperties() {
		healthVariableName = (String)(this.customParameter.getSingleParameter(HEALTHVARIABLENAME));
		healthChangeVariableName = (String)(this.customParameter.getSingleParameter(HEALTHCHANGENAME));
		maxhealthVariableName = (String)(this.customParameter.getSingleParameter(MAXHEALTHVARIABLENAME));
		minhealthVariableName = (String)(this.customParameter.getSingleParameter(MINHEALTHVARIABLENAME));
		
		maxHealth = Integer.valueOf(this.customParameter.getSingleParameter(MAXHEALTHNAME));
		minHealth = Integer.valueOf(this.customParameter.getSingleParameter(MINHEALTHNAME));
		initHealth = Integer.valueOf(this.customParameter.getSingleParameter(INITHEALTHNAME));
	}
	
	public void runEventInit() throws Exception {
		//Initialized shared data structures
		//Set maxhealth
		this.myBody.getPermanentSharedVariables().putData(new Datapoint<Integer>(this.maxhealthVariableName, this.maxHealth));
		
		//Set minhealth
		this.myBody.getPermanentSharedVariables().putData(new Datapoint<Integer>(this.minhealthVariableName, this.minHealth));
		
		//Set current health
		this.myBody.getPermanentSharedVariables().putData(new Datapoint<Integer>(healthVariableName, initHealth));
		
	}

}
