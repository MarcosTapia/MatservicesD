package ComponenteDatos;

import beans.PrecioCompuestoBean;
import beans.ProductoBean;
import beans.ProductosProveedoresCostosBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BDProductosProveedoresCostos {
    public boolean insertarProducto(ProductosProveedoresCostosBean p) throws SQLException {
        boolean insertar;
        try (Connection cnn = BD.getConnection()) {
            PreparedStatement ps;
            ps = cnn.prepareStatement("insert into productosproveedorescostos ("
                    + "nProvCodigo,codigo,precioCosto,fecha,idUsuario,categoria,"
                    + "precioVenta,precioMaxPublico,precioSinIva,porcentajeDescuento"
                    + ") values(?,?,?,?,?,?,?,?,?,?)");
            
            ps.setInt(1, p.getnProvCodigo());
            ps.setString(2, p.getCodigo());
            ps.setDouble(3, p.getPrecioCosto());
            ps.setTimestamp(4, p.getFecha());
            ps.setInt(5, p.getIdUsuario());
            
            //nuevos
            ps.setString(6, p.getCategoria());
            ps.setDouble(7, p.getPrecioVenta());
            ps.setDouble(8, p.getPrecioMaxPublico());
            ps.setDouble(9, p.getPrecioSinIva());
            ps.setDouble(10, p.getPorcentajeDescuento());
            
            ps.executeUpdate();
            ps.close();
            cnn.close();
            insertar = true;
        } catch (Exception e) {
            insertar = false;
        }
        return insertar;
    }
    
    public static ProductosProveedoresCostosBean buscarPPC(ProductosProveedoresCostosBean ppc) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ProductosProveedoresCostosBean p = null;
        ps = cnn.prepareStatement("select * from productosproveedorescostos where nProvCodigo=? and codigo=? and id=? and fecha=?");
        ps.setInt(1, ppc.getnProvCodigo());
        ps.setString(2, ppc.getCodigo());        
        ps.setInt(3, ppc.getId());
        ps.setTimestamp(4, ppc.getFecha());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            p = new ProductosProveedoresCostosBean();
            p.setId(rs.getInt("id"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setCodigo(rs.getString("codigo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setFecha(rs.getTimestamp("fecha"));
            p.setIdUsuario(rs.getInt("idUsuario"));
        }
        ps.close();
        cnn.close();
        return p;
    }
    
    public void buscarProductoCodigoDescripcionEliminaMenor(ProductosProveedoresCostosBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement psConsultaMayor = null;
        PreparedStatement psEliminar = null;
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select count(*) as numRegistros from productosproveedorescostos where codigo=? and nProvCodigo=?");
        ps.setString(1, p.getCodigo());
        ps.setInt(2, p.getnProvCodigo());
        ResultSet rs = ps.executeQuery();
        int numRegistros = 0;
        if (rs.next()) {
            numRegistros = rs.getInt("numRegistros");
        }
        if (numRegistros > 3) {
            //Selecciona registro mas antiguo
            psConsultaMayor = cnn.prepareStatement("select * from productosproveedorescostos where fecha=(select min(fecha) from productosproveedorescostos where codigo=? and nProvCodigo=?)");
            psConsultaMayor.setString(1, p.getCodigo());
            psConsultaMayor.setInt(2, p.getnProvCodigo());
            ResultSet rsMax = psConsultaMayor.executeQuery();
            int idEliminar = 0;
            if (rsMax.next()) {
                idEliminar = rsMax.getInt("id");
            }
            //Elimina registro mas antiguo
            psEliminar = cnn.prepareStatement("delete from productosproveedorescostos where id=?");
            psEliminar.setInt(1, idEliminar);
            psEliminar.executeUpdate();            
            psConsultaMayor.close();
            psEliminar.close();
        }
        ps.close();
        cnn.close();
    }
    
    public static ArrayList<ProductosProveedoresCostosBean> mostrarProductoProveedorCosto() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductosProveedoresCostosBean> lista = new ArrayList<ProductosProveedoresCostosBean>();
        ps = cnn.prepareStatement("select * from productosproveedorescostos");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductosProveedoresCostosBean p = new ProductosProveedoresCostosBean() {
            };
//id        nProvCodigo        codigo                precioCosto                fecha            
            p.setId(rs.getInt("id"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setCodigo(rs.getString("codigo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setFecha(rs.getTimestamp("fecha"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //nuevos
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
    public static ArrayList<ProductosProveedoresCostosBean> listarPPCPorPP(String codigo, int codProv) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductosProveedoresCostosBean> lista = new ArrayList<ProductosProveedoresCostosBean>();
        ps = cnn.prepareStatement("select * from productosproveedorescostos where nProvCodigo=? and codigo=?");
        ps.setInt(1, codProv);
        ps.setString(2, codigo);        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductosProveedoresCostosBean p = new ProductosProveedoresCostosBean() {
            };
            p.setId(rs.getInt("id"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setCodigo(rs.getString("codigo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setFecha(rs.getTimestamp("fecha"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //nuevos
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
    public static boolean actualizarPPC(ProductosProveedoresCostosBean p) throws SQLException {
        /*
   setnProvCodigo        
        setPrecioMaxPublico setPrecioVenta setPrecioSinIva setPorcentajeDescuento setPrecioCosto
        */
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("update productosproveedorescostos set "
                + "precioCosto=?, "
                + "precioVenta=?,"
                + "precioMaxPublico=?,"
                + "precioSinIva=?,"
                + "porcentajeDescuento=?"
                + "where nProvCodigo=? and codigo=? and id=?");
        ps.setDouble(1, p.getPrecioCosto());
        ps.setDouble(2, p.getPrecioVenta());
        ps.setDouble(3, p.getPrecioMaxPublico());
        ps.setDouble(4, p.getPrecioSinIva());
        ps.setDouble(5, p.getPorcentajeDescuento());
        
        
        ps.setInt(6, p.getnProvCodigo());
        ps.setString(7, p.getCodigo());
        ps.setInt(8, p.getId());
        //ps.setTimestamp(5, p.getFecha());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    //obtiene el registro mas actual por codigo de producto y cod de proveedor
    public static ProductosProveedoresCostosBean listarPPCPorCodProd(String codigo,int nProvCodigo) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductosProveedoresCostosBean> lista = new ArrayList<ProductosProveedoresCostosBean>();
        ps = cnn.prepareStatement("SELECT * from productosproveedorescostos where codigo=? and nProvCodigo=? order by fecha desc");
        ps.setString(1, codigo);
        ps.setInt(2, nProvCodigo);
        ResultSet rs = ps.executeQuery();
        ProductosProveedoresCostosBean p = null; 
        //sirve para solo guardar el primer registro encontrado
        int numReg = 1;
        while (rs.next()) {
            if (numReg == 1) {
                p = new ProductosProveedoresCostosBean(); 
                p.setId(rs.getInt("id"));
                p.setnProvCodigo(rs.getInt("nProvCodigo"));
                p.setCodigo(rs.getString("codigo"));
                p.setPrecioCosto(rs.getDouble("precioCosto"));
                p.setFecha(rs.getTimestamp("fecha"));
                p.setIdUsuario(rs.getInt("idUsuario"));
            }
            numReg++;
        }
        ps.close();
        cnn.close();
        return p;
    }
    
    //obtiene el registro mas actual por codigo de producto no importa el proveedor
    public PrecioCompuestoBean obtienePrecioMasActual(String codigo) throws SQLException {
        double precioMasActual = 0;
        String categ = "";
        double precioSinIva = 0;
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("SELECT * from productosproveedorescostos where codigo=? order by fecha desc");
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        //sirve para solo guardar el primer registro encontrado
        int numReg = 1;
        while (rs.next()) {
            if (numReg == 1) {
                precioMasActual = rs.getDouble("precioVenta");
                categ = rs.getString("categoria");      
                precioSinIva = rs.getDouble("precioSinIva");
            }
            numReg++;
        }
        PrecioCompuestoBean pcb = new PrecioCompuestoBean();
        pcb.setPrecioUnitario(precioMasActual);
        pcb.setCategoria(categ);
        pcb.setPrecioSinIva(precioSinIva);
        ps.close();
        cnn.close();
        return pcb;
    }
    
    //consulta precios por codigo en prodprovcostos
    public static ArrayList<ProductosProveedoresCostosBean> consultarPPCPorCodProd(String codigo) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProductosProveedoresCostosBean> lista = new ArrayList<ProductosProveedoresCostosBean>();
        ps = cnn.prepareStatement("select * from productosproveedorescostos where codigo=? order by fecha desc");
        ps.setString(1, codigo);        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductosProveedoresCostosBean p = new ProductosProveedoresCostosBean() {
            };
            p.setId(rs.getInt("id"));
            p.setnProvCodigo(rs.getInt("nProvCodigo"));
            p.setCodigo(rs.getString("codigo"));
            p.setPrecioCosto(rs.getDouble("precioCosto"));
            p.setFecha(rs.getTimestamp("fecha"));
            p.setIdUsuario(rs.getInt("idUsuario"));
            
            //nuevos
            p.setCategoria(rs.getString("categoria"));
            p.setPrecioVenta(rs.getDouble("precioVenta"));
            p.setPrecioMaxPublico(rs.getDouble("precioMaxPublico"));
            p.setPrecioSinIva(rs.getDouble("precioSinIva"));
            p.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
            
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }
}
