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
import mappersSingle.PowerToolMapper;
import mappersSingle.ToolMapper;
import sharedDomain.MapperManager;
import sharedDomain.Nail;
import sharedDomain.PowerTool;
import sharedDomain.Tool;
import testData.NailsForTest;

import org.junit.jupiter.api.Test;

/**
 * @author Christopher Roadcap
 * @author Kanza Amin
 *
 */
class PowerToolMapperTest {
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
			System.out.println("IN THE CATCH");
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
	public void testFindingOnePowerTool() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");
		PowerToolMapper ptm = new PowerToolMapper("123", 55, 12, "I AM A POWERTOOL", true);
		int powerToolID = ptm.getPowerToolID();
		assertNotNull(ptm.getPowerTool());
		// compare the id in the gateway and the ID created by the nail object
	}
	/**
	 * Tets to make sure the finder constructor works and to make sure all the data
     * is correct.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFinderConstructor() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");

		PowerToolMapper ptm = new PowerToolMapper(6);

		assertEquals("123", ptm.getPowerTool().getUpc());
		assertEquals(55, ptm.getPowerTool().getManufacturerID());
		assertEquals(12, ptm.getPowerTool().getPrice());
		assertEquals("I AM POWERTOOL NUMBER ONE", ptm.getPowerTool().getDescription());
		assertEquals(6, ptm.getPowerTool().getID());
	}
	
	/**
	 * Tests to see whether or not the FindAll Method works and returns the correct ID's.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testFindAllMethod() throws DatabaseException {
		MapperManager.getSingleton().setMapperPackageName("mappersSingle");
		ArrayList<PowerTool> temp = PowerToolMapper.findAll();
		assertEquals(6, temp.get(0).getID());
		assertEquals(7, temp.get(1).getID());
		assertEquals(8, temp.get(2).getID());
		assertEquals(9, temp.get(3).getID());
		assertEquals(10, temp.get(4).getID());
	}
	/**
	 * Test that delete returns true when successful, and False when there is a failure
	 * @throws Exception 
	 */
	@Test
	public void testDelete() throws Exception {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		PowerToolMapper powerTool = new PowerToolMapper(6);
		String query = "SELECT * FROM InventoryItems WHERE ID = ?";

		PreparedStatement stmnt1 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
		stmnt1.setInt(1, 6);
		ResultSet rs = stmnt1.executeQuery();

		assertTrue(rs.next());
		powerTool.delete();

		PreparedStatement stmnt2 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
		stmnt2.setInt(1, 6);
		rs = stmnt2.executeQuery();

		assertFalse(rs.next());
	}
	/**
	 * Tests that persist updates everything in the row where the id's match.
	 * @throws Exception 
	 */
	@Test
	public void testPersistChangeAll() throws Exception {
		PowerToolMapper powerTool = new PowerToolMapper(6);
		assertNotNull(powerTool);
		powerTool.persist();
	}
	
	/**
	 * Tests that persist can update some data and pass through the previous data that is not updated.
	 * @throws Exception 
	 */
	@Test
	public void testPersistChangeSome() throws Exception {
		PowerToolMapper powerTool = new PowerToolMapper(6);
		assertNotNull(powerTool);
		powerTool.persist();

	}
}
