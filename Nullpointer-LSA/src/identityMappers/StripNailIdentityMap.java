package identityMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;
import exception.DatabaseException;
import sharedDomain.MapperManager;
import sharedDomain.StripNail;
import sharedDomain.StripNailAbstract;

public class StripNailIdentityMap {

	private static StripNailIdentityMap mapInstance = null; 
	private HashMap<Integer, StripNail> hashMap;
	
	//Used for testing only
	public boolean alreadyInHashMap;

	private StripNailIdentityMap()
	{
		if (hashMap == null)
		{
			hashMap = new HashMap<Integer, StripNail>();

		}
	}
	
	public static StripNailIdentityMap getInstance()
	{
		if(mapInstance == null)
		{
			mapInstance = new StripNailIdentityMap();
		}
	 return mapInstance;
		
	}
	
	
	/**
	 * method to get the nail by it's id
	 * 
	 * @param ID
	 *            id of we want to find
	 * @return nail
	 * @throws DatabaseQueryException
	 */
	public StripNail find(int ID) throws DatabaseException
	{
		//if hashmap contains the object already then return it
		if (hashMap.containsKey(ID))
		{
			alreadyInHashMap = true;
			return hashMap.get(ID);
		}
		
		// otherwise get relevant mapper and use gateway to get nail
		else
		{
			try
			{
				Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("StripNailMapper");
				Class<?>[] parameterTypes = { Integer.TYPE };
				StripNailAbstract stripNailMapper = (StripNailAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(ID));
				StripNail stripNail = stripNailMapper.getStripNail();
				
				if (stripNail == null)
				{
					throw new DatabaseException("StripNail not found");
				}

				// save the strip nail in the hash map for later reference
				hashMap.put(ID, stripNail);
				
				alreadyInHashMap = false;

				// return the nail
				return stripNail;
			} catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * method for creating a strip nail in the database and adding it to the hash map
	 * 
	 * @param nail
	 * @throws DatabaseUpdateException
	 */
	public StripNail create(String upc, int manufacturerID, int price, double length, int numberInStrip) throws DatabaseException
	{
		StripNail createdStripNail = null;

		try
		{
			Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("StripNailMapper");
			Class<?>[] parameterTypes =
			{ String.class, Integer.TYPE, Integer.TYPE, Double.TYPE, Integer.TYPE };
			StripNailAbstract stripNailMapper = (StripNailAbstract) mapperClass.getConstructor(parameterTypes).newInstance(
					upc, manufacturerID, price, length, numberInStrip);
			createdStripNail = stripNailMapper.getStripNail();
			
			if (createdStripNail == null)
			{
				throw new DatabaseException("Creation of StripNail failed");
			}
			
			alreadyInHashMap = false;

			hashMap.put(stripNailMapper.getStripNailID(), stripNailMapper.getStripNail());
			return createdStripNail;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	 
	

	/**
	 * method to get all nails from the gateway (database)
	 * 
	 * @return
	 * @throws DatabaseQueryException
	 */
	public ArrayList<StripNail> getAll() throws DatabaseException
	{
		/*
		 * Rather than check the hash map we should assume that we do not have all items
		 * loaded and just go get them. Once we have them only load them in the hash map
		 * if it's not all ready there.
		 */

		Class<?> mapperClass;
		try
		{
			mapperClass = MapperManager.getSingleton().getMapperClass("StripNailMapper");
			Method getAllMethod = mapperClass.getDeclaredMethod("findAll");
			Object getAllResult = getAllMethod.invoke(null);
			// I suppressed the warnings here because the compiler will make sure the type
			// returned by getAll is correct
			@SuppressWarnings("unchecked")
			ArrayList<StripNail> stripNails = (ArrayList<StripNail>) getAllResult;

			for (StripNail stripNail : stripNails)
			{
				if (!hashMap.containsKey(stripNail.getID()))
				{
					hashMap.put(stripNail.getID(), stripNail);
				}
			}
			return stripNails;
		}  catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public void clearHashMap() {
		hashMap.clear();		
	}
	
			
}
