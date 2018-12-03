package sharedDomain;
import java.util.ArrayList;
import exception.DatabaseException;

public abstract class PowerToolToStripNailAbstract 
{
	public abstract ArrayList<PowerToolToStripNail> findAll() throws DatabaseException;
	public abstract PowerToolToStripNail getPowerToolToStripNail();
	public abstract void persist() throws DatabaseException;
	public abstract void delete() throws DatabaseException;
	public abstract int getPowerToolID();
	public abstract int getStripNailID();
}
