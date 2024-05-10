<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <a class="btn btn-secondary rounded-pill" href="${pageContext.request.contextPath}/new" >
                    ADD MEMBERS <span class="fw-bold fs-6">+</span>
                </a>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-9">
                    <table class="table table-striped">
                        <thead class="table-bordered">
                        <tr>
                            <th>
                                <label>
                                    <input type="checkbox" id="all" onchange="checkAll(this)"/>
                                </label>
                            </th>
                            <c:forEach items="${field}" var="f">
                                <th>
                                        ${f}
                                </th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="u">
                            <td>
                                <label>
                                    <input type="checkbox" id="element${u.getId()}" class="element">
                                </label>
                            </td>
                            <td>
                                    ${u.getId()}
                            </td>
                            <td>
                                    ${u.getUsername()}
                            </td>
                            <td>
                                    ${u.getFirstName()}
                            </td>
                            <td>
                                    ${u.getLastName()}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.getIsActive()}">
                                        Activate
                                    </c:when>
                                    <c:otherwise>
                                        Deactivate
                                    </c:otherwise>
                                </c:choose>
                            </td>
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
<script>
    function checkAll(source) {
        const checkboxes = document.getElementsByClassName('element');
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }
</script>