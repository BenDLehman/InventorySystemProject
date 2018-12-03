package sharedDomain;

public class StripNail 
{
	String upc;
	int manufacturerID;
	int price;
	double length;
	int numberInStrip;
	private PowerToolList list; 
	private int ID;
	
	public StripNail(int ID, String upc, int manufacturerID, int price, double length, int numberInStrip)
	{
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.numberInStrip = numberInStrip;
		this.length = length;
		this.ID = ID;
		list = new PowerToolList(ID);
	}
	
	public int getID()
	{
		return ID;
	}
	
	public PowerToolList getPowerToolList()
	{
		return list;
	}
	
	public String getUpc() 
	{
		return upc;
	}
	
	
	public void setUpc(String upc) 
	{
		this.upc = upc;
	}
	
	public int getManufacturerID() {
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
	
	
	public double getLength() 
	{
		return length;
	}
	
	
	public void setLength(double length) 
	{
		this.length = length;
	}
	
	
	public int getNumberInStrip() 
	{
		return numberInStrip;
	}
	
	
	public void setNumberInStrip(int numberInStrip) 
	{
		this.numberInStrip = numberInStrip;
	}
	
	
}
