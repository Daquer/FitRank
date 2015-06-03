package br.com.fitrank.modelo;

/** Entity(name="amizade")
 *
 */
public class Amizade {
//	Column(name="id_pessoa", FK="FK_ID_PESSOA_AMIZADE")
	private String idPessoa;
//	Column(name="id_amigo", FK="FK_ID_AMIGO_AMIZADE)
	private String idAmigo;
	
	public String getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getIdAmigo() {
		return idAmigo;
	}
	public void setIdAmigo(String idAmigo) {
		this.idAmigo = idAmigo;
	}

}
