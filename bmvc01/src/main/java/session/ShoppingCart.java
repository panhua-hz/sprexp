package session;

import java.util.List;

public interface ShoppingCart {
	public void addProduct(String prodName);
	public List<String> listAllProd();
}
