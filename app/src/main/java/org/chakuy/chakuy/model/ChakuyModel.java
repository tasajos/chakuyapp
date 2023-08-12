package org.chakuy.chakuy.model;

public class ChakuyModel {



    String area,descripcion,ubicacion,fecha,nombre,tipo;

    public ChakuyModel (){ }


    public ChakuyModel(String area, String descripcion, String ubicacion, String fecha, String nombre,String tipo) {
        this.area = ubicacion;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.nombre = nombre;

    }


    public String getubicacion() {
        return ubicacion;
    }

    public void setubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String gettipo() {
        return tipo;
    }

    public void settipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }


}
