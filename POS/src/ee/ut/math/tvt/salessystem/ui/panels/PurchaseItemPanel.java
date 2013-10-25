package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItemListable;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.utils.WarehouseHelper;
import ee.ut.math.tvt.salessystem.utils.WarehouseStateException;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.xml.Log4jEntityResolver;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField priceField;
	private JComboBox<StockItemListable> productField;
	private JButton addItemButton;

	// Warehouse model
	private SalesSystemModel model;

	// Warehouse helper
	private WarehouseHelper helper;
	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;
		helper = new WarehouseHelper(model);
		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {
		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		quantityField = new JTextField("1");
		quantityField.setEditable(true);
		priceField = new JTextField();
		productField = new JComboBox<StockItemListable>();
		productField.setEditable(false);
		productField.setModel(populateComboBox());
		productField.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				fillDialogFields();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				fillDialogFields();

			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}

		});

		priceField.setEditable(false);

		// == Add components to the panel

		// - Product list
		panel.add(new JLabel("Product:"));
		panel.add(productField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}
	// Populates the combo box list.
	private ComboBoxModel<StockItemListable> populateComboBox() {
		Vector<StockItemListable> newVector = new Vector<StockItemListable>();
		try {
			List<StockItem> items = model.getWarehouseTableModel()
					.getTableRows();
			for (StockItem item : items) {
				StockItemListable newListable = new StockItemListable(
						item.getId(), item.getName(), item.getDescription(),
						item.getPrice());
				newVector.add(newListable);
			}
		} catch (Exception e) {
			System.err.println("Exception happened. Help! : " + e);
		}
		ComboBoxModel<StockItemListable> list = new DefaultComboBoxModel<StockItemListable>(
				newVector);
		return list;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = (StockItem) productField.getSelectedItem();

		if (stockItem != null) {
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	// Obsolete - never used.
	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = (StockItem) productField.getSelectedItem();
		if (stockItem != null) {
			long productID = stockItem.getId();
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			HandlePurchase(productID, quantity, stockItem);
			
		}
	}

	public void HandlePurchase(long productID, int quantity, StockItem item){
		if(!helper.canOrderQuantity(productID, quantity)){
			JOptionPane.showMessageDialog(null, "You cannot order that many items!");
		} else {
			try {
				helper.decreaseWarehouseState(productID, quantity);
				model.getCurrentPurchaseTableModel().addItem(
						new SoldItem(item, quantity));
			} catch (NoSuchElementException | WarehouseStateException e) {
				// TODO Think of something to go here.
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.productField.setEnabled(enabled);
		this.addItemButton.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		quantityField.setText("1");
		priceField.setText("");
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
