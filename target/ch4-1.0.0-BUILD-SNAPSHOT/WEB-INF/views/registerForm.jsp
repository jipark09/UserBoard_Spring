<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.net.URLDecoder"%>
<c:set var="loginId" value="${sessionScope.id == null ? 'Login' : sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='Login' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='Login' ? 'Login' : loginId}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <style>
        * { box-sizing:border-box; }

        form {
            width:400px;
            height:600px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 3px solid #fdb2b6;
            border-radius: 10px;
        }

        .input-field {
            width: 300px;
            height: 40px;
            border : 1px solid #fdb2b6;
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 10px;
        }
        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }

        button {
            background-color: #c6ddde;
            color : white;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }

        .title {
            font-size : 50px;
            margin: 40px 0 30px 0;
        }

        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 20px;
        }
        .sns-chk {
            margin-top : 5px;
        }

    </style>
    <title>Register</title>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">JTI</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<%--<form action="<c:url value="/register/add"/>" method="post" onsubmit="return formCheck(this)">--%>
<form:form modelAttribute="user">
    <div class="title">Register</div>
    <div id="msg" class="msg">
<%--        <c:if test="${not empty param.msg}">--%>
<%--            <i class="fa fa-exclamation-circle"> ${URLDecoder.decode(param.msg)}</i>--%>
<%--        </c:if>--%>
        <form:errors path="id"/>
    </div>
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">비밀번호</label>
    <input class="input-field" type="text" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    <label for="">이메일</label>
    <input class="input-field" type="text" name="email" placeholder="example@fastcampus.co.kr">
    <label for="">생일</label>
    <input class="input-field" type="text" name="birth" placeholder="2020/12/31">
    <div class="sns-chk">
        <label><input type="checkbox" name="sns" value="facebook"/>&nbsp;페이스북&nbsp;</label>
        <label><input type="checkbox" name="sns" value="kakaotalk"/>&nbsp;카카오톡&nbsp;</label>
        <label><input type="checkbox" name="sns" value="instagram"/>&nbsp;인스타그램&nbsp;</label>
    </div>
    <button>회원 가입</button>
</form:form>
<script>
    function formCheck(frm) {
        let msg ='';

        if(frm.id.value.length<3) {
            setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
            return false;
        }

        if(frm.pwd.value.length<3) {
            setMessage('pwd의 길이는 3이상이어야 합니다.', frm.pwd);
            return false;
        }

        return true;
    }

    function setMessage(msg, element){
        document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;

        if(element) {
            element.select();
        }
    }

</script>
</body>
</html>