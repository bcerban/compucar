<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <div>
        <h2>Services</h2>
        <table border="1">
            <thead>
                <th>Code</th>
                <th>Date</th>
                <th>Service Time</th>
                <th>Cost</th>
                </thead>
            <tbody>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>${service.code}</td>
                        <td>${service.date}</td>
                        <td>${service.serviceTime}</td>
                        <td>${service.cost}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</html>
