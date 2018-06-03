package vistas;

import beans.ProveedorBean;
import ComponenteConsulta.JDListaProveedor;
import beans.ClienteBean;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSProveedores;
import consumewebservices.WSProveedoresList;
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
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;

public class FrmProveedor extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSProveedoresList hiloProveedoresList;
    WSProveedores hiloProveedores;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    String accion = "";
    
    private int llamadoCompra;

    public int getLlamadoCompra() {
        return llamadoCompra;
    }

    public void setLlamadoCompra(int llamadoCompra) {
        this.llamadoCompra = llamadoCompra;
    }
    

    public FrmProveedor(int llamadoCompra) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setLlamadoCompra(llamadoCompra);
        initComponents();
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean configuracionBean = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        activarBotones(true);
        this.setTitle(configuracionBean.getNombreEmpresa());
        actualizarBusqueda();
        //carga estados
        Iterator it = Principal.estadosHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboEstados.addItem(Principal.estadosHM.get(key));
        }
        
        
        this.setLocationRelativeTo(null);
        cboEstados.setEnabled(false);
        cboMunicipio.setEnabled(false);
        btnNuevoProv.setEnabled(true);
        btnGuardarProv.setEnabled(false);
        //btnEliminarProv.setEnabled(true);
        //btnModificarProv.setEnabled(false);
        btnCancelarProv.setEnabled(true);
        lblIdProveedor.setText("");
        lblIdProveedor.setVisible(false);
//        JOptionPane.showMessageDialog(null, this.getLlamadoVenta());
        if (this.getLlamadoCompra() == 1) {
            btnNuevoProv.setVisible(true);
            btnGuardarProv.setEnabled(true);
            accion = "Guardar";
            btnModificarProv.setVisible(false);
            btnEliminarProv.setVisible(false);
        }
    }

    public void limpiarCajaTexto() {
        cboEstados.setSelectedIndex(0);
        cboMunicipio.setSelectedIndex(0);
        lblIdProveedor.setText("");
        txtEmpresa.setText("");
        txtRFC.setText("");
        txtNombreProv.setText("");
        txtApellidos.setText("");
        txtDireccion1.setText("");
        txtDireccion2.setText("");
        txtTelefonoCasa.setText("");
        txtTelefonoCelular.setText("");
        txtEmail.setText("");
        txtCP.setText("");
        txtNoCuenta.setText("");
        txtComentarios.setText("");
        lblIdProveedor.setText("");
    }

    public void activarCajaTexto(boolean b) {
        txtEmpresa.setEditable(b);
        txtRFC.setEditable(b);
        txtNombreProv.setEditable(b);
        txtApellidos.setEditable(b);
        txtDireccion1.setEditable(b);
        txtDireccion2.setEditable(b);
        txtTelefonoCasa.setEditable(b);
        txtTelefonoCelular.setEditable(b);
        txtEmail.setEditable(b);
        cboEstados.setEnabled(b);
        cboMunicipio.setEnabled(b);
        txtCP.setEditable(b);
        txtNoCuenta.setEditable(b);
        txtComentarios.setEditable(b);
        btnNuevoProv.setEnabled(false);
    }

    public void activarBotones(boolean b) {
        btnNuevoProv.setEnabled(b);
        btnGuardarProv.setEnabled(!b);
        //btnEliminarCli.setEnabled(b);
        //btnModificarCli.setEnabled(!b);
        btnCancelarProv.setEnabled(!b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarProv = new javax.swing.JTextField();
        cboParametroProv = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProveedor = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNombreProv = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDireccion2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTelefonoCasa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefonoCelular = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cboEstados = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        cboMunicipio = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        txtCP = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtComentarios = new javax.swing.JTextField();
        lblIdProveedor = new javax.swing.JLabel();
        btnNuevoProv = new javax.swing.JButton();
        btnGuardarProv = new javax.swing.JButton();
        btnModificarProv = new javax.swing.JButton();
        btnCancelarProv = new javax.swing.JButton();
        btnMostrarProv = new javax.swing.JButton();
        btnSalirProv = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnEliminarProv = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel1.setText("BUSCAR PROVEEDOR");

        txtBuscarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProvActionPerformed(evt);
            }
        });
        txtBuscarProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProvKeyReleased(evt);
            }
        });

        cboParametroProv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Id" }));
        cboParametroProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroProvActionPerformed(evt);
            }
        });

        jtProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "RFC", "Nombre"
            }
        ));
        jtProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProveedorMouseClicked(evt);
            }
        });
        jtProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProveedorKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtProveedor);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtBuscarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboParametroProv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Proveedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(247, 254, 255));

        jLabel2.setText("Empresa :");

        txtEmpresa.setEditable(false);
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });

        jLabel6.setText("RFC :");

        txtRFC.setEditable(false);
        txtRFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRFCActionPerformed(evt);
            }
        });
        txtRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRFCKeyTyped(evt);
            }
        });

        jLabel7.setText("Nombre (*):");

        txtNombreProv.setEditable(false);
        txtNombreProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProvActionPerformed(evt);
            }
        });

        jLabel5.setText("Apellidos (*):");

        txtApellidos.setEditable(false);
        txtApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidosActionPerformed(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        jLabel8.setText("Dirección 1 :");

        txtDireccion1.setEditable(false);
        txtDireccion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion1ActionPerformed(evt);
            }
        });

        jLabel11.setText("Direccion 2 :");

        txtDireccion2.setEditable(false);
        txtDireccion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Teléfono Casa :");

        txtTelefonoCasa.setEditable(false);
        txtTelefonoCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoCasaActionPerformed(evt);
            }
        });
        txtTelefonoCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoCasaKeyTyped(evt);
            }
        });

        jLabel9.setText("Teléfono Celular : ");

        txtTelefonoCelular.setEditable(false);
        txtTelefonoCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoCelularActionPerformed(evt);
            }
        });
        txtTelefonoCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoCelularKeyTyped(evt);
            }
        });

        jLabel10.setText("Email :");

        txtEmail.setEditable(false);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel14.setText("Estado :");

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

        jLabel15.setText("Municipio :");

        cboMunicipio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMunicipioActionPerformed(evt);
            }
        });

        jLabel16.setText("CP :");

        txtCP.setEditable(false);
        txtCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPActionPerformed(evt);
            }
        });

        jLabel17.setText("No. Cuenta :");

        txtNoCuenta.setEditable(false);
        txtNoCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoCuentaActionPerformed(evt);
            }
        });

        jLabel18.setText("Comentarios :");

        txtComentarios.setEditable(false);
        txtComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComentariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addGap(14, 14, 14)
                                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(78, 78, 78)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(63, 63, 63)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtComentarios))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccion2))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIdProveedor)
                .addGap(61, 61, 61))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lblIdProveedor)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnNuevoProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document.png"))); // NOI18N
        btnNuevoProv.setText("NUEVO");
        btnNuevoProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProvActionPerformed(evt);
            }
        });

        btnGuardarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarProv.setText("GUARDAR");
        btnGuardarProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProvActionPerformed(evt);
            }
        });

        btnModificarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarProv.setText("MODIFICAR");
        btnModificarProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProvActionPerformed(evt);
            }
        });

        btnCancelarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarProv.setText("CANCELAR");
        btnCancelarProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProvActionPerformed(evt);
            }
        });

        btnMostrarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarProv.setText("MOSTRAR");
        btnMostrarProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarProvActionPerformed(evt);
            }
        });

        btnSalirProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirProv.setText("SALIR");
        btnSalirProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirProvActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel13.setText("REGISTRAR PROVEEDOR");

        btnEliminarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarProv.setText("ELIMINAR");
        btnEliminarProv.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarProv.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProvActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNuevoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarProv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarProv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarProv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarProv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirProv, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(196, 196, 196)
                        .addComponent(lblUsuario))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoProv, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(btnSalirProv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProvActionPerformed
        this.setLlamadoCompra(0);
        this.dispose();
//        FrmConfiguracion operaciones = new FrmConfiguracion();
    }//GEN-LAST:event_btnSalirProvActionPerformed

    private void btnNuevoProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProvActionPerformed
        limpiarCajaTexto();
        activarCajaTexto(true);
        activarBotones(false);
        accion = "Guardar";
        txtEmpresa.requestFocus();
    }//GEN-LAST:event_btnNuevoProvActionPerformed

    private void cboParametroProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroProvActionPerformed
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroProvActionPerformed

    private void txtBuscarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProvActionPerformed
    }//GEN-LAST:event_txtBuscarProvActionPerformed

    private void txtBuscarProvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProvKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarProvKeyReleased

    private void btnGuardarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProvActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtNombreProv.getText().compareTo("") != 0 
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtRFC.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtApellidos.getText().compareTo("") != 0
                    && txtDireccion1.getText().compareTo("") != 0
                    && txtTelefonoCasa.getText().compareTo("") != 0
                    && !cboEstados.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && !cboMunicipio.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && txtEmpresa.getText().compareTo("") != 0
                        ) {
                    ProveedorBean prov = new ProveedorBean();
                    prov.setEmpresa(txtEmpresa.getText());
                    prov.setRfc(txtRFC.getText());
                    prov.setNombre(txtNombreProv.getText());
                    prov.setApellidos(txtApellidos.getText());
                    prov.setDireccion1(txtDireccion1.getText());
                    prov.setDireccion2(txtDireccion2.getText());
                    prov.setTelefono_casa(txtTelefonoCasa.getText());
                    prov.setTelefono_celular(txtTelefonoCelular.getText());
                    prov.setEmail(txtEmail.getText());
                    int edo = util.buscaIdEdo(Principal.estadosHM
                            , cboEstados.getSelectedItem().toString());
                    int mun = util.buscaIdMun(Principal.municipiosHM
                            , cboMunicipio.getSelectedItem().toString());
                    prov.setEstado("" + edo);
                    prov.setCiudad("" + mun);
                    prov.setCp(txtCP.getText());
                    prov.setPais("Mx");
                    prov.setNoCuenta(txtNoCuenta.getText());
                    prov.setComentarios(txtComentarios.getText());
                    //huardar producto
                    hiloProveedores = new WSProveedores();
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GUARDAPROVEEDOR");
                    ProveedorBean proveedorInsertado = hiloProveedores.ejecutaWebService(rutaWS,"1"
                            ,prov.getEmpresa()
                            ,prov.getNombre()
                            ,prov.getApellidos()
                            ,prov.getTelefono_casa()
                            ,prov.getTelefono_celular()
                            ,prov.getDireccion1()
                            ,prov.getDireccion2()
                            ,prov.getRfc()
                            ,prov.getEmail()
                            ,prov.getCiudad()
                            ,prov.getEstado()
                            ,prov.getCp()
                            ,prov.getPais()
                            ,prov.getComentarios()
                            ,prov.getNoCuenta()
                            );
                    if (proveedorInsertado != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                        actualizarBusqueda();
                        limpiarCajaTexto();
                        activarCajaTexto(false);
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
            if (txtNombreProv.getText().compareTo("") != 0 
                    && lblIdProveedor.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0 
                    && txtRFC.getText().compareTo("") != 0
                    && lblIdProveedor.getText().compareTo("") != 0
                    && txtEmpresa.getText().compareTo("") != 0
                    && txtApellidos.getText().compareTo("") != 0
                    && txtDireccion1.getText().compareTo("") != 0
                    && txtTelefonoCasa.getText().compareTo("") != 0
                    && !cboEstados.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && !cboMunicipio.getSelectedItem().toString().
                    equalsIgnoreCase("Seleccionar...")
                    && txtEmpresa.getText().compareTo("") != 0
                        ) {
                    ProveedorBean prov = new ProveedorBean();
                    prov.setIdProveedor(Integer.parseInt(lblIdProveedor.getText()));
                    prov.setEmpresa(txtEmpresa.getText());
                    prov.setRfc(txtRFC.getText());
                    prov.setNombre(txtNombreProv.getText());
                    prov.setApellidos(txtApellidos.getText());
                    prov.setDireccion1(txtDireccion1.getText());
                    prov.setDireccion2(txtDireccion2.getText());
                    prov.setTelefono_casa(txtTelefonoCasa.getText());
                    prov.setTelefono_celular(txtTelefonoCelular.getText());
                    prov.setEmail(txtEmail.getText());
                    int edo = util.buscaIdEdo(Principal.estadosHM
                            , cboEstados.getSelectedItem().toString());
                    int mun = util.buscaIdMun(Principal.municipiosHM
                            , cboMunicipio.getSelectedItem().toString());
                    prov.setEstado("" + edo);
                    prov.setCiudad("" + mun);
                    prov.setCp(txtCP.getText());
                    prov.setPais("Mx");
                    prov.setNoCuenta(txtNoCuenta.getText());
                    prov.setComentarios(txtComentarios.getText());
                    //huardar producto
                    hiloProveedores = new WSProveedores();
                    String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICAPROVEEDOR");
                    ProveedorBean proveedorActualizado = hiloProveedores.ejecutaWebService(rutaWS,"2"
                            ,String.valueOf(prov.getIdProveedor())
                            ,prov.getEmpresa()
                            ,prov.getNombre()
                            ,prov.getApellidos()
                            ,prov.getTelefono_casa()
                            ,prov.getTelefono_celular()
                            ,prov.getDireccion1()
                            ,prov.getDireccion2()
                            ,prov.getRfc()
                            ,prov.getEmail()
                            ,prov.getCiudad()
                            ,prov.getEstado()
                            ,prov.getCp()
                            ,prov.getPais()
                            ,prov.getComentarios()
                            ,prov.getNoCuenta()
                            );
                    if (proveedorActualizado != null) {
                        JOptionPane.showMessageDialog(null, "[ Datos Actualizados ]");
                        actualizarBusqueda();
                        limpiarCajaTexto();
                        activarCajaTexto(false);
                        activarBotones(true);
                        jtProveedor.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Error al actualizar el registro");
                    }    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Llena los campos requeridos!!");
            }    
        }  
        btnNuevoProv.setEnabled(true);
    }//GEN-LAST:event_btnGuardarProvActionPerformed

    private void btnModificarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProvActionPerformed
        if (lblIdProveedor.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
            return;
        }
        jtProveedor.setEnabled(false);
        accion = "Actualizar";
        activarCajaTexto(true);
        btnNuevoProv.setEnabled(false);
        btnGuardarProv.setEnabled(true);
//        btnModificarProv.setEnabled(false);
//        btnCancelarProv.setEnabled(true);
//        btnMostrarProv.setEnabled(false);
    }//GEN-LAST:event_btnModificarProvActionPerformed

    private void btnCancelarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProvActionPerformed
        limpiarCajaTexto();
        activarCajaTexto(false);
        activarBotones(true);
        btnCancelarProv.setEnabled(true);
    }//GEN-LAST:event_btnCancelarProvActionPerformed

    private void buscaProveedorFromJTable() {
        lblIdProveedor.setText(jtProveedor.getModel().getValueAt(
            jtProveedor.getSelectedRow(),0).toString());
        ArrayList<ProveedorBean> resultWS = null;
        hiloProveedoresList = new WSProveedoresList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETPROVEEDORPORID")
                + String.valueOf(jtProveedor.getModel().getValueAt(jtProveedor.getSelectedRow(), 0)).trim();
        resultWS = hiloProveedoresList.ejecutaWebService(rutaWS,"4");
        ProveedorBean prov = resultWS.get(0);
        txtEmpresa.setText(prov.getEmpresa());
        txtApellidos.setText(prov.getApellidos());
        txtRFC.setText(prov.getRfc());
        txtNombreProv.setText(prov.getNombre());
        txtDireccion1.setText(prov.getDireccion1());
        txtDireccion2.setText(prov.getDireccion2());
        txtTelefonoCasa.setText(prov.getTelefono_casa());
        txtTelefonoCelular.setText(prov.getTelefono_celular());
        txtEmail.setText(prov.getEmail());
        txtCP.setText(prov.getCp());
        txtNoCuenta.setText(prov.getNoCuenta());
        txtComentarios.setText(prov.getComentarios());
        String edo = "";
        String munic = "";
        edo = util.buscaDescFromIdEdo(Principal.estadosHM, 
                prov.getEstado().toString());
        if ("".equalsIgnoreCase(edo)) {
            cboEstados.setSelectedItem("Seleccionar...");
        } else {
            cboEstados.setSelectedItem(edo);
        }
        munic = util.buscaDescFromIdMun(Principal.municipiosHM, 
                prov.getCiudad());
        if ("".equalsIgnoreCase(munic)) {
            cboMunicipio.setSelectedItem("Seleccionar...");
        } else {
            cboMunicipio.setSelectedItem(munic);
        }
        jtProveedor.requestFocus(true);
    }
    
    private void jtProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProveedorMouseClicked
        buscaProveedorFromJTable();
    }//GEN-LAST:event_jtProveedorMouseClicked

    private void btnMostrarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarProvActionPerformed
        JDListaProveedor jdListaP = new JDListaProveedor(this,true
                ,Principal.municipiosHM,Principal.estadosHM);
        jdListaP.setVisible(true);
    }//GEN-LAST:event_btnMostrarProvActionPerformed

    private void eliminarProveedor() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (lblIdProveedor.getText().compareTo("") != 0) {
                hiloProveedores = new WSProveedores();
                String rutaWS = constantes.getProperty("IP") + constantes.getProperty("ELIMINAPROVEEDOR");
                ProveedorBean proveedorEliminar = hiloProveedores.ejecutaWebService(rutaWS,"3"
                        ,lblIdProveedor.getText().trim());
                if (proveedorEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                    //Carga productos
                    actualizarBusqueda();
                    limpiarCajaTexto();
                    activarCajaTexto(false);
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible "
                            + "eliminar el "
                            + "proveedor existen movimientos que lo relacionan"
                            , JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                        "No hay Proveedor seleccionado");
            }
            btnCancelarProv.setEnabled(true);
        }
    }
    
    private void btnEliminarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProvActionPerformed
        eliminarProveedor();
    }//GEN-LAST:event_btnEliminarProvActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        txtRFC.requestFocus(true);
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void txtRFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRFCActionPerformed
        txtNombreProv.requestFocus();
    }//GEN-LAST:event_txtRFCActionPerformed

    private void txtRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFCKeyTyped
        //        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            //            Toolkit.getDefaultToolkit().beep();
            //            evt.consume();
            //        }
    }//GEN-LAST:event_txtRFCKeyTyped

    private void txtNombreProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProvActionPerformed
        txtApellidos.requestFocus();
    }//GEN-LAST:event_txtNombreProvActionPerformed

    private void txtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidosActionPerformed
        txtDireccion1.requestFocus();
    }//GEN-LAST:event_txtApellidosActionPerformed

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        // TODO add your handling code here:
        //        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            //            Toolkit.getDefaultToolkit().beep();
            //            evt.consume();
            //        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtDireccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion1ActionPerformed
        txtDireccion2.requestFocus();
    }//GEN-LAST:event_txtDireccion1ActionPerformed

    private void txtDireccion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion2ActionPerformed
        txtTelefonoCasa.requestFocus();
    }//GEN-LAST:event_txtDireccion2ActionPerformed

    private void txtTelefonoCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoCasaActionPerformed
        txtTelefonoCelular.requestFocus();
    }//GEN-LAST:event_txtTelefonoCasaActionPerformed

    private void txtTelefonoCasaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoCasaKeyTyped
        // TODO add your handling code here:
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoCasaKeyTyped

    private void txtTelefonoCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoCelularActionPerformed
        txtEmail.requestFocus();
    }//GEN-LAST:event_txtTelefonoCelularActionPerformed

    private void txtTelefonoCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoCelularKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoCelularKeyTyped

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        cboEstados.requestFocus();
    }//GEN-LAST:event_txtEmailActionPerformed

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
        txtCP.requestFocus(true);
    }//GEN-LAST:event_cboMunicipioActionPerformed

    private void txtCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPActionPerformed
        txtNoCuenta.requestFocus();
    }//GEN-LAST:event_txtCPActionPerformed

    private void txtNoCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoCuentaActionPerformed
        txtComentarios.requestFocus();
    }//GEN-LAST:event_txtNoCuentaActionPerformed

    private void txtComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComentariosActionPerformed
        btnGuardarProv.requestFocus();
    }//GEN-LAST:event_txtComentariosActionPerformed

    private void jtProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProveedorKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaProveedorFromJTable();
        }
    }//GEN-LAST:event_jtProveedorKeyReleased

    private void actualizarBusqueda() {
        ArrayList<ProveedorBean> resultWS = null;
        String rutaWS = "";
        if (String.valueOf(cboParametroProv.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarProv.getText().equalsIgnoreCase("")) {
                hiloProveedoresList = new WSProveedoresList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETPROVEEDORES");
                resultWS = hiloProveedoresList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloProveedoresList = new WSProveedoresList();
                rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETPROVEEDORBUSQUEDANOMBRE")
                    + txtBuscarProv.getText().trim();
                resultWS = hiloProveedoresList.ejecutaWebService(rutaWS,"2");
            }
        } else {
            if (String.valueOf(cboParametroProv.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarProv.getText().equalsIgnoreCase("")) {
                    hiloProveedoresList = new WSProveedoresList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETPROVEEDORES");
                    resultWS = hiloProveedoresList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloProveedoresList = new WSProveedoresList();
                    rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETPROVEEDORBUSQUEDAID") 
                            + txtBuscarProv.getText().trim();
                    resultWS = hiloProveedoresList.ejecutaWebService(rutaWS,"3");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<ProveedorBean> list) {
        Object[][] datos = new Object[list.size()][3];
        int i = 0;
        for (ProveedorBean pv : list) {
            datos[i][0] = pv.getIdProveedor();
            datos[i][1] = pv.getRfc();
            datos[i][2] = pv.getNombre() + " " 
                    + pv.getApellidos();
            i++;
        }
        jtProveedor.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "CODIGO", "RFC", "NOMBRE"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarProv;
    private javax.swing.JButton btnEliminarProv;
    private javax.swing.JButton btnGuardarProv;
    private javax.swing.JButton btnModificarProv;
    private javax.swing.JButton btnMostrarProv;
    private javax.swing.JButton btnNuevoProv;
    private javax.swing.JButton btnSalirProv;
    private javax.swing.JComboBox cboEstados;
    private javax.swing.JComboBox cboMunicipio;
    private javax.swing.JComboBox cboParametroProv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtProveedor;
    private javax.swing.JLabel lblIdProveedor;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarProv;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtDireccion2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNombreProv;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtTelefonoCasa;
    private javax.swing.JTextField txtTelefonoCelular;
    // End of variables declaration//GEN-END:variables
}