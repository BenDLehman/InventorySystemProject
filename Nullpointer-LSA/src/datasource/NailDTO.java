package datasource;

public class NailDTO implements InventoryItemDTO
{
	private int numberInBox;
	private int id;
	private String upc;
	private int manufacturerID;
	private int price;
	private double length;
	private int type =3;
	
	public NailDTO(int id, String upc, int manufacturerID, int price, double length, int numberInBox)
	{
		this.id = id;
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInBox = numberInBox;
	}
	
	public int getNumberInBox()
	{
		return numberInBox;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getUpc()
	{
		return upc;
	}
	
	public int getManufacturerID()
	{
		return manufacturerID;
	}
	
	public int getPrice()
	{
		return price;
	}

	public double getLength()
	{
		return length;
	}

	public int getType() {
		return this.type;
	}
}
