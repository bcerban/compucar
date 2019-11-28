<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<div>
    <h1>Services reports</h1>
    <form action="services" method="post">
        <label for="month">Services for month</label>
        <br/>
        <input type="text" name="month" placeholder="Ex: 1 (January)"/>
        <br/>
        <input type="submit" value="Check services for month"/>
    </form>
</div>

<div>
    <h2>Services</h2>
    <table border="1">
        <thead>
        <th>Code</th>
        <th>Date</th>
        <th>Service Time</th>
        <th>Cost</th>
        <th>Events</th>
        <th>Diagnoses</th>
        </thead>
        <tbody>
        <c:forEach var="service" items="${services}">
            <tr>
                <td>${service.code}</td>
                <td>${service.date}</td>
                <td>${service.serviceTime}</td>
                <td>${service.cost}</td>
                <td>
                    <table>
                        <tbody>
                            <c:forEach var="event" items="${service.events}">
                                <tr><td>${event.name}</td></tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td>
                    <table>
                        <tbody>
                        <c:forEach var="diagnosis" items="${service.diagnoses}">
                            </tr>
                                <td>${diagnosis.eventName}</td>
                                <td>${diagnosis.result}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty services}">
        <form action="services/pdf" method="post">
            <input type="hidden" value="${month}" name="month"/>
            <input type="submit" value="Generate PDF" />
        </form>
    </c:if>

</div>
</body>
</html>
