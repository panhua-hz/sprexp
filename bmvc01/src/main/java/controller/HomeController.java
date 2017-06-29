package controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String add2Cart(@Valid ProdForm prodForm,Errors errors, RedirectAttributes model, Model model1){
		if (errors.hasErrors()){
			List<String> prodList = shoppingCart.listAllProd();
			model1.addAttribute("prodList", prodList);
	        return "home";
		}
		logger.info("--->into add2Cart......with prod: "+prodForm.getProdName());
		shoppingCart.addProduct(prodForm.getProdName());
		model.addFlashAttribute("recentProd", prodForm); //set to FlashModel can be used after redirect.
		return "redirect:/";
	}
	
	@RequestMapping(value="/query", method=GET)
	public String rpcQuery(@RequestParam String prodName, Model model){
		logger.info("rpcQuery: prodName?="+prodName);
		return "redirect:/";
	}
	
	@RequestMapping(value="/query/{category}/{name}", method=GET)
	public String pathQuery(@PathVariable String category, @PathVariable String name, Model model){
		logger.info("pathQuery: "+category+"="+name);
		return "redirect:/";
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value="npexpt", method=GET)
	public String ohNullEx(){
		//NullPointerException
		String hello = null;
		return hello.toUpperCase();
	}
	
	@RequestMapping(value="oobexpt", method=GET)
	public String ohOOBEx(){
		//IndexOutOfBoundsException
		int[] nums = new int[1];
		nums[1] = 2;
		return "redirect:/";
	}

}
