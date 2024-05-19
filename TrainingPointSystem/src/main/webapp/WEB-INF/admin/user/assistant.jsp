<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 11/05/2024
  Time: 02:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container-fluid px-2 pb-5">
    <div class="bg-light px-2 shadow">
        <div class="d-flex justify-content-between mb-md-5">
            <div>
                <h2> Select members to change </h2>
            </div>
            <div>
                <a class="btn btn-secondary rounded-pill"
                   href="${pageContext.request.contextPath}/admin/assistants/new">
                    ADD MEMBERS <span class="fw-bold fs-6">+</span>
                </a>
            </div>
        </div>
        <div class="container-fluid min-vh-100">
            <table class="table table-striped">
                <thead class="table-bordered">
                <tr>
                    <c:forEach items="${field}" var="f">
                        <th>
                                ${f}
                        </th>
                    </c:forEach>
                    <th>
                        Action
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td class="text-decorator-none">
                            <a href="${pageContext.request.contextPath}/admin/assistants/${u.id}">${u.id}</a>
                        </td>
                        <td>${u.username}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>${u.gender}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.dob != null}">
                                    <fmt:formatDate value="${u.dob}" pattern="dd-MM-yyyy"/>
                                </c:when>
                                <c:otherwise>
                                    N/A
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${u.faculty}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.isActive}">
                                    active
                                </c:when>
                                <c:otherwise>
                                    inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/assistants/delete"
                                  method="post">
                                <input type="hidden" name="id" value="${u.id}"/>
                                <input type="submit" value="Delete" class="btn btn-danger">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
