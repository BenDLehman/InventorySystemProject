import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import exception.DatabaseException;

class TestDatabaseManager {

	 
		@Test
		public void testCreateClassConnection() throws DatabaseException
		{
		   Connection connection = null;
           connection = DatabaseManager.getDatabaseManager().getConnection(dbTables.CLASS);
           assertNotNull(connection);
			
		
		}
		
		@Test
		public void testCreateConcreteConnection() throws DatabaseException
		{
		   Connection connection = null;
           connection = DatabaseManager.getDatabaseManager().getConnection(dbTables.CONCRETE);
           assertNotNull(connection);
			
		
		}
		
		@Test
		public void testCreateSingleConnection() throws DatabaseException
		{
		   Connection connection = null;
           connection = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE);
           assertNotNull(connection);
			
		
		}

}
