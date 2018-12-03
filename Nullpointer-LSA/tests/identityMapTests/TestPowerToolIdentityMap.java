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
import identityMappers.PowerToolIdentityMap;
import sharedDomain.MapperManager;
import sharedDomain.PowerTool;

class TestPowerToolIdentityMap {
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
		assertEquals(PowerToolIdentityMap.getInstance(),PowerToolIdentityMap.getInstance());
	}
	
	@Test
	public void testFindConcrete() throws DatabaseException
	{
		PowerTool powerTool;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		powerTool = PowerToolIdentityMap.getInstance().find(6);
		assertFalse(PowerToolIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(6, powerTool.getID());
		
		powerTool = PowerToolIdentityMap.getInstance().find(6);
		assertTrue(PowerToolIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(6, powerTool.getID());
	}
	
	@Test
	public void testCreateConcrete() throws DatabaseException
	{
		PowerTool powerTool;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		powerTool = PowerToolIdentityMap.getInstance().create("123456", 55, 2,"I am the created powertool", true);
		assertEquals("123456", powerTool.getUpc());
		assertEquals(55, powerTool.getManufacturerID());
		assertEquals(2, powerTool.getPrice());
		assertEquals("I am the created powertool", powerTool.getDescription());
		assertEquals(true, powerTool.isBatteryPowered());
		assertFalse(PowerToolIdentityMap.getInstance().alreadyInHashMap);
	}
	
	@Test
	public void testGetAll() throws DatabaseException
	{
		ConcreteTableHelper.main(null);
		ArrayList<PowerTool> powerTools = new ArrayList<PowerTool>();
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		PowerToolIdentityMap.getInstance().clearHashMap();
		
		powerTools = PowerToolIdentityMap.getInstance().getAll();
		
		assertEquals(5, powerTools.size());
		assertEquals(6, powerTools.get(0).getID());
		assertEquals(7, powerTools.get(1).getID());
		assertEquals(8, powerTools.get(2).getID());
		assertEquals(9, powerTools.get(3).getID());
		assertEquals(10, powerTools.get(4).getID());
	}
	
	
}