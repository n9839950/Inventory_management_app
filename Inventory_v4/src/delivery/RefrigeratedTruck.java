package delivery;

import stock.Item;

public class RefrigeratedTruck extends Truck{
	private int temperature;
	
	public RefrigeratedTruck() {
		
		super(800);
		temperature = Integer.MAX_VALUE;
	}
	
	@Override
	public double getCost() {
		return 900 + 200 * Math.pow(0.7, (1.0 * temperature) / 5.0);
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public void addItem(Item item) throws DeliveryException {
		// TODO Auto-generated method stub
		super.addItem(item);

		if(item.getTemperature() != null && item.getTemperature() < temperature) {
			temperature = item.getTemperature();
		}
	}
}
