<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <div>
        <h2>Readers</h2>
        <table border="1">
            <thead>
                <th>Code</th>
                <th>Brand</th>
                <th>Battery Life</th>
                <th>Battery Used</th>
                </thead>
            <tbody>
                <c:forEach var="reader" items="${readers}">
                    <tr>
                        <td>${reader.code}</td>
                        <td>${reader.brand}</td>
                        <td>${reader.batteryLife}</td>
                        <td>${reader.batteryUsed}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</html>
