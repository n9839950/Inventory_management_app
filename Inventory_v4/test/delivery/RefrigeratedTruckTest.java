package delivery;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stock.Item;

class RefrigeratedTruckTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetCost() {
		RefrigeratedTruck truck = new RefrigeratedTruck();
		truck.setTemperature(0);
		assertEquals(1100, truck.getCost());
		truck.setTemperature(5);
		assertEquals(1040, truck.getCost());
	}

	@Test
	void testGetTemperature() throws DeliveryException {
		RefrigeratedTruck truck = new RefrigeratedTruck();
		assertEquals(Integer.MAX_VALUE, truck.getTemperature());
		Item item = new Item("first", 1.0, 1.0, 10, 10);
		item.setTemperature(-10);
		truck.addItem(item);
	
		assertEquals(-10, truck.getTemperature());

		item = new Item("second", 1.0, 1.0, 10, 10);
		item.setTemperature(-15);
		truck.addItem(item);

		assertEquals(-15, truck.getTemperature());
	}

}
