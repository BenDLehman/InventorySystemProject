package concreteTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceConcrete.*;
import exception.DatabaseException;

class TestLastIDGateway {

	DatabaseManager dbm = DatabaseManager.getDatabaseManager();
	Connection c;
	
	@After
    public void buildDB() throws DatabaseException {
		ConcreteTableHelper.dropAllTables();
		ConcreteTableHelper.createTables();
		ConcreteTableHelper.populateTables();
    }
	
	@BeforeEach
    public void setup() throws DatabaseException {
		ConcreteTableHelper.dropAllTables();
		ConcreteTableHelper.createTables();
		ConcreteTableHelper.populateTables();
    }
    
   
    
    @Test
	void testCheckID() throws SQLException, DatabaseException {
    	assertTrue(LastIDGateway.checkID(1));
    	assertFalse(LastIDGateway.checkID(1000));
    }
    
	@Test
	void testGetNextID() throws SQLException, DatabaseException {
		assertEquals(21,LastIDGateway.getNextID());
		assertEquals(22,LastIDGateway.getNextID());
		assertEquals(23,LastIDGateway.getNextID());
		assertEquals(24,LastIDGateway.getNextID());
		
		assertTrue(LastIDGateway.checkID(21));
		assertTrue(LastIDGateway.checkID(22));
		assertTrue(LastIDGateway.checkID(23));
		assertTrue(LastIDGateway.checkID(24));	
	}
	
	@Test
	void testGetDeletedID() throws SQLException, DatabaseException {
		assertEquals(21,LastIDGateway.getNextID());
		assertEquals(22,LastIDGateway.getNextID());
		assertEquals(23,LastIDGateway.getNextID());
		assertEquals(24,LastIDGateway.getNextID());
		LastIDGateway.deleteID(23);
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String checkRowStatement = "select ID from LastID where ID = 23";
		PreparedStatement stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(checkRowStatement);
		ResultSet rs = stmnt.executeQuery();	
		assertFalse(rs.next());
		assertEquals(23,LastIDGateway.getNextID());
		stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(checkRowStatement);
		rs = stmnt.executeQuery();	
		assertTrue(rs.next());
		
	}

}
