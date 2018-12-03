package sharedDomain;

public class Tool {

	private int ID;
	private String upc;
	private int manufacturerID;
	private int price;
	private String description; 
	 
	public Tool(int ID, String upc, int manufacturerID, int price, String description)
	{
		this.ID = ID;
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
	}
	
	public int getID() 
	{
		return ID;
	}
	
	public String getUpc() 
	{
		return upc;
	}

	public void setUpc(String upc) 
	{
		this.upc = upc;
	}

	public int getManufacturerID() 
	{
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) 
	{
		this.manufacturerID = manufacturerID;
	}

	public int getPrice() 
	{
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

}
