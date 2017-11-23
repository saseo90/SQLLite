<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- onepage-topbar -->
<tiles:insertAttribute name="onepage-topbar"/>
<!-- /.onepage-topbar -->
<head>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome 4.5.0 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css"> -->
  <!-- Ionicons 2.0.1 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/Ionicons/2.0.1/css/ionicons.min.css">
  <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css"> -->
  <!-- Theme style -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/dist/css/AdminLTE.css">
</head>

<!-- onepage-body -->
<tiles:insertAttribute name="onepage-body"/>
<!-- /.onepage-body -->

</html>