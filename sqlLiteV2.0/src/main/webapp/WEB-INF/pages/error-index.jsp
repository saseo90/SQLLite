<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- main-topbar -->
<tiles:insertAttribute name="error-topbar"/>
<!-- /.main-topbar -->
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
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/AdminLTE/dist/css/skins/_all-skins.css">
</head>

<body class="hold-transition skin-green-light sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

  <!-- Main Header -->
  <tiles:insertAttribute name="main-header"/>
  <!-- /.Main Header -->
 
  <!-- Content Wrapper. Contains page content  -->
  <tiles:insertAttribute name="error-wrapper"/>
  <!-- /.content-wrapper -->
  
  <!-- Main Footer -->
  <tiles:insertAttribute name="main-footer" />
  <!-- /.Main Footer -->
  
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/resources/AdminLTE/dist/js/app.min.js"></script>
</body>
</html>