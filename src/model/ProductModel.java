package model;

import java.sql.ResultSet;

import utils.ConnectDB;

public class ProductModel {
	ConnectDB db = ConnectDB.getConnection();

	public ResultSet getProductById(String productId) {
		String query = "SELECT * FROM product WHERE idProduct = '" + productId + "'";
		return db.execQuery(query);
	}
}
