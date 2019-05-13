<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*" %>
    
<%!
	int cnt;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public void init(){
		cnt = 0;
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("DB 연결 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
try {

	conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
	stmt = conn.createStatement();

	String sql = "";
	sql += "select no \n";
	sql += "from counter \n";

	rs = stmt.executeQuery(sql);

	// select
	// 반드시 결과가 1개 -> rs.next(); 단독 사용!
	rs.next();
	cnt = rs.getInt("no");

	// update
	sql = "update counter \n";
	sql += "set no = no + 1";

	stmt.executeUpdate(sql);

} catch (SQLException e) {
	e.printStackTrace();
} finally {

	try {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

out.println("당신은 " + (cnt + 1) + "번째 방문자입니다.");

%>
</body>
</html>