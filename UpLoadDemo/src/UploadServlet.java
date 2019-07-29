

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
		//����Ƿ�Ϊ��ý���ϴ�  �ж�enctype�����Ƿ�Ϊmultipart/form-data
		if(!ServletFileUpload.isMultipartContent(request)){
			//���������� ֹͣ�ϴ�
			System.out.println("Error:�ϴ�ʧ�ܣ���������� enctype=multipart/form-data");
			PrintWriter writer=response.getWriter();
			writer.println("Error:�ϴ�ʧ��,��������� enctype=multipart/form-data");
			writer.flush();
			return;
		}
		//�����ϴ�����
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//���û�������С
		factory.setSizeThreshold(Max_sizeThreshold);
		//������ʱ�洢Ŀ¼
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload=new ServletFileUpload(factory);
		//����ļ��ϴ�ֵ���ļ���С���ƣ�
		upload.setFileSizeMax(Max_filesize);
		//����ϴ�������������
		upload.setSizeMax(Max_size);
		upload.setHeaderEncoding("UTF-8");
		
		//������ʱ·��·����������ڵ�ǰӦ�õ�Ŀ¼
		String uploadpath=request.getServletContext().getRealPath("/")+Upload_Directory;
		
		File uploadDir=new File(uploadpath);
		if(!uploadDir.exists()){
			uploadDir.mkdir();//�����ڸ�·�� �򴴽�
		}
		
		try {
			//������������ ��ȡ�ļ�����
			List<FileItem> formItems=upload.parseRequest(request);
			
			if(formItems !=null && formItems.size()>0){
				for(FileItem item:formItems){
					//�ж�����ͨ�ı����ֶΣ�����һ���ļ����ֶ�
					if(!item.isFormField()){
						//���ļ�ʱ
						String filename=new File(item.getName()).getName();
						String filepath=uploadpath + File.separator + filename;
						File storeFile = new File(filepath);
						System.out.println(filepath);
						item.write(storeFile);
						request.setAttribute("message", "�ļ��ϴ��ɹ�");
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", "������Ϣ"+e.getMessage());
		}
		request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
