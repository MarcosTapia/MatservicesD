package vistas;

import beans.ClienteBean;
import ComponenteConsulta.JDListaClientes;
import ComponenteConsulta.JDListaSucursales;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.ProductoBean;
import beans.SistemaBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSSistema;
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
import static vistas.Principal.datosEmpresaBean;
import static vistas.Principal.estadosMun;
import static vistas.Principal.productos;

public class FrmSistema extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSSistema hiloSistema;
    //WSUsuarios
    WSSucursalesList hiloSucursalesList;
    WSSucursales hiloSucursales;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    SistemaBean sistemaBean = new SistemaBean();

    String accion = "";
    
    public FrmSistema() {
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
        activarBotones(true);
        
        hiloSistema = new WSSistema();
        rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSSISTEMA");
        SistemaBean sistemaBean = hiloSistema.
                ejecutaWebService(rutaWS,"1");
        activarBotonesSist(true);
        
        
        //carga estados
        Iterator it = Principal.estadosHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboEstados.addItem(Principal.estadosHM.get(key));
        }
        cargaDatosEmpresa(configuracionBean);
        cargaDatosSistema(sistemaBean);
        cboEstados.setEnabled(false);
        cboMunicipio.setEnabled(false);
        lblUsuario.setText("Usuario : " 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
        btnGuardarCEmp.setEnabled(false);
        lblIdSucursal.setText("");
    }
    
    public void cargaDatosEmpresa(DatosEmpresaBean configuracionBean) {
        txtNombreEmpresa.setText(configuracionBean.getNombreEmpresa());
        txtRfcEmpresa.setText(configuracionBean.getRfcEmpresa());
        txtEmailEmp.setText(configuracionBean.getEmailEmpresa());
        txtDirEmpresa.setText(configuracionBean.getDireccionEmpresa());
        txtCPEmp.setText(configuracionBean.getCpEmpresa());
        txtTelEmp.setText(configuracionBean.getTelEmpresa());
        String edo = "";
        String munic = "";
        edo = util.buscaDescFromIdEdo(Principal.estadosHM, 
                configuracionBean.getEstadoEmpresa().toString());
        if ("".equalsIgnoreCase(edo)) {
            cboEstados.setSelectedItem("Seleccionar...");
        } else {
            cboEstados.setSelectedItem(edo);
        }
        munic = util.buscaDescFromIdMun(Principal.municipiosHM, 
                configuracionBean.getCiudadEmpresa());
        if ("".equalsIgnoreCase(munic)) {
            cboMunicipio.setSelectedItem("Seleccionar...");
        } else {
            cboMunicipio.setSelectedItem(munic);
        }
        txtNombreEmpresa.requestFocus(true);
    }
    
    public void cargaDatosSistema(SistemaBean sistemaBean) {
        txtIvaEmpresa.setText("" + sistemaBean.getIvaEmpresa());
        txtIvaGral.setText("" + sistemaBean.getIvaGral());
        txtIvaEmpresa.requestFocus(true);
    }
    
    public void limpiarCajatexto() {
        lblIdSucursal.setText("");
        txtNombreEmpresa.setText("");
        cboEstados.setSelectedIndex(0);
        cboMunicipio.setSelectedIndex(0);
    }

    public void activarCajatexto(boolean b) {
        txtNombreEmpresa.setEditable(b);
        txtRfcEmpresa.setEditable(b);
        txtEmailEmp.setEditable(b);
        txtDirEmpresa.setEditable(b);
        txtCPEmp.setEditable(b);
        txtTelEmp.setEditable(b);
        cboEstados.setEnabled(b);
        cboMunicipio.setEnabled(b);
        txtNombreEmpresa.requestFocus(true);
    }
    
    public void activarCajatextoSistema(boolean b) {
        txtIvaEmpresa.setEditable(b);
        txtIvaGral.setEditable(b);
        txtIvaEmpresa.requestFocus(true);
    }
    
    public void activarBotones(boolean b){
        btnGuardarCEmp.setEnabled(!b);
    }

    public void activarBotonesSist(boolean b){
        btnGuardarDatSist.setEnabled(!b);
        btnModificarDatSist.setEnabled(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnMostrarCli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblIdSucursal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreEmpresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRfcEmpresa = new javax.swing.JTextField();
        btnGuardarCEmp = new javax.swing.JButton();
        btnModificarDatEmp = new javax.swing.JButton();
        btnCancelarDatEmp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDirEmpresa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmailEmp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelEmp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCPEmp = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboEstados = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cboMunicipio = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        lblIdSucursal1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIvaEmpresa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIvaGral = new javax.swing.JTextField();
        btnGuardarDatSist = new javax.swing.JButton();
        btnModificarDatSist = new javax.swing.JButton();
        btnCancelarDatSist = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        btnSalirCli = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

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
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores Globales (Datos de la Empresa)"));

        jLabel1.setText("Nombre :");

        txtNombreEmpresa.setEditable(false);
        txtNombreEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEmpresaActionPerformed(evt);
            }
        });

        jLabel2.setText("RFC :");

        txtRfcEmpresa.setEditable(false);
        txtRfcEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRfcEmpresaActionPerformed(evt);
            }
        });

        btnGuardarCEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarCEmp.setText("GUARDAR");
        btnGuardarCEmp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarCEmp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarCEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCEmpActionPerformed(evt);
            }
        });

        btnModificarDatEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarDatEmp.setText("MODIFICAR");
        btnModificarDatEmp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarDatEmp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarDatEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatEmpActionPerformed(evt);
            }
        });

        btnCancelarDatEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarDatEmp.setText("CANCELAR");
        btnCancelarDatEmp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarDatEmp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarDatEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDatEmpActionPerformed(evt);
            }
        });

        jLabel6.setText("Dirección :");

        txtDirEmpresa.setEditable(false);
        txtDirEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirEmpresaActionPerformed(evt);
            }
        });

        jLabel7.setText("Email :");

        txtEmailEmp.setEditable(false);
        txtEmailEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailEmpActionPerformed(evt);
            }
        });

        jLabel8.setText("Teléfono :");

        txtTelEmp.setEditable(false);
        txtTelEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelEmpActionPerformed(evt);
            }
        });

        jLabel9.setText("CP :");

        txtCPEmp.setEditable(false);
        txtCPEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPEmpActionPerformed(evt);
            }
        });

        jLabel12.setText("Estado :");

        cboEstados.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadosActionPerformed(evt);
            }
        });
        cboEstados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboEstadosKeyTyped(evt);
            }
        });

        jLabel13.setText("Municipio :");

        cboMunicipio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMunicipioActionPerformed(evt);
            }
        });

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
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDirEmpresa))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRfcEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmailEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnGuardarCEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModificarDatEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelarDatEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCPEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTelEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRfcEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmailEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDirEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCPEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCancelarDatEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificarDatEmp))
                    .addComponent(btnGuardarCEmp))
                .addGap(30, 30, 30)
                .addComponent(lblIdSucursal)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(247, 254, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores Globales del Sistema"));

        jLabel3.setText("IVA EMPRESA (Ganancia por Producto) :");

        txtIvaEmpresa.setEditable(false);
        txtIvaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIvaEmpresaActionPerformed(evt);
            }
        });

        jLabel5.setText("IVA GENERAL (Iva para Ventas) :");

        txtIvaGral.setEditable(false);
        txtIvaGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIvaGralActionPerformed(evt);
            }
        });

        btnGuardarDatSist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarDatSist.setText("GUARDAR");
        btnGuardarDatSist.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarDatSist.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarDatSist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDatSistActionPerformed(evt);
            }
        });

        btnModificarDatSist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarDatSist.setText("MODIFICAR");
        btnModificarDatSist.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarDatSist.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarDatSist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatSistActionPerformed(evt);
            }
        });

        btnCancelarDatSist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarDatSist.setText("CANCELAR");
        btnCancelarDatSist.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarDatSist.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarDatSist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDatSistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 168, Short.MAX_VALUE)
                .addComponent(lblIdSucursal1)
                .addGap(230, 230, 230))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIvaGral, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(txtIvaEmpresa)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnGuardarDatSist, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarDatSist, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarDatSist, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIvaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtIvaGral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCancelarDatSist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificarDatSist))
                    .addComponent(btnGuardarDatSist))
                .addGap(30, 30, 30)
                .addComponent(lblIdSucursal1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(494, 494, 494)
                        .addComponent(btnMostrarCli))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btnMostrarCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("CONFIGURACIÓN DE VALORES GENERALES");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(388, 388, 388))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSalirCli)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void btnMostrarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCliActionPerformed
        JDListaSucursales jdlSucursal = new JDListaSucursales(this,true);
        jdlSucursal.setVisible(true);
    }//GEN-LAST:event_btnMostrarCliActionPerformed

    private void btnCancelarDatEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDatEmpActionPerformed
        // Carga datos de la empresa
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETDATOSEMPRESA");
        datosEmpresaBean = hiloEmpresa.ejecutaWebService(rutaWS,"1");
        // Fin Carga datos de la empresa
        cargaDatosEmpresa(datosEmpresaBean);
        btnModificarDatEmp.setEnabled(true);
        btnGuardarCEmp.setEnabled(false);
    }//GEN-LAST:event_btnCancelarDatEmpActionPerformed

    private void btnGuardarCEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCEmpActionPerformed
        if (accion.equalsIgnoreCase("Actualizar")) {
            if ((txtNombreEmpresa.getText().compareTo("") != 0) 
                    && !cboEstados.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && !cboMunicipio.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar..."))
                    {
                    DatosEmpresaBean configuracionBeanGuardar = 
                            new DatosEmpresaBean();
                    configuracionBeanGuardar.setNombreEmpresa
                        (txtNombreEmpresa.getText());
                    configuracionBeanGuardar.setRfcEmpresa
                        (txtRfcEmpresa.getText());
                    configuracionBeanGuardar.setEmailEmpresa
                        (txtEmailEmp.getText());
                    configuracionBeanGuardar.setDireccionEmpresa
                        (txtDirEmpresa.getText());
                    configuracionBeanGuardar.setCpEmpresa(txtCPEmp.getText());
                    configuracionBeanGuardar.setTelEmpresa(txtTelEmp.getText());
                    int edo = util.buscaIdEdo(Principal.estadosHM
                            , cboEstados.getSelectedItem().toString());
                    int mun = util.buscaIdMun(Principal.municipiosHM
                            , cboMunicipio.getSelectedItem().toString());
                    configuracionBeanGuardar.setEstadoEmpresa("" + edo);
                    configuracionBeanGuardar.setCiudadEmpresa("" + mun);
                    configuracionBeanGuardar.setPaisEmpresa("México");
                    //huardar producto
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICADATOSEMPRESA");
                    DatosEmpresaBean datosEmpresaActualizada = hiloEmpresa.ejecutaWebService(rutaWS,"2"
                            ,configuracionBeanGuardar.getNombreEmpresa()
                            ,configuracionBeanGuardar.getRfcEmpresa()
                            ,configuracionBeanGuardar.getEmailEmpresa()
                            ,configuracionBeanGuardar.getDireccionEmpresa()
                            ,configuracionBeanGuardar.getCpEmpresa()
                            ,configuracionBeanGuardar.getTelEmpresa()
                            ,configuracionBeanGuardar.getEstadoEmpresa()
                            ,configuracionBeanGuardar.getCiudadEmpresa()
                            ,configuracionBeanGuardar.getPaisEmpresa()
                    );
                    if (datosEmpresaActualizada != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Actualizados ]");
                        // Carga datos de la empresa
                        hiloEmpresa = new WSDatosEmpresa();
                        rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETDATOSEMPRESA");
                        datosEmpresaBean = hiloEmpresa.ejecutaWebService(rutaWS,"1");
                        // Fin Carga datos de la empresa
                        cargaDatosEmpresa(datosEmpresaBean);
                        activarCajatexto(false);
                        activarBotones(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al actualizar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
    }//GEN-LAST:event_btnGuardarCEmpActionPerformed

    private void btnModificarDatEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatEmpActionPerformed
        accion = "Actualizar";
        activarCajatexto(true);
        btnGuardarCEmp.setEnabled(true);
        btnModificarDatEmp.setEnabled(false);
    }//GEN-LAST:event_btnModificarDatEmpActionPerformed

    private void btnGuardarDatSistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDatSistActionPerformed
        if (accion.equalsIgnoreCase("Actualizar")) {
            if ((txtIvaEmpresa.getText().compareTo("") != 0) 
                    && (txtIvaGral.getText().compareTo("") != 0))
                    {
                    SistemaBean sistemaBean = 
                            new SistemaBean();
                    sistemaBean.setIvaEmpresa(Double.parseDouble(txtIvaEmpresa.getText()));
                    sistemaBean.setHistoricoProveedores("1");
                    sistemaBean.setCriterioHistoricoProveedores("3");
                    sistemaBean.setCamposInventario("0111111110");
                    sistemaBean.setCamposVentas("0111111111");
                    sistemaBean.setCamposCompras("0111111111");
                    sistemaBean.setCamposConsultas("0111111111");
                    sistemaBean.setCamposProveedores("0111111111");
                    sistemaBean.setCamposClientes("0111111111");
                    sistemaBean.setCamposEmpleados("0111111111");
                    sistemaBean.setCamposEmpresa("0111111111");
                    sistemaBean.setIvaGral(Double.parseDouble(txtIvaGral.getText()));
                    //huardar producto
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICADATOSSISTEMA");
                    SistemaBean sistemaActualizado = hiloSistema.ejecutaWebService(rutaWS,"2"
                        ,"" + sistemaBean.getIvaEmpresa()
                        ,"" + sistemaBean.getHistoricoProveedores()
                        ,"" + sistemaBean.getCriterioHistoricoProveedores()
                        ,"" + sistemaBean.getCamposInventario()
                        ,"" + sistemaBean.getCamposVentas()
                        ,"" + sistemaBean.getCamposCompras()
                        ,"" + sistemaBean.getCamposConsultas()
                        ,"" + sistemaBean.getCamposProveedores()
                        ,"" + sistemaBean.getCamposClientes()
                        ,"" + sistemaBean.getCamposEmpleados()
                        ,"" + sistemaBean.getCamposEmpresa()
                        ,"" + sistemaBean.getIvaGral()
                    );
                    if (sistemaActualizado != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Actualizados ]");
        
                        // Carga datos de la empresa
                        hiloSistema = new WSSistema();
                        rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETDATOSSISTEMA");
                        sistemaBean = hiloSistema.ejecutaWebService(rutaWS,"1");
                        // Fin Carga datos de la empresa
                        cargaDatosSistema(sistemaBean);
                        
                        activarCajatextoSistema(false);
                        activarBotonesSist(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al actualizar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
    }//GEN-LAST:event_btnGuardarDatSistActionPerformed

    private void btnModificarDatSistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatSistActionPerformed
        accion = "Actualizar";
        activarCajatextoSistema(true);
        btnGuardarDatSist.setEnabled(true);
        btnModificarDatSist.setEnabled(false);        
    }//GEN-LAST:event_btnModificarDatSistActionPerformed

    private void btnCancelarDatSistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDatSistActionPerformed
        // Carga datos de la empresa
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETDATOSEMPRESA");
        datosEmpresaBean = hiloEmpresa.ejecutaWebService(rutaWS,"1");
        // Fin Carga datos de la empresa
        cargaDatosEmpresa(datosEmpresaBean);
        btnModificarDatSist.setEnabled(true);
        btnGuardarDatSist.setEnabled(false);
    }//GEN-LAST:event_btnCancelarDatSistActionPerformed

    private void cboEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadosActionPerformed
        String item = cboEstados.getSelectedItem().toString();
        int indiceEdo = util.buscaIdEdo(Principal.estadosHM, item);
        cboMunicipio.removeAllItems();
        List<String> listMuni = new ArrayList();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement("Seleccionar...");
        // llena combo con municipios
        for (EdoMunBean s : Principal.estadosMun) {
            if (s.getIdEstado() == indiceEdo) {
                String muni = util.buscaDescFromIdMun(Principal.municipiosHM, ""
                    + s.getIdMunicipio());
                listMuni.add(muni);
            }
        }
        Collections.sort(listMuni);
        //Collections.reverse(listMuni);
        for (String listMuni1 : listMuni) {
            modelo.addElement(listMuni1);
        }
        cboMunicipio.setModel(modelo);
        // llena combo con municipios
        cboMunicipio.requestFocus();
    }//GEN-LAST:event_cboEstadosActionPerformed

    private void cboEstadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEstadosKeyTyped
        //        int key=evt.getKeyCode();
        //        if(key==0)
        //        {
            //            String item = cboEstados.getSelectedItem().toString();
            //            JOptionPane.showMessageDialog(null, item);
            //        }
    }//GEN-LAST:event_cboEstadosKeyTyped

    private void cboMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMunicipioActionPerformed
        btnGuardarCEmp.requestFocus(true);
    }//GEN-LAST:event_cboMunicipioActionPerformed

    private void txtNombreEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEmpresaActionPerformed
        txtRfcEmpresa.requestFocus(true);
    }//GEN-LAST:event_txtNombreEmpresaActionPerformed

    private void txtRfcEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRfcEmpresaActionPerformed
        txtEmailEmp.requestFocus(true);
    }//GEN-LAST:event_txtRfcEmpresaActionPerformed

    private void txtEmailEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailEmpActionPerformed
        txtDirEmpresa.requestFocus(true);
    }//GEN-LAST:event_txtEmailEmpActionPerformed

    private void txtDirEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirEmpresaActionPerformed
        txtCPEmp.requestFocus(true);
    }//GEN-LAST:event_txtDirEmpresaActionPerformed

    private void txtCPEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPEmpActionPerformed
        txtTelEmp.requestFocus(true);
    }//GEN-LAST:event_txtCPEmpActionPerformed

    private void txtTelEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelEmpActionPerformed
        cboEstados.requestFocus(true);
    }//GEN-LAST:event_txtTelEmpActionPerformed

    private void txtIvaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIvaEmpresaActionPerformed
        txtIvaGral.requestFocus();
    }//GEN-LAST:event_txtIvaEmpresaActionPerformed

    private void txtIvaGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIvaGralActionPerformed
        btnGuardarDatSist.requestFocus();
    }//GEN-LAST:event_txtIvaGralActionPerformed

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
            btnCancelarDatEmp.setEnabled(true);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarDatEmp;
    private javax.swing.JButton btnCancelarDatSist;
    private javax.swing.JButton btnGuardarCEmp;
    private javax.swing.JButton btnGuardarDatSist;
    private javax.swing.JButton btnModificarDatEmp;
    private javax.swing.JButton btnModificarDatSist;
    private javax.swing.JButton btnMostrarCli;
    private javax.swing.JButton btnSalirCli;
    private javax.swing.JComboBox cboEstados;
    private javax.swing.JComboBox cboMunicipio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblIdSucursal;
    private javax.swing.JLabel lblIdSucursal1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtCPEmp;
    private javax.swing.JTextField txtDirEmpresa;
    private javax.swing.JTextField txtEmailEmp;
    private javax.swing.JTextField txtIvaEmpresa;
    private javax.swing.JTextField txtIvaGral;
    private javax.swing.JTextField txtNombreEmpresa;
    private javax.swing.JTextField txtRfcEmpresa;
    private javax.swing.JTextField txtTelEmp;
    // End of variables declaration//GEN-END:variables
}