package model;

public class OrderHeader {
	private String idOrder;
	private String idCustomer;
	private String idPromo;
	private String status;
	private String orderedAt;
	
	public OrderHeader(String idOrder, String idCustomer, String idPromo, String status, String orderedAt) {
		super();
		this.idOrder = idOrder;
		this.idCustomer = idCustomer;
		this.idPromo = idPromo;
		this.status = status;
		this.orderedAt = orderedAt;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public String getIdPromo() {
		return idPromo;
	}

	public String getStatus() {
		return status;
	}

	public String getOrderedAt() {
		return orderedAt;
	}
	
	
	
}
