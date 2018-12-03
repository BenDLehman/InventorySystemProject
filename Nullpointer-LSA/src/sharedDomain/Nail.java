package sharedDomain;

public class Nail 
{
	String upc;
	int ID;
	int manufacturerID;
	int price;
	double length;
	int numInBox;
	
	public Nail(int ID, String upc, int manufacturerID, int price, double length, int numInBox) 
	{ 
		this.ID = ID;
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numInBox = numInBox;
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


	public double getLength() 
	{
		return length;
	}


	public void setLength(double length) 
	{
		this.length = length;
	}


	public int getNumberInBox() 
	{
		return numInBox;
	}


	public void setNumberInBox(int numInBox) 
	{
		this.numInBox = numInBox;
	}




	

}
