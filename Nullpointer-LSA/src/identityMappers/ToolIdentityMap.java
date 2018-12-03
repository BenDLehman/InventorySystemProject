package identityMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;
import exception.DatabaseException;
import sharedDomain.MapperManager;
import sharedDomain.Tool;
import sharedDomain.ToolAbstract;

public class ToolIdentityMap  {

	private static ToolIdentityMap mapInstance = null; 
	private HashMap<Integer, Tool> hashMap;
	
	public boolean alreadyInHashMap;

	private ToolIdentityMap()
	{
		if (hashMap == null)
		{
			hashMap = new HashMap<Integer, Tool>();

		}
	}
	
	public static ToolIdentityMap getInstance()
	{
		if(mapInstance == null)
		{
			mapInstance = new ToolIdentityMap();
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
	public Tool find(int ID) throws DatabaseException
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
				Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("ToolMapper");
				Class<?>[] parameterTypes = { Integer.TYPE };
				ToolAbstract toolMapper = (ToolAbstract) mapperClass.cast(mapperClass.getConstructor(parameterTypes).newInstance(ID));
				Tool tool = toolMapper.getTool();
				
				if (tool == null)
				{
					throw new DatabaseException("Tool not found");
				}

				// save the nail in the hash map for later reference
				hashMap.put(ID, tool);
				
				alreadyInHashMap = false;

				// return the nail
				return tool;
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
	public Tool create(String upc, int manufacturerID, int price, String description) throws DatabaseException
	{
		Tool createdTool = null;

		try
		{
			Class<?> mapperClass = MapperManager.getSingleton().getMapperClass("ToolMapper");
			Class<?>[] parameterTypes =
			{ String.class, Integer.TYPE, Integer.TYPE, String.class };
			ToolAbstract toolMapper = (ToolAbstract) mapperClass.getConstructor(parameterTypes).newInstance(
					upc, manufacturerID, price, description);
			createdTool = toolMapper.getTool();
			
			if (createdTool == null)
			{
				throw new DatabaseException("Creation of tool failed");
			}
			
			alreadyInHashMap = false;

			hashMap.put(toolMapper.getToolID(), toolMapper.getTool());
			return createdTool;
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
	public ArrayList<Tool> getAll() throws DatabaseException
	{
		/*
		 * Rather than check the hash map we should assume that we do not have all items
		 * loaded and just go get them. Once we have them only load them in the hash map
		 * if it's not all ready there.
		 */

		Class<?> mapperClass;
		try
		{
			mapperClass = MapperManager.getSingleton().getMapperClass("ToolMapper");
			Method getAllMethod = mapperClass.getDeclaredMethod("findAll");
			Object getAllResult = getAllMethod.invoke(null);
			// I suppressed the warnings here because the compiler will make sure the type
			// returned by getAll is correct
			@SuppressWarnings("unchecked")
			ArrayList<Tool> tools = (ArrayList<Tool>) getAllResult;

			for (Tool tool : tools)
			{
				if (!hashMap.containsKey(tool.getID()))
				{
					hashMap.put(tool.getID(), tool);
				}
			}
			return tools;
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
