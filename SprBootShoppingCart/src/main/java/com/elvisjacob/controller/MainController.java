package com.elvisjacob.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elvisjacob.dao.OrderDAO;
import com.elvisjacob.dao.ProductDAO;
import com.elvisjacob.entities.Product;
import com.elvisjacob.form.CustomerForm;
import com.elvisjacob.model.CartInfo;
import com.elvisjacob.model.CustomerInfo;
import com.elvisjacob.model.ProductInfo;
import com.elvisjacob.pagination.PaginationResult;
import com.elvisjacob.utils.Utils;
import com.elvisjacob.validator.CustomerFormValidator;

@Controller
@Transactional
public class MainController {

	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private CustomerFormValidator customerFormValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target = " + target);
		
		// Case update quantity in cart
		// @ModelAttribute("cartForm") @Validated CartInfo cartForm
		if (target.getClass() == CartInfo.class) {}
		// Case save customer info
		// @ModelAttribute @Validated CustomerInfo customerForm
		else if (target.getClass() == CustomerForm.class) {
			dataBinder.setValidator(customerFormValidator);
		}
	}
	
	
	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping({"/productList"})
	public String listProductHandler(Model model, 
			@RequestParam(value="name", defaultValue = "") String likeName,
			@RequestParam(value="page", defaultValue = "1") int page) {
		
		final int maxResult = 5;
		final int maxNavigationPage = 10;
		
		PaginationResult<ProductInfo> results = productDao.queryProducts(page, //
	            maxResult, maxNavigationPage, likeName);
		
		model.addAttribute("paginationProducts", results);
		return "productList";
	}
	
	@RequestMapping({"/buyProduct"})
	public String listProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value="code", defaultValue="") String code) {
		
		Product product = null;
		if (code != null && code.length() > 0) {
			product = productDao.findProduct(code);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}
		return "redirect:/shoppingCart";
	}
	
	
	@RequestMapping({"/shoppingCartRemoveProduct"})
	public String removeProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value="code", defaultValue="") String code) {
		
		Product product = null;
		if (code != null && code.length() > 0) {
			product = productDao.findProduct(code);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		return "redirect:/shoppingCart";
	}
	
	
	// POST: Update quantity of product in the cart
	@RequestMapping(value= {"/shoppingCart"}, method=RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, Model model,
			@ModelAttribute("cartForm") CartInfo cartForm) {
		
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		
		return "redirect:/shoppingCart";
	}
	
	
	// GET: Show shopping cart
	@RequestMapping(value= {"/shoppingCart"}, method=RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);
		model.addAttribute("cartForm", myCart);
		
		return "shoppingCart";
	}
	
	// GET: Enter customer info
	@RequestMapping(value= {"shoppingCartCustomer"}, method=RequestMethod.GET)
	public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		
		if (cartInfo.isEmpty()) {
			return "redirect:/shoppingCart";
		}
		
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		CustomerForm customerForm = new CustomerForm(customerInfo);
		model.addAttribute("customerForm", customerForm);
		
		return "shoppingCartCustomer";
	}
	
	// POST: Save customer info
	@RequestMapping(value= {"shoppingCartCustomer"}, method=RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request, Model model,
			@ModelAttribute("customerForm") @Validated CustomerForm customerForm,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			customerForm.setValid(false);
			return "shoppingCartCustomer";
		}
		
		customerForm.setValid(true);
		CartInfo cartInfo = Utils.getCartInSession(request);
		CustomerInfo customerInfo = new CustomerInfo(customerForm);
		cartInfo.setCustomerInfo(customerInfo);
			
		return "redirect:/shoppingCartConfirmation";
	}
	
	
	// GET: Show shopping cart information
	@RequestMapping(value = {"/shoppingCartConfirmation"}, method=RequestMethod.GET)
	public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		if (cartInfo == null || cartInfo.isEmpty()) {
			return "redirect:/shoppingCart";
		}
		else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shoppingCartCustomer";
		}
		model.addAttribute("myCart", cartInfo);
		return "shoppingCartConfirmation";
	}
	
	// POST: Save shopping cart
	@RequestMapping(value = {"/shoppingCartConfirmation"}, method=RequestMethod.POST)	
	public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		if (cartInfo == null || cartInfo.isEmpty()) {
			return "redirect:/shoppingCart";
		}
		else if (!cartInfo.isValidCustomer()) {
			return "redirect:/shoppingCartCustomer";
		}
		try {
			orderDao.saveOrder(cartInfo);
		} catch (Exception e) {
			return "shoppingCartConfirmation";
		}
		
		Utils.removeCartInSession(request);
		// Store last cart.
		Utils.storeLastOrderedCartInSession(request, cartInfo);
	 
		return "redirect:/shoppingCartFinalize";
	}
	
	@RequestMapping(value = {"/shoppingCartFinalize"}, method=RequestMethod.GET)
	public String shoppinngCartFinalize(HttpServletRequest request, Model model) {
		CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
		if (lastOrderedCart == null) {
			return "redirect:/shoppingCart";
		}
		model.addAttribute("lastOrderedCart", lastOrderedCart);
		return "shoppingCartFinalize";
	}
	/*
	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam("code") String code) throws IOException {
		Product product = null;
		if (code != null) {
			product = this.productDao.findProduct(code);
		}
		if (product != null && product.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}
	*/
}
