package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.OrderHistoryRow;
import utils.ConnectDB;

public class OrderHistoryDAO {
    private ConnectDB db = ConnectDB.getConnection();

    public List<OrderHistoryRow> getHistory(String idCustomer) {
        List<OrderHistoryRow> list = new ArrayList<>();

        String q =
        	    "SELECT " +
        	    "  oh.idOrder, " +
        	    "  DATE_FORMAT(oh.orderedAt, '%Y-%m-%d %H:%i') AS orderedAt, " +
        	    "  oh.status, " +
        	    "  pr.code AS promoCode, " +
        	    "  pr.headline, " +
        	    "  COALESCE(pr.discountPercentage, 0) AS discountPercentage, " +
        	    "  SUM(p.price * od.qty) AS subtotal, " +
        	    "  SUM(p.price * od.qty) * (1 - COALESCE(pr.discountPercentage,0)/100.0) AS total " +
        	    "FROM orderheader oh " +
        	    "JOIN orderdetail od ON od.idOrder = oh.idOrder " +
        	    "JOIN product p ON p.idProduct = od.idProduct " +
        	    "LEFT JOIN promo pr ON pr.idPromo = oh.idPromo " +
        	    "WHERE oh.idCustomer = ? " +
        	    "GROUP BY oh.idOrder, oh.orderedAt, oh.status, pr.code, pr.headline, pr.discountPercentage " +
        	    "ORDER BY oh.orderedAt DESC";


        try {
            PreparedStatement ps = db.prepare(q);
            ps.setString(1, idCustomer);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OrderHistoryRow(
                    rs.getString("idOrder"),
                    rs.getString("orderedAt"),
                    rs.getString("status"),
                    rs.getString("promoCode"),
                    rs.getString("headline"),
                    rs.getInt("discountPercentage"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
}
