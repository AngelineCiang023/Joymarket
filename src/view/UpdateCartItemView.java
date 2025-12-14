package view;

import controller.CartController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CartItem;
import model.User;

public class UpdateCartItemView {
	
	private Scene scene;
	
	private Stage priamryStage;
	
	private User loggedUser;
	private CartItem cartItem;
	private CartController cartController;
	
	private Label titleLabel;
	private Label productLabel;
	private Label priceLabel;
	private Label countLabel;
	
	private TextField countField;
	
	private Button saveButton;
	private Button cancelButton;
	
	private Alert alert;
	
	private VBox root;
	
	public UpdateCartItemView (Stage primaryStage, User loggedUser, CartItem cartItem) {
		this.priamryStage = primaryStage;
		this.loggedUser = loggedUser;
		this.cartItem = cartItem;
		this.cartController = new CartController();
		
		//
		
		titleLabel = new Label("--- Update Cart Item ---");
		
		//
		
		productLabel = new Label("Product: " + cartItem.getProductName());
		priceLabel = new Label("Price: " + cartItem.getFormattedPrice());
		countLabel = new Label("Count :");
		countField = new TextField(String.valueOf(cartItem.getCount()));
		
		//
		
		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		
		saveButton.setOnAction(e -> {
			String result = cartController.updateCartItemCount(
					loggedUser.getIdUser(),
					cartItem.getIdProduct(),
					countField.getText()
			);
			
			if ("OK".equals(result)) {
				showAlert("Success", "Cart item updated");
				CartListView cartView = new CartListView(primaryStage, loggedUser);
				primaryStage.setScene(cartView.getScene());
			} else {
				showAlert("Error", result);
			}
			
		});
		
		cancelButton.setOnAction(e -> {
			CartListView cartView = new CartListView(primaryStage, loggedUser);
			primaryStage.setScene(cartView.getScene());
		});
		
		//
		
		root = new VBox(10, 
			titleLabel,
			productLabel,
			priceLabel,
			countLabel,
			countField,
			saveButton,
			cancelButton
		);
		
		root.setPadding(new Insets(20));
		
		scene = new Scene(root, 350, 250);
		
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
