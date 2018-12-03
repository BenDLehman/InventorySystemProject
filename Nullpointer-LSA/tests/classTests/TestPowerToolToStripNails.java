package classTests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.PowerToolToStripNailDTO;
import datasourceClass.PowerToolInventoryItemsGateway;
import datasourceClass.PowerToolsToStripNailsGateway;
import datasourceClass.StripNailsFastenersInventoryItemsGateway;
import exception.DatabaseException;

class TestPowerToolToStripNails {
	DatabaseManager dbm;
    Savepoint sp;
    Connection conn = null;
    @BeforeEach
    void setUpTests() throws DatabaseException
    {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			conn = dbm.getConnection(dbTables.CLASS);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

    }
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
    public void AddingAStripNailAndPowerTool() throws DatabaseException
   {
		PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("ups222",1234,1234,"A new description",true);
   	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
   	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
   	PowerToolsToStripNailsGateway FPTSN = new PowerToolsToStripNailsGateway(PTSN.getID());
   	assertEquals(PTIIG.getInventoryItemID(),FPTSN.getPowerToolID());
   	assertEquals(snfiig.getInventoryItemID(),FPTSN.getStripNailID());
   	
   	
   }
    
    @Test
    public void TestFindingByPowerTool() throws DatabaseException
   {


	PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("ups222",1234,1234,"A new description",true);
   	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
   	StripNailsFastenersInventoryItemsGateway snfiig2 = new StripNailsFastenersInventoryItemsGateway("1234",1111,2222,0.33,4444);
   	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN2 = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig2.getInventoryItemID());

   	
    ArrayList<PowerToolToStripNailDTO> PowerToolToStripNailInventoryItems = new ArrayList<PowerToolToStripNailDTO>();
       PowerToolToStripNailInventoryItems = PowerToolsToStripNailsGateway.findStripNailByPowerToolID(PTIIG.getInventoryItemID());
		assertNotNull(PowerToolToStripNailInventoryItems);
		assertEquals(2, PowerToolToStripNailInventoryItems.size());
		assertEquals(snfiig.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(0).getStripNailId());
		assertEquals(snfiig2.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(1).getStripNailId());	

   	
   }
    @Test
    public void TestFindingByStripNail() throws DatabaseException
   {


   	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
   	StripNailsFastenersInventoryItemsGateway snfiig2 = new StripNailsFastenersInventoryItemsGateway("1234",1111,2222,0.33,4444);
	PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("upc444",123,1234,"A another description",true);
	PowerToolInventoryItemsGateway PTIIG1 = new PowerToolInventoryItemsGateway("upc444",111,1234,"A brand new description",true);
	PowerToolInventoryItemsGateway PTIIG2 = new PowerToolInventoryItemsGateway("upc44242",222,1234,"A even cooler description",true);
	PowerToolInventoryItemsGateway PTIIG3 = new PowerToolInventoryItemsGateway("upc42424",3333,1234,"A nice nice description",true);
	PowerToolInventoryItemsGateway PTIIG4 = new PowerToolInventoryItemsGateway("upc42224",4444,1234,"A new description",true);

   	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN2 = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig2.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN3 = new PowerToolsToStripNailsGateway(PTIIG1.getInventoryItemID(),snfiig.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN4 = new PowerToolsToStripNailsGateway(PTIIG2.getInventoryItemID(),snfiig2.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN5 = new PowerToolsToStripNailsGateway(PTIIG3.getInventoryItemID(),snfiig2.getInventoryItemID());
   	PowerToolsToStripNailsGateway PTSN6 = new PowerToolsToStripNailsGateway(PTIIG4.getInventoryItemID(),snfiig.getInventoryItemID());



   	
    ArrayList<PowerToolToStripNailDTO> PowerToolToStripNailInventoryItems = new ArrayList<PowerToolToStripNailDTO>();
    PowerToolToStripNailInventoryItems = PowerToolsToStripNailsGateway.findPowerToolByStripNailID(snfiig.getInventoryItemID());
		assertNotNull(PowerToolToStripNailInventoryItems);
		assertEquals(3, PowerToolToStripNailInventoryItems.size());
		assertEquals(PTIIG.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(0).getPowerToolId());
		assertEquals(PTIIG1.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(1).getPowerToolId());	
		assertEquals(PTIIG4.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(2).getPowerToolId());	
		 PowerToolToStripNailInventoryItems = PowerToolsToStripNailsGateway.findPowerToolByStripNailID(snfiig2.getInventoryItemID());
			assertNotNull(PowerToolToStripNailInventoryItems);
			assertEquals(3, PowerToolToStripNailInventoryItems.size());
			assertEquals(PTIIG.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(0).getPowerToolId());
			assertEquals(PTIIG2.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(1).getPowerToolId());	
			assertEquals(PTIIG3.getInventoryItemID(), PowerToolToStripNailInventoryItems.get(2).getPowerToolId());	



   	
   }
    
    @Test
    public void UpdatePowerToolIDTheManyToManyTable() throws DatabaseException
   {
    	PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("ups222",1234,1234,"A new description",true);
    	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
    	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
    	PowerToolsToStripNailsGateway FPTSN = new PowerToolsToStripNailsGateway(PTSN.getID());
    	assertEquals(PTIIG.getInventoryItemID(),FPTSN.getPowerToolID());
    	assertEquals(snfiig.getInventoryItemID(),FPTSN.getStripNailID());
    	
    	
    	PowerToolInventoryItemsGateway PTIIG2 = new PowerToolInventoryItemsGateway("upc44242",222,1234,"A even cooler description",true);

    	FPTSN.setPowerToolID(PTIIG2.getInventoryItemID());
    	FPTSN.persist();
    	
    	assertEquals(PTIIG2.getInventoryItemID(),FPTSN.getPowerToolID());
    	assertEquals(snfiig.getInventoryItemID(),FPTSN.getStripNailID());


   }
    
    @Test
    public void UpdateStripNailIDTheManyToManyTable() throws DatabaseException
   {
    	PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("ups222",1234,1234,"A new description",true);
    	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
    	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
    	PowerToolsToStripNailsGateway FPTSN = new PowerToolsToStripNailsGateway(PTSN.getID());
    	assertEquals(PTIIG.getInventoryItemID(),FPTSN.getPowerToolID());
    	assertEquals(snfiig.getInventoryItemID(),FPTSN.getStripNailID());
    	
    	
    	StripNailsFastenersInventoryItemsGateway snfiig2 = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);

    	FPTSN.setStripNailID(snfiig2.getInventoryItemID());
    	FPTSN.persist();
    	
    	assertEquals(snfiig2.getInventoryItemID(),FPTSN.getStripNailID());
    	assertEquals(PTIIG.getInventoryItemID(),FPTSN.getPowerToolID());


   }
//    @Test
//    public void DeleteAnEntry() throws DatabaseException
//    {
//     	
//     	
//		
//
//
//    }
    /**
     * makes sure minprice cant be larger than maxprice
     */
    @Test
    void DeletedEntry() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	 PowerToolInventoryItemsGateway PTIIG = new PowerToolInventoryItemsGateway("ups222",1234,1234,"A new description",true);
       	StripNailsFastenersInventoryItemsGateway snfiig = new StripNailsFastenersInventoryItemsGateway("1234",1234,1234,0.75,1111);
       	PowerToolsToStripNailsGateway PTSN = new PowerToolsToStripNailsGateway(PTIIG.getInventoryItemID(),snfiig.getInventoryItemID());
       	PowerToolsToStripNailsGateway FPTSN = new PowerToolsToStripNailsGateway(PTSN.getID());
       	assertEquals(PTIIG.getInventoryItemID(),FPTSN.getPowerToolID());
       	assertEquals(snfiig.getInventoryItemID(),FPTSN.getStripNailID());
       	PTIIG.delete();
       	PowerToolsToStripNailsGateway DPTSN = new PowerToolsToStripNailsGateway(PTSN.getID());
      });
    }




}
