package view;

import java.util.List;

import dao.ProductDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import model.User;

public class ProductListView {
	private Scene scene;
	
	private User loggedUser;
	private Stage primaryStage;
	
	private Button cartButton;
	
	private TableView<Product> table;
	
	private VBox root;
	
	public ProductListView (Stage primaryStage, User loggedUser) {
		this.primaryStage = primaryStage;
		this.loggedUser = loggedUser;
		
		cartButton = new Button("View Cart");
		cartButton.setOnAction(e -> {
			CartListView cartView = new CartListView(primaryStage, loggedUser);
			primaryStage.setScene(cartView.getScene());
		});
		
		table = new TableView<>();
		
		// Table Product
		TableColumn<Product, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
		
		TableColumn<Product, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("formattedPrice"));
		
		TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		
		TableColumn<Product, Void> actionCol = new TableColumn<>("Action");
		actionCol.setCellFactory(col -> new TableCell<>() {
			
			private final Button buyButton = new Button("Buy");
			
			{
				buyButton.setOnAction (e -> {
					Product selected = getTableView().getItems().get(getIndex());
					
					// Redirect ke page AddProductToCartView
					AddProductToCartView formView = new AddProductToCartView(primaryStage, loggedUser, selected);
					primaryStage.setScene(formView.getScene());
				});
			}
			
			protected void updateItem (Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(buyButton);
				}
			}
			
		});
		
		table.getColumns().addAll(idCol, nameCol, priceCol, stockCol, actionCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// Load data dari ProductDAO
		ProductDAO productDAO = new ProductDAO();
		List<Product> productList = productDAO.getAllProducts();
		ObservableList<Product> data = FXCollections.observableArrayList(productList);
		table.setItems(data);
		
		root = new VBox(10, cartButton, table);
		root.setPadding(new Insets(20));
		
		scene = new Scene(root, 600, 400);
	}
	
	public Scene getScene() {
		return scene;
	}
}
