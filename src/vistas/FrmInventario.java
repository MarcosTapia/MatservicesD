package vistas;

import java.text.DateFormat;
import util.Util;
import javax.swing.ImageIcon;

public class FrmInventario extends javax.swing.JFrame {

    String permisos = "";
    Util util = new Util();

    public FrmInventario() {
        initComponents();

        java.util.Date fecha = util.obtieneFechaServidor();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);
        lblFecha.setText("Fecha: " + a);
        lblUsuario.setText("Usuario: " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());

        //verifica permisos de usuario
        permisos = Ingreso.usuario.getClase();
        //verifica permiso de inventario
        if (permisos.charAt(0) == '0') {
            btnProductos.setVisible(false);
        }
        //fin verifica permiso de inventario
        //Fin verifica permisos de usuario

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
        setBackground(new java.awt.Color(51, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(247, 254, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(70, 99, 138)));
        jPanel1.setAutoscrolls(true);

        lblTitulo.setBackground(new java.awt.Color(247, 254, 255));
        lblTitulo.setFont(new java.awt.Font("Arial Black", 2, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(70, 99, 138));
        lblTitulo.setText("jLabel1");

        jLabel2.setBackground(new java.awt.Color(247, 254, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 42)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(70, 99, 138));
        jLabel2.setText("MÓDULO DE INVENTARIO");

        lblFecha.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblFecha.setText("jLabel1");

        lblUsuario.setBackground(new java.awt.Color(247, 254, 255));
        lblUsuario.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblUsuario.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(430, 430, 430)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(680, 680, 680))
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
        btnProductos.setText("Inventario");
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
        btnConsultaVentas.setText("Consultas");
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
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        inicio();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        lblTitulo.setText(Principal.datosEmpresaBean.getNombreEmpresa());
    }//GEN-LAST:event_formWindowOpened

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmProducto frmP = new FrmProducto(0);
        frmP.setExtendedState(frmP.MAXIMIZED_BOTH);
        frmP.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnConsultaVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaVentasActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmConsultas frmConsultas = new FrmConsultas();
        frmConsultas.setExtendedState(frmConsultas.MAXIMIZED_BOTH);
        frmConsultas.setVisible(true);
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
            java.util.logging.Logger.getLogger(FrmInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmInventario().setVisible(true);
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
