package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method ���� ����

	public ProductController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties , classpath:config/commonservice.xml
	// ���� �Ұ�
	// ==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	 @Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 10}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 10}")
	int pageSize;

	@RequestMapping("/addProductView")
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");

		return "redirect:/product/addProductView.jsp";
	}

	@RequestMapping("/addProduct")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct.do");
		System.out.println(product.toString());
		// Business Logic
		productService.addProduct(product);

		return "redirect:/product/default.jsp";
	}
	
	@RequestMapping("/getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/getProduct.do");
		// Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);

		return "forward:/product/getProduct.jsp";
	}

	@RequestMapping("/updateProductView")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/updateProductView.do");
		System.out.println(prodNo);
		// Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

	@RequestMapping("/updateProduct")
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/updateProduct.do");
		// Business Logic
		productService.updateProduct(product);
		
		System.out.println(product.toString());

		return "redirect:/product/getProduct?prodNo=" + product.getProdNo();
	}

	@RequestMapping("/listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)
			throws Exception {

		System.out.println("/listProduct.do");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		// Business logic ����
		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		System.out.println(resultPage);

		String choice = request.getParameter("menu");
		String userId = request.getParameter("userId");
		
		System.out.println("---->" + map.get("list"));
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("userId", userId);
		model.addAttribute("search", search);
		model.addAttribute("choice", choice);

		return "forward:/product/listProduct.jsp";
	}
}