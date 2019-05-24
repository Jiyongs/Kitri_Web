<%@page import="com.kitri.dto.RepBoard"%>
<%@page import="java.util.List"%>
<%@page import="com.kitri.dto.PageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
  div.boardlist{width: 70%; }
  div.boardlist>h3{ font-weight: bold; text-align: center;}
  div.boardlist>div.pageInfo{text-align:right; font-style: italic;}
  div.boardlist>div.table{display:table; margin: 10 auto; width: 90%;}
  div.boardlist>div.table>div.tr{display: table-row;}
  div.boardlist>div.table>div.tr>div.th{display:table-cell; font-weight: bold; text-align: center;}
  div.boardlist>div.table>div.tr>div.td{display:table-cell;}
  div.boardlist>div.table, div.boardlist div.th, div.boardlist div.td{
   border: 1px solid #93DAFF; border-collapse: collapse;
  }
  div.boardlist>div.pagegroup{
    width: 90%; 
  }
  div.boardlist>div.pagegroup>ul{
    margin: 0 auto;
  }
  div.boardlist>div.pagegroup>ul>li{
    
    list-style: none;
    display: inline-block;
  }
   
  div.boardlist>div.pagegroup a{
    margin:10px;
    text-decoration: none;    
  }
  
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
 
<script>
$(function(){
	$("div.boardlist>div.table>div.tr").click(function(){
		if($(this).index() != 0){
			var board_seq = $(this).find("div.board_seq").html().trim();
			alert(board_seq+"번 글의 상세정보를 보여줍니다.");
			$.ajax({
				url:'boarddetail',
				method:'get',
				data:'board_seq='+board_seq,
				success:function(result){
					//~~~~
				}
			});
		}
		return false;
	});
	$("div.boardlist>div.pagegroup a").click(function(){
		var currentPage=$(this).attr("href");
		alert(currentPage+"페이지를 보여줍니다.");
		$.ajax({
			url:'boardlist',
			method:'get',
			data:'currentPage='+currentPage,
			success:function(result){
				$("section").html(result.trim());
			}
		});
		return false;
	});
});
</script>   

<%
	PageBean pb = (PageBean) request.getAttribute("pb");
	List<RepBoard> list = pb.getList();
	int size = list.size();								 //한 페이지 내에 보여줄 실제 행 개수
	
	int currentPage = pb.getCurrentPage();				 // 현재 페이지 index
	int startPage = pb.getStartPage();					 // 시작 페이지 index
	int endPage = pb.getEndPage();						 // 끝 페이지 index
	
	int cntPerPage = pb.getCntPerPage();				 // 한 페이지 내에 보여줄 최대 행 개수
	int totalPage = pb.getTotalPage();					 // 모든 페이지 개수
	int cntPerPageGroup = pb.getCntPerPageGroup();		 // 페이지 그룹 개수
%>

<div class="boardlist">
  <h3>게시글 목록</h3>
  <div class="pageInfo">현재페이지:<%=currentPage%>page, 총페이지:<%=pb.getTotalPage()%>page</div>
  <div class="table"> 
    <div class="tr">
      <div class="th board_seq">글번호</div>
      <div class="th board_subject">글제목</div>
      <div class="th board_writer">작성자</div>
      <div class="th board_viewcont">조회수</div>
    </div>
  <%
  	// 한 페이지에 출력될 실제 행 개수 만큼 반복
  	for(int i = 0; i < size; i++){
  %>
  
  	<div class="tr">
      <div class="td board_seq"><%=list.get(i).getBoard_seq()%></div>
      <div class="td board_subject"><%=list.get(i).getBoard_subject()%></div>
      <div class="td board_writer"><%=list.get(i).getBoard_writer()%></div>
      <div class="td board_viewcont"><%=list.get(i).getBoard_viewcount()%></div>
    </div>
    
  <%
  	}
  %>
  </div>
  
 <!--    <div class="tr">
      <div class="td board_seq">1</div>
      <div class="td board_subject">1</div>
      <div class="td board_writer">2019-05-23</div>
      <div class="td board_viewcont">0</div>
    </div>
    <div class="tr">
      <div class="td board_seq">8</div>
      <div class="td board_subject">&#10551;1-r2</div>
      <div class="td board_writer">2019-05-23</div>
      <div class="td board_viewcont">0</div>
    </div>   
  </div> -->
  
  <div class="pagegroup"> 
      <ul>
      <%
      	// 시작 페이지가 1이 아닌 경우만 < 표시
      	if(startPage != 1) {
      %>
       	   <li><a href="<%=startPage-1%>" >◀</a></li>       
      <%		
      	}
      %>
      <%
      	// 페이지 그룹 개수(3)만큼 반복
      	for(int i = 0; i < cntPerPageGroup; i++) {
      		// 마지막 페이지에서, 
      		if(startPage+i <= totalPage){
      %>
       	   		<li><a href="<%=startPage+i%>" ><%=startPage+i%></a></li>    
      <%
      		}
      	}
      %>
       <%
       	if(endPage != totalPage){
       	%>
	       <li><a href="<%=endPage+1%>">▶</a></li>            	
       <%		
       	}
       %>
      </ul>   
  </div>   
</div>