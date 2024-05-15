<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 02/04/2024
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container-fluid bg-secondary p-3 mb-2">
    <div class="d-flex align-items-center">
        <c:url var="contextPath" value="${pageContext.request.contextPath}" />
        <c:url var="home" value="${contextPath}/admin/" />
        <div>
            <a href="${home}" class="text-uppercase fs-2 fw-bold text-white text-decoration-none">Quản trị hệ thống</a>
        </div>
        <c:forEach items="${views}" var="view">
            <div>
                <a href="${view.key.toLowerCase()}" class="text-white text-decoration-none fs-5 mx-2">${view.value}</a>
            </div>
        </c:forEach>
        <div>
            <a href="/stats" class="text-white text-decoration-none fs-5 mx-2">Thống kê</a>
        </div>
        <div>
            <a href="${contextPath}/logout" class="text-white text-decoration-none fs-5 mx-2">Đăng xuất</a>
        </div>
    </div>
</div>