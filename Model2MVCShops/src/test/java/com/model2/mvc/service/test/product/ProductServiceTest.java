package com.model2.mvc.service.test.product;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdNo(25000);
		product.setProdName("test3");
		product.setProdDetail("test3");
		product.setPrice(999);
		product.setManuDate("20180504");
		product.setFileName("test3");
		
		productService.addProduct(product);
		
		System.out.println("testAddProduct");
		System.out.println(product.toString());
		System.out.println();
		
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		
		product = productService.getProduct(25000);
		
		System.out.println("testGetProduct");
		System.out.println(product);
		System.out.println();
		
	}
	
	//@Test
	public void testUpdateProduct() throws Exception {
		
		Product product = new Product();
		product.setProdNo(20000);
		product.setProdName("test3");
		product.setProdDetail("test3");
		product.setManuDate("20180504");
		product.setPrice(99);
		product.setFileName("test3");
		
		productService.updateProduct(product);
		
		System.out.println("testUpdateProduct");
		System.out.println(product);
		System.out.println();
		
		
	}
	
	//@Test
	public void testGetProductListAll() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		Map<String, Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>) map.get("list");
		
		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
		
		System.out.println("==========================================");
		
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);
		
		list = (List<Object>) map.get("list");
		
		System.out.println(list);
		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
		
	}
	
}
