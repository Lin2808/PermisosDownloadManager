package com.example.permisosdownloadmanager;

public class Fichero
{
    private String idFichero;
    private String fichero;
    private String fecha;
    private String url;

    public Fichero()
    {
    }

    public Fichero(String idFichero, String fichero, String fecha, String url) {
        this.idFichero = idFichero;
        this.fichero = fichero;
        this.fecha = fecha;
        this.url = url;
    }

    public String getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(String idFichero) {
        this.idFichero = idFichero;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
