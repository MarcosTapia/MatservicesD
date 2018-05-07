package ComponenteConsulta;

import beans.ProductoBean;
import beans.CategoriaBean;
import beans.DatosEmpresaBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import util.Util;
import vistas.FrmProducto;
import vistas.FrmUsuarios;
import vistas.Ingreso;
import vistas.Principal;

public class JDListaAlertas extends javax.swing.JDialog {
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

    public JDListaAlertas(java.awt.Frame parent, boolean modal
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
        String titulos[] = {"ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO", "EXIST.", "EXIST. MIN","SUCURSAL", "CATEGORÍA", "PROVEEDOR"};
        LProducto.setColumnIdentifiers(titulos);
        for (ProductoBean p : resultWSArray) {
            String sucursal = util.buscaDescFromIdSuc(sucursalesHMCons, "" + p.getIdSucursal());
            String categoria = util.buscaDescFromIdCat(categoriasHMCons, "" + p.getIdCategoria());
            String proveedor = util.buscaDescFromIdProv(proveedoresHMCons, "" + p.getIdProveedor());
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) 
                    || (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes.getProperty("SUPERUSUARIO"))) 
                    ) {
                if ((p.getExistencia() <= p.getExistenciaMinima())) {
                    String Datos[] = {""+p.getIdArticulo()
                            , p.getCodigo()
                            , p.getDescripcion()
                            , "" + p.getPrecioCosto()
                            , "" + p.getPrecioUnitario()
                            , "" + p.getExistencia()
                            , "" + p.getExistenciaMinima()
                            , sucursal
                            , categoria
                            , proveedor};
                    LProducto.addRow(Datos);
                }
            }
        }
        
        initComponents();
        //btnImprimir.setVisible(false);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DE PRODUCTOS POR SURTIR");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(53, 53, 53))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            tipo.setDescripcion(String.valueOf(tblConsultaProductos.getValueAt(fila, 2)));
            tipo.setPrecioCosto(Double.parseDouble(String.valueOf(tblConsultaProductos.getValueAt(fila, 3))));
            tipo.setExistencia(Double.parseDouble(String.valueOf(tblConsultaProductos.getValueAt(fila, 5))));
            //tipo.setIdSucursal(Integer.parseInt(String.valueOf(tblConsultaProductos.getValueAt(fila, 7))));
            
            //lo uso para sucursal para mostrarlo como string
            tipo.setObservaciones(String.valueOf(tblConsultaProductos.getValueAt(fila, 7)));
            Resultados.add(tipo);
            //util.buscaDescFromIdSuc(sucursalesHMCons, "" + p.getIdSucursal()
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
            jPrint = JasperFillManager.fillReport(this.getClass().
                    getClassLoader().getResourceAsStream
                    ("ComponenteReportes/reporteAlertas.jasper")
                    , map, new JRBeanCollectionDataSource(Resultados));
            JRViewer jv = new JRViewer(jPrint);
            reporte.getContentPane().add(jv);
            reporte.setVisible(true);
            reporte.requestFocus();
        } catch (JRException ex) {
            Logger.getLogger(JDListaAlertas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmProducto frmProducto = new FrmProducto(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConsultaProductos;
    // End of variables declaration//GEN-END:variables
}