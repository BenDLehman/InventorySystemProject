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
import datasource.*;
import datasourceConcrete.*;

import exception.DatabaseException;

class TestStripNailsGateway {

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
	void testCreateConstructor() throws DatabaseException {
		StripNailsGateway stripNail = new StripNailsGateway("777", 76, 9, 4, 10);
		assertNotNull(stripNail);

		StripNailsGateway foundStripNail = new StripNailsGateway(21);
		assertEquals(21, foundStripNail.getID());
		assertEquals("777", foundStripNail.getUpc());
		assertEquals(76, foundStripNail.getManufacturerID());
		assertEquals(9, foundStripNail.getPrice());
		assertEquals(4, foundStripNail.getLength());
		assertEquals(10, foundStripNail.getNumberInStrip());
	}

	@Test
	void testFindConstructor() throws DatabaseException {
		StripNailsGateway stripNail = new StripNailsGateway(17);
		assertNotNull(stripNail);

		assertEquals(17, stripNail.getID());
		assertEquals("9999", stripNail.getUpc());
		assertEquals(23, stripNail.getManufacturerID());
		assertEquals(25, stripNail.getPrice());
		assertEquals(4, stripNail.getLength());
		assertEquals(30, stripNail.getNumberInStrip());
	}

	@SuppressWarnings("unused")
	@Test
	public void testFindWithBadID() throws DatabaseException {
		Assertions.assertThrows(DatabaseException.class, () -> {
			StripNailsGateway stripNail = new StripNailsGateway(-1);
		});
	}

	@Test
	public void testFindByManID() throws DatabaseException {
		ArrayList<StripNailDTO> stripNails = new ArrayList<StripNailDTO>();

		stripNails = StripNailsGateway.findByManufacturerID(55);

		assertNotNull(stripNails);
		assertEquals(2, stripNails.size());

		assertEquals(18, stripNails.get(0).getId());
		assertEquals(19, stripNails.get(1).getId());
	}

	@Test
	public void testFindByUPC() throws DatabaseException {
		ArrayList<StripNailDTO> stripNails = new ArrayList<StripNailDTO>();

		stripNails = StripNailsGateway.findByUpc("9999");

		assertNotNull(stripNails);
		assertEquals(2, stripNails.size());

		assertEquals(17, stripNails.get(0).getId());
		assertEquals(20, stripNails.get(1).getId());

		assertEquals("9999", stripNails.get(0).getUpc());
		assertEquals("9999", stripNails.get(1).getUpc());
	}

	@Test
	public void testFindAll() throws DatabaseException {
		ArrayList<StripNailDTO> stripNails = new ArrayList<StripNailDTO>();

		stripNails = StripNailsGateway.findAll();

		assertNotNull(stripNails);
		assertEquals(5, stripNails.size());

		assertEquals(16, stripNails.get(0).getId());
		assertEquals(17, stripNails.get(1).getId());
		assertEquals(18, stripNails.get(2).getId());
		assertEquals(19, stripNails.get(3).getId());
		assertEquals(20, stripNails.get(4).getId());
	}

	/**
	 * Test that delete returns true when successful, and False when there is a
	 * failure
	 */
	@Test
	public void testDelete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		StripNailsGateway stripNail = new StripNailsGateway(18);
		String query = "SELECT * FROM StripNails WHERE ID = ?";

		try {
			PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt1.setInt(1, 18);
			ResultSet rs = stmnt1.executeQuery();

			assertTrue(rs.next());
			assertTrue(stripNail.delete());

			PreparedStatement stmnt2 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt2.setInt(1, 18);
			rs = stmnt2.executeQuery();

			assertFalse(rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Tests that persist updates everything in the row where the id's match.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeAll() throws DatabaseException {
		StripNailsGateway stripNail = new StripNailsGateway(16);
		assertNotNull(stripNail);
		assertEquals(16, stripNail.getID());

		stripNail.setUpc("98765");
		stripNail.setManufacturerID(24);
		stripNail.setPrice(21);
		stripNail.setLength(2);
		stripNail.setNumberInStrip(100);

		stripNail.persist();

		assertEquals(16, stripNail.getID());
		assertEquals("98765", stripNail.getUpc());
		assertEquals(24, stripNail.getManufacturerID());
		assertEquals(21, stripNail.getPrice());
		assertEquals(2, stripNail.getLength());
		assertEquals(100, stripNail.getNumberInStrip());
	}

	/**
	 * Tests that persist can update some data and pass through the previous data
	 * that is not updated.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeSome() throws DatabaseException {
		StripNailsGateway stripNail = new StripNailsGateway(16);
		assertNotNull(stripNail);
		assertEquals(16, stripNail.getID());

		stripNail.setUpc("98765");
		stripNail.setManufacturerID(24);
		stripNail.setNumberInStrip(100);

		stripNail.persist();

		assertEquals(16, stripNail.getID()); // ID
		assertEquals("98765", stripNail.getUpc()); // Original Value -> "987" | updated to "98765".
		assertEquals(24, stripNail.getManufacturerID()); // original value ->23 | updated to 24.
		assertEquals(20, stripNail.getPrice()); // original value -> 20
		assertEquals(4, stripNail.getLength()); // Original Value -> 4
		assertEquals(100, stripNail.getNumberInStrip()); // Original Value -> 50 | updated to 100.
	}
}
