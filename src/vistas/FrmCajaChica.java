package vistas;

import beans.ClienteBean;
import ComponenteConsulta.JDListaClientes;
import ComponenteConsulta.JDListaSucursales;
import beans.CajaChicaBean;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.ProductoBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCajaChica;
import consumewebservices.WSCajaChicaList;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSSucursales;
import consumewebservices.WSSucursalesList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.estadosMun;
import static vistas.Principal.productos;

public class FrmCajaChica extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSCajaChicaList hiloCajaChicaList;
    WSCajaChica hiloCajaChica;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();

    String accion = "";
    
    public FrmCajaChica() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        cboTipoMov.setEnabled(false);
        cboComprobante.setEnabled(false);
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean configuracionBean = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        actualizarBusqueda();
        buscaUltimoRegistro();
        activarBotones(true);

        lblUsuario.setText("Usuario : " 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
        
        btnNuevoMov.setEnabled(true);
        btnGuardarMov.setEnabled(false);
        //btnEliminarCli.setEnabled(true);
        //btnModificarCli.setEnabled(false);
        btnCancelarMov.setEnabled(true);
        lblIdMov.setText("");
    }
    
    public void limpiarCajatexto() {
        lblIdMov.setText("");
        txtMonto.setText("");
        txtReferencia.setText("");
        txtSaldoActual.setText("");
        cboTipoMov.setSelectedIndex(0);
        cboComprobante.setSelectedIndex(0);        
    }

    public void activarCajatexto(boolean b) {
        txtMonto.setEditable(b);
        txtReferencia.setEditable(b);
        cboTipoMov.setEnabled(b);
        cboComprobante.setEnabled(b);
        btnNuevoMov.setEnabled(false);
    }
    
    public void activarBotones(boolean b){
        btnNuevoMov.setEnabled(b);
        btnGuardarMov.setEnabled(!b);
        //btnEliminarCli.setEnabled(b);
        //btnModificarCli.setEnabled(!b);
        btnCancelarMov.setEnabled(!b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarMov = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboParametroSuc = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSucursal = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoMov = new javax.swing.JButton();
        btnGuardarMov = new javax.swing.JButton();
        btnCancelarMov = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblIdMov = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboTipoMov = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cboComprobante = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSaldoActual = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSaldoAnterior = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        btnSalirCli = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        txtBuscarMov.setForeground(new java.awt.Color(255, 0, 0));
        txtBuscarMov.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMovMouseClicked(evt);
            }
        });
        txtBuscarMov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMovKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR OPERACIÓN");

        cboParametroSuc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Nombre" }));
        cboParametroSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroSucActionPerformed(evt);
            }
        });

        jtSucursal.setModel(new javax.swing.table.DefaultTableModel(
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
        jtSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtSucursalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtSucursalMouseEntered(evt);
            }
        });
        jtSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtSucursalKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtSucursal);

        jLabel8.setText("Fecha Inicio :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jLabel9.setText("Fecha Fin :");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        jButton2.setText("CONSULTAR");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Buscar :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("OPERACIÓN:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarMov, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboParametroSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroSuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("REGISTRAR OPERACIÓN");

        btnNuevoMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document_1.png"))); // NOI18N
        btnNuevoMov.setText("NUEVO");
        btnNuevoMov.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoMov.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMovActionPerformed(evt);
            }
        });

        btnGuardarMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarMov.setText("GUARDAR");
        btnGuardarMov.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarMov.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMovActionPerformed(evt);
            }
        });

        btnCancelarMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarMov.setText("CANCELAR");
        btnCancelarMov.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarMov.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMovActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresar los datos de la Operación"));

        jLabel1.setText("Monto $ ;");

        txtMonto.setEditable(false);
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });

        jLabel2.setText("Tipo Movimiento :");

        cboTipoMov.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Gasto", "Ingreso" }));
        cboTipoMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoMovActionPerformed(evt);
            }
        });

        jLabel5.setText("Comprobante :");

        cboComprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Nota Remisión", "Factura", "Recibo", "Otro (Especifica en referencia)" }));
        cboComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboComprobanteActionPerformed(evt);
            }
        });

        jLabel6.setText("Referencia :");

        txtReferencia.setEditable(false);
        txtReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReferenciaActionPerformed(evt);
            }
        });

        jLabel7.setText("Saldo Anterior :");

        txtSaldoActual.setEditable(false);

        jLabel12.setText("Saldo Actual :");

        txtSaldoAnterior.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblIdMov)
                .addGap(230, 230, 230))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtReferencia)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSaldoAnterior))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboTipoMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cboComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(lblIdMov)
                .addContainerGap())
        );

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        btnSalirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCli.setText("SALIR");
        btnSalirCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNuevoMov, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarMov, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelarMov)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoMov, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardarMov, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelarMov, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalirCli, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscaUltimoRegistro() {
        ArrayList<CajaChicaBean> resultWS = null;
        hiloCajaChicaList = new WSCajaChicaList();
        String rutaWS = constantes.getProperty("IP") + constantes.
                getProperty("GETULTIMOCAJACHICA");
        resultWS = hiloCajaChicaList.ejecutaWebService(rutaWS,"2");
        CajaChicaBean cajaChicaBean = resultWS.get(0);
        txtSaldoAnterior.setText("" + cajaChicaBean.getSaldoAnterior());
        txtSaldoActual.setText("" + cajaChicaBean.getSaldoActual());
        //JOptionPane.showMessageDialog(null, "Saldo Anterior: " + cajaChicaBean.getSaldoAnterior() + " -- Saldo Aactual: " + cajaChicaBean.getSaldoActual());
    }
    
    private void txtBuscarMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMovMouseClicked
    }//GEN-LAST:event_txtBuscarMovMouseClicked

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void txtBuscarMovKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMovKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarMovKeyReleased

    private void cboParametroSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroSucActionPerformed
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroSucActionPerformed

    private void btnNuevoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMovActionPerformed
        limpiarCajatexto();
        activarCajatexto(true);
        activarBotones(false);
        accion = "Guardar";
        buscaUltimoRegistro();
        txtMonto.requestFocus();
    }//GEN-LAST:event_btnNuevoMovActionPerformed

    private void btnCancelarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMovActionPerformed
        limpiarCajatexto();
        activarCajatexto(false);
        activarBotones(true);
        buscaUltimoRegistro();
        btnCancelarMov.setEnabled(true);
    }//GEN-LAST:event_btnCancelarMovActionPerformed

    private void btnGuardarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMovActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if ((txtMonto.getText().compareTo("") != 0 ) 
                && !cboTipoMov.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboComprobante.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && (txtReferencia.getText().compareTo("") != 0 )   
                )
            {
                CajaChicaBean cajaChica = new CajaChicaBean();
                cajaChica.setFecha(new Date());
                cajaChica.setMonto(Double.parseDouble(txtMonto.getText()));
                cajaChica.setTipoMov(cboTipoMov.getSelectedItem().toString());
                cajaChica.setTipoComprobante(cboComprobante.getSelectedItem().toString());
                cajaChica.setReferencia(txtReferencia.getText());
                cajaChica.setIdUsuario(Ingreso.usuario.getIdUsuario());
                cajaChica.setIdSucursal(Ingreso.usuario.getIdSucursal());
                buscaUltimoRegistro();
                if ((cboTipoMov.getSelectedItem().toString()
                        .equalsIgnoreCase("Gasto") 
                        && (Double.parseDouble(txtSaldoActual.getText()) 
                        < Double.parseDouble(txtMonto.getText()))
                        )) {
                    JOptionPane.showMessageDialog(null, "La cantidad excede "
                            + "al Saldo disponible ");
                    txtMonto.requestFocus(true);
                    return;
                }

                cajaChica.setSaldoAnterior(Double.parseDouble(txtSaldoActual.getText()));
                double saldoActual = 0;
                //verifica si es gasto
                if (cboTipoMov.getSelectedItem().toString()
                        .equalsIgnoreCase("Gasto")) {
                    saldoActual = Double.parseDouble(txtSaldoActual.getText())
                            - Double.parseDouble(txtMonto.getText());
                }
//                //verifica si es Devolución Gasto
//                if (cboTipoMov.getSelectedItem().toString()
//                        .equalsIgnoreCase("Devolución Gasto")) {
//                    saldoActual = Double.parseDouble(txtSaldoActual.getText())
//                            + Double.parseDouble(txtMonto.getText());
//                }
                //verifica si es ingreso
                if (cboTipoMov.getSelectedItem().toString()
                        .equalsIgnoreCase("Ingreso")) {
                    saldoActual = Double.parseDouble(txtSaldoActual.getText())
                            + Double.parseDouble(txtMonto.getText());
                }
//                //verifica si es Devolución Ingreso
//                if (cboTipoMov.getSelectedItem().toString()
//                        .equalsIgnoreCase("Devolución Ingreso")) {
//                    saldoActual = Double.parseDouble(txtSaldoActual.getText())
//                            - Double.parseDouble(txtMonto.getText());
//                }
                cajaChica.setSaldoActual(saldoActual);
                //guardar sucursal
                hiloCajaChica = new WSCajaChica();
                String rutaWS = constantes.getProperty("IP") 
                        + constantes.getProperty("GUARDAMOVCAJACHICA");
                CajaChicaBean movCajaInsertada = hiloCajaChica.ejecutaWebService(rutaWS,"1"
                    , cajaChica.getFecha().toLocaleString()
                    , "" + cajaChica.getMonto()
                    , cajaChica.getTipoMov()
                    , cajaChica.getTipoComprobante()
                    , cajaChica.getReferencia()
                    , "" + cajaChica.getIdUsuario()
                    , "" + cajaChica.getIdSucursal()
                    , "" + cajaChica.getSaldoAnterior()
                    , "" + cajaChica.getSaldoActual()
                        );
                if (movCajaInsertada != null) {
                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
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
        btnNuevoMov.setEnabled(true);
    }//GEN-LAST:event_btnGuardarMovActionPerformed

    private void buscaSucursalFromJTable() {
        lblIdMov.setText(jtSucursal.getModel().getValueAt(
            jtSucursal.getSelectedRow(),0).toString());
        ArrayList<CajaChicaBean> resultWS = null;
        hiloCajaChicaList = new WSCajaChicaList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETCAJACHICAPORID")
                + String.valueOf(jtSucursal.getModel().getValueAt(
                        jtSucursal.getSelectedRow(), 0)).trim();
        resultWS = hiloCajaChicaList.ejecutaWebService(rutaWS,"3");
        CajaChicaBean cajaChica = resultWS.get(0);
        txtMonto.setText("" + cajaChica.getMonto());
        cboTipoMov.setSelectedItem(cajaChica.getTipoMov());
        cboComprobante.setSelectedItem(cajaChica.getTipoComprobante());
        txtReferencia.setText(cajaChica.getReferencia());
        txtSaldoAnterior.setText("" + cajaChica.getSaldoAnterior());
        txtSaldoActual.setText("" + cajaChica.getSaldoActual());
        
        jtSucursal.requestFocus(true);
    }
    
    private void jtSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtSucursalMouseClicked
        buscaSucursalFromJTable();
    }//GEN-LAST:event_jtSucursalMouseClicked

    private void jtSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtSucursalMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtSucursalMouseEntered

    private void jtSucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtSucursalKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaSucursalFromJTable();
        }
    }//GEN-LAST:event_jtSucursalKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        limpiaTblDetallePedido();
//        String fechaIni = "";
//        String fechaFin = "";
//        //Tomamos las dos fechas y las convierto a java.sql.date
//        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
//        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
//        java.sql.Date fechaSqlDateIni;
//        java.sql.Date fechaSqlDateFin;
//        try {
//            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
//            return;
//        }
//        try {
//            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
//        } catch (Exception e) {
//            fechaSqlDateFin = fechaSqlDateIni;
//        }
//        fechaIni = fechaSqlDateIni.toString();
//        fechaFin = fechaSqlDateFin.toString();
//        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
//            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
//            return;
//        }
//        // Actualizas tbl Ventas
//        ArrayList<PedidoBean> pedidosPorFechas = null;
//        hiloPedidosList = new WSPedidosList();
//        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETPEDIDOSPORFECHASFINI") + fechaIni +
//        constantes.getProperty("GETPEDIDOSPORFECHASFFIN") + fechaFin;
//        pedidosPorFechas = hiloPedidosList.ejecutaWebService(rutaWS,"2");
//        recargarTablePedidos(pedidosPorFechas);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        //valida solo numeros
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoKeyTyped

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        cboTipoMov.requestFocus(true);
    }//GEN-LAST:event_txtMontoActionPerformed

    private void cboTipoMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoMovActionPerformed
        cboComprobante.requestFocus();
    }//GEN-LAST:event_cboTipoMovActionPerformed

    private void cboComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboComprobanteActionPerformed
        txtReferencia.requestFocus(true);
    }//GEN-LAST:event_cboComprobanteActionPerformed

    private void txtReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReferenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReferenciaActionPerformed

    private void actualizarBusqueda() {
        ArrayList<CajaChicaBean> resultWS = null;
        String rutaWS = "";
        if (String.valueOf(cboParametroSuc.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarMov.getText().equalsIgnoreCase("")) {
                hiloCajaChicaList = new WSCajaChicaList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCAJACHICAS");
                resultWS = hiloCajaChicaList.ejecutaWebService(rutaWS,"1");
            } else {
//                hiloSucursalesList = new WSSucursalesList();
//                rutaWS = constantes.getProperty("IP") + constantes.
//                        getProperty("GETSUCURSALBUSQUEDANOMBRE")
//                    + txtBuscarSuc.getText().trim();
//                resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"4");
            }
        } else {
            if (String.valueOf(cboParametroSuc.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarMov.getText().equalsIgnoreCase("")) {
                    hiloCajaChicaList = new WSCajaChicaList();
                    rutaWS = constantes.getProperty("IP") + constantes.
                            getProperty("GETCAJACHICAS");
                    resultWS = hiloCajaChicaList.ejecutaWebService(rutaWS,"1");
                } else {
//                    hiloSucursalesList = new WSSucursalesList();
//                    rutaWS = constantes.getProperty("IP") 
//                            + constantes.getProperty("GETSUCURSALBUSQUEDAID") 
//                            + txtBuscarSuc.getText().trim();
//                    resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"3");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<CajaChicaBean> list) {
        Object[][] datos = new Object[list.size()][10];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
        int i = 0;
        for (CajaChicaBean p : list) {
            datos[i][0] = p.getIdMov();
            datos[i][1] = dateFormat.format(p.getFecha());
            datos[i][2] = p.getMonto();
            datos[i][3] = p.getTipoMov();
            datos[i][4] = p.getTipoComprobante();
            datos[i][5] = p.getReferencia();
            datos[i][6] = util.buscaIdUsuario(Principal.usuariosHM, "" + p.getIdUsuario());
            String suc = util.buscaDescFromIdSuc(Principal.sucursalesHM
                    , "" + p.getIdSucursal());
            datos[i][7] = suc;
            datos[i][8] = p.getSaldoAnterior();
            datos[i][9] = p.getSaldoActual();
            i++;
        }
        jtSucursal.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "idMov","Fecha","Monto","TipoMov","Comprobante","Referencia","Usuario","Sucursal","$ Anterior","$ Actual"                
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarMov;
    private javax.swing.JButton btnGuardarMov;
    private javax.swing.JButton btnNuevoMov;
    private javax.swing.JButton btnSalirCli;
    private javax.swing.JComboBox cboComprobante;
    private javax.swing.JComboBox cboParametroSuc;
    private javax.swing.JComboBox cboTipoMov;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtSucursal;
    private javax.swing.JLabel lblIdMov;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarMov;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtReferencia;
    private javax.swing.JTextField txtSaldoActual;
    private javax.swing.JTextField txtSaldoAnterior;
    // End of variables declaration//GEN-END:variables
}