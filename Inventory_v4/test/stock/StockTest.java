/**
 * 
 */
package stock;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import delivery.DeliveryException;
import delivery.Manifest;
import delivery.Truck;

/**
 * @author raditha
 *
 */
class StockTest {
	Stock stock;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		stock = new Stock();
	}

	/**
	 * Test method for {@link stock.Stock#getItems()}.
	 */
	@Test
	void testGetItems() {
		assertEquals(stock.getItems().size(), 0);
	}


	/**
	 * Test method for {@link stock.Stock#getReorderList()}.
	 * 
	 * @throws CSVFormatException
	 */
	@Test
	void testGetReorderList() throws CSVFormatException {
		stock.initialize();
		ArrayList<Item> items = stock.getReorderList();
		assertEquals(24, items.size());
		assertEquals("ice cream", items.get(0).getName());
		assertEquals("frozen meat", items.get(1).getName());

		assertNull(items.get(items.size() - 1).getTemperature());

	}
	
	/**
	 * Test selling a few items.
	 * @throws StockExceptions
	 * @throws CSVFormatException
	 * @throws FileNotFoundException
	 * @throws DeliveryException
	 */
	@Test
	void testSales() throws StockExceptions, CSVFormatException, FileNotFoundException, DeliveryException {
		stock.initialize();
		Manifest m = new Manifest();
		m.loadManifest(new File("manifest.csv"));
		stock.loadWareHouse(m.loadManifest(new File("manifest.csv")));
		
		HashMap<String, Integer> sales = new HashMap<>();
		sales.put("rice", 50);
		sales.put("ice cream", 20);
		
		assertEquals(stock.executeSales(sales), 3.0 * 50 + 14.0 * 20, 0.001);
	}
	
	@Test
	void testSalesInsufficient() throws CSVFormatException, FileNotFoundException, DeliveryException {
		stock.initialize();
		Manifest m = new Manifest();
		m.loadManifest(new File("manifest.csv"));
		stock.loadWareHouse(m.loadManifest(new File("manifest.csv")));
		
		HashMap<String, Integer> sales = new HashMap<>();
		sales.put("rice", 150);
		sales.put("ice cream", 20);
		
		try {
			assertEquals(stock.executeSales(sales), 3.0 * 150 + 14.0 * 20, 0.001);
			fail("Trying to sell more than we have in stock should raise an exception.");
		} catch (StockExceptions e) {
			
		}
	}
	
	/**
	 * Try to sell non existent items.
	 * @throws CSVFormatException
	 * @throws FileNotFoundException
	 * @throws DeliveryException
	 */
	@Test
	void testSalesExceptInvalid() throws CSVFormatException, FileNotFoundException, DeliveryException{
		stock.initialize();
		Manifest m = new Manifest();
		m.loadManifest(new File("manifest.csv"));
		stock.loadWareHouse(m.loadManifest(new File("manifest.csv")));

		stock.initialize();
		HashMap<String, Integer> sales = new HashMap<>();
		sales.put("not rice", 100);
		sales.put("not ice cream", 20);
		
		try {
			assertEquals(stock.executeSales(sales), 3.0 * 100 + 14.0 * 20, 0.001);
			fail("Trying to sell non existant items should raise exception.");
		} catch (StockExceptions e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	void testLoadWareHouse() throws DeliveryException, CSVFormatException {
		stock.initialize();
		ArrayList<Item> reoderItems = stock.getReorderList();
		Manifest manifest = new Manifest();
		manifest.buildManifest(reoderItems);
		ArrayList<Truck> trucks = manifest.getTrucks();
		
		assertEquals(57282.12, stock.loadWareHouse(trucks), 0.01);
		
		HashMap<String, Item> items = stock.getItems();
		Item item = items.get("biscuits");
		assertEquals(575, item.getInStock());
		
		item = items.get("pasta");
		assertEquals(250, item.getInStock());
		
		item = items.get("beans");
		assertEquals(525, item.getInStock());
		
	}
	/**
	 * Test method for {@link stock.Stock#initialize()}.
	 * 
	 * @throws CSVFormatException
	 */
	@Test
	void testInitialize() throws CSVFormatException {
		assertEquals(stock.getItems().size(), 0);
		stock.initialize();
		assertEquals(24, stock.getItems().size());
	}

}
