package stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import delivery.DeliveryException;
import delivery.Manifest;

public class Store {
	private String name;
	Stock stock;
	Double capital;
	
	static Store store = null;
	
	private Store() {
		stock = new Stock();
		capital = 100000.00;
	}
	
	public static Store getStore() {
		if(store == null) {
			store = new Store();
		}
		return store;
	}

	public Stock getStock() {
		return stock;
	}

	public void loadSales(File f) throws FileNotFoundException, StockExceptions, CSVFormatException {
		HashMap<String, Integer> sales = InventoryManager.loadSalesLog(f);
		capital += stock.executeSales(sales);
	}
	/**
	 * Exports a manifest to the given file
	 * @param f the file to write to 
	 * @throws IOException
	 * @throws DeliveryException
	 */
	public void exportManifest(File f) throws IOException, DeliveryException {
	 	ArrayList<Item> items = stock.getReorderList();
	 	Manifest manifest = new Manifest();
	 	manifest.buildManifest(items);
		manifest.exportManifest(f);
	}

	public void initialize() throws CSVFormatException  {
		stock.initialize();
	}

	/**
	 * Loads the given manifest file and pays for the supplies and transportation.
	 * 
	 * @param f the file with the manifest
	 * @return the new capital after payments are made.
	 * @throws FileNotFoundException
	 * @throws DeliveryException
	 */
	public double loadManifest(File f) throws FileNotFoundException, DeliveryException {
		Manifest manifest = new Manifest();
		capital -= stock.loadWareHouse(manifest.loadManifest(f));
		
		return capital;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the capital
	 */
	public Double getCapital() {
		return capital;
	}

	/**
	 * @param capital the capital to set
	 */
	public void setCapital(Double capital) {
		this.capital = capital;
	}
	
}
