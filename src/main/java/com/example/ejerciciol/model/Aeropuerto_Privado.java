package com.example.ejerciciol.model;

import java.util.Objects;

public class Aeropuerto_Privado {

    private int id;
    private int numSocios;


    public Aeropuerto_Privado(int id, int numSocios) {
        this.id = id;
        this.numSocios = numSocios;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getnumSocios() {
        return numSocios;
    }


    public void setnumSocios(int numSocios) {
        this.numSocios = numSocios;
    }


    @Override
    public String toString() {
        return "Aeropuerto_privado [id=" + id + ", numSocios=" + numSocios + "]";
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, numSocios);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aeropuerto_Privado other = (Aeropuerto_Privado) obj;
        return id == other.id && numSocios == other.numSocios;
    }





}
