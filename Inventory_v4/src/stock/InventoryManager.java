package stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InventoryManager {
	static HashMap<String, Item> items = new HashMap<>();

	public static void readItemProperties(String filename) throws CSVFormatException {
		Scanner sc;
		String s = null;
		try {
			sc = new Scanner(new File(filename));
			while (sc.hasNextLine()) {
				s = sc.nextLine().trim();
				String[] parts = s.split(",");
				Item item = new Item(parts[0], // name
						Double.parseDouble(parts[1]), // cost
						Double.parseDouble(parts[2]), // price
						Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));

				if (parts.length == 6) {
					item.setTemperature(Integer.parseInt(parts[5]));
				}

				item.setInStock(0);
				items.put(item.getName().toLowerCase(), item);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException nex) {
			nex.printStackTrace();
			throw new CSVFormatException("The CSV file contains a malformed record at: \n" + s);
		}
	}

	/**
	 * @return the items
	 */
	public static HashMap<String, Item> getItems() {
		return items;
	}

	public static HashMap<String, Integer> loadSalesLog(File f)
			throws FileNotFoundException, StockExceptions, CSVFormatException {
		HashMap<String, Integer> items = new HashMap<>();

		Scanner sc = new Scanner(f);
		while (sc.hasNextLine()) {
			String[] s = sc.nextLine().trim().split(",");
			try {
				items.put(s[0], Integer.parseInt(s[1]));
			} catch (NumberFormatException nex) {
				throw new CSVFormatException("Incorrct value for sales quantity");
			}
		}
		
		sc.close();
		return items;
	}

	/**
	 * Create and return a new item for the given name.
	 * 
	 * The item name must correspond to an item that we maintain in our inventory/
	 * @param name the name of the item
	 * @return null if the item is not in our inventory.
	 */
	public static Item getItem(String name) {
		Item item =  items.get(name.toLowerCase());
		if( item != null) {
			return new Item(item);
		}
		
		return null;
	}
}
