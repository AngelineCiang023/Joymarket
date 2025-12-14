package dao;

import java.sql.*;
import java.util.ArrayList;

import model.Product;
import utils.ConnectDB;

public class ProductDAO {
	
	ConnectDB db = ConnectDB.getConnection();
	
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> list = new ArrayList<Product>();
		
		String query = "SELECT * FROM product";
		
		ResultSet rs = db.execQuery(query);
		
		try {
			while (rs.next()) {
			Product p = new Product(
				rs.getString("idProduct"),
				rs.getString("name"),
				rs.getDouble("price"),
				rs.getInt("stock"),
				rs.getString("category")
				);
				list.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public Product getProductId (String idProduct) {
		String query = "SELECT * FROM product WHERE idProduct = ?";
		
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idProduct);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Product(
					rs.getString("idProduct"),
					rs.getString("name"),
					rs.getDouble("price"),
					rs.getInt("stock"),
					rs.getString("category")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean updateProductStock (String idProduct, int newStock) {
		String query = "UPDATE product SET stock = ? WHERE idProduct = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setInt(1, newStock);
			ps.setString(2, idProduct);
			
			return ps.executeUpdate() > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean decreaseStock (String idProduct, int quantity) {
		String query = "UPDATE product SET stock = stock - ? WHERE idProduct = ? AND stock >= ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setInt(1, quantity);
			ps.setString(2, idProduct);
			ps.setInt(3, quantity);
			return ps.executeUpdate()  > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
