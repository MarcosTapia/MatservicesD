package ComponenteDatos;

import beans.CategoriaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BDCategoria {
    
    public static void insertarCategoria(CategoriaBean cat) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into categoria (cCatDescripcion,utilidad) values (?,?)");
        ps.setString(1, cat.getcCatDescripcion());
        ps.setInt(2, cat.getUtilidad());
        ps.executeUpdate();
        ps.close();
        cnn.close();
    }
    
    public static CategoriaBean buscarCategoriaCodigo(int codigo) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        CategoriaBean cat = null;
        ps = cnn.prepareStatement("select nCatCodigo,cCatDescripcion,utilidad from categoria where nCatCodigo=?");
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cat = new CategoriaBean() {
            };
            cat.setnCatCodigo(codigo);
            cat.setcCatDescripcion(rs.getString("cCatDescripcion"));
            cat.setUtilidad(rs.getInt("utilidad"));
        }
        ps.close();
        cnn.close();
        return cat;
    }

    public static CategoriaBean buscarCategoriaDescripcion(String descripcion) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        CategoriaBean cat = null;
        ps = cnn.prepareStatement("select nCatCodigo,cCatDescripcion,utilidad from categoria where cCatDescripcion='"+descripcion+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cat = new CategoriaBean() {
            };
            cat.setcCatDescripcion(rs.getString("cCatDescripcion"));
            cat.setnCatCodigo(rs.getInt("nCatCodigo"));
            cat.setUtilidad(rs.getInt("utilidad"));
        }
        ps.close();
        cnn.close();
        return cat;
    }

    public static boolean eliminarCategoria(CategoriaBean cat) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("delete from categoria where nCatCodigo=?");
        ps.setInt(1, cat.getnCatCodigo());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean actualizarCategoria(CategoriaBean cat) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ps = cnn.prepareStatement("update categoria set cCatDescripcion=?, utilidad=? where nCatCodigo=" + cat.getnCatCodigo());
        ps.setString(1, cat.getcCatDescripcion());
        ps.setInt(2, cat.getUtilidad());
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<CategoriaBean> mostrarCategoria() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ArrayList<CategoriaBean> lista = new ArrayList<>();
        ps = cnn.prepareStatement("select nCatCodigo,cCatDescripcion,utilidad from categoria");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CategoriaBean cat = new CategoriaBean() {
            };
            cat.setnCatCodigo(rs.getInt("nCatCodigo"));
            cat.setcCatDescripcion(rs.getString("cCatDescripcion"));
            cat.setUtilidad(rs.getInt("utilidad"));
            lista.add(cat);
        }
        ps.close();
        cnn.close();
        return lista;
    }
}