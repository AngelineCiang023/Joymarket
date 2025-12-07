package model;

import utils.ConnectDB;

public class CartModel {
	ConnectDB db = ConnectDB.getConnection();

	public void addToCart(String customerId, String productId, int count) {
		String query = String.format("INSERT INTO cart(idCustomer, idProduct, count) VALUES('%s','%s', %d)",
				customerId, productId, count);
		db.execUpdate(query);
	}

	public void UpdateCartItem(String customerId, String productId, int count) {
		String query = String.format("UPDATE cart SET count = %d WHERE customer_id = '%s' AND product_id = '%s'", count,
				customerId, productId);
		db.execUpdate(query);
	}

	public void updateCartItem(String customerId, String productId, int count) {
		String query = String.format("UPDATE cart SET count = %d WHERE customer_id = '%s' AND product_id = '%s'", count,
				customerId, productId);
		db.execUpdate(query);
	}
	public int getCartTotal(String customerId) {
		int total = 0;

		try {
			String query = String.format("SELECT SUM(c.count * p.price) AS total "
					+ "FROM cart c JOIN product p ON c.idProduct = p.idProduct" 
					+ "WHERE c.idCustomer = '%s'",
					customerId);

			db.rs = db.execQuery(query);

			if (db.rs.next())
				total = db.rs.getInt("total");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return total;
	}
}