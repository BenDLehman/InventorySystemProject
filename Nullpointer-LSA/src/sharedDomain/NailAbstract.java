package sharedDomain;

import java.util.ArrayList;

import exception.DatabaseException;

public abstract class NailAbstract 
{
	public static ArrayList<Nail> findAll() throws DatabaseException
	{
		return null;
	}
	public abstract Nail getNail();
	
	protected abstract void buildNail();

	public abstract void persist() throws DatabaseException;

	public abstract void delete() throws DatabaseException;
	
	public abstract int getNailID();

}
