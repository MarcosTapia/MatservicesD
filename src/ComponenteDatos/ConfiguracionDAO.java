package ComponenteDatos;

import ComponenteDatos.Conexion;
import beans.DatosEmpresaBean;
import beans.UsuarioBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class ConfiguracionDAO {
	public boolean GuardaConfiguracion(DatosEmpresaBean configuracionBean) {
		Conexion conex = new Conexion();
                boolean status = false;
		try {
			PreparedStatement cmd;
			cmd = conex.conectarse().prepareStatement("UPDATE configuracion SET nombreEmpresa=?, utilidad=?,"
                                + "iva=? where id = 1");
			cmd.setString(1, configuracionBean.getNombreEmpresa());
			cmd.setDouble(2, configuracionBean.getUtilidad());
			cmd.setDouble(3, configuracionBean.getIva());
			cmd.executeUpdate();
                        status = true;
                        cmd.close();
                        conex.cerrar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
                        conex.cerrar();
                }
                return status;
	}
        
	public DatosEmpresaBean obtieneConfiguracion(int id) {
		DatosEmpresaBean obj = null;
		Conexion conex = new Conexion();
		try {
			PreparedStatement cmd;
			cmd = conex.conectarse().prepareStatement("select * from configuracion where id=?");
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			if (rs.next()) {
				obj = new DatosEmpresaBean();
                                obj.setNombreEmpresa(rs.getString("nombreEmpresa"));
				obj.setUtilidad(rs.getDouble("utilidad"));
				obj.setIva(rs.getDouble("iva"));
			}
                        cmd.close();
                        conex.cerrar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
                return obj;
	}
        
}
