package ee.ut.math.tvt.Labidas;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.*;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;


public class HistoryItemTest {
	
	private StockItem item1;
    private StockItem item2;
    private SoldItem item3;
    private SoldItem item4;
    private List<SoldItem> model1 = new ArrayList<SoldItem>();
    private List<SoldItem> model2 = new ArrayList<SoldItem>();
    private HistoryItem history1;
    private HistoryItem history2;

	@Before
    public void setUp() {
      item1 = new StockItem(new Long(100), "Õun", "Kollane", 0.5, 3);
      item2 = new StockItem(new Long(200), "Kõrvits", "Sinine", 0.1, 3);
      item3 = new SoldItem(item1, 3);
      item4 = new SoldItem(item2, 0);
      model1.add(item3);
      model2.add(item4);
      
      history1 = new HistoryItem(model1);
      history1.assignID(1L);
      
      history2 = new HistoryItem(model2);
      history2.assignID(new Long(1));
      
      
    }
	
    @Test
    public void testAddSoldItem(){
    	Assert.assertEquals(model1,history1.getSoldItems());
    }
    
    @Test
    public void testSumWithMultipleItems(){
    	System.out.println(history1.getTotalOrderPrice());
    	Assert.assertEquals(1.5, history1.getTotalOrderPrice());
    }
    
    @Test
    public void testSumWithNoItems(){
    	Assert.assertEquals(0.0, history2.getTotalOrderPrice());
    }
    
    @Test
    public void testAssignHistoryId(){
    	Long longTest1 = new Long(1);
    	Assert.assertEquals(longTest1, history2.getId());
    }
}
