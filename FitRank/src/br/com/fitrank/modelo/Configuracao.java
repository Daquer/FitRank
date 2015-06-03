package br.com.fitrank.modelo;

import java.util.Date;

/** Entity(name="configuracao")
 *
 */
public class Configuracao {
//	Column(name="id_configuracao", Primary Key)
	private Integer idConfiguracao;
//	Column(name="modalidade")
	private String modalidade;
//	Column(name="dia_noite")
	private Character diaNoite;
//	Column(name="intervalo_data")
	private Date intervaloData;
//	Column(name="favorito")
	private Boolean favorito;
//	Column(name="padrao_modalidade")
	private Boolean padraoModalidade;
//	Column(name="id_pessoa", FK="FK1_PESSOA_CONF")
	private String idPessoa;
	
	public Integer getIdConfiguracao() {
		return idConfiguracao;
	}
	public void setIdConfiguracao(Integer idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	public Character getDiaNoite() {
		return diaNoite;
	}
	public void setDiaNoite(Character diaNoite) {
		this.diaNoite = diaNoite;
	}
	public Date getIntervaloData() {
		return intervaloData;
	}
	public void setIntervaloData(Date intervaloData) {
		this.intervaloData = intervaloData;
	}
	public Boolean getFavorito() {
		return favorito;
	}
	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}
	public Boolean getPadraoModalidade() {
		return padraoModalidade;
	}
	public void setPadraoModalidade(Boolean padraoModalidade) {
		this.padraoModalidade = padraoModalidade;
	}
	public String getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}
}
