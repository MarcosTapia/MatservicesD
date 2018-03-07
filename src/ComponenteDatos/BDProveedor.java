package ComponenteDatos;

import beans.ProveedorBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public abstract class BDProveedor {

    public static ProveedorBean insertarProveedor(ProveedorBean pv) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into proveedor (nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono,cProvNumTelefono,cProvEstado,cProvObservacion) values (?,?,?,?,?,?,?,?,?,?)");

        ps.setString(1, pv.getnPRovNit());
        ps.setString(2, pv.getcProvNombre());
        ps.setString(3, pv.getcProvDireccion());
        ps.setString(4, pv.getcProvNroFax());
        ps.setString(5, pv.getcProvPaginaWeb());
        ps.setString(6, pv.getcProvEmail());
        ps.setString(7, pv.getcProvTipoTelefono());
        ps.setString(8, pv.getcProvNumTelefono());
        ps.setString(9, pv.getcProvEstado());
        ps.setString(10,pv.getcProvObservacion());
        ps.executeUpdate();
        PreparedStatement ps2 = cnn.prepareStatement("select max(nProvCodigo) from proveedor");
        ResultSet r = ps2.executeQuery();
        if (r.next()) {
            int lastID = r.getInt(1);
            pv.setnProvCodigo(lastID);
        }
        ps2.close();
        ps.close();
        cnn.close();
        return pv;
    }

    public static ProveedorBean buscarProveedor(int codigo) throws SQLException {
        return buscarProveedor("select nProvCodigo,nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono,cProvNumTelefono,cProvEstado,cProvObservacion from proveedor where nProvCodigo=" + codigo, null);
    }

    public static ProveedorBean buscarProveedorNombre(String nombre) throws SQLException {
        return buscarProveedor("select nProvCodigo,nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono,cProvNumTelefono,cProvEstado,cProvObservacion from proveedor where cProvNombre='" + nombre + "'", null);
    }

    public static ProveedorBean buscarProveedorNit(int nit) throws SQLException {
        return buscarProveedor("select nProvCodigo,nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono,cProvNumTelefono,cProvEstado,cProvObservacion from proveedor where nProvNit=" + nit, null);
    }

    public static ProveedorBean buscarProveedor(String sql, ProveedorBean pv) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement(sql);
        //ps.setString(1, Ruc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (pv == null) {
                pv = new ProveedorBean() {
                };
            }
            pv.setnProvCodigo(rs.getInt("nProvCodigo"));
            pv.setnPRovNit(rs.getString("nPRovNit"));
            pv.setcProvNombre(rs.getString("cProvNombre"));
            pv.setcProvDireccion(rs.getString("cProvDireccion"));
            pv.setcProvNroFax(rs.getString("cProvNroFax"));
            pv.setcProvPaginaWeb(rs.getString("cProvPaginaWeb"));
            pv.setcProvEmail(rs.getString("cProvEmail"));
            pv.setcProvTipoTelefono(rs.getString("cProvTipoTelefono"));
            pv.setcProvNumTelefono(rs.getString("cProvNumTelefono"));
            pv.setcProvEstado(rs.getString("cProvEstado"));
            pv.setcProvObservacion(rs.getString("cProvObservacion"));
        }
        ps.close();
        cnn.close();
        return pv;
    }

    public static boolean eliminarProveedor(ProveedorBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("delete from PROVEEDOR WHERE nProvCodigo=?");
        ps.setInt(1, p.getnProvCodigo());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean actualizarProveedor(ProveedorBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;

        ps = cnn.prepareStatement("update proveedor set nProvNit=?,cProvNombre=?,cProvDireccion=?,cProvNroFax=?,cProvPaginaWeb=?,cProvEmail=?,cProvTipoTelefono=?,cProvNumTelefono=?,cProvEstado=?,cProvObservacion=? where nProvCodigo=" + p.getnProvCodigo());
        ps.setString(1, p.getnPRovNit());
        ps.setString(2, p.getcProvNombre());
        ps.setString(3, p.getcProvDireccion());
        ps.setString(4, p.getcProvNroFax());
        ps.setString(5, p.getcProvPaginaWeb());
        ps.setString(6, p.getcProvEmail());
        ps.setString(7, p.getcProvTipoTelefono());
        ps.setString(8, p.getcProvNumTelefono());
        ps.setString(9, p.getcProvEstado());
        ps.setString(10, p.getcProvObservacion());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ProveedorBean> mostrarProveedor() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ProveedorBean> lista = new ArrayList<ProveedorBean>();

        ps = cnn.prepareStatement("select nProvCodigo,nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono, cProvNumTelefono,cProvEstado,cProvObservacion from proveedor");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProveedorBean pv = new ProveedorBean() {
            };
            pv.setnProvCodigo(rs.getInt(1));
            pv.setnPRovNit(rs.getString(2));
            pv.setcProvNombre(rs.getString(3));
            pv.setcProvDireccion(rs.getString(4));
            pv.setcProvNroFax(rs.getString(5));
            pv.setcProvPaginaWeb(rs.getString(6));
            pv.setcProvEmail(rs.getString(7));
            pv.setcProvTipoTelefono(rs.getString(8));
            pv.setcProvNumTelefono(rs.getString(9));
            pv.setcProvEstado(rs.getString(10));
            pv.setcProvObservacion(rs.getString(11));
            lista.add(pv);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<ProveedorBean> listarProveedorPorNombre(String nombre) {
        return listar("cProvNombre", nombre + "%", "like");
    }
    public static ArrayList<ProveedorBean> listarProveedorPorNit(String nit) {
        return listar("nProvNit", nit+ "%", "like");
    }
    public static ArrayList<ProveedorBean> listarProveedorPorCodigo(String codigo) {
        return listar("nProvCodigo", codigo + "%", "like");
    }

    private static ArrayList<ProveedorBean> listar(String atributo, String parametro, String comparador) {
        return consultarSQL("select nProvCodigo,nProvNit,cProvNombre,cProvDireccion,cProvNroFax,cProvPaginaWeb,cProvEmail,cProvTipoTelefono,cProvNumTelefono,cProvEstado,cProvObservacion from proveedor where " + atributo + " " + comparador + " '" + parametro + "'");
    }

    private static ArrayList<ProveedorBean> consultarSQL(String sql) {
        ArrayList<ProveedorBean> list = new ArrayList<ProveedorBean>();
        Connection cn = BD.getConnection();
        try {
            ProveedorBean p;
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                p = new ProveedorBean();
                p.setnProvCodigo(rs.getInt("nProvCodigo"));
                p.setnPRovNit(rs.getString("nProvNit"));
                p.setcProvNombre(rs.getString("cProvNombre"));
                p.setcProvDireccion(rs.getString("cProvDireccion"));
                p.setcProvNroFax(rs.getString("cProvNroFax"));
                p.setcProvPaginaWeb(rs.getString("cProvPaginaWeb"));
                p.setcProvEmail(rs.getString("cProvEmail"));
                p.setcProvTipoTelefono(rs.getString("cProvTipoTelefono"));
                p.setcProvNumTelefono(rs.getString("cProvNumTelefono"));
                p.setcProvEstado(rs.getString("cProvEstado"));
                p.setcProvObservacion(rs.getString("cProvObservacion"));
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
    
    public static HashMap<Integer, String> mostrarProveedorHashMap() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        HashMap<Integer, String> NombreProveedorConsulta = new HashMap<Integer, String>();
        ps = cnn.prepareStatement("select nProvCodigo,cProvNombre from proveedor");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProveedorBean pv = new ProveedorBean() {
            };
            NombreProveedorConsulta.put(rs.getInt("nProvCodigo"), rs.getString("cProvNombre"));
        }
        ps.close();
        cnn.close();
        return NombreProveedorConsulta;
    }
    
    public static ProveedorBean mostrarProveedorPorNombre(String nombre) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ProveedorBean pv = new ProveedorBean();
        ps = cnn.prepareStatement("select * from proveedor where cProvNombre = '" + nombre + "'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            pv.setnProvCodigo(rs.getInt(1));
            pv.setnPRovNit(rs.getString(2));
            pv.setcProvNombre(rs.getString(3));
            pv.setcProvDireccion(rs.getString(4));
            pv.setcProvNroFax(rs.getString(5));
            pv.setcProvPaginaWeb(rs.getString(6));
            pv.setcProvEmail(rs.getString(7));
            pv.setcProvTipoTelefono(rs.getString(8));
            pv.setcProvNumTelefono(rs.getString(9));
            pv.setcProvEstado(rs.getString(10));
            pv.setcProvObservacion(rs.getString(11));
        }
        ps.close();
        cnn.close();
        return pv;
    }
    
}