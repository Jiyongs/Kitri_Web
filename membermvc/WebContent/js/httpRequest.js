function getXMLHttpRequest(){
	if(window.ActiveXObject){ // 인터넷익스플로우면
		try{
			return new ActiveXObject("Msxml2.XMLHTTP"); // 인터넷익스플로우 신버전
		}catch(e1){
			try{
				return new ActiveXObject("Microsoft.XMLHTTP"); //인터넷익스플로우 구버전
			}catch(e2){
				return null;
			}
		}
	}else if(window.XMLHttpRequest){ // 인터넷익스플로우가 아니면 (*인터넷익스플로우 11버전 이상도 여기 걸림)
		return new XMLHttpRequest(); // 서버와 통신 가능한 객체
	}else{
		return null;
	}
}

var httpRequest = null;

function sendRequest(url, params, callback, method){
	httpRequest = getXMLHttpRequest();  //서버와 통신 가능한 객체 얻어오기
	
	var httpMethod = method ? method : 'GET'; //메소드의 값이 있으면 그걸 쓰고, 없으면 default로 get방식으로 처리
	if(httpMethod != 'GET' && httpMethod != 'POST'){ // 메소드가 get이 아니고 && post도 아니면, (오타)
		httpMethod = 'GET'; // 무조건 get방식으로 처리
	}
	var httpParams = (params == null || params == '') ? null : params; // 위의 메소드 판단 방식과 같은 효과
	var httpUrl = url;
	if(httpMethod == 'GET' && httpParams != null){ // get방식이고, 넘겨줄 값이 있다면
		httpUrl = httpUrl + "?" + httpParams;
	}
	
	//alert("method == " + httpMethod + "\turl == " + httpUrl + "\tparam == " + httpParams);
	httpRequest.open(httpMethod, httpUrl, true); //인자1방식으로 2url을 연결하겠다 (true : 비동기 / falase : 동기) *ajax는 비동기
	httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	httpRequest.onreadystatechange = callback; // ready state가 바뀔때마다 callback함수를 호출하겠다  //이 메소드가 호출되는 중에도 callback함수를 호출가능(비동기)  //callback함수는 화면에 뭔가를 보여라 -> ready state가 4일때만!  *on : javascript method
	//alert(httpMethod == 'POST' ? httpParams : null);
	httpRequest.send(httpMethod == 'POST' ? httpParams : null);  // post방식이면, send()를 통해 httpParams를 보냄
}