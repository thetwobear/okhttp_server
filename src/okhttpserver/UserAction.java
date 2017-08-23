package okhttpserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * 
 */
public class UserAction extends ActionSupport {

	private String userName;
	private String passWord;
	public File mPhoto;// 客户端发送的文件“键名”必须与其一致；
	public String mPhotoFileName;// 文件名格式也必须是mPhoto+FileName
	private HttpServletResponse response = ServletActionContext.getResponse();

	/**
	 * 模拟登陆
	 * 
	 * @return
	 * @throws IOException
	 */
	public String login() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("SessionId=" + request.getSession().getId());
		System.out.println("userName=" + userName + "  password=" + passWord);
		PrintWriter writer = response.getWriter();
		writer.write("login success");
		writer.flush();
		return null;
	}

	/**
	 * 客户端模拟发送String
	 * 
	 * @return
	 * @throws IOException
	 */
	public String doPostString() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("SessionId=" + request.getSession().getId());
		ServletInputStream is = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		int len = 0;
		byte[] bytes = new byte[1024];
		while ((len = is.read(bytes)) != -1) {
			//sb.append(new String(bytes, 0, len));
			
			
		}

		System.out.println(sb.toString());

		PrintWriter writer = response.getWriter();
		writer.write("post string success," + "and you send this text:\n"
				+ sb.toString());
		writer.flush();
		return null;
	}

	/**
	 * 客户端模拟发送File 1
	 * Retrofit2+RxJava2+OkHttp3.8
	 * @return
	 * @throws IOException
	 */
	public String doPostFile() throws IOException {
		if (null == mPhoto) {
			System.out.println(mPhotoFileName + "  is null!");
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("SessionId=" + request.getSession().getId());
		ServletInputStream is = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		// 获取到的文件存储目录
		// 具体目录：F:\learn\okHttp\apache-tomcat-7.0.78-windows-x64\apache-tomcat-7.0.78\webapps\okhttp_server\files
		String dir = ServletActionContext.getServletContext().getRealPath(
				"files");
		File file = new File(dir, System.currentTimeMillis() + ".jpg");
		
		//
		FileUtils.copyFile(mPhoto, file);
		
		//OkHttp3.8
//		FileOutputStream os = new FileOutputStream(file);
//		int len = 0;
//		byte[] bytes = new byte[1024];
//		while ((len = is.read(bytes)) != -1) {
//			os.write(bytes, 0, len);
//		}
//		os.flush();
//		os.close();
		
		PrintWriter writer = response.getWriter();
		writer.write("post file success");
		writer.flush();
		return null;
	}

	/**
	 * 客户端模拟发送File 2
	 * OkHttp3.8
	 * @return
	 * @throws IOException
	 */
	public String okdoPostFile() throws IOException {
		if (null == mPhoto) {
			System.out.println(mPhotoFileName + "  is null!");
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("SessionId=" + request.getSession().getId());
		ServletInputStream is = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		// 获取到的文件存储目录
		// 具体目录：F:\learn\okHttp\apache-tomcat-7.0.78-windows-x64\apache-tomcat-7.0.78\webapps\okhttp_server\files
		String dir = ServletActionContext.getServletContext().getRealPath(
				"files");
		File file = new File(dir, System.currentTimeMillis() + ".jpg");
		FileOutputStream os = new FileOutputStream(file);
		int len = 0;
		byte[] bytes = new byte[1024];
		while ((len = is.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		os.flush();
		os.close();
		PrintWriter writer = response.getWriter();
		writer.write("post file success");
		writer.flush();
		return null;
	}
	
	/**
	 * 客户端模拟上传表单
	 * 
	 * @return
	 */
	public String upLoadInfo() throws IOException {
		System.out.println("userName=" + userName + "  password=" + passWord);

		if (null == mPhoto) {
			System.out.println(mPhotoFileName + "is null!");
		}
		String dir = ServletActionContext.getServletContext().getRealPath(
				"files");
		File file = new File(dir, System.currentTimeMillis() + ".jpg");
		FileUtils.copyFile(mPhoto, file);
		
		String showText = "{userName:" + userName + "  password:" + passWord
				+ "}";
		PrintWriter writer = response.getWriter();
		writer.write("post info success" + "\n" + showText);
		writer.flush();
		return null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
