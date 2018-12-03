package datasourceSingle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.InventoryItemDTO;
import exception.DatabaseException;

/**
 * 
 * @author Christopher Roadcap
 * @author Kanza Amin A class that represents a gateway to a particular item
 *         type in a database table. Able to extract information from the
 *         database using a find constructor, as well as insert new data into
 *         the database via multiple create constructors, based on the type of
 *         the item we wish to insert into the database. well as insert new data
 *         into the database.
 *
 */

public class InventoryItemsGateway
{
	/**
	 * Returns All items with a particular manufacturer ID
	 * 
	 * @param manID
	 *            the ID of the manufacturer who makes the item
	 * @return A list of DTOs that represent items manufactured by the same
	 *         manufacturer
	 * @throws DatabaseException
	 *             - when communications with the database fail/ improper database
	 *             syntax
	 */
	public static ArrayList<InventoryItemDTO> findAllByManID(int manID) throws DatabaseException
	{
		String query = "Select * from InventoryItems where manID = ?;";
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PreparedStatement stmnt;
		ResultSet result = null;
		try
		{
			stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setInt(1, manID);
			result = stmnt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return SingleTableHelper.buildInventoryItemDtoList(result);
	}

	/**
	 * Returns All items with a particular price
	 * 
	 * @param price
	 *            the price of a particular item
	 * @return A list of DTOs that represent items manufactured by the same
	 *         manufacturer
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public static ArrayList<InventoryItemDTO> findAllByPrice(int price) throws DatabaseException
	{
		String query = "Select * from InventoryItems where price = ?;";
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		ResultSet result = null;
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setInt(1, price);
			result = stmnt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return SingleTableHelper.buildInventoryItemDtoList(result);
	}

	/**
	 * Returns All items ofa particular type
	 * 
	 * @param type
	 *            the type of the item(1 = tool, 2 = powertool, 3 = nail, 4 =
	 *            stripnail)
	 * @returnA list of DTOs that represent items items all of the same type
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public static ArrayList<InventoryItemDTO> findAllByType(int type) throws DatabaseException
	{
		String query = "Select * from InventoryItems where type = ?;";
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PreparedStatement stmnt;
		ResultSet result = null;
		try
		{
			stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setInt(1, type);
			result = stmnt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return SingleTableHelper.buildInventoryItemDtoList(result);

	}

	public static ArrayList<InventoryItemDTO> findAllByUpc(String upc) throws DatabaseException
	{

		String query = "Select * from InventoryItems where UPC = ?;";
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PreparedStatement stmnt;
		ResultSet result = null;
		try
		{
			stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setString(1, upc);
			result = stmnt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return SingleTableHelper.buildInventoryItemDtoList(result);
	}

	/**
	 * Returns All items with that are either battery powered, or not battery
	 * powered
	 * 
	 * @param battery
	 *            - true or false depending if the item is battery powered
	 * @return list of DTOs that represent the all of the items that are either
	 *         batterypowered or not battery powered, depedning on the parameter
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public static ArrayList<InventoryItemDTO> findAllByBatteryPowered(boolean battery) throws DatabaseException
	{
		ResultSet result = null;
		try
		{
			String query = "Select * from InventoryItems where batteryPowered = ?;";
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setBoolean(1, battery);
			result = stmnt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return SingleTableHelper.buildInventoryItemDtoList(result);
	}

	// Instance Variables
	private static final int TOOL_TYPE = 1;
	private static final int POWERTOOL_TYPE = 2;
	private static final int NAIL_TYPE = 3;
	private static final int STRIPNAIL_TYPE = 4;
	int autoGeneratedID;
	String upc;
	int manID;
	int price;
	String description;
	boolean batteryPowered;
	double length;
	int numberInStrip;
	int numberInBox;
	int type;

	// Tool
	/**
	 * Create Constructor of type tool
	 * 
	 * @param upc
	 *            upc of the item
	 * @param manID
	 *            manufacturerID of the item
	 * @param price
	 *            price of the item
	 * @param description
	 *            a short description of the item
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public InventoryItemsGateway(String upc, int manID, int price, String description) throws DatabaseException
	{
		try
		{
			this.upc = upc;
			this.manID = manID;
			this.price = price;
			this.description = description;
			this.type = TOOL_TYPE;
			this.insert();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// PowerTool
	/**
	 * Create constructor for type power tool
	 * 
	 * @param upc
	 *            upc of the item
	 * @param manID
	 *            manufacturerID of the item
	 * @param price
	 *            price of the item
	 * @param description
	 *            a short description of the item
	 * @param isBatteryPowered
	 *            true if the item is battery powered, false otherwise
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public InventoryItemsGateway(String upc, int manID, int price, String description, boolean isBatteryPowered)
			throws DatabaseException
	{
		try
		{
			this.upc = upc;
			this.manID = manID;
			this.price = price;
			this.description = description;
			this.batteryPowered = isBatteryPowered;
			this.type = POWERTOOL_TYPE;
			this.insert();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Nail
	/**
	 * Create Constructor of type nail
	 * 
	 * @param upc
	 *            upc of the item
	 * @param manID
	 *            manufacturerID of the item
	 * @param price
	 *            price of the item
	 * @param length
	 *            the length of the nail
	 * @param numInBox
	 *            the number of nails in a box
	 * @throws DatabaseException
	 *             Exception when communications with the database fail/ improper
	 *             database syntax
	 */
	public InventoryItemsGateway(String upc, int manID, int price, double length, int numInBox) throws DatabaseException
	{
		try
		{
			this.upc = upc;
			this.manID = manID;
			this.price = price;
			this.length = length;
			this.numberInBox = numInBox;
			this.type = NAIL_TYPE;
			this.insert();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// StripNail
	/**
	 * @param upc
	 *            upc of the item
	 * @param manID
	 *            manufacturerID of the item
	 * @param price
	 *            price of the item
	 * @param numberInStrip
	 *            number of nails in a strip
	 * @param length
	 *            the length of the nail
	 * @throws DatabaseException
	 *             Exception when communications with the database fail/ improper
	 *             database syntax
	 */
	public InventoryItemsGateway(String upc, int manID, int price, int numberInStrip, double length)
			throws DatabaseException
	{
		try
		{
			this.upc = upc;
			this.manID = manID;
			this.price = price;
			this.numberInStrip = numberInStrip;
			this.length = length;
			this.type = STRIPNAIL_TYPE;
			this.insert();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create Constructor, which queries the database for a particular item, and
	 * sets it's instance variables accordingly
	 * 
	 * @param ID
	 *            the ID of the item in the database we wish to extract information
	 *            from
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public InventoryItemsGateway(int ID) throws DatabaseException
	{
		try
		{
			ResultSet values = this.findItem(ID);
			values.next();
			this.autoGeneratedID = values.getInt(1);
			this.upc = values.getString("UPC");
			this.manID = values.getInt("manID");
			this.price = values.getInt("price");
			this.description = values.getString("description");
			this.batteryPowered = values.getBoolean("batteryPowered");
			this.length = values.getDouble("length");
			this.numberInStrip = values.getInt("numberInStrip");
			this.numberInBox = values.getInt("numberInBox");
			this.type = values.getInt("type");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Instance Variable Getters and Setters
	public int getAutoGeneratedID()
	{
		return this.autoGeneratedID;
	}

	public void setID(int ID)
	{
		this.autoGeneratedID = ID;
	}

	public String getUPC()
	{
		return upc;
	}

	public void setUPC(String uPC)
	{
		upc = uPC;
	}

	public int getManID()
	{
		return manID;
	}

	public void setManID(int manID)
	{
		this.manID = manID;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getDesc()
	{
		return description;
	}

	public void setDesc(String desc)
	{
		this.description = desc;
	}

	public boolean getBatteryPowered()
	{
		return batteryPowered;
	}

	public void setBatteryPowered(boolean batteryPowered)
	{
		this.batteryPowered = batteryPowered;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public int getNumberInStrip()
	{
		return numberInStrip;
	}

	public void setNumberInStrip(int numberInStrip)
	{
		this.numberInStrip = numberInStrip;
	}

	public int getNumberInBox()
	{
		return numberInBox;
	}

	public void setNumberInBox(int numberInBox)
	{
		this.numberInBox = numberInBox;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * Inserts the Item into the database, sets data in the appropriate columns
	 * depending on the type of the item
	 * 
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	private void insert() throws DatabaseException
	{
		int type = this.getType();
		String query;
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		dbm.getConnection(dbTables.SINGLE);
		PreparedStatement stmt;
		ResultSet autoGeneratedKey;
		try
		{
			switch (type)
			{

			case 1: // The item is a tool

				query = "Insert into InventoryItems (upc,manID,price, description, "
						+ "batteryPowered, length,numberInStrip, numberInBox, type) "
						+ "Values(  ?, ?, ?, ?, NULL, NULL, NULL, NULL, 1);";
				stmt = beginStatement(query);
				stmt.setString(4, this.getDesc());
				stmt.executeUpdate();
				autoGeneratedKey = stmt.getGeneratedKeys();
				autoGeneratedKey.next();

				// sets the item ID at the time of insertion because the IDs are autogenerated
				this.setID(autoGeneratedKey.getInt(1));
				break;

			case 2: // The item is a powertool

				query = "Insert into InventoryItems (upc,manID,price, description, "
						+ "batteryPowered, length,numberInStrip, numberInBox, type) "
						+ "Values(  ?, ?, ?, ?, ?, NULL, NULL, NULL, 2)";
				stmt = beginStatement(query);
				stmt.setString(4, this.getDesc());
				stmt.setBoolean(5, this.getBatteryPowered());
				stmt.executeUpdate();
				autoGeneratedKey = stmt.getGeneratedKeys();
				autoGeneratedKey.next();

				// sets the item ID at the time of insertion because the IDs are autogenerated
				this.setID(autoGeneratedKey.getInt(1));
				break;

			case 3: // The item is a nail

				query = "Insert into InventoryItems (upc,manID,price, description, "
						+ "batteryPowered, length,numberInStrip, numberInBox, type) "
						+ "Values (  ?, ?, ?, NULL, NULL, ?, NULL, ?, 3)";
				stmt = beginStatement(query);
				stmt.setDouble(4, this.getLength());
				stmt.setInt(5, this.getNumberInBox());
				stmt.executeUpdate();
				autoGeneratedKey = stmt.getGeneratedKeys();
				autoGeneratedKey.next();

				// sets the item ID at the time of insertion because the IDs are autogenerated
				this.setID(autoGeneratedKey.getInt(1));
				break;

			case 4: // The item is a strip nail
				query = "Insert into InventoryItems(upc,manID,price, description, "
						+ "batteryPowered, length,numberInStrip, numberInBox, type) "
						+ "Values( ?, ?, ?, NULL, NULL, ?, ?, NULL, 4)";
				stmt = beginStatement(query);
				stmt.setDouble(4, this.getLength());
				stmt.setInt(5, this.getNumberInStrip());
				stmt.executeUpdate();
				autoGeneratedKey = stmt.getGeneratedKeys();
				autoGeneratedKey.next();

				// sets the item ID at the time of insertion because the IDs are autogenerated
				this.setID(autoGeneratedKey.getInt(1));
				break;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Used with a find constructor, after changes have been made to the find
	 * constructor's instance variables, this method is called to update the changed
	 * values in the database
	 * 
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public void persist() throws DatabaseException
	{
		String updateQuery = "Update InventoryItems " + "Set " + "  UPC = ?," + "  manID = ?," + "  price = ?,"
				+ "  description = ?," + "  batteryPowered = ?," + "  length = ?," + "  numberInStrip = ?,"
				+ "  numberInBox = ? " + "WHERE" + " InventoryItems.ID = ?;";

		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(updateQuery);

			stmnt.setString(1, this.getUPC());
			stmnt.setInt(2, this.getManID());
			stmnt.setInt(3, this.getPrice());
			stmnt.setString(4, this.getDesc());
			stmnt.setBoolean(5, this.getBatteryPowered());
			stmnt.setDouble(6, this.getLength());
			stmnt.setInt(7, this.getNumberInStrip());
			stmnt.setInt(8, this.getNumberInBox());
			stmnt.setInt(9, this.getAutoGeneratedID());
			stmnt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Deletes a row (item) from the appropriate tables, based on the ID of the Item
	 * 
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	public void deleteInventoryItem() throws DatabaseException
	{
		String deleteInventoryItems = "Delete  from InventoryItems where ID = ?;";
		try
		{
			PreparedStatement stmnt = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE)
					.prepareStatement(deleteInventoryItems);
			stmnt.setInt(1, this.getAutoGeneratedID());
			stmnt.executeUpdate();
			this.deleteAssociation();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void deleteAssociation() throws SQLException, DatabaseException
	{
		String deleteInventoryItems = "Delete  from PowerToolsToStripNails where powerToolID = ? or stripNailID = ?;";
		PreparedStatement stmnt = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE)
				.prepareStatement(deleteInventoryItems);
		stmnt.setInt(1, this.getAutoGeneratedID());
		stmnt.setInt(2, this.getAutoGeneratedID());
		stmnt.executeUpdate();
	}

	/**
	 * Used to reduce code duplication, all items have a UPC, ManID, and price, and
	 * therefore this method can be called to eliminate having to set the values
	 * every time a query is made.
	 * 
	 * @param query
	 *            the query the user wished to execute upon the database
	 * @return a prepared statement that has a few values set that are uniform for
	 *         all item types.
	 * @throws DatabaseException
	 *             when communications with the database fail/ improper database
	 *             syntax
	 */
	private PreparedStatement beginStatement(String query) throws DatabaseException
	{
		PreparedStatement stmnt = null;
		try
		{
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1, this.getUPC());
			stmnt.setInt(2, this.getManID());
			stmnt.setInt(3, this.getPrice());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return stmnt;
	}

	/**
	 * Finds the item in the database using its unique ID, and returns a result set
	 * of that row.
	 * 
	 * @param id
	 *            the ID of the item we with to find
	 * @return a result set pertaining to the row of the table with the item's ID
	 * @throws DatabaseException
	 */
	public ResultSet findItem(int id) throws DatabaseException
	{
		return SingleTableHelper.findItem(id);
	}

}
