package ComponenteDatos;

import beans.VentasBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class BDVentas {
    
    public void guardaVenta(VentasBean ventasBean) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into ventas (nCliCodigo,idUsuario,cVenFecha,nVenMontoTotal) values (?,?,?,?)");
        ps.setInt(1, ventasBean.getnCliCodigo());
        ps.setInt(2, ventasBean.getIdUsuario());
        ps.setString(3,ventasBean.getcVenFecha());
        ps.setDouble(4, ventasBean.getnVenMontoTotal());
        ps.executeUpdate();
        ps.close();
        cnn.close();
    }
    
    public static ArrayList<VentasBean> mostrarVentas() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ArrayList<VentasBean> lista = new ArrayList<>();
        ps = cnn.prepareStatement("select * from ventas");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            VentasBean ventasBean = new VentasBean() {
            };
            ventasBean.setnVenCodigo(rs.getInt("nVenCodigo"));
            ventasBean.setnCliCodigo(rs.getInt("nCliCodigo"));
            ventasBean.setIdUsuario(rs.getInt("idUsuario"));
            ventasBean.setcVenFecha(rs.getString("cVenFecha"));
            ventasBean.setnVenMontoTotal(rs.getDouble("nVenMontoTotal"));
            lista.add(ventasBean);
        }
        ps.close();
        cnn.close();
        return lista;
    }

    public static ArrayList<VentasBean> buscarVentaPorCodigo(int codigoVenta) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<VentasBean> lista = new ArrayList<>();
        VentasBean ventasBean = null;
        ps = cnn.prepareStatement("select * from ventas where nVenCodigo='"+codigoVenta+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ventasBean = new VentasBean() {
            };
            ventasBean.setnVenCodigo(rs.getInt("nVenCodigo"));
            ventasBean.setnCliCodigo(rs.getInt("nCliCodigo"));
            ventasBean.setIdUsuario(rs.getInt("idUsuario")); 
            ventasBean.setcVenFecha(rs.getString("cVenFecha"));
            ventasBean.setnVenMontoTotal(rs.getDouble("nVenMontoTotal")); 
            lista.add(ventasBean);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
    public static ArrayList<VentasBean> buscarVentaPorUsuario(int idUsuario) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ArrayList<VentasBean> lista = new ArrayList<>();
        VentasBean ventasBean = null;
        ps = cnn.prepareStatement("select * from ventas where idUsuario = '"+idUsuario+"'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ventasBean = new VentasBean();
            ventasBean.setnVenCodigo(rs.getInt("nVenCodigo"));
            ventasBean.setnCliCodigo(rs.getInt("nCliCodigo"));
            ventasBean.setIdUsuario(rs.getInt("idUsuario")); 
            ventasBean.setcVenFecha(rs.getString("cVenFecha"));
            ventasBean.setnVenMontoTotal(rs.getDouble("nVenMontoTotal")); 
            lista.add(ventasBean);
        }
        ps.close();
        cnn.close();
        return lista;
    }    
    
    public static ArrayList<VentasBean> mostrarVentasPorFecha(java.sql.Date fechaSqlDateIni,
        java.sql.Date fechaSqlDateFin) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ArrayList<VentasBean> lista = new ArrayList<>();
        if (fechaSqlDateIni == fechaSqlDateFin) {
            ps = cnn.prepareStatement("select * from ventas where Date(cVenFecha) = '" + fechaSqlDateIni.toString() + "'");
        } else {
            ps = cnn.prepareStatement("select * from ventas where Date(cVenFecha) >= '" + fechaSqlDateIni.toString() + "'"
                    + "and Date(cVenFecha) <= '" + fechaSqlDateFin.toString() + "'");
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            VentasBean ventasBean = new VentasBean() {
            };
            ventasBean.setnVenCodigo(rs.getInt("nVenCodigo"));
            ventasBean.setnCliCodigo(rs.getInt("nCliCodigo"));
            ventasBean.setIdUsuario(rs.getInt("idUsuario"));
            ventasBean.setcVenFecha(rs.getString("cVenFecha"));
            ventasBean.setnVenMontoTotal(rs.getDouble("nVenMontoTotal"));
            lista.add(ventasBean);
        }
        ps.close();
        cnn.close();
        return lista;
    }
}