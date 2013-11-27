package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Container;


import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;


public class PurchaseConfirmationPanel extends JDialog {
	private JLabel orderSumLabel;
	private JTextField orderSum;
	private JLabel orderPaymentLabel;
	private JTextField orderPayment;
	private JLabel orderChangeLabel;
	private JTextField orderChange;
	private JButton acceptPayment;
	private JButton cancelPayment;
	private SalesSystemModel model;
	private JPanel combinationPanel;
	private JPanel fieldPanel;
	private Container container;
	private boolean orderConfirmed = false;
	private static final Logger log = Logger.getLogger(PurchaseConfirmationPanel.class);
	/**Constructs a new PurchaseConfirmationPanel using the given SalesSystemModel containing warehouse and sales data.
	 * @param SalesSystemModel model - the model all information will be parsed from.
	 */
	public PurchaseConfirmationPanel(SalesSystemModel model){
		this.model = model;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 100);
		this.setTitle("Confirmation");
		initGui();
		fillGui();
		addListeners();
		this.setName("Purchase confirmation");
		this.add(fieldPanel);
		this.setLocationRelativeTo(null);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private double getSoldSum(){
		double sum = 0;
		PurchaseInfoTableModel purchaseModel = model.getCurrentPurchaseTableModel();
		List<SoldItem> soldItems = purchaseModel.getTableRows();
		for(SoldItem item : soldItems){
			sum = sum + item.getSum();
		}
		return sum;
	}
	private void fillGui(){
		double sum = getSoldSum();
		orderSum.setText(String.valueOf(sum));
		orderChange.setText(String.valueOf(0.0));
	}
	private void initGui(){
		fieldPanel = new JPanel();
		Dimension minBoxSize = new Dimension(50, 20);
		orderSumLabel = new JLabel("Total cost of order :");
		orderSum = new JTextField();
		orderSum.setMinimumSize(minBoxSize);
		orderSum.setPreferredSize(minBoxSize);
		orderSum.setEditable(false);
		orderPaymentLabel = new JLabel("Money paid : ");
		orderPayment = new JTextField();
		orderPayment.setPreferredSize(minBoxSize);
		orderPayment.setMinimumSize(minBoxSize);
		orderChangeLabel = new JLabel("Change : ");
		orderChange = new JTextField();
		orderChange.setEditable(false);
		orderChange.setMinimumSize(minBoxSize);
		orderChange.setPreferredSize(minBoxSize);
		acceptPayment = new JButton("Accept");
		cancelPayment = new JButton("Cancel");
		fieldPanel.add(orderSumLabel);
		fieldPanel.add(orderSum);
		fieldPanel.add(orderPaymentLabel);
		fieldPanel.add(orderPayment);
		fieldPanel.add(orderChangeLabel);
		fieldPanel.add(orderChange);
		fieldPanel.add(acceptPayment);
		fieldPanel.add(cancelPayment);
	}
	private void addListeners(){
		
		cancelPayment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		orderPayment.addCaretListener(new CaretListener(){

			@Override
			public void caretUpdate(CaretEvent arg0) {
				String text = orderPayment.getText();
				double payment;
				try{
					payment = Double.parseDouble(text);
					double due = Double.parseDouble(orderSum.getText());
					double change = payment-due;
					orderChange.setText(String.valueOf(change));
				} catch (NumberFormatException e){
					if(!(orderPayment.getText().length()==0))	orderChange.setText("Fix the damned number format.");
				}
			}

		});
		acceptPayment.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				completeSale();
			}

		});
	}
	private void completeSale(){
		int i = checkForErrors();
		if(i==0){
			this.setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null, "Invalid parameters. Try again with new inputs.");
		} else if(i==2){
			this.setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null, "Payment must be bigger or equal to the order sum.");
		} else {
			orderConfirmed = true;
			this.dispose();
			return;
		}
		this.setAlwaysOnTop(true);
	}
	
	private int checkForErrors(){
		// 0 for error, 1 for success, 2 for invalid change.
		double changeCheck;
		try{
			changeCheck = Double.parseDouble(orderChange.getText());
			if(orderPayment.getText().length()==0){
				throw new Exception("Please fill the money field.");
			}
		} catch (Exception e){
			System.err.println("Exception caught!!!!REDTEXT!");
			return 0;
		}
		if(changeCheck>=0){
			return 1;
		} else {
			return 2;
		} 
		
	}
	// return true if the order has been confirmed.
	/**Checks if the order has been completed and confirmed. 
	 * @return boolean orderConfirmed - true if order has been confirmed.*/
	public boolean orderComplete(){
		return orderConfirmed;
	}
}
