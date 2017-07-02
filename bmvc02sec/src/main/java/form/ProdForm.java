package form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ProdForm {
	@NotBlank
	@Size(min = 1, max = 20, message="{prodForm.prodName.size}")
	private String prodName;
	
	public ProdForm(){
		
	}
	public ProdForm(String prodName){
		this.prodName = prodName;
	}
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
}
