package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Container;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class PurchaseConfirmationPanel extends JFrame {
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
	public PurchaseConfirmationPanel(SalesSystemModel model){
		this.model = model;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 400);
		initGui();
		fillGui();
		addListeners();
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
		container = new Container();
		orderSumLabel = new JLabel("Total cost of order :");
		orderSum = new JTextField();
		orderSum.setEditable(false);
		orderPaymentLabel = new JLabel("Money paid : ");
		orderPayment = new JTextField();
		orderChangeLabel = new JLabel("Change : ");
		orderChange = new JTextField();
		orderChange.setEditable(false);
		acceptPayment = new JButton("Accept");
		cancelPayment = new JButton("Cancel");
		container.add(orderSumLabel);
		container.add(orderSum);
		container.add(orderPaymentLabel);
		container.add(orderChangeLabel);
		container.add(orderChange);
		container.add(acceptPayment);
		container.add(cancelPayment);
	}
	private void addListeners(){
		orderPayment.addCaretListener(new CaretListener(){

			@Override
			public void caretUpdate(CaretEvent arg0) {
				String text = orderPayment.getText();
				double payment;
				try{
					payment = Double.parseDouble(text);
					orderChange.setText(String.valueOf(payment));
				} catch (NumberFormatException e){
					orderChange.setText("Fix the damned number format.");
				}
			}
			
		});
	}
}
