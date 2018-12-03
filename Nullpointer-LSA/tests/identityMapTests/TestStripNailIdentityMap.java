package identityMapTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import datasourceConcrete.ConcreteTableHelper;
import exception.DatabaseException;
import identityMappers.StripNailIdentityMap;
import sharedDomain.MapperManager;
import sharedDomain.StripNail;

class TestStripNailIdentityMap {
	
	@Test
	public void testSingleton()
	{	
		assertEquals(StripNailIdentityMap.getInstance(),StripNailIdentityMap.getInstance());
	}
	
	@Test
	public void testFindConcrete() throws DatabaseException
	{
		StripNail stripNail;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		stripNail = StripNailIdentityMap.getInstance().find(16);
		assertFalse(StripNailIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(16, stripNail.getID());
		
		stripNail = StripNailIdentityMap.getInstance().find(16);
		assertTrue(StripNailIdentityMap.getInstance().alreadyInHashMap);
		assertEquals(16, stripNail.getID());
	}
	
	@Test
	public void testCreateConcrete() throws DatabaseException
	{
		StripNail stripNail;
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		
		stripNail = StripNailIdentityMap.getInstance().create("123456", 55, 2, 4.0, 10);
		assertEquals("123456", stripNail.getUpc());
		assertEquals(55, stripNail.getManufacturerID());
		assertEquals(2, stripNail.getPrice());
		assertEquals(4.0, stripNail.getLength());
		assertEquals(10, stripNail.getNumberInStrip());
		assertFalse(StripNailIdentityMap.getInstance().alreadyInHashMap);
	}
	
	@Test
	public void testGetAll() throws DatabaseException
	{
		ConcreteTableHelper.main(null);
		ArrayList<StripNail> stripNails = new ArrayList<StripNail>();
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		StripNailIdentityMap.getInstance().clearHashMap();
		
		stripNails = StripNailIdentityMap.getInstance().getAll();
		
		assertEquals(5, stripNails.size());
		assertEquals(16, stripNails.get(0).getID());
		assertEquals(17, stripNails.get(1).getID());
		assertEquals(18, stripNails.get(2).getID());
		assertEquals(19, stripNails.get(3).getID());
		assertEquals(20, stripNails.get(4).getID());
	}
	
	
}