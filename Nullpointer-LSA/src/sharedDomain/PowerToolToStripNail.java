package sharedDomain;

public class PowerToolToStripNail 
{
	private int powerToolId;
	private int stripNailId;
	
	public PowerToolToStripNail(int powerToolId, int stripNailId)
	{
		this.powerToolId = powerToolId;
		this.stripNailId = stripNailId;
	}
	
	public int getPowerToolId()
	{
		return powerToolId;
	}
	
	public int getStripNailId()
	{
		return stripNailId;
	}
}
