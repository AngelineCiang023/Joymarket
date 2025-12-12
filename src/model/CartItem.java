package model;

public class CartItem {
	private String idCustomer;
	private String idProduct;
	private int count;
	
	private String productName;
	private double productPrice;
	
	public CartItem(String idCustomer, String idProduct, int count, String productName, double productPrice) {
		super();
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.count = count;
		this.productName = productName;
		this.productPrice = productPrice;
	}
	
	public String getIdCustomer() {
		return idCustomer;
	}
	
	public String getIdProduct() {
		return idProduct;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double getProductPrice() {
		return productPrice;
	}
	
//	
	
	public String getFormattedPrice() {
		return "Rp " + String.format("%,.0f", productPrice);
	}
	
	public String getFormattedTotalPrice() {
		return "Rp " + String.format("%,.0f", productPrice * count);
	}
	
}
