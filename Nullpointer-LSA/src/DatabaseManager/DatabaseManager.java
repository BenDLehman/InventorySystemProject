package DatabaseManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseException;

public class DatabaseManager
{

	public static String DB_LOCATION;
	public static final String LOGIN_NAME = "swe400_2";
	public static final String PASSWORD = "pwd4swe400_2F16";
	protected static Connection dbmConn = null;
	protected static DatabaseManager dbm = null;
	DatabaseMetaData meta = null;

	public enum dbTables
	{
		SINGLE, CONCRETE, CLASS
	}

	// singleton so that only one database manager exists
	public static DatabaseManager getDatabaseManager()
	{

		if (dbm == null)
		{
			dbm = new DatabaseManager();
		}
		return dbm;
	}

	private DatabaseManager()
	{
		activateJDBC();
	}

	// depending on the pattern the database location changes
	public Connection getConnection(dbTables dbt) throws DatabaseException
	{

		if (dbmConn == null)
		{
			try
			{
				if (dbt == dbTables.CLASS)
				{
					setDatabaseLocation(1);
					dbmConn = DriverManager.getConnection(getDataBaseLocation(), LOGIN_NAME, PASSWORD);
					System.out.println("DB Connection created successfully");
				}
				if (dbt == dbTables.SINGLE)
				{
					setDatabaseLocation(2);
					dbmConn = DriverManager.getConnection(getDataBaseLocation(), LOGIN_NAME, PASSWORD);
					System.out.println("DB Connection created successfully");
				}
				if (dbt == dbTables.CONCRETE)
				{
					setDatabaseLocation(3);
					dbmConn = DriverManager.getConnection(getDataBaseLocation(), LOGIN_NAME, PASSWORD);
					System.out.println("DB Connection created successfully");
				}
			} catch (SQLException sqle)
			{
				throw new DatabaseException("Unable to connect to database ");
			}

		}

		return dbmConn;
	}

	/**
	 * SET THE NUMBER AND THEN IT WILL GO TO THE RIGHT DB PLACE
	 * 
	 * @param number
	 */
	public void setDatabaseLocation(int number)
	{
		DB_LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/swe400-2" + number;
	}

	private static String getDataBaseLocation()
	{
		return DB_LOCATION;
	}

	private boolean activateJDBC()
	{
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException sqle)
		{

			return false;
		}

		return true;
	}

	public boolean hasConnection()
	{
		if (dbmConn == null)
		{
			return false;
		}

		return true;
	}

}
