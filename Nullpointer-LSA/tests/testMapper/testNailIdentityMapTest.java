//package testMapper;
//
//import identityMappers.NailIdentityMap;
//import mappersConcrete.NailMapper;
//import mappersClass.NailMapper;
//import sharedDomain.MapperManager;
//import sharedDomain.Nail;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import datasourceConcrete.ConcreteTableHelper;
//@RunWith(Parameterized.class)
//class testNailIdentityMapTest {
	
//	@Test
//	public void testFindingOneNailForClass() throws Exception
//	{
//		MapperManager.getSingleton().setMapperPackageName("mappersClass");
//		NailMapper nm = new NailMapper("1111", 14, 20, 3.3, 3);
//		NailIdentityMap.getInstance().getNail(nm.getNail());
//		Nail nail = NailIdentityMap.getInstance().checkNailMap(1);
//        assertNotNull(nail);
//	}
//	@Test
//	public void testFindingOneNailForSingle() throws Exception
//	{
//		MapperManager.getSingleton().setMapperPackageName("mappersSingle");
//		NailMapper nm = new NailMapper("1111", 14, 20, 3.3, 3);
//		NailIdentityMap.getInstance().getNail(nm.getNail());
//		Nail nail = NailIdentityMap.getInstance().checkNailMap(1);
//        assertNotNull(nail);
//	}
	
	/*
	 * Every method in mapper:
	 * Finder constructor
	 * Create constructor
	 * findAll
	 * persist
	 * delete
	 * 
	 */
	
//}