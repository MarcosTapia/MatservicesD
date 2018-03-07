package ComponenteDatos;

import beans.ComprasBean;
import beans.ProductoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public abstract class BDCompras {
    /* noCompra codigo cantidad observaciones factura precioVenta nProvCodigo fechaCompra
    edoVenta idUsuario */

    public static ArrayList<ComprasBean> mostrarComprasConsultaGral() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select * from compras");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ComprasBean p = new ComprasBean() {
            };
    /* noCompra codigo cantidad observaciones factura precioVenta nProvCodigo fechaCompra edoVenta idUsuario  */
            p.setNoCompra(rs.getInt("noCompra"));
            p.setCodigo(rs.getString("codigo"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setFechaCompra(rs.getString("fechaCompra"));
            p.setEdoVenta(rs.getBoolean("edoVenta"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //NUEVOS
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
    public static ArrayList<ComprasBean> mostrarCompras() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select DISTINCT(`noCompra`),`factura` from compras");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ComprasBean p = new ComprasBean() {
            };
    /* noCompra codigo cantidad observaciones factura precioVenta nProvCodigo fechaCompra edoVenta idUsuario  */
            p.setNoCompra(rs.getInt("noCompra"));
//            p.setCodigo(rs.getString("codigo"));
//            p.setCantidad(rs.getInt("cantidad"));
//            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
//            p.setPrecioVenta(rs.getDouble("precioVenta"));
//            p.setnProvCodigo(rs.getInt("nProvCodigo"));
//            p.setFechaCompra(rs.getTimestamp("fechaCompra"));
//            p.setEdoVenta(rs.getBoolean("edoVenta"));
//            p.setIdUsuario(rs.getInt("idUsuario"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
    public static int obtenerUltimoId() {
        int noCompra = 0;
        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(noCompra) FROM compras");
            while (rs.next()) {
                noCompra = rs.getInt(1); 
                noCompra++;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            return noCompra;
        }
    }
    
    public static ArrayList<ComprasBean> mostrarCompra(int noCompra) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select * from compras where noCompra = '" + noCompra + "'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ComprasBean p = new ComprasBean() {
            };
    /* noCompra codigo cantidad observaciones factura precioVenta nProvCodigo fechaCompra edoVenta idUsuario  */
            p.setNoCompra(rs.getInt("noCompra"));
            p.setCodigo(rs.getString("codigo"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setFechaCompra(rs.getString("fechaCompra"));
            p.setEdoVenta(rs.getBoolean("edoVenta"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //NUEVOS
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static boolean insertarCompra(ArrayList<ComprasBean> compra) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        int rowsUpdated = 0;
        for (ComprasBean p : compra) {
            ps = cnn.prepareStatement("insert into compras (noCompra,codigo,cantidad,observaciones,"
                    + "factura,precioVenta,nProvCodigo,fechaCompra,edoVenta,idUsuario,"
                    + "categoria,precioCosto,precioMaxPublico,precioSinIva,porcentajeDescuento) "
                    + "values(?,?,?,?,?,?,?,?,?,?"
                    + ",?,?,?,?,?)");
            ps.setInt(1, p.getNoCompra());
            ps.setString(2, p.getCodigo());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getObservaciones());
            ps.setString(5, p.getFactura());
            ps.setDouble(6, p.getPrecioVenta());
            ps.setInt(7, p.getnProvCodigo());
            ps.setString(8, p.getFechaCompra());            
            ps.setBoolean(9, p.isEdoVenta());
            ps.setInt(10, p.getIdUsuario());
            
            //nuevos
            ps.setString(11, p.getCategoria());
            ps.setDouble(12, p.getPrecioCosto());
            ps.setDouble(13, p.getPrecioMaxPublico());
            ps.setDouble(14, p.getPrecioSinIva());
            ps.setDouble(15, p.getPorcentajeDescuento());
            
            rowsUpdated = ps.executeUpdate();            
        } 
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ComprasBean> buscarCompraPorCodigo(int noCompra) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select * from compras where noCompra like '" + noCompra + "%'");
        //ps.setInt(1, noCompra);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ComprasBean p = new ComprasBean();
            p.setNoCompra(rs.getInt("noCompra"));
            p.setCodigo(rs.getString("codigo"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setFechaCompra(rs.getString("fechaCompra"));
            p.setEdoVenta(rs.getBoolean("edoVenta"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //NUEVOS
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<ComprasBean> buscarCompraPorFactura(String factura) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select * from compras where factura like '" + factura + "%'");
        //ps.setString(1, factura);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ComprasBean p = new ComprasBean();
            p.setNoCompra(rs.getInt("noCompra"));
            p.setCodigo(rs.getString("codigo"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setFechaCompra(rs.getString("fechaCompra"));
            p.setEdoVenta(rs.getBoolean("edoVenta"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //NUEVOS
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<ComprasBean> buscarCompraPorCodigoGeneral(int noCompra) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ComprasBean> lista = new ArrayList<ComprasBean>();
        ps = cnn.prepareStatement("select * from compras where noCompra = '" + noCompra + "'");
        //ps.setInt(1, noCompra);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ComprasBean p = new ComprasBean();
            p.setNoCompra(rs.getInt("noCompra"));
            p.setCodigo(rs.getString("codigo"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setFechaCompra(rs.getString("fechaCompra"));
            p.setEdoVenta(rs.getBoolean("edoVenta"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //NUEVOS
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
//    public static ProductoBean buscarProductoNombre(String nom) throws SQLException {
//        return buscarProductoNombre(nom, null);
//    }
//
//    public static ProductoBean buscarProductoNombre(String nom, ProductoBean p) throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        //ps = cnn.prepareStatement("select nProCodigo,nProvCodigo,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cProEstado from producto where cProNombre=?");
//        ps = cnn.prepareStatement("select nProCodigo,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre=?");
//        ps.setString(1, nom);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            if (p == null) {
//                p = new ProductoBean() {
//                };
//            }
//        }
//        cnn.close();
//        ps.close();
//        return p;
//    }
//
//    
//    public static ProductoBean buscarProductoProvNombre(String propv,String nombre) throws SQLException {
//        return buscarProductoProvNombre(propv,nombre, null);
//    }
//
//    public static ProductoBean buscarProductoProvNombre(String propv,String nombre, ProductoBean p) throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre='"+nombre+"' and cCodProProv='"+propv+"'");
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            p = p == null ? new ProductoBean() {
//            } : p;
//            p.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt("nCatCodigo")));
//        } else {
//            p = null;
//        }
//        cnn.close();
//        ps.close();
//        return p;
//    }
//    
//    public static ProductoBean buscarProductoCateg(int CatCodigo) throws SQLException {
//        return buscarProductoCateg(CatCodigo, null);
//    }
//
//    public static ProductoBean buscarProductoCateg(int CatCodigo, ProductoBean p) throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        //ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProvCodigo,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cProEstado from producto where nCatCodigo=?");
//        ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where nCatCodigo=?");
//        ps.setInt(1, CatCodigo);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            p = p == null ? new ProductoBean() {
//            } : p;
//            p.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt(CatCodigo)));
//        } else {
//            p = null;
//        }
//        cnn.close();
//        ps.close();
//        return p;
//    }
//
//    public static boolean actualizarProducto(ProductoBean p) throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        ps = cnn.prepareStatement("update productos set "
//                + "codigo=?,"
//                + "descripcion=?,"
//                + "cantidad=?,"
//                + "nCatCodigo=?,"
//                + "formula=?,"
//                + "ubicacion=?,"
//                + "observaciones=?,"
//                + "factura=?,"
//                + "minimo=? where codigo=" + p.getCodigo());
//        ps.setString(1, p.getCodigo());
//        ps.setString(2, p.getDescripcion());
//        ps.setInt(3, p.getCantidad());
//        ps.setInt(4, p.getCategoria().getnCatCodigo());
//        ps.setString(5, p.getFormula());
//        ps.setString(6, p.getUbicacion());
//        ps.setString(7, p.getObservaciones());
//        ps.setString(8, p.getFactura());
//        ps.setInt(9, p.getMinimo());
//        int rowsUpdated = ps.executeUpdate();
//        cnn.close();
//        ps.close();
//        if (rowsUpdated > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public static ArrayList<ProductoBean> mostrarProducto() throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        ArrayList<ProductoBean> lista = new ArrayList<ProductoBean>();
//
//        ps = cnn.prepareStatement("select * from productos");
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            ProductoBean p = new ProductoBean() {
//            };
//            p.setCodigo(rs.getString("codigo"));
//            p.setDescripcion(rs.getString("descripcion"));
//            p.setCantidad(rs.getInt("cantidad"));
//            p.setnCatCodigo(rs.getInt("nCatCodigo"));
//            p.setFormula(rs.getString("formula"));
//            p.setUbicacion(rs.getString("ubicacion"));
//            p.setObservaciones(rs.getString("observaciones"));
//            p.setFactura(rs.getString("factura"));
//            p.setMinimo(rs.getInt("minimo"));
//            lista.add(p);
//        }
//        cnn.close();
//        ps.close();
//        return lista;
//    }
//
//    public static ArrayList<ProductoBean> listarProductoPorDescripcion(String descripcion) {
//        return listar("descripcion", descripcion + "%", "like");
//    }
//    
//    public static ArrayList<ProductoBean> listarProductoPorNombreEstado(String codigo) {
//        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre like '" + codigo + "%' and cProEstado='Activo'");
//    }
//    
//    public static ArrayList<ProductoBean> listarProductoPorCodProveedor(String nombre) {
//        return listar("cCodProProv", nombre + "%", "like");
//    }
//    
//    public static ArrayList<ProductoBean> listarProductoPorCodProveedorEstado(String codigo) {
//        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cCodProProv like '" + codigo + "%' and cProEstado='Activo'");
//    }
//
//    public static ArrayList<ProductoBean> listarProductoPorCodigo(String codigo) {
//        return listar("codigo", codigo + "%", "like");
//    }
//    
//    public static ArrayList<ProductoBean> listarProductoPorCodigoEstado(String codigo) {
//        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where nProCodigo like '" + codigo + "%' and cProEstado='Activo'");
//    }
//
//    private static ArrayList<ProductoBean> listar(String atributo, String parametro, String comparador) {
//        return consultarSQL("select * from productos where " + atributo + " " + comparador + " '" + parametro + "'");
//    }
//
//    private static ArrayList<ProductoBean> consultarSQL(String sql) {
//        ArrayList<ProductoBean> list = new ArrayList<ProductoBean>();
//        Connection cn = BD.getConnection();
//        try {
//            ProductoBean p;
//            Statement stmt = cn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                p = new ProductoBean();
//                p.setCodigo(rs.getString("codigo"));
//                p.setDescripcion(rs.getString("descripcion"));
//                p.setCantidad(rs.getInt("cantidad"));
//                p.setnCatCodigo(rs.getInt("nCatCodigo"));
//                p.setFormula(rs.getString("formula"));
//                p.setUbicacion(rs.getString("ubicacion"));
//                p.setObservaciones(rs.getString("observaciones"));
//                p.setFactura(rs.getString("factura"));
//                p.setMinimo(rs.getInt("minimo"));
//                list.add(p);
//            }
//            cn.close();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            return null;
//        }
//        return list;
//    }
//    
//    public static boolean eliminarProducto(ProductoBean p) throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        ps = cnn.prepareStatement("delete from productos WHERE codigo=?");
//        ps.setString(1, p.getCodigo());
//        int rowsUpdated = ps.executeUpdate();
//        cnn.close();
//        ps.close();
//        if (rowsUpdated > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
// 
//    public static ArrayList<ProductoBean> mostrarProductoPorSurtir() throws SQLException {
//        Connection cnn = BD.getConnection();
//        PreparedStatement ps = null;
//        ArrayList<ProductoBean> lista = new ArrayList<ProductoBean>();
//
//        ps = cnn.prepareStatement("select * from productos where cantidad <= minimo");
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            ProductoBean p = new ProductoBean() {
//            };
//            p.setCodigo(rs.getString("codigo"));
//            p.setDescripcion(rs.getString("descripcion"));
//            p.setCantidad(rs.getInt("cantidad"));
//            p.setnCatCodigo(rs.getInt("nCatCodigo"));
//            p.setFormula(rs.getString("formula"));
//            p.setUbicacion(rs.getString("ubicacion"));
//            p.setObservaciones(rs.getString("observaciones"));
//            p.setFactura(rs.getString("factura"));
//            p.setMinimo(rs.getInt("minimo"));
//            lista.add(p);
//        }
//        cnn.close();
//        ps.close();
//        return lista;
//    }
    
}