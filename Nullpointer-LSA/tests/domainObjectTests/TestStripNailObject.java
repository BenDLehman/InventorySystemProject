package domainObjectTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sharedDomain.StripNail;
import sharedDomain.Tool;

class TestStripNailObject {

	StripNail sNail;
	@BeforeEach
	void makeTool()
	{
		sNail = new StripNail(1, "1", 1, 1, 1, 1);
	}
	@Test
	void testInitialization() {
		assertEquals(1, sNail.getID());
		assertEquals("1", sNail.getUpc());
		assertEquals(1, sNail.getManufacturerID());
		assertEquals(1, sNail.getPrice());
		assertEquals(1, sNail.getLength());
		assertEquals(1, sNail.getNumberInStrip());
	}
	
	@Test
	void testSetters() {
		sNail.setUpc("2");
		sNail.setManufacturerID(2);
		sNail.setPrice(2);
		sNail.setLength(2);
		sNail.setNumberInStrip(2);
		
		assertEquals(1, sNail.getID());
		assertEquals("2", sNail.getUpc());
		assertEquals(2, sNail.getManufacturerID());
		assertEquals(2, sNail.getPrice());
		assertEquals(2, sNail.getLength());
		assertEquals(2, sNail.getNumberInStrip());
	}

}
