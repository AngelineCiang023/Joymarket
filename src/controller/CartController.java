package controller;

import model.ProductModel;
import model.CartModel;
import utils.ConnectDB;

import java.sql.ResultSet;

public class CartController {

	ProductModel productModel = new ProductModel();
	CartModel cartModel = new CartModel();

	// Add product to cart with validation
	public String addToCart(String customerId, String productId, int count) {
	    if (count < 1)
	        return "Count must be ≥ 1";

	    try {
	        ResultSet rs = productModel.getProductById(productId);
	        if (!rs.next())
	            return "Product does not exist";

	        int stock = rs.getInt("stock");
	        if (count > stock)
	            return "Not enough stock";

	        cartModel.addToCart(customerId, productId, count);
	        return "Item added to cart successfully";

	    } catch (Exception e) {
	        return "Error adding to cart";
	    }
	}
}
