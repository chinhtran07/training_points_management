<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 24/04/2024
  Time: 09:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Administrator</h1>
<ul>
<c:forEach items="${pointGroups}" var="p">
    <li>
        ${p.id},
        ${p.name}
    </li>
</c:forEach>
</ul>