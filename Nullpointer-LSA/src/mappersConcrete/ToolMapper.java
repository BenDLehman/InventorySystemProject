package mappersConcrete;

import java.util.ArrayList;
import datasource.ToolDTO;
import datasourceConcrete.ToolsGateway;
import exception.DatabaseException;
import sharedDomain.Tool;
import sharedDomain.ToolAbstract;

/**
 * Tool Data Mapper
 * 
 * @author clabaugh10
 *
 */
public class ToolMapper extends ToolAbstract {

	private ToolsGateway toolGateway;
	private Tool tool;

	/**
	 * Finder Constructor
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public ToolMapper(int toolID) throws DatabaseException {
		this.toolGateway = new ToolsGateway(toolID);
		buildTool();
	}

	/**
	 * Creation Constructor
	 * 
	 * @throws DatabaseException
	 */
	public ToolMapper(String upc, int manufacturerID, int price, String description) throws DatabaseException {
		this.toolGateway = new ToolsGateway(upc, manufacturerID, price, description);
		buildTool();
	}

	/**
	 * 
	 * @return ArrayList of Tool objects
	 * @throws DatabaseException
	 */
	public static ArrayList<Tool> findAll() throws DatabaseException {
		ArrayList<Tool> tools = new ArrayList<Tool>();
		ArrayList<ToolDTO> dtos = ToolsGateway.findAll();

		for (int i = 0; i < dtos.size(); i++) {
			tools.add(new Tool(dtos.get(i).getId(), dtos.get(i).getUpc(), dtos.get(i).getManufacturerID(),
					dtos.get(i).getPrice(), dtos.get(i).getDescription()));
		}

		return tools;
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	@Override
	public void persist() throws DatabaseException {
		toolGateway.setUpc(tool.getUpc());
		toolGateway.setManufacturerID(tool.getManufacturerID());
		toolGateway.setPrice(tool.getPrice());
		toolGateway.setDescription(tool.getDescription());
		toolGateway.persist();
	}

	/**
	 * 
	 * @throws DatabaseException
	 */
	@Override
	public void delete() throws DatabaseException {
		toolGateway.delete();
	}

	public Tool getTool() {
		return tool;
	}

	@Override
	protected void buildTool() {
		tool = new Tool(toolGateway.getID(), toolGateway.getUpc(), toolGateway.getManufacturerID(),
				toolGateway.getPrice(), toolGateway.getDescription());

	}

	@Override
	public int getToolID() {
		// TODO Auto-generated method stub
		return toolGateway.getID();
	}

}
