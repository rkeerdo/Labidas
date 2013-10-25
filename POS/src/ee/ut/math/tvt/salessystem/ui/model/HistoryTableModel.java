package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

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
		int currentRows = this.getRowCount();
		item.assignID(new Long(currentRows+1));
		rows.add(item);
		log.debug("Added item ID : " + item.getId());
		fireTableDataChanged();
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
