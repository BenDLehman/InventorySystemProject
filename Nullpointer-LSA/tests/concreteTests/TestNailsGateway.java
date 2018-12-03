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

class TestNailsGateway {

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
    	NailsGateway nail = new NailsGateway("999", 67, 6, 3, 100);
		assertNotNull(nail);
		
		NailsGateway foundNail = new NailsGateway(21);
		assertEquals(21, foundNail.getID());
		assertEquals("999", foundNail.getUpc());
		assertEquals(67, foundNail.getManufacturerID());
		assertEquals(6, foundNail.getPrice());
		assertEquals(3, foundNail.getLength());
		assertEquals(100, foundNail.getNumberInBox());
	}

	@Test
	void testFindConstructor() throws DatabaseException {
		NailsGateway nail = new NailsGateway(13);
		assertNotNull(nail);
		
		assertEquals(13, nail.getID());
		assertEquals("98765", nail.getUpc());
		assertEquals(55, nail.getManufacturerID());
		assertEquals(15, nail.getPrice());
		assertEquals(5, nail.getLength());
		assertEquals(50, nail.getNumberInBox());
	}

	@SuppressWarnings("unused")
	@Test
	public void testFindWithBadID() throws DatabaseException {
		Assertions.assertThrows(DatabaseException.class, () -> {
			NailsGateway nail = new NailsGateway(-1);
	     });
	}

	@Test
	public void testFindByManID() throws DatabaseException {
		ArrayList<NailDTO> nails = new ArrayList<NailDTO>();

		nails = NailsGateway.findByManufacturerID(55);

		assertNotNull(nails);
		assertEquals(2, nails.size());
		
		assertEquals(2, nails.size());
		assertEquals(2, nails.size());
	}

	@Test
	public void testFindByUPC() throws DatabaseException {
		ArrayList<NailDTO> nails = new ArrayList<NailDTO>();

		nails = NailsGateway.findByUpc("9876");
			
		assertNotNull(nails);
		assertEquals(2, nails.size());
		
		assertEquals(12, nails.get(0).getId());
		assertEquals(15, nails.get(1).getId());
		
		assertEquals("9876", nails.get(0).getUpc());
		assertEquals("9876", nails.get(1).getUpc());
	}

	@Test
	public void testFindAll() throws DatabaseException {
		ArrayList<NailDTO> nails = new ArrayList<NailDTO>();

		nails = NailsGateway.findAll();

		assertNotNull(nails);
		assertEquals(5, nails.size());
		
		assertEquals(11, nails.get(0).getId());
		assertEquals(12, nails.get(1).getId());
		assertEquals(13, nails.get(2).getId());
		assertEquals(14, nails.get(3).getId());	
		assertEquals(15, nails.get(4).getId());
	}
	
	/**
	 * Test that delete returns true when successful, and False when there is a failure
	 */
	@Test
	public void testDelete() throws SQLException, DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		NailsGateway nail = new NailsGateway(12);
		String query = "SELECT * FROM Nails WHERE ID = ?";
		
		PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt1.setInt(1, 12);
		ResultSet rs = stmnt1.executeQuery();
		
		assertTrue(rs.next());
		assertTrue(nail.delete());

		PreparedStatement stmnt2 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt2.setInt(1, 12);
		rs = stmnt2.executeQuery();
		
		assertFalse(rs.next());
	}
	
	/**
	 * Tests that persist updates everything in the row where the id's match.
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeAll() throws DatabaseException {
		NailsGateway nail = new NailsGateway(11);
		assertNotNull(nail);
		
		nail.setUpc("98765");
		nail.setManufacturerID(24);
		nail.setPrice(21);
		nail.setLength(2);
		nail.setNumberInBox(100);
		
		nail.persist();
		
		assertEquals(11, nail.getID());
		assertEquals("98765", nail.getUpc());
		assertEquals(24, nail.getManufacturerID());
		assertEquals(21, nail.getPrice());
		assertEquals(2, nail.getLength());
		assertEquals(100, nail.getNumberInBox());
	}
	
	/**
	 * Tests that persist can update some data and pass through the previous data that is not updated.
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeSome() throws DatabaseException {
		NailsGateway nail = new NailsGateway(11);
		assertNotNull(nail);
		
		nail.setUpc("98765");
		nail.setManufacturerID(24);
		nail.setNumberInBox(100);
		
		nail.persist();
		
		assertEquals(11, nail.getID()); //ID
		assertEquals("98765", nail.getUpc()); //Original Value -> "987" | updated to "98765".
		assertEquals(24, nail.getManufacturerID()); //original value ->23 | updated to 24.
		assertEquals(15, nail.getPrice()); //original value -> 15
		assertEquals(4, nail.getLength()); //Original Value -> 4
		assertEquals(100, nail.getNumberInBox()); //Original Value -> 50 | updated to 100.
	}
	
}
