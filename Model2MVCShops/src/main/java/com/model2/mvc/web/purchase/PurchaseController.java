package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

//==> 구매관리 Controller
@Controller
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public PurchaseController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties , classpath:config/commonservice.xml
	// 참조 할것
	// ==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 10}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 10}")
	int pageSize;

	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(HttpServletRequest req, HttpSession session) throws Exception {

		System.out.println("addPurchasView.do");
		
		String prodNoChange = req.getParameter("prod_no");
		int prodNo = Integer.parseInt(prodNoChange);

		Product product = productService.getProduct(prodNo);

		// req.setAttribute("product", product);

		ModelAndView mav = new ModelAndView();
		mav.addObject("product", product);
		mav.setViewName("forward:/purchase/addPurchaseView.jsp");
		return mav;
	}

	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(HttpServletRequest req, HttpSession session) throws Exception {

		System.out.println("addPurchase.do");
		
		User user = new User();
		Product product = new Product();
		Purchase purchase = new Purchase();

		String userId = req.getParameter("buyerId");
		user = userService.getUser(userId);

		String prodNoChange = req.getParameter("prodNo");
		int prodNo = Integer.parseInt(prodNoChange);
		product = productService.getProduct(prodNo);

		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(req.getParameter("paymentOption"));
		purchase.setReceiverName(req.getParameter("receiverName"));
		purchase.setReceiverPhone(req.getParameter("receiverPhone"));
		purchase.setDlvyAddr(req.getParameter("receiverAddr"));
		purchase.setDlvyRequest(req.getParameter("receiverRequest"));
		purchase.setDlvyDate(req.getParameter("receiverDate"));

		System.out.println(purchase.toString());

		purchaseService.addPurchase(purchase);

		ModelAndView mav = new ModelAndView();
		mav.addObject("purchase", purchase);
		mav.setViewName("forward:/purchase/addPurchase.jsp");
		return mav;

	}
	
	@RequestMapping("listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpServletRequest req, HttpSession session) throws Exception {
		
		System.out.println("listPurchase.do");
		
		String buyerId = req.getParameter("userId");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		// Business logic 수행
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		System.out.println(resultPage);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", map.get("list"));
		mav.addObject("resultPage", resultPage);
		mav.addObject("userId", buyerId);
		mav.addObject("search", search);
		mav.setViewName("forward:/purchase/listPurchase.jsp");
		return mav;
	}
	
	// 뷰 체크 완성
	// @RequestMapping("listPurchase.do")
	// public ModelAndView listPurchase() {
	//
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("forward:/purchase/listPurchase.jsp");
	// return mav;
	// }

}
