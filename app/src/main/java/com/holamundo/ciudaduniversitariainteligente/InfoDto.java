package com.holamundo.ciudaduniversitariainteligente;

/**
 * Created by usuario on 12/2/2018.
 */

public class InfoDto {

    private int id;

    public InfoDto(int id, String nombre, String localidad, String direccion, String telefono, String fax, String pagina) {
        this.id = id;
        this.nombre = nombre;
        this.localidad = localidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.pagina = pagina;
    }

    private String nombre;
    private String localidad;
    private String direccion;
    private String telefono;

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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    private String fax;
    private String pagina;

}
