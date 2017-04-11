package br.com.fitrank.modelo;

/** Entity(name="aplicativo")
 *
 */
public class Aplicativo implements Comparable<Object> {
//	Column(name="id_aplicativo", Primary Key)
	private String idAplicativo;
//	Column(name="nome")
	private String nome;
//	Column(name="url_site")
	private String urlSite;
	
	public String getIdAplicativo() {
		return idAplicativo;
	}
	public void setIdAplicativo(String idAplicativo) {
		this.idAplicativo = idAplicativo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUrlSite() {
		return urlSite;
	}
	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}
	@Override
	public int compareTo(Object o) {
		if(o != null && o instanceof Aplicativo){
			Aplicativo app = (Aplicativo) o;
			
			if(app.idAplicativo.equals(this.idAplicativo)){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Aplicativo){
			Aplicativo app = (Aplicativo) o;
			
			if(app.idAplicativo.equals(this.idAplicativo)){
				return true;
			}
		}
		
		return false;
	}
	
}
