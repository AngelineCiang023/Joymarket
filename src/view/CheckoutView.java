package view;

import java.util.Scanner;

import controller.CheckoutController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckoutView {

    CheckoutController controller = new CheckoutController();

    public void render(Stage stage, String customerId) {

        Label lblTitle = new Label("--- Checkout ---");

        Label lblPromo = new Label("Promo Code (optional):");
        TextField txtPromo = new TextField();

        Button btnCheckout = new Button("Checkout");

        Label lblResult = new Label();

        btnCheckout.setOnAction(e -> {
            String promo = txtPromo.getText();
            String result = controller.checkout(customerId, promo);
            lblResult.setText(result);
        });

        VBox layout = new VBox(10, lblTitle, lblPromo, txtPromo, btnCheckout, lblResult);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 300));
        stage.show();
    }
}
