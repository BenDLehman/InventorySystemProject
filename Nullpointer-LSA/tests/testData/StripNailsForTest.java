package testData;


public enum StripNailsForTest {
	
	STRIPNAIL1("987", 23, 20, 4, 20),
	
	STRIPNAIL2("9999", 23, 25, 4, 30),
	
	STRIPNAIL3("98769", 55, 15, 5, 18),
	
	STRIPNAIL4("987123", 55, 5, 5, 8),
	
	STRIPNAIL5("9999", 67, 17, 6, 15);
	

	private String upc;
	private int manufacturerID;
	private int price;
	private double length;
	private int numberInStrip;
	

	StripNailsForTest(String upc, int manufacturerID, int price, double length, int numberInStrip) 
	{
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.length = length;
		this.numberInStrip = numberInStrip;
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
	
	public int getNumberInStrip() {
		return numberInStrip;
	}

	public void setNumberInStrip(int numberInStrip) {
		this.numberInStrip = numberInStrip;
	}


}


