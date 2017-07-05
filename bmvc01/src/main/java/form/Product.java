package form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {
	String prdID;
	String name;
	String category;
	
	public Product() {
	}

	public Product(String prdID, String name, String category) {
		super();
		this.prdID = prdID;
		this.name = name;
		this.category = category;
	}

	public String getPrdID() {
		return prdID;
	}

	public void setPrdID(String prdID) {
		this.prdID = prdID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
	
}
