/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ComponenteReportes;

import ComponenteDatos.BD;
import java.sql.Connection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import java.util.*;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BADY RICHARD
 */
public class ReporteListaProducto {

    private Connection cnn;

    public ReporteListaProducto() {
        cnn = BD.getConnection();
    }

    public void runRListaProducto() {
        try {
            String master = System.getProperty("user.dir") + "/src/ComponenteReportes/ReporteListaProductos.jasper";
            //System.out.println("master" + master);
            if (master == null) {
                JOptionPane.showMessageDialog(null, "no encuentro el archivo de reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport = null;
            try {
                masterReport = (JasperReport) JRLoader.loadObject(master);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, "error cargando el reporte maestro:" + e.getMessage());
                System.exit(3);
            }
            Map parametro = new HashMap();
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle("REPORTE LISTA DE CLIENTES");
            jviewer.setVisible(true);
            cnn.close();
        } catch (Exception j) {
            JOptionPane.showMessageDialog(null, "mensaje de error:" + j.getMessage());
        }
    }
}