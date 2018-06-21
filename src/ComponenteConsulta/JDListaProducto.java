package ComponenteConsulta;

import beans.DatosEmpresaBean;
import beans.ProductoBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Util;
import vistas.FrmProducto;
import vistas.Ingreso;

public class JDListaProducto extends javax.swing.JDialog {
    Workbook wb;
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    DefaultTableModel LProducto = new DefaultTableModel();
    String empresa = "";
    //WSUsuarios
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSInventariosList hiloInventariosList;
    WSInventarios hiloInventarios;
    //Fin WSUsuarios
    Util util = new Util();

    public JDListaProducto(java.awt.Frame parent, boolean modal
            , Map<String,String> sucursalesHMCons
            , Map<String,String> categoriasHMCons
            , Map<String,String> proveedoresHMCons) {
        super(parent, modal);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        this.empresa = resultadoWS.getNombreEmpresa();
        
        ArrayList<ProductoBean> resultWSArray = null;
        hiloInventariosList = new WSInventariosList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETINVENTARIOS");
        resultWSArray = hiloInventariosList.ejecutaWebService(rutaWS,"1");
        
        Util util = new Util();
        FrmProducto frmproductos = new FrmProducto(0);
        String titulos[] = {"ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO"
                , "EXIST.", "EXIST. MIN","UNIDAD","F. CADUCIDAD"
                ,"SUCURSAL", "CATEGORÍA", "PROVEEDOR"};
        LProducto.setColumnIdentifiers(titulos);
        for (ProductoBean p : resultWSArray) {
            String sucursal = util.buscaDescFromIdSuc(sucursalesHMCons, "" 
                    + p.getIdSucursal());
            String categoria = util.buscaDescFromIdCat(categoriasHMCons, "" 
                    + p.getIdCategoria());
            String proveedor = util.buscaDescFromIdProv(proveedoresHMCons, "" 
                    + p.getIdProveedor());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
            
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes
                            .getProperty("SUPERUSUARIO")))) {
                String Datos[] = {""+p.getIdArticulo()
                        , p.getCodigo()
                        , p.getDescripcion()
                        , "" + p.getPrecioCosto()
                        , "" + p.getPrecioUnitario()
                        , "" + p.getExistencia()
                        , "" + p.getExistenciaMinima()
                        , "" + p.getUnidadMedida()
                        , dateFormat.format(p.getFechaCaducidad())
                        , sucursal
                        , categoria
                        , proveedor};
                LProducto.addRow(Datos);
            }
        }
        initComponents();
        this.setIcon();
    }
    
    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaProductos = new javax.swing.JTable();
        btnImprimir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DE PRODUCTOS");

        tblConsultaProductos.setModel(LProducto);
        jScrollPane1.setViewportView(tblConsultaProductos);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnExportar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xls.png"))); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(36, 36, 36))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel1)
                .addContainerGap(489, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        List Resultados = new ArrayList();
        int fila = 0;
        String datos ="";
        ProductoBean tipo;
        //recorrer la tabla
        for (fila=0;fila<tblConsultaProductos.getRowCount();fila++) {
            tipo = new ProductoBean();
            tipo.setDescripcion(String.valueOf(tblConsultaProductos
                    .getValueAt(fila, 2)));
            tipo.setPrecioCosto(Double.parseDouble(String
                    .valueOf(tblConsultaProductos.getValueAt(fila, 3))));
            tipo.setExistencia(Double.parseDouble(String
                    .valueOf(tblConsultaProductos.getValueAt(fila, 5))));
            tipo.setUnidadMedida(String.valueOf(tblConsultaProductos
                    .getValueAt(fila, 7)));
            tipo.setObservaciones(String.valueOf(tblConsultaProductos
                    .getValueAt(fila, 9)));
            Resultados.add(tipo);
        }
        
        Map map = new HashMap();
        JasperPrint jPrint;
        JDialog reporte = new JDialog();
        reporte.setSize(900,700);
        reporte.setLocationRelativeTo(null);
        reporte.setTitle(this.empresa);
        
        map.put("empresa", this.empresa);
        java.util.Date fecha = new Date();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        map.put("fecha", "Fecha: " + a);
        try {
            this.dispose();
            jPrint = JasperFillManager.fillReport(this.getClass().getClassLoader()
                    .getResourceAsStream("ComponenteReportes/reporteGeneralProducto.jasper")
                    , map, new JRBeanCollectionDataSource(Resultados));
            JRViewer jv = new JRViewer(jPrint);
            reporte.getContentPane().add(jv);
            reporte.setVisible(true);
            reporte.requestFocus();
        } catch (JRException ex) {
            Logger.getLogger(JDListaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        if(selecArchivo.showDialog(null, "Exportar")==JFileChooser.APPROVE_OPTION){
            archivo=selecArchivo.getSelectedFile();
            if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")){
                JOptionPane.showMessageDialog(null, Exportar(archivo
                        , tblConsultaProductos) + "\n Formato ."
                        + archivo.getName().substring(archivo.getName()
                                .lastIndexOf(".")+1));
            }else{
                JOptionPane.showMessageDialog(null, "Elija un formato valido.");
            }
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    public String Exportar(File archivo, JTable tablaD){
        String respuesta="No se realizo con exito la exportación.";
        int numFila=tablaD.getRowCount(), numColumna=tablaD.getColumnCount();
        if(archivo.getName().endsWith("xls")){
            wb = new HSSFWorkbook();
        }else{
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Pruebita");
        try {
            for (int i = -1; i < numFila; i++) {
                Row fila = hoja.createRow(i+1);
                for (int j = 0; j < numColumna; j++) {
                    Cell celda = fila.createCell(j);
                    if(i==-1){
                        celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
                    }else{
                        celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            respuesta="Exportación exitosa.";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return respuesta;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConsultaProductos;
    // End of variables declaration//GEN-END:variables
}