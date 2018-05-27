package delivery;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stock.Item;

class OrdinaryTruckTest {
	OrdinaryTruck truck ;
	@BeforeEach
	void setUp() throws Exception {
		truck = new OrdinaryTruck();
	}

	@Test
	void testGetspaceRemaining() {
		assertEquals(truck.getCapacity(),  truck.getspaceRemaining());
	}
	
	@Test
	void testGetcost() throws DeliveryException {
		assertEquals(truck.getCost(), 750, 0.001);
		Item item = new Item("first", 1.0, 1.0, 10, 10);
		item.setInStock(500);
		truck.addItem(item);
		assertEquals(truck.getCost(), 750 + 0.25 * 500, 0.001);
	}


	@Test
	void testAddItem() throws DeliveryException {
		assertEquals(0, truck.getCargo().size());
		Item item = new Item("first", 1.0, 1.0, 10, 10);
		try {
			item.setTemperature(-10);
			truck.addItem(item);
			fail("Cannot add an item that requires refrigeration to an ordinary truck");
		} catch (DeliveryException dex) {
			assertTrue(true);
		}
		
		item.setTemperature(null);
		truck.addItem(item);
		assertEquals(1, truck.getCargo().size());
		assertEquals(truck.getCapacity() - item.getInStock(), truck.getspaceRemaining());
	}

}
