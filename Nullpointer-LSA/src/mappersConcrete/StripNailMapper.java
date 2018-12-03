package mappersConcrete;

import java.util.ArrayList;

import datasource.PowerToolToStripNailDTO;
import datasource.StripNailDTO;
import datasourceConcrete.PowerToolsToStripNailsGateway;
import datasourceConcrete.StripNailsGateway;
import exception.DatabaseException;
import mappersConcrete.PowerToolMapper;
import sharedDomain.PowerTool;
import sharedDomain.StripNail;
import sharedDomain.StripNailAbstract;

/**
 * StripNail Mapper
 * 
 * @author clabaugh10
 *
 */
public class StripNailMapper extends StripNailAbstract {

	private StripNailsGateway stripNailGateway;
	private StripNail stripNail;

	/**
	 * Finder Constructor
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public StripNailMapper(int stripNailID) throws DatabaseException 
	{
		this.stripNailGateway = new StripNailsGateway(stripNailID);
		buildStripNail();
	}

	/**
	 * Creation Constructor
	 * 
	 * @throws DatabaseException
	 */
	public StripNailMapper(String upc, int manufacturerID, int price, double length, int numberInStrip) throws DatabaseException 
	{
		this.stripNailGateway = new StripNailsGateway(upc, manufacturerID, price, length, numberInStrip);
		buildStripNail();
	}

	/**
	 * 
	 * @return ArrayList of Tool objects
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNail> findAll() throws DatabaseException {
		ArrayList<StripNail> stripNails = new ArrayList<StripNail>();
		ArrayList<StripNailDTO> dtos = StripNailsGateway.findAll();

		for (int i = 0; i < dtos.size(); i++) {
			stripNails.add(new StripNail(dtos.get(i).getId(), dtos.get(i).getUpc(), dtos.get(i).getManufacturerID(), dtos.get(i).getPrice(),
					dtos.get(i).getLength(), dtos.get(i).getNumberInStrip()));
		}

		return stripNails;
	}


	/**
	 * 
	 * @throws DatabaseException
	 */
	@Override
	public void persist() throws DatabaseException {
		stripNailGateway.setUpc(stripNail.getUpc());
		stripNailGateway.setManufacturerID(stripNail.getManufacturerID());
		stripNailGateway.setPrice(stripNail.getPrice());
		stripNailGateway.setLength(stripNail.getLength());
		stripNailGateway.setNumberInStrip(stripNail.getNumberInStrip());
		stripNailGateway.persist();
	}


	public ArrayList<PowerTool> getPowerTools() throws DatabaseException 
	{
		ArrayList<PowerTool> powerTools = new ArrayList<PowerTool>();
		ArrayList<PowerToolToStripNailDTO> dtos = PowerToolsToStripNailsGateway.findPowerToolByStripNailID(stripNailGateway.getID());
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
	 * 
	 * @throws DatabaseException
	 */
	public void delete() throws DatabaseException 
	{
		stripNailGateway.delete();
	}

	public StripNail getStripNail() 
	{
		return stripNail;
	}

	public int getStripNailID() 
	{
		return stripNail.getID();
	}

	@Override
	public void buildStripNail() {
		stripNail = new StripNail(stripNailGateway.getID(), stripNailGateway.getUpc(), stripNailGateway.getManufacturerID(), stripNailGateway.getPrice(), stripNailGateway.getLength(), stripNailGateway.getNumberInStrip());
	}
	
	public void addPowerTool(PowerTool powerTool) throws DatabaseException
	{
		PowerToolsToStripNailsGateway pt2sng = new PowerToolsToStripNailsGateway(powerTool.getID(), stripNail.getID());
		stripNail.getPowerToolList().update();
	}
}
