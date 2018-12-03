package datasourceConcrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.NailDTO;
import exception.DatabaseException;

public class NailsGateway {

	/***********************************************/
	/* STATIC METHODS */
	/***********************************************/

	// Finds rows in the table that contain the given UPC and returns an ArrayList
	// of DTOs that
	// contain the data for the rows
	public static ArrayList<NailDTO> findByUpc(String upc) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<NailDTO> nails = new ArrayList<NailDTO>();
			String query = "SELECT * FROM Nails WHERE upc = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.setString(1, upc);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				NailDTO nailDTO = new NailDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getInt("price"),
						results.getInt("numberInBox"));
				nails.add(nailDTO);
			}

			return nails;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds rows in the table that contain the given manufacturerID and returns an
	// ArrayList of DTOs
	// that contain the data for the rows
	public static ArrayList<NailDTO> findByManufacturerID(int manID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		try {
			ArrayList<NailDTO> nails = new ArrayList<NailDTO>();
			String query = "SELECT * FROM Nails WHERE manufacturerID = ?";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);

			stmnt.setInt(1, manID);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				NailDTO nailDTO = new NailDTO(results.getInt("ID"), results.getString("upc"),
						results.getInt("manufacturerID"), results.getInt("price"), results.getInt("price"),
						results.getInt("numberInBox"));
				nails.add(nailDTO);
			}
			return nails;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Finds all the rows in the table and returns an ArrayList of DTOs that contain
	// the data for the rows
	public static ArrayList<NailDTO> findAll() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		int ID;
		String upc;
		int manufacturerID;
		int price;
		int length;
		int numberInBox;
		int count = 0;

		try {
			ArrayList<NailDTO> nails = new ArrayList<NailDTO>();
			String query = "SELECT * FROM Nails";

			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			ResultSet results = stmnt.executeQuery();

			// Loop that fills an arraylist with a DTO for every row in the table
			while (results.next()) {
				// Populate instance variables with values from result set
				ID = results.getInt("ID");
				upc = results.getString("upc");
				manufacturerID = results.getInt("manufacturerID");
				price = results.getInt("price");
				length = results.getInt("length");
				numberInBox = results.getInt("numberInBox");

				NailDTO nailDTO = new NailDTO(ID, upc, manufacturerID, price, length, numberInBox);
				nails.add(nailDTO);
				count++;

				System.out.println(
						"Retrieved nail number: " + count + "\nID:" + ID + ", MANUFACTURERID: " + manufacturerID
								+ ", PRICE: " + price + ", LENGTH: " + length + ", NUMBER IN BOX:" + numberInBox);
			}
			return nails;

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
	private double length;
	private int numberInBox;

	/************************************************/
	/* CONSTRUCTORS */
	/************************************************/

	// Creates a gateway that takes in all the columns. Inserts those columns into
	// the database table as a new row.
	// Sets the gateway variables equal to the variables
	public NailsGateway(String upc, int manufacturerID, int price, double length, int numberInBox)
			throws DatabaseException {
		insert(upc, manufacturerID, price, length, numberInBox);
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInBox = numberInBox;

	}

	// Creates a gateway by finding an existing row in the table that matches the
	// given ID and setting the instance variables equal to the row
	public NailsGateway(int ID) throws DatabaseException {
		boolean ResultExists = false;
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String query = "SELECT upc,manufacturerID,price,length,numberInBox FROM Nails WHERE ID =? ";
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
				setLength(rs.getDouble("length"));
				setNumberInBox(rs.getInt("numberInBox"));

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

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public int getNumberInBox() {
		return numberInBox;
	}

	public void setNumberInBox(int numberInBox) {
		this.numberInBox = numberInBox;
	}

	/**
	 * Updates the row of data.
	 * 
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String updateQuery = "UPDATE Nails SET upc = ?, manufacturerID = ?, price = ?, length = ?, numberInBox = ? WHERE ID = ?";

		try {
			PreparedStatement updateStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(updateQuery);
			updateStatement.setString(1, getUpc());
			updateStatement.setInt(2, getManufacturerID());
			updateStatement.setInt(3, getPrice());
			updateStatement.setDouble(4, getLength());
			updateStatement.setInt(5, getNumberInBox());
			updateStatement.setInt(6, getID());
			updateStatement.executeUpdate();

			System.out.println("UPDATED ENTRY:  UPC: " + getUpc() + ", manufacturerID: " + getManufacturerID()
					+ ", Price: " + getPrice() + ", Length: " + getLength() + ", NumberInBox: " + getNumberInBox());

		} catch (SQLException e) {
			throw new DatabaseException("Tables have not been created");
		}
	}

	/**
	 * Delete a Nail row by the id given.
	 * 
	 * @param id
	 *            - id to delete by
	 * 
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String deleteQuery = "DELETE from Nails WHERE id=?";

		boolean wasDeleted = false;

		try {
			PreparedStatement deleteStatement = dbm.getConnection(dbTables.CONCRETE).prepareStatement(deleteQuery);
			deleteStatement.setInt(1, ID);
			int changedRows = deleteStatement.executeUpdate();

			if (changedRows == 1)
				wasDeleted = true;

		} catch (SQLException | DatabaseException e) {
			System.out.println("Returned False for: deletebyID");
		}
		return wasDeleted;
	}

	// Insert method, used by the create constructor.
	public void insert(String upc, int manufacturerID, int price, double length, int numberInBox)
			throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String insertStatementString = "INSERT INTO Nails VALUES(?,?,?,?,?,?)";
		int nextID = LastIDGateway.getNextID();
		setID(nextID);
		try {
			PreparedStatement insertStatement = dbm.getConnection(dbTables.CONCRETE)
					.prepareStatement(insertStatementString);
			insertStatement.setInt(1, nextID);
			insertStatement.setString(2, upc);
			insertStatement.setInt(3, manufacturerID);
			insertStatement.setInt(4, price);
			insertStatement.setDouble(5, length);
			insertStatement.setInt(6, numberInBox);
			insertStatement.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
