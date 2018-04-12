<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-1.11.1.min.js"></script>

</head>
<body>
${sessionScope.name}
<table>
    <tr>
        <th>Event name</th>
        <th>Event manager</th>
        <th>actions</th>
    </tr>
    <c:forEach items="${events}" var="event">
        <tr>
            <td>${event.name}</td>
            <td>${event.manager.name} ${event.manager.surname} ${event.manager.id} ${sessionScope.user_id}</td>
            <td>
                <c:if test="${ event.manager.id eq sessionScope.user_id} ">
                    <a href="/deleteEvent?id=${event.id}"> Delete</a>
                </c:if>
            </td>

        </tr>
    </c:forEach>
</table>
</body>
</html>