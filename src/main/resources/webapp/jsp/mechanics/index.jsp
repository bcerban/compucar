<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <div>
        <h2>Mechanics</h2>
        <table border="1">
            <thead>
                <th>Number</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Start Date</th>
                </thead>
            <tbody>
                <c:forEach var="mechanic" items="${mechanics}">
                    <tr>
                        <td>${mechanic.number}</td>
                        <td>${mechanic.name}</td>
                        <td>${mechanic.phone}</td>
                        <td>${mechanic.startDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</html>
