<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 03/05/2024
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-auto px-0">
    <div id="sidebar" class="collapse collapse-horizontal show border-end">
        <div id="sidebar-nav" class="list-group border-0 rounded-0 text-sm-start min-vh-80">
            <c:forEach items="${entityNames}" var="e">
                <a href="<c:url value="/admin/${fn:toLowerCase(e)}"/>"
                   class="list-group-item border-end-0 d-inline-block text-truncate"
                   data-bs-parent="#sidebar"><i class="bi bi-bootstrap"></i> <span>${e}</span> </a>
            </c:forEach>
        </div>
    </div>
</div>
