package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.util.WarehouseHelper;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);

    // Warehouse model
    private StockTableModel warehouseTableModel;
    
    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;

    // History table model
    private HistoryTableModel historyTableModel;
    private final SalesDomainController domainController;

    /**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        historyTableModel = new HistoryTableModel();
        // populate stock model with history data
        historyTableModel.populateWithData(domainController.loadHistoryState());
        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());

    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    public HistoryTableModel getHistoryTableModel(){
    	return historyTableModel;
    }
    // If the purchase is cancelled, restocks the warehouse based on shopping cart contents.
    public void restockCurrentPurchaseTableModel(){
    	WarehouseHelper helper = new WarehouseHelper(this);
    	List<SoldItem> items = currentPurchaseTableModel.getTableRows();
    	for(SoldItem item : items){
    		helper.increaseWarehouseState(item.getId(), item.getQuantity());
    	}
    }
}
