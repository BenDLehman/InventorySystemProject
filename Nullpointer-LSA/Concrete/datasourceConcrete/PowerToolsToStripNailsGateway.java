package datasourceConcrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolToStripNailDTO;
import exception.DatabaseException;


public class PowerToolsToStripNailsGateway {

	/************************************************/
	/* STATIC METHODS */
	/************************************************/

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	// Finds all the rows in the table and returns an ArrayList of DTOs that contain
	// the data for the rows
	public static ArrayList<PowerToolToStripNailDTO> findAll() throws DatabaseException {
		ArrayList<PowerToolToStripNailDTO> resultList = new ArrayList<PowerToolToStripNailDTO>();
		String query = "Select * from PowerToolsToStripNails;";
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			ResultSet results = stmnt.executeQuery();

			int powerToolID;
			int stripNailID;

			while (results.next()) {
				powerToolID = results.getInt("powerToolID");
				stripNailID = results.getInt("stripNailID"); 

				PowerToolToStripNailDTO relation = new PowerToolToStripNailDTO(powerToolID, stripNailID);
				System.out.println("POWERTOOL ID: " + powerToolID + " <---> STRIPNAIL ID: " + stripNailID);
				resultList.add(relation);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("Entry could not be found");
		}

		return resultList;
	}

	/**
	 * 
	 * @param StripNailID
	 * @return
	 * @throws DatabaseException
	 */

	// Finds the rows according to the Strip Nail ID given. Returns an ArrayList of
	// those rows
	public static ArrayList<PowerToolToStripNailDTO> findPowerToolByStripNailID(int StripNailID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "SELECT PowerToolID FROM PowerToolsToStripNails WHERE StripNailID = ?";
		ArrayList<PowerToolToStripNailDTO> relations = new ArrayList<PowerToolToStripNailDTO>();

		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setInt(1, StripNailID);
			ResultSet results = stmnt.executeQuery();

			System.out.println("POWERTOOLS WITH STRIPNAIL ID = " + StripNailID + ":");

			int powerToolID;

			while (results.next()) {
				powerToolID = results.getInt("PowerToolID");
				PowerToolToStripNailDTO relationDTO = new PowerToolToStripNailDTO(powerToolID, StripNailID);
				relations.add(relationDTO);
				System.out.println("POWERTOOL ID: " + powerToolID);
			}

		} catch (SQLException e) {
			throw new DatabaseException("Entry could not be found");

		}
		return relations;
	}

	/**
	 * 
	 * @param powerToolID
	 * @return
	 * @throws DatabaseException
	 */

	// Finds the rows according to the Power Tools ID given. Returns an ArrayList of
	// those rows
	public static ArrayList<PowerToolToStripNailDTO> findStripNailByPowerToolID(int powerToolID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "SELECT StripNailID FROM PowerToolsToStripNails WHERE PowerToolID = ?";
		ArrayList<PowerToolToStripNailDTO> relations = new ArrayList<PowerToolToStripNailDTO>();

		System.out.println("STRIPNAILS WITH POWERTOOL ID = " + powerToolID + ": ");

		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setInt(1, powerToolID);
			ResultSet results = stmnt.executeQuery();

			int stripNailID;

			while (results.next()) {
				stripNailID = results.getInt("StripNailID");
				PowerToolToStripNailDTO relationDTO = new PowerToolToStripNailDTO(powerToolID, stripNailID);
				relations.add(relationDTO);
				System.out.println("STRIPNAIL ID: " + stripNailID);
			}

		} catch (SQLException e) {
			throw new DatabaseException("Entry could not be found");
		}
		return relations;

	}

	int ID;
	int powerToolID;
	int stripNailID;

	/************************************************/
	/* CONSTRUCTORS */
	/************************************************/

	// Creates a gateway that takes in all the columns. Inserts those columns into
	// the database table as a new row.
	// Sets the gateway variables equal to the variables
	public PowerToolsToStripNailsGateway(int powerToolID, int stripNailID) throws DatabaseException {
		insert(powerToolID, stripNailID);
		this.powerToolID = powerToolID;
		this.stripNailID = stripNailID;
	}

	// Creates a gateway by finding an existing row in the table that matches the
	// given ID and setting the instance variables equal to the row
	public PowerToolsToStripNailsGateway(int ID) throws DatabaseException {
		boolean ResultExists = false;
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "SELECT * FROM PowerToolsToStripNails WHERE ID =?";

		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setInt(1, ID);
			ResultSet rs = stmnt.executeQuery();
			while (rs.next()) {
				setPowerToolID(rs.getInt("powerToolID"));
				setStripNailID(rs.getInt("stripNailID"));
			}

			setID(ID);

			if (ResultExists == false)
				ResultExists = true;

			if (ResultExists != true) {
				throw new DatabaseException("POWERTOOL ITEM WITH ID: " + ID + " WAS NOT FOUND.");
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * Updates the row of data.
	 * 
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String updateQuery = "UPDATE PowerToolsToStripNails SET powerToolID = ? WHERE stripNailID = ?";

		try {
			PreparedStatement updateStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(updateQuery);
			updateStatement.setInt(1, getPowerToolID());
			updateStatement.setInt(2, getStripNailID());
			updateStatement.execute();

			System.out
					.println("UPDATED ENTRY:  powerToolID: " + getPowerToolID() + ", stripNailID: " + getStripNailID());

		} catch (SQLException e) {
			throw new DatabaseException("Tables have not been created");
		}
	}

	/**
	 * Delete a PowerTooltoStripNail row by the id given.
	 * 
	 * @param id
	 *            - id to delete by
	 * 
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String deleteQuery = "DELETE from PowerToolsToStripNails WHERE powerToolID =? AND stripNailID=?";

		boolean wasDeleted = false;

		try {
			PreparedStatement deleteStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(deleteQuery);
			deleteStatement.setInt(1, powerToolID);
			deleteStatement.setInt(2, stripNailID);
			int changedRows = deleteStatement.executeUpdate();

			if (changedRows == 1)
				wasDeleted = true;

		} catch (SQLException | DatabaseException e) {
			System.out.println("Returned False for: deletebyID");
		}
		return wasDeleted;
	}

	/************************************************/
	/* GETTER/SETTERS */
	/************************************************/

	public int getPowerToolID() {
		return powerToolID;
	}

	public void setPowerToolID(int powerToolID) {
		this.powerToolID = powerToolID;
	}

	public int getStripNailID() {
		return stripNailID;
	}

	public void setStripNailID(int stripNailID) {
		this.stripNailID = stripNailID;
	}

	public int getID() {
		return ID;
	}

	private void setID(int iD) {
		ID = iD;
	}

	// Insert method, used by the create constructor.
	private void insert(int powerToolID, int stripNailID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String insertStatementString = "INSERT INTO PowerToolsToStripNails (powerToolID, stripNailID) VALUES(?,?)";
		try {
			PreparedStatement insertStatement = dbm.getConnection(dbTables.CONCRETE)
					.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, powerToolID);
			insertStatement.setInt(2, stripNailID);
			insertStatement.execute();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				setID(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
