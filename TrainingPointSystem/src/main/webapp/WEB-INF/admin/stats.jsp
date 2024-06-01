<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%--
  Created by IntelliJ IDEA.
  User: Buu Huyen
  Date: 5/14/2024
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar">
            <nav class="nav d-flex flex-column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/stats">Theo Khoa</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/stats">Theo Lớp</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/stats">Theo Thành tích</a>
            </nav>
        </div>
        <div class="col-md-10">
            <p>${statsByFaculty}</p>
        </div>
    </div>
</div>