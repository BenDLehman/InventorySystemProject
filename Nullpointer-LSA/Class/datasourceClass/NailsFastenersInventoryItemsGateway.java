package datasourceClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import exception.DatabaseException;
import datasource.NailDTO;
import exception.DatabaseException;

/**
 * 
 * @author Courtney Rush
 * 
 * Table and Row Data Gateway for Nails and accompanying abstract classes
 *
 */
public class NailsFastenersInventoryItemsGateway 
{
	private static int inventoryItemID;
	private String upc;
	private int manufacturerID;
	private int price;
	private int fastenerID;
	private double length;
	private int numberInBox;
	private boolean ResultExists;
	
	/*********************
	 *      Setters      *
	 *********************/
	private void setResultExistence(boolean re) {
		ResultExists = re;
	}
	
	private void setInventoryItemID(int inventoryID)
	{
		this.inventoryItemID = inventoryID;
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
		
	public void setNumberInBox(int numberInBox)
	{
		this.numberInBox = numberInBox;
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
	
	public int getNumberInBox()
	{
		return numberInBox;
	}
	private boolean getResultExistence() 
	{
		return ResultExists;
	}
	
	/**************************
	 *      Constructors      *
	 **************************/
	
	/**
	 * Constructs a new NailsFastenersInventoryItemsGateway object according to parameters and inserts it into the database
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param length
	 * @param numberInBox
	 * @throws DatabaseException
	 */
	public NailsFastenersInventoryItemsGateway(String upc, int manufacturerID, int price, double length, int numberInBox) throws DatabaseException
	{
		insert(upc, manufacturerID, price, length, numberInBox);
		
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInBox = numberInBox;
	}

	/**
	 * Constructs a new NailsFastenersInventoryItemsGateway object according to the inventoryItemID of the object requested
	 * @param inventoryItemID
	 * @throws DatabaseException
	 */
	public NailsFastenersInventoryItemsGateway(int inventoryItemID) throws DatabaseException
	{
		try {		
			//create connection
			Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
			//create select statement for Nail object, will only return one row in the resultSet
			PreparedStatement getStatement = dbmConn.prepareStatement("select upc, manufacturerID, price, length, numberInBox from NailsView where InventoryItemID = ?");	
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
				setNumberInBox(rs.getInt("numberInBox"));
				setResultExistence(true);
			}
			
			//same process for Fastener ID
			PreparedStatement fastenerStmnt = dbmConn.prepareStatement("SELECT ID FROM Fasteners WHERE InventoryItemID = ?", Statement.RETURN_GENERATED_KEYS);
			fastenerStmnt.setInt(1, inventoryItemID);
			fastenerStmnt.execute();
			ResultSet rsFastener = fastenerStmnt.getGeneratedKeys();
			//this one only needs to save the FastenerID of the Nail
			if(rsFastener.next())
			{
				setFastenerID(rsFastener.getInt("ID"));
			}
			if(getResultExistence() != true)
			{	    
				throw new DatabaseException("Nail ITEM WITH ID: "+inventoryItemID+" WAS NOT FOUND.");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new DatabaseException("Finding Nail Error");
		}
	}
	
	/********************************
	 *      Non-Static Methods      *
	 ********************************/
	
	/**
	 * Persists a create constructor generated object to the database
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param length
	 * @param numberInBox
	 * @throws DatabaseException
	 */
	private void insert(String upc, int manufacturerID,int price, double length, int numberInBox) throws DatabaseException
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
			
			//inserts FastenerID and numberInBox to Nails table
			PreparedStatement nailStmt = dbmConn.prepareStatement("INSERT INTO Nails(FastenerID, numberInBox) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			nailStmt.setInt(1,getFastenerID());
			nailStmt.setInt(2, numberInBox);
			nailStmt.execute();
		} catch (SQLException e) 
		{
			throw new DatabaseException("Insert failed");
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
			PreparedStatement fastenerStmt = dbmConn.prepareStatement("update Fasteners set length = ? where InventoryItemID = ?");
			fastenerStmt.setDouble(1, getLength());
			fastenerStmt.setInt(2, getInventoryItemID());
			fastenerStmt.execute();
			
			//updates Nails with this object's new information
			PreparedStatement nailsStmt = dbmConn.prepareStatement("update Nails set numberInBox = ? WHERE FastenerID = ?");
			nailsStmt.setInt(1, getNumberInBox());
			nailsStmt.setInt(2, getFastenerID());
			nailsStmt.execute();
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
	 * Deletes item in database with inventoryItemID 
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
			//find FastenerID
			PreparedStatement findFastenerStmt = dbmConn.prepareStatement("DELETE FROM InventoryItems WHERE ID = ?");
			findFastenerStmt.setInt(1, inventoryItemID);
			findFastenerStmt.execute();
			wasDeleted = true;
		}	
		catch(SQLException e)
		{
			throw new DatabaseException("Object not deleted properly");
		}
		return wasDeleted;
	}

	/**
	 * finds all Nails in database and returns an ArrayList of NailDtos representing that information
	 * @return all Nails as NailDTOs
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findAll() throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<NailDTO> all = new ArrayList<NailDTO>();			  
		try {
			PreparedStatement getAllStmt = dbmConn.prepareStatement("SELECT * FROM NailsView;");
			ResultSet rs = getAllStmt.executeQuery();
			//for each row result in the query, create a NailDTO with the information and add it to the ArrayList
			while(rs.next())
			{
				all.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findAll");
		}
		return all;
	}

	/**
	 * finds all Nails in database with desired upc and returns an ArrayList of NailDtos representing that information
	 * @param upc
	 * @return all Nails with the desired upc
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findByUPC(String upc) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<NailDTO> upcList = new ArrayList<NailDTO>();			  
		try {
			PreparedStatement getUPCStmt = dbmConn.prepareStatement("SELECT * FROM NailsView WHERE upc = ?");
			getUPCStmt.setString(1, upc);
			ResultSet rs = getUPCStmt.executeQuery();
			//for each row result in the query, create a NailDTO representing that data and add it to the ArrayList
			while(rs.next())
			{
				upcList.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByUPC");
		}
		return upcList;
	}

	/**
	 * finds all Nails in database with desired manufacturerID and returns an ArrayList of NailDtos representing that information
	 * @param manufacturerID
	 * @return
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findByManufacturerID(int manufacturerID) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<NailDTO> midList = new ArrayList<NailDTO>();			  
		try {
			PreparedStatement getMidStmt = dbmConn.prepareStatement("SELECT * FROM NailsView WHERE manufacturerID = ?");
			getMidStmt.setInt(1, manufacturerID);
			ResultSet rs = getMidStmt.executeQuery();
			//for each row result in the query, create a NailDTO representing that data and add it to the ArrayList
			while(rs.next())
			{
				midList.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByManufacturerID");
		}
		return midList;
	}
	
	/**
	 * finds all Nails in database with desired price range and returns an ArrayList of NailDtos representing that information
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findByPriceRange(int minPrice,int maxPrice) throws DatabaseException
	{
		ArrayList<NailDTO> priceList = new ArrayList<NailDTO>();			  

		if(minPrice <= maxPrice)
		{
			Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
			try {
				PreparedStatement getPriceStmt = dbmConn.prepareStatement("SELECT * FROM NailsView WHERE price >= ? OR price <= ?");
				getPriceStmt.setInt(1, minPrice);
				getPriceStmt.setInt(2, maxPrice);
				ResultSet rs = getPriceStmt.executeQuery();
				//for each row result in the query, create a NailDTO representing that data and add it to the ArrayList
				while(rs.next())
				{
					priceList.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
				}
			} 
			catch (SQLException e) 
			{
				throw new DatabaseException("Error in identifying priceRange");
			}
		}
		else
		{
       	 throw new DatabaseException("Minimum price cannot be more than maximum price");

		}
		return priceList;
	}
	
	/**
	 * finds all Nails in database with desired length and returns an ArrayList of NailDtos representing that information
	 * @param length
	 * @return
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findByLength(double length) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<NailDTO> lengthList = new ArrayList<NailDTO>();			  
		try {
			PreparedStatement getLengthStmt = dbmConn.prepareStatement("SELECT * FROM NailsView WHERE length = ?");
			getLengthStmt.setDouble(1, length);
			ResultSet rs = getLengthStmt.executeQuery();
			//for each row result in the query, create a NailDTO representing that data and add it to the ArrayList
			while(rs.next())
			{
				lengthList.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByLength");
		}
		return lengthList;
	}
	
	/**
	 * finds all Nails in database with desired numberInBox and returns an ArrayList of NailDtos representing that information
	 * @param numberInBox
	 * @return
	 * @throws DatabaseException
	 */
	public static ArrayList<NailDTO> findByNumberInBox(int numberInBox) throws DatabaseException
	{
		Connection dbmConn = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
		ArrayList<NailDTO> numberInBoxList = new ArrayList<NailDTO>();			  
		try {
			PreparedStatement getNumberInBoxStmt = dbmConn.prepareStatement("SELECT * FROM NailsView WHERE numberInBox = ?");
			getNumberInBoxStmt.setInt(1, numberInBox);
			ResultSet rs = getNumberInBoxStmt.executeQuery();
			//for each row result in the query, create a NailDTO representing that data and add it to the ArrayList
			while(rs.next())
			{
				numberInBoxList.add(new NailDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getDouble("length"), rs.getInt("numberInBox")));
			}
		} 
		catch (SQLException e) 
		{
			throw new DatabaseException("Error in findByNumberInBox");
		}
		return numberInBoxList;
	}
}