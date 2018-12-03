package mappersClass;

import java.util.ArrayList;

import datasource.ToolDTO;
import datasourceClass.ToolInventoryItemsGateway;
import exception.DatabaseException;
import identityMappers.ToolIdentityMap;
import sharedDomain.Tool;
import sharedDomain.ToolAbstract;

public class ToolMapper extends ToolAbstract {

	private ToolInventoryItemsGateway toolGateway;
	protected Tool tool;
	/**
	 * Create Constructor
	 * @param upc the upc of the tool
	 * @param manufacturerID the manufacturer ID of the tool
	 * @param price the price of the tool
	 * @param description the description of the tool
	 * @throws Exception
	 */
	protected ToolMapper(String upc, int manufacturerID, int price, String description) throws DatabaseException
	{
		this.toolGateway = new ToolInventoryItemsGateway(upc, manufacturerID, price, description);
		this.buildTool();
	}
		
	
	/**
	 * Find constructor
	 * @param toolID the ID of the tool in the database
	 * @throws Exception
	 */
	protected ToolMapper(int toolID) throws DatabaseException
	{
		this.toolGateway = new ToolInventoryItemsGateway(toolID);
		this.buildTool();
	}
	
	
	/**
	 * Finds all tools that exist in the database
	 * @return An array list that contains instances of all tools held in the database
	 * @throws DatabaseException
	 */
	 public static ArrayList<Tool> findAll() throws DatabaseException
	{
		ArrayList<Tool> tools = new ArrayList<Tool>();
		ArrayList<ToolDTO> toolDtos = ToolInventoryItemsGateway.findAll();
		
		for(int i=0; i<toolDtos.size(); i++)
		{
			tools.add(new Tool(toolDtos.get(i).getId(),toolDtos.get(i).getUpc(), toolDtos.get(i).getManufacturerID(), toolDtos.get(i).getPrice(), toolDtos.get(i).getDescription()));
		}
		
		return tools;
	}
	 
	 
		/**
		 * Saves changes made to the tool to the database
		 */
		
     @Override
	public void persist() throws DatabaseException {
    	toolGateway.setUPC(tool.getUpc());
 		toolGateway.setManufacturerID(tool.getManufacturerID());
 		toolGateway.setPrice(tool.getPrice());
 		toolGateway.setDescription(tool.getDescription());
 		toolGateway.persist();

		
	}

     /**
 	 * Deletes a the tool from the database
 	 */
	@Override
	public void delete() throws DatabaseException {
		toolGateway.delete();
	}

	/**
	 * @return the instance of tool stored by the class as an instance variable
	 */
	@Override
	public Tool getTool() {
		return tool;
	}

	/**
	 * constructs and the instance of tool stored by the class as an instance variable
	 */
	@Override
	protected void buildTool() 
	{
		tool = new Tool(toolGateway.getInventoryItemID(),toolGateway.getUPC(), toolGateway.getManufacturerID(), toolGateway.getPrice(), toolGateway.getDescription());
			
	}

	/**
	 * @return the ID of the tool
	 */
	@Override
	public int getToolID() {
		return tool.getID();
	}
}
