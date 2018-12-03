package singleTests;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.*;
import datasourceSingle.*;
/**
 * 
 * @author Chris Roadcap
 * @author Kanza Amin
 *
 *Test Class which tests the functionality of the SingleTableHelper class
 */
class SingleTableHelperTest {
	
	DatabaseManager dbm;
	java.sql.Connection connection = null;
	/**
	  * Creates tables before every test, if they don't already exist
	 * @throws Exception  when communications with the database fail/ improper database syntax
	 */
	 @BeforeEach
     void setUpTests() throws Exception
  {
  	SingleTableHelper.createInventoryItems();
  	SingleTableHelper.createPowerToolsToStripNails();
  	SingleTableHelper.createInventoryView();
  	
		try {
			dbm = DatabaseManager.getDatabaseManager();
			connection = dbm.getConnection(dbTables.SINGLE);
			connection.setAutoCommit(false);
		
			
			
		} catch (Exception e) {
			
		}

  }
	 /**
	   	 * Resets the database to empty after each test 
	   	 * @throws Exception  when communications with the database fail/ improper database syntax
	   	 */
	   @AfterEach
	    void afterTests() throws Exception
	    {
			connection.rollback();
	    }
	   /**
		 * Tests the Create Contructors and the builds the InventoryItemDTO list and finds each item. The test function as intended
		 * @throws Exception  when communications with the database fail/ improper database syntax
		 */
	@Test
	void testBuildInventoryItemsDtoListAndFind() throws Exception 
	{
		//creates a tool, powertool, nail, and stripnail
		InventoryItemsGateway tool = new InventoryItemsGateway("abc1", 1, 3,"tool description");
		InventoryItemsGateway powerTool = new InventoryItemsGateway("abc2",4,5,"powertool description",true);
		InventoryItemsGateway nail = new InventoryItemsGateway("abc3", 2,3, 2.5, 5);
		InventoryItemsGateway stripNail = new InventoryItemsGateway("abc4", 2,7, 6, 3.5);
		
		//creates resultSets for each type
		ResultSet toolResult = SingleTableHelper.findItem(tool.getAutoGeneratedID());
		ResultSet powerToolResult = SingleTableHelper.findItem(powerTool.getAutoGeneratedID());
		ResultSet nailResult = SingleTableHelper.findItem(nail.getAutoGeneratedID());
		ResultSet stripNailResult = SingleTableHelper.findItem(stripNail.getAutoGeneratedID());
		//creates DTO's for each type
		ArrayList<InventoryItemDTO> toolList = SingleTableHelper.buildInventoryItemDtoList(toolResult);
		ArrayList<InventoryItemDTO> powerToolList = SingleTableHelper.buildInventoryItemDtoList(powerToolResult);
		ArrayList<InventoryItemDTO> nailList = SingleTableHelper.buildInventoryItemDtoList(nailResult);
		ArrayList<InventoryItemDTO> stripNailList = SingleTableHelper.buildInventoryItemDtoList(stripNailResult);
		
		//tests is the dtos as the instanceOf the actual DTO classes
		assertTrue(toolList.get(0) instanceof ToolDTO);
		assertTrue(powerToolList.get(0) instanceof PowerToolDTO);
		assertTrue(nailList.get(0) instanceof NailDTO);
		assertTrue(stripNailList.get(0) instanceof StripNailDTO);
		
		//tests that the dtos are not null
		assertNotNull(toolList.get(0));
		assertNotNull(powerToolList.get(0));
		assertNotNull(nailList.get(0));
		assertNotNull(stripNailList.get(0));
		
	}
	
	/**
	 * Tests the creation of InventoryItems, InventoryView, and PowerToolsToStripNails. The test function as intended
	 * @throws Exception  when communications with the database fail/ improper database syntax
	 */
	@Test
	public void testCreateTables() throws Exception
	{
		//creates a tool, powertool, nail, and stripnail
		InventoryItemsGateway powerTool = new InventoryItemsGateway("abc2",4,5,"powertool description",true);
		InventoryItemsGateway stripNail = new InventoryItemsGateway("abc4", 2,7, 6, 3.5);
		//creates InventoryItems table
		SingleTableHelper.createInventoryItems();
		
		//makes select statements to find all items using ID
		String query = "Select * from InventoryItems where ID = ?";
		String query2 = "Select * from InventoryItems where ID = ?";

		PreparedStatement stmnt = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query);
		PreparedStatement stmnt2 = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query);
		//ID = 1 are set here
		stmnt.setInt(1, powerTool.getAutoGeneratedID());
		stmnt2.setInt(1, stripNail.getAutoGeneratedID());
		
		ResultSet result = stmnt.executeQuery();
		ResultSet result2 = stmnt2.executeQuery();
		//checks to see if correct UPC is in the table
		result.next();
		assertEquals(result.getString("UPC"), "abc2");
		result2.next();
		assertEquals(result2.getString("UPC"), "abc4");
		
		
		//creates PowerToolsToStripNail table
		SingleTableHelper.createPowerToolsToStripNails();
		//instance of PowerToolsToStripNailsGateway with powetoolID and stripnailID (autogenteratedID's)
		PowerToolsToStripNailsGateway association = new PowerToolsToStripNailsGateway(powerTool.getAutoGeneratedID(),stripNail.getAutoGeneratedID());
		
		//makes select statements to find all items using ID
		String relationQuery = "Select * from PowerToolsToStripNails where autoID = ?";
		PreparedStatement relationStatement = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(relationQuery);
		relationStatement.setInt(1, association.getAutoGeneratedID());
		
		ResultSet relationResult = relationStatement.executeQuery();
		relationResult.next();
		
		//tests that the instance of PowerToolsToStripNailsGateway is associated with the ID as the autoID
		assertEquals(association.getPowerToolID(), powerTool.getAutoGeneratedID());
		assertEquals(association.getStripNailID(), stripNail.getAutoGeneratedID());
		
		//creates the inventoryView table
		SingleTableHelper.createInventoryView();
		String viewQuery = "Select * from InventoryView where type = ?;";
		PreparedStatement viewStatement = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(viewQuery);
		
		//gets the ID as 1, will be added to the viewQuery above
		viewStatement.setInt(1, powerTool.getType());
		ResultSet viewResult = viewStatement.executeQuery();
		viewResult.next();
		
		//tests getting the type column 
		assertEquals(2, viewResult.getInt("type"));
		
	}
	/**
	 * Tests the function of dropping the tables. The test function as intended
	 * @throws Exception  when communications with the database fail/ improper database syntax
	 */
	@Test
	public void testDropTables() throws Exception
	{
		boolean tablesDropped = false;
		try 
		{
			SingleTableHelper.cleanDB();
			String query = "Select * from InventoryItems where ID = ?";
			PreparedStatement relationStatement = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query);
			relationStatement.executeQuery();
		}
		catch(Exception e)
		{
			tablesDropped = true;
		}
		assertTrue(tablesDropped);
	}
	/**
	 * Tests the population of all the tables. The test function as intended
	 * @throws Exception  when communications with the database fail/ improper database syntax
	 */
	@Test
	public void populateTables() throws SQLException, Exception
	{
		//cleans the dabatase
		SingleTableHelper.cleanDB();
		//makes the inventoryItems table
		SingleTableHelper.createInventoryItems();
		//makes the createPowerToolsToStripNails table
		SingleTableHelper.createPowerToolsToStripNails();
		//makes the inventoryView table
		SingleTableHelper.createInventoryView();
		//populates the tables
		SingleTableHelper.populateTables();
		
		//selects all colums from each of the tables		
		String query1 = "Select * from InventoryItems;";
		String query2 = "Select * from PowerToolsToStripNails;";
		String query3 = "Select * from InventoryView;";
		
		//each preparedStatement gets the connections for each query above
		PreparedStatement stmt = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query1);
		PreparedStatement stmt2 = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query2);
		PreparedStatement stmt3 = DatabaseManager.getDatabaseManager().getConnection(dbTables.SINGLE).prepareStatement(query3);
		
		ResultSet rs1 = stmt.executeQuery();
		ResultSet rs2 = stmt2.executeQuery();
		ResultSet rs3 = stmt3.executeQuery();
		
		//tests each resultSet executes correctly
		assertTrue(rs1.next());
		assertTrue(rs2.next());
		assertTrue(rs3.next());
		
		
	}
		
		
		
		
		
		
		
		
}