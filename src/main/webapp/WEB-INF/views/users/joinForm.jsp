<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id="username" type="text" class="form-control" placeholder="Enter username">
			<button id="btnUsernameSameCheck" class="btn btn-warning" type="button">유저네임 중복체크</button>
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password">
		</div>
		<div class="mb-3">
			<input id="email" type="email" class="form-control" placeholder="Enter email">
		</div>
		<button id="btnJoin" type="button" class="btn btn-primary">회원가입</button>
	</form>
</div>

<script>
   let isUsernameSameCheck = false;
   
   // 회원가입
   $("#btnJoin").click(()=>{
      if(isUsernameSameCheck == false){
         alert("유저네임 중복 체크를 진행해주세요");
         return;
      }
      
      // 0. 통신 오브젝트 생성
      let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
      };
      
      $.ajax("/join",{
         type: "POST",
         dataType: "json",
         data: JSON.stringify(data),
         headers : {
               "Content-Type" : "application/json"	//json 타입 컨텐트 타입 날릴꺼야!
         }
      }).done((res)=>{
         if(res.code == 1){
            //console.log(res);
            location.href = "/loginForm";
         }
      });
   });
   
   
   // 유저네임 중복 체크
   $("#btnUsernameSameCheck").click(()=>{	// 리스너. / 행위는 람다식으로 쓰는 것이 편하다	(자바스크립트는 =>, 자바는 ->)
      // 0. 통신 오브젝트 생성(->json 스트링으로 바로 바꾸기 위해서). (Get 요청은 body가 없다.)
      
      // 1. 사용자가 적은 입력값 username 값을 가져오기 -> 가져오려면 id를 알아야 한다.
      let username = $("#username").val();   //get 요청, -> 쿼리스트링을 날린다, -> body 데이터가 없어서 json을 만들 필요가 없다

      // 2. Ajax 통신
      $.ajax("/users/usernameSameCheck?username="+username,{ //()"http의주소",{object}).done(행위의결과); / 응답의 결과가 done 에 들어온다

         type:"GET",
         dataType: "json",	// 디폴트값 json
         async: true
      }).done((res)=>{		//람다식 (js에서 익명함수를 써도 되지만 코드가 간결)
         console.log(res);
         if(res.code == 1){ // 통신 성공
            if(res.data == false){
               alert("아이디가 중복되지 않았습니다.");
               isUsernameSameCheck = true;
            }else{
               alert("아이디가 중복되었어요. 다른 아이디를 사용해주세요.");
               isUsernameSameCheck = false;
               $("#username").val("");
            }
         }
      });
   });
   

   
</script>

<%@ include file="../layout/footer.jsp"%>



