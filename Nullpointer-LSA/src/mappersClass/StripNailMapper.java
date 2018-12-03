package mappersClass;

import java.util.ArrayList;

import datasource.PowerToolToStripNailDTO;
import datasource.StripNailDTO;
import datasourceClass.PowerToolsToStripNailsGateway;
import datasourceClass.StripNailsFastenersInventoryItemsGateway;
import exception.DatabaseException;
import sharedDomain.*;

public class StripNailMapper extends StripNailAbstract{

	private StripNailsFastenersInventoryItemsGateway stripNailGateway;
	protected StripNail stripNail;

	/**
	 * Create Constructor
	 * @param upc upc of the stripNail
	 * @param manufacturerID manufacturerID of the stripNail
	 * @param price price of the stripNail
	 * @param numberInStrip number of nails in a strip
	 * @param length the length of the stripnail
	 * @throws Exception
	 */	
	public StripNailMapper(String upc, int manufacturerID, int price, double length, int numberInStrip) throws Exception
	{
		this.stripNailGateway = new StripNailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInStrip);
		this.buildStripNail();
	}
	
	
	/**
	 * Finds all stripnails within the database
	 * @return an ArrayList of all StripNails which exist in the database
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNail> findAll() throws DatabaseException
	{
		ArrayList<StripNail> stripNails = new ArrayList<StripNail>();
		ArrayList<StripNailDTO> stripNailDTOs = StripNailsFastenersInventoryItemsGateway.findAll();
		
		stripNailDTOs.forEach(sn -> 
		{
			try 
			{
				stripNails.add(new StripNail(sn.getId(), sn.getUpc(), sn.getManufacturerID(), sn.getPrice(), sn.getLength(), sn.getNumberInStrip()));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		});

		return stripNails;
	}
	
	/**
	 * Finder Constructor
	 * finds the stripnail by id
	 * @return
	 * @throws DatabaseException
	 */
	public StripNailMapper(int stripNailID) throws DatabaseException {
		this.stripNailGateway = new StripNailsFastenersInventoryItemsGateway(stripNailID);
		this.buildStripNail();
	} 
	
	/**
	 * Saves changed values of the stripNail to the database
	 */
	public void persist() throws DatabaseException {
		stripNailGateway.setUPC(stripNail.getUpc());
		stripNailGateway.setManufacturerID(stripNail.getManufacturerID());
		stripNailGateway.setPrice(stripNail.getPrice());
		stripNailGateway.setLength(stripNail.getLength());
		stripNailGateway.setNumberInStrip(stripNail.getNumberInStrip());		
		stripNailGateway.persist();
	}

	/**
	 * Deletes a the powertool from the database
	 */
	public void delete() throws DatabaseException {
		stripNailGateway.delete();
	}

	/**
	 * finds all powertools which exist in the database
	 * @return an arraylist of all powertools which exist in the database
	 */
	public ArrayList<PowerTool> getPowerTools() throws DatabaseException 
	{
		ArrayList<PowerTool> powerTools = new ArrayList<PowerTool>();
		ArrayList<PowerToolToStripNailDTO> dtos = PowerToolsToStripNailsGateway.findPowerToolByStripNailID(stripNailGateway.getInventoryItemID());
		dtos.forEach(dto -> {
			try {
				powerTools.add(new PowerToolMapper(dto.getPowerToolId()).getPowerTool());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		});
		
		return powerTools;
	}

	
	/**
	 * returns the instance of stripnail stored as an instance variable
	 */
	public StripNail getStripNail() 
	{
		return stripNail;
	}

	/**
	 * @return the ID of stripnail
	 */
	public int getStripNailID() 
	{
		return stripNailGateway.getInventoryItemID();
	}

	public void addPowerTool(PowerTool powerTool) throws DatabaseException
	{
		PowerToolsToStripNailsGateway pt2sng = new PowerToolsToStripNailsGateway(powerTool.getID(), stripNail.getID());
		stripNail.getPowerToolList().update();
	}
	
	protected void buildStripNail() {
		stripNail = new StripNail(stripNailGateway.getInventoryItemID(), stripNailGateway.getUPC(), stripNailGateway.getManufacturerID(), 
									stripNailGateway.getPrice(), stripNailGateway.getLength(), stripNailGateway.getNumberInStrip());		
	}
}
