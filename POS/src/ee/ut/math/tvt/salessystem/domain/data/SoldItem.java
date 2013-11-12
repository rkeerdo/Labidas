package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name="PUBLIC.SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	@Transient
    private StockItem stockItem;
    @Column(name="NAME")
    private String name;
    @Column(name="QUANTITY")
    private Integer quantity;
    @Column(name="ITEMPRICE")
    private double price;
    @Transient
    private HistoryItem historyItem;
    
    public SoldItem(StockItem stockItem, int quantity) {
    	this.id = stockItem.getId();
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        
    }
    
    public HistoryItem getHistoryItem(){
    	return this.historyItem;
    }
    public void setHistoryItem(HistoryItem item){
    	this.historyItem=item;
    }
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
}
