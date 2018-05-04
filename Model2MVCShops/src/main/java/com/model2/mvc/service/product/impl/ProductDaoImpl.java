package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	private static final String NAMESPACE = "ProductMapper";

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public ProductDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertProduct", product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getProduct", prodNo);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		sqlSession.update(NAMESPACE + ".updateProduct", product);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {

		List<Product> list = sqlSession.selectList(NAMESPACE + ".getProductList", search);
		int totalCount = sqlSession.selectOne(NAMESPACE + ".getTotalCount", search);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));

		return map;
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getTotalCount", search);
	}

}
