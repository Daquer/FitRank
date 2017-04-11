package br.com.fitrank.modelo;

public class Course implements Comparable<Object> {
	
	private String idCourse;
	private float  distancia;
	private String idPessoa;
	private float  calorias;
	private String idPost;
	private String 	json;
	
	public String getIdCourse() {
		return idCourse;
	}
	public void setIdCourse(String idCourse) {
		this.idCourse = idCourse;
	}
	public float getDistancia() {
		return distancia;
	}
	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	public String getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}
	public float getCalorias() {
		return calorias;
	}
	public void setCalorias(float calorias) {
		this.calorias = calorias;
	}
	public String getIdPost() {
		return idPost;
	}
	public void setIdPost(String idPost) {
		this.idPost = idPost;
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
			
			if(course.idCourse.equals(this.idCourse)){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Course){
			Course course = (Course) o;
			
			if(course.idCourse.equals(this.idCourse)){
				return true;
			}
		}
		
		return false;
	}
	
}
