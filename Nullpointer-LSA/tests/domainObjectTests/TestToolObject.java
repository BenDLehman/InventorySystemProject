package domainObjectTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sharedDomain.Tool;

class TestToolObject {

	Tool tool;
	@BeforeEach
	void makeTool()
	{
		tool = new Tool(1, "1", 1, 1, "1");
	}
	@Test
	void testInitialization() {
		assertEquals(1, tool.getID());
		assertEquals("1", tool.getUpc());
		assertEquals(1, tool.getManufacturerID());
		assertEquals(1, tool.getPrice());
		assertEquals("1", tool.getDescription());
	}
	
	@Test
	void testSetters() {
		tool.setUpc("2");
		tool.setManufacturerID(2);
		tool.setPrice(2);
		tool.setDescription("2");
		
		assertEquals(1, tool.getID());
		assertEquals("2", tool.getUpc());
		assertEquals(2, tool.getManufacturerID());
		assertEquals(2, tool.getPrice());
		assertEquals("2", tool.getDescription());
	
	}

}
