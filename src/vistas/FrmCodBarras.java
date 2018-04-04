package vistas;

import ComponenteConsulta.JDListaCategorias;
import ComponenteDatos.ConfiguracionDAO;
import beans.DatosEmpresaBean;
import beans.ProductoBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSSistema;
//import com.lowagie.text.pdf.Barcode;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import util.Util;
import static vistas.Principal.datosEmpresaBean;
import static vistas.Principal.datosSistemaBean;

public class FrmCodBarras extends javax.swing.JFrame {
    //WS
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSSistema hiloSistema;
    //Fin WS
    //WSUsuarios
    Util util = new Util();
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    //Fin WS
    
    
    

    public FrmCodBarras() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
        
        
        // Carga datos de la empresa
        hiloSistema = new WSSistema();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETDATOSSISTEMA");
        datosSistemaBean = hiloSistema.ejecutaWebService(rutaWS,"1");
        // Fin Carga datos de la empresa
        
        this.setTitle(datosEmpresaBean.getNombreEmpresa());        
        this.setLocationRelativeTo(null);
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnVerCodigoBarras = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnSalirCat = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnMostrarCat = new javax.swing.JButton();
        btnCopiarPortapapeles = new javax.swing.JButton();
        lblcode = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        btnSalirCat1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(70, 99, 138));

        jPanel1.setBackground(new java.awt.Color(247, 254, 255));

        txtcodigo.setFont(new java.awt.Font("Book Antiqua", 1, 20)); // NOI18N
        txtcodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodigoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Codigo :");

        btnVerCodigoBarras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document.png"))); // NOI18N
        btnVerCodigoBarras.setText("VER CÓDIGO");
        btnVerCodigoBarras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerCodigoBarras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerCodigoBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCodigoBarrasActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnSalirCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCat.setText("CERRAR");
        btnSalirCat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCatActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("GENERACIÓN DE CÓDIGO DE BARRAS");

        btnMostrarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnMostrarCat.setText("GUARDAR EN C:\\etiquetas");
        btnMostrarCat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarCat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCatActionPerformed(evt);
            }
        });

        btnCopiarPortapapeles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/portapapeles.jpg"))); // NOI18N
        btnCopiarPortapapeles.setText("COPIAR AL PORTAPAPELES");
        btnCopiarPortapapeles.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiarPortapapeles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCopiarPortapapeles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiarPortapapelesActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        btnSalirCat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnSalirCat1.setText("CANCELAR");
        btnSalirCat1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCat1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCat1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCopiarPortapapeles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMostrarCat)
                .addGap(5, 5, 5)
                .addComponent(btnSalirCat1)
                .addGap(32, 32, 32)
                .addComponent(btnSalirCat, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(lblcode, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(59, 59, 59)
                                .addComponent(lblUsuario)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblUsuario))
                .addGap(8, 8, 8)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblcode, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalirCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarCat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCopiarPortapapeles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVerCodigoBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalirCat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCatActionPerformed
        //Checa si existe archivo licencia
        File f = new File("C:\\etiquetas\\" + txtcodigo.getText() + ".png");
        if (f.exists()) { 
            JOptionPane.showMessageDialog(null, "Ya está creado este código");
            return;
        }        

        net.sourceforge.barbecue.Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode39(txtcodigo.getText(), true);
        } catch (Exception e) { }
        barcode.setDrawingText(false);
        barcode.setBarWidth(2);
        barcode.setBarHeight(60);
        try {
            FileOutputStream fos = new FileOutputStream("C:/Etiquetas/" + txtcodigo.getText() + ".png");
            try {
                BarcodeImageHandler.writePNG(barcode, fos);
            } catch (OutputException ex) {
                Logger.getLogger(FrmCodBarras.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmCodBarras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMostrarCatActionPerformed

    private void verificaCodigoDuplicado() {
        if (txtcodigo.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes asignar un código");
            return;
        }
        //verifica que el codigo no exista
        hiloInventarios = new WSInventarios();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("OBTIENEPRODUCTOPORCODIGO")
                + txtcodigo.getText().trim();
        ProductoBean productoEliminar = hiloInventarios.ejecutaWebService(rutaWS
                ,"4");
        if (productoEliminar != null) {
            util.registroDuplicado("Código");
            return;
        } else {
            btnVerCodigoBarras.requestFocus(true);
        }
        //Fin verifica que el codigo no exista
    }
    
    private void btnVerCodigoBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCodigoBarrasActionPerformed
        verificaCodigoDuplicado();
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode39(txtcodigo.getText(), true);
        } catch (Exception e) { }
        barcode.setDrawingText(false);
        barcode.setBarWidth(2);
        barcode.setBarHeight(60);
        BufferedImage image = new BufferedImage(300, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        try {
            barcode.draw(g, 5, 20);
        } catch (Exception e) { }
        ImageIcon icon = new ImageIcon(image);
        lblcode.setIcon(icon);
    }//GEN-LAST:event_btnVerCodigoBarrasActionPerformed

    private void btnCopiarPortapapelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiarPortapapelesActionPerformed
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        ImageIcon image = (ImageIcon) lblcode.getIcon();
        ImageSelection dh = new ImageSelection(image.getImage());
        cb.setContents(dh, null);
    }//GEN-LAST:event_btnCopiarPortapapelesActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        net.sourceforge.barbecue.Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode39(txtcodigo.getText(), true);
        } catch (Exception e) { }
        barcode.setDrawingText(false);
        barcode.setBarWidth(2);
        barcode.setBarHeight(60);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(barcode);
                 if (job.printDialog()){
                         try {
                              job.print();
                         } catch (PrinterException ex) {
                             Logger.getLogger(FrmCodBarras.class.getName()).log(Level.SEVERE, null, ex);
                             }
                   }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSalirCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCatActionPerformed
        this.dispose();
        Configuracion operaciones = new Configuracion();
    }//GEN-LAST:event_btnSalirCatActionPerformed

    private void txtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoActionPerformed
        verificaCodigoDuplicado();
    }//GEN-LAST:event_txtcodigoActionPerformed

    private void btnSalirCat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCat1ActionPerformed
        lblcode.setIcon(null);
        txtcodigo.setText(null);
        txtcodigo.requestFocus(true);
    }//GEN-LAST:event_btnSalirCat1ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCodBarras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCodBarras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCodBarras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCodBarras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmCodBarras().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopiarPortapapeles;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMostrarCat;
    private javax.swing.JButton btnSalirCat;
    private javax.swing.JButton btnSalirCat1;
    private javax.swing.JButton btnVerCodigoBarras;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblcode;
    private javax.swing.JTextField txtcodigo;
    // End of variables declaration//GEN-END:variables
}
