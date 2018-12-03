package domainObjectTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sharedDomain.Nail;
import sharedDomain.Tool;

class TestNailObject {

	Nail nail;
	@BeforeEach
	void makeTool()
	{
		nail = new Nail(1, "1", 1, 1, 1, 1);
	}
	@Test
	void testInitialization() {
		assertEquals(1, nail.getID());
		assertEquals("1", nail.getUpc());
		assertEquals(1, nail.getManufacturerID());
		assertEquals(1, nail.getPrice());
		assertEquals(1, nail.getLength());
		assertEquals(1, nail.getNumberInBox());
		
	}
	
	@Test
	void testSetters() {
		nail.setUpc("2");
		nail.setManufacturerID(2);
		nail.setPrice(2);
		nail.setLength(2);
		nail.setNumberInBox(2);
		
		assertEquals(1, nail.getID());
		assertEquals("2", nail.getUpc());
		assertEquals(2, nail.getManufacturerID());
		assertEquals(2, nail.getPrice());
		assertEquals(2, nail.getLength());
		assertEquals(2, nail.getNumberInBox());
	
	}

}
