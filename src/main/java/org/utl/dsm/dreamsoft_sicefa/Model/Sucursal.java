package org.utl.dsm.dreamsoft_sicefa.Model;

public class Sucursal {

    private int idSucursal;
    private String nombreSucursal;
    private String titular;
    private String rfc;
    private String domicilio;
    private String colonia;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private String telefono;
    private String latitud;
    private String longitud;
    private int estatus;
    private Empleado empleado;
    private Usuario usuario;
    private Persona persona;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Sucursal() {
    }

    public Sucursal(Empleado empleado, Usuario usuario, Persona persona, int idSucursal, String nombreSucursal, String titular, String rfc, String domicilio, String colonia, String codigoPostal, String ciudad, String estado, String telefono, String latitud, String longitud, int estatus) {
        this.empleado = empleado;
        this.usuario = usuario;
        this.persona = persona;
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.titular = titular;
        this.rfc = rfc;
        this.domicilio = domicilio;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estatus = estatus;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return this.nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    public Sucursal getSucursal() {
        return this;
    }

}