package view;

import controller.CustomerController;
import dao.CustomerDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class TopUpView {
	
	private Scene scene;
	
	private Label titleLabel;
	private Label balanceLabel;
	private Label amountLabel;
	
	private TextField amountField;
	
	private Button topUpButton;
	private Button backButton;
	
	private VBox root;
	
	private Alert alert;
	
	public TopUpView (Stage primaryStage, User loggedUser) {
		CustomerDAO customerDAO = new CustomerDAO();
		CustomerController customerController = new CustomerController();
		
		//
		
		titleLabel = new Label("--- Top Up Balance ---");
		
		//
		
		double balance = customerDAO.getBalance(loggedUser.getIdUser());
		balanceLabel = new Label("Current Balance: Rp " + String.format("%,.0f", balance));
		
		amountLabel = new Label("Top Up Amount: ");
		amountField = new TextField();
		amountField.setPromptText("Enter amount (e.g. 10000)");
		
		//
		
		topUpButton = new Button("Top Up");
		backButton = new Button("Back");
		
		topUpButton.setOnAction(e -> {
			String result = customerController.topUpBalance(loggedUser.getIdUser(), amountField.getText());
			
			if ("OK".equals(result)) {
				showAlert("Success", "Top up successful");
				
				CartListView cartView = new CartListView(primaryStage, loggedUser);
				primaryStage.setScene(cartView.getScene());
			} else {
				showAlert("Error", result);
			}
		});
		
		backButton.setOnAction(e -> {
			CartListView cartView = new CartListView(primaryStage, loggedUser);
			primaryStage.setScene(cartView.getScene());
		});
		
		//
		
		root = new VBox(10,
				titleLabel,
				backButton,
				balanceLabel,
				amountLabel,
				amountField,
				topUpButton);
		
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
