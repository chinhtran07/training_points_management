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
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar">
            <nav class="nav d-flex flex-column">
                <input type="button" class="btn btn-primary mb-1" id="faculty" value="Theo khoa" onclick="statsByFaculty()"/>
                <input type="button" class="btn btn-primary mb-1" id="class" value="Theo lớp" onclick="statsByClass()"/>
                <input type="button" class="btn btn-primary mb-1" id="rank" value="Theo thành tích" onclick="statsByRank()"/>
            </nav>
        </div>
        <div class="col-md-10">
            <div class="d-flex" id="filter">

            </div>
            <div class="row">
                <div class="col-md-5 col-12">
                    <table class="table table-striped">
                        <thead>

                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <div class="col-md-7 col-12">
                    <canvas id="bar"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function statsByFaculty() {
        // Update filter
        document.getElementById('filter').innerHTML = '<input type="text" placeholder="Nhập khoa" class="form-control" />';

        // Update table headers
        document.getElementById('table-head').innerHTML = `
            <tr>
                <th>Khoa</th>
                <th>Số lượng sinh viên</th>
                <th>Thành tích</th>
            </tr>
        `;

        // Update table body with sample data
        document.getElementById('table-body').innerHTML = `
            <tr>
                <td>Khoa Công nghệ thông tin</td>
                <td>200</td>
                <td>Giỏi</td>
            </tr>
            <tr>
                <td>Khoa Kinh tế</td>
                <td>150</td>
                <td>Khá</td>
            </tr>
        `;
    }

    function statsByClass() {

    }

    function statsByRank() {

    }
</script>