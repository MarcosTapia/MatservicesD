package vistas;

import beans.UsuarioBean;
import ComponenteConsulta.JDListaUsuario;
import beans.DatosEmpresaBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.security.MessageDigest;
import javax.swing.ImageIcon;
import util.Util;
import static vistas.Principal.sucursales;

/*
Inventario.-0
Alertas.-1
ConsultaVentas.-2
ConsultaCompras.-3
ConsultaMovimientos.-4
ConsultaPedidos.-5
Proveedores.-6
Usuarios.-7
Clientes.-8
Categorias.-9
Sucursales.-10
Sistema.-11
Mensajes.-12
Ventas.-13
Compras.-14
Caja.-15
*/

public class FrmUsuarios extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    //Fin WSUsuarios
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    String accion = "";

    public FrmUsuarios() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
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
        //se ocultan porque quedan incluidas en inventario
        actualizarBusqueda();
        activarBotones(true);
        //carga sucursales
        cargaSucursales();
        cboSucursal.addItem("");
        Iterator it = Principal.sucursalesHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboSucursal.addItem(Principal.sucursalesHM.get(key));
        }        
        habilitaChecksPermisos(false);
        cboSucursal.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.setIcon();
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    } 
    
    public void cargaSucursales() {
        //Carga proveedores
        sucursales = util.getMapSucursales();
        util.llenaMapSucursales(sucursales);
        Principal.sucursalesHM = util.getSucursalesHM();
    }
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void limpiarCajaTexto() {
        txtCodigoUsuario.setText("");
        txtUser.setText("");
        txtClave.setText("");
        txtNombre.setText("");
        txtApellidoMaterno.setText("");
        txtApellidoPaterno.setText("");
        txtTelCasa.setText("");
        txtTelCel.setText("");
        //limpia checkboxs
        chkInventario.setSelected(false);
        chkConsultaAlertas.setSelected(false);
        chkConsultaVentas.setSelected(false);
        chkConsultaCompras.setSelected(false);
        chkConsultaMovimientos.setSelected(false);
        chkConsultaPedidos.setSelected(false);
        chkProveedores.setSelected(false);
        chkUsuarios.setSelected(false);
        chkClientes.setSelected(false);
        chkCategorias.setSelected(false);
        chkSucursales.setSelected(false);
        chkSistema.setSelected(false);
        chkMensajes.setSelected(false);
        chkVentas.setSelected(false);
        chkCompras.setSelected(false);
        chkCaja.setSelected(false);
        //fin limpia checkboxs
        cboSucursal.setSelectedItem("");
    }

    public void activarCajaTexto(boolean b) {
        txtCodigoUsuario.setEditable(!b);
        txtUser.setEditable(b);
        txtClave.setEditable(b);
        txtNombre.setEditable(b);
        txtApellidoMaterno.setEditable(b);
        txtApellidoPaterno.setEditable(b);
        txtTelCasa.setEditable(b);
        txtTelCel.setEditable(b);
        cboSucursal.setEnabled(b);
    }

    public void activarBotones(boolean b) {
        btnNuevoPer.setEnabled(b);
        btnGuardarPer.setEnabled(!b);
        btnModificarPer.setEnabled(b);
        btnMostrarPer.setEnabled(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarUsuario = new javax.swing.JTextField();
        cboParametroUsuario = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoUsuario = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtClave = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        panelPermisos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        chkInventario = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        chkConsultaAlertas = new javax.swing.JCheckBox();
        chkConsultaVentas = new javax.swing.JCheckBox();
        chkConsultaCompras = new javax.swing.JCheckBox();
        chkConsultaMovimientos = new javax.swing.JCheckBox();
        chkConsultaPedidos = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        chkVentas = new javax.swing.JCheckBox();
        chkCompras = new javax.swing.JCheckBox();
        chkCaja = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        chkProveedores = new javax.swing.JCheckBox();
        chkUsuarios = new javax.swing.JCheckBox();
        chkClientes = new javax.swing.JCheckBox();
        chkCategorias = new javax.swing.JCheckBox();
        chkSucursales = new javax.swing.JCheckBox();
        chkSistema = new javax.swing.JCheckBox();
        chkMensajes = new javax.swing.JCheckBox();
        chkSeleccion = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtApellidoMaterno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelCasa = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        txtTelCel = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboSucursal = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btnNuevoPer = new javax.swing.JButton();
        btnGuardarPer = new javax.swing.JButton();
        btnModificarPer = new javax.swing.JButton();
        btnCancelarUsuario = new javax.swing.JButton();
        btnMostrarPer = new javax.swing.JButton();
        btnSalirPer = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setText("BUSCAR USUARIO :");

        txtBuscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarUsuarioKeyReleased(evt);
            }
        });

        cboParametroUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Nombre" }));
        cboParametroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroUsuarioActionPerformed(evt);
            }
        });

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroUsuario, 0, 75, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar datos del Personal"));

        jLabel3.setText("Codigo :");

        jLabel4.setText("Clave (*):");

        jLabel5.setText("Nombre (*):");

        jLabel6.setText("Usuario (*):");

        txtCodigoUsuario.setEditable(false);

        txtUser.setEditable(false);
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        txtClave.setEditable(false);
        txtClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveActionPerformed(evt);
            }
        });

        txtNombre.setEditable(false);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        panelPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Permisos de Usuario"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventario"));

        chkInventario.setText("Inventario");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 102, 102)));

        chkConsultaAlertas.setText("Alertas");

        chkConsultaVentas.setText("Ventas");

        chkConsultaCompras.setText("Compras");

        chkConsultaMovimientos.setText("Movimientos");

        chkConsultaPedidos.setText("Pedidos");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chkConsultaAlertas)
            .addComponent(chkConsultaVentas)
            .addComponent(chkConsultaCompras)
            .addComponent(chkConsultaMovimientos)
            .addComponent(chkConsultaPedidos)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(chkConsultaAlertas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConsultaVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkConsultaCompras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkConsultaMovimientos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConsultaPedidos)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkInventario)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkInventario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Operaciones"));

        chkVentas.setText("Ventas");

        chkCompras.setText("Compras");

        chkCaja.setText("Caja");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkVentas)
                    .addComponent(chkCompras)
                    .addComponent(chkCaja))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkCompras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkCaja)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));

        chkProveedores.setText("Proveedores");

        chkUsuarios.setText("Usuarios");

        chkClientes.setText("Clientes");

        chkCategorias.setText("Categorías");

        chkSucursales.setText("Sucursales");

        chkSistema.setText("Sistema");

        chkMensajes.setText("Mensajes");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkCategorias)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkProveedores)
                            .addComponent(chkUsuarios)
                            .addComponent(chkClientes))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkMensajes)
                            .addComponent(chkSistema)
                            .addComponent(chkSucursales))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkProveedores)
                    .addComponent(chkSucursales))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkUsuarios)
                    .addComponent(chkSistema))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkClientes)
                    .addComponent(chkMensajes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkCategorias)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chkSeleccion.setText(" Seleccionar/Deseleccionar Todo");
        chkSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSeleccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPermisosLayout = new javax.swing.GroupLayout(panelPermisos);
        panelPermisos.setLayout(panelPermisosLayout);
        panelPermisosLayout.setHorizontalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(chkSeleccion)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelPermisosLayout.setVerticalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkSeleccion)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel7.setText("Ap. Pat.:");

        txtApellidoPaterno.setEditable(false);
        txtApellidoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoPaternoActionPerformed(evt);
            }
        });

        jLabel8.setText("Ap. Mat.:");

        txtApellidoMaterno.setEditable(false);
        txtApellidoMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoMaternoActionPerformed(evt);
            }
        });

        jLabel9.setText("Tel. Casa:");

        txtTelCasa.setEditable(false);
        txtTelCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelCasaActionPerformed(evt);
            }
        });

        jTextField1.setText("Tel. Cel.:");

        txtTelCel.setEditable(false);
        txtTelCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelCelActionPerformed(evt);
            }
        });

        jLabel10.setText("Sucursal (*):");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidoPaterno)
                            .addComponent(cboSucursal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelCel)
                            .addComponent(txtApellidoMaterno)
                            .addComponent(txtTelCasa)
                            .addComponent(txtNombre)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoUsuario)
                            .addComponent(txtClave)
                            .addComponent(txtUser))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTelCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel2.setText("REGISTRAR USUARIOS");

        btnNuevoPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document.png"))); // NOI18N
        btnNuevoPer.setText("NUEVO");
        btnNuevoPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPerActionPerformed(evt);
            }
        });

        btnGuardarPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarPer.setText("GUARDAR");
        btnGuardarPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPerActionPerformed(evt);
            }
        });

        btnModificarPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarPer.setText("MODIFICAR");
        btnModificarPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPerActionPerformed(evt);
            }
        });

        btnCancelarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarUsuario.setText("CANCELAR");
        btnCancelarUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarUsuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUsuarioActionPerformed(evt);
            }
        });

        btnMostrarPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarPer.setText("MOSTRAR");
        btnMostrarPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPerActionPerformed(evt);
            }
        });

        btnSalirPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.jpg"))); // NOI18N
        btnSalirPer.setText("REGRESAR");
        btnSalirPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPerActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarUsuario.setText("ELIMINAR");
        btnEliminarUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarUsuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(78, 78, 78)
                                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnNuevoPer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarPer, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificarPer, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMostrarPer, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalirPer)))
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lblUsuario))
                .addGap(4, 4, 4)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardarPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalirPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPerActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSalirPerActionPerformed

    private void btnNuevoPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPerActionPerformed
        habilitaChecksPermisos(true);
        limpiarCajaTexto();
        activarCajaTexto(true);
//        obtenerUltimoId();
        accion = "Guardar";
        activarBotones(false);
        txtUser.requestFocus();
    }//GEN-LAST:event_btnNuevoPerActionPerformed

    private void btnModificarPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPerActionPerformed
        habilitaChecksPermisos(true);
        activarCajaTexto(true);
        accion = "Actualizar";
        btnNuevoPer.setEnabled(false);
        btnGuardarPer.setEnabled(true);
        btnModificarPer.setEnabled(false);
        btnCancelarUsuario.setEnabled(true);
        btnMostrarPer.setEnabled(false);
    }//GEN-LAST:event_btnModificarPerActionPerformed

    private void btnCancelarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarUsuarioActionPerformed
        limpiarCajaTexto();
        activarCajaTexto(false);
        activarBotones(true);
        habilitaChecksPermisos(false);
    }//GEN-LAST:event_btnCancelarUsuarioActionPerformed

    private void habilitaChecksPermisos(boolean habilita) {
        //Modulo Inventario
        chkInventario.setEnabled(habilita);
        //Modulo Consulta Alertas
        chkConsultaAlertas.setEnabled(habilita);
        //Modulo Consulta Ventas
        chkConsultaVentas.setEnabled(habilita);
        //Modulo Consulta Compras
        chkConsultaCompras.setEnabled(habilita);
        //Modulo Consulta Movimientos
        chkConsultaMovimientos.setEnabled(habilita);
        //Modulo Consulta Pedidos
        chkConsultaPedidos.setEnabled(habilita);
        //Modulo Proveedores
        chkProveedores.setEnabled(habilita);
        //Modulo Usuarios
        chkUsuarios.setEnabled(habilita);
        //Modulo Clientes
        chkClientes.setEnabled(habilita);
        //Modulo Categorias
        chkCategorias.setEnabled(habilita);
        //Modulo Sucursales
        chkSucursales.setEnabled(habilita);
        //Modulo Sistema
        chkSistema.setEnabled(habilita);
        //Modulo Mensajes
        chkMensajes.setEnabled(habilita);
        //Modulo Ventas
        chkVentas.setEnabled(habilita);
        //Modulo Compras
        chkCompras.setEnabled(habilita);
        //Modulo Caja Chica
        chkCaja.setEnabled(habilita);
        //Seleccion o deseleccion de todo
        chkSeleccion.setEnabled(habilita);
    }
    
    private void seleccionaCheckBoxes(){
        boolean habilita;
        if (chkSeleccion.isSelected()) {
            habilita = true;
        } else {
            habilita = false;
        }
        //Modulo Inventario
        chkInventario.setSelected(habilita);
        //Modulo Consulta Alertas
        chkConsultaAlertas.setSelected(habilita);
        //Modulo Consulta Ventas
        chkConsultaVentas.setSelected(habilita);
        //Modulo Consulta Compras
        chkConsultaCompras.setSelected(habilita);
        //Modulo Consulta Movimientos
        chkConsultaMovimientos.setSelected(habilita);
        //Modulo Consulta Pedidos
        chkConsultaPedidos.setSelected(habilita);
        //Modulo Proveedores
        chkProveedores.setSelected(habilita);
        //Modulo Usuarios
        chkUsuarios.setSelected(habilita);
        //Modulo Clientes
        chkClientes.setSelected(habilita);
        //Modulo Categorias
        chkCategorias.setSelected(habilita);
        //Modulo Sucursales
        chkSucursales.setSelected(habilita);
        //Modulo Sistema
        chkSistema.setSelected(habilita);
        //Modulo Mensajes
        chkMensajes.setSelected(habilita);
        //Modulo Ventas
        chkVentas.setSelected(habilita);
        //Modulo Compras
        chkCompras.setSelected(habilita);
        //Modulo Caja Chica
        chkCaja.setSelected(habilita);
    }
    
    private void guardar() {
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtUser.getText().compareTo("") != 0 
                    && txtNombre.getText().compareTo("") != 0
                    && cboSucursal.getSelectedIndex() != 0) {
                UsuarioBean p = new UsuarioBean();
                p.setUsuario(txtUser.getText());
                p.setPassword(getMD5(txtClave.getText()));
                p.setApellido_materno(txtApellidoMaterno.getText());
                p.setApellido_paterno(txtApellidoPaterno.getText());
                p.setTelefono_casa(txtTelCasa.getText());
                p.setTelefono_celular(txtTelCel.getText());

                //Reune permisos de usuario para guardarlos
                String permisos = "";
                //Modulo Inventario
                if (chkInventario.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Inventario
                //Modulo Consulta Alertas
                if (chkConsultaAlertas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Alertas
                //Modulo Consulta Ventas
                if (chkConsultaVentas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Ventas
                //Modulo Consulta Compras
                if (chkConsultaCompras.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Compras
                //Modulo Consulta Movimientos
                if (chkConsultaMovimientos.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Movimientos
                //Modulo Consulta Pedidos
                if (chkConsultaPedidos.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Pedidos
                //Modulo Proveedores
                if (chkProveedores.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Proveedores
                //Modulo Usuarios
                if (chkUsuarios.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Usuarios
                //Modulo Clientes
                if (chkClientes.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Clientes
                //Modulo Categorias
                if (chkCategorias.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Categorias
                //Modulo Sucursales
                if (chkSucursales.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Sucursales
                //Modulo Sistema
                if (chkSistema.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Sistema
                //Modulo Mensajes
                if (chkMensajes.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Mensajes
                //Modulo Ventas
                if (chkVentas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Ventas
                //Modulo Compras
                if (chkCompras.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Compras
                //Modulo Caja Chica
                if (chkCaja.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Caja Chica
                //Fin Reune permisos de usuario para guardarlos
                
                p.setClase(permisos);
                p.setPermisos(permisos);
                p.setNombre(txtNombre.getText());
                p.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , cboSucursal.getSelectedItem().toString()));
                hiloUsuarios = new WSUsuarios();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("GUARDAUSUARIO");
                UsuarioBean usuarioInsertado = hiloUsuarios
                        .ejecutaWebService(rutaWS,"3",p.getUsuario()
                        ,p.getPassword()
                        ,p.getPermisos()
                        ,p.getNombre()
                        ,p.getApellido_paterno()
                        ,p.getApellido_materno()
                        ,p.getTelefono_casa()
                        ,p.getTelefono_celular()
                        ,"" + p.getIdSucursal());
                if (usuarioInsertado != null) {
                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                    actualizarBusqueda();
                    activarBotones(true);
                    limpiarCajaTexto();
                    cboSucursal.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene Todos Los "
                        + "Campos..!!");
            }
        }
        if (accion.equalsIgnoreCase("Actualizar")) {
            if (txtCodigoUsuario.getText().compareTo("") != 0
                    && (txtUser.getText().compareTo("") != 0 
                    && txtNombre.getText().compareTo("") != 0
                    && cboSucursal.getSelectedIndex() != 0)) {
                UsuarioBean p = new UsuarioBean();
                p.setIdUsuario(Integer.parseInt(txtCodigoUsuario.getText()));
                p.setUsuario(txtUser.getText());
                //verifica si clave fue modificada
                if (txtClave.getText().equalsIgnoreCase("")) {                    
                    p.setPassword("");
                } else {
                    p.setPassword(getMD5(txtClave.getText()));
                }
                //verifica si clave fue modificada
                
                p.setApellido_paterno(txtApellidoPaterno.getText());
                p.setApellido_materno(txtApellidoMaterno.getText());
                p.setNombre(txtNombre.getText());
                p.setTelefono_casa(txtTelCasa.getText());
                p.setTelefono_celular(txtTelCel.getText());

                //Reune permisos de usuario para guardarlos
                String permisos = "";
                //Modulo Inventario
                if (chkInventario.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Inventario
                //Modulo Consulta Alertas
                if (chkConsultaAlertas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Alertas
                //Modulo Consulta Ventas
                if (chkConsultaVentas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Ventas
                //Modulo Consulta Compras
                if (chkConsultaCompras.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Compras
                //Modulo Consulta Movimientos
                if (chkConsultaMovimientos.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Movimientos
                //Modulo Consulta Pedidos
                if (chkConsultaPedidos.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Consulta Pedidos
                //Modulo Proveedores
                if (chkProveedores.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Proveedores
                //Modulo Usuarios
                if (chkUsuarios.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Usuarios
                //Modulo Clientes
                if (chkClientes.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Clientes
                //Modulo Categorias
                if (chkCategorias.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Categorias
                //Modulo Sucursales
                if (chkSucursales.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Sucursales
                //Modulo Sistema
                if (chkSistema.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Sistema
                //Modulo Mensajes
                if (chkMensajes.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Mensajes
                //Modulo Ventas
                if (chkVentas.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Ventas
                //Modulo Compras
                if (chkCompras.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Compras
                //Modulo Caja Chica
                if (chkCaja.isSelected()) {
                    permisos = permisos + "1";
                } else {
                    permisos = permisos + "0";
                }
                //Fin Modulo Caja Chica
                //Fin Reune permisos de usuario para guardarlos
                p.setClase(permisos);
                p.setPermisos(permisos);
                p.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , cboSucursal.getSelectedItem().toString()));
                hiloUsuarios = new WSUsuarios();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("MODIFICAUSUARIO");
                UsuarioBean usuarioModificar = hiloUsuarios
                        .ejecutaWebService(rutaWS,"4"
                        ,"" + p.getIdUsuario()
                        ,p.getUsuario()
                        ,p.getPassword()
                        ,p.getPermisos()
                        ,p.getNombre()
                        ,p.getApellido_paterno()
                        ,p.getApellido_materno()
                        ,p.getTelefono_casa()
                        ,p.getTelefono_celular()
                        ,"" + p.getIdSucursal());
                if (usuarioModificar != null) {
                    JOptionPane.showMessageDialog(null, " [ Datos "
                            + "Actualizados ]");
                    actualizarBusqueda();
                    activarBotones(true);
                    limpiarCajaTexto();
                    cboSucursal.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "[[ Debes seleccionar un "
                        + "registro para actualizar ]]");
                return;
            }
        }        
    }
    
    private void btnGuardarPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPerActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarPerActionPerformed

    private void cboParametroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroUsuarioActionPerformed
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroUsuarioActionPerformed

    private void txtBuscarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuarioKeyReleased
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarUsuarioKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        limpiarCajaTexto();
        ArrayList<UsuarioBean> resultWS = null;
        hiloUsuariosList = new WSUsuariosList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETUSUARIOBUSQUEDAID") 
                + String.valueOf(tblUsuarios.getModel()
                        .getValueAt(tblUsuarios.getSelectedRow(), 0)).trim();
        resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"2");
        UsuarioBean p = resultWS.get(0);
        txtCodigoUsuario.setText(""+p.getIdUsuario());
        txtUser.setText(p.getUsuario());
        //txtClave.setText(p.getPassword());
        txtNombre.setText(p.getNombre());
        txtApellidoMaterno.setText(p.getApellido_materno());
        txtApellidoPaterno.setText(p.getApellido_paterno());
        txtTelCasa.setText(p.getTelefono_casa());
        txtTelCel.setText(p.getTelefono_celular());

        //verifica permisos de usuario
        String permisos = p.getPermisos();
        //verifica permiso de modulo inventario
        if (permisos.charAt(0)=='1') {
            chkInventario.setSelected(true);
        } else {
            chkInventario.setSelected(false);
        }
        //fin verifica permiso de modulo inventario
        //verifica permiso de modulo consulta alertas
        if (permisos.charAt(1)=='1') {
            chkConsultaAlertas.setSelected(true);
        } else {
            chkConsultaAlertas.setSelected(false);
        }
        //fin verifica permiso de consulta alertas
        //verifica permiso de modulo consulta ventas
        if (permisos.charAt(2)=='1') {
            chkConsultaVentas.setSelected(true);
        } else {
            chkConsultaVentas.setSelected(false);
        }
        //fin verifica permiso de consulta ventas
        //verifica permiso de modulo consulta compras
        if (permisos.charAt(3)=='1') {
            chkConsultaCompras.setSelected(true);
        } else {
            chkConsultaCompras.setSelected(false);
        }
        //fin verifica permiso de consulta compras
        //verifica permiso de modulo consulta movimientos
        if (permisos.charAt(4)=='1') {
            chkConsultaMovimientos.setSelected(true);
        } else {
            chkConsultaMovimientos.setSelected(false);
        }
        //fin verifica permiso de modulo consulta movimientos
        //verifica permiso de modulo consulta pedidos
        if (permisos.charAt(5)=='1') {
            chkConsultaPedidos.setSelected(true);
        } else {
            chkConsultaPedidos.setSelected(false);
        }
        //fin verifica permiso de consulta pedidos
        //verifica permiso de modulo proveedores
        if (permisos.charAt(6)=='1') {
            chkProveedores.setSelected(true);
        } else {
            chkProveedores.setSelected(false);
        }
        //fin verifica permiso de proveedores
        //verifica permiso de modulo usuarios
        if (permisos.charAt(7)=='1') {
            chkUsuarios.setSelected(true);
        } else {
            chkUsuarios.setSelected(false);
        }
        //fin verifica permiso de usuarios
        //verifica permiso de modulo clientes
        if (permisos.charAt(8)=='1') {
            chkClientes.setSelected(true);
        } else {
            chkClientes.setSelected(false);
        }
        //fin verifica permiso de clientes
        //verifica permiso de modulo categorias
        if (permisos.charAt(9)=='1') {
            chkCategorias.setSelected(true);
        } else {
            chkCategorias.setSelected(false);
        }
        //fin verifica permiso de categorias
        //verifica permiso de modulo sucursales
        if (permisos.charAt(10)=='1') {
            chkSucursales.setSelected(true);
        } else {
            chkSucursales.setSelected(false);
        }
        //fin verifica permiso de modulo sucursales
        //verifica permiso de modulo sistema
        if (permisos.charAt(11)=='1') {
            chkSistema.setSelected(true);
        } else {
            chkSistema.setSelected(false);
        }
        //fin verifica permiso de modulo sistema
        //verifica permiso de modulo mensajes
        if (permisos.charAt(12)=='1') {
            chkMensajes.setSelected(true);
        } else {
            chkMensajes.setSelected(false);
        }
        //fin verifica permiso de modulo mensajes
        //verifica permiso de modulo ventas
        if (permisos.charAt(13)=='1') {
            chkVentas.setSelected(true);
        } else {
            chkVentas.setSelected(false);
        }
        //fin verifica permiso de modulo ventas
        //verifica permiso de modulo compras
        if (permisos.charAt(14)=='1') {
            chkCompras.setSelected(true);
        } else {
            chkCompras.setSelected(false);
        }
        //fin verifica permiso de modulo compras
        //verifica permiso de modulo caja chica
        if (permisos.charAt(15)=='1') {
            chkCaja.setSelected(true);
        } else {
            chkCaja.setSelected(false);
        }
        //fin verifica permiso de modulo caja chica
        //fin verifica permisos de usuario
        
        cboSucursal.setSelectedItem(util.buscaDescFromIdSuc(Principal.sucursalesHM
                , "" + p.getIdSucursal()));
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnMostrarPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPerActionPerformed
        JDListaUsuario jdListaP = new JDListaUsuario(this, true,Principal.sucursalesHM);
        jdListaP.setVisible(true);
    }//GEN-LAST:event_btnMostrarPerActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (txtCodigoUsuario.getText().compareTo("") != 0) {
                UsuarioBean p = new UsuarioBean();
                hiloUsuarios = new WSUsuarios();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("ELIMINAUSUARIO");
                UsuarioBean usuarioEliminar = hiloUsuarios
                        .ejecutaWebService(rutaWS,"5"
                        ,txtCodigoUsuario.getText().trim());
                if (usuarioEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro "
                            + "Eliminado ]");
                    limpiarCajaTexto();
                    actualizarBusqueda();
                    activarBotones(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona el usuario para "
                        + "eliminar");
            }
        }
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        txtClave.requestFocus();
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveActionPerformed
        txtApellidoPaterno.requestFocus();
    }//GEN-LAST:event_txtClaveActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        txtTelCasa.requestFocus();
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoPaternoActionPerformed
        txtApellidoMaterno.requestFocus();
    }//GEN-LAST:event_txtApellidoPaternoActionPerformed

    private void txtApellidoMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoMaternoActionPerformed
        txtNombre.requestFocus();
    }//GEN-LAST:event_txtApellidoMaternoActionPerformed

    private void txtTelCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelCasaActionPerformed
        txtTelCel.requestFocus();
    }//GEN-LAST:event_txtTelCasaActionPerformed

    private void txtTelCelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelCelActionPerformed
        cboSucursal.requestFocus();
    }//GEN-LAST:event_txtTelCelActionPerformed

    private void chkSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSeleccionActionPerformed
        seleccionaCheckBoxes();
    }//GEN-LAST:event_chkSeleccionActionPerformed

    private void actualizarBusqueda() {
        ArrayList<UsuarioBean> resultWS = null;
        if (String.valueOf(cboParametroUsuario.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarUsuario.getText().equalsIgnoreCase("")) {
                hiloUsuariosList = new WSUsuariosList();
                String rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETUSUARIOS");
                resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloUsuariosList = new WSUsuariosList();
                String rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETUSUARIOBUSQUEDANOMBRE")
                    + txtBuscarUsuario.getText().trim();
                resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"3");
            }
        } else {
            if (String.valueOf(cboParametroUsuario.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarUsuario.getText().equalsIgnoreCase("")) {
                    hiloUsuariosList = new WSUsuariosList();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETUSUARIOS");
                    resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloUsuariosList = new WSUsuariosList();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETUSUARIOBUSQUEDAID") 
                            + txtBuscarUsuario.getText().trim();
                    resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"2");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<UsuarioBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        String superUsuario = constantes.getProperty("SUPERUSUARIO");
        for (UsuarioBean p : list) {
            //filtra por sucursal
            if (!p.getUsuario().equalsIgnoreCase(superUsuario)) {
                datos[i][0] = p.getIdUsuario();
                datos[i][1] = p.getNombre() + " " + p.getApellido_paterno() 
                        + " " + p.getApellido_materno();
                i++;
            }            
        }
        Object[][] datosFinal = new Object[i][2];
        //Para filtrar los registros
        for (int j=0; j<i; j++) {
            if (datos[j][0]!=null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
            }
        }
        //Fin Para filtrar los registros
        
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
                new String[]{
                    "ID USUARIO", "NOMBRE"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmUsuarios().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarUsuario;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnGuardarPer;
    private javax.swing.JButton btnModificarPer;
    private javax.swing.JButton btnMostrarPer;
    private javax.swing.JButton btnNuevoPer;
    private javax.swing.JButton btnSalirPer;
    private javax.swing.JComboBox cboParametroUsuario;
    private javax.swing.JComboBox cboSucursal;
    private javax.swing.JCheckBox chkCaja;
    private javax.swing.JCheckBox chkCategorias;
    private javax.swing.JCheckBox chkClientes;
    private javax.swing.JCheckBox chkCompras;
    private javax.swing.JCheckBox chkConsultaAlertas;
    private javax.swing.JCheckBox chkConsultaCompras;
    private javax.swing.JCheckBox chkConsultaMovimientos;
    private javax.swing.JCheckBox chkConsultaPedidos;
    private javax.swing.JCheckBox chkConsultaVentas;
    private javax.swing.JCheckBox chkInventario;
    private javax.swing.JCheckBox chkMensajes;
    private javax.swing.JCheckBox chkProveedores;
    private javax.swing.JCheckBox chkSeleccion;
    private javax.swing.JCheckBox chkSistema;
    private javax.swing.JCheckBox chkSucursales;
    private javax.swing.JCheckBox chkUsuarios;
    private javax.swing.JCheckBox chkVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelPermisos;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtBuscarUsuario;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtCodigoUsuario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelCasa;
    private javax.swing.JTextField txtTelCel;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}