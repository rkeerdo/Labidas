package ee.ut.math.tvt.salessystem.domain.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="PUBLIC.HISTORYITEM")
public class HistoryItem implements DisplayableItem {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="DATE", nullable=false)
	private String dateTaken;
	@Column(name="TIME", nullable=false)
	private String timeTaken;
	@Column(name="HISTORY_PRICE")
	private double totalOrderPrice;
	@Column(name="SOLD_ITEMS")
	private List<SoldItem> soldItemList;
	/**Constructs a new HistoryItem based on the list of Sold Items.
	 * @param items - Sold Items to be added and compiled.*/
	public HistoryItem(List<SoldItem> items) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = new GregorianCalendar();
		dateTaken = dateFormat.format(cal.getTime());
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		timeTaken = dateFormat.format(cal.getTime());
		this.soldItemList = items;
		assignHistory();
		this.totalOrderPrice = calculatePrice();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getDateTaken() {
		return dateTaken;
	}

	public String getTimeTaken() {
		return timeTaken;
	}

	public double getTotalOrderPrice() {
		return totalOrderPrice;
	}

	public List<SoldItem> getSoldItems() {
		return soldItemList;
	}

	public void assignID(Long id) {
		this.id = id;
	}
	private void assignHistory(){
		for(SoldItem item:soldItemList){
			item.setHistoryItem(this);
		}
	}
	
	private double calculatePrice(){
		double totalPrice = 0;
		for(SoldItem item : soldItemList){
			totalPrice = totalPrice+ item.getPrice();
		}
		return totalPrice;
	}
	@Override
	public String toString(){
		String returnMeThis = "Order no : " + this.id + ", Date : " + this.dateTaken + ", Time : " + this.timeTaken + ", Price : " + this.totalOrderPrice;
		return returnMeThis;
	}
}
