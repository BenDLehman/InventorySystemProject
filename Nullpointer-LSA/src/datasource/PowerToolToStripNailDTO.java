package datasource;

public class PowerToolToStripNailDTO implements InventoryItemDTO
{
	private int powerToolId;
	private int stripNailId;
	
	public PowerToolToStripNailDTO(int powerToolId, int stripNailId)
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
