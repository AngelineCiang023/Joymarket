module JoyMarket {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    
    opens main to javafx.graphics, javafx.fxml;
    opens view to javafx.fxml;
    opens controller to javafx.fxml;
}
