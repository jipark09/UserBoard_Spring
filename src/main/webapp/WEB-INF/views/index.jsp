<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page session="false"%>--%>
<c:set var="loginId" value="${sessionScope.id == null ? 'Login' : sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='Login' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId == 'Login' ? 'Login' : loginId}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JTI HomePage</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
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
<div style="text-align:center">
    <h1>Welcom Board HomePage</h1>
</div>
</body>
</html>