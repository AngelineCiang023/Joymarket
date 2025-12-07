package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CartItem {
	private StringProperty productId;
	private IntegerProperty count;
	
	public CartItem(String productId, int count) {
		this.productId = new SimpleStringProperty(productId);
		this.count = new SimpleIntegerProperty(count);
	}

	public StringProperty productIdProperty() {
		return productId;
	}
	
	public IntegerProperty countProperty() {
		return count;
	}
	

	public String getProductId() {
		return productId.get();
	}

	public void setProductId(String productId) {
		this.productId.set(productId);;
	}

	public Integer getCount() {
		return count.get();
	}

	public void setCount(Integer count) {
		this.count.set(count);;
	}
	
	

	
	
	
}
