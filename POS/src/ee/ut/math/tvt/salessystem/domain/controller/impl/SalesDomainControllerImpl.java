package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.Labidas.Intro;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is underaged and
		// cannot buy chupa-chups
		// Disabled for temporary debugging purposes.
		//throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
		
	}
	public void submitCurrentPurchase(List<SoldItem> goods, SalesSystemModel model) throws VerificationFailedException {
		HistoryItem item = new HistoryItem(goods);
		model.getHistoryTableModel().addItem(item);
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		return Intro.service.getStockItems();
	}
	/*public List<HistoryItem> loadHistoryState(){
		return Intro.service.getHistoryItems();
	}*/
	@Override
	public void endSession() {
		 HibernateUtil.closeSession();
	}
}
