package delivery;

import stock.Item;

public class OrdinaryTruck extends Truck {

	public OrdinaryTruck() {
		super(1000);
	}
	
	@Override
	public double getCost() {
		return 750 + 0.25 * (capacity - spaceRemaining);
	}

	@Override
	public void addItem(Item item) throws DeliveryException {
		if(item.getTemperature() != null) {
			throw new DeliveryException(String.format("%s requires refrigeration and cannot be carried in an ordinary truck", item.getName()));
		}
		super.addItem(item);
	}
}
