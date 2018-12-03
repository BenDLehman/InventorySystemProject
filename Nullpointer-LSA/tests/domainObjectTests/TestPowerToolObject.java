package domainObjectTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sharedDomain.PowerTool;

class TestPowerToolObject {

	PowerTool pTool;
	@BeforeEach
	void makeTool()
	{
		pTool = new PowerTool(1, "1", 1, 1, "1", true);
	}
	@Test
	void testInitialization() {
		assertEquals(1, pTool.getID());
		assertEquals("1", pTool.getUpc());
		assertEquals(1, pTool.getManufacturerID());
		assertEquals(1, pTool.getPrice());
		assertEquals("1", pTool.getDescription());
	}
	
	@Test
	void testSetters() {
		pTool.setUpc("2");
		pTool.setManufacturerID(2);
		pTool.setPrice(2);
		pTool.setDescription("2");
		pTool.setBatteryPowered(false);
		
		assertEquals(1, pTool.getID());
		assertEquals("2", pTool.getUpc());
		assertEquals(2, pTool.getManufacturerID());
		assertEquals(2, pTool.getPrice());
		assertEquals("2", pTool.getDescription());
		assertEquals(false, pTool.isBatteryPowered());
	
	}

}
