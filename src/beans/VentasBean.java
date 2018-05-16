package beans;

import java.sql.Timestamp;
import java.util.Date;

public class VentasBean {
    private int idVenta;
    private Date fecha;
    private int idCliente;
    private String observaciones;
    private int idUsuario;
    private int idSucursal;
    
    private double subtotal;
    private double iva;
    private double total;
    private String tipovta;
    private int cancelada;
    private int facturada;
    private int idFactura;

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipovta() {
        return tipovta;
    }

    public void setTipovta(String tipovta) {
        this.tipovta = tipovta;
    }

    public int getCancelada() {
        return cancelada;
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public int getFacturada() {
        return facturada;
    }

    public void setFacturada(int facturada) {
        this.facturada = facturada;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
}
