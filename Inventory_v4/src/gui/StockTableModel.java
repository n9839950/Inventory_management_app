package gui;

import javax.swing.table.AbstractTableModel;

import stock.Item;
import stock.Stock;

public class StockTableModel extends AbstractTableModel{
	Stock stock;
	
	/**
	 * @param stock
	 */
	public StockTableModel(Stock stock) {
		super();
		this.stock = stock;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return stock.getItems().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(row < stock.getItems().size()) {
			Item item = stock.getItems().get(row);
			switch(col) {
			case 0: return item.getName();
			case 1: return item.getCost();
			case 3: return item.getReorderPoint();
			case 4: return item.getReorderAmount();
			case 5: return (item.getTemperature() == null) ? "" :  item.getTemperature();
			}
		}
		
		return null;
	}

}
