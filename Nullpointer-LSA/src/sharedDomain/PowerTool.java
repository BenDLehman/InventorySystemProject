package sharedDomain;


public class PowerTool  
{
	
	private String upc;
	private int manufacturerID;
	private int price;
	private String description; 
	boolean isBatteryPowered;
	private int ID;
	private StripNailList list;

	public PowerTool(int ID, String upc, int manufacturerID, int price, String description, boolean isBatteryPowered)
	{
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
		this.isBatteryPowered = isBatteryPowered;
		this.ID = ID;
		list = new StripNailList(ID);
	}
	
	public int getID()
	{
		return ID;
	}
	
	public StripNailList getStripNailList()
	{
		return list;
	}
	
	public String getUpc() {
		return upc;
	}
	
	public void setUpc(String upc) 
	{
		this.upc = upc;
	}
	
	public int getManufacturerID() {
		return manufacturerID;
	}
	
	public void setManufacturerID(int manufacturerID ) 
	{
		this.manufacturerID = manufacturerID ;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) 
	{
		this.price = price;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public boolean isBatteryPowered() 
	{
		return isBatteryPowered;
	}
	
	public void setBatteryPowered(boolean isBatteryPowered) 
	{
		this.isBatteryPowered = isBatteryPowered;
	}
}
