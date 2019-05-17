<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
$(function(){
	var arr = $("div.addcartresult>button");
	$(arr[0]).click(function(){
		// menu 중, 상품목록 메뉴 찾기
		// trigger를 이용하여 강제 클릭이벤트 발생
		$("nav>ul>li>a[href=productlist]").trigger("click");
		return false;
	});
	
	$(arr[1]).click(function(){
		$("nav>ul>li>a[href=viewcart]").trigger("click");
		return false;
	});
	
});
</script>

<div class="addcartresult" 
	 style="position:absolute; top : 200px; left : 100px; width:250px; border:1px solid; background-color:white;">
	장바구니에 넣었습니다.
	<button>상품목록으로 가기</button>
	<button>장바구니 보기</button>
</div>