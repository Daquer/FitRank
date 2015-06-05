package br.com.fitrank.modelo;

/** Entity(name="pessoa")
 *
 */
public class Pessoa {
//	Column(name="id_usuario", Primary Key)
	private Integer idUsuario;
//	Column(name="nome")
	private String nome;
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdAplicativo(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
