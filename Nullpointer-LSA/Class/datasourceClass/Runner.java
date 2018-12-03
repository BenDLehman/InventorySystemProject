package datasourceClass;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasourceConcrete.PowerToolsToStripNailsGateway;
import exception.DatabaseException;
import testData.NailsForTest;
import testData.PowerToolsForTest;
import testData.PowerToolsToStripNailsForTest;
import testData.StripNailsForTest;
import testData.ToolsForTest;

public class Runner 
{
	private static dbTables classConnection = dbTables.CLASS;
	
	public static void main(String[] args) throws DatabaseException
	{
		dropAllClassTables();
		createAllClassTables();
		populateAllClassTables();
	}
	
	public static void createAllClassTables() throws DatabaseException {
		 DatabaseManager dbm = null;
		 dbm = DatabaseManager.getDatabaseManager();		
						 createInventoryItem(dbm);
						 createToolsTable(dbm);
						 createFastenerTable(dbm);
						 createStripNailTable(dbm);
						 createNailsTable(dbm);
						 createPowerToolTable(dbm);
						 createPowerToolView(dbm);
						 createStripNailView(dbm);
						 createToolsView(dbm);
						 createNailsView(dbm);
						 createPowerToolsToStripNails(dbm);

		}
	
	
	private static void createToolsView(DatabaseManager dbm) throws DatabaseException  {

	     String ToolInventoryItemViewQ = "CREATE OR REPLACE VIEW ToolInventoryItems AS SELECT Tools.InventoryItemID, upc,"
	     									+ " manufacturerID,price,description"
	     									+ " FROM InventoryItems"
	     									+ " INNER JOIN Tools ON InventoryItems.ID = Tools.InventoryItemID;";	
		  try {
			  PreparedStatement ToolInventoryItemViewStmt = dbm.getConnection(dbTables.CLASS).prepareStatement(ToolInventoryItemViewQ);	
			  ToolInventoryItemViewStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
               throw new DatabaseException("Tools View was not created");
		}
	}

	public static void dropAllClassTables() throws DatabaseException
	{
		DatabaseManager dbm = null;
		 dbm = DatabaseManager.getDatabaseManager();
		dropPowerToolsToStripNails(dbm);	
		dropPowerToolInventoryItemsView(dbm);
		dropToolsView(dbm);
		dropStripNailsView(dbm);
		dropNailsView(dbm);
		dropPowerTools(dbm);
		dropStripNails(dbm);
		dropNails(dbm);
		dropTools(dbm);
		dropFasteners(dbm);
		dropInventoryItems(dbm);

		

		
	}
	
	
	protected static void populateAllClassTables() throws DatabaseException 
	{		

		ToolInventoryItemsGateway tool1 = new ToolInventoryItemsGateway(ToolsForTest.TOOL1.getUpc(), ToolsForTest.TOOL1.getManufacturerID(), ToolsForTest.TOOL1.getPrice(), ToolsForTest.TOOL1.getDescription());
		ToolInventoryItemsGateway tool2 = new ToolInventoryItemsGateway(ToolsForTest.TOOL2.getUpc(), ToolsForTest.TOOL2.getManufacturerID(), ToolsForTest.TOOL2.getPrice(), ToolsForTest.TOOL2.getDescription());
		ToolInventoryItemsGateway tool3 = new ToolInventoryItemsGateway(ToolsForTest.TOOL3.getUpc(), ToolsForTest.TOOL3.getManufacturerID(), ToolsForTest.TOOL3.getPrice(), ToolsForTest.TOOL3.getDescription());
		ToolInventoryItemsGateway tool4 = new ToolInventoryItemsGateway(ToolsForTest.TOOL4.getUpc(), ToolsForTest.TOOL4.getManufacturerID(), ToolsForTest.TOOL4.getPrice(), ToolsForTest.TOOL4.getDescription());
		ToolInventoryItemsGateway tool5 = new ToolInventoryItemsGateway(ToolsForTest.TOOL5.getUpc(), ToolsForTest.TOOL5.getManufacturerID(), ToolsForTest.TOOL5.getPrice(), ToolsForTest.TOOL5.getDescription());
		
		PowerToolInventoryItemsGateway powerTool1 = new PowerToolInventoryItemsGateway(PowerToolsForTest.POWERTOOL1.getUpc(),PowerToolsForTest.POWERTOOL1.getManufacturerID(), PowerToolsForTest.POWERTOOL1.getPrice(), PowerToolsForTest.POWERTOOL1.getDescription(), PowerToolsForTest.POWERTOOL1.isBatteryPowered());
		PowerToolInventoryItemsGateway powerTool2 = new PowerToolInventoryItemsGateway(PowerToolsForTest.POWERTOOL2.getUpc(),PowerToolsForTest.POWERTOOL2.getManufacturerID(), PowerToolsForTest.POWERTOOL2.getPrice(), PowerToolsForTest.POWERTOOL2.getDescription(), PowerToolsForTest.POWERTOOL2.isBatteryPowered());
		PowerToolInventoryItemsGateway powerTool3 = new PowerToolInventoryItemsGateway(PowerToolsForTest.POWERTOOL3.getUpc(),PowerToolsForTest.POWERTOOL3.getManufacturerID(), PowerToolsForTest.POWERTOOL3.getPrice(), PowerToolsForTest.POWERTOOL3.getDescription(), PowerToolsForTest.POWERTOOL3.isBatteryPowered());
		PowerToolInventoryItemsGateway powerTool4 = new PowerToolInventoryItemsGateway(PowerToolsForTest.POWERTOOL4.getUpc(),PowerToolsForTest.POWERTOOL4.getManufacturerID(), PowerToolsForTest.POWERTOOL4.getPrice(), PowerToolsForTest.POWERTOOL4.getDescription(), PowerToolsForTest.POWERTOOL4.isBatteryPowered());
		PowerToolInventoryItemsGateway powerTool5 = new PowerToolInventoryItemsGateway(PowerToolsForTest.POWERTOOL5.getUpc(),PowerToolsForTest.POWERTOOL5.getManufacturerID(), PowerToolsForTest.POWERTOOL5.getPrice(), PowerToolsForTest.POWERTOOL5.getDescription(), PowerToolsForTest.POWERTOOL5.isBatteryPowered());
		
		
		NailsFastenersInventoryItemsGateway nail1 = new NailsFastenersInventoryItemsGateway(NailsForTest.NAIL1.getUpc(), NailsForTest.NAIL1.getManufacturerID(), NailsForTest.NAIL1.getPrice(), NailsForTest.NAIL1.getLength(), NailsForTest.NAIL1.getNumberInBox());
		NailsFastenersInventoryItemsGateway nail2 = new NailsFastenersInventoryItemsGateway(NailsForTest.NAIL2.getUpc(), NailsForTest.NAIL2.getManufacturerID(), NailsForTest.NAIL2.getPrice(), NailsForTest.NAIL2.getLength(), NailsForTest.NAIL2.getNumberInBox());
		NailsFastenersInventoryItemsGateway nail3 = new NailsFastenersInventoryItemsGateway(NailsForTest.NAIL3.getUpc(), NailsForTest.NAIL3.getManufacturerID(), NailsForTest.NAIL3.getPrice(), NailsForTest.NAIL3.getLength(), NailsForTest.NAIL3.getNumberInBox());
		NailsFastenersInventoryItemsGateway nail4 = new NailsFastenersInventoryItemsGateway(NailsForTest.NAIL4.getUpc(), NailsForTest.NAIL4.getManufacturerID(), NailsForTest.NAIL4.getPrice(), NailsForTest.NAIL4.getLength(), NailsForTest.NAIL4.getNumberInBox());
		NailsFastenersInventoryItemsGateway nail5 = new NailsFastenersInventoryItemsGateway(NailsForTest.NAIL5.getUpc(), NailsForTest.NAIL5.getManufacturerID(), NailsForTest.NAIL5.getPrice(), NailsForTest.NAIL5.getLength(), NailsForTest.NAIL5.getNumberInBox());
		
		StripNailsFastenersInventoryItemsGateway stripNail1 = new StripNailsFastenersInventoryItemsGateway(StripNailsForTest.STRIPNAIL1.getUpc(), StripNailsForTest.STRIPNAIL1.getManufacturerID(), StripNailsForTest.STRIPNAIL1.getPrice(), StripNailsForTest.STRIPNAIL1.getLength(), StripNailsForTest.STRIPNAIL1.getNumberInStrip());
		StripNailsFastenersInventoryItemsGateway stripNail2 = new StripNailsFastenersInventoryItemsGateway(StripNailsForTest.STRIPNAIL2.getUpc(), StripNailsForTest.STRIPNAIL2.getManufacturerID(), StripNailsForTest.STRIPNAIL2.getPrice(), StripNailsForTest.STRIPNAIL2.getLength(), StripNailsForTest.STRIPNAIL2.getNumberInStrip());
		StripNailsFastenersInventoryItemsGateway stripNail3 = new StripNailsFastenersInventoryItemsGateway(StripNailsForTest.STRIPNAIL3.getUpc(), StripNailsForTest.STRIPNAIL3.getManufacturerID(), StripNailsForTest.STRIPNAIL3.getPrice(), StripNailsForTest.STRIPNAIL3.getLength(), StripNailsForTest.STRIPNAIL3.getNumberInStrip());
		StripNailsFastenersInventoryItemsGateway stripNail4 = new StripNailsFastenersInventoryItemsGateway(StripNailsForTest.STRIPNAIL4.getUpc(), StripNailsForTest.STRIPNAIL4.getManufacturerID(), StripNailsForTest.STRIPNAIL4.getPrice(), StripNailsForTest.STRIPNAIL4.getLength(), StripNailsForTest.STRIPNAIL4.getNumberInStrip());
		StripNailsFastenersInventoryItemsGateway stripNail5 = new StripNailsFastenersInventoryItemsGateway(StripNailsForTest.STRIPNAIL5.getUpc(), StripNailsForTest.STRIPNAIL5.getManufacturerID(), StripNailsForTest.STRIPNAIL5.getPrice(), StripNailsForTest.STRIPNAIL5.getLength(), StripNailsForTest.STRIPNAIL5.getNumberInStrip());
		
		
		PowerToolsToStripNailsGateway M2Mrelationship1 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION1.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION1.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship2 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION2.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION2.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship3 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION3.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION3.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship4 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION4.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION4.getStripNailID());
		PowerToolsToStripNailsGateway M2Mrelationship5 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION5.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION5.getStripNailID());
				
		System.out.println("Populated all tables");
		
	}
	
	
	

	private static void dropNailsView(DatabaseManager dbm) throws DatabaseException {
		String deleteNailView = "DROP VIEW IF EXISTS NailsView;";
		PreparedStatement deleteNailViewStmt;
		try {
			deleteNailViewStmt = dbm.getConnection(classConnection).prepareStatement(deleteNailView);
			deleteNailViewStmt.execute();

		} catch (SQLException e) {
			throw new DatabaseException("Nails View Did not drop");
		}
	}

	private static void dropStripNailsView(DatabaseManager dbm) throws DatabaseException {
		String deleteStripNailView = "DROP VIEW IF EXISTS StripNailsView;";
		PreparedStatement stmtCreateNailsViewSmt;
		try {
			stmtCreateNailsViewSmt = dbm.getConnection(classConnection).prepareStatement(deleteStripNailView);
			stmtCreateNailsViewSmt.execute();

		} catch (SQLException e) {
			throw new DatabaseException("StripNailsView not deleted");
		}
	}

	private static void dropToolsView(DatabaseManager dbm) throws DatabaseException {
		String deleteToolView = "DROP VIEW IF EXISTS ToolInventoryItems;";
		PreparedStatement deleteToolViewStmt;
		try {
			deleteToolViewStmt = dbm.getConnection(classConnection).prepareStatement(deleteToolView);
			deleteToolViewStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("ToolInventoryItems not deleted");
		}
	}
	private static void dropPowerToolInventoryItemsView(DatabaseManager dbm) throws DatabaseException {
		String deletePowerToolView = "DROP VIEW IF EXISTS PowerToolInventoryItems;";
		PreparedStatement deletePowerToolViewStmt;
		try {
			deletePowerToolViewStmt = dbm.getConnection(classConnection).prepareStatement(deletePowerToolView);
			deletePowerToolViewStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("PowerToolInventoryItems not deleted");
		}
	}

	private static void dropPowerToolsToStripNails(DatabaseManager dbm) throws DatabaseException {
		String deletePowerToolsToStripNails = "DROP TABLE IF EXISTS PowerToolsToStripNails;";
		try {
			PreparedStatement deletePowerToolsToStripNailsStmt = dbm.getConnection(classConnection).prepareStatement(deletePowerToolsToStripNails);
			deletePowerToolsToStripNailsStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("PowerToolsToStripNails not deleted");
		}

	}

	

	private static void dropInventoryItems(DatabaseManager dbm) throws DatabaseException {
		String deleteInventoryItems = "DROP TABLE IF EXISTS InventoryItems;";
		try {
			PreparedStatement deleteInventoryItemsStmt = dbm.getConnection(classConnection).prepareStatement(deleteInventoryItems);
			deleteInventoryItemsStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("InventoryItems not deleted");
		}

	}

	private static void dropFasteners(DatabaseManager dbm) throws DatabaseException {
		String deleteFasteners = "DROP TABLE IF EXISTS Fasteners;";
		try {
			PreparedStatement deleteFastenersStmt = dbm.getConnection(classConnection).prepareStatement(deleteFasteners);
			deleteFastenersStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Fasteners not deleted");
		}

	}

	private static void dropTools(DatabaseManager dbm) throws DatabaseException {
		String deleteTools = "DROP TABLE IF EXISTS Tools;";
		try {
			PreparedStatement deleteToolsStmt = dbm.getConnection(classConnection).prepareStatement(deleteTools);
			deleteToolsStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Tools not deleted");
		}

	}

	private static void dropNails(DatabaseManager dbm) throws DatabaseException {
		String deleteNails = "DROP TABLE IF EXISTS Nails;";
		try {
			PreparedStatement deleteNailsStmt = dbm.getConnection(classConnection).prepareStatement(deleteNails);
			deleteNailsStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Nails not deleted");
		}
	}

	private static void dropStripNails(DatabaseManager dbm) throws DatabaseException {
		String deleteStripNails = "DROP TABLE IF EXISTS StripNails;";
		try {
			PreparedStatement deleteStripNailsStmt = dbm.getConnection(classConnection).prepareStatement(deleteStripNails);
			deleteStripNailsStmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("StripNails not deleted");
		}
	}

	private static void dropPowerTools(DatabaseManager dbm) throws DatabaseException {
		String deletePowerTools = "DROP TABLE IF EXISTS PowerTools CASCADE ;";
		try {
			PreparedStatement deletePowerToolsStmt = dbm.getConnection(classConnection).prepareStatement(deletePowerTools);
			deletePowerToolsStmt.execute();
		} catch (SQLException e) {

			throw new DatabaseException("PowerTools not deleted");
		}
	}
	


	private static void createPowerToolsToStripNails(DatabaseManager dbm) throws DatabaseException {
		String PowerToolsToStripNails =  
				"CREATE TABLE IF NOT EXISTS PowerToolsToStripNails" +"("+
						"ID int NOT NULL AUTO_INCREMENT,\n"+
						"PowerToolID int NOT NULL,\n"+
						"StripNailID int NOT NULL,\n"+
						"FOREIGN KEY (PowerToolID) REFERENCES InventoryItems(ID)\n"+
						 "ON DELETE CASCADE\n"+
						 "ON UPDATE CASCADE,\n"+
						"FOREIGN KEY (StripNailID) REFERENCES InventoryItems(ID)\n"+
						"ON DELETE CASCADE\n" +
						"ON UPDATE CASCADE,\n"+
						"UNIQUE(PowerToolID, StripNailID),\n"
						+"PRIMARY KEY (ID)\n"+");";

		PreparedStatement PowerToolsToStripNailsStmt;
		try {
			PowerToolsToStripNailsStmt = dbm.getConnection(classConnection).prepareStatement(PowerToolsToStripNails);
			PowerToolsToStripNailsStmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			throw new DatabaseException("PowerToolsToStripNails Table was not created");
		}
		
	}

	private static void createNailsView(DatabaseManager dbm) throws DatabaseException {
		String createNailsView = 
		"CREATE OR REPLACE VIEW NailsView AS SELECT Fasteners.InventoryItemID, upc,"
			+ " manufacturerID, price, length, numberInBox FROM InventoryItems"
			+ " INNER JOIN Fasteners ON InventoryItems.ID = Fasteners.InventoryItemID"
			+ " INNER JOIN Nails ON Fasteners.ID = Nails.FastenerID;";
		PreparedStatement stmtCreateNailsView;
		try {
			stmtCreateNailsView = dbm.getConnection(classConnection).prepareStatement(createNailsView);
			stmtCreateNailsView.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("NailsView was not created");
		}
	}








	private static void createNailsTable(DatabaseManager dbm) throws DatabaseException {
		String createNailsTable = 
		"create table if not exists Nails(ID int NOT NULL AUTO_INCREMENT, "
		+"FastenerID int NOT NULL, numberInBox int,"
		+" FOREIGN KEY (FastenerID) REFERENCES Fasteners(ID)"+
		 "ON DELETE CASCADE\n"+
		 "ON UPDATE CASCADE,\n"+
		 " PRIMARY KEY (ID));";
		PreparedStatement NailsCreationStmt;
		try {
			NailsCreationStmt = dbm.getConnection(classConnection).prepareStatement(createNailsTable);
			NailsCreationStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("Nails Table was not created");
		}
	}








	private static void createStripNailView(DatabaseManager dbm) throws DatabaseException {
		//Creating StripNails view
		String createStripNailsView = "CREATE OR REPLACE VIEW StripNailsView AS SELECT Fasteners.InventoryItemID, upc,"
					+ " manufacturerID, price, length,numberInStrip  FROM InventoryItems"
					+ " INNER JOIN Fasteners ON InventoryItems.ID = Fasteners.InventoryItemID"
					+ " INNER JOIN StripNails ON Fasteners.ID = StripNails.FastenerID;\n";
		PreparedStatement stmtCreateStripNailsView;
		try {
			stmtCreateStripNailsView = dbm.getConnection(dbTables.CLASS).prepareStatement(createStripNailsView);
			stmtCreateStripNailsView.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("StripNails View was not created");
		}
	}


	private static void createStripNailTable(DatabaseManager dbm) throws DatabaseException {
		String StripNailQuery = "CREATE TABLE IF NOT EXISTS StripNails" + "("+ 
		"ID int NOT NULL AUTO_INCREMENT,\n"+
		"FastenerID int NOT NULL,\n"+
		"numberInStrip int,\n"+
		"FOREIGN KEY (FastenerID) REFERENCES Fasteners(ID)\n"+
		 "ON DELETE CASCADE\n"+
		 "ON UPDATE CASCADE,\n"+
		"PRIMARY KEY (ID)" + ");";
		PreparedStatement StripNailStmt;
		try {
			StripNailStmt = dbm.getConnection(classConnection).prepareStatement(StripNailQuery);
			StripNailStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("StripNails Table was not created");
		}
	}
	private static void createFastenerTable(DatabaseManager dbm) throws DatabaseException {
		String FastenersQuery = "CREATE TABLE IF NOT EXISTS Fasteners" + "("+ 
		"ID int NOT NULL AUTO_INCREMENT,\n"+
		"InventoryItemID int NOT NULL,\n" +
		"length double,\n"+
		"FOREIGN KEY (InventoryItemID) REFERENCES InventoryItems(ID)\n" +
		 "ON DELETE CASCADE\n"+
		 "ON UPDATE CASCADE,\n"+
		"PRIMARY KEY (ID)" + ");";
		
		PreparedStatement FastenerStmt;
		try {
			FastenerStmt = dbm.getConnection(classConnection).prepareStatement(FastenersQuery);
			FastenerStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("Fasteners Table was not created");
		}
	}

	private static void createPowerToolView(DatabaseManager dbm) throws DatabaseException {
		String PowerToolInventoryItemsViewQuery = "CREATE OR REPLACE VIEW PowerToolInventoryItems AS SELECT Tools.InventoryItemID, upc,"
					+ " manufacturerID,price,description, batteryPowered"
					+ " FROM InventoryItems"
					+ " INNER JOIN Tools ON InventoryItems.ID = Tools.InventoryItemID"
					+ " INNER JOIN PowerTools ON Tools.ID = PowerTools.ToolID;\n";
		PreparedStatement PowerToolInventoryItemsStmt;
		try {
			PowerToolInventoryItemsStmt = dbm.getConnection(classConnection).prepareStatement(PowerToolInventoryItemsViewQuery);
			PowerToolInventoryItemsStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("PowerTool View was not created");
		}
	}

	private static void createPowerToolTable(DatabaseManager dbm) throws DatabaseException {
		String powerToolQuery = "CREATE TABLE IF NOT EXISTS PowerTools" + "("+ 
		"ID int NOT NULL AUTO_INCREMENT,\n"+
		"ToolID int NOT NULL\n," +
		"batteryPowered boolean\n,"+
		"FOREIGN KEY (ToolID) REFERENCES Tools(ID)" +
		"ON DELETE CASCADE\n"+
		"ON UPDATE CASCADE,\n"+
		"PRIMARY KEY (ID) "+");";
		PreparedStatement powerToolStmt;
		try {
			powerToolStmt = dbm.getConnection(classConnection).prepareStatement(powerToolQuery);
			powerToolStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(" PowerTools Table was not created");
		}
	}

	private static void createToolsTable(DatabaseManager dbm) throws DatabaseException {
		String toolsQuery = "CREATE TABLE IF NOT EXISTS Tools" + "("+ "id int NOT NULL AUTO_INCREMENT,\n"+
												 "InventoryItemID int NOT NULL,\n" +
												 "description varchar(50) ,\n" +
												 "FOREIGN KEY (InventoryItemID) " +
												 "REFERENCES InventoryItems(ID)\n" + 
												 "ON DELETE CASCADE\n"+
												 "ON UPDATE CASCADE,\n"+
												 "PRIMARY KEY (ID)" + ");";
		PreparedStatement toolStmt;
		try {
			toolStmt = dbm.getConnection(classConnection).prepareStatement(toolsQuery);
			toolStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("Tools Table was not created");
		}
	}

	private static void createInventoryItem(DatabaseManager dbm) throws DatabaseException {
		String inventoryItemQuery = "CREATE TABLE IF NOT EXISTS InventoryItems" + "("+ 
					"ID int NOT NULL AUTO_INCREMENT,\n"+
					"upc varchar(25)\n," +
					"manufacturerID int\n," +
					" price int,\n" +
					"PRIMARY KEY (ID)" + ");";
		 PreparedStatement inventoryStmt;
		try {
			inventoryStmt = dbm.getConnection(classConnection).prepareStatement(inventoryItemQuery);
			 inventoryStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException("InventoryItems table was not created");
		}
	}
}