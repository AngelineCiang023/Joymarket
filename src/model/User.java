package model;

public class User {
	private String idUser;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String role;
	
	public User() {
		
	}
	
	public User(String idUser, String fullName, String email, String password, String phone, String address,
			String role) {
		super();
		this.idUser = idUser;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getFullName() {
		return fullName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getRole() {
		return role;
	}
	
	
	
}
