package vistas;

import beans.SucursalBean;
import beans.UsuarioBean;
import com.sun.awt.AWTUtilities;
import constantes.ConstantesProperties;
import consumewebservices.WSSucursalesList;
import consumewebservices.WSUsuarios;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
//import static vistas.Ingresoa.usuario;

public class FrmMensaje extends javax.swing.JFrame {
    Properties constantes = new ConstantesProperties().getProperties();
    //WS
    WSUsuarios hiloUsuarios;
//    WSSucursalesList hiloSucursalesList;
//    static Map<String,String> sucursalesHM = new HashMap();
    //Fin WS
    
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public static UsuarioBean usuario;

    int x,y;
    
    public FrmMensaje() {
        initComponents();
        this.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(this, false);
        
//        //Checa si existe archivo licencia
//        File f = new File("C:\\Windows\\addins\\w4mdp.ecf");
//        if (!(f.exists())) { 
//            JOptionPane.showMessageDialog(null, "Copia Ilegal");
//            System.exit(1);
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        lblContenido = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(525, 535));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        btnBorrar.setFont(new java.awt.Font("Mode G", 0, 16)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_grande.png"))); // NOI18N
        btnBorrar.setText("Continuar");
        btnBorrar.setBorder(null);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_grande_hov.png"))); // NOI18N
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrar);
        btnBorrar.setBounds(190, 390, 133, 33);

        lblMensaje.setFont(new java.awt.Font("Mode G", 0, 36)); // NOI18N
        lblMensaje.setForeground(new java.awt.Color(255, 255, 255));
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje.setText("Mensaje(s) :");
        lblMensaje.setToolTipText("");
        lblMensaje.setAutoscrolls(true);
        lblMensaje.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        lblMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblMensaje.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblMensaje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMensajeMousePressed(evt);
            }
        });
        lblMensaje.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblMensajeMouseDragged(evt);
            }
        });
        jPanel1.add(lblMensaje);
        lblMensaje.setBounds(80, 100, 330, 50);

        lblContenido.setFont(new java.awt.Font("Mode G", 0, 14)); // NOI18N
        lblContenido.setForeground(new java.awt.Color(255, 255, 255));
        lblContenido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(lblContenido);
        lblContenido.setBounds(40, 160, 420, 170);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/500px-WET-ASPHALT.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 500, 500);

        jLabel2.setFont(new java.awt.Font("Mode G", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Inicio de sesión");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jPanel1.add(jLabel2);
        jLabel2.setBounds(80, 90, 340, 50);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //ParaWS
    public void consultaUsuariosWS() {
        hiloUsuarios = new WSUsuarios();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETUSUARIOS");
        UsuarioBean resultadoWS = hiloUsuarios.ejecutaWebService(rutaWS,"1");
    }

    public void verificaUsuarioWS() {
//        hiloUsuarios = new WSUsuarios();
//        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("VERIFICA_USUARIOUSER") + txtUser.getText() +
//                constantes.getProperty("VERIFICA_USUARIOCVE") + txtPassword.getText();
//        //String resultadoWS = hiloUsuarios.ejecutaWebService(rutaWS,"2");
//        usuario = hiloUsuarios.verificaUsuarioWS(rutaWS);
////        if (!"".equalsIgnoreCase(resultadoWS)) {
//        if (usuario != null) {
//            this.dispose();
//            vistas.Principal principal = new vistas.Principal();
//            principal.setExtendedState(principal.MAXIMIZED_BOTH);
//            principal.setDefaultCloseOperation(principal.EXIT_ON_CLOSE);
//            principal.setVisible(true);    
//        } else {
//            JOptionPane.showMessageDialog(null, "ERROR: Usuario o clave erróneos");            
//            borrar();
//        }
    }
    //Fin para WS
    
    
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        this.dispose();
        vistas.Principal principal = new vistas.Principal();
        principal.setExtendedState(principal.MAXIMIZED_BOTH);
        principal.setDefaultCloseOperation(principal.EXIT_ON_CLOSE);
        principal.setVisible(true);    
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        lblContenido.setText(mensaje);
    }//GEN-LAST:event_formWindowOpened

    private void lblMensajeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMensajeMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMensajeMousePressed

    private void lblMensajeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMensajeMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_lblMensajeMouseDragged

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel2MouseDragged

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
            java.util.logging.Logger.getLogger(FrmMensaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMensaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMensaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMensaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                new FrmMensaje().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblContenido;
    private javax.swing.JLabel lblMensaje;
    // End of variables declaration//GEN-END:variables
}
