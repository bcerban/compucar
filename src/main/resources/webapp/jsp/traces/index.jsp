<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<div>
    <h2>Traces</h2>
    <table border="1">
        <thead>
        <th>ID</th>
        <th>Date</th>
        <th>Operation</th>
        <th>Request Info</th>
        <th>Username</th>
        </thead>
        <tbody>
        <c:forEach var="trace" items="${traces}">
            <tr>
                <td>${trace.id}</td>
                <td>${trace.date}</td>
                <td>${trace.operation}</td>
                <td>${trace.requestInfo}</td>
                <td>${trace.username}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</html>
