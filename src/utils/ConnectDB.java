package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

	private String USERNAME = "root";
	private String PASSWORD = "";
	private String DATABASE = "joymarket";
	private String HOST = "localhost:3306";
	private String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private Connection conn;
	private Statement st;
	private PreparedStatement ps;
	public ResultSet rs;

	private static ConnectDB connectDB;

	public static ConnectDB getConnection() {
		if (connectDB == null) {
			connectDB = new ConnectDB();
		}
		return connectDB;
	}

	private ConnectDB() {
	    try {
	        conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
	        st = conn.createStatement();
	        System.out.println("DB Connected");
	    } 
	    catch (SQLException e) {
	        System.out.println("DB Connection Failed!");
	        e.printStackTrace();   // tampilkan error tapi jangan crash
	    }
	}

	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PreparedStatement prepare(String query) {
		try {
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}
}
