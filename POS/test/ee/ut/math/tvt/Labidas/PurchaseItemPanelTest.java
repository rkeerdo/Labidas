package ee.ut.math.tvt.Labidas;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

// because the getSum method is actually buried really deep
// and would require generating some UI, the content of the 
// method is just copied and put into work with what's 
// needed for it
public class PurchaseItemPanelTest {
	private PurchaseInfoTableModel model;
	private SoldItem item1;
	private SoldItem item2;
	private static final org.apache.log4j.Logger logger = Logger.getLogger(PurchaseItemPanelTest.class);
	@Before
	public void setUp() {
		PropertyConfigurator.configure("log4j.properties");
		model= new PurchaseInfoTableModel();
		item1=new SoldItem(new StockItem(new Long(1), "Soda","Beverage",1.0,10),3);
		item2=new SoldItem(new StockItem(new Long(2), "Beer","Beverage",2.0,10),1);
	}

	@Test
	public void testAddSoldItem() {
		model.addItem(item1);
		assertEquals(model.getTableRows().get(0).getPrice(),1.0,0.001);
	}
	
	@Test
	public void testGetSumWithNoItems() {
		double sum = 0;
		List<SoldItem> soldItems = model.getTableRows();
		for(SoldItem item : soldItems){
			sum = sum + item.getSum();
		}
		assertEquals(sum,0.0,0.001);
	}
	
	@Test
	public void testGetSumWithOneItem() {
		double sum = 0;
		model.addItem(item1);
		List<SoldItem> soldItems = model.getTableRows();
		for(SoldItem item : soldItems){
			sum = sum + item.getSum();
		}
		assertEquals(sum,3.0,0.001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		double sum = 0;
		model.addItem(item1);
		model.addItem(item2);
		List<SoldItem> soldItems = model.getTableRows();
		for(SoldItem item : soldItems){
			sum = sum + item.getSum();
		}
		assertEquals(sum,5.0,0.001);
	}
}
