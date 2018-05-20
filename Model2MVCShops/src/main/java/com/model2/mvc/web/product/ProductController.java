package com.model2.mvc.web.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductController() {
		System.out.println(this.getClass());
	}

	 @Value("#{commonProperties['pageUnit']}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	@RequestMapping(value="/addProductView", method=RequestMethod.GET)
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");

		return "redirect:/product/addProductView.jsp";
	}

	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct.do");
		System.out.println(product.toString());
		productService.addProduct(product);

		return "redirect:/product/default.jsp";
	}
	

	@RequestMapping(value="/getProduct", method= {RequestMethod.GET, RequestMethod.POST})
	public String getProduct(@RequestParam("prodNo") int prodNo,HttpServletRequest req, Model model) throws Exception {

		System.out.println("/getProduct.do");
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}

	@RequestMapping(value="/updateProductView", method={RequestMethod.GET, RequestMethod.POST})

	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/updateProductView.do");
		System.out.println(prodNo);
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/updateProduct.do");
		productService.updateProduct(product);
		
		System.out.println(product.toString());

		return "redirect:/product/getProduct?prodNo=" + product.getProdNo();
	}

	@RequestMapping(value="/listProduct", method={RequestMethod.GET, RequestMethod.POST})
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)
			throws Exception {

		System.out.println("/listProduct.do");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		System.out.println(resultPage);

		String choice = request.getParameter("menu");
		String userId = request.getParameter("userId");
		
		System.out.println("---->" + map.get("list"));
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("userId", userId);
		model.addAttribute("search", search);
		model.addAttribute("choice", choice);

		return "forward:/product/listProduct.jsp";
	}
}