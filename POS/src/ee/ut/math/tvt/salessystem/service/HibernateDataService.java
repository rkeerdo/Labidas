package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();
	
	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("FROM SoldItem").list();
		return result;
	}
	
	public List<StockItem> getStockItems() {
		List<StockItem> result = session.createQuery("FROM StockItem").list();
		return result;
	}
	
	public List<HistoryItem> getHistoryItems() {
		List<HistoryItem> result = session.createQuery("FROM HistoryItem").list();
		List<SoldItem> result2 = session.createQuery("FROM SoldItem").list();
		for(SoldItem item:result2){
			Integer hId=item.getHistoryId();
			result.get(hId).getSoldItems().add(item);
		}
		return result;
	}

	public void addHistoryItem(HistoryItem item) {
		session.save(item);
	}
	
	public void addSoldItem(SoldItem item) {
		session.save(item);
	}
}