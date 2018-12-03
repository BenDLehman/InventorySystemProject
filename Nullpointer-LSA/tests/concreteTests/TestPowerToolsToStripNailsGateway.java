package concreteTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.*;
import datasourceConcrete.*;
import exception.DatabaseException;

class TestPowerToolsToStripNailsGateway {

	Connection c = null;

    @BeforeEach
    public void setup() throws DatabaseException {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			c = dbm.getConnection(dbTables.CONCRETE);
			c.setAutoCommit(false);
			ConcreteTableHelper.main(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
    }
    
    @AfterEach
    public void rollback() {
    	try {
			c.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
    }
    
    @Test
	public void testFindAll() throws DatabaseException  {
		ArrayList<PowerToolToStripNailDTO> relations = new ArrayList<PowerToolToStripNailDTO>();
		
		relations = PowerToolsToStripNailsGateway.findAll();
		
		assertNotNull(relations);
		assertEquals(5, relations.size());
		
		assertEquals(16, relations.get(0).getStripNailId());
		assertEquals(16, relations.get(1).getStripNailId());
		assertEquals(17, relations.get(2).getStripNailId());
		assertEquals(18, relations.get(3).getStripNailId());
		assertEquals(20, relations.get(4).getStripNailId());
		
		assertEquals(6, relations.get(0).getPowerToolId());
		assertEquals(7, relations.get(1).getPowerToolId());
		assertEquals(8, relations.get(2).getPowerToolId());
		assertEquals(8, relations.get(3).getPowerToolId());
		assertEquals(9, relations.get(4).getPowerToolId());
		
	}
    
    @Test
   	public void testFindByPowerTool() throws DatabaseException  {
   		ArrayList<PowerToolToStripNailDTO> relations = new ArrayList<PowerToolToStripNailDTO>();
   		relations = PowerToolsToStripNailsGateway.findStripNailByPowerToolID(8);
   		
   		assertNotNull(relations);
   		assertEquals(2, relations.size());
   		
   		assertEquals(17, relations.get(0).getStripNailId());
   		assertEquals(18, relations.get(1).getStripNailId());
   	}
    
    @Test
   	public void testFindByStripNail() throws DatabaseException  {
   		ArrayList<PowerToolToStripNailDTO> relations = new ArrayList<PowerToolToStripNailDTO>();
   		relations = PowerToolsToStripNailsGateway.findPowerToolByStripNailID(16);
   		
   		assertNotNull(relations);
   		assertEquals(2, relations.size());
   		
   		assertEquals(6, relations.get(0).getPowerToolId());
   		assertEquals(7, relations.get(1).getPowerToolId());
   	}
    
    @Test
	void testCreateConstructor() throws DatabaseException {
    	PowerToolsToStripNailsGateway relation = new PowerToolsToStripNailsGateway(10, 19);
		assertNotNull(relation);
		
		PowerToolsToStripNailsGateway newRelation = new PowerToolsToStripNailsGateway(relation.getID());
		assertEquals(10, newRelation.getPowerToolID());
		assertEquals(19, newRelation.getStripNailID());
	}
    
    @Test
	void testFindConstructor() throws DatabaseException {
    	PowerToolsToStripNailsGateway relation = new PowerToolsToStripNailsGateway(5);
		assertNotNull(relation);
		
		assertEquals(9, relation.getPowerToolID());
		assertEquals(20, relation.getStripNailID());
	}
    
    @Test
	public void testDeleteID() throws DatabaseException {
    	PowerToolsToStripNailsGateway relation = new PowerToolsToStripNailsGateway(1);
		assertNotNull(relation);
		
		assertEquals(6, relation.getPowerToolID());
		assertEquals(16, relation.getStripNailID());
		assertTrue(relation.delete());
		assertFalse(relation.delete());
	}
    
    
}

