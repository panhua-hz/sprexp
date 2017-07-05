package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import form.Product;

@Component
public class ProductServiceImpl implements ProductService {

	public static Product[] productList = new Product[]{
		new Product("b001","Java Core", "Book"),
		new Product("b002","C# example", "Book"),
		new Product("c001","Golden fish", "Food"),
		new Product("d002","black berry", "Phone"),
		new Product("c002","Berry", "Fruit") 	
	};
	
	@Override
	public List<Product> getAllProducts() {
		return Arrays.asList(productList);
	}

}
