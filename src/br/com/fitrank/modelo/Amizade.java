package br.com.fitrank.modelo;

import java.sql.Timestamp;

/** Entity(name="amizade")
 *
 */
public class Amizade {
//	Column(name="id_pessoa", FK="FK_ID_PESSOA_AMIZADE")
	private String idPessoa;
//	Column(name="id_amigo", FK="FK_ID_AMIGO_AMIZADE)
	private String idAmigo;
//	Column(name="data_amizade")
	private Timestamp dataAmizade;
	
	private String ativo;
	
	public String getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getIdAmigo() {
		return idAmigo;
	}
	public void setId_amigo(String idAmigo) {
		this.idAmigo = idAmigo;
	}
	public Timestamp getDataAmizade() {
		return dataAmizade;
	}
	public void setDataAmizade(Timestamp dataAmizade) {
		this.dataAmizade = dataAmizade;
	}
	
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
