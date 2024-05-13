<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 13/05/2024
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="px-5 mx-5">
    <div class="my-3">
        <a href="<c:url value="/admin/users"/>">
            <button type="button" class="btn btn-secondary">Quay lại</button>
        </a>
    </div>
    <c:if test="${alert != null}">
        <div class="alert alert-info">
            ${alert}
        </div>
    </c:if>
    <c:url value="/admin/assistants/${assistant.id}" var="action"/>
    <div class="card">
        <div class="card-header">
            <h3 class="text-center">Thông tin người dùng</h3>
        </div>
        <c:if test="${errMsg != null}">
            <div class="alert alert-danger">${errMsg}</div>
        </c:if>
        <%--@elvariable id="assistant" type="com.tps.pojo.Assistant"--%>
        <form:form method="put" action="${action}" modelAttribute="assistant" enctype="multipart/form-data">
        <form:errors path="*" element="div" cssClass="alert alert-danger"/>
        <div class="card-body">
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="user.username" id="username" placeholder="Tên đăng nhập"
                            name="username"/>
                <label for="username">Tên đăng nhập</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:password class="form-control" path="user.password" id="password" placeholder="Mật khẩu"
                               name="password"/>
                <label for="password">Mật khẩu</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="user.firstName" id="firstName" placeholder="Họ"
                            name="firstName"/>
                <label for="firstName">Họ</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="user.lastName" id="lastName" placeholder="Tên"
                            name="lastName"/>
                <label for="lastName">Tên</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" path="user.email" id="email" placeholder="Email" name="email"/>
                <label for="email">Email</label>
            </div>
            <div class="form-floating">
                <form:select class="form-select" path="faculty.id" id="faculty" name="faculty">
                    <c:forEach items="${faculties}" var="f">
                        <c:choose>
                            <c:when test="${f.id == assistant.faculty.id}">
                                <option value="${f.id}" selected>${f.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${f.id}">${f.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
                <label for="faculty" class="form-label">Faculty</label>
            </div>
            <div class="mb-3 mt-3">
                <label for="file">Ảnh đại diện</label>
                <input type="file" accept=".png,.jpg" class="form-control" path="file" id="file" name="file"/>
                <c:if test="${assistant.user.avatar != null}">
                    <img src="${assistant.user.avatar }" width="200"/>
                </c:if>
            </div>
            <div class="form-check form-switch mb-3 mt-3">
                <form:checkbox class="form-check-input" id="isActive" path="user.isActive" name="isActive" />
                <label class="form-check-label" for="isActive">Is Active</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <button type="submit" class="btn btn-info">Lưu</button>
                <form:hidden path="id"/>
            </div>
        </div>
    </div>
    </form:form>
</div>