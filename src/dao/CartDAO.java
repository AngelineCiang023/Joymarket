package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import utils.ConnectDB;

public class CartDAO {
	
	ConnectDB db = ConnectDB.getConnection();
	
	public int getCartItemCount (String idCustomer, String idProduct) {
		String query = "SELECT count FROM cartitem WHERE idCustomer = ? AND idProduct = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			
			ps.setString(1, idCustomer);
			ps.setString(2, idProduct);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean insertCartItem (String idCustomer, String idProduct, int count) {
		String query = "INSERT INTO cartitem(idCustomer, idProduct, count) VALUES(?, ?, ?)";
		
		try {
			PreparedStatement ps = db.prepare(query);
			
			ps.setString(1, idCustomer);
			ps.setString(2, idProduct);
			ps.setInt(3, count);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateCartItem (String idCustomer, String idProduct, int count) {
		String query = "UPDATE cartitem SET count = ? WHERE idCustomer = ? AND idProduct = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			
			ps.setInt(1, count);
			ps.setString(2, idCustomer);
			ps.setString(3, idProduct);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List<CartItem> getCartItemsByCustomer (String idCustomer) {
		List<CartItem> list = new ArrayList<>();
		
		String query = "SELECT c.idCustomer, c.idProduct, c.count, p.name, p.price " +
						"FROM cartitem c " +
						"JOIN product p ON c.idProduct = p.idProduct " +
						"WHERE c.idCustomer = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idCustomer);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CartItem item = new CartItem(
						rs.getString("idCustomer"), 
						rs.getString("idProduct"), 
						rs.getInt("count"), 
						rs.getString("name"), 
						rs.getDouble("price")
				);
				list.add(item);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean deleteCartItem (String idCustomer, String idProduct) {
		String query = "DELETE FROM cartitem WHERE idCustomer = ? AND idProduct = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idCustomer);
			ps.setString(2, idProduct);
			
			return ps.executeUpdate() > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}

