package testData;


public enum PowerToolsToStripNailsForTest {
	
	RELATION1(6,16),
	
	RELATION2(7,16),
	
	RELATION3(8,17),
	
	RELATION4(8,18),
	
	RELATION5(9,20);

	

	int powerToolID;
	int stripNailID;
	

	PowerToolsToStripNailsForTest(int powerToolID, int stripNailID) 
	{
		this.powerToolID = powerToolID;
		this.stripNailID = stripNailID;
	}


	public int getPowerToolID() {
		return powerToolID;
	}


	public void setPowerToolID(int powerToolID) {
		this.powerToolID = powerToolID;
	}


	public int getStripNailID() {
		return stripNailID;
	}


	public void setStripNailID(int stripNailID) {
		this.stripNailID = stripNailID;
	}

}



