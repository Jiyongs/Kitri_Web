package com.kitri.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kitri.dto.Product;
import com.kitri.dto.ProductCategory;
import com.kitri.exception.NotFoundException;
import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;


public class ProductDao {
	public List<Product>selectAll(){
		List<Product> list = new ArrayList<Product>();
		//1)JDBC드라이버로드
		//2)DB연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			//3)SQL송신
			sql.append("select cate_no, cate_name, prod_no, prod_name, prod_price, prod_detail \n"); 
			sql.append("from product p join product_category pc \n");
			sql.append("on p.prod_cate=pc.cate_no \n");
			sql.append("order by cate_no, prod_name");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				String prod_detail = rs.getString("prod_detail");
				
				String cate_no = rs.getString("cate_no");
				String cate_name = rs.getString("cate_name");
				
				ProductCategory pc = new ProductCategory(cate_no,cate_name);
				Product p = new Product(prod_no, prod_name, prod_price, prod_detail, pc);
				list.add(p);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//5)연결닫기
			DBClose.close(conn, pstmt, rs);
		}
		return list;
		
		//4)결과수신
	}
	
	public Product selectByNo(String no){
		Product product =null;
		ProductCategory productCategory = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			//3)SQL송신
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT cate_no, cate_name, prod_no, prod_name, prod_price, prod_detail \n");
			sql.append("FROM product p JOIN product_category pc \n");
			sql.append("ON p.prod_cate=pc.cate_no \n"); 
			sql.append("WHERE prod_no=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				product = new Product(); 
				productCategory = new ProductCategory();
				
				product.setProd_no(rs.getString("prod_no"));
				product.setProd_name(rs.getString("prod_name"));
				product.setProd_price(rs.getInt("prod_price"));
				product.setProd_detail(rs.getString("prod_detail"));
				
				productCategory.setCate_no(rs.getString("cate_no"));
				productCategory.setCate_name(rs.getString("cate_name"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, pstmt, rs);
		}
		return product;
	}
}
