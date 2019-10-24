<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <div>
        <h2>Clients</h2>
        <table border="1">
            <thead>
                <th>Number</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Type</th>
                </thead>
            <tbody>
                <c:forEach var="client" items="${clients}">
                    <tr>
                        <td>${client.number}</td>
                        <td>${client.name}</td>
                        <td>${client.email}</td>
                        <td>${client.phone}</td>
                        <td>${client.type}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</html>
