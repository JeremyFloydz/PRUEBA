package com.example.ejerciciol.model;

public class Avion {
    private String id;
    private String modelo;
    private String asientos;
    private String velMax;
    private boolean activado;
    private Aeropuerto aeropuerto; // Campo para relacionar con Aeropuerto

    // Constructor
    public Avion(String id, String modelo, String asientos, String velMax, boolean activado, Aeropuerto aeropuerto) {
        this.id = id;
        this.modelo = modelo;
        this.asientos = asientos;
        this.velMax = velMax;
        this.activado = activado;
        this.aeropuerto = aeropuerto;
    }

    // Getters
    public String getId() { return id; }
    public String getModelo() { return modelo; }
    public String getAsientos() { return asientos; }
    public String getVelMax() { return velMax; }
    public boolean isActivado() { return activado; }
    public Aeropuerto getAeropuerto() { return aeropuerto; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAsientos(String asientos) { this.asientos = asientos; }
    public void setVelMax(String velMax) { this.velMax = velMax; }
    public void setActivado(boolean activado) { this.activado = activado; }
    public void setAeropuerto(Aeropuerto aeropuerto) { this.aeropuerto = aeropuerto; }
}
