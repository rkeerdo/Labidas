package ee.ut.math.tvt.salessystem.util;

import java.util.NoSuchElementException;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class WarehouseHelper {
private SalesSystemModel model;
	public WarehouseHelper(SalesSystemModel model){
		this.model = model;
	}
	/**Checks if there are any more products with the given ID in warehouse
	 * @param productID - the productID of the chosen product
	 * @return boolean - true if there are any items left, false if none exist.*/
	public boolean hasMoreOf(long productID){
		int i = 0;
		try{
			StockItem item = model.getWarehouseTableModel().getItemById(productID);
			i = item.getQuantity();
			if(i==0){
				return false;
			}
		} catch (NoSuchElementException e){
			return false;
		}
		if(i>0){
			return true;
		} else {
			return false;
		}
	}
	/**Checks if the given amount can be ordered from warehouse.
	 * @param productID - the ID of the chosen product
	 * @param quantity - the quantity for checking
	 * @return boolean - true if the amount can be ordered, false if the order cannot be made.*/
	public boolean canOrderQuantity(long productID, int quantity){
		int i = 0;
		try{
			StockItem item = model.getWarehouseTableModel().getItemById(productID);
			i = item.getQuantity();
		} catch (NoSuchElementException e){
		}
		if(i>=quantity){
			return true;
		} else {
			return false;
		}
	}
	/**Decreases the warehouse state of the given item by the given amount. If the amount exceeds the current
	 * avalible amount in warehouse, throws an Exception. The same behavior applies to missing items.
	 * @param productID - the ID of the chosen product
	 * @param quantity - the quantity to be decreased.
	 * @throws NoSuchElementException - item is missing from the warehouse
	 * @throws WarehouseStateException - the number of items in the warehouse is less than the specified amount.*/
	public void decreaseWarehouseState (long productID, int quantity) throws NoSuchElementException, WarehouseStateException{
		int i = 0;
		StockItem item = model.getWarehouseTableModel().getItemById(productID);
		i = item.getQuantity();
		if(quantity>i){
			throw new WarehouseStateException("You cannot remove a number bigger than the item quantity in warehouse!");
		} else {
			int j = i-quantity;
			item.setQuantity(j);
		}
	}
	/**Increases the warehouse state of the given item by the given amount. If the item is not present in the warehouse, throws a NoSuchElementException
	 * @throws NoSuchElementException - item is missing from the warehouse. This method differs from addItem(StockItem item) by the fact that it does not require a StockItem.
	 * @param productID - the ID of the chosen product
	 * @param quantity - the quantity to be decreased.*/
	public void increaseWarehouseState (long productID, int quantity) throws NoSuchElementException {
		int i = 0;
		StockItem item = model.getWarehouseTableModel().getItemById(productID);
		i = item.getQuantity();
		int j = quantity + i;
		item.setQuantity(j);
	}
}
