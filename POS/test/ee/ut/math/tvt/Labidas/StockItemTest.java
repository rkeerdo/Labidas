package ee.ut.math.tvt.Labidas;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {
	
	private StockItem testItem;
	
    @Before
    public void setUp() {
    	testItem = new StockItem((long) 100, "testItem", "", 100.0, 10);
    }

    @Test
    public void testClone() {
    	StockItem testItem2 = (StockItem) testItem.clone();
    	if(testItem.getId().equals(testItem2.getId()))
    		if(testItem.getName().equals(testItem2.getName()))
    			if(testItem.getDescription().equals(testItem2.getDescription()))
    				if(testItem.getPrice() == testItem2.getPrice())
    					if(testItem.getQuantity() == testItem2.getQuantity())
    						Assert.assertTrue(true);
    }
    
    @Test
    public void testGetColumn() {
    	if((long) testItem.getColumn(0) == 100)
    		if(((String) testItem.getColumn(1)).equals("testItem"))
    			if((double) testItem.getColumn(2) == 100.0)
    				if((int) testItem.getColumn(3) == 10)
    					Assert.assertTrue(true);
    }
}
