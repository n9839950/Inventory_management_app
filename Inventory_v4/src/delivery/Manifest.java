package delivery;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import stock.InventoryManager;
import stock.Item;

/**
 * Manifest 
 * 
 * Manages shipping.
 */
public class Manifest {
	ArrayList<Truck> trucks;
	
	public Manifest() {
		trucks = new ArrayList<>();
	}
	
	/**
	 * Exports the manifest 
	 * @param f the file to save the manifest.
	 * @throws IOException
	 * @throws DeliveryException
	 */
	public void exportManifest(File f) throws IOException, DeliveryException {
		PrintWriter writer = new PrintWriter(new FileWriter(f));

		for (Truck truck : trucks) {
			if (truck instanceof RefrigeratedTruck) {
				writer.println(">Refrigerated");
			} else {
				writer.println(">Ordinary");
			}

			for (Item item : truck.getCargo()) {
				writer.printf("%s,%s\n", item.getName(), item.getInStock());
			}
		}

		writer.close();
	}
	
	/**
	 * Build a manifest for the given list of items that need to be ordered.
	 * @param reorderList
	 * @throws DeliveryException
	 */
	public void buildManifest(ArrayList<Item> reorderList) throws DeliveryException {
		Truck truck = null;

		for (Item item : reorderList) {
			
			int qty = item.getReorderAmount();

			if (item.getTemperature() != null) {
				if(truck == null) {
					truck = new RefrigeratedTruck();
				}
				while(qty > 0) {
					if(truck.getspaceRemaining() >= qty) {
						Item orderItem = new Item(item);
						orderItem.setInStock(qty);	
						//System.out.printf("Adding %d of %s  to a refrigerated truck\n",orderItem.getInStock(), orderItem.getName());
						truck.addItem(orderItem);
						qty = 0;
					}
					else {
						Item orderItem = new Item(item);
						orderItem.setInStock(truck.getspaceRemaining());
						qty -= truck.getspaceRemaining();
						//System.out.printf("Adding %d of %s  to a refrigerated truck\n",orderItem.getInStock(), orderItem.getName());
						truck.addItem(orderItem);
						trucks.add(truck);
						truck = new RefrigeratedTruck();
					}
				}
			} else {
				if(truck == null) {
					truck = new OrdinaryTruck();
				}
				while(qty > 0) {
	
					if(truck.getspaceRemaining() >= qty) {
						Item orderItem = new Item(item);
						
						orderItem.setInStock(qty);
						truck.addItem(orderItem);
						//System.out.printf("Adding %d of %s  to an ordinary truck\n",orderItem.getInStock(), orderItem.getName());
						qty = 0;
					}
					else {
						Item orderItem = new Item(item);
						orderItem.setInStock(truck.getspaceRemaining());
						qty -= truck.getspaceRemaining();
						//System.out.printf("Adding %d of %s  to an ordinary truck\n",orderItem.getInStock(), orderItem.getName());
						truck.addItem(orderItem);
						trucks.add(truck);
						truck = new OrdinaryTruck();
					}
				}
			}
		}

		if(truck != null && ! truck.isEmpty()) {
			trucks.add(truck);
		}
		
	}
	
	public ArrayList<Truck> loadManifest(File f) throws FileNotFoundException, DeliveryException {
		Scanner sc;
		ArrayList<Truck> trucks = new ArrayList<>();

		sc = new Scanner(f);
		Truck truck = null;

		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			if (s.equals(">Refrigerated")) {
				truck = new RefrigeratedTruck();
				trucks.add(truck);
			} else if (s.equals(">Ordinary")) {
				truck = new OrdinaryTruck();
				trucks.add(truck);
			} else {
				String[] parts = s.trim().split(",");
				Item item = InventoryManager.getItem(parts[0]);
				item.setInStock(Integer.parseInt(parts[1]));
				truck.addItem(item);
			}
		}
		sc.close();
		return trucks;
	}
	
	public ArrayList<Truck> getTrucks() {
		return trucks;
	}
}
