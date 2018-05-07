package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String addPurchaseView(HttpServletRequest req, HttpSession session) throws Exception {

		String prodNoChange = req.getParameter("prod_no");
		int prodNo = Integer.parseInt(prodNoChange);

		Product product = productService.getProduct(prodNo);

		req.setAttribute("product", product);

		return "forward:/purchase/addPurchaseView.jsp";

	}

	@RequestMapping("/addPurchase.do")
	public String addPurchase(HttpServletRequest req, HttpSession session) throws Exception {

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
		purchase.setDivyAddr(req.getParameter("receiverAddr"));
		purchase.setDivyRequest(req.getParameter("receiverRequest"));
		purchase.setDivyDate(req.getParameter("receiverDate"));

		System.out.println(purchase.toString());

		purchaseService.addPurchase(purchase);

		req.setAttribute("purchase", purchase);

		return "forward:/purchase/addPurchase.jsp";

	}

}
