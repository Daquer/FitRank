package br.com.fitrank.modelo;

import java.sql.Timestamp;

/** Entity(name="amizade")
 *
 */
public class Amizade {
//	Column(name="id_pessoa", FK="FK_ID_PESSOA_AMIZADE")
	private String id_pessoa;
//	Column(name="id_amigo", FK="FK_ID_AMIGO_AMIZADE)
	private String id_amigo;
//	Column(name="data_amizade")
	private Timestamp data_amizade;
	
	private String ativo;
	
	public String getId_pessoa() {
		return id_pessoa;
	}
	public void setId_pessoa(String id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	public String getId_amigo() {
		return id_amigo;
	}
	public void setId_amigo(String id_amigo) {
		this.id_amigo = id_amigo;
	}
	public Timestamp getData_amizade() {
		return data_amizade;
	}
	public void setData_amizade(Timestamp data_amizade) {
		this.data_amizade = data_amizade;
	}
	
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
