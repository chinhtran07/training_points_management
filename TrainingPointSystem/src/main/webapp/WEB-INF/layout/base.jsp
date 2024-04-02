<%--
  Created by IntelliJ IDEA.
  User: chinh
  Date: 02/04/2024
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <title><tiles:insertAttribute name="title"/></title>
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="content"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
