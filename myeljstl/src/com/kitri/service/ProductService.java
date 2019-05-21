package com.kitri.service;

import java.util.List;

import com.kitri.dao.ProductDao;
import com.kitri.dto.Product;
import com.kitri.exception.NotFoundException;

// 서비스 존재 목적 : use case별로 나누기 위함. / login, registr ...
// dao              : db 작업만을 위함.         / findById ...

public class ProductService {
	private ProductDao dao;
	public ProductService() {
		dao = new ProductDao();
	}
	public List<Product> findAll(){
		return dao.selectAll();
	}
	public Product findByNo(String no){
		return dao.selectByNo(no);
	}
}
