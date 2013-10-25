package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class HistoryDetailPanel extends JFrame {
	private JTable orderDetailList;
	private JPanel comboPanel;
	private PurchaseInfoTableModel infoTable;
	private HistoryItem items;
	private JLabel text;
	private boolean openFlag;
	private JScrollPane scrollPane;
	/**Creates a frame which displays the given order details.*/
	public HistoryDetailPanel(HistoryItem item, boolean OpenFlag){
		this.items = item;
		this.openFlag = OpenFlag;
		openFlag = true;
		infoTable = new PurchaseInfoTableModel();
		initList();
		initGui();
		this.setSize(640, 480);
	}
	private void initGui(){
		scrollPane = new JScrollPane();
		text = new JLabel();
		comboPanel = new JPanel();
		orderDetailList = new JTable(infoTable);
		scrollPane.setViewportView(orderDetailList);
		comboPanel.setLayout(new BorderLayout());
		comboPanel.add(text, BorderLayout.NORTH);
		comboPanel.add(orderDetailList, BorderLayout.CENTER);
		text.setText("Details for order no: " + items.getId() + "@" + items.getDateTaken() + " :: " + items.getTimeTaken());
		this.add(comboPanel);
		this.setVisible(true);
		this.repaint();
	}
	private void initList(){
		List<SoldItem> orderItems = items.getSoldItems();
		for(SoldItem item : orderItems){
			infoTable.addItem(item);
		}
	}
	private void setClosingAction(){
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				disableFlag();			
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void disableFlag(){
		openFlag = false;
		this.dispose();
	}
}
