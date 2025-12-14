package controller;

import dao.CustomerDAO;

public class CustomerController {

	private CustomerDAO customerDAO = new CustomerDAO();
	
	public String topUpBalance (String idCustomer, String amountStr) {
		if (amountStr == null || amountStr.trim().isEmpty()) return "Amount must be filled";
		
		if (!isNumeric(amountStr)) return "Amount must be numeric";
		
		double amount = Double.parseDouble(amountStr);
		if (amount <= 0) return "Amount must be greater than 0";
		
		boolean ok = customerDAO.topUp(idCustomer, amount);
		return ok ? "OK" : "Failed to top up";
		
	}
	
	private boolean isNumeric (String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			if (!Character.isDigit(c)) return false;
		}
		
		return true;
	}
	
}
