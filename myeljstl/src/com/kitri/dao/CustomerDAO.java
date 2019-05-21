package com.kitri.dao;

import java.sql.*;
import java.util.List;

import com.kitri.dto.Customer;
import com.kitri.exception.NotFoundException;
import com.kitri.util.*;

public class CustomerDAO {
	public Customer selectById(String id) throws NotFoundException{
		Connection con=null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		try {
			//1)JDBC드라이버 로드			
			//2)DB연결
			con = DBConnection.makeConnection();

			//3)SQL구문 DB서버로 송신 :executeQuery()
			//4)결과수신 : rs
			String selectByIdSQL = "SELECT * FROM customer WHERE id=?";
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Customer c = new Customer();
				c.setId(id);
				c.setPass(rs.getString("pass"));
				c.setName(rs.getString("name"));
				return c;
			}else {
				throw new NotFoundException("아이디에해당하는 고객이 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}finally {
			DBClose.close(con, pstmt, rs);
		}

	}

	public List<Customer> selectByName(String name){
		return null;
	}

	public List<Customer> selectAll(){
		return null;
	}
	public void insert(Customer c) {

	}
}
