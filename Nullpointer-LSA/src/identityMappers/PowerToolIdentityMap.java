package identityMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;
import exception.DatabaseException;
import sharedDomain.MapperManager;
import sharedDomain.PowerTool;
import sharedDomain.PowerToolAbstract;

public class PowerToolIdentityMap  {

	private static PowerToolIdentityMap mapInstance = null; 
	private HashMap<Integer, PowerTool> hashMap;
	
	//Used for testing only
	public boolean alreadyInHashMap;

	private PowerToolIdentityMap()
	{
		if (hashMap == null)
		{
			hashMap = new HashMap<Integer, PowerTool>();

		}
	}
	
	public static PowerToolIdentityMap getInstance()
	{
		if(mapInstance == null)
		{
			mapInstance = new PowerToolIdentityMap();
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
	public PowerTool find(int ID) throws DatabaseException
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
				Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("PowerToolMapper");
				Class<?>[] parameterTypes = { Integer.TYPE };
				PowerToolAbstract pToolMapper = (PowerToolAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(ID));
				PowerTool pTool = pToolMapper.getPowerTool();
				
				if (pTool == null)
				{
					throw new DatabaseException("PowerTool not found");
				}

				// save the nail in the hash map for later reference
				hashMap.put(ID, pTool);
				
				alreadyInHashMap = false;

				// return the nail
				return pTool;
			} catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * method for creating a nail in the database and adding it to the hash map
	 * 
	 * @param nail
	 * @throws DatabaseUpdateException
	 */
	public PowerTool create(String upc, int manufacturerID, int price, String description, boolean isBatteryPowered) throws DatabaseException
	{
		PowerTool createdPowerTool = null;

		try
		{
			Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("PowerToolMapper");
			Class<?>[] parameterTypes =
			{ String.class, Integer.TYPE, Integer.TYPE, String.class, Boolean.TYPE };
			PowerToolAbstract pToolMapper = (PowerToolAbstract) mapperClass.getConstructor(parameterTypes).newInstance(
					upc, manufacturerID, price, description, isBatteryPowered);
			createdPowerTool = pToolMapper.getPowerTool();
			
			if (createdPowerTool == null)
			{
				throw new DatabaseException("Creation of pTool failed");
			}
			
			alreadyInHashMap = false;

			hashMap.put(pToolMapper.getPowerToolID(), pToolMapper.getPowerTool());
			return createdPowerTool;
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
	public ArrayList<PowerTool> getAll() throws DatabaseException
	{
		/*
		 * Rather than check the hash map we should assume that we do not have all items
		 * loaded and just go get them. Once we have them only load them in the hash map
		 * if it's not all ready there.
		 */

		Class<?> mapperClass;
		try
		{
			mapperClass = MapperManager.getSingleton().getMapperClass("PowerToolMapper");
			Method getAllMethod = mapperClass.getDeclaredMethod("findAll");
			Object getAllResult = getAllMethod.invoke(null);
			// I suppressed the warnings here because the compiler will make sure the type
			// returned by getAll is correct
			@SuppressWarnings("unchecked")
			ArrayList<PowerTool> pTools = (ArrayList<PowerTool>) getAllResult;

			for (PowerTool pTool : pTools)
			{
				if (!hashMap.containsKey(pTool.getID()))
				{
					hashMap.put(pTool.getID(), pTool);
				}
			}
			return pTools;
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
