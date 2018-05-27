import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import delivery.DeliveryException;
import stock.CSVFormatException;
import stock.Item;
import stock.StockExceptions;
import stock.Store;
/**
 * This is not a unit test. Rather a driver to 
 * execute a series of manifest and sales operations.
 */
public class Driver {

	public static void main(String[] args) throws CSVFormatException, IOException, DeliveryException, StockExceptions {
		Store store = Store.getStore();
		store.initialize();
		
		File manifestFile = new File("test.csv");
		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);

		System.out.printf("1. Loaded initial manifest. Now the capital is %.2f\n", store.getCapital());

		store.loadSales(new File("sales_log_0.csv"));
		System.out.printf("2. Loaded the first sales log. Now the capital is  %.2f\n", store.getCapital());

		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);
		System.out.printf("3. Restocked. Now the capital is  %.2f\n", store.getCapital());
		
		store.loadSales(new File("sales_log_1.csv"));
		System.out.printf("4. Loaded the second sales log. Now the capital is  %.2f\n", store.getCapital());

		
		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);
		System.out.printf("5. Restocked. Now the capital is  %.2f\n", store.getCapital());
		
		store.loadSales(new File("sales_log_2.csv"));
		System.out.printf("6. Loaded the third sales log. Now the capital is  %.2f\n", store.getCapital());
		
		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);

		System.out.printf("7. Restocked. Now the capital is  %.2f\n", store.getCapital());

		
		store.loadSales(new File("sales_log_3.csv"));
		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);

		store.loadSales(new File("sales_log_4.csv"));
		store.exportManifest(manifestFile);
		store.loadManifest(manifestFile);

		
		System.out.printf("6. Loaded sales log 3,4 and restocked. Now the capital is  %.2f\n", store.getCapital());

		
		ArrayList<String> keys = new ArrayList(store.getStock().getItems().keySet());
		Collections.sort(keys);
		
		for(String key: keys) {
			Item item = store.getStock().getItems().get(key);
			System.out.printf("%s = %d\n", item.getName(), item.getInStock());
		}
		
	}
}
