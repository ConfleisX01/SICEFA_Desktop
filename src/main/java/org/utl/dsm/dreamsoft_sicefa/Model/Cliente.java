package org.utl.dsm.dreamsoft_sicefa.Model;

public class Cliente {
    private Persona persona;
    private int idCliente;
    private String email;
    private String fechaRegistro;
    private int estatus;
    public Cliente(){

    }
    public Cliente(Persona persona, int idCliente, String email, String fechaRegistro, int estatus) {
        this.persona = persona;
        this.idCliente = idCliente;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
