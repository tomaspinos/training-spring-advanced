<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listman.css"/>
</head>

<body>
<h1>${mlist.name}</h1>

<p>${mlist.description}</p>

<p>
    <a href="${mlist.id}/edit">Edit details</a>
</p>

Items:
<table style="float: none">
    <c:forEach items="${items}" var="item">
        <tr>
            <td>${item.name}</td>
            <td style="width: 80%">${item.description}</td>
            <td><a href="${mlist.id}/editItem/${item.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>

<p>
    <a href="${mlist.id}/addItem">New item</a>
</p>
</body>
</html>
