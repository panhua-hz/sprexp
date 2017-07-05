package client;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import form.Product;

public class RestClientTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		RestTemplate rest = new RestTemplate();
		List<LinkedHashMap<String,?>> list = rest.getForObject("http://localhost:8080/bmvc/prd", List.class);
		list.stream()
			.map(item->{
				return new Product(
						(String)item.get("prdID"), 
						(String)item.get("name"),
						(String)item.get("category"));})
			.forEach(System.out::println);
	}

}
