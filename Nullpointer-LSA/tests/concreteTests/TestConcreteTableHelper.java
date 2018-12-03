package concreteTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.sql.PreparedStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.*;
import datasourceConcrete.*;
import exception.DatabaseException;

class TestConcreteTableHelper {

	

    @BeforeEach
    public void setup() throws DatabaseException {
		
    }
    
    @AfterEach
    public void buildDB() throws DatabaseException {
    	
    }
    
    @Test
	void testDropTables() throws DatabaseException {		
    	boolean tablesDropped = false;
		try {
			ConcreteTableHelper.dropAllTables();
			String query = "Select * from Tools where ID = ?";
			PreparedStatement stmnt = DatabaseManager.getDatabaseManager().getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt.executeQuery();
		}
		catch(Exception e)  
		{
			tablesDropped = true;
		}
		assertTrue(tablesDropped);
		
    	ConcreteTableHelper.createTables();
    	ConcreteTableHelper.populateTables();
		}
    
    @Test
	void testCreateAndPopulateTables() throws DatabaseException {		
			ConcreteTableHelper.dropAllTables();
			ConcreteTableHelper.createTables();
			ConcreteTableHelper.populateTables();
			
			ToolsGateway tool = new ToolsGateway(1);
			assertEquals(1, tool.getID());
			PowerToolsGateway powerTool = new PowerToolsGateway(6);
			assertEquals(6, powerTool.getID());
			NailsGateway nail = new NailsGateway(11);
			assertEquals(11, nail.getID());
			StripNailsGateway stripNail = new StripNailsGateway(16);
			assertEquals(16, stripNail.getID());
			PowerToolsToStripNailsGateway relation = new PowerToolsToStripNailsGateway(1);
			assertEquals(1, relation.getID());
			
			ConcreteTableHelper.dropAllTables();
			ConcreteTableHelper.createTables();
			ConcreteTableHelper.populateTables();
		}

}
