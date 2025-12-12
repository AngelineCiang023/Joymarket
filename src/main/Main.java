package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import dao.UserDAO;
import view.ProductListView;

public class Main  extends Application{
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		UserDAO userDAO = new UserDAO();
		User loggedUser = userDAO.getUserById("C001");
		
		if (loggedUser == null) {
			System.out.println("User tidak ditenmukan");
			return;
		}
		
		ProductListView listView = new ProductListView(primaryStage, loggedUser);
		
		primaryStage.setScene(listView.getScene());
		primaryStage.setTitle("Joymarket");
		primaryStage.show();
		
	}
}
