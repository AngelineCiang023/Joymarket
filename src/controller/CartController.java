package controller;

import dao.CartDAO;
import model.Product;
import dao.ProductDAO;

public class CartController {
	
	private ProductDAO productDAO = new ProductDAO();
	private CartDAO cartDAO = new CartDAO();
	
	public String addToCart (String idCustomer, String idProduct, String countStr) {
		
		if (countStr == null || countStr.trim().isEmpty()) return "Count must be filled";
		if (!isNumeric(countStr)) return "Count must be numeric";
		
		int count = Integer.parseInt(countStr);
		if (count < 1) return "Count must be at least 1";
		
		Product p = productDAO.getProductId(idProduct);
		if (p == null) return "Product not found";
		
		int stock = p.getStock();
		int currentCount = cartDAO.getCartItemCount(idCustomer, idProduct);
		int newCount = currentCount + count;
		if (newCount > stock) return "Not enough stock. Available: " + stock;
		
		boolean success;
		if (currentCount == 0 ) {
			success = cartDAO.insertCartItem(idCustomer, idProduct, count);
		} else {
			success = cartDAO.updateCartItem(idCustomer, idProduct, newCount);
		}
		
		if (!success) return "Failed to store cart item";
		
		return "OK";
	}
	
	public String updateCartItemCount (String idCustomer, String idProduct, String newCountStr) {
		if (newCountStr == null || newCountStr.trim().isEmpty()) return "Count must be filled";
		if (!isNumeric(newCountStr)) return "Count must be numeric";
		
		int newCount = Integer.parseInt(newCountStr);
		if (newCount < 1) return "Count must be at least 1";
		
		Product p = productDAO.getProductId(idProduct);
		if (p == null) return "Product not found";
		
		int stock = p.getStock();
		if (newCount > stock) return "Not enough stock. Available: " + stock;
		
		boolean ok = cartDAO.updateCartItem(idCustomer, idProduct, newCount);
		
		return ok ? "OK" : "Failed to update cart";
	}
	
	public String removeCartItem (String idCustomer, String idProduct) {
		int currentCount = cartDAO.getCartItemCount(idCustomer, idProduct);
		if (currentCount == 0) return "Item not found in cart";
		
		boolean ok = cartDAO.deleteCartItem(idCustomer, idProduct);
		
		return ok ? "OK" : "Failed to delete cart item";
	}
	
	private boolean isNumeric (String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) return false;
		}
		
		return true;
	}
}
