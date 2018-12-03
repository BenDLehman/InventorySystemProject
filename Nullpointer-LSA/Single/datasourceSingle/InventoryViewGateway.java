package datasourceSingle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DatabaseManager.DatabaseManager;
import DatabaseManager.DatabaseManager.dbTables;
import datasource.StripNailDTO;
import datasource.PowerToolDTO;

import exception.DatabaseException;

/**
 * 
 * @author Chris Roadcap
 * @author Kanza Amin
 *
 *         Test Class which tests the functionality of the View and many to many
 *         relationship between StripNails and PoweTools There are two methods:
 *         selectStripNailsForPT and selectPowerToolsForSN
 */

public class InventoryViewGateway
{

	/*
	 * FIND STRIPNAILS FOR ONE POWERTOOL
	 * 
	 * This function passes a query and prints out a result set that has the
	 * associated StripNails for 1 Powertool ID
	 */
	public static ArrayList<StripNailDTO> findStripNailsbyPowerToolID(int powerToolID) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();

		String query = "Select stripNailId, UPC, manID, price, length, numberInStrip, type "
				+ "From InventoryView where powerToolID = ?";
		ArrayList<StripNailDTO> resultList = new ArrayList<StripNailDTO>();
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setInt(1, powerToolID);
			ResultSet result = stmnt.executeQuery();

			int id;
			String upc;
			int manufacturerID;
			int price;
			double length;
			int numInStrip;
			int type;

			while (result.next())
			{
				id = result.getInt("stripNailID");
				upc = result.getString("UPC");
				manufacturerID = result.getInt("manID");
				price = result.getInt("price");
				length = result.getDouble("length");
				numInStrip = result.getInt("numberInStrip");
				type = result.getInt("type");

				if (type == 4)
				{
					StripNailDTO dto = new StripNailDTO(id, upc, manufacturerID, price, length, numInStrip);
					resultList.add(dto);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return resultList;
	}

	/*
	 * FIND POWERTOOLS FOR ONE STRIPNAIL
	 * 
	 * This function passes a query and prints out a result set that has the
	 * associated Powertools for 1 Stripnail ID
	 */
	public static ArrayList<PowerToolDTO> findPowerToolsbyStripNailID(int stripNailID) throws DatabaseException
	{
		DatabaseManager dbm = DatabaseManager.getDatabaseManager();
		String query = "Select ID, UPC, manID, price, description, batteryPowered, powerToolID, type "
				+ "From InventoryView where stripNailID = ?";
		ArrayList<PowerToolDTO> resultList = new ArrayList<PowerToolDTO>();
		try
		{
			PreparedStatement stmnt = dbm.getConnection(dbTables.SINGLE).prepareStatement(query);
			stmnt.setInt(1, stripNailID);
			ResultSet result = stmnt.executeQuery();

			int id;
			String upc;
			int manufacturerID;
			int price;
			String description;
			boolean batteryPowered;
			int type;

			if (!result.next())
			{
				System.out.println("DOBIS");
				throw new Exception();
			}
			while (result.next())
			{
				id = result.getInt("powerToolID");
				upc = result.getString("UPC");
				manufacturerID = result.getInt("manID");
				price = result.getInt("price");
				description = result.getString("description");
				batteryPowered = result.getBoolean("batteryPowered");
				type = result.getInt("type");

				if (type == 2)
				{
					PowerToolDTO dto = new PowerToolDTO(id, upc, manufacturerID, price, description, batteryPowered);
					resultList.add(dto);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultList;
	}

}