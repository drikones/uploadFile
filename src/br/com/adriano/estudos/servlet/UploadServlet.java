package br.com.adriano.estudos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.adriano.estudos.model.Arquivo;

@WebServlet(name = "UploadServlet", urlPatterns = {"/sobe"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5172444605435430589L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Arquivo arquivo = new Arquivo();
		Part filePart = req.getPart("arquivo");
		
		System.out.println("Nome do arquivo: "+getFileName(filePart));
		arquivo.upload(	"/Users/adrianorocha/Documents/estudos redes", getFileName(filePart), req.getInputStream());
	}
	
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	
}
