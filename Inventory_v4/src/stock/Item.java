package stock;

public class Item {
	private String name;
	private double cost;
	private double price;
	private int reorderPoint;
	private int reorderAmount;
	private Integer temperature;
	private int inStock;
	
	
	/**
	 * Build a new item.
	 * @param name the name of the item.
	 * @param cost the the item
	 * @param reorderPoint the reorder point. The item will be reordered when the item falls below this amount
	 * @param reorderAmount the amount to reorder
	 */
	public Item(String name, double cost, double price, int reorderPoint, int reorderAmount) {
		super();
		this.name = name;
		this.cost = cost;
		this.setPrice(price);
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
	}
	
	/**
	 * Create a copy of the given item.
	 * @param item item to be copied.
	 */
	public Item(Item item) {
		this.name = item.name;
		this.cost = item.cost;
		this.setPrice(item.getPrice());
		this.reorderAmount = item.reorderAmount;
		this.reorderPoint = item.reorderPoint;
		this.temperature = item.temperature;
	}
	
	/**
	 * Get the name of the item.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the item
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * @return the reorderPoint
	 */
	public int getReorderPoint() {
		return reorderPoint;
	}
	/**
	 * @param reorderPoint the reorderPoint to set
	 */
	public void setReorderPoint(int reorderPoint) {
		this.reorderPoint = reorderPoint;
	}
	/**
	 * @return the reorderAmount
	 */
	public int getReorderAmount() {
		return reorderAmount;
	}
	/**
	 * @param reorderAmount the reorderAmount to set
	 */
	public void setReorderAmount(int reorderAmount) {
		this.reorderAmount = reorderAmount;
	}
	/**
	 * @return the temperature
	 */
	public Integer getTemperature() {
		return temperature;
	}
	/**
	 * @param d the temperature to set
	 */
	public void setTemperature(Integer d) {
		this.temperature = d;
	}
	/**
	 * @return the inStock
	 */
	public int getInStock() {
		return inStock;
	}
	/**
	 * @param inStock the inStock to set
	 */
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	
	@Override
	public String toString() {
		return String.format("%s cost %.2f per unit. %d in stock %d is the reorder point. Reorder amount = %d", 
				name, cost, inStock, reorderPoint, reorderAmount);
	}

	public void sell(Integer qty) {
		inStock -= qty;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Adds to the stocks in hand.
	 * @param qty
	 */
	public void addToStock(int qty) {
		inStock += qty;
	}
	
}
