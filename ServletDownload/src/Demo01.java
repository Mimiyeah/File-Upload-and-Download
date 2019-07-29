import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.Buffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.OutputBuffer;

import com.sun.xml.internal.bind.v2.runtime.output.Encoded;


public class Demo01 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取下载文件名
		String filename=request.getParameter("filename");
		//解决中文文件下载乱码
		filename=new String(filename.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("filename="+filename);
		//获取在tomcat里的绝对路径
		String path=getServletContext().getRealPath("download/"+filename);
		
		/**
		 * 文件名包含中文，需要进行编码处理
		 */
		//获取来访客户端类型
		String clientType=request.getHeader("User-Agent");
		if(clientType.contains("Firefox")){
			
			filename=Encodering.base64EncodeFileName(filename);
		}else{
			filename=URLEncoder.encode(filename,"UTF-8");
		}
		
		
		//弹框提示下载
		response.setHeader("Content-Disposition", "atachment;filename="+filename);
		//转化成输入流
		InputStream is=new FileInputStream(path);
		OutputStream os=response.getOutputStream();
		int len=0;
		byte[] buffer=new byte[1024];
		while((len=is.read(buffer))!=-1){
			os.write(buffer,0,len);
		}
		os.close();
		is.close();
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
