package sharedDomain;
/**
 * this class makes sure that we are all doing the functionalities
 * @author merlin, nahesha
 *
 */
public class MapperManager {
	/**
	 * A list of all of the packages that contain our mapper implementations
	 */
	public static String[] MAPPER_PACKAGES = {"mappersConcrete","mappersSingle","mappersClass"};
	
	private static MapperManager singleton;

	private String mapperPackageName;
	
	/**
	 * @return the one of these that we can have
	 */
	public static MapperManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new MapperManager();
		}
		return singleton;
	}

	/**
	 * Specify the name of the package of mappers we should be using
	 * @param mapperPackage the name of the package
	 */
	public void setMapperPackageName(String mapperPackage)
	{
		this.mapperPackageName = mapperPackage;
	}
	
	/**
	 * Get the class object for a particular mapper in the package we are currently using
	 * @param mapperName the name of the mapper we want
	 * @return the class object
	 */
	public Class<?> getMapperClass(String mapperName)
	{
		Class<?> mapperClass = null;
		try
		{
			mapperClass = Class.forName(mapperPackageName + "." + mapperName);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return mapperClass;
	}

}
