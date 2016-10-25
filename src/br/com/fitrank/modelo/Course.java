package br.com.fitrank.modelo;

public class Course implements Comparable<Object> {
	
	private String id_course;
	private float  distancia;
	private String id_pessoa;
	private float  calorias;
	private String id_post;
	private String 	json;
	
	public String getId_course() {
		return id_course;
	}
	public void setId_course(String id_course) {
		this.id_course = id_course;
	}
	public float getDistancia() {
		return distancia;
	}
	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	public String getId_pessoa() {
		return id_pessoa;
	}
	public void setId_pessoa(String id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	public float getCalorias() {
		return calorias;
	}
	public void setCalorias(float calorias) {
		this.calorias = calorias;
	}
	public String getId_post() {
		return id_post;
	}
	public void setId_post(String id_post) {
		this.id_post = id_post;
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	@Override
	public int compareTo(Object o) {
		if(o != null && o instanceof Course){
			Course course = (Course) o;
			
			if(course.id_course.equals(this.id_course)){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Course){
			Course course = (Course) o;
			
			if(course.id_course.equals(this.id_course)){
				return true;
			}
		}
		
		return false;
	}
	
}
