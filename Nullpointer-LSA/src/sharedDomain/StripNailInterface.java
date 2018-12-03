package sharedDomain;

import java.util.ArrayList;

import exception.DatabaseException;

public interface StripNailInterface {
	public abstract ArrayList<StripNail> findAll() throws DatabaseException;
	
	public abstract StripNail getStripNail();

	public abstract void persist() throws DatabaseException;

	public abstract void delete() throws DatabaseException;
	
	public abstract ArrayList<PowerTool> getPowerTools() throws DatabaseException;

	public abstract int getStripNailID();
}
