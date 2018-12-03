package sharedDomain;

import java.util.ArrayList;

import exception.DatabaseException;

public abstract class PowerToolAbstract
{
	public static ArrayList<PowerTool> findAll() throws DatabaseException
	{
		return null;
	}
	
	public abstract PowerTool getPowerTool();
	
	protected abstract void buildPowerTool();

	public abstract void persist() throws DatabaseException;

	public abstract void delete() throws DatabaseException;

	public abstract ArrayList<StripNail> getStripNails() throws DatabaseException;

	public abstract int getPowerToolID();
}
