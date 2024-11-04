package com.example.ejerciciol.model;

import java.util.Objects;

public class Direccion {
	
	private int id;
	private String pais;
	private String ciudad;
	private String calle;
	private int numero;
	
	public Direccion(int id, String pais, String ciudad, String calle, int numero) {
		this.id = id;
		this.pais = pais;
		this.ciudad = ciudad;
		this.calle = calle;
		this.numero = numero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Direccion [id=" + id + ", pais=" + pais + ", ciudad=" + ciudad + ", calle=" + calle + ", numero="
				+ numero + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(calle, ciudad, id, numero, pais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(calle, other.calle) && Objects.equals(ciudad, other.ciudad) && id == other.id
				&& numero == other.numero && Objects.equals(pais, other.pais);
	}
	


}
