package dao;

import java.sql.ResultSet;

import model.User;
import utils.ConnectDB;

public class UserDAO {
	
	private ConnectDB db = ConnectDB.getConnection();
	
	public User getUserById (String idUser) {
		String query = "SELECT * FROM users WHERE idUser = '" + idUser + "'";
		
		ResultSet rs = db.execQuery(query);
		
		try {
			if (rs.next()) {
				return new User(
					rs.getString("idUser"),
					rs.getString("fullName"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("phone"),
					rs.getString("address"),
					rs.getString("role")
					);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}