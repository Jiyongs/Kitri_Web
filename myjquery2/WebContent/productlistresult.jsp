<%@page import="com.kitri.dto.Product"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
div.product_list ul > li{
    float: left;
    position: relative;
    margin: 5px;
}
div.product_list ul{
  list-style: none;
}
</style>   
<script>
$(function(){
	var $aObj = $("dl>dt>a");
	$aObj.click(function(){
		var url =$(this).attr("href");
/* 		var $dt = $(this).parent();
		var $span = $dt.siblings("dd.no").find("span");
		var $no = $span.html().trim(); */
		
		$.ajax({
			url:url,
			method:'get',
			success:function(result){
				$("section").html(result);
			}
		});
		return false;
	});
});
</script> 
<div class="product_list">
<ul>
<% 
	List<Product> list = (List)request.getAttribute("productlist");
   for(Product p: list){    
%>
  
    <li class="menu">
      <dl>
        <dt>
          <a href="productinfo?no=<%=p.getProd_no()%>">
            <img src="images/<%=p.getProd_no()%>.jpg" alt="<%=p.getProd_name()%>">
          </a>
        </dt>
        <dd>카테고리:<span><%=p.getProductCategory().getCate_name()%></span></dd>
        <dd>상품번호:<span><%=p.getProd_no() %></span></dd>
        <dd>상품명:<span><%=p.getProd_name() %></span></dd>
        <dd>가격:<span><%=p.getProd_price() %></span></dd>
      </dl>
    </li>

<%
	}//end for
%>
  </ul>    
</div>