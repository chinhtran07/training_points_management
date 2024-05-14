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
                <a class="btn btn-secondary rounded-pill" href="${pageContext.request.contextPath}/admin/assistants/new">
                    ADD MEMBERS <span class="fw-bold fs-6">+</span>
                </a>
            </div>
        </div>
        <div class="container-fluid min-vh-100">
            <div class="row">
                <div class="col-md-9">
                    <div>
                        <label class="form-label" for="action"></label>
                        <select class="form-select" id="action" name="action">
                            <option value="delete">delete</option>
                            <option value="filter">filter</option>
                        </select>
                        <div>
                            <div>
                                <button class="btn btn-success" type="button" onclick="">Action</button>
                            </div>
                        </div>
                    </div>
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
                            <tr>
                                <td>
                                    <label>
                                        <input type="checkbox" id="element${u[0]}" class="element">
                                    </label>
                                </td>
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
<script>
    function checkAll(source) {
        const checkboxes = document.getElementsByClassName('element');
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }
    
    function performAction() {
        const action = document.getElementById('action').value;
        if (action === 'delete') {
            const selectedUserIds = [];
            const checkboxes = document.querySelectorAll('.element:checked')
            checkboxes.forEach((checkbox) => {
                selectedUserIds.push(checkbox.value);
            })

            if(selectedUserIds.length === 0) {
                alert("Chọn ít nhất 1 người dùng để xóa");
                return;
            }

            fetch('${pageContext.request.contextPath}/admin/assistants', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({userIds: selectedUserIds})
            })
                .then(response => response.json())
                .then(data =>{
                    if(data.status === 204) {
                        alert("Xóa thành công");
                        location.reload();
                    } else {
                        alert("Lỗi");
                    }
                })
                .catch(error => {
                    console.error(error);
                    alert("Lỗi");
                })
        }
    }
</script>