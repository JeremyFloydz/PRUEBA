package com.example.ejerciciol.model;

import java.math.BigDecimal;
import java.util.Objects;

public class AeropuertoInfo {
	
	  private int idAeropuerto;
	    private String nombre;
	    private String pais;
	    private String ciudad;
	    private String calle;
	    private int numero;
	    private int anio_inauguracion;
	    private int capacidad;
	    
	    private int num_socios;
	    
	    private BigDecimal financiacion;
	    private int num_trabajadores;

	    // Constructor para privados
	    public AeropuertoInfo(int idAeropuerto, String nombre, String pais, String ciudad, String calle, int numero, int anio_inauguracion, int capacidad, int num_socios) {
	        this.idAeropuerto = idAeropuerto;
	        this.nombre = nombre;
	        this.pais = pais;
	        this.ciudad = ciudad;
	        this.calle = calle;
	        this.numero = numero;
	        this.anio_inauguracion = anio_inauguracion;
	        this.capacidad = capacidad;
	        this.num_socios = num_socios;
	    }
	    
	    // Constuctor para publicos
		public AeropuertoInfo(int idAeropuerto, String nombre, String pais, String ciudad, String calle, int numero,
				int anio_inauguracion, int capacidad, BigDecimal financiacion, int num_trabajadores) {
			this.idAeropuerto = idAeropuerto;
			this.nombre = nombre;
			this.pais = pais;
			this.ciudad = ciudad;
			this.calle = calle;
			this.numero = numero;
			this.anio_inauguracion = anio_inauguracion;
			this.capacidad = capacidad;
			this.num_trabajadores = num_trabajadores;
			this.financiacion = financiacion;
		}
	    
	    
		@Override
		public String toString() {
			return "AeropuertoInfo [idAeropuerto=" + idAeropuerto + ", nombre=" + nombre + ", pais=" + pais
					+ ", ciudad=" + ciudad + ", calle=" + calle + ", numero=" + numero + ", anio_inauguracion="
					+ anio_inauguracion + ", capacidad=" + capacidad + ", num_socios=" + num_socios + ", financiacion="
					+ financiacion + ", num_trabajadores=" + num_trabajadores + "]";
		}

		public int getIdAeropuerto() {
			return idAeropuerto;
		}

		public void setIdAeropuerto(int idAeropuerto) {
			this.idAeropuerto = idAeropuerto;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
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

		public int getAnio_inauguracion() {
			return anio_inauguracion;
		}

		public void setAnio_inauguracion(int anio_inauguracion) {
			this.anio_inauguracion = anio_inauguracion;
		}

		public int getCapacidad() {
			return capacidad;
		}

		public void setCapacidad(int capacidad) {
			this.capacidad = capacidad;
		}

		public int getNum_socios() {
			return num_socios;
		}

		public void setNum_socios(int num_socios) {
			this.num_socios = num_socios;
		}

		public BigDecimal getFinanciacion() {
			return financiacion;
		}

		public void setFinanciacion(BigDecimal financiacion) {
			this.financiacion = financiacion;
		}

		public int getNum_trabajadores() {
			return num_trabajadores;
		}

		public void setNum_trabajadores(int num_trabajadores) {
			this.num_trabajadores = num_trabajadores;
		}
	    
	    /**
	     * Método para comparar este AeropuertoInfo con otro objeto para determinar la igualdad.
	     * La comparación se basa en los atributos del aeropuerto, excluyendo su ID.
	     *
	     * @param obj El objeto con el que se compara.
	     */
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;
		    AeropuertoInfo other = (AeropuertoInfo) obj;
		    return Objects.equals(nombre, other.nombre) &&
		           Objects.equals(pais, other.pais) &&
		           Objects.equals(ciudad, other.ciudad) &&
		           Objects.equals(calle, other.calle) &&
		           numero == other.numero &&
		           anio_inauguracion == other.anio_inauguracion &&
		           capacidad == other.capacidad &&
		           num_socios == other.num_socios;
		}

		@Override
		public int hashCode() {
		    return Objects.hash(nombre, pais, ciudad, calle, numero, anio_inauguracion, capacidad, num_socios);
		}

	   


}
