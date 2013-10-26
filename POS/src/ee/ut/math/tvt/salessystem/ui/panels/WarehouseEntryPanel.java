package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

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
	public WarehouseEntryPanel(SalesSystemModel model) {
		this.model = model;
		this.setName("Warehouse tab");
		tabPane = new JTabbedPane();
		initNewItemGui();
		initPane1Button();
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
		nameField.setToolTipText("May be left empty.");
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
	private void initPane1Button(){
		submitPane1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StockItem newItem = constructStockItem();
				if(!(newItem==null)){
					model.getWarehouseTableModel().addItem(newItem);
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
