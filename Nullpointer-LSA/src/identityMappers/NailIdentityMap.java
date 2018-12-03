package identityMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;
import exception.DatabaseException;
import sharedDomain.MapperManager;
import sharedDomain.Nail;
import sharedDomain.NailAbstract;

public class NailIdentityMap {

	private static NailIdentityMap mapInstance = null; 
	private HashMap<Integer, Nail> hashMap;
	
	//Used only for testing
	public boolean alreadyInHashMap;

	private NailIdentityMap()
	{
		if (hashMap == null)
		{
			hashMap = new HashMap<Integer, Nail>(); //reached

		}
	}
	
	public static NailIdentityMap getInstance()
	{
		if(mapInstance == null)
		{
			mapInstance = new NailIdentityMap(); //reached
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
	public Nail find(int ID) throws DatabaseException
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
				Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("NailMapper");
				Class<?>[] parameterTypes = { Integer.TYPE };
				NailAbstract nailMapper = (NailAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(ID));
				Nail nail = nailMapper.getNail();
				
				if (nail == null)
				{
					throw new DatabaseException("Nail not found");
				}

				// save the nail in the hash map for later reference
				hashMap.put(ID, nail);
				
				alreadyInHashMap = false;

				// return the nail
				return nail;
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
	public Nail create(String upc, int manufacturerID, int price, double length, int numberInBox) throws DatabaseException
	{
		Nail createdNail = null;

		try
		{
			Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("NailMapper");
			Class<?>[] parameterTypes =
			{ String.class, Integer.TYPE, Integer.TYPE, Double.TYPE, Integer.TYPE };
			NailAbstract nailMapper = (NailAbstract) mapperClass.getConstructor(parameterTypes).newInstance(
					upc, manufacturerID, price, length, numberInBox);
			createdNail = nailMapper.getNail();
			
			if (createdNail == null)
			{
				throw new DatabaseException("Creation of nail failed");
			}
			
			alreadyInHashMap = false;

			hashMap.put(nailMapper.getNailID(), nailMapper.getNail());
			return createdNail;
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
	public ArrayList<Nail> getAll() throws DatabaseException
	{
		/*
		 * Rather than check the hash map we should assume that we do not have all items
		 * loaded and just go get them. Once we have them only load them in the hash map
		 * if it's not all ready there.
		 */

		Class<?> mapperClass;
		try
		{
			mapperClass = MapperManager.getSingleton().getMapperClass("NailMapper");
			Method getAllMethod = mapperClass.getDeclaredMethod("findAll");
			Object getAllResult = getAllMethod.invoke(null);
			// I suppressed the warnings here because the compiler will make sure the type
			// returned by getAll is correct
			@SuppressWarnings("unchecked")
			ArrayList<Nail> nails = (ArrayList<Nail>) getAllResult;

			for (Nail nail : nails)
			{
				if (!hashMap.containsKey(nail.getID()))
				{
					hashMap.put(nail.getID(), nail);
				}
			}
			return nails;
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
