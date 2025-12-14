package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utils.ConnectDB;

public class CustomerDAO {
	ConnectDB db = ConnectDB.getConnection();
	
	public double getBalance (String idCustomer) {
		String query = "SELECT balance FROM customer WHERE idCustomer = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, idCustomer);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getDouble("balance");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean topUp (String idCustomer, double amount) {
		String query = "UPDATE customer SET balance = balance + ? WHERE idCustomer = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setDouble(1, amount);
			ps.setString(2, idCustomer);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean decreaseBalance (String idCustomer, double amount) {
		String query = "UPDATE customer SET balance = balance - ? WHERE idCustomer = ? AND balance >= ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setDouble(1, amount);
			ps.setString(2, idCustomer);
			ps.setDouble(3, amount);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
