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
<div class="container bg-light">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="#" data-bs-target="#sidebar" data-bs-toggle="collapse"><i class="gg-menu"></i></a>
        <a href="admin/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32">
                <use xlink:href="https://res.cloudinary.com/dk4uoxtsx/image/upload/v1712234809/LOGO-TRUONGV21-12-2018-01-300x300_jexwp0.png"></use>
            </svg>
            <span class="fs-4">Hệ thống quản lý điểm rèn luyện</span>
        </a>

        <ul class="nav nav-pills">
            <li class="nav-item">
                <a href="#" class="nav-link">
                    <img src="${pageContext.request.userPrincipal.avatar}" style="width: 40px" alt="">
                    ${pageContext.request.userPrincipal.name}
                </a>
            </li>
            <li class="nav-item mx-2"><a href="#" class="nav-link text-bg-primary">Đổi mật khẩu</a></li>
            <li class="nav-item"><a href="#" class="nav-link text-bg-danger">Đăng xuất</a></li>
        </ul>
    </header>
</div>
