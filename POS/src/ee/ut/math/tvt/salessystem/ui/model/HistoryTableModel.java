package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ee.ut.math.tvt.Labidas.Intro;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem>{

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);
	public HistoryTableModel() {
		super(new String[]{"Id" ,"Date", "Time", "Price"});
		}

	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getDateTaken();
		case 2:
			return item.getTimeTaken();
		case 3:
			return item.getTotalOrderPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	/**Adds the history item and assigns it an ID based on current session.*/
	public void addItem(HistoryItem item){
		Session session = Intro.service.getSession();
		session.beginTransaction();
		int currentRows = this.getRowCount();
		item.assignID(new Long(currentRows));
		SoldItem[] clone=new SoldItem[item.getSoldItems().size()];
		item.getSoldItems().toArray(clone);
		for(int i=0;i<clone.length;i++){
			clone[i].assignSID(currentRows);
			clone[i].assignID(new Long(this.getSoldItems()+i));
			session.save(clone[i]);
			System.out.println(i);
		}
		rows.add(item);
		session.save(item);
		session.getTransaction().commit();
		log.debug("Added item ID : " + item.getId());
		fireTableDataChanged();
	}
	
	public void addFromHibernate() {
		rows = Intro.service.getHistoryItems();
	}
	
	public int getSoldItems(){
		int count=0;
		for(HistoryItem item:rows){
			if(item.getSoldItems()!=null)
				count+=item.getSoldItems().size();
		}
		return count;
	}
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getDateTaken() + "\t");
			buffer.append(stockItem.getTimeTaken() + "\t");
			buffer.append(stockItem.getTotalOrderPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	

}
