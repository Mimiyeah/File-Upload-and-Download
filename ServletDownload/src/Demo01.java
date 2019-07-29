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
		//��ȡ�����ļ���
		String filename=request.getParameter("filename");
		//��������ļ���������
		filename=new String(filename.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("filename="+filename);
		//��ȡ��tomcat��ľ���·��
		String path=getServletContext().getRealPath("download/"+filename);
		
		/**
		 * �ļ����������ģ���Ҫ���б��봦��
		 */
		//��ȡ���ÿͻ�������
		String clientType=request.getHeader("User-Agent");
		if(clientType.contains("Firefox")){
			
			filename=Encodering.base64EncodeFileName(filename);
		}else{
			filename=URLEncoder.encode(filename,"UTF-8");
		}
		
		
		//������ʾ����
		response.setHeader("Content-Disposition", "atachment;filename="+filename);
		//ת����������
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
