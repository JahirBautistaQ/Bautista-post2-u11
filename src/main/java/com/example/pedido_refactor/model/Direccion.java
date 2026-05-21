package com.example.pedido_refactor.model;

public class Direccion {

    private final String calle;
    private final String ciudad;
    private final String codigoPostal;

    public Direccion(String calle,
                     String ciudad,
                     String codigoPostal) {

        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }
}