<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<div>
    <h1>Reader reports</h1>
    <form action="readers" method="post">
        <label for="readerCode">Select a reader</label>
        <br/>
        <input type="text" name="readerCode" placeholder="Reader code"/>
        <br/>
        <br/>

        <label for="from">From date: </label>
        <input type="date" name="from" value="${from}"/><br/>

        <label for="to">To date: </label>
        <input type="date" name="to" value="${to}"/>
        <br/>
        <input type="submit" value="Check services"/>
    </form>
</div>

<div>
    <h2>Services</h2>

    <c:if test="${not empty serviceCount}">
        <h5>Service count for period: ${serviceCount}</h5>
    </c:if>
    <c:if test="${not empty minutes}">
        <h5>Minutes of use: ${minutes}</h5>
    </c:if>
    <c:if test="${not empty earnings}">
        <h5>Earnings: $${earnings}</h5>
    </c:if>

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
        <form action="readers/pdf" method="post">
            <input type="hidden" value="${readerCode}" name="readerCode"/>
            <input type="hidden" value="${from}" name="from"/>
            <input type="hidden" value="${to}" name="to"/>
            <input type="submit" value="Generate PDF" />
        </form>
    </c:if>
</div>
</body>
</html>
