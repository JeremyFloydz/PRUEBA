package com.example.ejerciciol.model;

import java.util.Objects;

public class Aeropuerto_Publico {
    private int id;
    private double financiacion;
    private int numTrabajadores;

    public Aeropuerto_Publico(int id, double financiacion, int numTrabajadores) {
        this.id = id;
        this.financiacion = financiacion;
        this.numTrabajadores = numTrabajadores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFinanciacion() {
        return financiacion;
    }

    public void setFinanciacion(double financiacion) {
        this.financiacion = financiacion;
    }

    public int getnumTrabajadores() {
        return numTrabajadores;
    }

    public void setnumTrabajadores(int numTrabajadores) {
        this.numTrabajadores = numTrabajadores;
    }

    @Override
    public String toString() {
        return "Aeropuerto_publico [id=" + id + ", financiacion=" + financiacion + ", numTrabajadores="
                + numTrabajadores + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(financiacion, id, numTrabajadores);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aeropuerto_Publico other = (Aeropuerto_Publico) obj;
        return Double.doubleToLongBits(financiacion) == Double.doubleToLongBits(other.financiacion) && id == other.id
                && numTrabajadores == other.numTrabajadores;
    }





}