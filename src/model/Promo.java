package model;

public class Promo {
	private String idPromo;
	private String code;
	private String headline;
	private int discountPercentage;
	
	public Promo(String idPromo, String code, String headline, int discountPercentage) {
		super();
		this.idPromo = idPromo;
		this.code = code;
		this.headline = headline;
		this.discountPercentage = discountPercentage;
	}

	public String getIdPromo() {
		return idPromo;
	}

	public String getCode() {
		return code;
	}

	public String getHeadline() {
		return headline;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}
	
	
}
