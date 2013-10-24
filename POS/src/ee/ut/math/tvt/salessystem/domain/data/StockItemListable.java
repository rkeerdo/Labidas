package ee.ut.math.tvt.salessystem.domain.data;

public class StockItemListable extends StockItem{

	public StockItemListable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockItemListable(Long id, String name, String desc, double price,
			int quantity) {
		super(id, name, desc, price, quantity);
		// TODO Auto-generated constructor stub
	}

	public StockItemListable(Long id, String name, String desc, double price) {
		super(id, name, desc, price);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString(){
		return getName();
	}
}
