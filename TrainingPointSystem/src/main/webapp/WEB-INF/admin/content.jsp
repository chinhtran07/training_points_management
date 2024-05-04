<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 03/05/2024
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container-fluid row">
    <div class="col-sm-10">
        <table class="table table-bordered border-opacity-25">
            <tr class="bg-info">
                <th> ID</th>
                <th>Name</th>
            </tr>
            <c:forEach items="${pointGroups}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-sm-2">
        <h1>Control</h1>
    </div>
</div>