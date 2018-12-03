package datasourceSingle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.InventoryItemDTO;
import datasource.NailDTO;
import datasource.PowerToolDTO;
import datasource.StripNailDTO;
import datasource.ToolDTO;
import datasourceClass.PowerToolsToStripNailsGateway;
import datasourceConcrete.NailsGateway;
import datasourceConcrete.PowerToolsGateway;
import datasourceConcrete.StripNailsGateway;
import datasourceConcrete.ToolsGateway;
import exception.DatabaseException;
import testData.NailsForTest;
import testData.PowerToolsForTest;
import testData.PowerToolsToStripNailsForTest;
import testData.StripNailsForTest;
import testData.ToolsForTest;

public class SingleTableHelper
{
	/**
	 * 
	 * @author Chris Roadcap @author Kanza Amin
	 *
	 *         Test Class which tests the functionality of the single class
	 *         inheritance and methods that help with InventoryItems table
	 *
	 *         There are seven methods: buildInventoryItemDtoList(resultSet result),
	 *         createInventoryItems(), createPowerToolsToStripNails(),
	 *         createInventoryView() populateTables(), cleanDB(),
	 *         findItem(ID) @throws
	 *
	 */
	/*
	 * BUILD INVENTORY DTO LIST
	 * 
	 * This method used the type variable from the single table inheritance for four
	 * cases Tool, Powertool, Nail, Stripnails and each type to each dto
	 */

	public static ArrayList<InventoryItemDTO> buildInventoryItemDtoList(ResultSet result) throws DatabaseException
	{
		ArrayList<InventoryItemDTO> dtos = new ArrayList<InventoryItemDTO>();
		try
		{
			while (result.next())
			{
				int id;
				String upc;
				int manufacturerID;
				int price;
				String description;
				boolean isBatteryPowered;
				double length;
				int numberInBox;
				int numberInStrip;

				int type = result.getInt("type");

				switch (type)
				{
				case 1:
					id = result.getInt("ID");
					upc = result.getString("UPC");
					manufacturerID = result.getInt("manID");
					price = result.getInt("price");
					description = result.getString("description");

					dtos.add(new ToolDTO(id, upc, manufacturerID, price, description));
					break;

				case 2:
					id = result.getInt("ID");
					upc = result.getString("UPC");
					manufacturerID = result.getInt("manID");
					price = result.getInt("price");
					description = result.getString("description");
					isBatteryPowered = result.getBoolean("batteryPowered");

					dtos.add(new PowerToolDTO(id, upc, manufacturerID, price, description, isBatteryPowered));
					break;

				case 3:

					id = result.getInt("ID");
					upc = result.getString("UPC");
					manufacturerID = result.getInt("manID");
					price = result.getInt("price");
					length = result.getDouble("length");
					numberInBox = result.getInt("numberInBox");

					dtos.add(new NailDTO(id, upc, manufacturerID, price, length, numberInBox));
					break;

				case 4:
					id = result.getInt("ID");
					upc = result.getString("UPC");
					manufacturerID = result.getInt("manID");
					price = result.getInt("price");
					length = result.getDouble("length");
					numberInStrip = result.getInt("numberInStrip");

					dtos.add(new StripNailDTO(id, upc, manufacturerID, price, length, numberInStrip));
					break;
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtos;
	}

	/*
	 * Create Inventory Items
	 * 
	 * This method creates the Inventory Items table
	 */
	public static void createInventoryItems() throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "create table if not exists  InventoryItems(\n" + "  ID int Primary key auto_increment,\n"
				+ "  UPC varchar(25),\n" + "  manID int,\n" + "  price int,\n" + "  description varchar(50),\n"
				+ "  batteryPowered boolean,\n" + "  length double,\n" + "  numberInStrip int,\n"
				+ "  numberInBox int,\n" + "  type int);";
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Create PowerToolsToStripNails
	 * 
	 * This method creates the PowerToolsToStripNails table
	 */
	public static void createPowerToolsToStripNails() throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "create table if not exists PowerToolsToStripNails(" + "  autoID int Primary key auto_increment,"
				+ "  powerToolID int," + "  stripNailId int);";
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Create InventoryView
	 * 
	 * This method creates the view between IntenvoryItems and
	 * PowerToolsToStripNails
	 */
	public static void createInventoryView() throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String query = "CREATE OR REPLACE VIEW InventoryView AS " + "SELECT * "
				+ "FROM InventoryItems FULL JOIN PowerToolsToStripNails " + "ON ID = powerToolID OR ID = stripNailID;";
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Populate Tables
	 * 
	 * This method popuates the tables with all the instance variables
	 */
	public static void populateTables() throws DatabaseException
	{                                     
		InventoryItemsGateway tool1 = new InventoryItemsGateway(ToolsForTest.TOOL1.getUpc(), ToolsForTest.TOOL1.getManufacturerID(), ToolsForTest.TOOL1.getPrice(), ToolsForTest.TOOL1.getDescription());
		InventoryItemsGateway tool2 = new InventoryItemsGateway(ToolsForTest.TOOL2.getUpc(), ToolsForTest.TOOL2.getManufacturerID(), ToolsForTest.TOOL2.getPrice(), ToolsForTest.TOOL2.getDescription());
		InventoryItemsGateway tool3 = new InventoryItemsGateway(ToolsForTest.TOOL3.getUpc(), ToolsForTest.TOOL3.getManufacturerID(), ToolsForTest.TOOL3.getPrice(), ToolsForTest.TOOL3.getDescription());
		InventoryItemsGateway tool4 = new InventoryItemsGateway(ToolsForTest.TOOL4.getUpc(), ToolsForTest.TOOL4.getManufacturerID(), ToolsForTest.TOOL4.getPrice(), ToolsForTest.TOOL4.getDescription());
		InventoryItemsGateway tool5 = new InventoryItemsGateway(ToolsForTest.TOOL5.getUpc(), ToolsForTest.TOOL5.getManufacturerID(), ToolsForTest.TOOL5.getPrice(), ToolsForTest.TOOL5.getDescription());
		                                
		InventoryItemsGateway powerTool1 = new InventoryItemsGateway(PowerToolsForTest.POWERTOOL1.getUpc(),PowerToolsForTest.POWERTOOL1.getManufacturerID(), PowerToolsForTest.POWERTOOL1.getPrice(), PowerToolsForTest.POWERTOOL1.getDescription(), PowerToolsForTest.POWERTOOL1.isBatteryPowered());
		InventoryItemsGateway powerTool2 = new InventoryItemsGateway(PowerToolsForTest.POWERTOOL2.getUpc(),PowerToolsForTest.POWERTOOL2.getManufacturerID(), PowerToolsForTest.POWERTOOL2.getPrice(), PowerToolsForTest.POWERTOOL2.getDescription(), PowerToolsForTest.POWERTOOL2.isBatteryPowered());
		InventoryItemsGateway powerTool3 = new InventoryItemsGateway(PowerToolsForTest.POWERTOOL3.getUpc(),PowerToolsForTest.POWERTOOL3.getManufacturerID(), PowerToolsForTest.POWERTOOL3.getPrice(), PowerToolsForTest.POWERTOOL3.getDescription(), PowerToolsForTest.POWERTOOL3.isBatteryPowered());
		InventoryItemsGateway powerTool4 = new InventoryItemsGateway(PowerToolsForTest.POWERTOOL4.getUpc(),PowerToolsForTest.POWERTOOL4.getManufacturerID(), PowerToolsForTest.POWERTOOL4.getPrice(), PowerToolsForTest.POWERTOOL4.getDescription(), PowerToolsForTest.POWERTOOL4.isBatteryPowered());
		InventoryItemsGateway powerTool5 = new InventoryItemsGateway(PowerToolsForTest.POWERTOOL5.getUpc(),PowerToolsForTest.POWERTOOL5.getManufacturerID(), PowerToolsForTest.POWERTOOL5.getPrice(), PowerToolsForTest.POWERTOOL5.getDescription(), PowerToolsForTest.POWERTOOL5.isBatteryPowered());
		
		InventoryItemsGateway nail1 = new InventoryItemsGateway(NailsForTest.NAIL1.getUpc(), NailsForTest.NAIL1.getManufacturerID(), NailsForTest.NAIL1.getPrice(), NailsForTest.NAIL1.getLength(), NailsForTest.NAIL1.getNumberInBox());
		InventoryItemsGateway nail2 = new InventoryItemsGateway(NailsForTest.NAIL2.getUpc(), NailsForTest.NAIL2.getManufacturerID(), NailsForTest.NAIL2.getPrice(), NailsForTest.NAIL2.getLength(), NailsForTest.NAIL2.getNumberInBox());
		InventoryItemsGateway nail3 = new InventoryItemsGateway(NailsForTest.NAIL3.getUpc(), NailsForTest.NAIL3.getManufacturerID(), NailsForTest.NAIL3.getPrice(), NailsForTest.NAIL3.getLength(), NailsForTest.NAIL3.getNumberInBox());
		InventoryItemsGateway nail4 = new InventoryItemsGateway(NailsForTest.NAIL4.getUpc(), NailsForTest.NAIL4.getManufacturerID(), NailsForTest.NAIL4.getPrice(), NailsForTest.NAIL4.getLength(), NailsForTest.NAIL4.getNumberInBox());
		InventoryItemsGateway nail5 = new InventoryItemsGateway(NailsForTest.NAIL5.getUpc(), NailsForTest.NAIL5.getManufacturerID(), NailsForTest.NAIL5.getPrice(), NailsForTest.NAIL5.getLength(), NailsForTest.NAIL5.getNumberInBox());
		
		InventoryItemsGateway stripNail1 = new InventoryItemsGateway(StripNailsForTest.STRIPNAIL1.getUpc(), StripNailsForTest.STRIPNAIL1.getManufacturerID(), StripNailsForTest.STRIPNAIL1.getPrice(),  StripNailsForTest.STRIPNAIL1.getNumberInStrip(),StripNailsForTest.STRIPNAIL1.getLength());
		InventoryItemsGateway stripNail2 = new InventoryItemsGateway(StripNailsForTest.STRIPNAIL2.getUpc(), StripNailsForTest.STRIPNAIL2.getManufacturerID(), StripNailsForTest.STRIPNAIL2.getPrice(),  StripNailsForTest.STRIPNAIL2.getNumberInStrip(),StripNailsForTest.STRIPNAIL2.getLength());
		InventoryItemsGateway stripNail3 = new InventoryItemsGateway(StripNailsForTest.STRIPNAIL3.getUpc(), StripNailsForTest.STRIPNAIL3.getManufacturerID(), StripNailsForTest.STRIPNAIL3.getPrice(),  StripNailsForTest.STRIPNAIL3.getNumberInStrip(),StripNailsForTest.STRIPNAIL3.getLength());
		InventoryItemsGateway stripNail4 = new InventoryItemsGateway(StripNailsForTest.STRIPNAIL4.getUpc(), StripNailsForTest.STRIPNAIL4.getManufacturerID(), StripNailsForTest.STRIPNAIL4.getPrice(),  StripNailsForTest.STRIPNAIL4.getNumberInStrip(),StripNailsForTest.STRIPNAIL4.getLength());
		InventoryItemsGateway stripNail5 = new InventoryItemsGateway(StripNailsForTest.STRIPNAIL5.getUpc(), StripNailsForTest.STRIPNAIL5.getManufacturerID(), StripNailsForTest.STRIPNAIL5.getPrice(),  StripNailsForTest.STRIPNAIL5.getNumberInStrip(),StripNailsForTest.STRIPNAIL5.getLength());
		                                                                                                                                                                                                                                                                                         
		PowerToolsToStripNailsGateway M2Mrelationship1 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION1.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION1.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship2 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION2.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION2.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship3 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION3.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION3.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship4 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION4.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION4.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship5 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION5.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION5.getStripNailID());
		

	}

	/*
	 * Clean database
	 * 
	 * This method drops and cleans all the tables
	 */
	public static void cleanDB() throws SQLException, DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String query1 = "drop table PowerToolsToStripNails;";
		String query2 = "drop table InventoryItems;";
		String query3 = "drop view InventoryView;";
		PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query1);
		PreparedStatement stmnt2 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query2);
		PreparedStatement stmnt3 = dbm.getConnection(dbTables.SINGLE).prepareStatement(query3);
		stmnt.executeUpdate();
		stmnt2.executeUpdate();
		stmnt3.executeUpdate();

	}

	/*
	 * Find an Item
	 * 
	 * This method finds the specific items in InventoryItems using the ID
	 */
	public static ResultSet findItem(int ID) throws DatabaseException
	{
		DatabaseManager manager = DatabaseManager.getDatabaseManager();

		String query = "Select * from InventoryItems WHERE (ID = ?);";
		PreparedStatement preparedStatement;

		try
		{
			preparedStatement = manager.getConnection(dbTables.SINGLE).prepareStatement(query);
			preparedStatement.setInt(1, ID);
			return preparedStatement.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
