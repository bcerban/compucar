<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <div>
        <h2>Workshops</h2>
        <table border="1">
            <thead>
                <th>Code</th>
                <th>Name</th>
                <th>Address</th>
                <th>City</th>
                </thead>
            <tbody>
                <c:forEach var="workshop" items="${workshops}">
                    <tr>
                        <td>${workshop.code}</td>
                        <td>${workshop.name}</td>
                        <td>${workshop.address}</td>
                        <td>${workshop.city}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</html>
