package br.com.adriano.estudos.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.com.adriano.estudos.listener.UploadProgressListener;


public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5172444605435430589L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
		out.println("Hello<br/>");

		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			out.println("You are not trying to upload<br/>");
			return;
		}
		out.println("You are trying to upload<br/>");

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setSizeMax(MAX_UPLOAD_IN_MEGS * 1024 * 1024);
		
		UploadProgressListener uploadProgressListener = new UploadProgressListener();
		upload.setProgressListener(uploadProgressListener);

		HttpSession session = request.getSession();
		session.setAttribute("uploadProgressListener", uploadProgressListener);
		
		try {
			List<FileItem> fields = upload.parseRequest(request);
			out.println("Number of fields: " + fields.size() + "<br/><br/>");
			Iterator<FileItem> it = fields.iterator();
			if (!it.hasNext()) {
				out.println("No fields found");
				return;
			}
			out.println("<table border=\"1\">");
			while (it.hasNext()) {
				out.println("<tr>");
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					out.println("<td>regular form field</td><td>FIELD NAME: " + fileItem.getFieldName() + 
							"<br/>STRING: " + fileItem.getString()
							);
					out.println("</td>");
				} else {
					out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
//							"<br/>STRING: " + fileItem.getString() +
							"<br/>NAME: " + fileItem.getName() +
							"<br/>CONTENT TYPE: " + fileItem.getContentType() +
							"<br/>SIZE (BYTES): " + fileItem.getSize() +
							"<br/>TO STRING: " + fileItem.toString()
							);
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
	        out.println("</html>");
		} catch (FileUploadException e) {
			out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	

	
	
}
