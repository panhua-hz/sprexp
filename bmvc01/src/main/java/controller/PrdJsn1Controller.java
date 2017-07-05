package controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import form.Product;
import services.ProductService;

@Controller
@RequestMapping("/prd")
public class PrdJsn1Controller {
	@Autowired
	ProductService productService;
	
	@RequestMapping(method = GET)
	public @ResponseBody List<Product> getProductJson(){
		return this.productService.getAllProducts();
	}
}
