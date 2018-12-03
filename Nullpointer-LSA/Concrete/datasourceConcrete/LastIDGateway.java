package datasourceConcrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import exception.DatabaseException;


public class LastIDGateway {

	private LastIDGateway() {}
	
	
	//Returns true if the ID is in the table meaning its in use and false if it isn't
	public static boolean checkID(int ID) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String checkRowStatement = "select ID from LastID where ID = ?";
		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(checkRowStatement);
			stmnt.setInt(1, ID);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next())
			{ 
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Returns the next ID. It looks through the entire ID map and sets lastID to the lowest unused ID
	public static int getNextID() throws DatabaseException
	{
		int nextID = 1;
		while(checkID(nextID))
		{
			nextID++;
		}
		insertNextID(nextID);
		return nextID;
	}	
	
	//Deletes the row matching the given ID from the table
	public static void deleteID(int id) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String checkRowStatement = "DELETE FROM LastID WHERE ID = ?";
		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(checkRowStatement);
			stmnt.setInt(1, id);
			stmnt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//Inserts a row containing the ID given
	private static void insertNextID(int ID) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String checkRowStatement = "INSERT INTO LastID VALUES(?)";
		try {
			PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(checkRowStatement);
			stmnt.setInt(1, ID);
			stmnt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	


	
}
