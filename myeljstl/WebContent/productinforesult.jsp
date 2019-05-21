<%@page import="com.kitri.dto.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Product p = (Product)request.getAttribute("productinfo");
	String no = p.getProd_no(); // 상품번호
%>

<script>
$(function(){
	var $bt = $(".submit dl>dt>button");
	$bt.click(function(){
		$.ajax({
		  url:'addcart',
		  method:'get',
		  data:
				'no=<%=no%>&quantity='+$("input[name=quantity]").val(),
		  success:function(result){
			  //$("section").html(result.trim());
			  $("div.addcartresult").remove();    //기존의 알림메세지 제거
			  $("section").append(result.trim()); //알림메세지 생성
		  }
		});
		return false;
	});
});

</script>

<div >
	<div>
		<img src="images/<%=p.getProd_no()%>.jpg">
	</div>
<div>
		<h4><%=p.getProd_name()%></h4>
		<p><%=p.getProd_detail() == null ? "" : p.getProd_detail()%></p>
		<div>
		
	<!-- <form action="addcart" method="get"> -->
			<input type="hidden" name="no" value="<%=no%>">
				<ul>
				<li class="no">
					<dl>
						<dt>상품번호:</dt>
						<dd><%=p.getProd_no()%></dd>
					</dl>
				</li>
				<li class="name">
					<dl>
						<dt>가격:</dt>
						<dd><%=p.getProd_price()%></dd>
					</dl>
				</li>
					<li class="quantitiy">
						<dl>
							<dt>수량:</dt>
							<dd>
								<input type="number" name="quantity" value="1" min="1" max="999">
							</dd>
						</dl>
					</li>
					<li class="submit">
						<dl>					
							<dt><button>장바구니넣기</button></dt>
						</dl>
					</li>
			</ul>
		<!-- </form> -->
		</div>
	</div>
</div>