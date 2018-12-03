package datasourceClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.StripNailDTO;
import exception.DatabaseException;

/**
 * 
 * @author cr5603
 *
 * Table and Row Data Gateway for StripNails and accompanying abstract classes
 *
 */
public class StripNailsFastenersInventoryItemsGateway 
{
	private static int inventoryItemID;
	private String upc;
	private int manufacturerID;
	private int price;
	private int fastenerID;
	private double length;
	private int numberInStrip;
	private boolean ResultExists;

	
	/*********************
	 *      Setters      *
	 *********************/
	
	private void setInventoryItemID(int inventoryID)
	{
		this.inventoryItemID = inventoryID;
	}
	private void setResultExistence(boolean re) {
		ResultExists = re;
	}
	
	public void setUPC(String upc)
	{
		 this.upc = upc;
	}
	
	public void setManufacturerID(int manufacturerID)
	{
		 this.manufacturerID = manufacturerID;
	}
	
	public void setPrice(int p)
	{
		 price = p;
	}	
	
	private void setFastenerID(int fastenerID)
	{
		this.fastenerID = fastenerID;
	}
	
	public void setLength(double length)
	{
		this.length = length;
	}
		
	public void setNumberInStrip(int numberInStrip)
	{
		this.numberInStrip = numberInStrip;
	}
	
	/*********************
	 *      Getters      *
	 *********************/
	
	public int getInventoryItemID()
	{
		return inventoryItemID;
	}
	
	public String getUPC()
	{
		return upc;
	}
	
	public int getManufacturerID()
	{
		return manufacturerID;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public int getFastenerID()
	{
		return fastenerID;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public int getNumberInStrip()
	{
		return numberInStrip;
	}
	private boolean getResultExistence() 
	{
		return ResultExists;
	}
	
	/**************************
	 *      Constructors      *
	 **************************/
	
	/**
	 * Constructs a new StripNailsFastenersInventoryItemsGateway object according to parameters and inserts it into the database
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param length
	 * @param numberInStrip
	 * @throws DatabaseException
	 */
	public StripNailsFastenersInventoryItemsGateway(String upc, int manufacturerID, int price, double length, int numberInStrip) throws DatabaseException
	{
		insert(upc, manufacturerID, price, length, numberInStrip);
		
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInStrip = numberInStrip;
	}

	/**
	 * Constructs a new StripNailsFastenersInventoryItemsGateway object according to the inventoryItemID of the object requested
	 * @param inventoryItemID
	 * @throws DatabaseException
	 */
	public StripNailsFastenersInventoryItemsGateway(int inventoryItemID) throws DatabaseException
	{
		try {		
			//create connection
			Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
			//create select statement for StripNail object, will only return one row in the resultSet			
			PreparedStatement getStatement = dbmConn.prepareStatement("select upc, manufacturerID, price, length, numberInStrip from StripNailsView where InventoryItemID = ?");	
			getStatement.setInt(1, inventoryItemID);
			ResultSet rs = getStatement.executeQuery();
			//if a row was returned, save the values of the item
			if(rs.next())
			{
				setInventoryItemID(inventoryItemID);
				setUPC(rs.getString("upc"));
				setManufacturerID(rs.getInt("manufacturerID"));
				setPrice(rs.getInt("price"));
				setLength(rs.getDouble("length"));
				setNumberInStrip(rs.getInt("numberInStrip"));
				setResultExistence(true);
			}
			
			//same process for FastenerID
			PreparedStatement fastenerStmnt = dbmConn.prepareStatement("SELECT ID FROM Fasteners WHERE InventoryItemID = ?", Statement.RETURN_GENERATED_KEYS);
			fastenerStmnt.setInt(1, inventoryItemID);
			fastenerStmnt.execute();
			ResultSet rsFastener = fastenerStmnt.getGeneratedKeys();
			if(rsFastener.next())
			{
				setFastenerID(rsFastener.getInt(1));
			}
			if(getResultExistence() != true)
			{
				throw new DatabaseException("STRIP NAILS WITH ID: "+inventoryItemID+ " DOES NOT EXIST");
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Finding StripNail Error");
		}
	}
	
	/********************************
	 *      Non-Static Methods      *
	 ********************************/
	
	private void insert(String upc, int manufacturerID,int price, double length, int numberInStrip) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		try {
			//inserts upc, manufacturerID, and price to InventoryItems table
			PreparedStatement inventoryItemStmt = dbmConn.prepareStatement("INSERT INTO InventoryItems(upc,manufacturerID,price) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);	
			inventoryItemStmt.setString(1, upc);
			inventoryItemStmt.setInt(2, manufacturerID);
			inventoryItemStmt.setInt(3, price);
			inventoryItemStmt.execute();
			ResultSet rsInventoryItem = inventoryItemStmt.getGeneratedKeys();
			if(rsInventoryItem.next())
			{
				setInventoryItemID(rsInventoryItem.getInt(1));
			}
			
			//inserts length and InventoryItemID to Fasteners table
			PreparedStatement fastenerStmnt = dbmConn.prepareStatement("INSERT INTO Fasteners(InventoryItemID, length) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			fastenerStmnt.setInt(1, getInventoryItemID());
			fastenerStmnt.setDouble(2, length);
			fastenerStmnt.execute();
			ResultSet rsFastener = fastenerStmnt.getGeneratedKeys();
			if(rsFastener.next())
			{
				setFastenerID(rsFastener.getInt(1));
			}
			
			//inserts FastenerID and numberInStrip to StripNails table
			PreparedStatement nailStmt = dbmConn.prepareStatement("INSERT INTO StripNails(FastenerID, numberInStrip) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			nailStmt.setInt(1,getFastenerID());
			nailStmt.setInt(2, numberInStrip);
			nailStmt.execute();
		} catch (SQLException e) 
		{
			throw new DatabaseException("StripNail not inserted");
		}
		
		
	}

	/**
	 * Persists this object's updates to the database
	 * @throws DatabaseException
	 */
	public void persist() throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		try
		{
			//updates InventoryItems with this object's new information
			PreparedStatement inventoryItemStmt = dbmConn.prepareStatement("update InventoryItems set upc = ?, manufacturerID = ?, price = ? where ID = ?");
			inventoryItemStmt.setString(1, getUPC());
			inventoryItemStmt.setInt(2, getManufacturerID());
			inventoryItemStmt.setInt(3, getPrice());
			inventoryItemStmt.setInt(4, getInventoryItemID());
			inventoryItemStmt.execute();

			//updates Fasteners with this object's new information
			PreparedStatement fastenerStmt = dbmConn.prepareStatement("update Fastners set length = ? where InventoryItemID = ?");
			fastenerStmt.setDouble(1, getLength());
			fastenerStmt.setInt(2, getInventoryItemID());
			fastenerStmt.execute();
			
			//updates StripNails with this object's new information
			PreparedStatement stripNailsStmt = dbmConn.prepareStatement("update StripNails set numberInStrip = ? WHERE FastnerID = ?");
			stripNailsStmt.setInt(1, getNumberInStrip());
			stripNailsStmt.setInt(2, getFastenerID());
			stripNailsStmt.execute();
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Object not persisted");
		}
	}
	
	/****************************
	 *      Static Methods      *
	 ****************************/
	
	/**
	 * Deletes item in database with inventoryItemID x
	 * @param inventoryItemID
	 * @return result of deletion
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		boolean wasDeleted = false;
		try
		{			
			//Deletes row from InventoryItems and since the inventoryitems is cascading it then deletes everything else
			PreparedStatement deleteInventoryItemStmt = dbmConn.prepareStatement("delete from InventoryItems where ID = ?");
			deleteInventoryItemStmt.setInt(1, inventoryItemID);
			deleteInventoryItemStmt.execute();
			
			//sets to true if an exception wasn't thrown before this point, means item was successfully deleted
			wasDeleted = true;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Object not deleted properly");
		}
		return wasDeleted;
	}
	
	/**
	 * finds all StripNails in database and returns an ArrayList of StripNailDtos representing that information
	 * @return all StripNails as StripNailDTOs
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findAll() throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<StripNailDTO> all = new ArrayList<>();			  
		try {
			PreparedStatement getAllStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView;");
			ResultSet rs = getAllStmt.executeQuery();
			//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				all.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findAll");
		}
		return all;
	}
	
	/**
	 * finds all StripNails in database with desired upc and returns an ArrayList of StripNailDtos representing that information
	 * @param upc
	 * @return all StripNails with the desired upc
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findByUPC(String upc) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<StripNailDTO> upcList = new ArrayList<>();			  
		try {
			PreparedStatement getUPCStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView WHERE upc = ?");
			getUPCStmt.setString(1, upc);
			ResultSet rs = getUPCStmt.executeQuery();
			//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				upcList.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findUPC");
		}
		return upcList;
	}

	/**
	 * finds all StripNails in database with desired manufacturerID and returns an ArrayList of StripNailDtos representing that information
	 * @param manufacturerID
	 * @return all StripNails with the desired manufacturerID
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findByManufacturerID(int manufacturerID) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<StripNailDTO> midList = new ArrayList<>();			  
		try {
			PreparedStatement getMidStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView WHERE manufacturerID = ?");
			getMidStmt.setInt(1, manufacturerID);
			ResultSet rs = getMidStmt.executeQuery();
			//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				midList.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findManufacturerID");
		}
		return midList;
	}
	
	/**
	 * finds all StripNails in database with desired price range and returns an ArrayList of StripNailDtos representing that information
	 * @param minPrice
	 * @param maxPrice
	 * @return all StripNails with the desired price range
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findByPrice(int minPrice,int maxPrice) throws DatabaseException
	{
		ArrayList<StripNailDTO> priceList = new ArrayList<>();			  

		if(minPrice < maxPrice)
		{
			Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
			try {
				PreparedStatement getPriceStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView WHERE price <= ? OR price >= ?");
				getPriceStmt.setInt(1, maxPrice);
				getPriceStmt.setInt(2, minPrice);
				ResultSet rs = getPriceStmt.executeQuery();
				//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
				while(rs.next())
				{
					priceList.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
				}
			} 
			catch (SQLException e) 
			{
				throw new DatabaseException("Error in findManufacturerID");
			}
		}else
		{
			throw new DatabaseException("Minimum price must be less than maximum price");
		}
		return priceList;

	}
	
	/**
	 * finds all StripNails in database with desired length and returns an ArrayList of StripNailDtos representing that information
	 * @param length
	 * @return all StripNails with the desired length
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findByLength(double length) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<StripNailDTO> lengthList = new ArrayList<StripNailDTO>();			  
		try {
			PreparedStatement getLengthStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView WHERE length = ?");
			getLengthStmt.setDouble(1, length);
			ResultSet rs = getLengthStmt.executeQuery();
			//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				lengthList.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByLength");
		}
		return lengthList;
	}
	
	/**
	 * finds all StripNails in database with desired numberInStrip and returns an ArrayList of StripNailDtos representing that information
	 * @param numberInStrip
	 * @return all StripNails with the desired numberInStrip
	 * @throws DatabaseException
	 */
	public static ArrayList<StripNailDTO> findByNumberInStrip(int numberInStrip) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<StripNailDTO> numberInStripList = new ArrayList<StripNailDTO>();			  
		try {
			PreparedStatement getNumberInStripStmt = dbmConn.prepareStatement("SELECT * FROM StripNailsView WHERE numberInStrip = ?");
			getNumberInStripStmt.setInt(1, numberInStrip);
			ResultSet rs = getNumberInStripStmt.executeQuery();
			//for each row result in the query, create a StripNailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				numberInStripList.add(new StripNailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInStrip")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByNumberInStrip");
		}
		return numberInStripList;
	}
}
