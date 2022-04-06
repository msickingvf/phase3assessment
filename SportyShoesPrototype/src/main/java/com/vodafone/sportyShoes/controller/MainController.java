package com.vodafone.sportyShoes.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.vodafone.sportyShoes.model.AdminUser;
import com.vodafone.sportyShoes.model.Product;
import com.vodafone.sportyShoes.model.Purchase;
import com.vodafone.sportyShoes.model.User;
import com.vodafone.sportyShoes.service.AdminUserService;
import com.vodafone.sportyShoes.service.CategoryService;
import com.vodafone.sportyShoes.service.ProductService;
import com.vodafone.sportyShoes.service.PurchaseService;
import com.vodafone.sportyShoes.service.UserService;

@Controller
public class MainController {
	@Autowired
	AdminUserService adminUserService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;
	@Autowired
	PurchaseService purchaseService;
	
	@PostMapping("/adminLogin")
	public RedirectView adminLogin(AdminUser adminUser, HttpSession session) {
		//return new RedirectView("/check/cpcmQueryRunner?reportId="+sqlreport.getId());
		System.out.println("user details "+ adminUser);
		boolean authenticated = false;
		if (adminUserService.getAdminUserByEmailAndPassword(adminUser) != null) {
			authenticated = true;
		}
		if (authenticated) {
			session.setAttribute("email", adminUser.getEmail());
			System.out.println("authenticated");
			return new RedirectView("/welcomeAdmin");
		} else {
			return new RedirectView("/adminLoginPage?message=Invalid Credentials");
		}
	}
	
	@GetMapping(path="/adminLoginPage")
	public ModelAndView adminLoginPage(@RequestParam(name="message", required=false) String message, Model model, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("adminLoginPage");
		if (message != null) {
			mav.addObject("message",message);
		}
		return mav;
	}
	
	@GetMapping(path="/productAdmin")
	public ModelAndView productAdmin( Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("productAdmin");
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			mav.addObject("products",productService.getProducts());
			//mav.addObject("categories",categoryService.getCategories());
		}
		return mav;
	}
	
	@GetMapping(path="/editProduct")
	public ModelAndView editProduct(@RequestParam(name="id", required=false) Integer id,Model model, HttpServletResponse response, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView("editProduct");
		Product product = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			if (id == null) { //a new product
				product = new Product();
				product.setName("");
				product.setPictureUrl("");
				product.setPrice(0);
				product.setCategory(null);
			} else {
				product = productService.getProduct(id);
				if (product==null) {throw new Exception("No product with id "+id+" existing");}
			}
		}
		mav.addObject("categories",categoryService.getCategories());
		mav.addObject("product",product);
		return mav;
	}
	
	
	
	@PostMapping(path="saveProduct")
	public ModelAndView saveProduct(
			Product product,
			Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/productAdmin");
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			if (product.getId()==null) {
				System.out.println("save new product");
				Product prod = new Product();
				prod=product;
				productService.setProduct(prod);
			} else {
				System.out.println("save existing product id"+product.getId());
				productService.setProduct(product);
			}
			
		}
		return mav;
	}
	
	@PostMapping(path="/adminPassword")
	public ModelAndView adminPassword(AdminUser adminUser, HttpSession session, Model model, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("adminLoginPage");
		//set adminUserPasswordNow
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			String sessionEmail = (String) session.getAttribute("email");
			System.out.println("email: "+sessionEmail);
			if (!sessionEmail.equals(adminUser.getEmail())) {//avoid changing password for other admin user
				mav = new ModelAndView("redirect:/adminLoginPage");
				mav.addObject("message","You cant change a password for another admin");
			} else {
				boolean isValidPassword = true; //dummy for implementing logic of password validation (length, characters etc)
				if (isValidPassword) {
					adminUserService.setAdminUser(adminUser);
					mav = new ModelAndView("redirect:/welcomeAdmin?message=Password Changed");
				} else {
					mav = new ModelAndView("redirect:/welcomeAdmin?message=Invalid new Password");
				}

			}
		}
		return mav;
	}
	
	@GetMapping(path="/adminPassword")
	public ModelAndView adminPassword(Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			String sessionEmail = (String) session.getAttribute("email");
			System.out.println("email: "+sessionEmail);
			AdminUser adminUser = adminUserService.getAdminUserByEmail(sessionEmail);
			mav = new ModelAndView("adminPassword");
			mav.addObject("adminUser",adminUser);
		}
		return mav;
	}
	
	@GetMapping(path="/welcomeAdmin")
	public ModelAndView welcomeAdmin(@RequestParam(name="message", required=false) String message,Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			String sessionEmail = (String) session.getAttribute("email");
			System.out.println("email: "+sessionEmail);
			AdminUser adminUser = adminUserService.getAdminUserByEmail(sessionEmail);
			mav = new ModelAndView("welcomeAdmin");
			if (message != null) {
				mav.addObject("message",message);
			}
			mav.addObject("adminUser",adminUser);
		}
		return mav;
	}
	
	@GetMapping(path="/purchaseReportDetails")
	public ModelAndView purchaseReportDetails(
			@RequestParam(name="category", required=true) String category,
			@RequestParam(name="dateString", required=true) String dateString,
			Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;
		ArrayList<Purchase> purchases = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			purchases = purchaseService.getPurchasesByDayAndCategory(category, dateString);
			mav = new ModelAndView("purchaseReportDetails");
		}
		mav.addObject("dateString",dateString);
		mav.addObject("category",category);
		mav.addObject("purchases",purchases);
		return mav;
	}
	
	
	@GetMapping(path="/purchaseReportsOverview")
	public ModelAndView purchaseReportDetails(
			Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;
		ArrayList<ArrayList<String>> summaryTable = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			//purchases = purchaseService.getPurchasesByDayAndCategory(category, dateString);
			summaryTable = purchaseService.getSummaryTable();
			mav = new ModelAndView("purchaseReportsOverview");
		}
		mav.addObject("summaryTable",summaryTable);
		return mav;
	}
	
	@GetMapping(path="/logout")
	public ModelAndView logout(
			Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;

		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			session.setAttribute("email", null);
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","User logged out");
		}

		return mav;
	}
	
	@GetMapping(path="/showUsers")
	public ModelAndView showUsers(@RequestParam(name="filter", required=false) String filter,Model model, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = null;
		ArrayList<User> users = null;
		if (session.getAttribute("email") == null) {
			mav = new ModelAndView("redirect:/adminLoginPage");
			mav.addObject("message","Login Required");
		} else {
			//String sessionEmail = (String) session.getAttribute("email");
			if (filter == null || filter.isEmpty()) {
				users = userService.getAllUsers();
			} else {
				users = userService.getUsersByEmailfilter(filter);
			}
			
		}
		mav = new ModelAndView("showUsers");
		mav.addObject("filter",filter);
		mav.addObject("users",users);
		return mav;
	}
}
