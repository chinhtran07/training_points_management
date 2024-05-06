<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 24/04/2024
  Time: 09:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<div class="alert alert-info">
    <h1>Xin chào, ${pageContext.request.userPrincipal.name}</h1>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-7">
            <div class="card">
                <div class="card-header bg-info fs-4 fw-bold">
                    CLASS
                </div>
                <div class="card-body">
                    <c:forEach items="${pojos}" var="p">
                        <div class="row py-2 border-bottom">
                            <div class="col-sm-8 fw-medium">
                                <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/${p.toLowerCase()}">${p}</a>
                            </div>
                            <div class="col-sm-4">
                                <a class="p-2 btn btn-success" href="${pageContext.request.contextPath}/admin/${p.toLowerCase()}/new">
                                    Add
                                </a>
                                <a class="p-2 btn btn-warning" href="${pageContext.request.contextPath}/admin/${p.toLowerCase()}/alter">
                                    Change
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-md-5">
            <div class="card">
                <div class="card-header">
                    Recent actions
                </div>
                <div class="card-body">

                </div>
            </div>
        </div>
    </div>
</div>
