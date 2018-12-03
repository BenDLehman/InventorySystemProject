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
import exception.DatabaseException;
import datasource.NailDTO;
import datasourceClass.NailsFastenersInventoryItemsGateway;
import datasourceClass.PowerToolInventoryItemsGateway;

/**
 * 
 * @author cr5603
 *
 * Tests the NailsFastenersInventoryItemsGateway
 *
 */
class TestNailsFastenersInventoryItemsGateway 
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
     * Tests adding and finding a nail in the database
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
		NailsFastenersInventoryItemsGateway nailFastenerInventoryItem = new NailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInBox);
		NailsFastenersInventoryItemsGateway foundNailFastenerInventoryItem = new NailsFastenersInventoryItemsGateway(nailFastenerInventoryItem.getInventoryItemID());

		assertEquals(upc, foundNailFastenerInventoryItem.getUPC());
		assertEquals(manufacturerID, foundNailFastenerInventoryItem.getManufacturerID());
		assertEquals(price, foundNailFastenerInventoryItem.getPrice());
		assertEquals(length, foundNailFastenerInventoryItem.getLength());
		assertEquals(numberInBox, foundNailFastenerInventoryItem.getNumberInBox());
	}


	/**
	 * tests updating nails
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
		NailsFastenersInventoryItemsGateway nailFastenerInventoryItem = new NailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInBox);		
		NailsFastenersInventoryItemsGateway updatedfastener = new NailsFastenersInventoryItemsGateway(nailFastenerInventoryItem.getInventoryItemID());
		updatedfastener.setLength(0.636);
		updatedfastener.setManufacturerID(111);
		updatedfastener.setNumberInBox(111);
		updatedfastener.persist();
		assertEquals(upc, updatedfastener.getUPC());
		assertEquals(111, updatedfastener.getManufacturerID());
		assertEquals(price, updatedfastener.getPrice());
		assertEquals(0.636, updatedfastener.getLength());
		assertEquals(111, updatedfastener.getNumberInBox());
	}
    
	/**
	 * tests findAll nails
	 * @throws DatabaseException
	 */
    @Test
    public void testFindAll() throws DatabaseException
    {
    	NailsFastenersInventoryItemsGateway nail1 = new NailsFastenersInventoryItemsGateway("upc2", 1, 10, 0.5, 60);
    	ArrayList<NailDTO> allNails = NailsFastenersInventoryItemsGateway.findAll();
    	assertNotNull(allNails);
    	assertEquals(6, allNails.size());
    }
    
    /**
     * tests accuracy of findByUPC
     * @throws DatabaseException
     */
    @Test
    public void testFindByUPC() throws DatabaseException
    {
    	NailsFastenersInventoryItemsGateway nail1 = new NailsFastenersInventoryItemsGateway("upc2", 1, 10, 0.5, 60);
    	NailsFastenersInventoryItemsGateway nail2 = new NailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	NailsFastenersInventoryItemsGateway nail3 = new NailsFastenersInventoryItemsGateway("upc1", 2, 15, 0.5, 60);
    	NailsFastenersInventoryItemsGateway nail4 = new NailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	NailsFastenersInventoryItemsGateway nail5 = new NailsFastenersInventoryItemsGateway("upc", 3, 15, 0.5, 60);
    	ArrayList<NailDTO> upcNails = NailsFastenersInventoryItemsGateway.findByUPC("upc");
    	assertNotNull(upcNails);
    	assertEquals(2, upcNails.size());
    	
    	assertEquals("upc", upcNails.get(0).getUpc());
    	assertEquals(2, upcNails.get(0).getManufacturerID());
    	assertEquals(15, upcNails.get(0).getPrice());
    	assertEquals(1.0, upcNails.get(0).getLength());
    	assertEquals(50, upcNails.get(0).getNumberInBox());
    	
    	assertEquals("upc", upcNails.get(1).getUpc());
    	assertEquals(3, upcNails.get(1).getManufacturerID());
    	assertEquals(15, upcNails.get(1).getPrice());
    	assertEquals(0.5, upcNails.get(1).getLength());
    	assertEquals(60, upcNails.get(1).getNumberInBox());
    }
    
    /**
     * tests accuracy of FindByManufacturerID
     * @throws DatabaseException
     */
    @Test
    public void testFindByManufacturerID() throws DatabaseException
    {
    	NailsFastenersInventoryItemsGateway nail1 = new NailsFastenersInventoryItemsGateway("upc2", 1, 10, 0.5, 60);
    	NailsFastenersInventoryItemsGateway nail2 = new NailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	NailsFastenersInventoryItemsGateway nail3 = new NailsFastenersInventoryItemsGateway("upc1", 2, 15, 0.5, 60);
    	NailsFastenersInventoryItemsGateway nail4 = new NailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	NailsFastenersInventoryItemsGateway nail5 = new NailsFastenersInventoryItemsGateway("upc", 3, 15, 0.5, 60);
    	ArrayList<NailDTO> MIDNails = NailsFastenersInventoryItemsGateway.findByManufacturerID(1);
    	assertNotNull(MIDNails);
    	assertEquals(2, MIDNails.size());
    	
    	assertEquals("upc2", MIDNails.get(0).getUpc());
    	assertEquals(1, MIDNails.get(0).getManufacturerID());
    	assertEquals(10, MIDNails.get(0).getPrice());
    	assertEquals(0.5, MIDNails.get(0).getLength());
    	assertEquals(60, MIDNails.get(0).getNumberInBox());
    	
    	assertEquals("upc1", MIDNails.get(1).getUpc());
    	assertEquals(1, MIDNails.get(1).getManufacturerID());
    	assertEquals(10, MIDNails.get(1).getPrice());
    	assertEquals(1.0, MIDNails.get(1).getLength());
    	assertEquals(50, MIDNails.get(1).getNumberInBox());
    }
    
    /**
     * tests accuracy of findByPriceRange
     * @throws DatabaseException
     */
    @Test
    public void testFindByPriceRange() throws DatabaseException
    {
    	ArrayList<NailDTO> priceNails = NailsFastenersInventoryItemsGateway.findByPriceRange(10,15);
    	assertNotNull(priceNails);
    	assertEquals(5, priceNails.size());
    	
    	assertEquals("987", priceNails.get(0).getUpc());
    	assertEquals(23, priceNails.get(0).getManufacturerID());
    	assertEquals(15, priceNails.get(0).getPrice());
    	assertEquals(4, priceNails.get(0).getLength());
    	assertEquals(50, priceNails.get(0).getNumberInBox());
    	
    	assertEquals("9876", priceNails.get(1).getUpc());
    	assertEquals(23, priceNails.get(1).getManufacturerID());
    	assertEquals(15, priceNails.get(1).getPrice());
    	assertEquals(4, priceNails.get(1).getLength());
    	assertEquals(50, priceNails.get(1).getNumberInBox());
    }
    
    /**
     * tests accuracy of findByLength
     * @throws DatabaseException
     */
    @Test
    public void testFindByLength() throws DatabaseException
    {
    	NailsFastenersInventoryItemsGateway nail2 = new NailsFastenersInventoryItemsGateway("upc1", 1, 10, 1.0, 50);
    	NailsFastenersInventoryItemsGateway nail4 = new NailsFastenersInventoryItemsGateway("upc", 2, 15, 1.0, 50);
    	ArrayList<NailDTO> lengthNails = NailsFastenersInventoryItemsGateway.findByLength(1.0);
    	assertNotNull(lengthNails);
    	assertEquals(2, lengthNails.size());
    	
    	assertEquals("upc1", lengthNails.get(0).getUpc());
    	assertEquals(1, lengthNails.get(0).getManufacturerID());
    	assertEquals(10, lengthNails.get(0).getPrice());
    	assertEquals(1.0, lengthNails.get(0).getLength());
    	assertEquals(50, lengthNails.get(0).getNumberInBox());
    	
    	assertEquals("upc", lengthNails.get(1).getUpc());
    	assertEquals(2, lengthNails.get(1).getManufacturerID());
    	assertEquals(15, lengthNails.get(1).getPrice());
    	assertEquals(1.0, lengthNails.get(1).getLength());
    	assertEquals(50, lengthNails.get(1).getNumberInBox());
    }
    
    /**
     * tests accuracy of findByNumberInBox
     * @throws DatabaseException
     */
    @Test
    public void testFindByNumberInBox() throws DatabaseException
    {
    	ArrayList<NailDTO> numberInBoxNails = NailsFastenersInventoryItemsGateway.findByNumberInBox(50);
    	assertNotNull(numberInBoxNails);
    	assertEquals(3, numberInBoxNails.size());
    	
    	assertEquals("987", numberInBoxNails.get(0).getUpc());
    	assertEquals(23, numberInBoxNails.get(0).getManufacturerID());
    	assertEquals(15, numberInBoxNails.get(0).getPrice());
    	assertEquals(4, numberInBoxNails.get(0).getLength());
    	assertEquals(50, numberInBoxNails.get(0).getNumberInBox());
    	
    	assertEquals("9876", numberInBoxNails.get(1).getUpc());
    	assertEquals(23, numberInBoxNails.get(1).getManufacturerID());
    	assertEquals(15, numberInBoxNails.get(1).getPrice());
    	assertEquals(4, numberInBoxNails.get(1).getLength());
    	assertEquals(50, numberInBoxNails.get(1).getNumberInBox());
    }
    
    /**
     * tests the nonexistence of a nail with ID 100000000
     */
    @Test
    public void throwsExceptionWhenNotFound() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  NailsFastenersInventoryItemsGateway nFIIG = new NailsFastenersInventoryItemsGateway(100000000);});
    }
    
    /**
     * makes sure minprice cant be larger than maxprice
     */
    @Test
    public void throwsWhenMinimumIsLargerThanMaximum() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  NailsFastenersInventoryItemsGateway.findByPriceRange(30,3);});
    }
    
	/**
	 * tests deletion of nail
	 * @throws DatabaseException
	 */
    @Test
    public void testDeleteNail() 
    {
      Assertions.assertThrows(DatabaseException.class, () -> {
    	  String upc = "TestUPC";
  		int manufacturerID = 121;
  		int price = 1231;
  		double length = 0.5;
  		int numberInBox = 16;
  		NailsFastenersInventoryItemsGateway nailFastenerInventoryItem = new NailsFastenersInventoryItemsGateway(upc, manufacturerID, price, length, numberInBox);
  		assertTrue(nailFastenerInventoryItem.delete());
  		NailsFastenersInventoryItemsGateway deleteEntry = new NailsFastenersInventoryItemsGateway(nailFastenerInventoryItem.getInventoryItemID()); 
      });
    }
    
	
	
}