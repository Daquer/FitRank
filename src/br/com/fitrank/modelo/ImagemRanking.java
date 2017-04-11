package br.com.fitrank.modelo;

import java.sql.Blob;

public class ImagemRanking {
	
	private int idRanking;
	
	private Blob imagem;

	public int getIdRanking() {
		return idRanking;
	}

	public void setIdRanking(int idRanking) {
		this.idRanking = idRanking;
	}

	public Blob getImagem() {
		return imagem;
	}

	public void setImagem(Blob imagem) {
		this.imagem = imagem;
	}
}
