package beans;

import java.sql.Timestamp;

public class ComprasBean {
    private int noCompra;
    private String descripcion;
    private String codigo;
    private int cantidad;
    private String factura;
    private double precioVenta;
    private int nProvCodigo;
    private String fechaCompra;
    private boolean edoVenta;
    private int idUsuario;

   //nuevos
   private String categoria;
   private double precioCosto;
   private double precioMaxPublico;
   private double precioSinIva;
   private double porcentajeDescuento;

   //nuevos2
    private String observaciones;
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }   

    public int getNoCompra() {
        return noCompra;
    }

    public void setNoCompra(int noCompra) {
        this.noCompra = noCompra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getnProvCodigo() {
        return nProvCodigo;
    }

    public void setnProvCodigo(int nProvCodigo) {
        this.nProvCodigo = nProvCodigo;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean isEdoVenta() {
        return edoVenta;
    }

    public void setEdoVenta(boolean edoVenta) {
        this.edoVenta = edoVenta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioMaxPublico() {
        return precioMaxPublico;
    }

    public void setPrecioMaxPublico(double precioMaxPublico) {
        this.precioMaxPublico = precioMaxPublico;
    }

    public double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
