package br.com.fitrank.modelo;

import java.sql.Timestamp;
import java.util.List;

/** Entity(name="pessoa")
 *
 */
public class Pessoa {
//	Column(name="id_usuario", Primary Key)
	private String idUsuario;
//	Column(name="data_cadastro")
	private Timestamp dataCadastro;
//	Column(name="nome")
	private String nome;
//	Column(name="data_ultimo_login")	
	private Timestamp dataUltimoLogin;
//	Column(name="data_ultimo_login")	
	private Timestamp dataUltimaAtualizacaoRuns;
//	Column(name="data_ultimo_login")	
	private Timestamp dataUltimaAtualizacaoWalks;
//	Column(name="data_ultimo_login")	
	private Timestamp dataUltimaAtualizacaoBikes;
//	Column(name="genero")
	private String genero;
//	Column(name="data_nascimento")
	private Timestamp dataNascimento;
//	Column(name="url_foto")
	private String urlFoto;
//	Column(name="url_perfil")
	private String urlPerfil;
	
	private Configuracao configuracaoFavorita;

	private List<Pessoa> amigos;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setData_cadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Timestamp getDataUltimoLogin() {
		return dataUltimoLogin;
	}

	public void setData_ultimo_login(Timestamp dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}

	public Timestamp getDataUltimaAtualizacaoRuns() {
		return dataUltimaAtualizacaoRuns;
	}

	public void setDataUltimaAtualizacaoRuns(Timestamp dataUltimaAtualizacaoRuns) {
		this.dataUltimaAtualizacaoRuns = dataUltimaAtualizacaoRuns;
	}

	public Timestamp getDataUltimaAtualizacaoWalks() {
		return dataUltimaAtualizacaoWalks;
	}

	public void setDataUltimaAtualizacaoWalks(Timestamp dataUltimaAtualizacaoWalks) {
		this.dataUltimaAtualizacaoWalks = dataUltimaAtualizacaoWalks;
	}

	public Timestamp getDataUltimaAtualizacaoBikes() {
		return dataUltimaAtualizacaoBikes;
	}

	public void setDataUltimaAtualizacaoBikes(Timestamp dataUltimaAtualizacaoBikes) {
		this.dataUltimaAtualizacaoBikes = dataUltimaAtualizacaoBikes;
	}

	public Configuracao getConfiguracaoFavorita() {
		return configuracaoFavorita;
	}

	public void setConfiguracaoFavorita(Configuracao configuracaoFavorita) {
		this.configuracaoFavorita = configuracaoFavorita;
	}

	public List<Pessoa> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Pessoa> amigos) {
		this.amigos = amigos;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Timestamp getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Timestamp dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlPerfil() {
		return urlPerfil;
	}

	public void setUrlPerfil(String urlPerfil) {
		this.urlPerfil = urlPerfil;
	}
	
}