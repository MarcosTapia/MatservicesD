package ComponenteDatos;

import beans.UsuarioBean;
import beans.UsuarioBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public abstract class BDUsuario {
    public static HashMap<Integer, String> mostrarUsuariosHashMap() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        HashMap<Integer, String> nombreUsuariosConsulta = new HashMap<Integer, String>();
        ps = cnn.prepareStatement("select idUsuario,nombre from usuarios");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            UsuarioBean usuario = new UsuarioBean() {
            };
            nombreUsuariosConsulta.put(rs.getInt("idUsuario"), rs.getString("nombre"));
        }
        ps.close();
        cnn.close();
        return nombreUsuariosConsulta;
    }
    
    public static UsuarioBean insertarUsuario(UsuarioBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into usuarios (usuario,password,clase,nombre) values (?,?,?,?)");
        ps.setString(1, p.getUsuario());
        ps.setString(2, p.getPassword());
        ps.setString(3, p.getClase());
        ps.setString(4, p.getNombre());
        ps.executeUpdate();
        PreparedStatement ps2 = cnn.prepareStatement("select max(idUsuario) from usuarios");
        ResultSet r = ps2.executeQuery();
        if (r.next()) {
            int lastID = r.getInt(1);
            p.setIdUsuario(lastID);
        }
        ps2.close();
        ps.close();
        cnn.close();
        return p;
    }

    public static UsuarioBean buscarUsuariosCodigo(int codigo) throws SQLException {
        return buscarUsuarioNuevo("select * from usuarios where idUsuario=" + codigo, null);
    }

    public static UsuarioBean buscarUsuariosNombre(String nombre) throws SQLException {
        return buscarUsuarioNuevo("select * from usuarios where nombre='" + nombre+"'", null);
    }

    public static UsuarioBean buscaerUsuariosEstado(String estado) throws SQLException {
        return buscarUsuarioNuevo("select * from usuarios where estado='" + estado+"'", null);
    }

    public static UsuarioBean buscarUsuarioNuevo(String sql, UsuarioBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (p == null) {
                p = new UsuarioBean() {
                };
            }
            p.setIdUsuario(rs.getInt("idUsuario"));
            p.setUsuario(rs.getString("usuario"));
            p.setPassword(rs.getString("password"));
            p.setNombre(rs.getString("nombre"));
            p.setClase(rs.getString("clase"));
        }
        ps.close();
        cnn.close();
        return p;
    }

    public static boolean eliminarUsuario(UsuarioBean usuario) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("delete from usuarios WHERE idUsuario=?");
        ps.setInt(1, usuario.getIdUsuario());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean actualizarUsuario(UsuarioBean p) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("update usuarios set usuario=?,password=?,nombre=?,clase=? where idUsuario=" + p.getIdUsuario());
        ps.setString(1, p.getUsuario());
        ps.setString(2, p.getPassword());
        ps.setString(3, p.getNombre());
        ps.setString(4, p.getClase());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<UsuarioBean> mostrarUsuarios() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<UsuarioBean> lista = new ArrayList<UsuarioBean>();
        ps = cnn.prepareStatement("select * from usuarios");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            UsuarioBean p = new UsuarioBean() {
            };
            p.setIdUsuario(rs.getInt("idUsuario"));
            p.setUsuario(rs.getString("usuario"));
            p.setPassword(rs.getString("password"));
            p.setNombre(rs.getString("nombre"));
            p.setClase(rs.getString("clase"));
            lista.add(p);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<UsuarioBean> listarUsuariosPorNombre(String nombre) {
        return listar("nombre", nombre + "%", "like");
    }

    public static ArrayList<UsuarioBean> listarUsuariosPorCodigo(int id) {
        return listar("idUsuario", id + "%", "like");
    }

    private static ArrayList<UsuarioBean> listar(String atributo, String parametro, String comparador) {
        System.out.print("select * from usuarios where " + atributo + " " + comparador + " '" + parametro + "'");
        return consultarSQL("select * from usuarios where " + atributo + " " + comparador + " '" + parametro + "'");
    }

    public static ArrayList<UsuarioBean> listarGeneral() {
        return consultarSQL("select * from usuarios");
    }
    
    private static ArrayList<UsuarioBean> consultarSQL(String sql) {
        ArrayList<UsuarioBean> list = new ArrayList<UsuarioBean>();
        Connection cn = BD.getConnection();
        try {
            UsuarioBean p;
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                p = new UsuarioBean();
                p.setIdUsuario(rs.getInt("idUsuario"));
                p.setUsuario(rs.getString("usuario"));
                p.setPassword(rs.getString("password"));
                p.setNombre(rs.getString("nombre"));
                p.setClase(rs.getString("clase"));
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
    
    public static int buscaUsuarioPorNombre(String nombre) throws SQLException {
        int idUsuario = -1;
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select * from usuarios where nombre = '" + nombre + "'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            idUsuario = rs.getInt("idUsuario");
        }
        ps.close();
        cnn.close();
        return idUsuario;
    }
}