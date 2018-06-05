package vistas;

import beans.UsuarioBean;
import ComponenteConsulta.JDListaUsuario;
import beans.CajaChicaBean;
import beans.ComprasBean;
import beans.CorteCajaBean;
import beans.DatosEmpresaBean;
import beans.SucursalBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCajaChicaList;
import consumewebservices.WSComprasList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSSucursalesList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import consumewebservices.WSVentasList;
import java.awt.Toolkit;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static vistas.Ingreso.usuario;

import java.security.MessageDigest;
import java.util.Date;
import util.Util;
import static vistas.Principal.productos;


public class FrmCorte extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    WSVentasList hiloVentasList;
    WSComprasList hiloComprasList;
    WSCajaChicaList hiloCajaChicaList;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    CorteCajaBean corteCaja = null;
    ArrayList<CorteCajaBean> corteCajaHoy = new ArrayList<>();
    Date fechaServidor = null;

    String accion = "";

    public FrmCorte() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        jCalFechaIni.setVisible(false);
        jCalFechaFin.setVisible(false);
        fechaServidor = util.obtieneFechaServidor();

        cargaVentasHoy();
        cargaComprasHoy();
        cargaMovsCajaChicaHoy();
        recargarTable(corteCajaHoy);
        txtTotal.setText("" + String.format("%.2f", obtieneTotal()));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnGuardarPer = new javax.swing.JButton();
        btnSalirPer = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel2.setText("CORTE DE CAJA :");

        btnGuardarPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarPer.setText("PROCESAR CORTE");
        btnGuardarPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPerActionPerformed(evt);
            }
        });

        btnSalirPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirPer.setText("SALIR");
        btnSalirPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPerActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Nombre"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("MOVIMIENTOS :");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("TOTAL :");

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<HTML><BODY>GRACIAS POR TU COLABORACIÓN. <P>QUE TENGAS BUEN DÍA.</BODY></HTML>");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addGap(118, 118, 118)
                        .addComponent(lblUsuario))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(608, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalirPer, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarPer))))
                .addGap(54, 54, 54))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(366, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lblUsuario))
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnGuardarPer, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalirPer, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCalFechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(98, 98, 98)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(19, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPerActionPerformed
        //Carga productos
        Principal principal = new Principal();
        principal.cargaUsuarios();
        this.dispose();
        FrmConfiguracion operaciones = new FrmConfiguracion();
    }//GEN-LAST:event_btnSalirPerActionPerformed

    private void cargaVentasHoy() {
        hiloVentasList = new WSVentasList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        // Actualizas tbl Ventas
        ArrayList<VentasBean> ventasPorFechas = null;
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETVENTASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETVENTASPORFECHASFFIN") + fechaFin;
        ventasPorFechas = hiloVentasList.ejecutaWebService(rutaWS,"2");
        
        //iguala beans venta y cortecaja
        for (VentasBean vta : ventasPorFechas) {
            corteCaja = new CorteCajaBean();
            corteCaja.setIdMov(vta.getIdVenta());
            corteCaja.setFecha(vta.getFecha());
            corteCaja.setIdUsuario(vta.getIdUsuario());
            corteCaja.setIdSucursal(vta.getIdSucursal());
            corteCaja.setTotal(vta.getTotal());
            corteCaja.setTipoMov("VENTA " + vta.getTipovta());
            corteCajaHoy.add(corteCaja);
        }
    }
    
    private double obtieneTotal(){
        double total = 0;
        for (int i=0;i<tblUsuarios.getRowCount();i++) {
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("VENTA NORMAL")) {
                total = total + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("COMPRA NORMAL")) {
                total = total - Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("INGRESO")) {
                total = total + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("GASTO")) {
                total = total - Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            //total = total + tblUsuarios
        }
        return total;
    }
    
    private void cargaComprasHoy() {
        hiloVentasList = new WSVentasList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        ArrayList<ComprasBean> comprasPorFechas = null;
        hiloComprasList = new WSComprasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCOMPRASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETCOMPRASPORFECHASFFIN") + fechaFin;
        comprasPorFechas = hiloComprasList.ejecutaWebService(rutaWS,"2");
        //iguala beans venta y cortecaja
        for (ComprasBean compra : comprasPorFechas) {
            corteCaja = new CorteCajaBean();
            corteCaja.setIdMov(compra.getIdCompra());
            corteCaja.setFecha(compra.getFecha());
            corteCaja.setIdUsuario(compra.getIdUsuario());
            corteCaja.setIdSucursal(compra.getIdSucursal());
            corteCaja.setTotal(compra.getTotal());
            corteCaja.setTipoMov("COMPRA " + compra.getTipocompra());
            corteCajaHoy.add(corteCaja);
        }
    }
    
    private void cargaMovsCajaChicaHoy() {
        hiloCajaChicaList = new WSCajaChicaList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        ArrayList<CajaChicaBean> movsCajaChicaPorFechas = null;
        hiloCajaChicaList = new WSCajaChicaList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETMOVSCAJACHICAPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETMOVSCAJACHICAPORFECHASFFIN") + fechaFin;
        movsCajaChicaPorFechas = hiloCajaChicaList.ejecutaWebService(rutaWS,"4");
        //iguala beans venta y cortecaja
        for (CajaChicaBean movCajaChica : movsCajaChicaPorFechas) {
            corteCaja = new CorteCajaBean();
            corteCaja.setIdMov(movCajaChica.getIdMov());
            corteCaja.setFecha(movCajaChica.getFecha());
            corteCaja.setIdUsuario(movCajaChica.getIdUsuario());
            corteCaja.setIdSucursal(movCajaChica.getIdSucursal());
            corteCaja.setTotal(movCajaChica.getMonto());
            corteCaja.setTipoMov(movCajaChica.getTipoMov());
            corteCajaHoy.add(corteCaja);
        }
    }
    
    private void btnGuardarPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPerActionPerformed
        for(CorteCajaBean corteCajaBean : corteCajaHoy) {
            JOptionPane.showMessageDialog(null, "" + corteCajaBean.getTotal());
        }
    }//GEN-LAST:event_btnGuardarPerActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
    }//GEN-LAST:event_tblUsuariosMouseClicked

    public void recargarTable(ArrayList<CorteCajaBean> list) {
        Object[][] datos = new Object[list.size()][6];
        int i = 0;
        for (CorteCajaBean p : list) {
            datos[i][0] = p.getIdMov();
            datos[i][1] = p.getFecha();
            datos[i][2] = p.getIdUsuario();
            datos[i][3] = p.getIdSucursal();
            datos[i][4] = p.getTotal();
            datos[i][5] = p.getTipoMov();
            i++;
        }
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID MOV", "FECHA", "USUARIO", "SUCURSAL", "TOTAL", "TIPO MOV"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmCorte().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarPer;
    private javax.swing.JButton btnSalirPer;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}