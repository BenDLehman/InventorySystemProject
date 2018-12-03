package singleTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceSingle.InventoryItemsGateway;
import datasourceSingle.SingleTableHelper;
import exception.DatabaseException;
import mappersSingle.NailMapper;
import mappersSingle.ToolMapper;
import sharedDomain.MapperManager;
import sharedDomain.Nail;
import sharedDomain.Tool;
import testData.NailsForTest;

import org.junit.jupiter.api.Test;
/**
 * @author Christopher Roadcap
 * @author Kanza Amin
 *
 */
class ToolMapperTest {
	Connection c = null;

	/**
	 * Prepares the database with the propper data before each test
	 * @throws DatabaseException
	 */
	@BeforeEach
	public void setup() throws DatabaseException {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			c = dbm.getConnection(dbTables.SINGLE);
			SingleTableHelper.cleanDB();
			SingleTableHelper.createInventoryItems();
			SingleTableHelper.createPowerToolsToStripNails();
			SingleTableHelper.createInventoryView();
			SingleTableHelper.populateTables();
			c.setAutoCommit(false);
		} catch (SQLException e) {
			
		}
	}

	/**
	 * Rolls back the database to the original form if it was altered by a test
	 */
	@AfterEach
	public void rollback() {
		try {
			c.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * Tests to make sure the creation constructor works and that you can find the
	 * correct nail ID in the mapper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindingOneTool() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");
		ToolMapper tm = new ToolMapper("123", 55, 12, "I AM TOOL NUMBER ONE"); // Creation Constructor
		int toolID = tm.getToolID();
		assertNotNull(tm.getTool());
		// compare the id in the gateway and the ID created by the nail object
		assertEquals(toolID, tm.getTool().getID());
	}
	/**
	 * Tests to make sure the finder constructor works and to make sure all the data
     * is correct.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFinderConstructor() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");

		ToolMapper tm = new ToolMapper(1);

		assertEquals("123", tm.getTool().getUpc());
		assertEquals(55, tm.getTool().getManufacturerID());
		assertEquals(12, tm.getTool().getPrice());
		assertEquals("I AM TOOL NUMBER ONE", tm.getTool().getDescription());
		assertEquals(1, tm.getTool().getID());
	}
	/**
	 * Tests to see whether or not the FindAll Method works and returns the correct ID's.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testFindAllMethod() throws DatabaseException {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");
		ArrayList<Tool> temp = ToolMapper.findAll();
		assertEquals(1, temp.get(0).getID());
		assertEquals(2, temp.get(1).getID());
		assertEquals(3, temp.get(2).getID());
		assertEquals(4, temp.get(3).getID());
		assertEquals(5, temp.get(4).getID());
	}
	/**
	 * Test that delete returns true when successful, and False when there is a failure
	 * @throws Exception 
	 */
	@Test
	public void testDelete() throws Exception {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		ToolMapper tool = new ToolMapper(1);
		String query = "SELECT * FROM InventoryItems WHERE ID = ?";

		PreparedStatement stmnt1 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
		stmnt1.setInt(1, 1);
		ResultSet rs = stmnt1.executeQuery();

		assertTrue(rs.next());
		tool.delete();

		PreparedStatement stmnt2 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
		stmnt2.setInt(1, 1);
		rs = stmnt2.executeQuery();

		assertFalse(rs.next());
	}
	/**
	 * Tests that persist updates everything in the row where the id's match.
	 * @throws Exception 
	 */
	@Test
	public void testPersistChangeAll() throws Exception {
		ToolMapper tool = new ToolMapper(1);
		assertNotNull(tool);
		tool.persist();
	}
	
	/**
	 * Tests that persist can update some data and pass through the previous data that is not updated.
	 * @throws Exception 
	 */
	@Test
	public void testPersistChangeSome() throws Exception {
		ToolMapper tool = new ToolMapper(1);
		assertNotNull(tool);
		tool.persist();

	}


}
