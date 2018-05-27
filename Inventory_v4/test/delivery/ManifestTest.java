package delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stock.CSVFormatException;
import stock.Stock;

public class ManifestTest {
	Stock stock;
	Manifest manifest;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		stock = new Stock();
		manifest = new Manifest();
	}

	
	/**
	 * Test method for {@link stock.Manifest#exportManifest(java.io.File)}.
	 * @throws CSVFormatException 
	 * @throws DeliveryException 
	 * @throws IOException 
	 */
	@Test
	void testExportManifest() throws CSVFormatException, DeliveryException, IOException {
		stock.initialize();
		File f = new File("test.csv");
		f.delete();
		manifest.exportManifest(f);
		
		assertTrue(f.exists());
	}
	

	/**
	 * Test method for {@link stock.Stock#buildManifest()}.
	 * 
	 * @throws DeliveryException
	 * @throws CSVFormatException
	 */
	@Test
	void testBuildManifest() throws DeliveryException, CSVFormatException {
		stock.initialize();

		manifest.buildManifest(stock.getReorderList());
		ArrayList<Truck> trucks = manifest.getTrucks();
		assertFalse(trucks.isEmpty());
		Truck truck0 = trucks.get(0);

		assertTrue(truck0 instanceof RefrigeratedTruck);

		assertEquals(0, truck0.getspaceRemaining());
		assertFalse(truck0.getCargo().isEmpty());
		assertEquals(((RefrigeratedTruck) truck0).getTemperature(), (int) truck0.getCargo().get(0).getTemperature());
	}
	

	/**
	 * Test method for {@link stock.Stock#loadManifest(java.io.File)}.
	 * 
	 * @throws DeliveryException
	 * @throws FileNotFoundException
	 * @throws CSVFormatException
	 */
	@Test
	void testLoadManifest() throws FileNotFoundException, DeliveryException, CSVFormatException {
		stock.initialize();
		ArrayList<Truck> trucks = manifest.loadManifest(new File("manifest.csv"));
		assertEquals(2, trucks.size());
		assertTrue(trucks.get(0) instanceof RefrigeratedTruck);
		assertTrue(trucks.get(1) instanceof OrdinaryTruck);
	}


}
