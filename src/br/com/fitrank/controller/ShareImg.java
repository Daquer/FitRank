package br.com.fitrank.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import br.com.fitrank.modelo.ImagemRanking;
import br.com.fitrank.service.ImagemRankingServico;

/**
 * Servlet implementation class ShareImg
 */
public class ShareImg extends HttpServlet {
	
	private static final long serialVersionUID = 6784564045213105624L;
	
	ImagemRankingServico imagemRankingServico = new ImagemRankingServico();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgId = request.getParameter("id");
		
//		byte[] b = null;
		Blob blob;
		byte[] blobAsBytes = null;
		
		
		ImagemRanking imagemRanking = imagemRankingServico.leRanking(Integer.valueOf(imgId));
		
		response.setContentType("image/png");
		
		if (imagemRanking.getImagem() != null){
			try {
				
					blob = new SerialBlob(imagemRanking.getImagem());
	//			blob = new SerialBlob(b);
					int blobLength = (int) blob.length(); 
					blobAsBytes = blob.getBytes(1, blobLength);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			BufferedImage image = createImageFromBytes(blobAsBytes);
			ImageIO.write(image, "png", response.getOutputStream());
		}
		
	    response.getOutputStream().close();
		
	}
	
	private BufferedImage createImageFromBytes(byte[] imageData) {
	    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	    try {
	        return ImageIO.read(bais);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
