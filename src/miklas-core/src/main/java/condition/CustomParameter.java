package condition;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomParameter {
	private final HashMap<String, ArrayList<String>> parameterList;

	/**
	 * Keep custom parameters
	 * 
	 * @param parameters
	 */
	public CustomParameter(HashMap<String, ArrayList<String>> parameters) {
		this.parameterList = parameters;
	}
	
	/**
	 * get a single value of a property
	 * 
	 * @param property
	 * @return
	 */
	public String getSingleParameter(String property) {
		String result = "";
		
		ArrayList<String> entryList = this.getMultipleParameter(property);
		if (entryList.isEmpty()==false) {
			result = entryList.get(0);
		}
		
		return result;
	}
	
	/**
	 * Get a list of values for a single property
	 * 
	 * @param property
	 * @return
	 */
	public ArrayList<String> getMultipleParameter(String property) throws NullPointerException {
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<String> entryList = this.parameterList.get(property);
		if (entryList!=null) {
			result = entryList;
		} else {
			throw new NullPointerException("the property " + property + " does not exist");
		}
		
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomParameter [parameterList=");
		builder.append(parameterList);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
