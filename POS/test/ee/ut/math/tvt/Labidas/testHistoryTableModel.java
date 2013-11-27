package ee.ut.math.tvt.Labidas;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.*;

import ee.ut.math.tvt.Labidas.Intro;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;

public class testHistoryTableModel {
	
	private HistoryTableModel model;
	private HistoryItem hItem;
	
	//NB! Ant startDB before running this test
	
	@Before
	public void setUp() {
		HibernateDataService hib = new HibernateDataService();
		model = new HistoryTableModel(hib);
		ArrayList<SoldItem> hItemList = new ArrayList<SoldItem>();
		SoldItem sItem1 = new SoldItem(new StockItem(50L, "test1", "", 50.0), 5);
		hItemList.add(sItem1);
		hItem = new HistoryItem(hItemList);
		model.addItem(hItem);
	}
	
	@Test
	public void testGetSoldItems() {
		assertEquals(1, model.getSoldItems());
	}

}
