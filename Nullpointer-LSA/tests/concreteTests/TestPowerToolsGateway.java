package concreteTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceConcrete.*;
import datasource.*;
import exception.DatabaseException;

class TestPowerToolsGateway {
	
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
	void testPowerToolCreateConstructor() throws DatabaseException {		
		PowerToolsGateway createdItem = new PowerToolsGateway("123", 67, 10, "I AM THE INSERTED POWERTOOL", true);
		assertNotNull(createdItem);
		
		PowerToolsGateway foundItem = new PowerToolsGateway(createdItem.getID());
		
		assertEquals("123", foundItem.getUpc());
		assertEquals(67, foundItem.getManufacturerID());
		assertEquals(10, foundItem.getPrice());
		assertEquals("I AM THE INSERTED POWERTOOL", foundItem.getDescription());
		assertTrue(foundItem.isBatteryPowered());
		}
    
	@Test
	void testFindConstructor() throws DatabaseException {		
		PowerToolsGateway powerTool = new PowerToolsGateway(6);
		assertNotNull(powerTool);
		
		assertEquals(6, powerTool.getID());
		assertEquals("123", powerTool.getUpc());
		assertEquals(55, powerTool.getManufacturerID());
		assertEquals(12, powerTool.getPrice());
		assertEquals("I AM POWERTOOL NUMBER ONE", powerTool.getDescription());
		assertTrue(powerTool.isBatteryPowered());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testFindWithBadID() throws DatabaseException {
		
		 Assertions.assertThrows(DatabaseException.class, () -> {
	    	 PowerToolsGateway powerTool = new PowerToolsGateway(-1);
	     });

	}
	
	@Test
	public void testFindByManID() throws DatabaseException {
		ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
		powerTools = PowerToolsGateway.findByManufacturerID(55);
		
		assertNotNull(powerTools);
		assertEquals(2, powerTools.size());
		
		assertEquals(6, powerTools.get(0).getId());
		assertEquals(7, powerTools.get(1).getId());	
	}
	
	@Test
	public void testFindByUPC() throws DatabaseException {
		ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
		powerTools = PowerToolsGateway.findByUpc("1234");
			
		assertNotNull(powerTools);
		assertEquals(2, powerTools.size());
		
		assertEquals(7, powerTools.get(0).getId());
		assertEquals(10, powerTools.get(1).getId());
	}
	
	@Test
	public void testFindAll() throws DatabaseException  {
		ArrayList<PowerToolDTO> powerTools = new ArrayList<PowerToolDTO>();
		powerTools = PowerToolsGateway.findAll();
			
		assertNotNull(powerTools);
		assertEquals(5, powerTools.size());
		
		assertEquals(6, powerTools.get(0).getId());
		assertEquals(7, powerTools.get(1).getId());
		assertEquals(8, powerTools.get(2).getId());
		assertEquals(9, powerTools.get(3).getId());
		assertEquals(10, powerTools.get(4).getId());
		}
	
	/**
	 * Test that PowerTool is deleted
	 */
	@Test
	public void testDelete() throws SQLException, DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PowerToolsGateway powerTool = new PowerToolsGateway(9);
		String query = "SELECT * FROM PowerTools WHERE ID = ?";
		
		PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt1.setInt(1, 9);
		ResultSet rs = stmnt1.executeQuery();
		
		assertTrue(rs.next());
		assertTrue(powerTool.delete());

		PreparedStatement stmnt2 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt2.setInt(1, 9);
		rs = stmnt2.executeQuery();
		
		assertFalse(rs.next());
	}
	
	/**
	 * Test that PowerTool is deleted
	 */
	@Test
	public void testDeleteRelation() throws SQLException, DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PowerToolsGateway powerTool = new PowerToolsGateway(8);
		String query = "SELECT * FROM PowerToolsToStripNails WHERE powerToolID = ?";
		
		PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt1.setInt(1, 8);
		ResultSet rs = stmnt1.executeQuery();
		
		assertTrue(rs.next());
		assertTrue(powerTool.delete());

		PreparedStatement stmnt2 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt2.setInt(1, 8);
		rs = stmnt2.executeQuery();
		
		assertFalse(rs.next());
	}
	
	/**
	 * Tests that persist updates everything in the row where the id's match.
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeAll() throws DatabaseException {
		PowerToolsGateway tool = new PowerToolsGateway(6);
		
		assertNotNull(tool);
		assertEquals(6, tool.getID());
		assertEquals("123", tool.getUpc());
		assertEquals("I AM POWERTOOL NUMBER ONE", tool.getDescription());
		
		tool.setUpc("12345");
		tool.setManufacturerID(24);
		tool.setPrice(21);
		tool.setDescription("I AM STILL POWERTOOL NUMBER ONE");
		
		tool.persist();
		
		assertEquals(6, tool.getID());
		assertEquals("12345", tool.getUpc());
		assertEquals(24, tool.getManufacturerID());
		assertEquals(21, tool.getPrice());
		assertEquals("I AM STILL POWERTOOL NUMBER ONE", tool.getDescription());
	}
	
	/**
	 * Tests that persist can update some data and pass through the previous data that is not updated.
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeSome() throws DatabaseException {
		PowerToolsGateway tool = new PowerToolsGateway(6);
		  
		assertNotNull(tool);
		assertEquals(6, tool.getID());
		assertEquals("123", tool.getUpc());
		assertEquals("I AM POWERTOOL NUMBER ONE", tool.getDescription());
		
		tool.setUpc("12345");
		tool.setManufacturerID(24);
		
		tool.persist();
		 
		assertEquals(6, tool.getID()); //Updated
		assertEquals("12345", tool.getUpc()); //Updated
		assertEquals(24, tool.getManufacturerID()); //Updated
		assertEquals(12, tool.getPrice()); //Original
		assertEquals("I AM POWERTOOL NUMBER ONE", tool.getDescription()); //Original
	}

	
}
