package vistas;

import java.awt.Toolkit;
import java.text.DateFormat;
import util.Util;

public class FrmOperaciones extends javax.swing.JFrame {
    String permisos = "";
    Util util = new Util();
    
    public FrmOperaciones() {
        initComponents();
        java.util.Date fecha = util.obtieneFechaServidor();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        lblFecha.setText("Fecha: " + a);
        lblUsuario.setText("Usuario: " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        //verifica permisos de usuario
        permisos = Ingreso.usuario.getClase();
        //verifica permiso de modulo ventas
        if (permisos.charAt(13)=='0') {
            btnVentas.setVisible(false);
        }
        //fin verifica permiso de modulo ventas
        //verifica permiso de modulo compras
        if (permisos.charAt(14)=='0') {
            btnCompras.setVisible(false);
        }
        //fin verifica permiso de modulo compras
        //verifica permiso de modulo caja
        if (permisos.charAt(15)=='0') {
            btnCaja.setVisible(false);
        }
        //fin verifica permiso de modulo caja
        //Fin verifica permisos de usuario
        this.setIcon();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("..\\img\\matserviceslogo.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnVentas = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnCaja = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblTitulo1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control Farmacia Lux V2");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jToolBar1.setRollover(true);
        jToolBar1.setAutoscrolls(true);

        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/VENTASS.png"))); // NOI18N
        btnVentas.setText("VENTAS");
        btnVentas.setToolTipText("");
        btnVentas.setFocusable(false);
        btnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVentas);

        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pedidos.png"))); // NOI18N
        btnCompras.setText("COMPRAS");
        btnCompras.setFocusable(false);
        btnCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompras);

        btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cajachica2.png"))); // NOI18N
        btnCaja.setText("CAJA");
        btnCaja.setFocusable(false);
        btnCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCaja);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        jButton5.setText("INICIO");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir3.png"))); // NOI18N
        jButton6.setText("SALIR");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        getContentPane().add(jToolBar1);
        jToolBar1.setBounds(0, 0, 1580, 60);

        jPanel1.setBackground(new java.awt.Color(247, 254, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(70, 99, 138)));
        jPanel1.setAutoscrolls(true);
        jPanel1.setMinimumSize(new java.awt.Dimension(1680, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 439));

        lblTitulo.setFont(new java.awt.Font("Arial Black", 2, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("jLabel1");

        lblFecha.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblFecha.setText("jLabel1");

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblUsuario.setText("jLabel1");

        lblTitulo1.setBackground(new java.awt.Color(247, 254, 255));
        lblTitulo1.setFont(new java.awt.Font("Arial Black", 2, 48)); // NOI18N
        lblTitulo1.setForeground(new java.awt.Color(70, 99, 138));
        lblTitulo1.setText("jLabel1");

        jLabel2.setBackground(new java.awt.Color(247, 254, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 42)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(70, 99, 138));
        jLabel2.setText("MÓDULO DE OPERACIONES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jLabel2)
                .addGap(98, 98, 98)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(471, 471, 471)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitulo1)
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(125, 125, 125)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 60, 1680, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inicio() {
        this.dispose();
        Principal principal = new Principal();
        principal.setExtendedState(Principal.MAXIMIZED_BOTH);
        principal.setVisible(true);        
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        inicio();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        lblTitulo1.setText(Principal.datosEmpresaBean.getNombreEmpresa());
    }//GEN-LAST:event_formWindowOpened

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        FrmVenta frmVventa = new FrmVenta();
        frmVventa.setVisible(true);
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        FrmCompras frmCompra = new FrmCompras();
        frmCompra.setVisible(true);
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaActionPerformed
        FrmCajaChica frmCajaChica = new FrmCajaChica();
        frmCajaChica.setVisible(true);
    }//GEN-LAST:event_btnCajaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmOperaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmOperaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmOperaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmOperaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmOperaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaja;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
