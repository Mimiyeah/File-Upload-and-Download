文件上传
	准备：
		引入两个依赖包commons-fileupload-1.3.2、commons-io-2.5.jar。
	步骤：
		1.创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录。
		2.使用DiskFileItemFactory 对象创建ServletFileUpload对象，并设置上传文件的大小限制。
		3.调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
		4.对list进行迭代，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件:若是则上传
	
文件下载：
	两种方式：
		1.默认：直接以超链接方式下载，tomcat中有默认的DefaultServlet专门处理静态资源
		2.编码设置下载到某一位置，设有弹框自主进行选择下载位置
