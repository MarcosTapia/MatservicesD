package vistas;

import beans.DatosEmpresaBean;
import beans.MensajeBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSMensajes;
import consumewebservices.WSMensajesList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JDialog;
import util.Util;

public class FrmConfiguraMensajes extends javax.swing.JFrame {
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    WSMensajes hiloMensajes;
    WSMensajesList hiloMensajesList;
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    String accion = "";
    ArrayList<MensajeBean> mensajesGlobal = null;

    public FrmConfiguraMensajes() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        jCalFechaFin.setVisible(false);
        jCalFechaIni.setDate(util.obtieneFechaServidor());
        jCalFechaIni.setEnabled(false);
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno()
        );
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        
        // Actualizas tbl Mensajes
        hiloMensajesList = new WSMensajesList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETMENSAJES");
        mensajesGlobal = hiloMensajesList.ejecutaWebService(rutaWS,"1");
        
        //se ocultan porque quedan incluidas en inventario
        actualizarBusqueda();
        btnGuardarMensaje.setEnabled(false);
//        activarBotones(true);
        this.setIcon();
        this.setLocationRelativeTo(null);
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    public void activarBotones(boolean b) {
        btnNuevoMensaje.setEnabled(b);
        btnGuardarMensaje.setEnabled(!b);
        txtAreaMensaje.setEditable(true);
        txtAreaMensaje.requestFocus(true);
        jCalFechaIni.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarMsg = new javax.swing.JTextField();
        cboParametroUsuario = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMensajes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnNuevoMensaje = new javax.swing.JButton();
        btnGuardarMensaje = new javax.swing.JButton();
        btnEliminarMensaje = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaMensaje = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        btnSalir = new javax.swing.JButton();
        lblIdMensaje = new javax.swing.JLabel();
        btnCancelarMensaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setText("BUSCAR MENSAJE :");

        txtBuscarMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMsgKeyReleased(evt);
            }
        });

        cboParametroUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Mensaje" }));
        cboParametroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroUsuarioActionPerformed(evt);
            }
        });

        tblMensajes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMensajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMensajesMouseClicked(evt);
            }
        });
        tblMensajes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblMensajesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblMensajes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtBuscarMsg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboParametroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        btnNuevoMensaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document.png"))); // NOI18N
        btnNuevoMensaje.setText("NUEVO");
        btnNuevoMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoMensaje.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMensajeActionPerformed(evt);
            }
        });

        btnGuardarMensaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarMensaje.setText("GUARDAR");
        btnGuardarMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarMensaje.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMensajeActionPerformed(evt);
            }
        });

        btnEliminarMensaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarMensaje.setText("ELIMINAR");
        btnEliminarMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarMensaje.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMensajeActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        txtAreaMensaje.setEditable(false);
        txtAreaMensaje.setColumns(20);
        txtAreaMensaje.setRows(5);
        jScrollPane2.setViewportView(txtAreaMensaje);

        jLabel3.setText("Mensaje :");

        jLabel8.setText("Fecha :");

        jCalFechaIni.setDateFormatString("dd/MM/yyyy");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.jpg"))); // NOI18N
        btnSalir.setText("REGRESAR");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnCancelarMensaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarMensaje.setText("CANCELAR");
        btnCancelarMensaje.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarMensaje.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMensajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnNuevoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarMensaje)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lblIdMensaje))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnCancelarMensaje)
                                        .addGap(7, 7, 7)
                                        .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(lblIdMensaje)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(btnGuardarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                            .addComponent(btnEliminarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 62, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMensajeActionPerformed
        accion = "Guardar";
        activarBotones(false);
        limpiarCajaTexto();
    }//GEN-LAST:event_btnNuevoMensajeActionPerformed

    private void guardar() {
        if (accion.equalsIgnoreCase("Guardar")) {
            if ((txtAreaMensaje.getText().compareTo("") != 0)
                && (jCalFechaIni.getDate() != null)
            ) {
                MensajeBean p = new MensajeBean();
                p.setMensaje(txtAreaMensaje.getText());
                p.setFecha(jCalFechaIni.getDate());
                //guardar mensaje
                hiloMensajes = new WSMensajes();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("GUARDAMENSAJE");
                MensajeBean mensajeInsertado = hiloMensajes
                        .ejecutaWebService(rutaWS,"1"
                        ,p.getMensaje()
                        ,p.getFecha().toLocaleString()
                );
                if (mensajeInsertado != null) {
                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                    activarBotones(true);
                    limpiarCajaTexto();
                    
                    // Actualizas tbl Mensajes
                    hiloMensajesList = new WSMensajesList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETMENSAJES");
                    mensajesGlobal = hiloMensajesList
                            .ejecutaWebService(rutaWS,"1");                    
                    actualizarBusqueda();
                    txtAreaMensaje.setEditable(false);
                    jCalFechaIni.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");        
            }
        }
    }
    
    private void limpiarCajaTexto() {
        lblIdMensaje.setText("");
        txtAreaMensaje.setText("");
    }    
    
    private void btnGuardarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMensajeActionPerformed
        guardar();
        jCalFechaIni.setDate(util.obtieneFechaServidor());
    }//GEN-LAST:event_btnGuardarMensajeActionPerformed

    private void cboParametroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroUsuarioActionPerformed
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroUsuarioActionPerformed

    private void txtBuscarMsgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMsgKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarMsgKeyReleased

    private void buscaMensajeFromJTable() {
        lblIdMensaje.setText(tblMensajes.getModel().getValueAt(
            tblMensajes.getSelectedRow(),0).toString());
        ArrayList<MensajeBean> resultWS = null;
        hiloMensajesList = new WSMensajesList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETMENSAJESPORID")
                + String.valueOf(tblMensajes.getModel().getValueAt
                (tblMensajes.getSelectedRow(), 0)).trim();
        resultWS = hiloMensajesList.ejecutaWebService(rutaWS,"3");
        MensajeBean msg = resultWS.get(0);
        txtAreaMensaje.setText(msg.getMensaje());
        jCalFechaIni.setDate(null);
        jCalFechaIni.setDate(msg.getFecha());        
        tblMensajes.requestFocus(true);
    }
    
    private void tblMensajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMensajesMouseClicked
        buscaMensajeFromJTable();
    }//GEN-LAST:event_tblMensajesMouseClicked

    private void eliminarMensaje() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente "
                + "deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (lblIdMensaje.getText().compareTo("") != 0) {
                hiloMensajes = new WSMensajes();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("ELIMINAMENSAJE");
                MensajeBean mensajeEliminar = hiloMensajes
                        .ejecutaWebService(rutaWS,"2"
                        ,lblIdMensaje.getText().trim());
                if (mensajeEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro "
                            + "Eliminado ]");
                    limpiarCajaTexto();
                    // Actualizas tbl Mensajes
                    hiloMensajesList = new WSMensajesList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETMENSAJES");
                    mensajesGlobal = hiloMensajesList
                            .ejecutaWebService(rutaWS,"1");                    
                    actualizarBusqueda();
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible "
                            + "eliminar el "
                            + "mensaje existen movimientos que lo relacionan"
                            , JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay mensaje para eliminar");
            }
        }
    }
    
    private void btnEliminarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMensajeActionPerformed
        eliminarMensaje();
    }//GEN-LAST:event_btnEliminarMensajeActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblMensajesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMensajesKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaMensajeFromJTable();
        }
    }//GEN-LAST:event_tblMensajesKeyReleased

    private void btnCancelarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMensajeActionPerformed
        limpiarCajaTexto();
        actualizarBusqueda();
        txtAreaMensaje.setEnabled(false);
        jCalFechaIni.setEnabled(false);
        btnNuevoMensaje.setEnabled(true);
        btnGuardarMensaje.setEnabled(false);
        jCalFechaIni.setDate(util.obtieneFechaServidor());
    }//GEN-LAST:event_btnCancelarMensajeActionPerformed

    private void actualizarBusqueda() {
        ArrayList<MensajeBean> resultWS = null;
        if (String.valueOf(cboParametroUsuario.getSelectedItem()).
                equalsIgnoreCase("Id")) {
            if (txtBuscarMsg.getText().equalsIgnoreCase("")) {
                resultWS = mensajesGlobal;
            } else {
                resultWS = llenaTablaMovs(
                        txtBuscarMsg.getText().trim().toLowerCase(),0);
            }
        }
        //Monto
        if (String.valueOf(cboParametroUsuario.getSelectedItem()).
                equalsIgnoreCase("Mensaje")) {
            if (txtBuscarMsg.getText().equalsIgnoreCase("")) {
                resultWS = mensajesGlobal;
            } else {
                resultWS = llenaTablaMovs(
                        txtBuscarMsg.getText().trim().toLowerCase(),1);
            }
        } 
        recargarTable(resultWS);
        //IdMov,Monto, TipoMov, Comprobante, Referencia, Usuario, Sucursal
    }
    
    private ArrayList<MensajeBean> llenaTablaMovs(String buscar, int tipoBusq) {
        ArrayList<MensajeBean> resultWS = new ArrayList<MensajeBean>();
        MensajeBean mensaje = null;
        for (int i=0; i<tblMensajes.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblMensajes.getModel().getValueAt(
                    i,0).toString();
                    campoBusq = campoBusq.toLowerCase();
                    break;
                case 1 : campoBusq = tblMensajes.getModel().getValueAt(
                    i,1).toString();
                    campoBusq = campoBusq.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                mensaje = new MensajeBean();
                mensaje.setIdMensaje(Integer.parseInt(tblMensajes.getModel()
                        .getValueAt(i,0).toString()));
                mensaje.setMensaje(String.valueOf(tblMensajes.getModel()
                        .getValueAt(i,1)));
                String fecha = String.valueOf(tblMensajes.getModel()
                        .getValueAt(i,1));
                mensaje.setFecha(util.stringToDate(String.valueOf(tblMensajes
                        .getModel().getValueAt(i,2))));
                resultWS.add(mensaje);
            }
        }
        return resultWS;
    }


    public void recargarTable(ArrayList<MensajeBean> list) {
        Object[][] datos = new Object[list.size()][3];
        int i = 0;
        for (MensajeBean p : list) {
            datos[i][0] = p.getIdMensaje();
            datos[i][1] = p.getMensaje();
            datos[i][2] = p.getFecha();
            i++;
        }
        tblMensajes.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID MENSAJE", "MENSAJE", "FECHA"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
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
            java.util.logging.Logger.getLogger(FrmConfiguraMensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmConfiguraMensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmConfiguraMensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmConfiguraMensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmConfiguraMensajes().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarMensaje;
    private javax.swing.JButton btnEliminarMensaje;
    private javax.swing.JButton btnGuardarMensaje;
    private javax.swing.JButton btnNuevoMensaje;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboParametroUsuario;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIdMensaje;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblMensajes;
    private javax.swing.JTextArea txtAreaMensaje;
    private javax.swing.JTextField txtBuscarMsg;
    // End of variables declaration//GEN-END:variables
}