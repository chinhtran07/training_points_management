<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <div class="row">
                <div class="col-md-9">
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
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/assistants/${u[0]}">
                                            ${u[0]}
                                    </a>
                                </td>
                                <td>
                                        ${u[1]}
                                </td>
                                <td>
                                        ${u[2]}
                                </td>
                                <td>
                                        ${u[3]}
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u[4]}">
                                            Activate
                                        </c:when>
                                        <c:otherwise>
                                            Deactivate
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                        ${u[5]}
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/assistants/delete" method="post">
                                        <input type="hidden" name="id" value="${u[0]}"/>
                                        <input type="submit" value="Delete" class="btn btn-danger">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-2">
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
    </div>
</div>