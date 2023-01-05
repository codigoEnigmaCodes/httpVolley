package com.codigoEnigma.consumohttpvolley;

public class ModeloAuto {
    String matricula, marca, modelo, color, anio, combustible, renta;

    public ModeloAuto(String matricula, String marca, String modelo, String color, String anio, String combustible, String renta) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.anio = anio;
        this.combustible = combustible;
        this.renta = renta;
    }

    public ModeloAuto() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String getRenta() {
        return renta;
    }

    public void setRenta(String renta) {
        this.renta = renta;
    }
}
