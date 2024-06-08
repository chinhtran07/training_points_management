<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%--
  Created by IntelliJ IDEA.
  User: Buu Huyen
  Date: 5/14/2024
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 col-12">
            <button id="btnToanTruong" class="btn btn-primary" onclick="showContent('toanTruongContent')">Toàn trường
            </button>
            <button id="btnTheoKhoa" class="btn btn-primary my-2" onclick="showContentWithFaculty('theoKhoaContent')">
                Theo khoa
            </button>
            <button id="btnTheoThanhTich" class="btn btn-primary"
                    onclick="showContentWithFaculty('theoThanhTichContent')">Theo thành tích
            </button>
        </div>
        <div class="col-md-10">
            <div id="facultyInputDiv" style="display:none;">
                <label for="facultySelect">Khoa</label>
                <select id="facultySelect" class="form-select my-2">
                    <c:forEach items="${faculties}" var="f">
                        <option value="${f.id}">${f.name}</option>
                    </c:forEach>
                </select>
                <button id="btnSubmitFaculty" class="btn btn-secondary" onclick="submitFaculty()">Gửi</button>
            </div>
            <h1 id="contentTitle" class="text-center"></h1>
            <table id="contentTable" class="table table-striped">
                <thead id="contentThead">
                </thead>
                <tbody id="contentTbody">
                </tbody>
            </table>
            <hr/>
            <div>
                <canvas id="chart"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/stats.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>