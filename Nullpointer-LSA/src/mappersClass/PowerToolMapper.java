package mappersClass;

import java.util.ArrayList;

import datasource.PowerToolDTO;
import datasource.PowerToolToStripNailDTO;
import datasourceClass.PowerToolInventoryItemsGateway;
import datasourceClass.PowerToolsToStripNailsGateway;
import exception.DatabaseException;
import identityMappers.PowerToolIdentityMap;
import sharedDomain.PowerTool;
import sharedDomain.PowerToolAbstract;
import sharedDomain.StripNail;

public class PowerToolMapper extends PowerToolAbstract {
	private PowerToolInventoryItemsGateway powerToolGateway;
	protected PowerTool powerTool;

	/**
	 * Find Constructor
	 * 
	 * @param inventoryItemID
	 * @throws DatabaseException
	 */
	public PowerToolMapper(int powerToolID) throws DatabaseException {
		this.powerToolGateway = new PowerToolInventoryItemsGateway(powerToolID);
		this.buildPowerTool();
		
	}
	
	/**
	 * Create Constructor
	 * 
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param description
	 * @param batteryPowered
	 * @throws DatabaseException
	 */
	public PowerToolMapper(String upc, int manufacturerID, int price, String description, boolean batteryPowered)
			throws DatabaseException {
		this.powerToolGateway = new PowerToolInventoryItemsGateway(upc, manufacturerID, price, description, batteryPowered);
		this.buildPowerTool();
	}

	
	/**
	 * Finds all powertools that exist in the system and adds them to an
	 * arraylist
	 * @return an array list of all powertools that exist in the database
	 * @throws DatabaseException
	 */
	public static ArrayList<PowerTool> findAll() throws DatabaseException {
		ArrayList<PowerTool> powerTools = new ArrayList<PowerTool>();
		ArrayList<PowerToolDTO> dtos = PowerToolInventoryItemsGateway.findAll();

		for (int i = 0; i < dtos.size(); i++) {
			powerTools.add(new PowerTool(dtos.get(i).getId(), dtos.get(i).getUpc(), dtos.get(i).getManufacturerID(), dtos.get(i).getPrice(),
					dtos.get(i).getDescription(), dtos.get(i).isBatteryPowered()));
		}

		return powerTools;
	}

	

	/**
	 * Saves changes made to the object to the database
	 */
	public void persist() throws DatabaseException {
		powerToolGateway.setUPC(powerTool.getUpc());
		powerToolGateway.setManufacturerID(powerTool.getManufacturerID());
		powerToolGateway.setPrice(powerTool.getPrice());
		powerToolGateway.setDescription(powerTool.getDescription());
		powerToolGateway.setBatteryPower(powerTool.isBatteryPowered());
		powerToolGateway.persist();
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void delete() throws DatabaseException {
		powerToolGateway.delete();
	}

	/**
	 * Deletes a the powertool from the database
	 * 
	 * @throws DatabaseException
	 */
	public ArrayList<StripNail> getStripNails() throws DatabaseException 
	{
		ArrayList<StripNail> stripNails = new ArrayList<StripNail>();
		
		ArrayList<PowerToolToStripNailDTO> dtos = PowerToolsToStripNailsGateway.findStripNailByPowerToolID(powerToolGateway.getInventoryItemID());
		dtos.forEach(dto -> {
			try {
				stripNails.add(new StripNailMapper(dto.getStripNailId()).getStripNail());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		});
		
		return stripNails;
	}

	
	/**
	 * @return the ID of the powertool
	 */
public int getPowerToolID()
{
	return powerToolGateway.getInventoryItemID();
}
	
/**
 * Returns the instance of PowerTool stored as an instance variable
 */
public PowerTool getPowerTool() 
{
	return powerTool;
}


public void addStripNail(StripNail stripNail) throws DatabaseException 
{
	PowerToolsToStripNailsGateway pt2sng = new PowerToolsToStripNailsGateway(powerTool.getID(), stripNail.getID());
	powerTool.getStripNailList().update();
}

/**
 * constructs the instance of powertool held by the class as an instance variable
 */
@Override
protected void buildPowerTool() 
{
	powerTool = new PowerTool(powerToolGateway.getInventoryItemID(), powerToolGateway.getUPC(), powerToolGateway.getManufacturerID(),
								powerToolGateway.getPrice(), powerToolGateway.getDescription(), powerToolGateway.getBatteryPowered());
	
}



}
