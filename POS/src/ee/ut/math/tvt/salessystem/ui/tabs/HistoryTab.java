package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.HistoryDetailPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
    // TODO - implement!
private SalesDomainController controller;
private SalesSystemModel model;
private JTable table;
private boolean openFlag = false;
    public HistoryTab(SalesDomainController controller, SalesSystemModel model) {
    	this.controller = controller;
    	this.model = model;
    } 
    
    public Component draw() {
        JPanel panel = new JPanel();
        table = new JTable(model.getHistoryTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		listenerSetup();
        return panel;
    }
    
    private void listenerSetup(){
    	table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==1 && !openFlag){
					System.out.println("ACTIVE");
					createDetailPopup();	
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    }
    private void createDetailPopup(){
    	int index = table.getSelectedRow();
    	HistoryItem history = model.getHistoryTableModel().getTableRows().get(index);
    	HistoryDetailPanel panel = new HistoryDetailPanel(history, openFlag);
    }
}