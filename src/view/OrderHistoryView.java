package view;

import java.util.List;

import dao.OrderHistoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.OrderHistoryRow;
import model.User;

public class OrderHistoryView {
	
    private Scene scene;
    
    private Label titleLabel;
    
    private TableView<OrderHistoryRow> table;
    
    private Button backButton;
    
    private HBox bottom;
    
    private VBox root;

    public OrderHistoryView(Stage primaryStage, User loggedUser) {
    	
    	titleLabel = new Label("--- Order History ---");
    	
    	//

        table = new TableView<>();

        TableColumn<OrderHistoryRow, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idOrder"));

        TableColumn<OrderHistoryRow, String> dateCol = new TableColumn<>("Ordered At");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderedAt"));

        TableColumn<OrderHistoryRow, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<OrderHistoryRow, String> promoCol = new TableColumn<>("Promo");
        promoCol.setCellValueFactory(new PropertyValueFactory<>("promoCode"));

        TableColumn<OrderHistoryRow, String> subCol = new TableColumn<>("Subtotal");
        subCol.setCellValueFactory(new PropertyValueFactory<>("formattedSubtotal"));

        TableColumn<OrderHistoryRow, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("formattedTotal"));

        table.getColumns().addAll(idCol, dateCol, statusCol, promoCol, subCol, totalCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //
        
        OrderHistoryDAO dao = new OrderHistoryDAO();
        List<OrderHistoryRow> rows = dao.getHistory(loggedUser.getIdUser());

        ObservableList<OrderHistoryRow> data = FXCollections.observableArrayList(rows);
        table.setItems(data);
        
        //

        backButton = new Button("Back");
        backButton.setOnAction(e -> {
        	CartListView cartListView = new CartListView(primaryStage, loggedUser);
        	primaryStage.setScene(cartListView.getScene());
        }); 

        bottom = new HBox(10, backButton);
        
        //

        root = new VBox(10, titleLabel, table, bottom);
        root.setPadding(new Insets(20));
        scene = new Scene(root, 850, 450);
        
        //
        
        table.setPlaceholder(new Label("No Order History Available"));
    }

    public Scene getScene() { 
    	return scene; 
    }
}
