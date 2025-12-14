package model;

public class OrderHistoryRow {
	private String idOrder;
	private String orderedAt;
	private String status;
	private String promoCode;
	private String promoHeadline;
	private int discountPercetange;
	private double subtotal;
	private double total;
	
	public OrderHistoryRow(String idOrder, String orderedAt, String status, String promoCode, String promoHeadline,
			int discountPercetange, double subtotal, double total) {
		super();
		this.idOrder = idOrder;
		this.orderedAt = orderedAt;
		this.status = status;
		this.promoCode = promoCode;
		this.promoHeadline = promoHeadline;
		this.discountPercetange = discountPercetange;
		this.subtotal = subtotal;
		this.total = total;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public String getOrderedAt() {
		return orderedAt;
	}

	public String getStatus() {
		return status;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public String getPromoHeadline() {
		return promoHeadline;
	}

	public int getDiscountPercetange() {
		return discountPercetange;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getTotal() {
		return total;
	}
	
//
	
	public String getFormattedSubtotal() {
		return "Rp " + String.format("%,.0f", subtotal);
	}
	
	public String getFormattedTotal() {
		return "Rp " + String.format("%,.0f", total);
	}
	
}
