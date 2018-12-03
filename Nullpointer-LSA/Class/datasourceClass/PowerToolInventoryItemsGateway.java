package datasourceClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolDTO;
import exception.DatabaseException;

public class PowerToolInventoryItemsGateway {
	boolean ResultExists = false;
	private String description;
	private String upc;
	private int manufacturerID;
	private int price;
	private int inventoryItemID;
	private boolean batteryPowered;
	private static boolean empty = true;
	
	
	
	
	/************************************************/
	/*            STATIC METHODS                    */
	/************************************************/
	
	public static ArrayList<PowerToolDTO> findAll() throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	     ArrayList<PowerToolDTO> ptInvItemDTO = new ArrayList<>();
		 String findUPCQ = "SELECT * FROM PowerToolInventoryItems";
		 try {
			 
				PreparedStatement powertoolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findUPCQ);
				ResultSet rs = powertoolInvTable.executeQuery();

				while(rs.next())
				{
					PowerToolDTO pII = new PowerToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"),rs.getBoolean("batteryPowered"));
					ptInvItemDTO.add(pII);

				}
				
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		return ptInvItemDTO;

		
	}
	
	
	public static ArrayList<PowerToolDTO> findByUPC(String upc) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	     ArrayList<PowerToolDTO> ptInvItemDTO = new ArrayList<>();
		 String findUPCQ = "SELECT * FROM PowerToolInventoryItems WHERE upc = ?";
		 try {
			 
				PreparedStatement powertoolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findUPCQ);
				powertoolInvTable.setString(1, upc);
				ResultSet rs = powertoolInvTable.executeQuery();

				while(rs.next())
				{
					PowerToolDTO pII = new PowerToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"),rs.getBoolean("batteryPowered"));
					ptInvItemDTO.add(pII);

				}
				
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		return ptInvItemDTO;

	}

	
	
	public static ArrayList<PowerToolDTO> findByManufacturerID(int mID) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	     ArrayList<PowerToolDTO> ptInvItemDTO = new ArrayList<>();

		 String findUPCQ = "SELECT * FROM PowerToolInventoryItems WHERE manufacturerID = ?";
		 try {
			 
				PreparedStatement powertoolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findUPCQ);
				powertoolInvTable.setInt(1, mID);
				ResultSet rs = powertoolInvTable.executeQuery();

				while(rs.next())
				{

					PowerToolDTO pII = new PowerToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"),rs.getBoolean("batteryPowered"));
					ptInvItemDTO.add(pII);

				}
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		return ptInvItemDTO;

	}
	
	public static ArrayList<PowerToolDTO> findByPriceRange(int minPrice, int maxPrice) throws DatabaseException
	{
	     ArrayList<PowerToolDTO> ptInvItemDTO = new ArrayList<>();
         if(minPrice <= maxPrice)
         {
             DatabaseManager dbm = DatabaseManager.getDatabaseManager();

   			 String findUPCQ = "SELECT * FROM PowerToolInventoryItems WHERE price >= ? AND price <= ?";
   			 try {
   				 
   					PreparedStatement powertoolInvTable = dbm.getConnection(dbTables.CLASS).prepareStatement(findUPCQ);
   					powertoolInvTable.setInt(1, minPrice);
   					powertoolInvTable.setInt(2, maxPrice);
   					ResultSet rs = powertoolInvTable.executeQuery();

   					while(rs.next())
   					{
   						PowerToolDTO pII = new PowerToolDTO(rs.getInt("InventoryItemID"),rs.getString("upc"),rs.getInt("manufacturerID"),rs.getInt("price"),rs.getString("description"),rs.getBoolean("batteryPowered"));
   						ptInvItemDTO.add(pII);

   					}
   				  } catch (SQLException e) {
   					// TODO Auto-generated catch block
   				}
         }else
         {
        	 throw new DatabaseException("MINIMUM PRICE CANNOT BE HIGHER THAN MAXIMUM PRICE");
         }
	
		return ptInvItemDTO;
     
	
	}
	
	/************************************************/
	/*           FIND/CREATE CONSTRUCTOR            */
	/************************************************/
	/**
	 * Inserts the parameters into the database
	 * @param upc
	 * @param manufacturerID
	 * @param price
	 * @param description
	 * @param batteryPowered
	 * @throws DatabaseException
	 */
	public PowerToolInventoryItemsGateway(String upc, int manufacturerID, int price, String description, boolean batteryPowered) throws DatabaseException
	{
		insert(upc, manufacturerID,price,description,batteryPowered);
		
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
		this.batteryPowered = batteryPowered;
	}
	/**
	 * Finds the parameters in the database
	 * @param inventoryItemID
	 * @throws DatabaseException
	 */
	public PowerToolInventoryItemsGateway(int inventoryItemID) throws DatabaseException {

		 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		 String itemQ = "SELECT upc,manufacturerID,price,description,batteryPowered FROM PowerToolInventoryItems WHERE InventoryItemID =? ";
		 try {
				
			  PreparedStatement invItemStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(itemQ);	
			  invItemStmnt.setInt(1,inventoryItemID);
			  ResultSet rs = invItemStmnt.executeQuery();
			  setInventoryItemID(inventoryItemID);
			  while(rs.next())
			  {
				  setUPC(rs.getString("upc"));
				  setManufacturerID(rs.getInt("manufacturerID"));
				  setPrice(rs.getInt("price"));
				  setDescription(rs.getString("description"));
				  setBatteryPower(rs.getBoolean("batteryPowered"));				  
				  setResultExistence(true);
			  }
			  if(getResultExistence() != true){	  
				  
                throw new DatabaseException("POWERTOOL ITEM WITH ID: "+inventoryItemID+" WAS NOT FOUND.");
			  }

			          
		  }catch(SQLException e)
		  {
		  }
	}
	


	/************************************************/
	/*                   SETTERS                    */
	/************************************************/
	//sets the result existsnce
	private void setResultExistence(boolean re)
	{
		 ResultExists = re;
	}
		// sets the description
		public void setDescription(String sd)
		{
			description = sd;
		}
		
		// set whether this is a powered tool
		public void setBatteryPower(boolean bp)
		{
			 batteryPowered = bp;
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
		
		private void setInventoryItemID(int iID)
		{
			inventoryItemID = iID;
		}
		/************************************************/
		/*                   GETTERS                    */
		/************************************************/
		//gets the result instance
		private boolean getResultExistence()
		{
			return ResultExists;
		}
		//gets the InventoryItemID

		public int getInventoryItemID()
		{
			return inventoryItemID;
		}
		//gets the description

		public String getDescription()
		{
			return description;
		}
		//gets the upc instance

		public String getUPC()
		{
			return upc;
		}
		//gets the manufactuerer id

		public int getManufacturerID()
		{
			return manufacturerID;
		}
		//gets the price

		public int getPrice()
		{
			return price;
		}	
		//gets the batteryPowered

		public boolean getBatteryPowered()
		{
			return batteryPowered;
		}

		

		/************************************************/
		/*                   STATIC METHODS             */
		/************************************************/
		
		/**
		 * 
		 * @param InvItemID
		 * @return true if the id was deleted otherwise return false
		 * @throws DatabaseException
		 * @throws SQLException
		 */
		
		public boolean delete() throws DatabaseException {
			 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			 boolean wasDeleted = false;
				 String deleteInvItem = "DELETE FROM InventoryItems WHERE ID = ?";
					try {

						PreparedStatement DInvItemStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(deleteInvItem);
						DInvItemStmt.setInt(1,getInventoryItemID());
						DInvItemStmt.execute();
						wasDeleted = true;



					} catch (SQLException e) {
						// TODO Auto-generated catch block
					}
			return wasDeleted;

		}

		
		/**
		 * After all the information the changes wanted is set
		 *  persist then commits those changes into the database
		 * @throws DatabaseException
		 */
		
		public void persist() throws DatabaseException {
			// TODO Auto-generated method stub
			 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			 String updateInvItem = "UPDATE InventoryItems SET upc = ?, manufacturerID = ?, price = ? WHERE ID = ?";
			 String updateTools = "UPDATE Tools SET description = ? WHERE InventoryItemID = ? ";
			 String updatePowerTools = "UPDATE PowerTools SET batteryPowered = ? WHERE ToolID = ? ";

			 try {
					PreparedStatement uInvItemStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(updateInvItem);
					uInvItemStmt.setString(1, getUPC());
					uInvItemStmt.setInt(2, getManufacturerID());
					uInvItemStmt.setInt(3, getPrice());
					uInvItemStmt.setInt(4, getInventoryItemID());
					uInvItemStmt.execute();
					PreparedStatement uToolStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(updateTools);
					uToolStmt.setString(1, description);
					uToolStmt.setInt(2, getInventoryItemID());
					uToolStmt.execute();
					
					int tools_id = findToolIDForPowerTool(getInventoryItemID(), dbm);
					PreparedStatement uPowerToolStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(updatePowerTools);
					uPowerToolStmt.setBoolean(1, batteryPowered);
					uPowerToolStmt.setInt(2,tools_id);
					uPowerToolStmt.execute();

				  } catch (SQLException e) {
					  throw new DatabaseException("Tables have not been created");
				}

		}
		
		
		
		/************************************************/
		/*            PRIVATE METHODS                   */
		/************************************************/
		
		
		/**
		 * This method checks to see if the db table exists 
		 * if not it will create the table 
		 * @throws DatabaseException 
		 */

		
		/**
		 * Inserts the following into its respective table
		 * @param upc
		 * @param manufacturerID
		 * @param price
		 * @param description
		 * @param batteryPowered
		 * @throws InsertionException
		 * @throws SQLException
		 * @throws DatabaseException 
		 */
		private void insert(String upc, int manufacturerID,int price, String description, boolean batteryPowered) throws DatabaseException
		{
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			String InvItemQ = "INSERT INTO PowerToolInventoryItems(upc,manufacturerID,price) VALUES (?,?,?)";
			PreparedStatement invItemStmnt = createInventoryItem(upc, manufacturerID, price, dbm, InvItemQ);
			PreparedStatement toolStmnt = createTool(description, dbm, invItemStmnt);
			 createPowerTool(batteryPowered, dbm, toolStmnt);

			
			
		}
		/**
		 * Find the id of the tool for the powertool
		 * @param InventoryItemID
		 * @param dbm
		 * @return
		 * @throws SQLException
		 * @throws DatabaseException
		 */
		
		private int findToolIDForPowerTool(int InventoryItemID, DatabaseManager dbm) throws SQLException, DatabaseException {
			String tool_id = "SELECT id FROM Tools WHERE InventoryItemID = ? ";
			
			PreparedStatement ToolIDStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(tool_id);
			ToolIDStmt.setInt(1, InventoryItemID);
			
			 ResultSet rs2 = ToolIDStmt.executeQuery();
			  int tools_id = 0;
			  //gets the id from the Tools ID
			  if(rs2.next())
			  {
				  tools_id = rs2.getInt("id");
			  }
			return tools_id;
		}

		
		
		
		/*
		 * Inserts into inventory item
		 */
		protected PreparedStatement createInventoryItem(String upc, int manufacturerID, int price, DatabaseManager dbm,
				String InvItemQ) throws DatabaseException {
			PreparedStatement invItemStmnt;
			try {
				invItemStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(InvItemQ,Statement.RETURN_GENERATED_KEYS);
				
				 invItemStmnt.setString(1, upc);
				  invItemStmnt.setInt(2, manufacturerID);
				  invItemStmnt.setInt(3, price);
				  invItemStmnt.execute();
				  ResultSet rs2 = invItemStmnt.getGeneratedKeys();

				  //gets the id from the Tools ID
				  if(rs2.next())
				  {
					  setInventoryItemID(rs2.getInt(1));
					   
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				  throw new DatabaseException("InventoryItem Table has not created");
			}	
			return invItemStmnt;

		}
		
		/*
		 * inserts into power tool
		 */
		private void createPowerTool(boolean batteryPowered, DatabaseManager dbm, PreparedStatement toolStmnt)
				throws DatabaseException {
			  ResultSet rs2;
			try {
				rs2 = toolStmnt.getGeneratedKeys();
				 int tools_id = 0;
				  //gets the id from the Tools ID
				  if(rs2.next())
				  {
					  tools_id = rs2.getInt(1);
				  }
				  //allows me to insert the id from the Tools into Powertools

				  String PowerToolQ = "INSERT INTO PowerTools(ToolID,batteryPowered) VALUES (?, ?)";
				  PreparedStatement powerToolStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(PowerToolQ,Statement.RETURN_GENERATED_KEYS);
				  powerToolStmnt.setInt(1,tools_id);
				  powerToolStmnt.setBoolean(2, batteryPowered);
				  powerToolStmnt.execute();
			} catch (SQLException e) {
				  throw new DatabaseException("PowerTool Table has not created");
			}
			 
		}
		
		/*
		 * inserts into tool
		 */
		private PreparedStatement createTool(String description, DatabaseManager dbm, PreparedStatement invItemStmnt)
				throws DatabaseException {
			  String toolQ = "INSERT INTO Tools(InventoryItemID,description) VALUES (?, ?)";

			  //once the inventory item was executed, getGeneratedKeys will give me the results of the autogenerated ID
			  ResultSet rs;
			  PreparedStatement toolStmnt = null;
			try {
				rs = invItemStmnt.getGeneratedKeys();
				 int invItem_id = 0;
				  //gets the id from the Inventory Item ID
				  if(rs.next())
				  {
					  invItem_id = rs.getInt(1);
				  }
				  //allows me to insert the id from the InventoryItem into tools
				  toolStmnt = dbm.getConnection(dbTables.CLASS).prepareStatement(toolQ,Statement.RETURN_GENERATED_KEYS);
				  toolStmnt.setInt(1,invItem_id);
				  toolStmnt.setString(2, description);
				  toolStmnt.execute();
			} catch (SQLException e) {
				  throw new DatabaseException("Tool Table has not created");
			}
			 
			return toolStmnt;
		}
		
		


		

}
