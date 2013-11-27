package ee.ut.math.tvt.salessystem.ui.model;

import java.awt.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.Labidas.Intro;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
 private static final long serialVersionUID = 1L;

 private static final Logger log = Logger.getLogger(StockTableModel.class);
 
 private ArrayList<StockItem> stockItems;


 public StockTableModel() {
  super(new String[] {"Id", "Name", "Price", "Quantity"});
  stockItems = new ArrayList<StockItem>();

 }

 @Override
 protected Object getColumnValue(StockItem item, int columnIndex) {
  switch (columnIndex) {
  case 0:
   return item.getId();
  case 1:
   return item.getName();
  case 2:
   return item.getPrice();
  case 3:
   return item.getQuantity();
  }
  throw new IllegalArgumentException("Column index out of range");
 }

 /**
  * Add new stock item to table. If there already is a stock item with
  * same id, then existing item's quantity will be increased.
  * @param stockItem
  */
 public void addItem(final StockItem stockItem) {
  Intro.service.getSession().beginTransaction();
  StockItem item = checkIfUnique(stockItem);
  if(item == stockItem) {
   Intro.service.getSession().save(stockItem);
   Intro.service.getSession().getTransaction().commit();
   log.debug("Added " + stockItem.getName()
     + " quantity of " + stockItem.getQuantity());
  }
  else {
   Intro.service.getSession().merge(item);
   Intro.service.getSession().getTransaction().commit();
   log.debug("Found existing item " + item.getName()
     + " increased quantity by " + stockItem.getQuantity());
  }
  fireTableDataChanged();
 }
 
 public StockItem checkIfUnique(StockItem stockItem) {
  try {
   StockItem item = getItemById(stockItem.getId());
   item.setQuantity(item.getQuantity() + stockItem.getQuantity());
   return item;
  }
  catch (NoSuchElementException e) {
   rows.add(stockItem);
   return stockItem;
  }
 }
 
 public boolean hasEnoughInStock(StockItem item, int quantity) {
        for(StockItem i : rows) {
                if (i == item) {
                        return (i.getQuantity() >= quantity);
                }
        }
        return false;
 }
 
 public boolean validateNameUniqueness(String newName) {
        return validateNameUniqueness(newName, null);
 }
 
 /**
     * Checks name uniqueness, ignoring a single item
     */
    public boolean validateNameUniqueness(String newName, StockItem ignoreItem) {
            for (StockItem item : stockItems) {
                    if (item == ignoreItem) {
                            log.debug("=== Skipping ignored item "+ item.getId());
                            continue;
                    }
                    log.debug(" === Comparing: " + newName + " vs. " + item.getName());

                    if (newName.equals(item.getName())) {
                            return false;
                    }
            }
            return true;
    }



 @Override
 public String toString() {
  final StringBuffer buffer = new StringBuffer();

  for (int i = 0; i < headers.length; i++)
   buffer.append(headers[i] + "\t");
  buffer.append("\n");

  for (final StockItem stockItem : rows) {
   buffer.append(stockItem.getId() + "\t");
   buffer.append(stockItem.getName() + "\t");
   buffer.append(stockItem.getPrice() + "\t");
   buffer.append(stockItem.getQuantity() + "\t");
   buffer.append("\n");
  }

  return buffer.toString();
 }

}