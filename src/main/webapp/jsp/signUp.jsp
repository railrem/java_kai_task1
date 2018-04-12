<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.kai.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in</h1>
            <div class="account-wall">
                <form class="form-signin" action="${pageContext.request.contextPath}/signUp" method="post">
                    <input type="text" class="form-control" placeholder="Login" required autofocus name="login">
                    <input type="password" class="form-control" placeholder="Password" required name="password">
                    <input type="text" class="form-control" placeholder="Name" required name="name">
                    <input type="text" class="form-control" placeholder="Surname" required name="surname">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Send
                    </button>
                </form>
            </div>
            <a href="${pageContext.request.contextPath}/login" class="text-center new-account">Login</a>
        </div>
    </div>
</div>
</body>
</html>
