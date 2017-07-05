package services;

import java.util.List;

import form.Product;

public interface ProductService {
	List<Product> getAllProducts();
	default Product save(Product p){
		System.out.println(p.toString());
		return p;
	}
	
}
