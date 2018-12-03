package sharedDomain;

import java.util.ArrayList;
import exception.DatabaseException;

public class StripNailList extends LazyLoadAbstract
{
	private int powerToolInventoryItemID;
	private ArrayList<StripNail> list = new ArrayList<StripNail>();
	
	/**
	 * Creates a StripNailList
	 * @param powerToolInventoryItemID
	 */
	public StripNailList(int powerToolInventoryItemID)
	{
		this.powerToolInventoryItemID = powerToolInventoryItemID;
	}
	
	public ArrayList<StripNail> getStripNailList() throws DatabaseException
	{
		//Check if LoadStatus.LOADED, if not, WAIT to return the list until it is loaded
		while(!isLoaded())
		{
			if(isGhost())
			{
				markLoading(); //locks from the same list being loaded twice
				load();
				markLoaded(); //so we don't have to load again
				
				
			}
		}
		return list;
	}
	
	/**
	 * Loads the list into memory
	 */
	private void load()
	{
		Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("PowerToolMapper");
		try {
			//The type that the object takes in
			Class<?>[] parameterTypes = { Integer.TYPE };
			//Uses the method from that class in that package and then returns the object that is given back 
			PowerToolAbstract mapper = (PowerToolAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(powerToolInventoryItemID));
			list = mapper.getStripNails();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Updates this list
	 * This is called when a stripNail is added to a PowerTool from the PowerToolMapper to make sure
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