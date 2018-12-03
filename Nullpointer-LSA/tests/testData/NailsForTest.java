package testData;


public enum NailsForTest {
	
	NAIL1("987", 23, 15, 4, 50),
	
	NAIL2("9876", 23, 15, 4, 50),
	
	NAIL3("98765", 55, 15, 5, 50),
	
	NAIL4("987654", 55, 20, 5, 25),
	
	NAIL5("9876", 67, 20, 6, 25);
	
	private String upc;
	private int manufacturerID;
	private int price;
	private double length;
	private int numberInBox;
	

	NailsForTest(String upc, int manufacturerID, int price, double length, int numberInBox) 
	{
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInBox = numberInBox;
	}
	

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public double getLength() {
		return length;
	}


	public void setLength(double length) {
		this.length = length;
	}


	public int getNumberInBox() {
		return numberInBox;
	}


	public void setNumberInBox(int numberInBox) {
		this.numberInBox = numberInBox;
	}

	

}

