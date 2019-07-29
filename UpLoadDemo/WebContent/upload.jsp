<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传练习</title>
</head>
<body>
<form action="UploadServlet" method="post" enctype="multipart/form-data">
	选择一个文件<br>
	<input type="file" name="filename"><br>
	<br>
	<br>
	<input type="submit" value="提交">

</form>
	

</body>
</html>