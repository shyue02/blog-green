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
	//loginTest();	// 로그인 테스트
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
	let username = $("#username").val();   //get 요청, -> 쿼리스트링을 날린다, -> body 데이터가 없어서 json을 만들 필요가 없다

	// 2. Ajax 통신
	$.ajax(`/users/usernameSameCheck?username= + ${username}`, { //"http의주소",{}.done(행위의결과); / 응답의 결과가 done 에 들어온다

		type: "GET",
		dataType: "json",   // 디폴트값 json
		async: true
	}).done((res) => {      //람다식 (js에서 익명함수를 써도 되지만 코드가 간결)
		console.log(res);
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

// 로그인 테스트용
//function loginTest() {
//	let remember = $("#remember").prop("checked");
//	console.log(remember);
//}

function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember: $("#remember").prop("checked")
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
		//else{
		//alert("로그인 실패, 아이디 패스워드를 확인해주세요");
		//}
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
