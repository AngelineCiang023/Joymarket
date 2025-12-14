package model;

public class OrderDetail {
	private String idOrder;
	private String idProduct;
	private int quantity;
	
	public OrderDetail(String idOrder, String idProduct, int quantity) {
		super();
		this.idOrder = idOrder;
		this.idProduct = idProduct;
		this.quantity = quantity;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public int getQuantity() {
		return quantity;
	}
	
	
	
}
