package vistas;

import ComponenteDatos.ConfiguracionDAO;
import beans.DatosEmpresaBean;
import beans.UsuarioBean;
import java.text.DateFormat;
import java.util.Date;

public class Inventario extends javax.swing.JFrame {
    String permisos = "";
    
    public Inventario() {
        initComponents();
        java.util.Date fecha = new Date();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        lblFecha.setText("Fecha: " + a);
        lblUsuario.setText("Usuario: " + Ingreso.usuario.getNombre());
        //verifica permisos de usuario
        permisos = Ingreso.usuario.getClase();
        if (permisos.charAt(0)=='0') {
            btnProductos.setVisible(false);
        }
        if (permisos.charAt(3)=='0') {
            btnConsultaVentas.setVisible(false);
        }
        
            //equivalencias de sistemas
            //Inventario pos 0  --> inventario->productos pos 0, 
            //            operaciones->alertas pos 0,
            //            configuracion->codigo de barras pos 0
            //Ventas pos 1   --> operaciones->ventas pos 1
            //Compras pos 2 --> operaciones->compras pos 2
            //Consultas pos 3 --> inventario->consultaventas pos 3
            //Proveedores pos 4 --> configuracion->proveedores pos 4
            //Clientes pos 5 --> configuracion->clientes pos 5
            //Empleados pos 6 --> configuracion->usuarios pos 6
            //Configuración pos 7 --> configuracion->categorias pos 7
        
        
        
        //Fin verifica permisos de usuario
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        btnProductos = new javax.swing.JButton();
        btnConsultaVentas = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control Farmacia Lux V2");
        setBackground(new java.awt.Color(51, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));
        jPanel1.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Arial Black", 2, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("jLabel1");

        jLabel2.setBackground(new java.awt.Color(0, 102, 204));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 42)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("MÓDULO DE INVENTARIO");

        lblFecha.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblFecha.setText("jLabel1");

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblUsuario.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblTitulo))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblUsuario)
                .addGap(11, 11, 11)
                .addComponent(lblTitulo)
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addGap(150, 150, 150)
                .addComponent(lblFecha))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 60, 1680, 800);

        jToolBar2.setRollover(true);

        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        btnProductos.setText("Inventarios");
        btnProductos.setToolTipText("");
        btnProductos.setFocusable(false);
        btnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jToolBar2.add(btnProductos);

        btnConsultaVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search.png"))); // NOI18N
        btnConsultaVentas.setText("Consulta Ventas");
        btnConsultaVentas.setFocusable(false);
        btnConsultaVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsultaVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConsultaVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaVentasActionPerformed(evt);
            }
        });
        jToolBar2.add(btnConsultaVentas);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        jButton5.setText("Inicio");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir3.png"))); // NOI18N
        jButton6.setText("Salir");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        getContentPane().add(jToolBar2);
        jToolBar2.setBounds(3, 0, 360, 60);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inicio() {
        this.setVisible(false);
        Principal principal = new Principal();
        principal.setExtendedState(Principal.MAXIMIZED_BOTH);
        principal.setVisible(true);        
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        inicio();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        lblTitulo.setText(Principal.datosEmpresaBean.getNombreEmpresa());
    }//GEN-LAST:event_formWindowOpened

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        FrmProducto frmP = new FrmProducto();
        frmP.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnConsultaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaVentasActionPerformed
//        this.dispose();
        FrmConsultaVentas frmConsultaVentas = new FrmConsultaVentas();
        frmConsultaVentas.setVisible(true);   
    }//GEN-LAST:event_btnConsultaVentasActionPerformed

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
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultaVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
