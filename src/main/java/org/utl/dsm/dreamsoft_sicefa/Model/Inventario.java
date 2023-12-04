package org.utl.dsm.dreamsoft_sicefa.Model;

public class Inventario {
    private int idInventario;
    private int idProducto;
    private int idSucursal;
    private int existencias;

    public Inventario(){

    }

    public Inventario(int idInventario, int idProducto, int idSucursal, int existencias) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
        this.existencias = existencias;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
}
