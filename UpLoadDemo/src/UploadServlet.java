

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static final String Upload_Directory = "upload";
	private static int Max_sizeThreshold=1024 * 1024 * 3;
	private static int Max_filesize=1024 * 1024 * 3;
	private static int Max_size=1024 * 1024 * 3;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//检测是否为多媒体上传  判断enctype属性是否为multipart/form-data
		if(!ServletFileUpload.isMultipartContent(request)){
			//不符合条件 停止上传
			System.out.println("Error:上传失败，表单必须包含 enctype=multipart/form-data");
			PrintWriter writer=response.getWriter();
			writer.println("Error:上传失败,表单必须包含 enctype=multipart/form-data");
			writer.flush();
			return;
		}
		//配置上传参数
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//设置缓冲区大小
		factory.setSizeThreshold(Max_sizeThreshold);
		//设置临时存储目录
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload=new ServletFileUpload(factory);
		//最大文件上传值（文件大小限制）
		upload.setFileSizeMax(Max_filesize);
		//最大上传数（请求数）
		upload.setSizeMax(Max_size);
		upload.setHeaderEncoding("UTF-8");
		
		//构造临时路径路径，即相对于当前应用的目录
		String uploadpath=request.getServletContext().getRealPath("/")+Upload_Directory;
		
		File uploadDir=new File(uploadpath);
		if(!uploadDir.exists()){
			uploadDir.mkdir();//不存在该路径 则创建
		}
		
		try {
			//解析请求内容 提取文件数据
			List<FileItem> formItems=upload.parseRequest(request);
			
			if(formItems !=null && formItems.size()>0){
				for(FileItem item:formItems){
					//判断是普通文本表单字段，还是一个文件表单字段
					if(!item.isFormField()){
						//是文件时
						String filename=new File(item.getName()).getName();
						String filepath=uploadpath + File.separator + filename;
						File storeFile = new File(filepath);
						System.out.println(filepath);
						item.write(storeFile);
						request.setAttribute("message", "文件上传成功");
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", "错误信息"+e.getMessage());
		}
		request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
