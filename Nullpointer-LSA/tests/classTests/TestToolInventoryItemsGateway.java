package classTests;

import static org.junit.Assert.assertArrayEquals;
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
import datasource.ToolDTO;
import datasourceClass.*;
import exception.DatabaseException;

class TestToolInventoryItemsGateway {

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
	void testAddingAndFindingaToolInventoryItem() throws DatabaseException {
			  String upc = "TestUPC2";
			    int manufacturerID = 121;
			    int price = 1231;
			    String description ="TestDescription2" ;
				ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway(upc,manufacturerID,price,description);
				ToolInventoryItemsGateway mTIIG = new ToolInventoryItemsGateway(TIIG.getInventoryItemID());

				assertEquals(upc,mTIIG.getUPC());
				assertEquals(manufacturerID,mTIIG.getManufacturerID());
				assertEquals(price,mTIIG.getPrice());
				assertEquals(description,mTIIG.getDescription());
		
  
  }

	@Test
   public void testEditingAToolInventoryItem() throws DatabaseException
   {

			  String upc = "TestUPC2";
			    int manufacturerID = 121;
			    int price = 1231;
			    String description ="TestDescription2" ;
				ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway(upc,manufacturerID,price,description);
				ToolInventoryItemsGateway mTIIG = new ToolInventoryItemsGateway(TIIG.getInventoryItemID());
				
				 mTIIG.setDescription("A new description");
				 mTIIG.setPrice(111123);
				 mTIIG.persist();

				assertEquals(upc,mTIIG.getUPC());
				assertEquals(manufacturerID,mTIIG.getManufacturerID());
				assertEquals(111123,mTIIG.getPrice());
				assertEquals("A new description",mTIIG.getDescription());
		
	   
   }

   @Test
   public void testFindAll() throws DatabaseException
   {
	    ArrayList<ToolDTO> ToolsInventoryItems = new ArrayList<ToolDTO>();
		
	    ToolsInventoryItems = ToolInventoryItemsGateway.findAll();
		assertNotNull(ToolsInventoryItems);
		assertEquals(5, ToolsInventoryItems.size());		       
	   
   }
    @Test
    public void testFindByManufacturerID() throws DatabaseException
    {
 		ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway("1111",1234,125,"This is a new tool");
 		ToolInventoryItemsGateway TIIG2 = new ToolInventoryItemsGateway("1131",1234,125,"This is another of the same tool");

 	    ArrayList<ToolDTO> ToolsInventoryItems = new ArrayList<ToolDTO>();
 		
 	    ToolsInventoryItems = ToolInventoryItemsGateway.findByManufacturerID(1234);
 		assertNotNull(ToolsInventoryItems);
 		assertEquals(2, ToolsInventoryItems.size());
 		
		assertEquals("1111", ToolsInventoryItems.get(0).getUpc());
		assertEquals("1131", ToolsInventoryItems.get(1).getUpc());
		
		assertEquals(125, ToolsInventoryItems.get(0).getPrice());
		assertEquals(125, ToolsInventoryItems.get(1).getPrice());

		assertEquals("This is a new tool", ToolsInventoryItems.get(0).getDescription());
		assertEquals("This is another of the same tool", ToolsInventoryItems.get(1).getDescription());	
 	   
    }
    
    
    
    
    

    @Test
    public void testFindByPriceRange() throws DatabaseException
    {
 		ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway("1111",1234,125,"This is a new tool");
 		ToolInventoryItemsGateway TIIG2 = new ToolInventoryItemsGateway("1131",1234,125,"This is another of the same tool");
 		ToolInventoryItemsGateway TIIG3 = new ToolInventoryItemsGateway("2542",5567,89,"This is a newer tool associated to no other tool");
 		ToolInventoryItemsGateway TIIG4 = new ToolInventoryItemsGateway("2222",5555,25,"This another tool");

 	    ArrayList<ToolDTO> ToolsInventoryItems = new ArrayList<ToolDTO>();
 		
 	    ToolsInventoryItems = ToolInventoryItemsGateway.findByPriceRange(25,125);
 		assertNotNull(ToolsInventoryItems);
 		assertEquals(4, ToolsInventoryItems.size());
 		
		assertEquals("1111", ToolsInventoryItems.get(0).getUpc());
		assertEquals("1131", ToolsInventoryItems.get(1).getUpc());
		assertEquals("2542", ToolsInventoryItems.get(2).getUpc());
		assertEquals("2222", ToolsInventoryItems.get(3).getUpc());

		
		assertEquals(125, ToolsInventoryItems.get(0).getPrice());
		assertEquals(125, ToolsInventoryItems.get(1).getPrice());
		assertEquals(89, ToolsInventoryItems.get(2).getPrice());
		assertEquals(25, ToolsInventoryItems.get(3).getPrice());

		assertEquals("This is a new tool", ToolsInventoryItems.get(0).getDescription());
		assertEquals("This is another of the same tool", ToolsInventoryItems.get(1).getDescription());	
		assertEquals("This is a newer tool associated to no other tool", ToolsInventoryItems.get(2).getDescription());	
		assertEquals("This another tool", ToolsInventoryItems.get(3).getDescription());	

 	   
    }
   
//   tests that it find the correct about of UPCs 
   @Test
   public void testFindByUPC() throws DatabaseException
   {
		ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway("1111",1234,125,"This is a new tool");
		ToolInventoryItemsGateway TIIG2 = new ToolInventoryItemsGateway("1111",1235,125,"This is another of the same tool");
		ToolInventoryItemsGateway TIIG3 = new ToolInventoryItemsGateway("222",1235,125,"This is not the same as the other tools");

	    ArrayList<ToolDTO> ToolsInventoryItems = new ArrayList<ToolDTO>();
		
	    ToolsInventoryItems = ToolInventoryItemsGateway.findByUpc("1111");
		assertNotNull(ToolsInventoryItems);
		assertEquals(2, ToolsInventoryItems.size());
		assertEquals("This is a new tool", ToolsInventoryItems.get(0).getDescription());
		assertEquals("This is another of the same tool", ToolsInventoryItems.get(1).getDescription());			       
	   
   }
   
   
  /**
   * throws an exception when not correct
  */
	
   @Test
   void throwsExceptionWhenNotFound() {
    
     Assertions.assertThrows(DatabaseException.class, () -> {
    	 ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway(100000000);
     });
    
   }
   /**
    * Tests that it throws an error when the minimum price is higher than the maximum price
    */
   
   @Test
   void throwsWhenMinimumIsLargerThanMaximum() {
    
     Assertions.assertThrows(DatabaseException.class, () -> {
    	 ToolInventoryItemsGateway.findByPriceRange(30,3);
     });
    
   }
   
   /**
    * Tests that the tool is delete and if they try to find it it throws an exception
    */
   
   @Test
   void testDeletingAToolInventoryItem() {
    
     Assertions.assertThrows(DatabaseException.class, () -> {
    	 String upc = "TestUPC2";
		    int manufacturerID = 121;
		    int price = 1231;
		    String description ="TestDescription2" ;
			ToolInventoryItemsGateway TIIG = new ToolInventoryItemsGateway(upc,manufacturerID,price,description);
			ToolInventoryItemsGateway cTIIG = new ToolInventoryItemsGateway(TIIG.getInventoryItemID());
			assertEquals(upc,cTIIG.getUPC());
			assertEquals(manufacturerID,cTIIG.getManufacturerID());
			assertEquals(price,cTIIG.getPrice());
			assertEquals(description,cTIIG.getDescription());
			
			
			assertTrue(TIIG.delete());
			ToolInventoryItemsGateway dTIIG = new ToolInventoryItemsGateway(TIIG.getInventoryItemID());
     });
    
   }

}
