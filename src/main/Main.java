package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.CartView;
//import view.CheckoutView;

public class Main  extends Application{
	public static void main(String[] args) {

		launch(args);

//		CheckoutView checkout = new CheckoutView();
//		checkout.render(sampleCustomer);
	}

	@Override
	 public void start(Stage primaryStage) throws Exception {
        String sampleCustomer = "CUST001";

        CartView cart = new CartView();
        cart.render(primaryStage, sampleCustomer); // Tampilkan Cart disini
		
	}
}
