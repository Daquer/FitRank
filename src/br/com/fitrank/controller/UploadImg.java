package br.com.fitrank.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.DatatypeConverter;

import br.com.fitrank.modelo.ImagemRanking;
import br.com.fitrank.service.ImagemRankingServico;


public class UploadImg extends HttpServlet {

	private static final long serialVersionUID = 5428715917240346541L;
	
	ImagemRankingServico imagemRankingServico = new ImagemRankingServico();
	
	private void inicia(HttpServletRequest request, HttpServletResponse response) {
		try {
			String img64 = request.getParameter("img64");
			String idRanking = request.getParameter("idRanking");
			
			img64 = img64.replace("data:image/png;base64,", "");
			
			byte[] imageByte = DatatypeConverter.parseBase64Binary(img64);
			
			Blob blob = null;
			
			try {
				blob = new SerialBlob(imageByte);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ImagemRanking imagemRanking = new ImagemRanking();
			imagemRanking.setId_ranking(Integer.parseInt(idRanking)); 
			imagemRanking.setImagem(blob);
			imagemRankingServico.adicionaRanking(imagemRanking);
		} catch(Exception e) {
			response.addHeader("msg", e.getMessage());
			response.setContentType("text/html;charset=UTF-8");
			response.setStatus(500);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		inicia(request, response);
	}


}
