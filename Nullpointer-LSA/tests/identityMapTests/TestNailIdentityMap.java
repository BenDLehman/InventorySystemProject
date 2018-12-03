package identityMapTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceConcrete.ConcreteTableHelper;
import exception.DatabaseException;
import identityMappers.NailIdentityMap;
import sharedDomain.MapperManager;
import sharedDomain.Nail;

class TestNailIdentityMap {
	Connection c = null;


	@BeforeEach
	public void setup() throws DatabaseException {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			c = dbm.getConnection(dbTables.CONCRETE);
			ConcreteTableHelper.main(null);
			c.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("IN THE CATCH");
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
	public void testSingleton()
	{	
		assertEquals(NailIdentityMap.getInstance(),NailIdentityMap.getInstance());
	}  
	
	@Test
	public void testFindConcrete() throws DatabaseException
	{
		Nail nail;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		nail = NailIdentityMap.getInstance().find(11);
		assertFalse(NailIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(11, nail.getID());
		
		nail = NailIdentityMap.getInstance().find(11);
		assertTrue(NailIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(11, nail.getID());
	}
	
	@Test
	public void testCreateConcrete() throws DatabaseException
	{
		Nail nail;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		nail = NailIdentityMap.getInstance().create("123456", 55, 2, 4.0, 50);
		assertEquals("123456", nail.getUpc());
		assertEquals(55, nail.getManufacturerID());
		assertEquals(2, nail.getPrice());
		assertEquals(4.0, nail.getLength());
		assertEquals(50, nail.getNumberInBox());
		assertFalse(NailIdentityMap.getInstance().alreadyInHashMap);
	}
	
	@Test
	public void testGetAll() throws DatabaseException
	{
		ConcreteTableHelper.main(null);
		ArrayList<Nail> nails = new ArrayList<Nail>();
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		NailIdentityMap.getInstance().clearHashMap();
		
		nails = NailIdentityMap.getInstance().getAll();
		
		assertEquals(5, nails.size());
		assertEquals(11, nails.get(0).getID());
		assertEquals(12, nails.get(1).getID());
		assertEquals(13, nails.get(2).getID());
		assertEquals(14, nails.get(3).getID());
		assertEquals(15, nails.get(4).getID());
	}
	
	
}