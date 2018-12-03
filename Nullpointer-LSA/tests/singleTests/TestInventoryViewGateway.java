package singleTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.*;
import datasourceSingle.*;
import exception.DatabaseException;
/**
 * 
 * @author Chris Roadcap
 * @author Kanza Amin
 *
 */
class TestInventoryViewGateway {
	
	DatabaseManager dbm;
	Savepoint sp;
	java.sql.Connection c = null;
	
	/**
	 * Creates tables before every test, if they don't already exist
	 * @throws Exception  when communications with the database fail/ improper database syntax
	 */

	@BeforeEach
	public void setup() throws DatabaseException {
		try {
			DatabaseManager dbm = DatabaseManager.getDatabaseManager();
			c = dbm.getConnection(dbTables.SINGLE);
			SingleTableHelper.cleanDB();
			SingleTableHelper.createInventoryItems();
			SingleTableHelper.createPowerToolsToStripNails();
			SingleTableHelper.createInventoryView();
			SingleTableHelper.populateTables();
			c.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void rollback() {
		try {
			c.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	void testSelectStripnailsForPowertool() throws Exception
	{
		//Creates a power tool and inserts into the DB
		InventoryItemsGateway powertool1 = new InventoryItemsGateway("upc1", 1, 10, "Powertool", true);
	
		//Creates 2 strip nails and inserts them into the DB
		InventoryItemsGateway stripnail1 = new InventoryItemsGateway("upc2", 1, 11, 12, 2.3);
		InventoryItemsGateway stripnail2 = new InventoryItemsGateway("upc3", 3, 12, 17, 2.4);


		
		//Creates an association between a powertool and stripnail
		PowerToolsToStripNailsGateway pttsn1 = new PowerToolsToStripNailsGateway(powertool1.getAutoGeneratedID(), stripnail1.getAutoGeneratedID());
		PowerToolsToStripNailsGateway pttsn2 = new PowerToolsToStripNailsGateway(powertool1.getAutoGeneratedID(), stripnail2.getAutoGeneratedID());
		
		//contains the stripNail DTOs that are associated with powertool1
		ArrayList<StripNailDTO> snList = InventoryViewGateway.findStripNailsbyPowerToolID(powertool1.getAutoGeneratedID());

		//asserts the result list contains dtos, and that it contains all stripnails associated with the powertool
		assertNotNull(snList);
    	assertEquals(2, snList.size());
    	
    	//Asserts the dtos in the result list represent the proper stripnails
		assertEquals(stripnail1.getAutoGeneratedID(),snList.get(0).getId());
  		assertEquals(stripnail2.getAutoGeneratedID(), snList.get(1).getId());
	}
	
	
	/**
	 * @throws Exception
	 */
	@Test
	void testSelectPowertoolsForStripnails() throws Exception
	{
	
		//Creates a stripNail and inserts it into the DB
		InventoryItemsGateway stripnail1 = new InventoryItemsGateway("upc", 1, 2, 3, 4.5);

		//Creates 3 PowerTools and Insertsthem into DB
		InventoryItemsGateway powertool1 = new InventoryItemsGateway("upcNo1", 2, 11, "powertool1", true);
		InventoryItemsGateway powertool2 = new InventoryItemsGateway("upcNo2", 3, 12, "Powertool2", true);
		InventoryItemsGateway powertool3 = new InventoryItemsGateway("upcNo3", 4, 13, "Powertool3", true);
		
		
		//Associates the powertools with the strip nail
		PowerToolsToStripNailsGateway pttsn1 = new PowerToolsToStripNailsGateway(powertool1.getAutoGeneratedID(), stripnail1.getAutoGeneratedID());
		PowerToolsToStripNailsGateway pttsn2 = new PowerToolsToStripNailsGateway(powertool2.getAutoGeneratedID(), stripnail1.getAutoGeneratedID());
		PowerToolsToStripNailsGateway pttsn3 = new PowerToolsToStripNailsGateway(powertool3.getAutoGeneratedID(), stripnail1.getAutoGeneratedID());

		
		//Holds DTOs that represent powertools that are associated with the given stripnail
		ArrayList<PowerToolDTO> ptList = new ArrayList<PowerToolDTO>();
		ptList= InventoryViewGateway.findPowerToolsbyStripNailID(stripnail1.getAutoGeneratedID());
		
		
		//asserts the list isnt null, and 3, and only 3 powertool DTOs are in the list
		//as well as the list contains DTOs with the appropriate values
		assertNotNull(ptList);
		assertEquals(3, ptList.size());
		assertEquals(ptList.get(0).getId(), powertool1.getAutoGeneratedID());
		assertEquals(ptList.get(1).getId(), powertool2.getAutoGeneratedID());
		assertEquals(ptList.get(2).getId(), powertool3.getAutoGeneratedID());
		
	}
	

}