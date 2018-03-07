package ComponenteDatos;

import beans.ClienteBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BDCliente {

    public static ClienteBean insertarCliente(ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into cliente (cCliNit,cCliCi,cCliNombre,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros) values (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getcCliNit());
        ps.setString(2, c.getcCliCi());
        ps.setString(3, c.getcCliNombre());
        ps.setString(4, c.getcCliDireccion());
        ps.setString(5, c.getcCliNumTelefono());
        ps.setString(6, c.getcCliTipoTelefono());
        ps.setString(7, c.getcCliNroFax());
        ps.setString(8, c.getcCliEmail());
        ps.setString(9, c.getcCliOtros());
        ps.executeUpdate();
        PreparedStatement ps2 = cnn.prepareStatement("select max(nCliCodigo) from cliente");
        ResultSet r = ps2.executeQuery();
        if (r.next()) {
            int lastID = r.getInt(1);
            c.setnCliCodigo(lastID);
        }
        ps2.close();
        ps.close();
        cnn.close();
        return c;
    }

    public static ClienteBean buscarClienteCodigo(int codigo) throws SQLException {
        return buscarClienteCodigo(codigo, null);
    }

    public static ClienteBean buscarClienteCodigo(int codigo, ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select cCliNit,cCliCi,cCliNombre,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros from cliente where nCliCodigo=?");
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (c == null) {
                c = new ClienteBean() {
                };
            }
            c.setnCliCodigo(codigo);
            c.setcCliNit(rs.getString("cCliNit"));
            c.setcCliCi(rs.getString("cCliCi"));
            c.setcCliNombre(rs.getString("cCliNombre"));
            c.setcCliDireccion(rs.getString("cCliDireccion"));
            c.setcCliNumTelefono(rs.getString("cCliNumTelefono"));
            c.setcCliTipoTelefono(rs.getString("cCliTipoTelefono"));
            c.setcCliNroFax(rs.getString("cCliNroFax"));
            c.setcCliEmail(rs.getString("cCliEmail"));
            c.setcCliOtros(rs.getString("cCliOtros"));
        }
        ps.close();
        cnn.close();
        return c;
    }

    public static ClienteBean buscarClienteNit(String nit) throws SQLException {
        return buscarClienteNit(nit, null);
    }

    public static ClienteBean buscarClienteNit(String nit, ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select nCliCodigo,cCliCi,cCliNombre,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros from cliente where cCliNit=?");
        ps.setString(1, nit);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (c == null) {
                c = new ClienteBean() {
                };
            }
            c.setcCliNit(nit);
            c.setnCliCodigo(rs.getInt("nCliCodigo"));
            c.setcCliCi(rs.getString("cCliCi"));
            c.setcCliNombre(rs.getString("cCliNombre"));
            c.setcCliDireccion(rs.getString("cCliDireccion"));
            c.setcCliNumTelefono(rs.getString("cCliNumTelefono"));
            c.setcCliTipoTelefono(rs.getString("cCliTipoTelefono"));
            c.setcCliNroFax(rs.getString("cCliNroFax"));
            c.setcCliEmail(rs.getString("cCliEmail"));
            c.setcCliOtros(rs.getString("cCliOtros"));
        }
        ps.close();
        cnn.close();
        return c;
    }

    public static ClienteBean buscarClienteNombre(String nombre) throws SQLException {
        return buscarClienteNombre(nombre, null);
    }

    public static ClienteBean buscarClienteNombre(String nombre, ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select nCliCodigo,cCliCi,cCliNit,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros from cliente where cCliNombre=?");
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            c = c == null ? new ClienteBean() {
            } : c;

            c.setcCliNombre(nombre);
            c.setnCliCodigo(rs.getInt("nCliCodigo"));
            c.setcCliCi(rs.getString("cCliCi"));
            c.setcCliNit(rs.getString("cCliNit"));
            c.setcCliDireccion(rs.getString("cCliDireccion"));
            c.setcCliNumTelefono(rs.getString("cCliNumTelefono"));
            c.setcCliTipoTelefono(rs.getString("cCliTipoTelefono"));
            c.setcCliNroFax(rs.getString("cCliNroFax"));
            c.setcCliEmail(rs.getString("cCliEmail"));
            c.setcCliOtros(rs.getString("cCliOtros"));

        } else {
            c = null;
        }
        ps.close();
        cnn.close();
        return c;
    }

    public static boolean eliminarCliente(ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("delete from CLIENTE WHERE nCliCodigo=?");
        ps.setInt(1, c.getnCliCodigo());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean actualizarCliente(ClienteBean c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;

        ps = cnn.prepareStatement("update cliente set cCliNombre=?,cCliCi=?, cCliNit=?, cCliDireccion=?, cCliNumTelefono=?, cCliTipoTelefono=?, cCliNroFax=?, cCliEmail=?, cCliOtros=? where nCliCodigo=" + c.getnCliCodigo());
        ps.setString(1, c.getcCliNombre());
        ps.setString(2, c.getcCliCi());
        ps.setString(3, c.getcCliNit());
        ps.setString(4, c.getcCliDireccion());
        ps.setString(5, c.getcCliNumTelefono());
        ps.setString(6, c.getcCliTipoTelefono());
        ps.setString(7, c.getcCliNroFax());
        ps.setString(8, c.getcCliEmail());
        ps.setString(9, c.getcCliOtros());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ClienteBean> mostrarCliente() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<ClienteBean> lista = new ArrayList<ClienteBean>();

        ps = cnn.prepareStatement("select nCliCodigo,cCliCi,cCliNit,cCliNombre,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros from cliente");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ClienteBean c = new ClienteBean() {
            };
            c.setnCliCodigo(rs.getInt("nCliCodigo"));
            c.setcCliCi(rs.getString("cCliCi"));
            c.setcCliNit(rs.getString("cCliNit"));
            c.setcCliNombre(rs.getString("cCliNombre"));
            c.setcCliDireccion(rs.getString("cCliDireccion"));
            c.setcCliNumTelefono(rs.getString("cCliNumTelefono"));
            c.setcCliTipoTelefono(rs.getString("cCliTipoTelefono"));
            c.setcCliNroFax(rs.getString("cCliNroFax"));
            c.setcCliEmail(rs.getString("cCliEmail"));
            c.setcCliOtros(rs.getString("cCliOtros"));
            lista.add(c);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<ClienteBean> listarClientePorNombre(String nombre) {
        return listar("cCliNombre", nombre + "%", "like");
    }

    public static ArrayList<ClienteBean> listarClientePorCodigo(String codigo) {
        return listar("cCliNit", codigo + "%", "like");
    }

    private static ArrayList<ClienteBean> listar(String atributo, String parametro, String comparador) {
        return consultarSQL("select nCliCodigo,cCliCi,cCliNit,cCliNombre,cCliDireccion,cCliNumTelefono,cCliTipoTelefono,cCliNroFax,cCliEmail,cCliOtros from cliente where " + atributo + " " + comparador + " '" + parametro + "'");
    }

    private static ArrayList<ClienteBean> consultarSQL(String sql) {
        ArrayList<ClienteBean> list = new ArrayList<ClienteBean>();
        Connection cn = BD.getConnection();
        try {
            ClienteBean cli;
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cli = new ClienteBean();
                cli.setnCliCodigo(rs.getInt("nCliCodigo"));
                cli.setcCliCi(rs.getString("cCliCi"));
                cli.setcCliNit(rs.getString("cCliNit"));
                cli.setcCliNombre(rs.getString("cCliNombre"));
                cli.setcCliDireccion(rs.getString("cCliDireccion"));
                cli.setcCliNumTelefono(rs.getString("cCliNumTelefono"));
                cli.setcCliTipoTelefono(rs.getString("cCliTipoTelefono"));
                cli.setcCliNroFax(rs.getString("cCliNroFax"));
                cli.setcCliEmail(rs.getString("cCliEmail"));
                cli.setcCliOtros(rs.getString("cCliOtros"));
                list.add(cli);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
        return list;
    }
}