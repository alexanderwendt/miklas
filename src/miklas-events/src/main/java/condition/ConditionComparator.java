package condition;

import java.util.ArrayList;
import java.util.HashMap;

import entity.body.BodyInterface;
import event.Datapoint;
import event.EventVariables;

public class ConditionComparator extends Condition {

	private final String OPERANDNAME = "operand";
	private final String VARIABLEANAME = "variablea";
	private final String VARIABLEBNAME = "variableb";
	
	BodyInterface body;
	
	private String variablea;
	private String variableb;
	private String operand;
	
	private int a;
	private int b;
	
	public ConditionComparator(String name, HashMap<String, ArrayList<String>> parameters) {
		super(name, parameters);

	}

	@Override
	public boolean testCondition() {
		boolean result = false;
		
		//Get variables as int
		Datapoint<Integer> dpa = new Datapoint<Integer>(variablea);
		Datapoint<Integer> dpb = new Datapoint<Integer>(variableb);
		
		boolean aFound = false;
		boolean bFound = false;
		try {
			aFound = body.getSharedVariable(dpa);
			bFound = body.getSharedVariable(dpb);
		} catch (Exception e) {
			log.error("Error getting values from the shared variables", e);
			return false;
		}
		
		if (aFound==true && bFound==true) {
			
			a = dpa.getValue();
			b = dpb.getValue();
			
			if (operand.equals("=")==true) {
				if (a==b) {
					result = true;
				}
			} else if (operand.equals(">=")==true) {
				if (a>=b) {
					result = true;
				}
			} else if (operand.equals("<=")==true) {
				if (a<=b) {
					result = true;
				}
			} else if (operand.equals(">")==true) {
				if (a>b) {
					result = true;
				}
			} else if (operand.equals("<")==true) {
				if (a<b) {
					result = true;
				}
			} else {
				try {
					throw new Exception("Operand " + operand + " is not supported");
				} catch (Exception e){
					log.error("Operand not supported. Check your config file", e);
					return false;
				}
			}
		}
		
		return result;
	}

	@Override
	protected void setCustomProperties() {
		variablea = this.customParameter.getSingleParameter(VARIABLEANAME);
		variableb = this.customParameter.getSingleParameter(VARIABLEBNAME);
		operand = this.customParameter.getSingleParameter(OPERANDNAME);
	}

	@Override
	public void initConditionWithPermanentDatastructures() throws Exception {
		Datapoint<BodyInterface> dp = new Datapoint<BodyInterface>(EventVariables.MYBODY.toString());
		boolean res = this.assignedEvent.getLocalDataStructure(dp);
		body = dp.getValue();
		
	}

}
