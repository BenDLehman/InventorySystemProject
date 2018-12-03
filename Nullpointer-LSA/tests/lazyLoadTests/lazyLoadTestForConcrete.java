package lazyLoadTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import exception.DatabaseException;
import mappersConcrete.PowerToolMapper;
import mappersConcrete.StripNailMapper;
import sharedDomain.MapperManager;
import sharedDomain.PowerTool;
import sharedDomain.StripNail;

class lazyLoadTestForConcrete {
	DatabaseManager dbm;
    Savepoint sp;
    Connection conn = null;
    
    //Creates database connection and savepoint for rollback
    @BeforeEach
    void setUpTests() throws DatabaseException
    {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			conn = dbm.getConnection(dbTables.CONCRETE);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
    }
    
    //rollback
    @AfterEach
    void afterTests()
    {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
    }
	@Test
	void testsLazyLoadOnStripNailSideForConcrete() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		StripNailMapper sn = new StripNailMapper("111",11, 1,0.1,1);
		PowerToolMapper pt = new PowerToolMapper("222", 22, 2, "its a thing", true);	
		sn.addPowerTool(pt.getPowerTool());
		ArrayList<PowerTool> ptlist = sn.getStripNail().getPowerToolList().getPowerToolList();
		assertEquals(pt.getPowerTool().getUpc(),ptlist.get(0).getUpc());
		assertEquals(pt.getPowerTool().getManufacturerID(),ptlist.get(0).getManufacturerID());
		assertEquals(pt.getPowerTool().getDescription(),ptlist.get(0).getDescription());
		assertEquals(pt.getPowerTool().isBatteryPowered(),ptlist.get(0).isBatteryPowered());
	}
	
	
	@Test
	void testsLazyLoadOnPowerToolSideForConcrete() throws Exception {
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		StripNailMapper sn = new StripNailMapper("111",11, 1,0.1,1);
		PowerToolMapper pt = new PowerToolMapper("222new", 22, 2, "its a thing", true);
		pt.addStripNail(sn.getStripNail());
		ArrayList<StripNail> snList = pt.getPowerTool().getStripNailList().getStripNailList();
		assertEquals(sn.getStripNail().getUpc(),snList.get(0).getUpc());
		assertEquals(sn.getStripNail().getLength(),snList.get(0).getLength());
		assertEquals(sn.getStripNail().getManufacturerID(),snList.get(0).getManufacturerID());
		assertEquals(sn.getStripNail().getNumberInStrip(),snList.get(0).getNumberInStrip());
		assertEquals(sn.getStripNail().getPrice(),snList.get(0).getPrice());
	}
	
	@Test
	void testUpdate() throws Exception
	{
		MapperManager.getSingleton().setMapperPackageName("mappersConcrete");
		StripNailMapper sn = new StripNailMapper("111",11, 1,0.1,1);
		PowerToolMapper pt1 = new PowerToolMapper("222", 22, 2, "its a thing", true);
		PowerToolMapper pt2 = new PowerToolMapper("333", 33, 3, "its another thing", false);
		sn.addPowerTool(pt1.getPowerTool());
		ArrayList<PowerTool> ptlist = sn.getStripNail().getPowerToolList().getPowerToolList();
		assertEquals(pt1.getPowerTool().getUpc(),ptlist.get(0).getUpc());
		assertEquals(pt1.getPowerTool().getManufacturerID(),ptlist.get(0).getManufacturerID());
		assertEquals(pt1.getPowerTool().getDescription(),ptlist.get(0).getDescription());
		assertEquals(pt1.getPowerTool().isBatteryPowered(),ptlist.get(0).isBatteryPowered());
		
		sn.addPowerTool(pt2.getPowerTool());
		ptlist = sn.getStripNail().getPowerToolList().getPowerToolList();
		assertEquals(pt1.getPowerTool().getUpc(),ptlist.get(0).getUpc());
		assertEquals(pt1.getPowerTool().getManufacturerID(),ptlist.get(0).getManufacturerID());
		assertEquals(pt1.getPowerTool().getDescription(),ptlist.get(0).getDescription());
		assertEquals(pt1.getPowerTool().isBatteryPowered(),ptlist.get(0).isBatteryPowered());
		
		assertEquals(pt2.getPowerTool().getUpc(),ptlist.get(1).getUpc());
		assertEquals(pt2.getPowerTool().getManufacturerID(),ptlist.get(1).getManufacturerID());
		assertEquals(pt2.getPowerTool().getDescription(),ptlist.get(1).getDescription());
		assertEquals(pt2.getPowerTool().isBatteryPowered(),ptlist.get(1).isBatteryPowered());
	}

}
