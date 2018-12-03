package sharedDomain;

/**
 * 
 * @author cr5603
 */
public abstract class LazyLoadAbstract 
{
	//status is GHOST until otherwise changed
	private LoadStatus status = LoadStatus.GHOST;
	private enum LoadStatus {GHOST, LOADING, LOADED};
	
	/**
	 * @return if status is GHOST or not
	 */
	public boolean isGhost() 
	{
		return status == LoadStatus.GHOST;
	}
	
	/**
	 * @return if status is LOADED or not
	 */
	public boolean isLoaded()
	{
		return status == LoadStatus.LOADED;
	}
	
	/**
	 * Marks status as LOADING if status is GHOST
	 */
	public void markLoading()
	{
		if(isGhost())
			status = LoadStatus.LOADING;
	}
	
	/**
	 * Marks status as LOADED if status is LOADING
	 */
	public void markLoaded()
	{
		if(status == LoadStatus.LOADING)
			status = LoadStatus.LOADED;
	}
}