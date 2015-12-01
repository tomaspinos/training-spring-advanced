<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listman.css"/>
</head>
<body>
<h1>Magnificent list editor</h1>

<h2>Lists:</h2>
<table>
    <c:forEach items="${lists}" var="mlist">
        <tr>
            <td style="padding: 0 10 0 10">${mlist.name}</td>
            <td style="width: 80%">${mlist.description}</td>
            <td><a href="${pageContext.request.contextPath}/list/${mlist.id}">Detail</a></td>
        </tr>
    </c:forEach>
</table>
<p style="float: right">
    <a href="${pageContext.request.contextPath}/list/add">New list</a>
</p>
</body>
</html>
