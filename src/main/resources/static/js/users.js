let isUsernameSameCheck = false;

// 회원가입
$("#btnJoin").click(() => {
	join();
});


// 유저네임 중복 체크
$("#btnUsernameSameCheck").click(() => {	// 리스너. / 행위는 람다식으로 쓰는 것이 편하다	(자바스크립트는 =>, 자바는 ->)
	checkUsername();
});



//	로그인
$("#btnLogin").click(() => {
	login();	// 로그인 함수  호출
});


// 회원탈퇴
$("#btnDelete").click(() => {
	resign();
});

// 회원수정
$("#btnUpdate").click(() => {
	update();
});




//회원가입 
function join() {
	if (isUsernameSameCheck == false) {
		alert("유저네임 중복 체크를 진행해주세요");
		return;
	}

	// 0. 통신 오브젝트 생성
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};

	$.ajax("/join", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"	//json 타입 컨텐트 타입 날릴꺼야!
		}
	}).done((res) => {
		if (res.code == 1) {
			//console.log(res);
			location.href = "/loginForm";
		}
	});
}


function checkUsername() {
	// 0. 통신 오브젝트 생성(->json 스트링으로 바로 바꾸기 위해서). (Get 요청은 body가 없다.)

	// 1. 사용자가 적은 입력값 username 값을 가져오기 -> 가져오려면 id를 알아야 한다.
	let username = $("#username").val();   //get 요청, -> 쿼리스트링을 날린다, -> body 데이터가 없어서 json을 만들 필요가 없다

	// 2. Ajax 통신
	$.ajax(`/users/usernameSameCheck?username= ${username}`,{
		type: "GET",
		dataType: "json",	// 디폴트값 json
		async: true
	}).done((res) => {		//람다식 (js에서 익명함수를 써도 되지만 코드가 간결)
		if (res.code == 1) { // 통신 성공
			if (res.data == false) {
				alert("아이디가 중복되지 않았습니다.");
				isUsernameSameCheck = true;
			} else {
				alert("아이디가 중복되었어요. 다른 아이디를 사용해주세요.");
				isUsernameSameCheck = false;
				$("#username").val("");
			}
		}
	});
}

function login() {
	alert("login 함수 실행됨");
	let data = {
		username: $("#username").val(),
		password: $("#password").val()
	};


	$.ajax("/login", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data),	// http body에 들고갈 요청 데이터
		headers: {	//http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"	//json 타입 컨텐트 타입 날릴꺼야!
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		}
	});
}

function resign() {
	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "DELETE",
		dataType: "json" // 응답 데이터
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴 완료");
			location.href = "/";
		} else {
			alert("회원탈퇴 실패");
		}
	});
}


function update() {
	let data = {
		password: $("#password").val(),
		email: $("#email").val()
	};


	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "PUT",
		dataType: "json", // 응답 데이터
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("회원 수정 완료");
			location.reload(); // f5
		} else {
			alert("업데이트에 실패하였습니다");
		}
	});
}
