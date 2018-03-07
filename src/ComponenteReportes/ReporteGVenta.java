/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ComponenteReportes;

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

/**
 *
 * @author BADY RICHARD
 */
public class ReporteGVenta {
    private Connection cnn;

    public ReporteGVenta() {
        cnn = BD.getConnection();
    }
    
    public void runGVenta(int codVenta,String fecVenta,String nroVenta,String formaPago,
            String vendedor,String cliente,String dirCliente,String tipDocCliente,String numDocCliente,
            String tipoDocumento,String subTotal,String igv,String totalApagar,String importe,String vuelto) {
        try {

            String master = System.getProperty("user.dir") + "/src/ComponenteReportes/ReporteGVenta.jasper";
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
            parametro.put("ncodVenta",codVenta);
            parametro.put("fecVenta",fecVenta);
            parametro.put("nroVenta",nroVenta);
            parametro.put("formaPago",formaPago);
            parametro.put("vendedor",vendedor);
            parametro.put("cliente",cliente);
            parametro.put("dirCliente",dirCliente);
            parametro.put("tipDocCliente", tipDocCliente);
            parametro.put("numDocCliente",numDocCliente);
            parametro.put("tipoDocumento",tipoDocumento);
            parametro.put("subTotal",subTotal);
            parametro.put("igv",igv);
            parametro.put("totalApagar",totalApagar);
            parametro.put("importe",importe);
            parametro.put("vuelto",vuelto);
            JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle("REPORTE VENTAS");
            jviewer.setVisible(true);
            cnn.close();
        } catch (Exception j) {
            JOptionPane.showMessageDialog(null, "mensaje de error:" + j.getMessage());
        }
    }
}