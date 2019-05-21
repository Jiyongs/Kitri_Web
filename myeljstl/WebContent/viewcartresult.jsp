<%@page import="java.util.Set"%>
<%@page import="com.kitri.dto.Product"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<style>
div.viewcartresult{
    text-align: center;
}
div.viewcartresult>h3{
    font-size: 24px;
    font-weight: bold;
    color: #222;
    
    padding-bottom: 18px;
    margin-bottom: 20px;
}
div.viewcartresult>table{
	width: 80%;
	margin: 5px;
}
div.viewcartresult>table, div.viewcartresult>table th, div.viewcartresult>table td{    
	border : 1px solid #222;
	border-collapse: collapse;
	text-align: left;
}
</style>
<script>
$(function(){
	var $btRemoveCart = $("div.viewcartresult>table tr>td>button.removecart");
	$btRemoveCart.click(function(){
		alert("장바구니비우기 클릭!");
		$.ajax({
			url:"removecart",
			method:"get",
			success:function(result){
				if(result.trim()=="1"){
					alert("장바구니 비우기에 성공했습니다.");
				}
			}
		});
		return false;
	});
	var $btAddOrder = $("div.viewcartresult>table tr>td>button.addorder");
	$btAddOrder.click(function(){

		alert("주문하기 클릭!");
		$.ajax({
			url:"addorder",
			method:"get",
			success:function(result){
				// 주문 실패
				if(result.trim()=="-1"){
					alert("주문에 실패했습니다.");
				}
				// 주문 성공
				else if(result.trim()=="1"){
					alert("주문에 성공했습니다.");
					// >> 주문 내역 보기로 이동
					location.href="vieworder";
				}
			}
		});
		return false;
	});
});
</script>

<div class="viewcartresult">
 <h3>장바구니 내용</h3>
<%
	Map<Product,Integer> rc = (Map)request.getAttribute("rcart");
	Set<Product> keys = rc.keySet();
%>    
 <table>
   <tr>
     <th>상품번호</th><th>상품명</th><th>상품가격</th><th>수량</th>     
   </tr>
   <%   
   for(Product p:keys){ %>
   <tr>
     <td><%=p.getProd_no() %></td>
     <td><%=p.getProd_name() %></td>
     <td><%=p.getProd_price() %></td>
     <td><%=rc.get(p)%></td>
   </tr>
   
<%
	}//end for
%> 
   
   <tr>
     <td colspan="4" style="text-align:center;">
       <button style="margin:10px;" class="removecart">장바구니 비우기</button>
<%
   	if(session.getAttribute("loginInfo") != null){ //로그인한 경우만 주문하기 버튼보여주기
%>
       <button style="margin:10px;"class="addorder">주문하기</button>
<%
	}//end if
%>       
     </td>
   </tr>
 
   
 </table>
</div>