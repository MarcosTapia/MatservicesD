package vistas;

import beans.MensajeBean;
import beans.UsuarioBean;
import com.sun.awt.AWTUtilities;
import constantes.ConstantesProperties;
import consumewebservices.WSMensajesList;
import consumewebservices.WSUsuarios;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Ingreso extends javax.swing.JFrame {

    Properties constantes = new ConstantesProperties().getProperties();
    //WS
    WSUsuarios hiloUsuarios;
    WSMensajesList hiloMensajesList;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String mensaje = "";

    public static UsuarioBean usuario;

    int x, y;

    public Ingreso() {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnIngreso = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(525, 535));
        setUndecorated(true);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Mode G", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Inicio de sesión");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jPanel1.add(jLabel2);
        jLabel2.setBounds(80, 90, 340, 50);

        jLabel3.setFont(new java.awt.Font("Mode G", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 160, 420, 20);

        txtUser.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUser.setBorder(null);
        txtUser.setOpaque(false);
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        jPanel1.add(txtUser);
        txtUser.setBounds(170, 180, 180, 40);

        txtPassword.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(null);
        txtPassword.setOpaque(false);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword);
        txtPassword.setBounds(171, 260, 180, 30);

        jLabel4.setFont(new java.awt.Font("Mode G", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Contraseña");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 240, 460, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/txt_med.png"))); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(160, 180, 200, 40);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/txt_med.png"))); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(160, 260, 200, 34);

        btnIngreso.setFont(new java.awt.Font("Mode G", 0, 16)); // NOI18N
        btnIngreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_grande.png"))); // NOI18N
        btnIngreso.setText("Iniciar");
        btnIngreso.setBorder(null);
        btnIngreso.setBorderPainted(false);
        btnIngreso.setContentAreaFilled(false);
        btnIngreso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngreso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngreso.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_grande_hov.png"))); // NOI18N
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });
        btnIngreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnIngresoKeyPressed(evt);
            }
        });
        jPanel1.add(btnIngreso);
        btnIngreso.setBounds(190, 330, 133, 33);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/linea.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7);
        jLabel7.setBounds(130, 370, 250, 14);

        btnBorrar.setFont(new java.awt.Font("Mode G", 0, 16)); // NOI18N
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/btn_grande.png"))); // NOI18N
        btnBorrar.setText("Borrar");
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

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar_32px.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8);
        jLabel8.setBounds(420, 220, 33, 33);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/500px-EMERALD.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 500, 500);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //ParaWS
    public void consultaUsuariosWS() {
        hiloUsuarios = new WSUsuarios();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETUSUARIOS");
        UsuarioBean resultadoWS = hiloUsuarios.ejecutaWebService(rutaWS, "1");
    }

    public void obtieneMensajes() {
        String fechaIni;
        String fechaFin;
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = new Date();
        java.util.Date fechaUtilDateFin = new Date();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo "
                    + "menos la fecha de Inicio");
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
        ArrayList<MensajeBean> mensajesPorFechas = null;
        hiloMensajesList = new WSMensajesList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETMENSAJESPORFECHASFINI") + fechaIni
                + constantes.getProperty("GETMENSAJESPORFECHASFFIN") + fechaFin;
        mensajesPorFechas = hiloMensajesList.ejecutaWebService(rutaWS, "2");
        if (mensajesPorFechas.size() > 0) {
            mensaje = "<html><body><ul>";
            for (MensajeBean msg : mensajesPorFechas) {
                mensaje = mensaje + "<li>" + msg.getMensaje() + "</li>";
            }
            mensaje = mensaje + "</ul></body></html>";
        } else {
            mensaje = "";
        }
    }

    public void verificaUsuarioWS() {
        hiloUsuarios = new WSUsuarios();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("VERIFICA_USUARIOUSER") 
                + txtUser.getText()
                + constantes.getProperty("VERIFICA_USUARIOCVE") 
                + txtPassword.getText();
        usuario = hiloUsuarios.verificaUsuarioWS(rutaWS);
        if (usuario != null) {
            this.dispose();
            obtieneMensajes();
            if (mensaje.equalsIgnoreCase("")) { //si no hay mensajes
                vistas.Principal principal = new vistas.Principal();
                principal.setExtendedState(principal.MAXIMIZED_BOTH);
                principal.setDefaultCloseOperation(principal.EXIT_ON_CLOSE);
                principal.setVisible(true);
            } else { //si hay mensajes
                FrmMensaje frmMensaje = new FrmMensaje();
                frmMensaje.setMensaje(mensaje);
                frmMensaje.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERROR: Usuario o clave "
                    + "erróneos");
            borrar();
        }
    }
    //Fin para WS


    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() 
                - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jLabel2MouseDragged

    public void borrar() {
        txtUser.setText(null);
        txtPassword.setText(null);
        txtUser.requestFocus();
    }

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        verificaUsuarioWS();
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
    }//GEN-LAST:event_jLabel7MouseClicked

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        txtPassword.requestFocus();
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        btnIngreso.requestFocus();
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnIngresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresoKeyPressed
        verificaUsuarioWS();
    }//GEN-LAST:event_btnIngresoKeyPressed

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
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                new Ingreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
