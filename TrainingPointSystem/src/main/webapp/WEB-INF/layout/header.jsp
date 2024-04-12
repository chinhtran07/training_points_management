<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 02/04/2024
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <img class="navbar-brand" src="https://res.cloudinary.com/dk4uoxtsx/image/upload/v1712234809/LOGO-TRUONGV21-12-2018-01-300x300_jexwp0.png" width="70">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="#">Trang chu1</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/login" />">Dang nhap</a>
                </li>
            </ul>
        </div>
    </div>
</nav>