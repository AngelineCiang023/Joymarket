package controller;

import java.util.List;

import dao.CartDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.PromoDAO;
import model.CartItem;
import model.Product;
import model.Promo;
import utils.ConnectDB;

public class CheckoutController {
	
	private CartDAO cartDAO = new CartDAO();
	private ProductDAO productDAO = new ProductDAO();
	private CustomerDAO customerDAO = new CustomerDAO();
	private PromoDAO promoDAO = new PromoDAO();
	private OrderDAO orderDAO = new OrderDAO();
	
	public String checkout (String idCustomer, String promoCodeInput) {
		
		List<CartItem> items = cartDAO.getCartItemsByCustomer(idCustomer);
		if (items == null || items.isEmpty()) return "No Cart Item Available";
		
		double subtotal = 0;
		for (CartItem it : items) {
			subtotal += it.getProductPrice() * it.getCount();
		}
		
		String idPromo = null;
		int discountPercent = 0;
		if (promoCodeInput != null && !promoCodeInput.trim().isEmpty()) {
			Promo promo = promoDAO.getPromoByCode(promoCodeInput.trim());
			if (promo == null) return "Invalid Promo Code";
			
			discountPercent = promo.getDiscountPercentage();
			if (discountPercent < 0 || discountPercent > 100) return "Invalid promo percentage";
			
			idPromo = promo.getIdPromo();
		}
		
		double total = subtotal - (subtotal * discountPercent / 100.0);
		if (total < 0) total = 0;
		
		double balance = customerDAO.getBalance(idCustomer);
		if (balance < total) return "Insufficient Balance";
		
		for (CartItem it : items) {
			Product p = productDAO.getProductId(it.getIdProduct());
			if (p == null) return "Product not found: " + it.getIdProduct();
			if (p.getStock() < it.getCount()) return "Not enough stock for " + p.getName(); 
		}
		
		ConnectDB db = ConnectDB.getConnection();
		try {
			db.getConn().setAutoCommit(false);
			
			String idOrder = orderDAO.insertOrderHeader(idCustomer, idPromo, "SUCCESS");
			if (idOrder == null) {
				db.getConn().rollback();
				return "Failed to create order";
			}
			
			for (CartItem it : items) {
				boolean detailOk = orderDAO.insertOrderDetail(idOrder, it.getIdProduct(), it.getCount());				
				if (!detailOk) {
					db.getConn().rollback();
					return "Failed to create order detail";
				}
				
				boolean stockOk = productDAO.decreaseStock(it.getIdProduct(), it.getCount());
				if (!stockOk) {
					db.getConn().rollback();
					return "Stock update failed";
				}
			}
			
			boolean balanceOk = customerDAO.decreaseBalance(idCustomer, total);
			if (!balanceOk) {
				db.getConn().rollback();
				return "Balance update failed";
			}
			
			boolean clearOk = cartDAO.clearCart(idCustomer);
			if (!clearOk) {
				db.getConn().rollback();
				return "Failed to clear cart";
			}
			
			db.getConn().commit();
			return "OK";
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				db.getConn().rollback();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				db.getConn().setAutoCommit(true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
}
