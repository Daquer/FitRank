package br.com.fitrank.modelo;

/** Entity(name="post_fitness")
 *
 */
public class PostFitness {
//	Column(name="id_publicacao", Primary Key)
	private Integer idPublicacao;
//	Column(name="nome")
	private String nome;
	
	public Integer getIdPublicacao() {
		return idPublicacao;
	}
	public void setIdPublicacao(Integer idPublicacao) {
		this.idPublicacao = idPublicacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
