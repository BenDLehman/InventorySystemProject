package testData;

public enum ToolsForTest {
	
	TOOL1("123", 55, 12, "I AM TOOL NUMBER ONE"),
	
	TOOL2("1234", 55, 10, "I AM TOOL NUMBER TWO"),
	
	TOOL3("12345", 23, 6, "I AM TOOL NUMBER THREE"),
	
	TOOL4("123456", 23, 9, "I AM TOOL NUMBER FOUR"),
	
	TOOL5("1234", 23, 8, "I AM TOOL NUMBER FIVE");
	

	private String upc;
	private int manufacturerID;
	private int price;
	private String description;

	ToolsForTest(String upc, int manufacturerID, int price, String description) {
		this.upc = upc;
		this.manufacturerID = manufacturerID;
		this.price = price;
		this.description = description;

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

}
