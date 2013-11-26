package ee.ut.math.tvt.Labidas;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	
	private StockItem item1;
    private StockItem item2;
    private StockItem item3;
    private StockItem item4;
    private StockItem item5;
    
    private SoldItem sold1;
    
    private StockTableModel model1 = new StockTableModel();
    private StockTableModel model2 = new StockTableModel();
    private StockTableModel model3 = new StockTableModel();
    private StockTableModel model4 = new StockTableModel();

    
	@Before
	public void setUp() {
		item1 = new StockItem((long) 100, "Kapsas", "Punane", 5.0, 5);
		item2 = new StockItem((long) 101, "Kapsas2", "Punane2", 5.0, 5);
		item3 = new StockItem((long) 100, "Kapsas", "Punane", 5.0, 5);
		item4 = new StockItem((long) 101, "Tomat", "Roheline", 5.0, 6);
		item5 = new StockItem((long) 101, "Kapsas", "Punane", 5.0, 5);

		
        model1.addItem(item1);
        model1.addItem(item2);
        
        model2.addItem(item3);
        
        model3.addItem(item4);
        model3.addItem(item5);
        
        model4.addItem(item1);

	}
	
	@Test
	public void testValidateNameUniqueness() {
		assertTrue(model1.validateNameUniqueness("Kapsas"));
        assertTrue(!model1.validateNameUniqueness("Juurikas"));

	}
	
	@Test
	public void testHasEnoughInStock() {
        assertTrue(model2.hasEnoughInStock(item2, 0));
        assertTrue(model2.hasEnoughInStock(item2, 5));
        assertTrue(!model2.hasEnoughInStock(item2, 6));

	}
	
	@Test
	public void testGetItemByIdWhenItemExists() {
		Assert.assertEquals(item1, model3.getItemById((long)100));
	}
	
	@Test
	(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		model4.getItemById((long)105);
	}
}
