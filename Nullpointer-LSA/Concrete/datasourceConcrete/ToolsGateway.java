package datasourceConcrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.ToolDTO;
import exception.DatabaseException;

public class ToolsGateway {

	/************************************************/
	/* Static Methods */
	/**
	 * @throws DatabaseException
	 **********************************************/

	// Finds rows in the table that contain the given UPC and returns an ArrayList
	// of DTOs that
	// contain the data for the rows
	public static ArrayList<ToolDTO> findByUpc(String upc) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();
			String query = "SELECT * FROM Tools WHERE upc = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setString(1, upc);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				ToolDTO toolDTO = new ToolDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getString("description"));
				tools.add(toolDTO);
			}

			return tools;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds rows in the table that contain the given manufacturerID and returns an
	// ArrayList of DTOs
	// that contain the data for the rows
	public static ArrayList<ToolDTO> findByManufacturerID(int manID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();
			String query = "SELECT * FROM Tools WHERE manufacturerID = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);

			stmnt.setInt(1, manID);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				ToolDTO toolDTO = new ToolDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getString("description"));
				tools.add(toolDTO);
			}
			return tools;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds all the rows in the table and returns an ArrayList of DTOs that contain
	// the data for the rows
	public static ArrayList<ToolDTO> findAll() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		int ID;
		String upc;
		int manufacturerID;
		int price;
		String description;

		try {
			ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();
			String query = "SELECT * FROM Tools";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				// Populate instance variables with values from result set
				ID = results.getInt("ID");
				upc = results.getString("upc");
				manufacturerID = results.getInt("manufacturerID");
				price = results.getInt("price");
				description = results.getString("description");

				ToolDTO toolDTO = new ToolDTO(ID, upc, manufacturerID, price, description);
				tools.add(toolDTO);

				// System.out.println("Retrieved tool number: "+count+"\nID:"+ID+",
				// MANUFACTURERID: "+manufacturerID+
				// ", PRICE: "+price+", DESCRIPTION: "+description);
			}
			return tools;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/************************************************/
	/* INSTANCE VARIABLES */
	/************************************************/

	private int ID;
	private String upc;
	private int manufacturerID;
	private int price;
	private String description;

	/************************************************/
	/* CONSTRUCTORS */
	/************************************************/

	// Creates a gateway that takes in all the columns. Inserts those columns into
	// the database table as a new row.
	// Sets the gateway variables equal to the variables
	public ToolsGateway(String upc, int manufacturerID, int price, String description) throws DatabaseException {
		insert(upc, manufacturerID, price, description);
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
	}

	// Creates a gateway by finding an existing row in the table that matches the
	// given ID and setting the instance variables equal to the row
	public ToolsGateway(int ID) throws DatabaseException {
		boolean ResultExists = false;
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "SELECT upc,manufacturerID,price,description FROM Tools WHERE ID =? ";
		try {

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setInt(1, ID);
			ResultSet rs = stmnt.executeQuery();
			setID(ID);

			while (rs.next()) {
				setUpc(rs.getString("upc"));
				setManufacturerID(rs.getInt("manufacturerID"));
				setPrice(rs.getInt("price"));
				setDescription(rs.getString("description"));

				if (ResultExists == false)
					ResultExists = true;

			}

			if (ResultExists != true) {
				throw new DatabaseException("POWERTOOL ITEM WITH ID: " + ID + " WAS NOT FOUND.");
			}

		} catch (SQLException e) 
		{
			
		}
	}

	/************************************************/
	/* GETTER/SETTERS */
	/************************************************/

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Updates the row of data.
	 * 
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String updateQuery = "UPDATE Tools SET upc = ?, manufacturerID = ?, price = ?, description = ? WHERE ID = ?";

		try {
			PreparedStatement updateStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(updateQuery);
			updateStatement.setString(1, getUpc());
			updateStatement.setInt(2, getManufacturerID());
			updateStatement.setInt(3, getPrice());
			updateStatement.setString(4, getDescription());
			updateStatement.setInt(5, getID());
			updateStatement.executeUpdate();

			PowerToolsGateway powerToolsGateway = new PowerToolsGateway(getID()); 
			if (powerToolsGateway.getUpc() != null) {
				persistPowerTool();
			}

		} catch (SQLException | DatabaseException e) {
			
		}
	}

	/**
	 * Delete a tool row by the id given.
	 * 
	 * @param id
	 *            - id to delete by
	 * 
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String deleteQuery = "DELETE from Tools WHERE id=?";
		boolean wasDeleted = false;

		try {
			PreparedStatement deleteStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(deleteQuery);
			deleteStatement.setInt(1, ID);
			int changedRows = deleteStatement.executeUpdate();
			LastIDGateway.deleteID(ID);

			if (changedRows == 1)
				wasDeleted = true;

		} catch (SQLException | DatabaseException e) {
			System.out.println("Returned False for: deletebyID");
		}
		return wasDeleted;
	}

	/************************************************/
	/* PRIVATE METHODS */
	/************************************************/

	// Insert method, used by the create constructor.
	private void insert(String upc, int manufacturerID, int price, String description) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String insertStatementString = "INSERT INTO Tools VALUES(?,?,?,?,?)";
		int nextID = LastIDGateway.getNextID();
		setID(nextID);
		try {
			PreparedStatement insertStatement = dbm.getConnection(dbTables.CONCRETE)
					.prepareStatement(insertStatementString);
			insertStatement.setInt(1, nextID);
			insertStatement.setString(2, upc);
			insertStatement.setInt(3, manufacturerID);
			insertStatement.setInt(4, price);
			insertStatement.setString(5, description);
			insertStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Persist method for persisting a powertool along with the tool
	private void persistPowerTool() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String updateQuery = "UPDATE PowerTools SET upc = ?, manufacturerID = ?, price = ?, description = ?, batteryPowered = ? WHERE ID = ?";

		PowerToolsGateway powerToolsGateway = new PowerToolsGateway(getID());
		try {
			PreparedStatement updateStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(updateQuery);
			updateStatement.setString(1, getUpc());
			updateStatement.setInt(2, getManufacturerID());
			updateStatement.setInt(3, getPrice());
			updateStatement.setString(4, getDescription());
			updateStatement.setBoolean(5, powerToolsGateway.isBatteryPowered());
			updateStatement.setInt(6, getID());
			updateStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
