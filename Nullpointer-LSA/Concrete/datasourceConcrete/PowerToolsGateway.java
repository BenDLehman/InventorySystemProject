package datasourceConcrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolDTO;
import exception.DatabaseException;

public class PowerToolsGateway {

	/************************************************/
	/** 			Static Methods 				   **/
	/************************************************/

	// Finds rows in the table that contain the given UPC and returns an ArrayList
	// of DTOs that
	// contain the data for the rows
	public static ArrayList<PowerToolDTO> findByUpc(String upc) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
			String query = "SELECT * FROM PowerTools WHERE upc = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setString(1, upc);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				PowerToolDTO ptDTO = new PowerToolDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getString("description"),
						results.getBoolean("batteryPowered"));
				powerTools.add(ptDTO);
			}

			return powerTools;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds rows in the table that contain the given manufacturerID and returns an
	// ArrayList of DTOs
	// that contain the data for the rows
	public static ArrayList<PowerToolDTO> findByManufacturerID(int manID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
			String query = "SELECT * FROM PowerTools WHERE manufacturerID = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);

			stmnt.setInt(1, manID);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				PowerToolDTO ptDTO = new PowerToolDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getString("description"),
						results.getBoolean("batteryPowered"));
				powerTools.add(ptDTO);
			}
			return powerTools;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds all the rows in the table and returns an ArrayList of DTOs that contain
	// the data for the rows
	public static ArrayList<PowerToolDTO> findAll() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		int ID;
		String upc;
		int manufacturerID;
		int price;
		String description;
		boolean batteryPowered;
		int count = 0;

		try {
			ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
			String query = "SELECT * FROM PowerTools";

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
				batteryPowered = results.getBoolean("batteryPowered");

				PowerToolDTO ptDTO = new PowerToolDTO(ID, upc, manufacturerID, price, description, batteryPowered);
				powerTools.add(ptDTO);
				count++;

				System.out.println("Retrieved tool number: " + count + "\nID:" + ID + ", MANUFACTURERID: "
						+ manufacturerID + ", PRICE: " + price + ", DESCRIPTION: " + description + ", BATTERYPOWERED:"
						+ batteryPowered);
			}
			return powerTools;

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
	private boolean batteryPowered;

	/************************************************/
	/* CONSTRUCTORS */
	/************************************************/

	// Creates a gateway that takes in all the columns. Inserts those columns into
	// the database table as a new row.
	// Sets the gateway variables equal to the variables
	public PowerToolsGateway(String upc, int manufacturerID, int price, String description, boolean batteryPowered)
			throws DatabaseException {
		insert(upc, manufacturerID, price, description, batteryPowered);
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
		this.batteryPowered = batteryPowered;

	}

	// Creates a gateway by finding an existing row in the table that matches the
	// given ID and setting the instance variables equal to the row
	public PowerToolsGateway(int ID) throws DatabaseException {
		boolean ResultExists = false;
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "SELECT upc,manufacturerID,price,description,batteryPowered FROM PowerTools WHERE ID =? ";
		try {

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setInt(1, ID);
			ResultSet rs = stmnt.executeQuery();
			setID(ID);

			//// NOT SURE WE NEED THE WHILE LOOP
			while (rs.next()) {
				setUpc(rs.getString("upc"));
				setManufacturerID(rs.getInt("manufacturerID"));
				setPrice(rs.getInt("price"));
				setDescription(rs.getString("description"));
				setBatteryPowered(rs.getBoolean("batteryPowered"));

				if (ResultExists == false)
					ResultExists = true;
			}
			if (ResultExists != true) {  
				throw new DatabaseException("POWERTOOL ITEM WITH ID: " + ID + " WAS NOT FOUND.");
			}

		} catch (SQLException e) {

		}
	}

	/************************************************/
	/* GETTERS/SETTERS */
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

	public boolean isBatteryPowered() {
		return batteryPowered;
	}

	public void setBatteryPowered(boolean batteryPowered) {
		this.batteryPowered = batteryPowered;
	}

	/**
	 * Updates the row of data.
	 * 
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String updateQuery = "UPDATE PowerTools SET upc = ?, manufacturerID = ?, price = ?, description = ?, batteryPowered = ? WHERE ID = ?";

		try {
			PreparedStatement updateStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(updateQuery);
			updateStatement.setString(1, getUpc());
			updateStatement.setInt(2, getManufacturerID());
			updateStatement.setInt(3, getPrice());
			updateStatement.setString(4, getDescription());
			updateStatement.setBoolean(5, isBatteryPowered());
			updateStatement.setInt(6, getID());
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Tables have not been created");
		}
	}

	/**
	 * Delete a powertool row by the id given.
	 * 
	 * @param id
	 *            - id to delete by
	 * 
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String deletePowerToolSQL = "DELETE FROM PowerTools WHERE ID=?";
		String deleteToolSQL = "DELETE FROM Tools WHERE ID = ?";
		String deleteRelationSQL = "DELETE FROM PowerToolsToStripNails WHERE powerToolID = ?";

		boolean wasDeleted = false;

		try {
			PreparedStatement deletePowerToolStmnt = dbm.getConnection(dbTables.CONCRETE)
					.prepareStatement(deletePowerToolSQL);
			deletePowerToolStmnt.setInt(1, ID);
			int changedRows = deletePowerToolStmnt.executeUpdate();

			PreparedStatement deleteToolStmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(deleteToolSQL);
			deleteToolStmnt.setInt(1, ID);
			deleteToolStmnt.executeUpdate();
			
			PreparedStatement deleteRelationStmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(deleteRelationSQL);
			deleteRelationStmnt.setInt(1, ID);
			deleteRelationStmnt.executeUpdate();

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
	private void insert(String upc, int manufacturerID, int price, String description, boolean batteryPowered)
			throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String sql = "INSERT INTO PowerTools VALUES(?,?,?,?,?,?)";
		int nextID = LastIDGateway.getNextID();
		setID(nextID);

		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(sql);
			stmnt.setInt(1, nextID);
			stmnt.setString(2, upc);
			stmnt.setInt(3, manufacturerID);
			stmnt.setInt(4, price);
			stmnt.setString(5, description);
			stmnt.setBoolean(6, batteryPowered);
			stmnt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
