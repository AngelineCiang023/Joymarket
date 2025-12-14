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
import model.Product;
import model.User;

public class AddProductToCartView {

    private Scene scene;
    private Stage primaryStage;
    private User loggedUser;
    private Product selectedProduct;
    private CartController cartController;
    
    private Button backButton;
    private Button addButton;
    
    private Label titleLabel;
    private Label nameLabel;
    private Label priceLabel;
    private Label stockLabel;
    private Label countLabel;
    
    private VBox root;
    
    private Alert alert;

    public AddProductToCartView(Stage primaryStage, User loggedUser, Product selectedProduct) {
    	this.primaryStage = primaryStage;
        this.loggedUser = loggedUser;
        this.selectedProduct = selectedProduct;
        this.cartController = new CartController();
        
        //
        
        backButton = new Button("Back");
        
        backButton.setOnAction (e -> {
        	ProductListView listView = new ProductListView (primaryStage, loggedUser);
        	primaryStage.setScene(listView.getScene());
        });
        
        //

        titleLabel = new Label("--- Add Product to Cart ---");
        nameLabel  = new Label("Product: " + selectedProduct.getName());
        priceLabel = new Label("Price  : " + selectedProduct.getFormattedPrice());
        stockLabel = new Label("Stock  : " + selectedProduct.getStock());
        countLabel = new Label("Count:");
        
        TextField countTextField = new TextField();
        countTextField.setPromptText("Enter quantity");
        
        //

        addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String idCustomer = loggedUser.getIdUser();
            String idProduct  = selectedProduct.getIdProduct();
            String countStr   = countTextField.getText();

            String result = cartController.addToCart(idCustomer, idProduct, countStr);

            if (result.equals("OK")) {
                showAlert("Success", "Item added to cart!");
                
                CartListView cartView = new CartListView(primaryStage, loggedUser);
                primaryStage.setScene(cartView.getScene());
            } else {
                showAlert("Error", result);
            }
        });
        
        //

        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
        		backButton,
                titleLabel,
                nameLabel,
                priceLabel,
                stockLabel,
                countLabel,
                countTextField,
                addButton
        );

        scene = new Scene(root, 350, 270);
    }

    public Scene getScene() {
        return scene;
    }

    private void showAlert(String title, String msg) {
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
