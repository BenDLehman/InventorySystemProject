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
import datasource.PowerToolDTO;
import datasourceClass.*;
import exception.DatabaseException;

class TestPowerToolInventoryItemsGateway {
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
	void testaddingAndFindingAPowerToolInventoryItem() throws DatabaseException  {
		
		
			 String upc = "TestUPC";
			    int manufacturerID = 121;
			    int price = 1231;
			    String description ="TestDescription" ;
				PowerToolInventoryItemsGateway TIIG = new PowerToolInventoryItemsGateway(upc,manufacturerID,price,description,true);
				PowerToolInventoryItemsGateway pTIIG = new PowerToolInventoryItemsGateway(TIIG.getInventoryItemID());
				

				assertEquals(upc,pTIIG.getUPC());
				assertEquals(manufacturerID,pTIIG.getManufacturerID());
				assertEquals(price,pTIIG.getPrice());
				assertEquals(description,pTIIG.getDescription());
				assertEquals(true,pTIIG.getBatteryPowered());

	   
	}
	
	   @Test
	   public void testEditingAPowerToolInventoryItem() throws DatabaseException
	   {
		   
		   
		  
				 String upc = "TestUPC";
				    int manufacturerID = 120;
				    int price = 123333;
				    String description ="TestDescription" ;
					PowerToolInventoryItemsGateway TIIG = new PowerToolInventoryItemsGateway(upc,manufacturerID,price,description,false);
					PowerToolInventoryItemsGateway pTIIG = new PowerToolInventoryItemsGateway(TIIG.getInventoryItemID());

					
					   pTIIG.setDescription("A new description");
					   pTIIG.setPrice(123);
					   pTIIG.setBatteryPower(true);
					   pTIIG.persist();
					
					assertEquals(upc,pTIIG.getUPC());
					assertEquals(manufacturerID,pTIIG.getManufacturerID());
					assertEquals(123,pTIIG.getPrice());
					assertEquals("A new description",pTIIG.getDescription());
					assertEquals(true,pTIIG.getBatteryPowered());

		   
	   }
	   

	   
	   
	   @Test
	   public void testFindAll() throws DatabaseException
	   {
		   ArrayList<PowerToolDTO> PowerToolsInventoryItems = new ArrayList<PowerToolDTO>();
			
		    PowerToolsInventoryItems = PowerToolInventoryItemsGateway.findAll();
			assertNotNull(PowerToolsInventoryItems);
			assertEquals(5, PowerToolsInventoryItems.size());		       
		   
	   }
	   
	   
	   @Test
	   public void testFindByUPC() throws DatabaseException
	   {
			PowerToolInventoryItemsGateway PIIG = new PowerToolInventoryItemsGateway("1111",1234,125,"This is a new power tool", false);
			PowerToolInventoryItemsGateway PIIG2 = new PowerToolInventoryItemsGateway("1111",1235,125,"This is another of the same power tool",true);
		    ArrayList<PowerToolDTO> PowerToolInventoryItems = new ArrayList<PowerToolDTO>();
			
		    PowerToolInventoryItems = PowerToolInventoryItemsGateway.findByUPC("1111");
			assertNotNull(PowerToolInventoryItems);
			assertEquals(2, PowerToolInventoryItems.size());
			assertEquals("This is a new power tool", PowerToolInventoryItems.get(0).getDescription());
			assertEquals("This is another of the same power tool", PowerToolInventoryItems.get(1).getDescription());						
					//I made the test not equal because the 
			       
		   
	   }
	   
	   
	    @Test
	    public void testFindByManufacturerID() throws DatabaseException
	    {
			   PowerToolInventoryItemsGateway PIIG = new PowerToolInventoryItemsGateway("1111",1234,125,"This is a new tool", false);
			   PowerToolInventoryItemsGateway PIIG2 = new PowerToolInventoryItemsGateway("1131",1234,125,"This is another of the same tool",true);
	 	    ArrayList<PowerToolDTO> PowerToolsInventoryItems = new ArrayList<PowerToolDTO>();
	 		
	 	   PowerToolsInventoryItems = PowerToolInventoryItemsGateway.findByManufacturerID(1234);
	 		assertNotNull(PowerToolsInventoryItems);
	 		assertEquals(2, PowerToolsInventoryItems.size());
	 		
			assertEquals("1111", PowerToolsInventoryItems.get(0).getUpc());
			assertEquals("1131", PowerToolsInventoryItems.get(1).getUpc());
			
			assertEquals(125, PowerToolsInventoryItems.get(0).getPrice());
			assertEquals(125, PowerToolsInventoryItems.get(1).getPrice());

			assertEquals("This is a new tool", PowerToolsInventoryItems.get(0).getDescription());
			assertEquals("This is another of the same tool", PowerToolsInventoryItems.get(1).getDescription());	
	 	   
	    }
	    
	    @Test
	    public void testPriceRange() throws DatabaseException
	    {
			   PowerToolInventoryItemsGateway PIIG = new PowerToolInventoryItemsGateway("1111",1234,125,"This is a new tool", false);
			   PowerToolInventoryItemsGateway PIIG2 = new PowerToolInventoryItemsGateway("1131",1234,125,"This is another of the same tool",true);
			   PowerToolInventoryItemsGateway PIIG3 = new PowerToolInventoryItemsGateway("2542",5567,89,"This is a newer tool associated to no other tool",true);
			   PowerToolInventoryItemsGateway PIIG4 = new PowerToolInventoryItemsGateway("2222",5555,25,"This another tool",false);
	 	   ArrayList<PowerToolDTO> PowerToolsInventoryItems = new ArrayList<PowerToolDTO>();
	 		
	 	    PowerToolsInventoryItems = PowerToolInventoryItemsGateway.findByPriceRange(25,125);
	 		assertNotNull(PowerToolsInventoryItems);
	 		assertEquals(4, PowerToolsInventoryItems.size());
	 		
			assertEquals("1111", PowerToolsInventoryItems.get(0).getUpc());
			assertEquals("1131", PowerToolsInventoryItems.get(1).getUpc());
			assertEquals("2542", PowerToolsInventoryItems.get(2).getUpc());
			assertEquals("2222", PowerToolsInventoryItems.get(3).getUpc());

			
			assertEquals(125, PowerToolsInventoryItems.get(0).getPrice());
			assertEquals(125, PowerToolsInventoryItems.get(1).getPrice());
			assertEquals(89, PowerToolsInventoryItems.get(2).getPrice());
			assertEquals(25, PowerToolsInventoryItems.get(3).getPrice());


			assertEquals("This is a new tool", PowerToolsInventoryItems.get(0).getDescription());
			assertEquals("This is another of the same tool", PowerToolsInventoryItems.get(1).getDescription());	
			assertEquals("This is a newer tool associated to no other tool", PowerToolsInventoryItems.get(2).getDescription());	
			assertEquals("This another tool", PowerToolsInventoryItems.get(3).getDescription());	
			
			
           assertFalse(PowerToolsInventoryItems.get(0).isBatteryPowered());
           assertTrue(PowerToolsInventoryItems.get(1).isBatteryPowered());
           assertTrue(PowerToolsInventoryItems.get(2).isBatteryPowered());
           assertFalse(PowerToolsInventoryItems.get(3).isBatteryPowered());


	 	   
	    }
	    @Test
	    public void testNoResultInFindByManufacturerID() throws DatabaseException
	    {
			   PowerToolInventoryItemsGateway PIIG = new PowerToolInventoryItemsGateway("1111",1234,125,"This is a new tool", false);
			   PowerToolInventoryItemsGateway PIIG2 = new PowerToolInventoryItemsGateway("1131",1234,125,"This is another of the same tool",true);
			   PowerToolInventoryItemsGateway PIIG3 = new PowerToolInventoryItemsGateway("2542",5567,89,"This is a newer tool associated to no other tool",true);
			   PowerToolInventoryItemsGateway PIIG4 = new PowerToolInventoryItemsGateway("2222",5555,25,"This another tool",false);
	 	    ArrayList<PowerToolDTO> PowerToolsInventoryItems = new ArrayList<PowerToolDTO>();
	 		
	 	   PowerToolsInventoryItems = PowerToolInventoryItemsGateway.findByManufacturerID(7777);
	 	   
	    }
	    @Test
	    void throwsExceptionWhenNotFound() {
	     
	      Assertions.assertThrows(DatabaseException.class, () -> {
				PowerToolInventoryItemsGateway pTIIG = new PowerToolInventoryItemsGateway(100000000);
	      });
	     
	    }
	    
	    @Test
	    void throwsWhenMinimumIsLargerThanMaximum() {
	     
	      Assertions.assertThrows(DatabaseException.class, () -> {
	    	  PowerToolInventoryItemsGateway.findByPriceRange(30,3);
	      });
	     
	    }
	    
	
		   @Test
		    void testDeletingAPowerToolInventoryItem() {
		     
		      Assertions.assertThrows(DatabaseException.class, () -> {
		    	  String upc = "TestUPC";
				    int manufacturerID = 120;
				    int price = 123333;
				    String description ="TestDescription" ;
					PowerToolInventoryItemsGateway pTIIG = new PowerToolInventoryItemsGateway(upc,manufacturerID,price,description,false);
					PowerToolInventoryItemsGateway fPTIIG = new PowerToolInventoryItemsGateway(pTIIG.getInventoryItemID());			
					
					assertEquals(upc,fPTIIG.getUPC());
					assertEquals(manufacturerID,fPTIIG.getManufacturerID());
					assertEquals(price,fPTIIG.getPrice());
					assertEquals(description,fPTIIG.getDescription());
					assertEquals(false,fPTIIG.getBatteryPowered());
					assertTrue(fPTIIG.delete());
					PowerToolInventoryItemsGateway dPTIIG = new PowerToolInventoryItemsGateway(pTIIG.getInventoryItemID());			
				  });
		     
		    }
	   
   
}
