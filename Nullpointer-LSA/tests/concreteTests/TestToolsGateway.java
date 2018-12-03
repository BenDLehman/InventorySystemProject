package concreteTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class TestToolsGateway {

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
		ToolsGateway tool = new ToolsGateway("ahhh", 88, 14, "I AM THE CREATED TOOL");
		assertNotNull(tool);

		ToolsGateway foundTool = new ToolsGateway(21);
		assertEquals(21, foundTool.getID());
		assertEquals("ahhh", foundTool.getUpc());
		assertEquals(88, foundTool.getManufacturerID());
		assertEquals(14, foundTool.getPrice());
		assertEquals("I AM THE CREATED TOOL", foundTool.getDescription());
	}

	@Test
	void testFindConstructor() throws DatabaseException {
		ToolsGateway tool = new ToolsGateway(1);

		assertNotNull(tool);
		assertEquals(1, tool.getID());
		assertEquals("123", tool.getUpc());
		assertEquals("I AM TOOL NUMBER ONE", tool.getDescription());
	}

	@Test @SuppressWarnings("unused")
	public void testFindWithBadID() throws DatabaseException {

		Assertions.assertThrows(DatabaseException.class, () -> {		
			ToolsGateway tool = new ToolsGateway(-1);
		});
	}

	@Test
	public void testFindByManID() throws DatabaseException {
		ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();

		tools = ToolsGateway.findByManufacturerID(55);

		assertNotNull(tools);
		assertEquals(2, tools.size());

		assertEquals(1, tools.get(0).getId());
		assertEquals(2, tools.get(1).getId());

		assertEquals("I AM TOOL NUMBER ONE", tools.get(0).getDescription());
		assertEquals("I AM TOOL NUMBER TWO", tools.get(1).getDescription());

	}

	@Test
	public void testFindByUPC() throws DatabaseException {
		ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();

		tools = ToolsGateway.findByUpc("1234");

		assertNotNull(tools);
		assertEquals(2, tools.size());

		assertEquals(2, tools.get(0).getId());
		assertEquals(5, tools.get(1).getId());

		assertEquals("I AM TOOL NUMBER TWO", tools.get(0).getDescription());
		assertEquals("I AM TOOL NUMBER FIVE", tools.get(1).getDescription());

	}

	@Test
	public void testFindAll() throws DatabaseException {
		ArrayList<ToolDTO> tools = new ArrayList<ToolDTO>();

		tools = ToolsGateway.findAll();

		assertNotNull(tools);
		assertEquals(5, tools.size());
		assertEquals("I AM TOOL NUMBER ONE", tools.get(0).getDescription());
		assertEquals("I AM TOOL NUMBER TWO", tools.get(1).getDescription());
		assertEquals("I AM TOOL NUMBER THREE", tools.get(2).getDescription());
		assertEquals("I AM TOOL NUMBER FOUR", tools.get(3).getDescription());
		assertEquals("I AM TOOL NUMBER FIVE", tools.get(4).getDescription());
	}

	/**
	 * Test that delete returns true when successful, and False when there is a
	 * failure
	 */
	@Test
	public void testDelete() throws DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		ToolsGateway tool = new ToolsGateway(3);
		String query = "SELECT * FROM Tools WHERE ID = ?";

		try {
			PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt1.setInt(1, 3);
			ResultSet rs = stmnt1.executeQuery();

			assertTrue(rs.next());
			assertTrue(tool.delete());

			PreparedStatement stmnt2 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
			stmnt2.setInt(1, 3);
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
		ToolsGateway tool = new ToolsGateway(1);

		assertNotNull(tool);
		assertEquals(1, tool.getID());
		assertEquals("123", tool.getUpc());
		assertEquals("I AM TOOL NUMBER ONE", tool.getDescription());

		tool.setUpc("12345");
		tool.setManufacturerID(24);
		tool.setPrice(21);
		tool.setDescription("I AM STILL TOOL NUMBER ONE");

		tool.persist();

		assertEquals(1, tool.getID());
		assertEquals("12345", tool.getUpc());
		assertEquals(24, tool.getManufacturerID());
		assertEquals(21, tool.getPrice());
		assertEquals("I AM STILL TOOL NUMBER ONE", tool.getDescription());
	}

	/**
	 * Tests that persist can update some data and pass through the previous data
	 * that is not updated.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeSome() throws DatabaseException {
		ToolsGateway tool = new ToolsGateway(1);

		assertNotNull(tool);
		assertEquals(1, tool.getID());
		assertEquals("123", tool.getUpc());
		assertEquals("I AM TOOL NUMBER ONE", tool.getDescription());

		tool.setUpc("12345");
		tool.setManufacturerID(24);

		tool.persist();

		assertEquals(1, tool.getID()); // Updated
		assertEquals("12345", tool.getUpc()); // Updated
		assertEquals(24, tool.getManufacturerID()); // Updated
		assertEquals(12, tool.getPrice()); // Original
		assertEquals("I AM TOOL NUMBER ONE", tool.getDescription()); // Original
	}
}