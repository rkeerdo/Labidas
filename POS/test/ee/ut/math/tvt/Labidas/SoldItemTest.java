package ee.ut.math.tvt.Labidas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {

	SoldItem testItem;
	SoldItem testItemEmpty;
	@Before
	public void setUp() {
		testItem = new SoldItem(new StockItem((long) 100, "test", "", 5.0), 5);
		testItemEmpty = new SoldItem(new StockItem((long) 100, "test", "", 5.0), 0);
	}
	
	@Test
	public void testGetSum() {
		assertEquals(testItem.getSum(), 25.0, 0.001);
	}
	
	@Test
	public void testGetSumWithZeroQuantity() {
		assertEquals(testItemEmpty.getSum(), 0.0, 0.001);
	}
}
