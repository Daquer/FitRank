package br.com.fitrank.modelo;

/** Entity(name="aplicativo")
 *
 */
public class Aplicativo {
//	Column(name="id_aplicativo", Primary Key)
	private Integer idAplicativo;
//	Column(name="nome")
	private String nome;
	
	public Integer getIdAplicativo() {
		return idAplicativo;
	}
	public void setIdAplicativo(Integer idAplicativo) {
		this.idAplicativo = idAplicativo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
