package ComponenteReportes;

import ComponenteDatos.BD;
import java.sql.Connection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import java.util.*;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteProductoGeneral {
    private Connection cnn;

    public ReporteProductoGeneral() {
        cnn = BD.getConnection();
    }
    
    public void runReporteProductoGeneral() {
        try {
            String master = System.getProperty("user.dir") + "/src/"
                    + "ComponenteReportes/reporteGeneralProducto.jasper";
            if (master == null) {
                JOptionPane.showMessageDialog(null, "no encuentro el "
                        + "archivo de reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport = null;
            try {
                masterReport = (JasperReport) JRLoader.loadObject(master);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null,"error cargando el "
                        + "reporte maestro:" + e.getMessage());
                System.exit(3);
            }
            Map parametro = new HashMap();
//            parametro.put("nProCodigo",codProducto);
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, cnn);
            
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle("REPORTE GENERAL DE INVENTARIO");
            jviewer.setVisible(true);
            jviewer.requestFocus();
            cnn.close();
        } catch (Exception j) {
            JOptionPane.showMessageDialog(null,"mensaje de error:" + j.getMessage());
        }
        
//Map params = new HashMap();
//params.put("outputText", "Hello world");
//try {
//        String master = System.getProperty("user.dir") + "/src/ComponenteReportes/ReporteListaProducto.jasper";
//        JasperViewer jv = new JasperViewer(master);
//        jv.show();
//    } catch (JRException ex) {
//        ex.printStackTrace();
//    }        
    }
}
