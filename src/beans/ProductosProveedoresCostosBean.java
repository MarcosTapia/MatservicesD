package beans;

public class ProductosProveedoresCostosBean {
   private int id;
   private ProveedorBean proveedorBean;
   private int nProvCodigo;
   private ProductoBean productoBean;
   private String codigo;
   private double precioCosto;
   private java.sql.Timestamp fecha;
   private int idUsuario;
   
   //nuevos
   private String categoria;
   private double precioVenta;
   private double precioMaxPublico;
   private double precioSinIva;
   private double porcentajeDescuento;
   
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProveedorBean getProveedorBean() {
        return proveedorBean;
    }

    public void setProveedorBean(ProveedorBean proveedorBean) {
        this.proveedorBean = proveedorBean;
    }

    public int getnProvCodigo() {
        return nProvCodigo;
    }

    public void setnProvCodigo(int nProvCodigo) {
        this.nProvCodigo = nProvCodigo;
    }

    public ProductoBean getProductoBean() {
        return productoBean;
    }

    public void setProductoBean(ProductoBean productoBean) {
        this.productoBean = productoBean;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public java.sql.Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
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
   
    
    
}
