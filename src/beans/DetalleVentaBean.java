package beans;

public class DetalleVentaBean {
    private VentasBean ventaBean;
    private int nVenCodigo;
    private String codigo;
    private int cantidad;
    private double precioUnitario;
    private double subTotalParcial;
    private double ivaParcial;

    public double getIvaParcial() {
        return ivaParcial;
    }

    public void setIvaParcial(double ivaParcial) {
        this.ivaParcial = ivaParcial;
    }

    public VentasBean getVentaBean() {
        return ventaBean;
    }

    public void setVentaBean(VentasBean ventaBean) {
        this.ventaBean = ventaBean;
    }

    public int getnVenCodigo() {
        return nVenCodigo;
    }

    public void setnVenCodigo(int nVenCodigo) {
        this.nVenCodigo = nVenCodigo;
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

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubTotalParcial() {
        return subTotalParcial;
    }

    public void setSubTotalParcial(double subTotalParcial) {
        this.subTotalParcial = subTotalParcial;
    }
    
    
}
