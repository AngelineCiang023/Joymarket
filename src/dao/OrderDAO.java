package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.ConnectDB;

public class OrderDAO {
	
	private ConnectDB db = ConnectDB.getConnection();
	
	public String generateOrderId() {
		String query = "SELECT idOrder FROM orderheader ORDER BY idOrder DESC LIMIT 1";
		
		try {
			ResultSet rs = db.execQuery(query);
			if (rs.next()) {
				String last = rs.getString("idOrder");
				int num = Integer.parseInt(last.substring(2));
				
				return String.format("OR%03d", num + 1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "OR001";
	}
	
	public String insertOrderHeader (String idCustomer, String idPromo, String status) {
		String idOrder = generateOrderId();
		
		String query = "INSERT INTO orderheader (idOrder, idCustomer, idPromo, status, orderedAt) VALUES (?, ?, ?, ?, NOW())";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idOrder);
			ps.setString(2, idCustomer);
			
			if (idPromo == null || idPromo.trim().isEmpty()) {
				ps.setNull(3, java.sql.Types.VARCHAR);
			} else {
				ps.setString(3, idPromo);
			}
			
			ps.setString(4, status);
			
			int rows = ps.executeUpdate();
			
			return rows > 0 ? idOrder : null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean insertOrderDetail (String idOrder, String idProduct, int qty) {
		String query = "INSERT INTO orderdetail (idOrder, idProduct, qty) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idOrder);
			ps.setString(2, idProduct);
			ps.setInt(3, qty);
			
			return ps.executeUpdate() > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
