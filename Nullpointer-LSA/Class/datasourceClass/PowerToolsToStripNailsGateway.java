package datasourceClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolToStripNailDTO;
import exception.DatabaseException;

public class PowerToolsToStripNailsGateway {
	private int PowerToolID;
	private int StripNailID;
	private int ID;
	private boolean ResultExists;


	
	public static ArrayList<PowerToolToStripNailDTO> findPowerToolByStripNailID(int StripNailID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String Query = "SELECT PowerToolID FROM PowerToolsToStripNails WHERE StripNailID = ?";
		ArrayList<PowerToolToStripNailDTO> PTSN = new ArrayList<PowerToolToStripNailDTO>();

		try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query,Statement.RETURN_GENERATED_KEYS);
			QStmt.setInt(1, StripNailID);
			ResultSet rs = QStmt.executeQuery();
			  while(rs.next())
			  {
				  PowerToolToStripNailDTO ptsnDTO = new PowerToolToStripNailDTO(rs.getInt("PowerToolID"),StripNailID);
				  PTSN.add(ptsnDTO);
				  rs.getInt("PowerToolID");
			  }
			
		}catch(SQLException e)
		{
			throw new DatabaseException("Entry could not be found");

		}
		return PTSN;
	}

	public static ArrayList<PowerToolToStripNailDTO> findStripNailByPowerToolID(int powerToolID) throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String Query = "SELECT StripNailID FROM PowerToolsToStripNails WHERE PowerToolID = ?";
		ArrayList<PowerToolToStripNailDTO> PTSN = new ArrayList<PowerToolToStripNailDTO>();
		try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query);
			QStmt.setInt(1, powerToolID);
			ResultSet rs = QStmt.executeQuery();
			  while(rs.next())
			  {
				  PowerToolToStripNailDTO ptsnDTO = new PowerToolToStripNailDTO(powerToolID,rs.getInt("StripNailID"));
				  PTSN.add(ptsnDTO);
				  rs.getInt("StripNailID");
			  }
			
		}catch(SQLException e)
		{
			throw new DatabaseException("Entry could not be found");

		}
		return PTSN;
		
		
	}
	
	public static ArrayList<PowerToolToStripNailDTO> findAll() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String Query = "SELECT PowerToolID,StripNailID FROM PowerToolsToStripNails";
		ArrayList<PowerToolToStripNailDTO> PTSN = new ArrayList<PowerToolToStripNailDTO>();
		try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query);
			ResultSet rs = QStmt.executeQuery();
			  while(rs.next())
			  {
				  PowerToolToStripNailDTO ptsnDTO = new PowerToolToStripNailDTO(rs.getInt("PowerToolID"),rs.getInt("StripNailID"));
				  PTSN.add(ptsnDTO);
			  }
			
		}catch(SQLException e)
		{
		}
		return PTSN;
		
	}
	/************************************************/
	/*        CREATE CONSTRUCTOR/ FIND CONSTRUCTOR  */
	/************************************************/
	
	public PowerToolsToStripNailsGateway(int PowerToolID, int StripNailID) throws DatabaseException {
		insert(PowerToolID,StripNailID);
		this.PowerToolID = PowerToolID;
		this.StripNailID = StripNailID;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public PowerToolsToStripNailsGateway(int id) throws DatabaseException {
        DatabaseManager dbm = DatabaseManager.getDatabaseManager();
        String Query = "SELECT PowerToolID,StripNailID FROM PowerToolsToStripNails WHERE ID = ?";
        
        try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query);	
			QStmt.setInt(1, id);
			ResultSet rs = QStmt.executeQuery();
			while(rs.next())
			{
				setPowerToolID(rs.getInt("PowerToolID"));
				setStripNailID(rs.getInt("StripNailID"));
				setResultExistence(true);
				
			}
			 if(getResultExistence() != true){	  
				  
	             throw new DatabaseException("POWERTOOL TO STRIPNAIL ITEM WITH ID: "+id+" WAS NOT FOUND.");
			  }
		} catch (SQLException e) {

		}
	}
	
	
	/************************************************/
	/*                     SETTERS                  */
	/************************************************/
	//sets the automated ID

	private void setID(int id)
	{
		ID = id;
	}
	//sets the PowerToolID

	public void setPowerToolID(int powerToolID)
	{
		PowerToolID = powerToolID;
	}
	//sets the stripNailID variable


	public void setStripNailID(int stripNailID) {
		StripNailID = stripNailID;
	}
	//sets the resultexist variable
	private void setResultExistence(boolean re)
	{
		 ResultExists = re;
	}
	/************************************************/
	/*                   GETTERS                    */
	/************************************************/
	//gets the automated if
	public int getID()
	{
		return ID;
	}
	

	public int getPowerToolID() {
		// TODO Auto-generated method stub
		return PowerToolID;
	}
	private boolean getResultExistence()
	{
		return ResultExists;
	}
	
	

	public int getStripNailID() {
		// TODO Auto-generated method stub
		return StripNailID;
	}


	
	/************************************************/
	/*                   STATIC METHODS             */
	/************************************************/
	
	/**
	 * Inserts the entry
	 * @param powerToolID
	 * @param stripNailID
	 * @throws DatabaseException
	 */
	
private void insert(int powerToolID, int stripNailID) throws DatabaseException {
		
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String Query = "INSERT INTO PowerToolsToStripNails(PowerToolID,StripNailID) VALUES (?,?)";
		try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query,Statement.RETURN_GENERATED_KEYS);
			QStmt.setInt(1, powerToolID);
			QStmt.setInt(2,stripNailID);
			QStmt.execute();
			ResultSet rs = QStmt.getGeneratedKeys();
			  if(rs.next())
			  {
				  setID(rs.getInt(1));
			  }
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
/**
 * Once when one or more variables are changed then this methods commits those changes to the database
 * @throws DatabaseException
 */

public void persist() throws DatabaseException
{
	 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	 String Query = "UPDATE PowerToolsToStripNails SET PowerToolID = ?, StripNailID = ?";
        try {
			PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query);	
			QStmt.setInt(1, getPowerToolID());
			QStmt.setInt(2, getStripNailID());
			QStmt.execute();
		
		} catch (SQLException e) {
		}
}


/**
 * Deletes the entry
 * @param id
 * @return
 * @throws DatabaseException
 */
public boolean delete() throws DatabaseException
{
	boolean wasDeleted = false;
	 DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	 String Query = "DELETE FROM PowerToolsToStripNails WHERE StripNailID = ? OR PowerToolID = ?";
        try {
			  PreparedStatement QStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(Query);	
			  QStmt.setInt(1,getStripNailID());
			  QStmt.setInt(2,getPowerToolID());

			  QStmt.execute();
			  wasDeleted = true;
		
		} catch (SQLException e) {
		}
        return wasDeleted;
}





}