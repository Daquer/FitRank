package br.com.fitrank.modelo;
//TODO Verificar classe no banco, classe de modelo e UML
public class Localizacao implements Comparable<Object> {

	private double  latitude;
	private double 	longitude;
	private double  altitude;
	private double  ritmo;
	private String  id_course;
	private int 	id_localizacao;
	

	public String getId_course() {
		return id_course;
	}
	public void setId_course(String id_course) {
		this.id_course = id_course;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public double getRitmo() {
		return ritmo;
	}
	public void setRitmo(double ritmo) {
		this.ritmo = ritmo;
	}
	public int getId_localizacao() {
		return id_localizacao;
	}
	public void setId_localizacao(int id_localizacao) {
		this.id_localizacao = id_localizacao;
	}	
	
	@Override
	public int compareTo(Object o) {
		if(o != null && o instanceof Localizacao){
			Localizacao localizacao = (Localizacao) o;
			
			if(localizacao.id_course.equals(this.id_course) && localizacao.latitude == this.latitude && localizacao.longitude == this.longitude && localizacao.altitude == this.altitude && localizacao.ritmo == this.ritmo){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Localizacao){
			Localizacao localizacao = (Localizacao) o;
			
			if(localizacao.id_course.equals(this.id_course) && localizacao.latitude == this.latitude && localizacao.longitude == this.longitude && localizacao.altitude == this.altitude && localizacao.ritmo == this.ritmo){
				return true;
			}
		}
		
		return false;
	}
}

