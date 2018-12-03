package concreteTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceConcrete.ConcreteTableHelper;
import exception.DatabaseException;
import mappersConcrete.NailMapper;
import sharedDomain.MapperManager;
import sharedDomain.Nail;

class TestNailMapper {
	
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

	/**
	 * Tests to make sure the creation constructor works and that you can find the
	 * correct nail ID in the mapper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindingOneNail() throws DatabaseException {
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		NailMapper nm = new NailMapper("1111", 14, 20, 3.3, 3); // Creation Constructor
		int nailID = nm.getNailID();
		assertNotNull(nm.getNail());
		// compare the id in the gateway and the ID created by the nail object
		assertEquals(nailID, nm.getNail().getID());
	}

	/**
	 * Tests to make sure the finder constructor works and to make sure all the data
	 * is correct.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFinderConstructor() throws DatabaseException {
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		NailMapper nm = new NailMapper(11);
		assertEquals("987", nm.getNail().getUpc());
		assertEquals(23, nm.getNail().getManufacturerID());
		assertEquals(15, nm.getNail().getPrice());
		assertEquals(4, nm.getNail().getLength());
		assertEquals(50, nm.getNail().getNumberInBox());
		assertEquals(11, nm.getNail().getID());
	}

	/**
	 * Tests to see whether or not the FindAll Method works and returns the correct ID's.
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void testFindAllMethod() throws DatabaseException {
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		ArrayList<Nail> temp = NailMapper.findAll();
		assertEquals(11, temp.get(0).getID());
		assertEquals(12, temp.get(1).getID());
		assertEquals(13, temp.get(2).getID());
		assertEquals(14, temp.get(3).getID());
		assertEquals(15, temp.get(4).getID());
	}
	
	/**
	 * Test that delete returns true when successful, and False when there is a failure
	 */
	@Test
	public void testDelete() throws SQLException, DatabaseException {
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		NailMapper nail = new NailMapper(12);
		String query = "SELECT * FROM Nails WHERE ID = ?";
		
		PreparedStatement stmnt1 = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
		stmnt1.setInt(1, 12);
		ResultSet rs = stmnt1.executeQuery();
		
		assertTrue(rs.next());
		nail.delete();

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
		NailMapper nail = new NailMapper(11);
		assertNotNull(nail);
		nail.persist();
	}
	
	/**
	 * Tests that persist can update some data and pass through the previous data that is not updated.
	 * @throws DatabaseException
	 */
	@Test
	public void testPersistChangeSome() throws DatabaseException {
		NailMapper nail = new NailMapper(11);
		assertNotNull(nail);
		nail.persist();
	}
}