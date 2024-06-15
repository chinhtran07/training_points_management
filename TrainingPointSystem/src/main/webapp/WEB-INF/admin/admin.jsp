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
<div class="container-fluid">
    <form>
        <div class="d-flex justify-center">
            <div class="p-2">
                <label for="years">Năm: </label>
                <select id="years" name="year" class="form-select">
                    <option value="">Chọn năm</option>
                    <c:forEach items="${years}" var="year">
                        <option value="${year}" >${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="p-2">
                <label for="semesters">Học kì: </label>
                <select id="semesters" name="semester" class="form-select">
                    <option value="">Chọn học kỳ</option>
                    <c:forEach items="${semesters}" var="semester">
                        <option value="${semester.id}">${semester.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
</div>
