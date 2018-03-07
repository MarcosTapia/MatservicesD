package ComponenteDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Conexion {
        Connection cn = null;
//	public Connection conectarse() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemaInventario","root","");
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "Error en Conexion a la base de "
//                                + "Probablemente no esté prendido el servidor");
//		}
//		return cn;
//	} 

    public Connection conectarse() {
        try {
                Class.forName("org.sqlite.JDBC");
                cn= DriverManager.getConnection("jdbc:sqlite:sistemainventario.db");
                cn.setAutoCommit(true);
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en Conexion a la base de "
                        + "Probablemente no esté prendido el servidor");
        }
        return cn;
    } 
        
        
    public void cerrar() {
        try {
            if (cn != null) {
                if (cn.isClosed() == false) {
                    cn.close();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}