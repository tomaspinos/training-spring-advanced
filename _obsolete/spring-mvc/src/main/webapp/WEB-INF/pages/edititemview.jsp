<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listman.css"/>
</head>

<body>
<h1>Edit ${mlist.name}</h1>
<form:form method="post" action="${pageContext.request.contextPath}/list/${command.listId}/saveItem">
    <table>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td><form:input path="name" type="text"/></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:input path="description" type="text"/></td>
        </tr>
    </table>

    <form:input type="hidden" path="id"/>
    <form:input type="hidden" path="listId"/>

    <input type="submit"/>

</form:form>
</body>
</html>
