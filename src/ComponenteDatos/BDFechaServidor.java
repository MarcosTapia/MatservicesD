package ComponenteDatos;

import beans.CategoriaBean;
import beans.FechaServidorBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class BDFechaServidor {
//        public java.sql.Timestamp fechaServidor() {
//             //INSERTA EN TABLA FECHA LA FECHA DEL SERVIDOR
//             String fechastring="";
//             Connection conn = null;
//             try {
//              conn = DriverManager.getConnection(url,login,password);
//              if (conn != null) {
//                Statement stmt = conn.createStatement();
//                PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO fecha(fecha) SELECT CURDATE()");
//                stmt1.executeUpdate();
//                stmt1.close();
//                stmt.close();
//                conn.close();
//              }
//             } catch(SQLException ex) {
//                javax.swing.JOptionPane.showMessageDialog(null,ex);
//              }
//             //CONSULTA DE TABLA FECHA LA FECHA DEL SERVIDOR
//        conn = null;
//        try {
//            conn = DriverManager.getConnection(url,login,password);
//            if (conn != null) {
//                Statement stmt = conn.createStatement();
//                ResultSet res = stmt.executeQuery("SELECT * FROM fecha");
//                while(res.next()) {
//                 fechastring=res.getString("fecha");
//                }
//                res.close();
//                stmt.close();
//                stmt = conn.createStatement();
//                conn.close();
//            }
//        } catch(SQLException ex) {
//            javax.swing.JOptionPane.showMessageDialog(null,ex);
//        }
//            
//            
//        }
    
    public static boolean actualizarFecha() throws SQLException {
        Connection cnn = BD.getConnection();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        JOptionPane.showMessageDialog(null, timeStamp);
        
        PreparedStatement ps;//datetime('now','localtime')   CURDATE()
        ps = cnn.prepareStatement("update fechaServidor set fecha=NOW() where id=1");
//        ps = cnn.prepareStatement("update fechaServidor set fecha=" + timeStamp +" where id=1");
//        ps = cnn.prepareStatement("update fechaServidor set fecha=CURDATE() where id=1");
        int rowsUpdated = ps.executeUpdate();
        ps.close();
        cnn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static FechaServidorBean mostrarFechaServidor() throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ArrayList<CategoriaBean> lista = new ArrayList<>();
        ps = cnn.prepareStatement("select fecha from fechaServidor where id=1");
        ResultSet rs = ps.executeQuery();
        FechaServidorBean fechaServidorBean = null;
        while (rs.next()) {
            fechaServidorBean = new FechaServidorBean();
            fechaServidorBean.setFechaServidor(rs.getTimestamp("fecha"));
        }
        ps.close();
        cnn.close();
        return fechaServidorBean;
    }
}