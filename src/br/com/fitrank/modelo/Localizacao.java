package br.com.fitrank.modelo;

public class Localizacao implements Comparable<Object> {

	private double  latitude;
	private double 	longitude;
	private double  altitude;
	private double  ritmo;
	private String  idCourse;
	private int 	idLocalizacao;
	

	public String getIdCourse() {
		return idCourse;
	}
	public void setIdCourse(String idCourse) {
		this.idCourse = idCourse;
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
	public int getIdLocalizacao() {
		return idLocalizacao;
	}
	public void setIdLocalizacao(int idLocalizacao) {
		this.idLocalizacao = idLocalizacao;
	}	
	
	@Override
	public int compareTo(Object o) {
		if(o != null && o instanceof Localizacao){
			Localizacao localizacao = (Localizacao) o;
			
			if(localizacao.idCourse.equals(this.idCourse) && localizacao.latitude == this.latitude && localizacao.longitude == this.longitude && localizacao.altitude == this.altitude && localizacao.ritmo == this.ritmo){
				return 0;
			}
		}
		
		return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Localizacao){
			Localizacao localizacao = (Localizacao) o;
			
			if(localizacao.idCourse.equals(this.idCourse) && localizacao.latitude == this.latitude && localizacao.longitude == this.longitude && localizacao.altitude == this.altitude && localizacao.ritmo == this.ritmo){
				return true;
			}
		}
		
		return false;
	}
}

