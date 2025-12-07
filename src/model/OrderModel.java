package model;

import utils.ConnectDB;

public class OrderModel {
	ConnectDB db = ConnectDB.getConnection();

	public void createOrder(String orderId, String customerId, int total, String promoCode) {
		String query = String.format(
				"INSERT INTO orders(idOrder, idCustomer, idPromo, status, orderedAt, totalAmount) " + "VALUES('%s', '%s', '%s', 'Pending', NOW(), %d)", orderId,
				orderId, customerId, promoCode, total);
		db.execUpdate(query);
	}

	public boolean promoExists(String promoCode) {

		String query = "SELECT * FROM promo WHERE promo_code = '" + promoCode + "'";
		db.rs = db.execQuery(query);

		try {
			return db.rs.next();
		} catch (Exception e) {
			return false;
		}
	}

	public void reduceBalance(String customerId, int total) {
		String q = String.format("UPDATE customer SET balance = balance - %d WHERE idCustomer = '%s'", total,
				customerId);
		db.execUpdate(q);
	}
}