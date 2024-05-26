<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Buu Huyen
  Date: 5/14/2024
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="px-5 mx-5">
    <div class="my-3">
        <a href="<c:url value="/admin/pointgroups"/>">
            <button type="button" class="btn btn-secondary">Quay lại</button>
        </a>
    </div>
    <c:url value="/admin/pointgroups" var="action"/>
    <div class="card">
        <div class="card-header">
            <h3 class="text-center">Thông tin điều</h3>
        </div>
        <c:if test="${errMsg != null}">
            <div class="alert alert-danger">${errMsg}</div>
        </c:if>
        <%--@elvariable id="pointgroup" type="com.tps.pojo.Pointgroup"--%>
        <form:form method="post" action="${action}" modelAttribute="pointgroup" enctype="multipart/form-data">
        <form:errors path="*" element="div" cssClass="alert alert-danger"/>
        <div class="card-body">
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="name" id="name" placeholder="Tên Điều"
                            name="name"/>
                <label for="name">Tên điều</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input path="content" class="form-control" id="content" name="content" maxlength="255"/>
                <label for="content">Nội dung</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="number" class="form-control" path="maxPoint" id="maxPoint"
                            name="maxPoint"/>
                <label for="maxPoint">Số điểm tối đa</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <button type="submit" class="btn btn-info">Lưu</button>
                <form:hidden path="id"/>
            </div>
        </div>
    </div>
    </form:form>
</div>
