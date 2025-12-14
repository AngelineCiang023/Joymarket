package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "joymarket";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

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
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = conn.createStatement();
            System.out.println("DB Connected");
        } catch (SQLException e) {
            System.out.println("DB Connection Failed!");
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void beginTransaction() throws SQLException {
        getConn().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        getConn().commit();
        getConn().setAutoCommit(true);
    }

    public void rollback() {
        try {
            getConn().rollback();
            getConn().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        try {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int execUpdate(String query) {
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public PreparedStatement prepare(String query) {
        try {
            ps = getConn().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
}
