package vistas;

import beans.ClienteBean;
import ComponenteConsulta.JDListaClientes;
import ComponenteConsulta.JDListaSucursales;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.ProductoBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
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
import java.util.ArrayList;
import java.util.Collections;
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
    WSSucursalesList hiloSucursalesList;
    WSSucursales hiloSucursales;
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
        lblIdSucursal.setText("");
    }
    
    public void limpiarCajatexto() {
        lblIdSucursal.setText("");
        txtMonto.setText("");
        txtReferencia.setText("");
        txtSaldoActual.setText("");
        cboTipoMov.setSelectedIndex(0);
        cboComprobante.setSelectedIndex(0);        
    }

    public void activarCajatexto(boolean b) {
        txtMonto.setEditable(b);
        txtReferencia.setEditable(b);
        txtSaldoActual.setEditable(b);
        cboTipoMov.setEnabled(b);
        cboComprobante.setEnabled(b);
        btnNuevoCli.setEnabled(false);
    }
    
    public void activarBotones(boolean b){
        btnNuevoCli.setEnabled(b);
        btnGuardarCli.setEnabled(!b);
        //btnEliminarCli.setEnabled(b);
        //btnModificarCli.setEnabled(!b);
        btnCancelarCli.setEnabled(!b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarSuc = new javax.swing.JTextField();
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
        btnSalirCli = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoCli = new javax.swing.JButton();
        btnGuardarCli = new javax.swing.JButton();
        btnModificarCli = new javax.swing.JButton();
        btnCancelarCli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblIdSucursal = new javax.swing.JLabel();
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
        btnEliminarCli = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        txtBuscarSuc.setForeground(new java.awt.Color(255, 0, 0));
        txtBuscarSuc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarSucMouseClicked(evt);
            }
        });
        txtBuscarSuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarSucKeyReleased(evt);
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

        btnSalirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCli.setText("SALIR");
        btnSalirCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addComponent(txtBuscarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboParametroSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addComponent(btnSalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboParametroSuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addComponent(btnSalirCli))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("REGISTRAR OPERACIÓN");

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

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresar los datos de la Operación"));

        jLabel1.setText("Monto $ ;");

        txtMonto.setEditable(false);

        jLabel2.setText("Tipo Movimiento :");

        cboTipoMov.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Gasto", "Ingreso" }));

        jLabel5.setText("Comprobante :");

        cboComprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Nota Remisión", "Factura", "Otro (Especifica en referencia)" }));

        jLabel6.setText("Referencia :");

        txtReferencia.setEditable(false);

        jLabel7.setText("Saldo Actual :");

        txtSaldoActual.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblIdSucursal)
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
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(433, Short.MAX_VALUE))
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
                    .addComponent(txtSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(lblIdSucursal)
                .addContainerGap())
        );

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addGap(69, 69, 69)
                        .addComponent(lblUsuario))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarCli)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCancelarCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtBuscarSucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarSucMouseClicked
    }//GEN-LAST:event_txtBuscarSucMouseClicked

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void txtBuscarSucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarSucKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarSucKeyReleased

    private void cboParametroSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroSucActionPerformed
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroSucActionPerformed

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(true);
        activarBotones(false);
        accion = "Guardar";
        txtMonto.requestFocus();
    }//GEN-LAST:event_btnNuevoCliActionPerformed

    private void btnCancelarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(false);
        activarBotones(true);
        btnCancelarCli.setEnabled(true);
    }//GEN-LAST:event_btnCancelarCliActionPerformed

    private void btnGuardarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCliActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtMonto.getText().compareTo("") != 0 ) {
                    SucursalBean suc = new SucursalBean();
                    suc.setDescripcionSucursal(txtMonto.getText());
                    //guardar sucursal
                    hiloSucursales = new WSSucursales();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GUARDASUCURSAL");
                    SucursalBean sucursalInsertada = hiloSucursales.ejecutaWebService(rutaWS,"1"
                            ,suc.getDescripcionSucursal()
                            );
                    if (sucursalInsertada != null) {
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
        if (accion.equalsIgnoreCase("Actualizar")) {
            if (txtMonto.getText().compareTo("") != 0 
                    && lblIdSucursal.getText().compareTo("") != 0
                        ) {
                    SucursalBean suc = new SucursalBean();
                    suc.setIdSucursal(Integer.parseInt(lblIdSucursal.getText()));
                    suc.setDescripcionSucursal(txtMonto.getText());
                    //huardar producto
                    hiloSucursales = new WSSucursales();
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICASUCURSAL");
                    SucursalBean sucursalActualizada = hiloSucursales.ejecutaWebService(rutaWS,"2"
                            ,String.valueOf(suc.getIdSucursal())
                            ,suc.getDescripcionSucursal()
                            );
                    if (sucursalActualizada != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Actualizados ]");
                        actualizarBusqueda();
                        limpiarCajatexto();
                        activarCajatexto(false);
                        activarBotones(true);
                        jtSucursal.setEnabled(true);
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
        if (lblIdSucursal.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
            return;
        }
        jtSucursal.setEnabled(false);
        accion = "Actualizar";
        activarCajatexto(true);
        btnNuevoCli.setEnabled(false);
        btnGuardarCli.setEnabled(true);
//        btnModificarCli.setEnabled(false);
//        btnCancelarCli.setEnabled(true);
//        btnMostrarCli.setEnabled(false);
    }//GEN-LAST:event_btnModificarCliActionPerformed

    private void buscaSucursalFromJTable() {
        lblIdSucursal.setText(jtSucursal.getModel().getValueAt(
            jtSucursal.getSelectedRow(),0).toString());
        ArrayList<SucursalBean> resultWS = null;
        hiloSucursalesList = new WSSucursalesList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETSUCURSALPORID")
                + String.valueOf(jtSucursal.getModel().getValueAt
                (jtSucursal.getSelectedRow(), 0)).trim();
        resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"2");
        SucursalBean suc = resultWS.get(0);
        txtMonto.setText(suc.getDescripcionSucursal());
        jtSucursal.requestFocus(true);
    }
    
    private void jtSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtSucursalMouseClicked
        buscaSucursalFromJTable();
    }//GEN-LAST:event_jtSucursalMouseClicked

    private void jtSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtSucursalMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtSucursalMouseEntered

    private void eliminarSucursal() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (lblIdSucursal.getText().compareTo("") != 0) {
                hiloSucursales = new WSSucursales();
                String rutaWS = constantes.getProperty("IP") 
                        + constantes.getProperty("ELIMINASUCURSAL");
                SucursalBean sucursalEliminar = hiloSucursales.ejecutaWebService(rutaWS,"3"
                        ,lblIdSucursal.getText().trim());
                if (sucursalEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                    //Carga productos
                    actualizarBusqueda();
                    limpiarCajatexto();
                    activarCajatexto(false);
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible "
                            + "eliminar la "
                            + "sucursal existen movimientos que lo relacionan"
                            , JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                        "No hay sucursal seleccionada");
            }
            btnCancelarCli.setEnabled(true);
        }
    }
    
    private void btnEliminarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCliActionPerformed
        eliminarSucursal();
    }//GEN-LAST:event_btnEliminarCliActionPerformed

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

    private void actualizarBusqueda() {
        ArrayList<SucursalBean> resultWS = null;
        String rutaWS = "";
        if (String.valueOf(cboParametroSuc.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarSuc.getText().equalsIgnoreCase("")) {
                hiloSucursalesList = new WSSucursalesList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETSUCURSALES");
                resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloSucursalesList = new WSSucursalesList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETSUCURSALBUSQUEDANOMBRE")
                    + txtBuscarSuc.getText().trim();
                resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"4");
            }
        } else {
            if (String.valueOf(cboParametroSuc.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarSuc.getText().equalsIgnoreCase("")) {
                    hiloSucursalesList = new WSSucursalesList();
                    rutaWS = constantes.getProperty("IP") + constantes.
                            getProperty("GETSUCURSALES");
                    resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloSucursalesList = new WSSucursalesList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETSUCURSALBUSQUEDAID") 
                            + txtBuscarSuc.getText().trim();
                    resultWS = hiloSucursalesList.ejecutaWebService(rutaWS,"3");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<SucursalBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (SucursalBean p : list) {
            datos[i][0] = p.getIdSucursal();
            datos[i][1] = p.getDescripcionSucursal();
            i++;
        }
        jtSucursal.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID SUCURSAL", "SUCURSAL"
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
    private javax.swing.JButton btnModificarCli;
    private javax.swing.JButton btnNuevoCli;
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
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarSuc;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtReferencia;
    private javax.swing.JTextField txtSaldoActual;
    // End of variables declaration//GEN-END:variables
}