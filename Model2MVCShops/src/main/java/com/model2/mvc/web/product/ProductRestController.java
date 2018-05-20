package com.model2.mvc.web.product;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductRestController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductRestController() {
		System.out.println(this.getClass());
	}

	 @Value("#{commonProperties['pageUnit']}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	@RequestMapping(value="/json/addProduct", method=RequestMethod.POST)
	public void addProduct(@RequestBody Product product) throws Exception {

		System.out.println("/product/json/addProduct : POST");
		
		productService.addProduct(product);
		
	}
	
	@RequestMapping(value="/json/getProduct/{prodNo}", method={RequestMethod.GET, RequestMethod.POST})
	public Product getProduct(@PathVariable int prodNo) throws Exception {

		System.out.println("/product/json/getProduct : { GET / POST }");
		System.out.println("request prodNo : " + prodNo);
		Product product = productService.getProduct(prodNo);
		System.out.println("response product : " + product);
		return product;
	}

	@RequestMapping(value="/json/updateProduct/{prodNo}", method=RequestMethod.POST)
	public void updateProduct(@PathVariable String prodNo, @RequestBody Product product) throws Exception {

		System.out.println("/product/json/updateProduct : POST");
		
		productService.updateProduct(product);

	}

	@RequestMapping(value="/json/listProduct", method={RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> listProduct(@ModelAttribute("search") Search search, HttpServletRequest request)
			throws Exception {

		System.out.println("/product/json/listProduct : { GET / POST }");

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
		
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("userId", userId);
		map.put("search", search);
		map.put("choice", choice);
		
		return map;
	}
}