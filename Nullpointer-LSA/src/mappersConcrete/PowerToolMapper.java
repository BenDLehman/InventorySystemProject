package mappersConcrete;

import java.util.ArrayList;
import datasource.PowerToolDTO;
import datasource.PowerToolToStripNailDTO;
import datasourceConcrete.PowerToolsToStripNailsGateway;
import datasourceConcrete.PowerToolsGateway;
import exception.DatabaseException;
import mappersConcrete.StripNailMapper;
import sharedDomain.PowerTool;
import sharedDomain.PowerToolAbstract;
import sharedDomain.StripNail;

/**
 * Tool Data Mapper
 * 
 * @author clabaugh10
 *
 */
public class PowerToolMapper extends PowerToolAbstract {

	private PowerToolsGateway powerToolGateway;
	private PowerTool powerTool;

	/**
	 * Finder Constructor
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public PowerToolMapper(int powerToolID) throws DatabaseException {
		this.powerToolGateway = new PowerToolsGateway(powerToolID);
		buildPowerTool();
		
	}

	/**
	 * Creation Constructor
	 * 
	 * @throws DatabaseException
	 */
	public PowerToolMapper(String upc, int manufacturerID, int price, String description, boolean batteryPowered)
			throws DatabaseException {
		this.powerToolGateway = new PowerToolsGateway(upc, manufacturerID, price, description, batteryPowered);
		buildPowerTool();
	}

	/**
	 * 
	 * @return ArrayList of Tool objects
	 * @throws DatabaseException
	 */
	public static ArrayList<PowerTool> findAll() throws DatabaseException {
		ArrayList<PowerTool> powerTools = new ArrayList<PowerTool>();
		ArrayList<PowerToolDTO> dtos = PowerToolsGateway.findAll();

		for (int i = 0; i < dtos.size(); i++) {
			powerTools.add(new PowerTool(dtos.get(i).getId(), dtos.get(i).getUpc(), dtos.get(i).getManufacturerID(), dtos.get(i).getPrice(),
					dtos.get(i).getDescription(), dtos.get(i).isBatteryPowered()));
		}

		return powerTools;
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException {
		powerToolGateway.setUpc(powerTool.getUpc());
		powerToolGateway.setManufacturerID(powerTool.getManufacturerID());
		powerToolGateway.setPrice(powerTool.getPrice());
		powerToolGateway.setDescription(powerTool.getDescription());
		powerToolGateway.setBatteryPowered(powerTool.isBatteryPowered());
		powerToolGateway.persist();
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	public void delete() throws DatabaseException {
		powerToolGateway.delete();
	}

	public PowerTool getPowerTool() 
	{
		return powerTool;
	}

	public ArrayList<StripNail> getStripNails() throws DatabaseException 
	{
		ArrayList<StripNail> stripNails = new ArrayList<StripNail>();
		
		ArrayList<PowerToolToStripNailDTO> dtos = PowerToolsToStripNailsGateway.findStripNailByPowerToolID(powerToolGateway.getID());
		dtos.forEach(dto -> {
			try {
				stripNails.add(new StripNailMapper(dto.getStripNailId()).getStripNail());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		});
		
		return stripNails;
	}

	public int getPowerToolID() 
	{
		return powerTool.getID();
	}

	@Override
	protected void buildPowerTool() {
		powerTool = new PowerTool(powerToolGateway.getID(), powerToolGateway.getUpc(), powerToolGateway.getManufacturerID(), powerToolGateway.getPrice(), powerToolGateway.getDescription(), powerToolGateway.isBatteryPowered());
		
	}
	
	public void addStripNail(StripNail stripNail) throws DatabaseException
	{
		PowerToolsToStripNailsGateway pt2sng = new PowerToolsToStripNailsGateway(powerTool.getID(), stripNail.getID());
		powerTool.getStripNailList().update();
	}
}
