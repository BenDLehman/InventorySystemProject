package datasource;


public class StripNailDTO implements InventoryItemDTO
{
	// The id of a StripNail
	private int id;
	// The Upc of a stripNail
	private String upc;
	// The manufacturer id of a StripNail
	private int manId;
	// The price of the StripNail
	private int price;
	// The length of the StripNail
	private double length;
	// Number of nails in the strip 
	private int numInStrip;
	
	private int type = 4;
	
	
	
	public StripNailDTO(int id, String upc, int manId, int price, double length, int numInStrip) {
		super();
		this.id = id;
		this.upc = upc;
		this.manId = manId;
		this.price = price;
		this.length = length;
		this.numInStrip = numInStrip;
	}

	/**
	 * Get the id of a StripNail
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 *  Get the length of the StripNail
	 * 
	 * @return length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Get the manufacturer id of the StripNail 
	 * 
	 * @return manufacturer id
	 */
	public int getManufacturerID() {
		return manId;
	}
	
	/**
	 * Get the number of nails in the strip 
	 * 
	 * @return numInStrip
	 */
	public int getNumberInStrip() {
		return numInStrip;
	}
	
	/**
	 * Get the price of the Strip Nail
	 * 
	 * @return price 
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Get the upc of the StripNail 
	 * 
	 * @return upc
	 */
	public String getUpc() {
		return upc;
	}

	public int getType() {
		return this.type;
	}
	
}
