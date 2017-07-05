package session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,
	proxyMode=ScopedProxyMode.INTERFACES)

public class ShoppingCartImpl implements ShoppingCart {
	private List<String> prodList;
	
	public ShoppingCartImpl(){
		System.out.println("ShoppingCartImpl Construction");
		prodList = new ArrayList<>(); //Have thread issue!!!!!
	}
	
	@Override
	public void addProduct(String prodName) {
		System.out.println("Add Prod: "+prodName);
		prodList.add(prodName);
	}

	@Override
	public List<String> listAllProd() {
		System.out.println("Contains prod size: "+prodList.size());
		return prodList;
	}

}
