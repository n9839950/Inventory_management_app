package delivery;

import java.util.ArrayList;

import stock.Item;

public abstract class Truck {
	protected int capacity;
	protected int spaceRemaining;
	
	protected ArrayList<Item> cargo;
	
	
	/**
	 * @param capacity
	 * @param spaceRemaining
	 * @param cargo
	 */
	public Truck(int capacity) {
		super();
		this.capacity = capacity;
		this.spaceRemaining = capacity;
		this.cargo = new ArrayList<>();
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public abstract double getCost();

	/**
	 * @return the spaceUsed
	 */
	public int getspaceRemaining() {
		return spaceRemaining;
	}


	/**
	 * @return the cargo
	 */
	public ArrayList<Item> getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(ArrayList<Item> cargo) {
		this.cargo = cargo;
	}
	
	public void addItem(Item item) throws DeliveryException {
		if(item.getInStock() > spaceRemaining) {
			throw new DeliveryException("Truck size exceeded");
		}
		
		else {
			spaceRemaining -= item.getInStock();
			cargo.add(item);
			
		}
	}

	public boolean isEmpty() {
		return capacity == spaceRemaining;
	}
}
