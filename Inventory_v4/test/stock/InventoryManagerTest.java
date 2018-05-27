package stock;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryManagerTest {
	InventoryManager manager;
	@BeforeEach
	void setUp() throws Exception {
		manager = new InventoryManager();
		manager.readItemProperties("item_properties.csv");
	}

	@Test
	void testReadItemProperties() {
		HashMap<String, Item> items = manager.getItems();
		assertEquals(items.size(), 24);
	}
	
	@Test
	void testGetItem() {
		assertNotNull(manager.getItem("RICE"));
		assertNotNull(manager.getItem("rice"));
		assertNull(manager.getItem("no RICE"));
	}
	
	@Test
	void testReadSalesLog() throws FileNotFoundException, StockExceptions, CSVFormatException {
		HashMap<String, Integer> items = InventoryManager.loadSalesLog(new File("sales_log_0.csv"));
		assertEquals(24, items.size());
	}

}
