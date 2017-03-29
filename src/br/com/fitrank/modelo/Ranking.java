package br.com.fitrank.modelo;

import java.sql.Timestamp;

public class Ranking {
	
	private int id_ranking;
	
	private int id_configuracao;
	
	private Timestamp data_ranking;

	public int getId_ranking() {
		return id_ranking;
	}

	public void setId_ranking(int id_ranking) {
		this.id_ranking = id_ranking;
	}

	public int getId_configuracao() {
		return id_configuracao;
	}

	public void setId_configuracao(int id_configuracao) {
		this.id_configuracao = id_configuracao;
	}

	public Timestamp getData_ranking() {
		return data_ranking;
	}

	public void setData_ranking(Timestamp data) {
		this.data_ranking = data;
	}
	
	
}
