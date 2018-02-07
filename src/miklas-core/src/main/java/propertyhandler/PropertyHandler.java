/*
 * @author Thomas Leber
 */
package propertyhandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * This class allows to load and store parameters from/to an parameter file.
 * 
 * @author Friederich Kupzog
 * @author leber
 */
public class PropertyHandler
{
	
	/** The ht. */
	private final Properties	ht;
	
	/**
	 * Instantiates a new property handler.
	 */
	public PropertyHandler()
	{
		this.ht = new Properties();
		//Configuration config = new PropertiesConfiguration();
		
	}
	
	/**
	 * Instantiates a new property handler.
	 * 
	 * @param noLogger
	 *          the no logger
	 */
	public PropertyHandler(boolean noLogger)
	{
		this.ht = new Properties();
	}
	
	/**
	 * Load.
	 * 
	 * @param filename
	 *          the filename
	 * @return true, if successful
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public boolean load(final String filename) throws IOException
	{
		
		
		FileInputStream fileStream = null;
		try
		{
			fileStream = new FileInputStream(filename);
			
		} catch (Exception e)
		{
			return false;
			// TODO: handle exception
		}
		
		
		
		final BufferedInputStream f = new BufferedInputStream(fileStream);
		this.ht.load(f);
		f.close();
		
		return true;
	}
	
	/**
	 * Safe.
	 * 
	 * @param filename
	 *          the filename
	 * @return true, if successful
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public boolean save(final String filename) throws IOException
	{
		final BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(filename));
		this.ht.store(f, "Programmspezifische Einstellungen. Aendern Sie nichts, wenn Sie nicht wissen, was Sie tun!");
		f.close();
		
		return true;
	}
	
	/**
	 * Sets the option.
	 * 
	 * @param optionName
	 *          the option name
	 * @param value
	 *          the value
	 */
	public void setOption(final String optionName, String value)
	{
		if (value == null)
		{
			value = "";
		}
		this.ht.setProperty(optionName, value);
	}
	
	/**
	 * Gets the int.
	 * 
	 * @param optionName
	 *          the option name
	 * @return the int
	 * @throws Exception
	 *           the exception
	 */
	public int getInt(String optionName) throws PropertyException
	{
		final String erg = this.ht.getProperty(optionName);
		if (erg == null)
		{
			throw new PropertyException("Option " + optionName + " was not found.");
		}
		return Integer.parseInt(erg);
	}
	
	/**
	 * Gets the string.
	 * 
	 * @param optionName
	 *          the option name
	 * @return the string
	 * @throws PropertyException
	 *           the property exception
	 */
	public String getString(String optionName) throws PropertyException
	{
		final String erg = this.ht.getProperty(optionName);
		if (erg == null)
		{
			throw new PropertyException("Option " + optionName + " was not found.");
		}
		return erg;
	}
	
	/**
	 * Gets the double.
	 * 
	 * @param optionName
	 *          the option name
	 * @return the double
	 * @throws Exception
	 *           the exception
	 */
	public double getDouble(String optionName) throws Exception
	{
		final String erg = this.ht.getProperty(optionName);
		if (erg == null)
		{
			throw new Exception("Option " + optionName + " was not found.");
		}
		return Double.parseDouble(erg);
		
	
	}
	
	/**
	 * Get boolean value.
	 * 
	 * @param optionName
	 *          the option name
	 * @return the boolean
	 * @throws Exception
	 *           the exception
	 */
	public boolean getBoolean(String optionName) throws Exception
	{
		final String erg = this.ht.getProperty(optionName);
		if (erg == null)
		{
			throw new Exception("Option " + optionName + " was not found.");
		}
		return Boolean.parseBoolean(erg);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Property handler. Content: " + this.ht.toString();
		
	}
	
	/**
	 * Get options with this format: device.1.datapointaddressI
	 * 
	 * @param poPrefix
	 *          the po prefix
	 * @param pnNumber
	 *          the pn number
	 * @param poSuffix
	 *          the po suffix
	 * @return the string
	 * @throws Exception
	 *           the exception
	 */
	public String getString(String poPrefix, int pnNumber, String poSuffix) throws Exception
	{
		String oResult = "";
		
		// Create String
		String oOptionString = poPrefix + "." + pnNumber + "." + poSuffix;
		oResult = this.getString(oOptionString);
		
		return oResult;
	}
	
	/**
	 * Get options with this format: device.1.datapointaddressI
	 * 
	 * @param poPrefix
	 *          the po prefix
	 * @param pnNumber
	 *          the pn number
	 * @param poSuffix
	 *          the po suffix
	 * @return the int
	 * @throws Exception
	 *           the exception
	 */
	public int getInt(String poPrefix, int pnNumber, String poSuffix) throws Exception
	{
		int oResult = 0;
		
		// Create String
		String oOptionString = poPrefix + "." + pnNumber + "." + poSuffix;
		oResult = this.getInt(oOptionString);
		
		return oResult;
	}
	
	/**
	 * Get options with this format: device.1.datapointaddressI
	 * 
	 * @param poPrefix
	 *          the po prefix
	 * @param pnNumber
	 *          the pn number
	 * @param poSuffix
	 *          the po suffix
	 * @return the double
	 * @throws Exception
	 *           the exception
	 */
	public double getDouble(String poPrefix, int pnNumber, String poSuffix) throws Exception
	{
		double rResult = 0;
		
		// Create String
		String oOptionString = poPrefix + "." + pnNumber + "." + poSuffix;
		rResult = this.getDouble(oOptionString);
		
		return rResult;
	}
	
	/**
	 * Get options with this format: device.1.datapointaddressI
	 * 
	 * @param poPrefix
	 *          the po prefix
	 * @param pnNumber
	 *          the pn number
	 * @param poSuffix
	 *          the po suffix
	 * @return the boolean
	 * @throws Exception
	 *           the exception
	 */
	public boolean getBoolean(String poPrefix, int pnNumber, String poSuffix) throws Exception
	{
		boolean bResult = false;
		
		// Create String
		String oOptionString = poPrefix + "." + pnNumber + "." + poSuffix;
		bResult = this.getBoolean(oOptionString);
		
		return bResult;
	}
	
	public ArrayList<String> getStringFromList(String address, String splitChar) throws Exception {
		String oOptionString = address;
		ArrayList<String> oSplitStringAsArrayList = new ArrayList<String>();
		
		try {
			String loadedOptions = this.ht.getProperty(oOptionString); 
			
			if (loadedOptions!=null && loadedOptions.isEmpty()==false) {
				String[] oSplitstring = loadedOptions.split(splitChar);
				oSplitStringAsArrayList.addAll(Arrays.asList(oSplitstring));
			}
		} catch (NullPointerException e) {
			throw new Exception("Cannot find string for property=" + oOptionString);
		}
		
		
		return oSplitStringAsArrayList;
	}
	
	/**
	 * Check if a property exists.
	 * 
	 * @param optionName
	 *          the option name
	 * @return true, if successful
	 */
	public boolean propertyExists(String optionName)
	{
		boolean bResult = false;
		
		final String erg = this.ht.getProperty(optionName);
		if (erg != null)
		{
			bResult = true;
		}
		
		return bResult;
	}
	
	public Hashtable<String, Object> getAllMatchingStringProperties(String stringExpression) {
		Hashtable<String, Object> result = new Hashtable<String, Object>();
		
		for (Object e : this.ht.keySet()) {
			if (stringExpression.contains(e.toString())) {
				result.put((String) e, ht.getProperty((String) e));
			}
		}
		
		return result;
	}
	
	public ArrayList<String> getUnknownAddressPart(String prefix, String splitchar) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (Object e : this.ht.keySet()) {
			//If string starts with prefix then it is taken
			String key = e.toString();
			if (key.startsWith(prefix)==true) {
				String sub = key.substring(prefix.length()+1, key.length());
				String[] sub2 =sub.split("\\.");
				String unknownAddressString = sub2[0];
				if (result.contains(unknownAddressString)==false) {
					result.add(unknownAddressString);
				}
			}
		}
		
		return result;
	}
	
	public int getSize() {
		return this.ht.size();
	}
	
}
