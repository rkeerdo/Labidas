package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.utils.WarehouseHelper;

public class WarehouseEntryPanel extends JFrame {
	private JTextField idField;
	private JLabel idLabel;
	private JTextField nameField;
	private JLabel nameLabel;
	private JTextField priceField;
	private JLabel priceLabel;
	private JTextField quantityField;
	private JLabel quantityLabel;
	private SalesSystemModel model;
	private JPanel layoutPanel;
	private JTabbedPane tabPane;
	private JButton submitPane1;
	private JScrollPane scrollPane1;
	private WarehouseHelper helper;
	public WarehouseEntryPanel(SalesSystemModel model) {
		helper = new WarehouseHelper(model);
		this.model = model;
		this.setName("Warehouse tab");
		tabPane = new JTabbedPane();
		initNewItemGui();
		initPane1Listeners();
		this.setSize(300,400);
		this.setMinimumSize(new Dimension(300, 400));
		this.setResizable(false);
		this.add(tabPane);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.repaint();
	}
	// Initializes the GUI elements.
	private void initNewItemGui(){
		Dimension minDim = new Dimension(100, 20);
		idField = new JTextField();
		idField.setPreferredSize(minDim);
		nameField = new JTextField();
		nameField.setPreferredSize(minDim);
		priceField = new JTextField();
		priceField.setPreferredSize(minDim);
		quantityField = new JTextField();
		quantityField.setPreferredSize(minDim);
		idLabel = new JLabel("Item ID:");
		nameLabel = new JLabel("Item Name:");
		priceLabel = new JLabel("Item price:");
		quantityLabel = new JLabel("Item quantity");
		layoutPanel = new JPanel();
		layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
		submitPane1 = new JButton("Submit");
		scrollPane1 = new JScrollPane(layoutPanel);
		JPanel comboID = new JPanel();
		comboID.add(idLabel);
		comboID.add(idField);
		JPanel comboName = new JPanel();
		comboName.add(nameLabel);
		comboName.add(nameField);
		JPanel comboPrice = new JPanel();
		comboPrice.add(priceLabel);
		comboPrice.add(priceField);
		JPanel comboQuantity = new JPanel();
		comboQuantity.add(quantityLabel);
		comboQuantity.add(quantityField);
		idField.setToolTipText("Must be of \'long' data type");
		nameField.setToolTipText("Name of the product. Must not be empty.");
		priceField.setToolTipText("Must be of \'double' data type");
		quantityField.setToolTipText("Must be of \'int' data type");
		layoutPanel.add(comboID);
		layoutPanel.add(comboName);
		layoutPanel.add(comboPrice);
		layoutPanel.add(comboQuantity);
		layoutPanel.add(submitPane1);
		tabPane.addTab("New item", scrollPane1);		
	}
	// TODO : in case you want to change existing item properties.
	// On hold for now.
	private void initExistingItemGui(){
		
	}
	private void initPane1Listeners(){
		idField.addCaretListener(new CaretListener(){

			@Override
			public void caretUpdate(CaretEvent arg0) {
				try{
					long id = Long.parseLong(idField.getText());
					StockItem existingItem = model.getWarehouseTableModel().getItemById(id);
					nameField.setEnabled(false);
					nameField.setText(existingItem.getName());
					priceField.setEnabled(false);
					priceField.setText(String.valueOf(existingItem.getPrice()));
					
				} catch(NumberFormatException e){
					if(!(idField.getText().length()==0)){
						JOptionPane.showMessageDialog(null, "Please enter the ID in a correct format");
					}
				} catch(NoSuchElementException e1){
					priceField.setEnabled(true);
					nameField.setEnabled(true);
					//Do nothing, element does not exist in warehouse.
				}
				
			}
			
		});
		submitPane1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StockItem newItem = constructStockItem();
				if(!(newItem==null)){
					int quantity = newItem.getQuantity();
					long id = newItem.getId();
					if(helper.canOrderQuantity(id, -quantity)){
						model.getWarehouseTableModel().addItem(newItem);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Warehouse item quantity cannot be set to negative.");
					}
				}
			}
			
		});
	}
	// Constructs a new stock item to be added to the warehouse.
	private StockItem constructStockItem(){
		StockItem item = new StockItem();
		//Parse the field information.
		long id;
		double price;
		int quantity;
		try{
			id = Long.parseLong(idField.getText());
			price = Double.parseDouble(priceField.getText());
			quantity = Integer.parseInt(quantityField.getText());
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "One or more fields are formatted incorrectly!");
			return null;
		}
		item.setId(id);
		item.setPrice(price);
		item.setQuantity(quantity);
		item.setName(nameField.getText());
		return item;
	}
}
