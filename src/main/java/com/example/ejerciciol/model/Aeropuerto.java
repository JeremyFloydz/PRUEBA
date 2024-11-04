package com.example.ejerciciol.model;

public class Aeropuerto {
    private int id;
    private String nombre;
    private int anioInauguracion;
    private int capacidad;
    private int idDireccion;
    private byte[] imagen;



    public Aeropuerto(int id, String nombre, int anioInauguracion, int capacidad, int idDireccion, byte[] imagen) {
		this.id = id;
		this.nombre = nombre;
		this.anioInauguracion = anioInauguracion;
		this.capacidad = capacidad;
		this.idDireccion = idDireccion;
		this.imagen = imagen;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getAnioInauguracion() {
		return anioInauguracion;
	}



	public void setAnioInauguracion(int anioInauguracion) {
		this.anioInauguracion = anioInauguracion;
	}



	public int getCapacidad() {
		return capacidad;
	}



	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}



	public int getIdDireccion() {
		return idDireccion;
	}



	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}



	public byte[] getImagen() {
		return imagen;
	}



	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	// Getters y setters para cada atributo

  
}
