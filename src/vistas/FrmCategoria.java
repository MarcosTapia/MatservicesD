package vistas;

import ComponenteConsulta.JDListaCategorias;
import beans.CategoriaBean;
import beans.DatosEmpresaBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCategorias;
import consumewebservices.WSCategoriasList;
import consumewebservices.WSDatosEmpresa;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;

public class FrmCategoria extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSCategoriasList hiloCategoriasList;
    WSCategorias hiloCategorias;
    //Fin WSUsuarios
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    String accion = "";
    
    public FrmCategoria() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean configuracionBean = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        actualizarBusqueda();
        activarBotones(true);

        lblUsuario.setText("Usuario : " 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
        
        btnNuevoCli.setEnabled(true);
        btnGuardarCli.setEnabled(false);
        //btnEliminarCli.setEnabled(true);
        //btnModificarCli.setEnabled(false);
        btnCancelarCli.setEnabled(true);
        lblIdCategoria.setText("");
        this.setIcon();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getResource("..\\img\\matserviceslogo.png")));
    }
    
    public void limpiarCajatexto() {
        lblIdCategoria.setText("");
        txtDescripCat.setText("");
        lblIdCategoria.setText("");
    }

    public void activarCajatexto(boolean b) {
        txtDescripCat.setEditable(b);
        btnNuevoCli.setEnabled(false);
    }
    
    public void activarBotones(boolean b){
        btnNuevoCli.setEnabled(b);
        btnGuardarCli.setEnabled(!b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarCat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboParametroCat = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtCategoria = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoCli = new javax.swing.JButton();
        btnGuardarCli = new javax.swing.JButton();
        btnModificarCli = new javax.swing.JButton();
        btnCancelarCli = new javax.swing.JButton();
        btnMostrarCli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtDescripCat = new javax.swing.JTextField();
        lblIdCategoria = new javax.swing.JLabel();
        btnSalirCli = new javax.swing.JButton();
        btnEliminarCli = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        btnInicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        txtBuscarCat.setForeground(new java.awt.Color(255, 0, 0));
        txtBuscarCat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCatMouseClicked(evt);
            }
        });
        txtBuscarCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCatKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 12)); // NOI18N
        jLabel3.setText("BUSCAR CATEGORÍA");

        cboParametroCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Descripción" }));
        cboParametroCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroCatActionPerformed(evt);
            }
        });

        jtCategoria.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null}
            },
            new String [] {
                "APELLIDOS", "NOMBRE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCategoriaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtCategoriaMouseEntered(evt);
            }
        });
        jtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCategoriaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtCategoria);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroCat, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("REGISTRAR CATEGORÍA");

        btnNuevoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document_1.png"))); // NOI18N
        btnNuevoCli.setText("NUEVO");
        btnNuevoCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        btnGuardarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarCli.setText("GUARDAR");
        btnGuardarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCliActionPerformed(evt);
            }
        });

        btnModificarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarCli.setText("MODIFICAR");
        btnModificarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCliActionPerformed(evt);
            }
        });

        btnCancelarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarCli.setText("CANCELAR");
        btnCancelarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCliActionPerformed(evt);
            }
        });

        btnMostrarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarCli.setText("MOSTRAR");
        btnMostrarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCliActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresar los datos del Cliente"));

        jLabel7.setText("Descripción de la Categoría (*):");

        txtDescripCat.setEditable(false);
        txtDescripCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripCatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescripCat, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 426, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblIdCategoria)
                .addGap(230, 230, 230))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDescripCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(lblIdCategoria)
                .addContainerGap())
        );

        btnSalirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCli.setText("SALIR");
        btnSalirCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCliActionPerformed(evt);
            }
        });

        btnEliminarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarCli.setText("ELIMINAR");
        btnEliminarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCliActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnInicio.setText("INICIO");
        btnInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(162, 162, 162)
                                .addComponent(lblUsuario))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnNuevoCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirCli)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalirCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCatMouseClicked
    }//GEN-LAST:event_txtBuscarCatMouseClicked

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void btnMostrarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCliActionPerformed
        JDListaCategorias jdlCategoria = new JDListaCategorias(this,true);
        jdlCategoria.setVisible(true);
    }//GEN-LAST:event_btnMostrarCliActionPerformed

    private void txtBuscarCatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCatKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarCatKeyReleased

    private void cboParametroCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroCatActionPerformed
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroCatActionPerformed

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(true);
        activarBotones(false);
        accion = "Guardar";
    }//GEN-LAST:event_btnNuevoCliActionPerformed

    private void btnCancelarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(false);
        activarBotones(true);
        btnCancelarCli.setEnabled(true);
    }//GEN-LAST:event_btnCancelarCliActionPerformed

    private void btnGuardarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCliActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtDescripCat.getText().compareTo("") != 0 
                        ) {
                    CategoriaBean cat = new CategoriaBean();
                    cat.setDescripcionCategoria(txtDescripCat.getText());
                    //huardar producto
                    hiloCategorias = new WSCategorias();
                    String rutaWS = constantes.getProperty("IP") + constantes
                            .getProperty("GUARDACATEGORIA");
                    CategoriaBean categoriaInsertada = hiloCategorias
                            .ejecutaWebService(rutaWS,"1"
                            ,cat.getDescripcionCategoria()
                            );
                    if (categoriaInsertada != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos "
                                + "Agregados ]");
                        actualizarBusqueda();
                        limpiarCajatexto();
                        activarCajatexto(false);
                        activarBotones(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al guardar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
        if (accion.equalsIgnoreCase("Actualizar")) {
            if (txtDescripCat.getText().compareTo("") != 0 
                    && lblIdCategoria.getText().compareTo("") != 0
                        ) {
                    CategoriaBean cat = new CategoriaBean();
                    cat.setIdCategoria(Integer.parseInt(lblIdCategoria.getText()));
                    cat.setDescripcionCategoria(txtDescripCat.getText());
                    //huardar producto
                    hiloCategorias = new WSCategorias();
                    String rutaWS = constantes.getProperty("IP") + constantes
                            .getProperty("MODIFICACATEGORIA");
                    CategoriaBean categoriaActualizada = hiloCategorias
                            .ejecutaWebService(rutaWS,"2"
                            ,String.valueOf(cat.getIdCategoria())
                            ,cat.getDescripcionCategoria()
                            );
                    if (categoriaActualizada != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos "
                                + "Actualizados ]");
                        actualizarBusqueda();
                        limpiarCajatexto();
                        activarCajatexto(false);
                        activarBotones(true);
                        jtCategoria.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al actualizar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
        btnNuevoCli.setEnabled(true);
    }//GEN-LAST:event_btnGuardarCliActionPerformed

    private void btnModificarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCliActionPerformed
        if (lblIdCategoria.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
            return;
        }
        jtCategoria.setEnabled(false);
        accion = "Actualizar";
        activarCajatexto(true);
        btnNuevoCli.setEnabled(false);
        btnGuardarCli.setEnabled(true);
    }//GEN-LAST:event_btnModificarCliActionPerformed

    private void buscaCategoriaFromJTable() {
        lblIdCategoria.setText(jtCategoria.getModel().getValueAt(
            jtCategoria.getSelectedRow(),0).toString());
        ArrayList<CategoriaBean> resultWS = null;
        hiloCategoriasList = new WSCategoriasList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETCATEGORIAPORID")
                + String.valueOf(jtCategoria.getModel().getValueAt(jtCategoria
                        .getSelectedRow(), 0)).trim();
        resultWS = hiloCategoriasList.ejecutaWebService(rutaWS,"2");
        CategoriaBean cat = resultWS.get(0);
        txtDescripCat.setText(cat.getDescripcionCategoria());
        jtCategoria.requestFocus(true);
    }
    
    private void jtCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCategoriaMouseClicked
        buscaCategoriaFromJTable();
    }//GEN-LAST:event_jtCategoriaMouseClicked

    private void jtCategoriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCategoriaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtCategoriaMouseEntered

    private void eliminarCategoria() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas "
                + "borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (lblIdCategoria.getText().compareTo("") != 0) {
                hiloCategorias = new WSCategorias();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("ELIMINACATEGORIA");
                CategoriaBean categoriaEliminar = hiloCategorias
                        .ejecutaWebService(rutaWS,"3"
                        ,lblIdCategoria.getText().trim());
                if (categoriaEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro "
                            + "Eliminado ]");
                    //Carga productos
                    actualizarBusqueda();
                    limpiarCajatexto();
                    activarCajatexto(false);
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible "
                            + "eliminar la "
                            + "categoria existen movimientos que la relacionan"
                            , JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                        "No hay categoria seleccionada");
            }
            btnCancelarCli.setEnabled(true);
        }
    }
    
    private void btnEliminarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCliActionPerformed
        eliminarCategoria();
    }//GEN-LAST:event_btnEliminarCliActionPerformed

    private void txtDescripCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripCatActionPerformed
    }//GEN-LAST:event_txtDescripCatActionPerformed

    private void jtCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCategoriaKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaCategoriaFromJTable();
        }
    }//GEN-LAST:event_jtCategoriaKeyReleased

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void actualizarBusqueda() {
        ArrayList<CategoriaBean> resultWS = null;
        String rutaWS = "";
        if (String.valueOf(cboParametroCat.getSelectedItem()).
                equalsIgnoreCase("Descripción")) {
            if (txtBuscarCat.getText().equalsIgnoreCase("")) {
                hiloCategoriasList = new WSCategoriasList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCATEGORIAS");
                resultWS = hiloCategoriasList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloCategoriasList = new WSCategoriasList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCATEGORIABUSQUEDANOMBRE")
                    + txtBuscarCat.getText().trim();
                resultWS = hiloCategoriasList.ejecutaWebService(rutaWS,"4");
            }
        } else {
            if (String.valueOf(cboParametroCat.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarCat.getText().equalsIgnoreCase("")) {
                    hiloCategoriasList = new WSCategoriasList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETCATEGORIAS");
                    resultWS = hiloCategoriasList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloCategoriasList = new WSCategoriasList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETCATEGORIABUSQUEDAID") 
                            + txtBuscarCat.getText().trim();
                    resultWS = hiloCategoriasList.ejecutaWebService(rutaWS,"3");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<CategoriaBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (CategoriaBean p : list) {
            datos[i][0] = p.getIdCategoria();
            datos[i][1] = p.getDescripcionCategoria();
            i++;
        }
        jtCategoria.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID", "DESCRIPCIÓN"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCli;
    private javax.swing.JButton btnEliminarCli;
    private javax.swing.JButton btnGuardarCli;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnModificarCli;
    private javax.swing.JButton btnMostrarCli;
    private javax.swing.JButton btnNuevoCli;
    private javax.swing.JButton btnSalirCli;
    private javax.swing.JComboBox cboParametroCat;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtCategoria;
    private javax.swing.JLabel lblIdCategoria;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarCat;
    private javax.swing.JTextField txtDescripCat;
    // End of variables declaration//GEN-END:variables
}