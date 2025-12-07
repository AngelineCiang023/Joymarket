package view;

import java.util.Scanner;

import controller.CartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartItem;

public class CartView {


	public void render(Stage stage, String customerId) {
		CartController controller = new CartController();

		ObservableList<CartItem> cartData = FXCollections.observableArrayList();

		Label lblTitle = new Label("--- Add Product to Cart ---");

		Label lblProduct = new Label("Product ID:");
		TextField txtProduct = new TextField();

		Label lblCount = new Label("Count:");
		TextField txtCount = new TextField();

		Button btnAdd = new Button("Add");

		Label lblResult = new Label();

		// untuk tampilin table cart
		TableView<CartItem> table = new TableView<>();
		TableColumn<CartItem, String> colProduct = new TableColumn<>("Product ID");
		colProduct.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());

		TableColumn<CartItem, Integer> colCount = new TableColumn<>("Count");
		colCount.setCellValueFactory(cell -> cell.getValue().countProperty().asObject());

		table.getColumns().addAll(colProduct, colCount);
		table.setItems(cartData);

		btnAdd.setOnAction(e -> {
			String productId = txtProduct.getText();
			String countInput = txtCount.getText();

			int count;
			try {
				count = Integer.parseInt(countInput);

			} catch (NumberFormatException ex) {
				lblResult.setText("Count must be numeric");
				return;
			}
			String result = controller.addToCart(customerId, productId, count);
			lblResult.setText(result);
			
			if(result.equals("Item added to cart successfully")) {
			cartData.add(new CartItem(productId, count));
			}
		});

		Button btnNext = new Button("Go to Checkout");
		btnNext.setOnAction(e -> {
			CheckoutView checkout = new CheckoutView();
			checkout.render(new Stage(), customerId);
		});

		VBox layout = new VBox(10, lblTitle, lblProduct, txtProduct, lblCount, txtCount, btnAdd, lblResult, table,
				btnNext);
		layout.setPadding(new Insets(20));

		stage.setScene(new Scene(layout, 350, 350));
		stage.show();
	}
}
