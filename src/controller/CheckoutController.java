package controller;

import model.CartModel;
import model.OrderModel;
import utils.ConnectDB;

import java.sql.ResultSet;

public class CheckoutController {

	CartModel cartModel = new CartModel();
	OrderModel orderModel = new OrderModel();

	ConnectDB db = ConnectDB.getConnection();

	public String checkout(String customerId, String promoInput) {

		// Calculate total
		int total = cartModel.getCartTotal(customerId);

		if (total == 0)
			return "Cart is empty";

		// Validate promo
		if (!promoInput.isEmpty() && !orderModel.promoExists(promoInput)) {
			return "Promo does not exist";
		}

		// Check customer balance
		int balance = 0;

		try {
			String query = "SELECT balance FROM customer WHERE customer_id = '" + customerId + "'";
			db.rs = db.execQuery(query);

			if (db.rs.next())
				balance = db.rs.getInt("balance");

		} catch (Exception e) {
		}

		if (balance < total)
			return "Balance not enough";

		// Create order
		String orderId = "ORD" + System.currentTimeMillis();
		orderModel.createOrder(orderId, customerId, total, promoInput);

		// Reduce balance
		orderModel.reduceBalance(customerId, total);

		return "Checkout successful! Order ID: " + orderId;
	}
}
