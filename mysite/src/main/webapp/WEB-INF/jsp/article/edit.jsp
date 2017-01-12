<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://www.ank.com/tmpl" prefix="tmpl" %>

<tmpl:override name="header">
	<!-- Select2 -->
	<link rel="stylesheet" href="${rc.contextPath}/static/plugins/select2/select2.min.css">
</tmpl:override>


<tmpl:override name="content">
  <div class="row">
    <!-- left column -->
	<div class="col-md-3">
		<a href="${rc.contextPath}/article/list" class="btn btn-primary btn-block margin-bottom">æç« åè¡¨</a>
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<!-- /.col -->
    <div class="col-md-9">
      <!-- general form elements -->
      <div class="box box-primary">
        <div class="box-header with-border">
          <h3 class="box-title">åå¸æç« </h3>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <form role="form">
          <div class="box-body">
            <div class="form-group">
              <label for="exampleInputEmail1">æç« æ é¢</label>
              <input type="email" class="form-control" id="exampleInputEmail1" placeholder="è¾å¥åå®¢æ é¢">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">æç« åç±»</label>
							<select class="form-control select2" style="width: 100%;">
								<option selected="selected">éæ©æç« åç±»</option>
								<option>Java</option>
								<option>Python</option>
								<option>PHP</option>
								<option>Linux</option>
							</select>
            </div>
            <div class="form-group">
							<label for="exampleInputPassword1">æç« ç±»å®¹</label>
							<textarea class="form-control" id="editor_content" name="editor_content" rows="10" cols="80">
							</textarea>
            </div>
						<div class="form-group">
              <label for="exampleInputEmail1">æ·»å ç­é¨æ ç­¾</label>
							<input type="email" class="form-control" id="exampleInputEmail1" placeholder="è¾å¥æ ç­¾ç¨éå·åå¼ï¼ä¸è¶è¿äºä¸ª">
            </div>
						<div class="form-group">
                <div class="btn btn-default btn-file">
                  <i class="fa fa-paperclip"></i> éæ©éä»¶
                  <input name="attachment" type="file">
                </div>
                <p class="help-block">Max. 32MB</p>
            </div>
          </div>
          <!-- /.box-body -->

          <div class="box-footer">
							<div class="pull-right">
                <button type="button" class="btn btn-default"><i class="fa fa-save"></i> ä¿å­</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-send"></i> åå¸</button>
              </div>
						<button type="reset" class="btn btn-default"><i class="fa fa-times"></i> åæ¶</button>
          </div>
        </form>
      </div>
      <!-- /.box -->
    </div>
  </div>
</tmpl:override>

<tmpl:override name="bottom">
<!-- Select2 -->
<script src="${rc.contextPath}/static/plugins/select2/select2.full.min.js' %}"></script>
<!-- CK Editor -->
<script src="${rc.contextPath}/static/plugins/ckeditor/ckeditor.js"></script>
<script>
  $(function () {
    $(".select2").select2();
      // Replace the <textarea id="editor_content"> with a CKEditor
      // instance, using default configuration.
      CKEDITOR.replace('editor_content',{'skin':'moono-lisa',extraPlugins: 'codesnippet',codeSnippet_theme: 'zenburn'});
  });
</script>
</tmpl:override>

<jsp:include page="../comm/base_template.jsp"/>