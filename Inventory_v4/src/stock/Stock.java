package stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import delivery.Truck;

public class Stock {

	static final String ITEM_PROPERTIES_CSV = "item_properties.csv";
	HashMap<String, Item> items;

	/**
	 * 
	 */
	public Stock() {
		super();
		items = new HashMap<>();
	}

	/**
	 * @return the items
	 */
	public HashMap<String, Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

	/**
	 * Produce a list of items that need to be reordered.
	 * 
	 * Tehse items will be sorted by temperature and then by the reorder amount this
	 * will make it possible to minimize the use of freezer trucks.
	 * 
	 * @return
	 */
	public ArrayList<Item> getReorderList() {
		ArrayList<Item> reorder = new ArrayList<>();
		for (Item item : items.values()) {
			if (item.getInStock() <= item.getReorderPoint()) {
				reorder.add(item);
			}
		}

		/*
		 * We sort the items in order of lowest temperature to highest, ties are broken
		 * by looking at the reorder amount. Then the non refrigerated items are listed
		 * in order of reorder amount.
		 */
		Collections.sort(reorder, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				if (o1.getTemperature() == null) {
					if (o2.getTemperature() == null) {
						return o1.getReorderAmount() - o2.getReorderAmount();
					} else {
						return 1;
					}
				}
				if (o2.getTemperature() == null) {
					return -1;
				}

				return o1.getTemperature() - o2.getTemperature();
			}
		});

		return reorder;
	}

	/**
	 * Executes a list of sales against the inventory.
	 * 
	 * Checks that al the items are held in stock in sufficient quantity before
	 * executing the orders. All the orders are carried out together in a single
	 * transaction so that if one item fails nothing is executed.
	 * 
	 * @param sales
	 *            the list of sales to be executed. Item name, and quantity pairs
	 * @return the total value of the sale
	 * @throws StockExceptions
	 *             if thre isn't enough stocks or the item is not something that is
	 *             stocked at all
	 */
	public double executeSales(HashMap<String, Integer> sales) throws StockExceptions {
		double revenue = 0.0;
		/*
		 * First iteration validate the result. We don't want a half backed sales log
		 * which leaves some of the items loaded but others not.
		 */
		for (Map.Entry<String, Integer> sale : sales.entrySet()) {
			Item item = items.get(sale.getKey());
			if (item == null) {
				throw new StockExceptions(String.format("%s was not in stock at the time of sale", sale.getKey()));
			}
			if (item.getInStock() < sale.getValue()) {
				throw new StockExceptions(String.format("%s had insufficient stock for an order quantity of %d",
						sale.getKey(), sale.getValue()));
			}
		}

		for (Item item : items.values()) {
			Integer i = sales.get(item.getName());
			if (i != null) {
				item.sell(i);
				revenue += item.getPrice() * i;
			}
		}

		return revenue;
	}

	/**
	 * Loads item properties
	 * 
	 * @throws CSVFormatException
	 */
	public void initialize() throws CSVFormatException {
		InventoryManager.readItemProperties(ITEM_PROPERTIES_CSV);
		for(String name : InventoryManager.getItems().keySet()) {
			items.put(name, InventoryManager.getItem(name));
		}
	}

	/**
	 * Move the cargo from the trucks into the ware house
	 * 
	 * This effectively increases the quantity of items in stock. but they have to
	 * be paid for. The total amount that has to be paid for including for transport
	 * will be returned.
	 * 
	 * @param trucks
	 *            collection of trucks carrynig goods.
	 * @return the total cost of the items and transportation.
	 */
	public double loadWareHouse(ArrayList<Truck> trucks) {
		Scanner sc;
		double cost = 0.0;
		double transportCost = 0.0;
	//	System.out.println("");
		for (Truck truck : trucks) {
		//	System.out.println("Unloading ...");
			transportCost += truck.getCost();
			for (Item item : truck.getCargo()) {
			//	System.out.printf("%d of %s\n", item.getInStock(), item.getName());
				Item inventoryItem = items.get(item.getName());
				inventoryItem.addToStock(item.getInStock());
				cost += item.getInStock() * item.getCost();
			}
		}

//		 System.out.printf("Number of trucks = %d\n", trucks.size());
//		 System.out.printf("Transport Cost = %.2f , item cost = %.2f\n",
//		 transportCost, cost);
		return transportCost + cost;
	}

}