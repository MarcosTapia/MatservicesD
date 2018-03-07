package ComponenteDatos;

import beans.ProductoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public abstract class BDProducto {

    public static ProductoBean insertarProducto(ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into productos (codigo,descripcion,cantidad,"
                + "nCatCodigo,formula,ubicacion,"
                + "observaciones,factura,minimo,nProvCodigo,precioCosto,precioPublico) values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getCodigo());
        ps.setString(2, p.getDescripcion());
        ps.setInt(3, p.getCantidad());
        ps.setInt(4, p.getCategoria().getnCatCodigo());
        ps.setString(5, p.getFormula());
        ps.setString(6, p.getUbicacion());
        ps.setString(7, p.getObservaciones());
        ps.setString(8, p.getFactura());
        ps.setInt(9, p.getMinimo());
        ps.setInt(10, p.getCodProveedor());
        ps.setDouble(11, p.getPrecioCosto());
        ps.setDouble(12, p.getPrecioPublico());
        ps.executeUpdate();
        ps.close();
        cnn.close();
        return p;
    }

    public static ProductoBean buscarProductoCodigoVentas(String codigo) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ProductoBean producto = null;
        ps = cnn.prepareStatement("select * from productos where codigo=?");
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            producto = new ProductoBean();
            producto.setCodigo(rs.getString("codigo"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setCantidad(rs.getInt("cantidad"));
            producto.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt("nCatCodigo")));
            producto.setFormula(rs.getString("formula")); 
            producto.setUbicacion(rs.getString("ubicacion"));
            producto.setObservaciones(rs.getString("observaciones"));
            producto.setFactura(rs.getString("factura"));
            producto.setMinimo(rs.getInt("minimo"));
        }
        ps.close();
        cnn.close();
        return producto;
    }

    public static ProductoBean buscarProducto(String codigo) throws SQLException {
        return buscarProducto(codigo, null);
    }

    public static ProductoBean buscarProducto(String codigo, ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        //ps = cnn.prepareStatement("select cProNombre,nProvCodigo,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cProEstado from producto where nProCodigo=?");
        ps = cnn.prepareStatement("select * from productos where codigo=?");
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (p == null) {
                p = new ProductoBean() {
                };
            }
            p.setCodigo(rs.getString("codigo"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt("nCatCodigo")));
            p.setFormula(rs.getString("formula")); 
            p.setUbicacion(rs.getString("ubicacion"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setMinimo(rs.getInt("minimo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioPublico(rs.getDouble("precioPublico"));
            p.setCodProveedor(rs.getInt("nProvCodigo"));
        }
        ps.close();
        cnn.close();
        return p;
    }

    public static ProductoBean buscarProductoNombre(String nom) throws SQLException {
        return buscarProductoNombre(nom, null);
    }

    public static ProductoBean buscarProductoNombre(String nom, ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        //ps = cnn.prepareStatement("select nProCodigo,nProvCodigo,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cProEstado from producto where cProNombre=?");
        ps = cnn.prepareStatement("select nProCodigo,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre=?");
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (p == null) {
                p = new ProductoBean() {
                };
            }
        }
        ps.close();
        cnn.close();
        return p;
    }

    
    public static ProductoBean buscarProductoProvNombre(String propv,String nombre) throws SQLException {
        return buscarProductoProvNombre(propv,nombre, null);
    }

    public static ProductoBean buscarProductoProvNombre(String propv,String nombre, ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre='"+nombre+"' and cCodProProv='"+propv+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            p = p == null ? new ProductoBean() {
            } : p;
            p.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt("nCatCodigo")));
        } else {
            p = null;
        }
        ps.close();
        cnn.close();
        return p;
    }
    
    public static ProductoBean buscarProductoCateg(int CatCodigo) throws SQLException {
        return buscarProductoCateg(CatCodigo, null);
    }

    public static ProductoBean buscarProductoCateg(int CatCodigo, ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        //ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProvCodigo,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cProEstado from producto where nCatCodigo=?");
        ps = cnn.prepareStatement("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where nCatCodigo=?");
        ps.setInt(1, CatCodigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            p = p == null ? new ProductoBean() {
            } : p;
            p.setCategoria(BDCategoria.buscarCategoriaCodigo(rs.getInt(CatCodigo)));
        } else {
            p = null;
        }
        ps.close();
        cnn.close();
        return p;
    }

    public static boolean actualizarProducto(ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("update productos set "
                + "codigo=?,"
                + "descripcion=?,"
                + "cantidad=?,"
                + "nCatCodigo=?,"
                + "formula=?,"
                + "ubicacion=?,"
                + "observaciones=?,"
                + "factura=?,"
                + "minimo=?,nProvCodigo=?,precioCosto=?,precioPublico=? where codigo=" + p.getCodigo());
        ps.setString(1, p.getCodigo());
        ps.setString(2, p.getDescripcion());
        ps.setInt(3, p.getCantidad());
        ps.setInt(4, p.getCategoria().getnCatCodigo());
        ps.setString(5, p.getFormula());
        ps.setString(6, p.getUbicacion());
        ps.setString(7, p.getObservaciones());
        ps.setString(8, p.getFactura());
        ps.setInt(9, p.getMinimo());
        ps.setInt(10, p.getCodProveedor());
        ps.setDouble(11, p.getPrecioCosto());
        ps.setDouble(12, p.getPrecioPublico());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ProductoBean> mostrarProducto() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductoBean> lista = new ArrayList<ProductoBean>();

        ps = cnn.prepareStatement("select * from productos");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductoBean p = new ProductoBean() {
            };
            p.setCodigo(rs.getString("codigo"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setnCatCodigo(rs.getInt("nCatCodigo"));
            p.setFormula(rs.getString("formula"));
            p.setUbicacion(rs.getString("ubicacion"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setMinimo(rs.getInt("minimo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setPrecioPublico(rs.getDouble("precioPublico"));
            p.setCodProveedor(rs.getInt("nProvCodigo"));
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<ProductoBean> listarProductoPorDescripcion(String descripcion) {
        return listar("descripcion", descripcion + "%", "like");
    }
    
    public static ArrayList<ProductoBean> listarProductoPorNombreEstado(String codigo) {
        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cProNombre like '" + codigo + "%' and cProEstado='Activo'");
    }
    
    public static ArrayList<ProductoBean> listarProductoPorCodProveedor(String nombre) {
        return listar("cCodProProv", nombre + "%", "like");
    }
    
    public static ArrayList<ProductoBean> listarProductoPorCodProveedorEstado(String codigo) {
        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where cCodProProv like '" + codigo + "%' and cProEstado='Activo'");
    }

    public static ArrayList<ProductoBean> listarProductoPorCodigo(String codigo) {
        return listar("codigo", codigo + "%", "like");
    }
    
    public static ArrayList<ProductoBean> listarProductoPorCodigoEstado(String codigo) {
        return consultarSQL("select nProCodigo,cProNombre,nProCantidad,nProPrecioCompra,nProPrecioVenta,nProUtilidad,cProDescripcion,nCatCodigo,cProMarca,cCodProProv,cProEstado from producto where nProCodigo like '" + codigo + "%' and cProEstado='Activo'");
    }

    private static ArrayList<ProductoBean> listar(String atributo, String parametro, String comparador) {
        return consultarSQL("select * from productos where " + atributo + " " + comparador + " '" + parametro + "'");
    }

    private static ArrayList<ProductoBean> consultarSQL(String sql) {
        ArrayList<ProductoBean> list = new ArrayList<ProductoBean>();
        Connection cn = BD.getConnection();
        try {
            ProductoBean p;
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                p = new ProductoBean();
                p.setCodigo(rs.getString("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setnCatCodigo(rs.getInt("nCatCodigo"));
                p.setFormula(rs.getString("formula"));
                p.setUbicacion(rs.getString("ubicacion"));
                p.setObservaciones(rs.getString("observaciones"));
                p.setFactura(rs.getString("factura"));
                p.setMinimo(rs.getInt("minimo"));
                p.setPrecioCosto(rs.getDouble("precioCosto"));
                p.setPrecioPublico(rs.getDouble("precioPublico"));
                p.setCodProveedor(rs.getInt("nProvCodigo"));
                list.add(p);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
        return list;
    }
    
    public static boolean eliminarProducto(ProductoBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("delete from productos WHERE codigo=?");
        ps.setString(1, p.getCodigo());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
 
    public static ArrayList<ProductoBean> mostrarProductoPorSurtir() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductoBean> lista = new ArrayList<ProductoBean>();

        ps = cnn.prepareStatement("select * from productos where cantidad <= minimo");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductoBean p = new ProductoBean() {
            };
            p.setCodigo(rs.getString("codigo"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setnCatCodigo(rs.getInt("nCatCodigo"));
            p.setFormula(rs.getString("formula"));
            p.setUbicacion(rs.getString("ubicacion"));
            p.setObservaciones(rs.getString("observaciones"));
            p.setFactura(rs.getString("factura"));
            p.setMinimo(rs.getInt("minimo"));
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
}