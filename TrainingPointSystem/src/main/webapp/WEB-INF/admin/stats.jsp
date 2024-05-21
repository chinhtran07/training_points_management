<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Buu Huyen
  Date: 5/14/2024
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container">
    <div class="d-flex justify-content-around">
        <div class="form-group">
            <label for="facultyId" class="form-label">Khoa: </label>
            <select class="form-select" id="facultyId">
                <c:forEach items="${faculties}" var="f">
                    <option value="${f.id}">${f.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="classId" class="form-label">Lớp: </label>
            <select class="form-select" id="classId">
                <option></option>
            </select>
        </div>
        <div class="form-group">
            <label for="target" class="form-label">Thành tích: </label>
            <select class="form-select" id="target">
                <option value=""></option>
            </select>
        </div>
        <button type="button" class="btn btn-primary" onclick="">
            Submit
        </button>
    </div>
    <div class="row">
        <div class="col-md-7">
            <table>
                <tr></tr>
            </table>
        </div>
        <div class="col-md-5">
            <canvas class="stats"></canvas>
        </div>
    </div>
    <script>

    </script>
</div>