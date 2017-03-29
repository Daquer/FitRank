package br.com.fitrank.modelo;

import java.sql.Timestamp;

/** Entity(name="post_fitness")
 *
 */
public class PostFitness implements Comparable<Object> {
	
	private String	id_publicacao;
	
	private String  id_pessoa;
	
	private String 	id_app;
	
	private double	distancia_percorrida;
	
	private double 	duracao;
	
	private Timestamp data_publicacao;
	
	private String  url;
	
	private String 	modalidade;
	
	private Course	course;

	public String getId_publicacao() {
		return id_publicacao;
	}

	public void setId_publicacao(String id_publicacao) {
		this.id_publicacao = id_publicacao;
	}

	public String getId_pessoa() {
		return id_pessoa;
	}

	public void setId_pessoa(String id_pessoa) {
		this.id_pessoa = id_pessoa;
	}

	public String getId_app() {
		return id_app;
	}

	public void setId_app(String id_app) {
		this.id_app = id_app;
	}

	public double getDistancia_percorrida() {
		return distancia_percorrida;
	}

	public void setDistancia_percorrida(double distancia_percorrida) {
		this.distancia_percorrida = distancia_percorrida;
	}

	public double getDuracao() {
		return duracao;
	}

	public void setDuracao(double duracao) {
		this.duracao = duracao;
	}

	public Timestamp getData_publicacao() {
		return data_publicacao;
	}

	public void setData_publicacao(Timestamp data_publicacao) {
		this.data_publicacao = data_publicacao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public int compareTo(Object o) {
		if(o != null && o instanceof PostFitness){
			PostFitness postFitness = (PostFitness) o;
			
			if(postFitness.id_publicacao.equals(this.id_publicacao) && postFitness.id_pessoa.equals(this.id_pessoa)){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof PostFitness){
			PostFitness postFitness = (PostFitness) o;
			
			if(postFitness.id_publicacao.equals(this.id_publicacao) && postFitness.id_pessoa.equals(this.id_pessoa)){
				return true;
			}
		}
		
		return false;
	}
}
