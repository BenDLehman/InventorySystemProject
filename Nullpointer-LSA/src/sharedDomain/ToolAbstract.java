package sharedDomain;

import java.util.ArrayList;

import exception.DatabaseException;

public abstract class ToolAbstract
{
	private Tool tool;

	public static ArrayList<Tool> findAll() throws DatabaseException
	{
		return null;
	}

	public abstract Tool getTool();
	
	protected abstract void buildTool();
	
	public abstract void persist() throws DatabaseException;

	public abstract void delete() throws DatabaseException;

	public abstract int getToolID();
}
