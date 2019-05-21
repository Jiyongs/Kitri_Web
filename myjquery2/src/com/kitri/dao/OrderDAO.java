package com.kitri.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultButtonModel;

import com.kitri.dto.*;
import com.kitri.exception.AddException;
import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;

public class OrderDAO {
	
	// <insert문 동시 실행> 메소드
	// : 같은 시퀀스 사용을 위해, 같은 연결내에서 2가지 insert문을 사용해야 함!
	public void insert(OrderInfo info) throws AddException {
		
		Connection conn = null;

		try {
			conn = DBConnection.makeConnection();
			conn.setAutoCommit(false); // [commit, rollback 처리] 1 자동 COMMIT 해제
			
			insertInfo(conn, info);
			
			List<OrderLine> lines = info.getLines();
			insertLine(conn, lines);
			
			conn.commit(); //2 수동 COMMIT

		} catch (Exception e) { //notFoundException, sqlException 모두 한번에 잡기 위해 Exception으로 함
			
			try {
				conn.rollback(); //4 에러 시, ROLLBACK
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new AddException("주문추가 오류 : " + e.getMessage()); //insert시 발생하는 예외를 사용자정의 예외 처리함! 
		} finally {
				DBClose.close(conn, null);
		}
	}

	
	// <ORDER_INFO insert문 실행> 메소드
	public void insertInfo(Connection conn, OrderInfo info) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ORDER_INFO(ORDER_NO, ORDER_ID) \n");
		sql.append("VALUES (ORDER_SEQ.NEXTVAL, ?)");
		
		try {
		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, info.getCustomer().getId());
			pstmt.executeUpdate();

		//} catch (SQLException e) { //3 에러를 잡지 않음. throw 처리 -> insert()의 catch로 감
		//	e.printStackTrace();
		} finally {
			DBClose.close(null, pstmt); //conn은 close하면 안 됨!
		}
		
	}
	
	// <ORDER_LINE insert문 실행> 메소드
	public void insertLine(Connection conn, List<OrderLine> lines) throws SQLException {
		
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ORDER_LINE(ORDER_NO, ORDER_PROD_NO, ORDER_QUANTITY) \n");
		sql.append("VALUES (ORDER_SEQ.CURRVAL, ?, ?)");

		try {
			pstmt = conn.prepareStatement(sql.toString());

			for(OrderLine line : lines) {
				String prod_no = line.getProduct().getProd_no();
				pstmt.setString(1, prod_no);
				
				int quantity = line.getOrder_quantity();
				pstmt.setInt(2, quantity);
				//pstmt.executeUpdate(); //매번 실행하면, 지연발생
				pstmt.addBatch();        //=> 나중에 일괄처리하기 위해, 작업을 쌓아둠!
			}
			
			pstmt.executeBatch();        //=> 일괄 처리!
		//} catch (SQLException e) {  //3 에러를 잡지 않음. throw 처리 -> insert()의 catch로 감
		//	e.printStackTrace();
		} finally {
			DBClose.close(null, pstmt); //conn은 close하면 안 됨!
		}
		
	}
	
	// <id로 해당 주문내역 조회> 메소드
	// 주문번호, 주문일자, 상품번호, 상품명, 가격, 주문수량 => join 필요함!!
	public List<OrderInfo> selectById(String id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderInfo> list = new ArrayList<>();
		
		try {
			conn = DBConnection.makeConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("select info.order_no, order_dt, prod_no, prod_name, prod_price, order_quantity \n");
			sql.append("from order_info info join order_line line on info.order_no = line.order_no \n");
			sql.append("join product p on p.prod_no = line.order_prod_no \n");
			sql.append("where order_id = ? \n");
			sql.append("order by order_dt desc, prod_no");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			OrderInfo info = null;
			OrderLine line = null;
			List<OrderLine> lines = null;
			
			int old_order_no = -1; // 이전 주문번호
			
			while(rs.next()) {
				int order_no = rs.getInt("order_no");
				
				// 주문번호가 다를 때 (order_no가 새로 시작하는 경우),
				if(old_order_no != order_no) {
					info = new OrderInfo();
					list.add(info);
					
					info.setOrder_no(order_no);
					info.setOrder_dt(rs.getDate("order_dt"));
					
					lines = new ArrayList<>();
					info.setLines(lines);
					
					old_order_no = order_no;
				}
				
				line = new OrderLine();			
				
				Product p = new Product();
				p.setProd_no(rs.getString("prod_no"));
				p.setProd_name(rs.getString("prod_name"));
				p.setProd_price(rs.getInt("prod_price"));
				line.setProduct(p);
				
				line.setOrder_quantity(rs.getInt("order_quantity"));
				
				lines.add(line);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
}
