package es.exitae.ejerciciofinal.beans;

import java.io.Serializable;

public class Lugar implements Serializable{
	
	// definimos los atributos del bean
	private int 		id;
	private String nombreLugar;
	private String descrLugar;
	private float   latitud;
	private float 	longitud;
	private String foto=null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreLugar() {
		return nombreLugar;
	}
	public void setNombreLugar(String nombreLugar) {
		this.nombreLugar = nombreLugar;
	}
	public String getDescrLugar() {
		return descrLugar;
	}
	public void setDescrLugar(String descrLugar) {
		this.descrLugar = descrLugar;
	}
	public float getLatitud() {
		return latitud;
	}
	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}
	public float getLongitud() {
		return longitud;
	}
	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
		
}
