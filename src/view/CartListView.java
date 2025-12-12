package view;

import java.util.List;

import controller.CartController;
import dao.CartDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartItem;
import model.Product;
import model.User;

public class CartListView {
	
	private Scene scene;
	
	private TableView<CartItem> table;
	
	private Button backButton;
	
	private HBox buttonBar;
	
	private VBox root;
	
	private Alert alert;
	
	public CartListView (Stage primaryStage, User loggedUser) {
		table = new TableView<>();
		
		TableColumn<CartItem, String> nameCol = new TableColumn<>("Product");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		
		TableColumn<CartItem, Integer> countCol = new TableColumn<>("Count");
		countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
		
		TableColumn<CartItem, String> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("formattedPrice"));
		
		TableColumn<CartItem, String> totalCol = new TableColumn<>("Total");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("formattedTotalPrice"));
		
		TableColumn<CartItem, Void> actionCol = new TableColumn<>("Action");
		actionCol.setCellFactory(col -> new TableCell<>() {
			
			private final Button updateButton = new Button("Update");
			private final Button removeButton = new Button("Remove");
			private final HBox box = new HBox(5, updateButton, removeButton);
			private final CartController cartController = new CartController();
			
			{
				updateButton.setOnAction(e -> {
					CartItem item = getTableView().getItems().get(getIndex());
					UpdateCartItemView updateView = new UpdateCartItemView(primaryStage, loggedUser, item);
					primaryStage.setScene(updateView.getScene());
				});
				
				removeButton.setOnAction(e -> {
					CartItem item = getTableView().getItems().get(getIndex());
					String result = cartController.removeCartItem(loggedUser.getIdUser(), item.getIdProduct());
					
					if ("OK".equals(result)) {
						getTableView().getItems().remove(item);
						showAlert("Success", "Item removed from cart");
					} else {
						showAlert("Error", result);
					}
				});
			}
			
			protected void updateItem (Void v, boolean empty) {
				super.updateItem(v, empty);
				setText(null);
				setGraphic(empty ? null : box);
			}
			
		});
		
		table.getColumns().addAll(nameCol, countCol, priceCol, totalCol, actionCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		CartDAO cartDAO = new CartDAO();
		List<CartItem> items = cartDAO.getCartItemsByCustomer(loggedUser.getIdUser());
		ObservableList<CartItem> data = FXCollections.observableArrayList(items);
		table.setItems(data);
		
		backButton = new Button("Back to Products");
		backButton.setOnAction(e -> {
			ProductListView listView = new ProductListView(primaryStage, loggedUser);
			primaryStage.setScene(listView.getScene());
		});
		
		buttonBar = new HBox(10, backButton);
		
		root = new VBox(10, table, buttonBar);
		root.setPadding(new Insets(20));
		
		scene = new Scene(root, 900, 400);
		
	}
	
	public Scene getScene() {
		return scene;
	}
	
	private void showAlert (String title, String msg) {
		Alert.AlertType type = title.equalsIgnoreCase("Error")
				? Alert.AlertType.ERROR
				: Alert.AlertType.INFORMATION;
		
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
}
