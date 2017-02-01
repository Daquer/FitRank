package br.com.fitrank.modelo;

import java.sql.Timestamp;
import java.util.List;

/** Entity(name="pessoa")
 *
 */
public class Pessoa {
//	Column(name="id_usuario", Primary Key)
	private String id_usuario;
//	Column(name="data_cadastro")
	private Timestamp data_cadastro;
//	Column(name="nome")
	private String nome;
//	Column(name="data_ultimo_login")	
	private Timestamp data_ultimo_login;
//	Column(name="data_ultimo_login")	
	private Timestamp data_ultima_atualizacao_runs;
//	Column(name="data_ultimo_login")	
	private Timestamp data_ultima_atualizacao_walks;
//	Column(name="data_ultimo_login")	
	private Timestamp data_ultima_atualizacao_bikes;
//	Column(name="rank_anual")	
	private String rank_anual;
//	Column(name="genero")
	private String genero;
//	Column(name="data_nascimento")
	private Timestamp data_nascimento;
//	Column(name="url_foto")
	private String url_foto;
	
	private Configuracao configuracaoFavorita;

	private List<Pessoa> amigos;

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Timestamp getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Timestamp data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Timestamp getData_ultimo_login() {
		return data_ultimo_login;
	}

	public void setData_ultimo_login(Timestamp data_ultimo_login) {
		this.data_ultimo_login = data_ultimo_login;
	}

	public Timestamp getData_ultima_atualizacao_runs() {
		return data_ultima_atualizacao_runs;
	}

	public void setData_ultima_atualizacao_runs(Timestamp data_ultima_atualizacao_runs) {
		this.data_ultima_atualizacao_runs = data_ultima_atualizacao_runs;
	}

	public Timestamp getData_ultima_atualizacao_walks() {
		return data_ultima_atualizacao_walks;
	}

	public void setData_ultima_atualizacao_walks(Timestamp data_ultima_atualizacao_walks) {
		this.data_ultima_atualizacao_walks = data_ultima_atualizacao_walks;
	}

	public Timestamp getData_ultima_atualizacao_bikes() {
		return data_ultima_atualizacao_bikes;
	}

	public void setData_ultima_atualizacao_bikes(Timestamp data_ultima_atualizacao_bikes) {
		this.data_ultima_atualizacao_bikes = data_ultima_atualizacao_bikes;
	}

	public String getRank_anual() {
		return rank_anual;
	}

	public void setRank_anual(String rank_anual) {
		this.rank_anual = rank_anual;
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

	public Timestamp getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Timestamp data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getUrl_foto() {
		return url_foto;
	}

	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}
	
}