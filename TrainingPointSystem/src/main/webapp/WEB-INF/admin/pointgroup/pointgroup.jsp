<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Buu Huyen
  Date: 5/14/2024
  Time: 11:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid px-2 pb-5">
    <div class="bg-light px-2 shadow">
        <div class="d-flex justify-content-between mb-md-5">
            <div>
                <h2> Select members to change </h2>
            </div>
            <div>
                <a class="btn btn-secondary rounded-pill"
                   href="${pageContext.request.contextPath}/admin/pointgroups/new">
                    ADD MEMBERS <span class="fw-bold fs-6">+</span>
                </a>
            </div>
        </div>
        <div class="container-fluid min-vh-100">
            <div class="row">
                <div class="col-md-12 col-12">
                    <table class="table table-striped">
                        <thead class="table-bordered">
                        <tr>
                            <c:forEach items="${fields}" var="f">
                                <th>
                                        ${f}
                                </th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <jsp:useBean id="pointGroups" scope="request" type="java.util.List"/>
                        <c:forEach items="${pointGroups}" var="p">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/pointgroups/${p.id}">
                                            ${p.id}
                                    </a>
                                </td>
                                <td>
                                        ${p.name}
                                </td>
                                <td>
                                        ${p.content}
                                </td>
                                <td>
                                        ${p.maxPoint}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function checkAll(source) {
        const checkboxes = document.getElementsByClassName('element');
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }
</script>