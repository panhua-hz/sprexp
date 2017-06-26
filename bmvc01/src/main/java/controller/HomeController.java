package controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import form.ProdForm;
import session.ShoppingCart;

@Controller
@RequestMapping("/")
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ShoppingCart shoppingCart;
	
	@RequestMapping(method = GET)
    public String home(Model model) {
        logger.info("--->into home......");
        ProdForm prodForm = new ProdForm();
        model.addAttribute("prodForm",prodForm);
        List<String> prodList = shoppingCart.listAllProd();
        model.addAttribute("prodList", prodList);
        return "home";
    }
	@RequestMapping(method = POST)
	public String add2Cart(ProdForm prodForm){
		logger.info("--->into add2Cart......with prod: "+prodForm.getProdName());
		shoppingCart.addProduct(prodForm.getProdName());
		return "redirect:/";
	}
	
}
