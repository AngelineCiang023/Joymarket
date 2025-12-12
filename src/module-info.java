module test {
	requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    
    opens main;
    opens view;
    opens controller;
    opens model;
    opens dao;
}