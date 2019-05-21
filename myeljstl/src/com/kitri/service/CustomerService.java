package com.kitri.service;

import com.kitri.dao.CustomerDAO;
import com.kitri.dto.Customer;
import com.kitri.exception.NotFoundException;

public class CustomerService {
	public String login(String id, String pass) {
		CustomerDAO dao = new CustomerDAO();
		try {
			Customer c = dao.selectById(id);
			if(c.getPass().equals(pass)) {
				return "로그인 성공";
			}else {
				return "로그인 실패";
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
			return "로그인 실패:"+e.getMessage();
		}
	}
}
