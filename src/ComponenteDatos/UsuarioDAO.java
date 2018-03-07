package ComponenteDatos;

import ComponenteDatos.Conexion;
import beans.UsuarioBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class UsuarioDAO extends Conexion{
	public boolean validaUsuario(String clave, String password) {
                boolean existe = false;
		// Aqui es donde se hace la busqueda, donde se mete uno
		// a la base de datos
		Conexion conex = new Conexion();
		try {
			PreparedStatement cmd;
			//el signo de interrogacion es para enviado comoo parametro
			// en la sentencia sql
			cmd = conex.conectarse().prepareStatement("select * from usuarios where usuario=? and password=?");
			cmd.setString(1, clave);
			cmd.setString(2, password);
			ResultSet rs = cmd.executeQuery();
			// no uso un while xq solo hay un registro de respuesta
			if (rs.next()) {
                            existe = true;
			}
                        cmd.close();
                        conex.cerrar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
                return existe;
	}

	public UsuarioBean BuscarUsuario(String clave, String password) {
		// Aqui es donde se hace la busqueda, donde se mete uno
		// a la base de datos
		UsuarioBean obj = null;
		Conexion conex = new Conexion();
		try {
			PreparedStatement cmd;
			//el signo de interrogacion es para enviado comoo parametro
			// en la sentencia sql
			cmd = conex.conectarse().prepareStatement("select * from usuarios where usuario=? and password=?");
			cmd.setString(1, clave);
			cmd.setString(2, password);
			ResultSet rs = cmd.executeQuery();
			// no uso un while xq solo hay un registro de respuesta
			if (rs.next()) {
				//si entra es que puede leer y por tanto hay resultado
				obj = new UsuarioBean();
				//solo lleno dos campos que traigo de la tabla
                                obj.setUsuario(rs.getString("usuario"));
				obj.setPassword(rs.getString("password"));
				obj.setClase(rs.getString("clase"));
				obj.setNombre(rs.getString("nombre"));
                                obj.setIdUsuario(rs.getInt("idUsuario"));
			}
                        cmd.close();
                        conex.cerrar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
                return obj;
	}
        
}
