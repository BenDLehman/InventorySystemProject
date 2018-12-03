package datasource;

public class PowerToolDTO  implements InventoryItemDTO
{
	// the id of a PowerTool
	private int id;
	// the upc of a PowerTool
	private String upc;
	// the Manufacturer ID a PowerTool
	private int manId;
	// the price of a PowerTool 
	private int price;
	// the description of a PowerTool 
	private String desc;
	// Whether or not the PowerTool is battery powered 
	private boolean batteryPowered;
	
	private int type = 2;
	
	public PowerToolDTO(int id, String upc, int manId, int price, String desc, boolean batteryPowered) {
		//super();
		this.id = id;
		this.upc = upc;
		this.manId = manId;
		this.price = price;
		this.desc = desc;
		this.batteryPowered = batteryPowered;
	}	
	
	/**
	 * Get id of a PowerTool 
	 * 
	 * @return id 
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Get the upc of a PowerTool 
	 * 
	 * @return id 
	 */
	public String getUpc() {
		return upc;
	}
	
	
	/**
	 * Get manufacturer ID for a PowerTool  
	 *
	 * @return manufacturerID
	 */
	public int getManufacturerID() {
		return manId;
	}
	
	
	/**
	 * Get the price of a PowerTool 
	 * 
	 * @return price 
	 */
	public int getPrice() {
		return price;
	}
	
	
	/**
	 * Get the description of a PowerTool 
	 * 
	 * @return description 
	 */
	public String getDescription() {
		return desc;
	}
	
	
	/**
	 * Returns true if PowerTool is batterypowered false if not 
	 * 
	 * @return batterypowered
	 */
	public boolean isBatteryPowered() {
		return batteryPowered;
	}

	public int getType() {
		return this.type;
	}
	

}

