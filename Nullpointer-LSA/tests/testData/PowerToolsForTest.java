package testData;


public enum PowerToolsForTest {
	
	POWERTOOL1("123", 55, 12, "I AM POWERTOOL NUMBER ONE", true),
	
	POWERTOOL2("1234", 55, 10, "I AM POWERTOOL NUMBER TWO", true),
	
	POWERTOOL3("12345", 23, 6, "I AM POWERTOOL NUMBER THREE", true),
	
	POWERTOOL4("123456", 23, 9, "I AM POWERTOOL NUMBER FOUR", true),
	
	POWERTOOL5("1234", 23, 8, "I AM POWERTOOL NUMBER FIVE", true);
	
	
	private String upc;
	private int manufacturerID;
	private int price;
	private String description;
	private boolean batteryPowered;
	

	PowerToolsForTest(String upc, int manufacturerID, int price, String description, boolean batteryPowered) {
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;
		this.batteryPowered = batteryPowered;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBatteryPowered() {
		return batteryPowered;
	}


	public void setBatteryPowered(boolean batteryPowered) {
		this.batteryPowered = batteryPowered;
	}


}
