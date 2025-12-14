package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Promo;
import utils.ConnectDB;

public class PromoDAO {
	
	private ConnectDB db = ConnectDB.getConnection();
	
	public Promo getPromoByCode (String code) {
		String query = "SELECT * FROM promo WHERE code = ?";
		
		try {
			PreparedStatement ps = db.prepare(query);
			ps.setString(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Promo(
					rs.getString("idPromo"),
					rs.getString("code"),
					rs.getString("headline"),
					rs.getInt("discountPercentage")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
