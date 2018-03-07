package ComponenteReportes.ComponenteReportes;

import ComponenteDatos.BD;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteComprasGeneral {
    private Connection cnn;

    public ReporteComprasGeneral() {
        cnn = BD.getConnection();
    }
    
    public void runReporteComprasGeneral() {
        try {
            String master = System.getProperty("user.dir") + "/src/"
                    + "ComponenteReportes/reporteGeneralCompras.jasper";
            if (master == null) {
                JOptionPane.showMessageDialog(null, "no encuentro el archivo de "
                        + "reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport = null;
            try {
                masterReport = (JasperReport) JRLoader.loadObject(master);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null,"error cargando el reporte "
                        + "maestro:" + e.getMessage());
                System.exit(3);
            }
            Map parametro = new HashMap();
//            parametro.put("nProCodigo",codProducto);
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, 
                    parametro, cnn);
            
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle("REPORTE GENERAL DE COMPRAS");
            jviewer.requestFocus();
            jviewer.setVisible(true);
            cnn.close();
        } catch (Exception j) {
            JOptionPane.showMessageDialog(null,"mensaje de error:" + j.getMessage());
        }
    }
}
