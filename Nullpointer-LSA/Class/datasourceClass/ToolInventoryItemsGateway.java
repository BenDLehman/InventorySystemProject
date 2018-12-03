package datasourceClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolToStripNailDTO;
import datasource.ToolDTO;
import exception.*;

public class ToolInventoryItemsGateway {
	private String description;
	private String upc;
	private int manufacturerID;
	private int price;
	private int inventoryItemID;
	private boolean ResultExists;



	public ToolInventoryItemsGateway(String upc, int manufacturerID, int price, String description) throws DatabaseException
	{
		insert(upc, manufacturerID,price,description);
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
		
	}
	
	public ToolInventoryItemsGateway(int itemInvID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		 String itemQ = "SELECT upc,manufacturerID,price,description FROM ToolInventoryItems WHERE InventoryItemID =? ";
		 try {
				
			  PreparedStatement invItemStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(itemQ);	
			  invItemStmnt.setInt(1,itemInvID);
			  ResultSet rs = invItemStmnt.executeQuery();
			 
				  while(rs.next())
				  {
					  setInventoryItemID(itemInvID);
					  setUPC(rs.getString("upc"));
					  setManufacturerID(rs.getInt("manufacturerID"));
					  setPrice(rs.getInt("price"));
					  setDescription(rs.getString("description"));					  
					  setResultExistence(true);
				  }
			 if(getResultExistence() != true){	  
				throw new DatabaseException("Tool with id: "+itemInvID+ " does not exist");
			  }
			 
           
		  }catch(SQLException e)
		  {
		  }
		}
	


	



	public void persist() throws DatabaseException {
		 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		 String updateInvItem = "UPDATE InventoryItems SET upc = ?, manufacturerID = ?, price = ? WHERE ID = ?";
		 String updateTools = "UPDATE Tools SET description = ? WHERE InventoryItemID = ? ";

		 try {
				PreparedStatement uInvItemStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(updateInvItem);
				uInvItemStmt.setString(1, getUPC());
				uInvItemStmt.setInt(2, getManufacturerID());
				uInvItemStmt.setInt(3, getPrice());
				uInvItemStmt.setInt(4, getInventoryItemID());
				uInvItemStmt.execute();
				PreparedStatement uToolStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(updateTools);
				uToolStmt.setString(1, getDescription());
				uToolStmt.setInt(2, getInventoryItemID());
				uToolStmt.execute();	

			  } catch (SQLException e) {
				// TODO Auto-generated catch block
			}

		 
	}
	
	/**
	 * Deletes an entry
	 * @param InvItemID
	 * @return false if the inventory item was not deleted, otherwise true
	 * @throws DatabaseException
	 */
	public boolean delete() throws DatabaseException {
		boolean wasDeleted = false;
		 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		 ToolInventoryItemsGateway ti = new ToolInventoryItemsGateway(getInventoryItemID());
		 if(ti.getResultExistence() == true)
		 {
			 String deleteInvItem = "DELETE FROM InventoryItems WHERE ID = ?";
			
			
			try {
					PreparedStatement dInvItem = dbm.getConnection(dbTables.CLASS).prepareStatement(deleteInvItem);
					dInvItem.setInt(1,getInventoryItemID());
					dInvItem.execute();
					setResultExistence(false);
					wasDeleted = true;	
						
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
				}  
		 }else
		 {
			 throw new DatabaseException("ENTRY COULD NOT BE DELETED; ID DOES NOT EXIST"); 
		 }
		return wasDeleted;

	}
		

	/************************************************/
	/*            GETTER/SETTERS                    */
	/************************************************/
	private void setResultExistence(boolean re) {
		ResultExists = re;
	}

	public void setDescription(String sd)
	{
		 description = sd;
	}
		
	//sets the upc
	public void setUPC(String u)
	{
		 upc = u;
	}
	//sets the manyfacturerID
	public void setManufacturerID(int mID)
	{
		 manufacturerID = mID;
	}
	public void setPrice(int p)
	{
		 price = p;
	}	
	public void setInventoryItemID(int IID)
	{
		 inventoryItemID = IID;
	}
	
	public int getInventoryItemID()
	{
		return inventoryItemID;
	}
	public String getDescription()
	{
		return description;
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
	private boolean getResultExistence() {
		// TODO Auto-generated method stub
		return ResultExists;
	}

	/************************************************/
	/*           STATIC METHODS                     */
	/************************************************/
	

	public static ArrayList<ToolDTO> findByUpc(String upc) throws DatabaseException {
     DatabaseManager dbm = DatabaseManager.getDatabaseManager();
     ArrayList<ToolDTO> tInvItemDTO = new ArrayList<>();

	 String findUPCQ = "SELECT * FROM ToolInventoryItems WHERE upc = ?";
	try {
		 PreparedStatement toolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findUPCQ);
		 toolInvTable.setString(1, upc);
		ResultSet rs = toolInvTable.executeQuery();

		while(rs.next())
		{
			ToolDTO tII = new ToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"));
			tInvItemDTO.add(tII);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block

	}
	return tInvItemDTO;

	}
	
	public static ArrayList<ToolDTO> findAll() throws DatabaseException
	{
		  DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		     ArrayList<ToolDTO> tInvItemDTO = new ArrayList<>();

			String ToolInvTable = "SELECT * FROM ToolInventoryItems LEFT JOIN PowerToolInventoryItems ON ToolInventoryItems.InventoryItemID = PowerToolInventoryItems.InventoryItemID WHERE PowerToolInventoryItems.InventoryItemID IS NULL;";
			  
			try {
				PreparedStatement toolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(ToolInvTable);
				ResultSet rs = toolInvTable.executeQuery();
				while(rs.next())
				{
					ToolDTO tII = new ToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"));
					tInvItemDTO.add(tII);

				}
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tInvItemDTO;
		
	}
	
	
	public static ArrayList<ToolDTO> findByManufacturerID(int mID) throws DatabaseException
	{
		  DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			ArrayList<ToolDTO> tInvItemDTO = new ArrayList<>();

		 String findManufIDQ = "SELECT * FROM ToolInventoryItems WHERE manufacturerID = ?";
		try {
			 PreparedStatement toolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findManufIDQ);
			 toolInvTable.setInt(1, mID);
			 ResultSet rs = toolInvTable.executeQuery();
			while(rs.next())
			{
				ToolDTO tII = new ToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"));
				tInvItemDTO.add(tII);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block

		}
		return tInvItemDTO;
	}

	
	public static ArrayList<ToolDTO> findByPriceRange(int minPrice,int maxPrice) throws DatabaseException
	{
		ArrayList<ToolDTO> tInvItemDTO = new ArrayList<>();
		if(minPrice <= maxPrice)
		{
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		 String findPriceQ = "SELECT * FROM ToolInventoryItems WHERE price >= ? AND price <= ?";
		 try {
			 PreparedStatement toolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findPriceQ);
			 toolInvTable.setInt(1, minPrice);
			 toolInvTable.setInt(2, maxPrice);
			 ResultSet rs = toolInvTable.executeQuery();
			while(rs.next())
			{
				ToolDTO tII = new ToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"));
				tInvItemDTO.add(tII);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block

		}
		}else
		{
			
			throw new DatabaseException("MINIMUM PRICE CANNOT BE HIGHER THAN MAXIMUM PRICE");
		}
		
	 return tInvItemDTO;
	 }


	
	
	
	

	
	
	/************************************************/
	/*            PRIVATE METHODS                   */
	/************************************************/

	/**
	 * This method inserts into its respective table
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param description
	 * @throws DatabaseException 
	 */
	private void insert(String upc, int manufacturerID,int price, String description)throws DatabaseException
	{
		 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		try {
			
			String InvItemQ = "INSERT INTO InventoryItems(upc,manufacturerID,price) VALUES (?,?,?)";
			PreparedStatement invItemStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(InvItemQ,Statement.RETURN_GENERATED_KEYS);	
			  invItemStmnt.setString(1, upc);
			  invItemStmnt.setInt(2, manufacturerID);
			  invItemStmnt.setInt(3, price);
			  invItemStmnt.execute();
			  ResultSet rs = invItemStmnt.getGeneratedKeys();
			  int invItem_id = 0;
			  //gets the id from the Inventory Item ID
			  if(rs.next())
			  {
				  invItem_id = rs.getInt(1);
				  setInventoryItemID(invItem_id);
			  }
			  //allows me to insert the id from the InventoryItem into tools
			  String toolQ = "INSERT INTO Tools(InventoryItemID,description) VALUES (?, ?)";
			  PreparedStatement toolStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(toolQ,Statement.RETURN_GENERATED_KEYS);
			  toolStmnt.setInt(1,invItem_id);
			  toolStmnt.setString(2, description);
			  toolStmnt.execute();
			 
		  }catch(SQLException e)
		  {
		  }

		
		
	}



	

	
	
	   
	  

		


		



}
