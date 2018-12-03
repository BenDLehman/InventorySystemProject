package classTests;

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
import datasource.StripNailDTO;
import datasourceClass.StripNailsFastenersInventoryItemsGateway;
import datasourceClass.ToolInventoryItemsGateway;
import exception.DatabaseException;

/**
 * 
 * @author cr5603
 *
 * Tests the StripNailsFastenersInventoryItemsGateway
 *
 */
class TestStripNailsFastenersInventoryItemsGateway 
{
	DatabaseManager dbm;
    Savepoint sp;
    Connection conn = null;
    
  //Creates database connection and savepoint for rollback
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

    /**
     * Tests adding and finding a stripnail in the database
     * @throws DatabaseException
     */
	@Test
	void testAddAndFindNailFastenerInventoryItem() throws DatabaseException  
	{
		String upc = "TestUPC";
		int manufacturerID = 121;
		int price = 1231;
		double length = 0.5;
		int numberInBox = 16;
		StripNailsFastenersInventoryItemsGateway stripNailFastenerInventoryItem = new StripNailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInBox);
		StripNailsFastenersInventoryItemsGateway foundStripNailFastenerInventoryItem = new StripNailsFastenersInventoryItemsGateway(stripNailFastenerInventoryItem.getInventoryItemID());

		assertEquals(upc, foundStripNailFastenerInventoryItem.getUPC());
		assertEquals(manufacturerID, foundStripNailFastenerInventoryItem.getManufacturerID());
		assertEquals(price, foundStripNailFastenerInventoryItem.getPrice());
		assertEquals(length, foundStripNailFastenerInventoryItem.getLength());
		assertEquals(numberInBox, foundStripNailFastenerInventoryItem.getNumberInStrip());
	}
	

	
	/**
	 * tests updating stripnails
	 * @throws DatabaseException
	 */
	@Test
	void testUpdateAndFindNailFastenerInventoryItem() throws DatabaseException  
	{
		String upc = "TestUPC";
		int manufacturerID = 121;
		int price = 1231;
		double length = 0.5;
		int numberInBox = 16;
		StripNailsFastenersInventoryItemsGateway stripNailFastenerInventoryItem = new StripNailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInBox);
		StripNailsFastenersInventoryItemsGateway updatedStripNail = new StripNailsFastenersInventoryItemsGateway(stripNailFastenerInventoryItem.getInventoryItemID());
		updatedStripNail.setNumberInStrip(19);
		updatedStripNail.setManufacturerID(1111);
		updatedStripNail.setPrice(123);

		assertEquals(upc, updatedStripNail.getUPC());
		assertEquals(1111, updatedStripNail.getManufacturerID());
		assertEquals(123, updatedStripNail.getPrice());
		assertEquals(length, updatedStripNail.getLength());
		assertEquals(19, updatedStripNail.getNumberInStrip());
	}
	
	/**
	 * tests findAll stripnails
	 * @throws DatabaseException
	 */
    @Test
    public void testFindAll() throws DatabaseException
    {
    	ArrayList<StripNailDTO> allNails = StripNailsFastenersInventoryItemsGateway.findAll();
    	assertNotNull(allNails);
    	assertEquals(5, allNails.size());
    }
    
    /**
     * tests accuracy of findByUPC
     * @throws DatabaseException
     */
    @Test
    public void testFindByUPC() throws DatabaseException
    {
    	StripNailsFastenersInventoryItemsGateway nail4 = new StripNailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	StripNailsFastenersInventoryItemsGateway nail5 = new StripNailsFastenersInventoryItemsGateway("upc", 3, 15, 0.5, 60);
    	ArrayList<StripNailDTO> upcStripNails = StripNailsFastenersInventoryItemsGateway.findByUPC("upc");
    	assertNotNull(upcStripNails);
    	assertEquals(2, upcStripNails.size());
    	
    	assertEquals("upc", upcStripNails.get(0).getUpc());
    	assertEquals(2, upcStripNails.get(0).getManufacturerID());
    	assertEquals(15, upcStripNails.get(0).getPrice());
    	assertEquals(1.0, upcStripNails.get(0).getLength());
    	assertEquals(50, upcStripNails.get(0).getNumberInStrip());
    	
    	assertEquals("upc", upcStripNails.get(1).getUpc());
    	assertEquals(3, upcStripNails.get(1).getManufacturerID());
    	assertEquals(15, upcStripNails.get(1).getPrice());
    	assertEquals(0.5, upcStripNails.get(1).getLength());
    	assertEquals(60, upcStripNails.get(1).getNumberInStrip());
    }
    
    /**
     * tests accuracy of FindByManufacturerID
     * @throws DatabaseException
     */
    @Test
    public void testFindByManufacturerID() throws DatabaseException
    {
    	StripNailsFastenersInventoryItemsGateway nail1 = new StripNailsFastenersInventoryItemsGateway("upc2", 1, 10, 0.5, 60);
    	StripNailsFastenersInventoryItemsGateway nail2 = new StripNailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	ArrayList<StripNailDTO> MIDStripNails = StripNailsFastenersInventoryItemsGateway.findByManufacturerID(1);
    	assertNotNull(MIDStripNails);
    	assertEquals(2, MIDStripNails.size());
    	
    	assertEquals("upc2", MIDStripNails.get(0).getUpc());
    	assertEquals(1, MIDStripNails.get(0).getManufacturerID());
    	assertEquals(10, MIDStripNails.get(0).getPrice());
    	assertEquals(0.5, MIDStripNails.get(0).getLength());
    	assertEquals(60, MIDStripNails.get(0).getNumberInStrip());
    	
    	assertEquals("upc1", MIDStripNails.get(1).getUpc());
    	assertEquals(1, MIDStripNails.get(1).getManufacturerID());
    	assertEquals(10, MIDStripNails.get(1).getPrice());
    	assertEquals(1.0, MIDStripNails.get(1).getLength());
    	assertEquals(50, MIDStripNails.get(1).getNumberInStrip());
    }
    
    /**
     * tests accuracy of findByPriceRange
     * @throws DatabaseException
     */
    @Test
    public void testFindByPriceRange() throws DatabaseException
    {
    	ArrayList<StripNailDTO> priceStripNails = StripNailsFastenersInventoryItemsGateway.findByPrice(10,15);
    	assertNotNull(priceStripNails);
    	assertEquals(5, priceStripNails.size());
    }
    
    /**
     * tests accuracy of findByLength
     * @throws DatabaseException
     */
    @Test
    public void testFindByLength() throws DatabaseException
    {
    	StripNailsFastenersInventoryItemsGateway nail2 = new StripNailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	StripNailsFastenersInventoryItemsGateway nail4 = new StripNailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	ArrayList<StripNailDTO> lengthStripNails = StripNailsFastenersInventoryItemsGateway.findByLength(1.0);
    	assertNotNull(lengthStripNails);
    	assertEquals(2, lengthStripNails.size());
    	
    	assertEquals("upc1", lengthStripNails.get(0).getUpc());
    	assertEquals(1, lengthStripNails.get(0).getManufacturerID());
    	assertEquals(10, lengthStripNails.get(0).getPrice());
    	assertEquals(1.0, lengthStripNails.get(0).getLength());
    	assertEquals(50, lengthStripNails.get(0).getNumberInStrip());
    	
    	assertEquals("upc", lengthStripNails.get(1).getUpc());
    	assertEquals(2, lengthStripNails.get(1).getManufacturerID());
    	assertEquals(15, lengthStripNails.get(1).getPrice());
    	assertEquals(1.0, lengthStripNails.get(1).getLength());
    	assertEquals(50, lengthStripNails.get(1).getNumberInStrip());
    }
    
    /**
     * tests accuracy of findByNumberInStrip
     * @throws DatabaseException
     */
    @Test
    public void testFindByNumberInStrip() throws DatabaseException
    {
    	StripNailsFastenersInventoryItemsGateway nail2 = new StripNailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	StripNailsFastenersInventoryItemsGateway nail4 = new StripNailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	ArrayList<StripNailDTO> numberInStripStripNails = StripNailsFastenersInventoryItemsGateway.findByNumberInStrip(50);
    	assertNotNull(numberInStripStripNails);
    	assertEquals(2, numberInStripStripNails.size());
    	
    	assertEquals("upc1", numberInStripStripNails.get(0).getUpc());
    	assertEquals(1, numberInStripStripNails.get(0).getManufacturerID());
    	assertEquals(10, numberInStripStripNails.get(0).getPrice());
    	assertEquals(1.0, numberInStripStripNails.get(0).getLength());
    	assertEquals(50, numberInStripStripNails.get(0).getNumberInStrip());
    	
    	assertEquals("upc", numberInStripStripNails.get(1).getUpc());
    	assertEquals(2, numberInStripStripNails.get(1).getManufacturerID());
    	assertEquals(15, numberInStripStripNails.get(1).getPrice());
    	assertEquals(1.0, numberInStripStripNails.get(1).getLength());
    	assertEquals(50, numberInStripStripNails.get(1).getNumberInStrip());
    }
    
    /**
     * tests the nonexistence of a nail with ID 100000000
     */
    @Test
    void throwsExceptionWhenNotFound() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  StripNailsFastenersInventoryItemsGateway SNIIG = new StripNailsFastenersInventoryItemsGateway(100000000);}); 
    }
    
    /**
     * makes sure minprice cant be larger than maxprice
     */
    @Test
    void throwsWhenMinimumIsLargerThanMaximum() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  StripNailsFastenersInventoryItemsGateway.findByPrice(100,30);});
    }
    
    
    
    
	/**
	 * tests deletion of stripnail
	 * @throws DatabaseException
	 */
    @Test
    void testDeleteStripNail() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  String upc = "TestUPC";
  		int manufacturerID = 121;
  		int price = 1231;
  		double length = 0.5;
  		int numberInStrip = 16;
  		StripNailsFastenersInventoryItemsGateway stripNailFastenerInventoryItem = new StripNailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInStrip);
  		assertTrue(stripNailFastenerInventoryItem.delete());
  		StripNailsFastenersInventoryItemsGateway deletedEntry = new StripNailsFastenersInventoryItemsGateway(stripNailFastenerInventoryItem.getInventoryItemID());

    	  
      });
    }
    
}
