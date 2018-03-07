package ComponenteDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public abstract class BD {
//     public static Connection getConnection() {
//        Connection cn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "jdbc:mysql://localhost:3306/sistemainventario";
//            String user = "root";
//            //String password = "";
//            String password = "";
//            //String password = "123456";
//            cn= DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException e) {
//            cn=null;
//            JOptionPane.showMessageDialog(null, "Error no se puede cargar el driver:" + e.getMessage());
//        } catch (SQLException e) {
//            cn=null;
//            JOptionPane.showMessageDialog(null, "Error no se establecer la conexion:" + e.getMessage());
//        }
//        return cn;
//    }

     public static Connection getConnection() {
        Connection cn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            cn= DriverManager.getConnection("jdbc:sqlite:sistemainventario.db");
            cn.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            cn=null;
            JOptionPane.showMessageDialog(null, "Error no se puede cargar el driver:" + e.getMessage());
        } catch (SQLException e) {
            cn=null;
            JOptionPane.showMessageDialog(null, "Error no se establece la conexion:" + e.getMessage());
        }
        return cn;
    }

}
