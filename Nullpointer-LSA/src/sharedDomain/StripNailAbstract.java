package sharedDomain;

import java.util.ArrayList;

import exception.DatabaseException;

public abstract class StripNailAbstract
{
	public static ArrayList<StripNail> findAll() throws DatabaseException
	{
		return null;
	}
	
	public abstract StripNail getStripNail();
	
	protected abstract void buildStripNail();

	public abstract void persist() throws DatabaseException;

	public abstract void delete() throws DatabaseException;
	
	public abstract ArrayList<PowerTool> getPowerTools() throws DatabaseException;

	public abstract int getStripNailID();
}
