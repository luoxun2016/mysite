<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Jackson - Web Developer</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${rc.contextPath}/static/plugins/bootstrap/css/bootstrap.min.css">
  <!-- BootstrapValidator 0.4.5 -->
  <link rel="stylesheet" href="${rc.contextPath}/static/plugins/bootstrapvalidator/dist/css/bootstrapValidator.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${rc.contextPath}/static/plugins/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${rc.contextPath}/static/plugins/ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${rc.contextPath}/static/style/base.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${rc.contextPath}/static/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>Jackson</b> site</a>
  </div>
  <c:if test="${error != null}">
	<div class="alert alert-danger alert-dismissible">
	  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	  <h4><i class="icon fa fa-ban"></i> 错误!</h4>
	  ${error}
	</div>
  </c:if>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">开始您的会话</p>

    <form id="login_form" action="${rc.contextPath}/dologin" method="post">
      <div class="form-group has-feedback">
        <input type="email" class="form-control" name="username" value="${user.username}" placeholder="邮箱">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	    <form:errors path="user.username"/>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" value="${user.password}" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	    <form:errors path="user.password"/>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> 记住密码
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
      <div class="row">
      	
      </div>
    </form>
    
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="${rc.contextPath}/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${rc.contextPath}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- BootstrapValidator 0.4.5 -->
<script src="${rc.contextPath}/static/plugins/bootstrapvalidator/dist/js/bootstrapValidator.min.js"></script>
<!-- iCheck -->
<script src="${rc.contextPath}/static/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    
    $('#login_form').bootstrapValidator({
        fields: {
            username: {
                validators: {
                	notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址不合法'
                    }
                }
            },
            password: {
                validators: {
                	notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '密码长度应在6~16之间'
                    }
                }
            }
        }
    });
  });
</script>
</body>
</html>
