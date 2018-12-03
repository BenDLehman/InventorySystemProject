package datasourceConcrete;

import java.sql.PreparedStatement;
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

public class ConcreteTableHelper {
	
	static String[] tableNames = {"PowerToolsToStripNails", "StripNails", "Nails", "PowerTools", "Tools", "LastID"};
	static String[] createStatements = {"create table LastID(ID int NOT NULL, UNIQUE(ID), PRIMARY KEY (ID));", 
			
			"create table Tools(ID int NOT NULL, upc varchar(25) NOT NULL, manufacturerID int NOT NULL, price int, "
			+ "description varchar(50), FOREIGN KEY (ID) REFERENCES LastID(ID), UNIQUE(ID));", 
			
			"create table PowerTools(ID int NOT NULL, upc varchar(25) NOT NULL, manufacturerID int NOT NULL, price int, "
			+ "description varchar(50), batteryPowered boolean, FOREIGN KEY (ID) REFERENCES LastID(ID), UNIQUE(ID));", 
			
			"create table Nails(ID int NOT NULL, upc varchar(25) NOT NULL, manufacturerID int NOT NULL, price int, length double, "
			+ "numberInBox int, FOREIGN KEY (ID) REFERENCES LastID(ID), UNIQUE(ID, upc));", 
			
			"create table StripNails(ID int NOT NULL, upc varchar(25) NOT NULL, manufacturerID int NOT NULL, price int, length double,"
			+ " numberInStrip int, FOREIGN KEY (ID) REFERENCES LastID(ID), UNIQUE(ID));", 
			
			"create table PowerToolsToStripNails(ID int NOT NULL AUTO_INCREMENT, powerToolID int NOT NULL, stripNailID int NOT NULL, "
			+ "PRIMARY KEY(ID), UNIQUE (PowerToolID, StripNailID));"};

	public static void main(String[] args) throws DatabaseException {
		dropAllTables();
		createTables();
		populateTables();
	}
	
	public static void dropAllTables() throws DatabaseException 
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		
		String query = "";
		
		for(int i=0; i<tableNames.length; i++) 
		{
			query = "drop table "+ tableNames[i];
			
			PreparedStatement stmnt;
			try {
				stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
				stmnt.execute();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Dropped all tables");
		
	}
	
	//Create tables	
	public static void createTables() throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();


		String query = "";

		for(int i = 0; i<createStatements.length; i++)
		{
			query = createStatements[i];
			
			PreparedStatement stmnt;
			try {
				stmnt = dbm.getConnection(dbTables.CONCRETE).prepareStatement(query);
				stmnt.execute();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		System.out.println("Created all tables");
				
	}
	
	//Create tables	
	@SuppressWarnings("unused")
	public static void populateTables() throws DatabaseException 
		{		

			ToolsGateway tool1 = new ToolsGateway(ToolsForTest.TOOL1.getUpc(), ToolsForTest.TOOL1.getManufacturerID(), ToolsForTest.TOOL1.getPrice(), ToolsForTest.TOOL1.getDescription());
			ToolsGateway tool2 = new ToolsGateway(ToolsForTest.TOOL2.getUpc(), ToolsForTest.TOOL2.getManufacturerID(), ToolsForTest.TOOL2.getPrice(), ToolsForTest.TOOL2.getDescription());
			ToolsGateway tool3 = new ToolsGateway(ToolsForTest.TOOL3.getUpc(), ToolsForTest.TOOL3.getManufacturerID(), ToolsForTest.TOOL3.getPrice(), ToolsForTest.TOOL3.getDescription());
			ToolsGateway tool4 = new ToolsGateway(ToolsForTest.TOOL4.getUpc(), ToolsForTest.TOOL4.getManufacturerID(), ToolsForTest.TOOL4.getPrice(), ToolsForTest.TOOL4.getDescription());
			ToolsGateway tool5 = new ToolsGateway(ToolsForTest.TOOL5.getUpc(), ToolsForTest.TOOL5.getManufacturerID(), ToolsForTest.TOOL5.getPrice(), ToolsForTest.TOOL5.getDescription());
			
			PowerToolsGateway powerTool1 = new PowerToolsGateway(PowerToolsForTest.POWERTOOL1.getUpc(),PowerToolsForTest.POWERTOOL1.getManufacturerID(), PowerToolsForTest.POWERTOOL1.getPrice(), PowerToolsForTest.POWERTOOL1.getDescription(), PowerToolsForTest.POWERTOOL1.isBatteryPowered());
			PowerToolsGateway powerTool2 = new PowerToolsGateway(PowerToolsForTest.POWERTOOL2.getUpc(),PowerToolsForTest.POWERTOOL2.getManufacturerID(), PowerToolsForTest.POWERTOOL2.getPrice(), PowerToolsForTest.POWERTOOL2.getDescription(), PowerToolsForTest.POWERTOOL2.isBatteryPowered());
			PowerToolsGateway powerTool3 = new PowerToolsGateway(PowerToolsForTest.POWERTOOL3.getUpc(),PowerToolsForTest.POWERTOOL3.getManufacturerID(), PowerToolsForTest.POWERTOOL3.getPrice(), PowerToolsForTest.POWERTOOL3.getDescription(), PowerToolsForTest.POWERTOOL3.isBatteryPowered());
			PowerToolsGateway powerTool4 = new PowerToolsGateway(PowerToolsForTest.POWERTOOL4.getUpc(),PowerToolsForTest.POWERTOOL4.getManufacturerID(), PowerToolsForTest.POWERTOOL4.getPrice(), PowerToolsForTest.POWERTOOL4.getDescription(), PowerToolsForTest.POWERTOOL4.isBatteryPowered());
			PowerToolsGateway powerTool5 = new PowerToolsGateway(PowerToolsForTest.POWERTOOL5.getUpc(),PowerToolsForTest.POWERTOOL5.getManufacturerID(), PowerToolsForTest.POWERTOOL5.getPrice(), PowerToolsForTest.POWERTOOL5.getDescription(), PowerToolsForTest.POWERTOOL5.isBatteryPowered());
			
			NailsGateway nail1 = new NailsGateway(NailsForTest.NAIL1.getUpc(), NailsForTest.NAIL1.getManufacturerID(), NailsForTest.NAIL1.getPrice(), NailsForTest.NAIL1.getLength(), NailsForTest.NAIL1.getNumberInBox());
			NailsGateway nail2 = new NailsGateway(NailsForTest.NAIL2.getUpc(), NailsForTest.NAIL2.getManufacturerID(), NailsForTest.NAIL2.getPrice(), NailsForTest.NAIL2.getLength(), NailsForTest.NAIL2.getNumberInBox());
			NailsGateway nail3 = new NailsGateway(NailsForTest.NAIL3.getUpc(), NailsForTest.NAIL3.getManufacturerID(), NailsForTest.NAIL3.getPrice(), NailsForTest.NAIL3.getLength(), NailsForTest.NAIL3.getNumberInBox());
			NailsGateway nail4 = new NailsGateway(NailsForTest.NAIL4.getUpc(), NailsForTest.NAIL4.getManufacturerID(), NailsForTest.NAIL4.getPrice(), NailsForTest.NAIL4.getLength(), NailsForTest.NAIL4.getNumberInBox());
			NailsGateway nail5 = new NailsGateway(NailsForTest.NAIL5.getUpc(), NailsForTest.NAIL5.getManufacturerID(), NailsForTest.NAIL5.getPrice(), NailsForTest.NAIL5.getLength(), NailsForTest.NAIL5.getNumberInBox());
			
			StripNailsGateway stripNail1 = new StripNailsGateway(StripNailsForTest.STRIPNAIL1.getUpc(), StripNailsForTest.STRIPNAIL1.getManufacturerID(), StripNailsForTest.STRIPNAIL1.getPrice(), StripNailsForTest.STRIPNAIL1.getLength(), StripNailsForTest.STRIPNAIL1.getNumberInStrip());
			StripNailsGateway stripNail2 = new StripNailsGateway(StripNailsForTest.STRIPNAIL2.getUpc(), StripNailsForTest.STRIPNAIL2.getManufacturerID(), StripNailsForTest.STRIPNAIL2.getPrice(), StripNailsForTest.STRIPNAIL2.getLength(), StripNailsForTest.STRIPNAIL2.getNumberInStrip());
			StripNailsGateway stripNail3 = new StripNailsGateway(StripNailsForTest.STRIPNAIL3.getUpc(), StripNailsForTest.STRIPNAIL3.getManufacturerID(), StripNailsForTest.STRIPNAIL3.getPrice(), StripNailsForTest.STRIPNAIL3.getLength(), StripNailsForTest.STRIPNAIL3.getNumberInStrip());
			StripNailsGateway stripNail4 = new StripNailsGateway(StripNailsForTest.STRIPNAIL4.getUpc(), StripNailsForTest.STRIPNAIL4.getManufacturerID(), StripNailsForTest.STRIPNAIL4.getPrice(), StripNailsForTest.STRIPNAIL4.getLength(), StripNailsForTest.STRIPNAIL4.getNumberInStrip());
			StripNailsGateway stripNail5 = new StripNailsGateway(StripNailsForTest.STRIPNAIL5.getUpc(), StripNailsForTest.STRIPNAIL5.getManufacturerID(), StripNailsForTest.STRIPNAIL5.getPrice(), StripNailsForTest.STRIPNAIL5.getLength(), StripNailsForTest.STRIPNAIL5.getNumberInStrip());
			
			PowerToolsToStripNailsGateway M2Mrelationship1 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION1.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION1.getStripNailID());
			PowerToolsToStripNailsGateway M2Mrelationship2 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION2.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION2.getStripNailID());
			PowerToolsToStripNailsGateway M2Mrelationship3 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION3.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION3.getStripNailID());
			PowerToolsToStripNailsGateway M2Mrelationship4 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION4.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION4.getStripNailID());
			PowerToolsToStripNailsGateway M2Mrelationship5 = new PowerToolsToStripNailsGateway(PowerToolsToStripNailsForTest.RELATION5.getPowerToolID(),PowerToolsToStripNailsForTest.RELATION5.getStripNailID());
			
			System.out.println("Populated all tables");
			
		}

}
