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
import identityMappers.ToolIdentityMap;
import sharedDomain.MapperManager;
import sharedDomain.Tool;


class TestToolIdentityMap {
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
		assertEquals(ToolIdentityMap.getInstance(),ToolIdentityMap.getInstance());
	}
	
	@Test
	public void testFindConcrete() throws DatabaseException
	{
		Tool tool;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		tool = ToolIdentityMap.getInstance().find(1);
		assertFalse(ToolIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(1, tool.getID());
		
		tool = ToolIdentityMap.getInstance().find(1);
		assertTrue(ToolIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(1, tool.getID());
	}
	
	@Test
	public void testCreateConcrete() throws DatabaseException
	{
		Tool tool;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		tool = ToolIdentityMap.getInstance().create("123456", 55, 2,"I am the created powertool");
		assertEquals("123456", tool.getUpc());
		assertEquals(55, tool.getManufacturerID());
		assertEquals(2, tool.getPrice());
		assertEquals("I am the created powertool", tool.getDescription());
		assertFalse(ToolIdentityMap.getInstance().alreadyInHashMap);
	}
	
	@Test
	public void testGetAll() throws DatabaseException
	{
		ConcreteTableHelper.main(null);
		ArrayList<Tool> tools = new ArrayList<Tool>();
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		ToolIdentityMap.getInstance().clearHashMap();
		
		tools = ToolIdentityMap.getInstance().getAll();
		
		assertEquals(5, tools.size());
		assertEquals(1, tools.get(0).getID());
		assertEquals(2, tools.get(1).getID());
		assertEquals(3, tools.get(2).getID());
		assertEquals(4, tools.get(3).getID());
		assertEquals(5, tools.get(4).getID());
	}
	
	
}