package vistas;

import ComponenteDatos.ConfiguracionDAO;
import beans.DatosEmpresaBean;
import beans.UsuarioBean;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Operaciones extends javax.swing.JFrame {
    String permisos = "";

    public Operaciones() {
        initComponents();
        java.util.Date fecha = new Date();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        lblFecha.setText("Fecha: " + a);
        lblUsuario.setText("Usuario: " + Ingreso.usuario.getNombre());
        //verifica permisos de usuario
        permisos = Ingreso.usuario.getClase();

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
        
        if (permisos.charAt(4)=='0') {
            btnProveedores.setVisible(false);
        }
        if (permisos.charAt(5)=='0') {
            btnUsuarios.setVisible(false);
        }
        if (permisos.charAt(6)=='0') {
            btnClientes.setVisible(false);
        }
        if (permisos.charAt(7)=='0') {
            btnCategorias.setVisible(false);
        }
        if (permisos.charAt(0)=='0') {
            btnCodigoBarras.setVisible(false);
        }
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
        btnProveedores = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnCategorias = new javax.swing.JButton();
        btnCodigoBarras = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

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

        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedores.png"))); // NOI18N
        btnProveedores.setText("Proveedores");
        btnProveedores.setToolTipText("");
        btnProveedores.setFocusable(false);
        btnProveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProveedores);

        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empleados.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUsuarios);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setFocusable(false);
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClientes);

        btnCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/report2.png"))); // NOI18N
        btnCategorias.setText("Categorías");
        btnCategorias.setFocusable(false);
        btnCategorias.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCategorias.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCategorias);

        btnCodigoBarras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cod_barras.png"))); // NOI18N
        btnCodigoBarras.setText("Gen. Cód. Barras");
        btnCodigoBarras.setFocusable(false);
        btnCodigoBarras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCodigoBarras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCodigoBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoBarrasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCodigoBarras);

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
        jToolBar1.add(jButton5);

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
        jToolBar1.add(jButton6);

        getContentPane().add(jToolBar1);
        jToolBar1.setBounds(0, 0, 1580, 60);

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));
        jPanel1.setLayout(null);

        lblTitulo.setFont(new java.awt.Font("Arial Black", 2, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("jLabel1");
        jPanel1.add(lblTitulo);
        lblTitulo.setBounds(170, 190, 1030, 80);

        jLabel2.setBackground(new java.awt.Color(0, 102, 204));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 42)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("MÓDULO DE CONFIGURACIÓN");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(170, 350, 780, 50);

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblUsuario.setText("jLabel1");
        jPanel1.add(lblUsuario);
        lblUsuario.setBounds(400, 110, 590, 30);

        lblFecha.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblFecha.setText("jLabel1");
        jPanel1.add(lblFecha);
        lblFecha.setBounds(380, 520, 600, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-50, -20, 1620, 1210);

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

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
//        this.dispose();
        FrmProveedor frmProv = new FrmProveedor();
        frmProv.setVisible(true);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        FrmCliente frmCli = new FrmCliente();
        frmCli.setVisible(true);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriasActionPerformed
        FrmCategoria frmCat = new FrmCategoria();
        frmCat.setVisible(true);
    }//GEN-LAST:event_btnCategoriasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        FrmUsuarios frmU = new FrmUsuarios();
        frmU.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnCodigoBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoBarrasActionPerformed
        FrmCodBarras frmCodBarras = new FrmCodBarras();
        frmCodBarras.setVisible(true);
    }//GEN-LAST:event_btnCodigoBarrasActionPerformed

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
            java.util.logging.Logger.getLogger(Operaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Operaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Operaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Operaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Operaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategorias;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCodigoBarras;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
