package sharedDomain;

import java.util.ArrayList;
import exception.DatabaseException;

public class PowerToolList extends LazyLoadAbstract
{
	private int stripNailInventoryItemID;
	private ArrayList<PowerTool> list = new ArrayList<PowerTool>();
	
	/**
	 * Creates new PowerToolList
	 * @param stripNailInventoryItemID
	 */
	public PowerToolList(int stripNailInventoryItemID)
	{
		this.stripNailInventoryItemID = stripNailInventoryItemID;
	}
	
	public ArrayList<PowerTool> getPowerToolList() throws DatabaseException
	{
		//Check if LoadStatus.LOADED, if not, WAIT to return the list until it is loaded
		while(!isLoaded())
		{
			if(isGhost())
			{
				markLoading(); //locks from the same list being loaded twice
                 //since all the mappers have the same named mappers, it will find the name of that specific Class
				load();
				//changes the status to loaded
				markLoaded(); //so we don't have to load again
			}
		}
		return list;
	}
	
	/**
	 * Loads the PowerTools into the list
	 */
	public void load()
	{
		Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("StripNailMapper");
		try {
			//The type that the object takes in
			Class<?>[] parameterTypes = { Integer.TYPE };
			//Uses the method from that class in that package and then returns the object that is given back 
			StripNailAbstract nailMapper = (StripNailAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(stripNailInventoryItemID));
			list = nailMapper.getPowerTools();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Updates this list
	 * This is called when a powerTool is added to a StripNail from the StripNailMapper to make sure
	 * 		that the list is up to date
	 */
	public void update()
	{
		//if the arrayList has more than 0 elements in it, assume it has missing elements and reload
		if(list.size() > 0)
			load();
		//else, do nothing
	}
}