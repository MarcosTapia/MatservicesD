package vistas;

import beans.ProductoBean;
import ComponenteConsulta.JDListaProducto;
import ComponenteDatos.*;
//import ComponenteReportes.ReporteProductoParametro;
import ComponenteDatos.ConfiguracionDAO;
import beans.CategoriaBean;
import beans.DatosEmpresaBean;
import beans.FechaServidorBean;
import beans.ProductosProveedoresCostosBean;
import beans.ProveedorBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import util.Util;

public class FrmProducto extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WS
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    //Fin WS
    
    
    
    String codProdAnterior = "";
    FechaServidorBean fechaServidorBean;

    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    int codigoProveedor;
    DatosEmpresaBean datosEmpresaBean;
    ConfiguracionDAO configuracionDAO;
    ProductosProveedoresCostosBean productosProveedoresCostosBean;
    BDProductosProveedoresCostos bdProductosProveedoresCostos;
    HashMap<Integer, String> NombreProveedor = new HashMap<Integer, String>();
    HashMap<String, String> NombreProducto = new HashMap<String, String>();
    HashMap<String, String> utilidadCategoriaHMap = new HashMap<>();
    HashMap<Integer, String> nombreUsuarios = new HashMap<Integer, String>();
    
    //cantidad global
    int cantGlobal = 0;

    public FrmProducto() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        buttonGroup1.add(radioAumentar);
        buttonGroup1.add(radioDisminuir);
        panTipoOperacion.setVisible(false);
        
        obtenerUltimoId();
        // Actualizas tbl proveedor
        actualizarBusquedaProveedor();
        
        // Actualizas tbl producto
        ArrayList<ProductoBean> resultWS = null;
        hiloInventariosList = new WSInventariosList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETINVENTARIOS");
        resultWS = hiloInventariosList.ejecutaWebService(rutaWS,"1");
        recargarTableProductos(resultWS);
        
        //carga categorias
        Iterator it = Principal.categoriasHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboCategoriaPro.addItem(Principal.categoriasHM.get(key));
        }        
        
        //carga proveedores
        it = Principal.proveedoresHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboProveedor.addItem(Principal.proveedoresHM.get(key));
        }        
        
        //carga sucursales
        it = Principal.sucursalesHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboSucursal.addItem(Principal.sucursalesHM.get(key));
        }        
        
        //Carga iva de la empresa de ganancia por producto
        txtIva.setText("" + Principal.datosSistemaBean.getIvaEmpresa());
//        //Carga HashMap de Provedores
//        try {
//            NombreProveedor = BDProveedor.mostrarProveedorHashMap();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        
//        //Carga HashMap de Usuarios
//        try {
//            nombreUsuarios = BDUsuario.mostrarUsuariosHashMap();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        
//        activarBotones(true);
//        // Para cargar la lista de Categorias al combobox
//        // y carga hashmap de precios
//        try {
//            Connection con = BD.getConnection();
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select cCatDescripcion,utilidad "
//                    + "from categoria");
//            while (rs.next()) {
//                cboCategoriaPro.addItem(rs.getObject(1));
//                utilidadCategoriaHMap.put(""+rs.getObject(1),""+rs.getObject(2));
//            }
//            rs.close();
//            stmt.close();
//            con.close();
//        } catch (SQLException error) {
//            JOptionPane.showMessageDialog(null, error.getMessage());
//        }
//        this.setLocationRelativeTo(null);
//        datosEmpresaBean = new DatosEmpresaBean();
//        configuracionDAO = new ConfiguracionDAO();
//        datosEmpresaBean = configuracionDAO.obtieneConfiguracion(1);
//        //txtUtilidadPro.setText(""+datosEmpresaBean.getUtilidad());
//        //txtIVA.setText(""+datosEmpresaBean.getIva());
//        this.setTitle("Inventario");
//        txtUtilidad.setText("" + datosEmpresaBean.getUtilidad());
//        
//        /* Se cambio por hora del servidor
//        jDateChooserFechaPro.setDate(new Date());
//        */
////        //muestra fecha servidor comentado porque es hora local
////        try {
////            if (BDFechaServidor.actualizarFecha()) {
////                fechaServidorBean = BDFechaServidor.mostrarFechaServidor();
////                jDateChooserFechaPro.setDateFormatString("d/MM/yyyy");
////                jDateChooserFechaPro.setDate(fechaServidorBean.getFechaServidor());
////            }
////        } catch (SQLException ex) {
////            JOptionPane.showMessageDialog(null, ex.getMessage());
////        }
////        //Fin muestra fecha servidor comentado porque es hora local
//        
////        java.util.Date fechaLocal = new Date();
//        //String a = DateFormat.getDateInstance(DateFormat.LONG).format(fechaLocal);        
////        jDateChooserFechaPro.setDateFormatString("dd/MM/yyyy");
////        jDateChooserFechaPro.setDate(fechaLocal);
//        
//        
////        lblCodProv.setVisible(false);
//        txtCodigoPro.requestFocus();
////        txtProovedorDescrip.setEnabled(false);
//        codProdAnterior = "";
    }

    public void obtenerUltimoId() {
//        try {
//            Connection con = BD.getConnection();
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM productos");
//            int lastID = 0;
//            while (rs.next()) {
//                lastID = rs.getInt(1);
//                boolean sigueBuscando = true;
//                while (sigueBuscando) {
//                    ProductoBean pt = BDProducto.buscarProducto("" + (lastID + 1));
//                    if (pt == null) {
//                        txtCodigoPro.setText(String.valueOf(lastID + 1));
//                        sigueBuscando = false;
//                        break;
//                    } else {
//                        lastID = (int) (Math.random() * 1000) + 1;
//                    }
//                }
//            }
//            rs.close();
//            stmt.close();
//            con.close();
//        } catch (SQLException error) {
//            JOptionPane.showMessageDialog(null, error.getMessage());
//        }
    }

    public void limpiarCajaTexto() {
        cboCategoriaPro.setSelectedItem("Seleccionar...");
        cboProveedor.setSelectedItem("Seleccionar...");
        cboSucursal.setSelectedItem("Seleccionar...");
        txtCodigoPro.setText("");
        txtDescripcionPro.setText("");
        txtCantidadPro.setText("");
        txtMinimoPro.setText("0");
        txtIva.setText("" + Principal.datosSistemaBean.getIvaEmpresa());
        txtUbicacion.setText("");
        txtPrecioCosto.setText("");
        txtPrecioPublico.setText("");
        txtUtilidad.setText("");
        
        txtBuscarPro.setText("");
        
        codProdAnterior = "";
    }

    public void activarCajaTexto(boolean b) {
        txtCantidadPro.setEditable(b);
        txtDescripcionPro.setEditable(b);
    }

    public void activarBotones(boolean b) {
        btnNuevoPro.setEnabled(b);
        btnGuardarPro.setEnabled(!b);
        btnModificarPro.setEnabled(b);
        //btnCancelarPro.setEnabled(!b);
        btnMostrarPro.setEnabled(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtBuscarPro = new javax.swing.JTextField();
        cboParametroPro = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtProducto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidadPro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboCategoriaPro = new javax.swing.JComboBox();
        txtDescripcionPro = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMinimoPro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboProveedor = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioCosto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioPublico = new javax.swing.JTextField();
        panTipoOperacion = new javax.swing.JPanel();
        radioAumentar = new javax.swing.JRadioButton();
        radioDisminuir = new javax.swing.JRadioButton();
        existOriginal = new javax.swing.JLabel();
        txtUtilidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUbicacion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboSucursal = new javax.swing.JComboBox();
        btnNuevoPro = new javax.swing.JButton();
        btnGuardarPro = new javax.swing.JButton();
        btnModificarPro = new javax.swing.JButton();
        btnCancelarPro = new javax.swing.JButton();
        btnMostrarPro = new javax.swing.JButton();
        btnSalirPro = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnEliminarPro = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel17.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel17.setText("BUSCAR PRODUCTO ");

        txtBuscarPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProKeyReleased(evt);
            }
        });

        cboParametroPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Nombre", "Sucursal" }));
        cboParametroPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroProActionPerformed(evt);
            }
        });

        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
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
                "Codigo", "Descripción"
            }
        ));
        jtProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductoMouseClicked(evt);
            }
        });
        jtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtProductoKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtProducto);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroPro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, 0, 458, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Caracteristicas del Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setText("Codigo :");

        txtCodigoPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });
        txtCodigoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoProKeyReleased(evt);
            }
        });

        jLabel6.setText("Existencia :");

        txtCantidadPro.setEditable(false);
        txtCantidadPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadProActionPerformed(evt);
            }
        });
        txtCantidadPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProKeyTyped(evt);
            }
        });

        jLabel7.setText("Descripcion :");

        jLabel13.setText("Categoria :");

        cboCategoriaPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboCategoriaPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCategoriaProItemStateChanged(evt);
            }
        });
        cboCategoriaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaProActionPerformed(evt);
            }
        });
        cboCategoriaPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboCategoriaProKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboCategoriaProKeyTyped(evt);
            }
        });

        txtDescripcionPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProActionPerformed(evt);
            }
        });

        jLabel15.setText("Mínimo :");

        txtMinimoPro.setText("0");
        txtMinimoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMinimoProActionPerformed(evt);
            }
        });
        txtMinimoPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinimoProFocusLost(evt);
            }
        });
        txtMinimoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMinimoProKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMinimoProKeyTyped(evt);
            }
        });

        jLabel1.setText("Proveedor");

        cboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProveedorActionPerformed(evt);
            }
        });
        cboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboProveedorKeyTyped(evt);
            }
        });

        jLabel3.setText("Precio Costo:");

        txtPrecioCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioCostoActionPerformed(evt);
            }
        });

        jLabel5.setText("Precio Público:");

        txtPrecioPublico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioPublicoActionPerformed(evt);
            }
        });

        panTipoOperacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Operación"));

        radioAumentar.setSelected(true);
        radioAumentar.setText("Aumentar cantidad al Inventario");

        radioDisminuir.setText("Disminuir cantidad al Inventario");

        existOriginal.setBackground(new java.awt.Color(255, 153, 153));
        existOriginal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        existOriginal.setText("Existencia Original: ");
        existOriginal.setOpaque(true);

        javax.swing.GroupLayout panTipoOperacionLayout = new javax.swing.GroupLayout(panTipoOperacion);
        panTipoOperacion.setLayout(panTipoOperacionLayout);
        panTipoOperacionLayout.setHorizontalGroup(
            panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoOperacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioAumentar)
                    .addComponent(radioDisminuir)
                    .addComponent(existOriginal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panTipoOperacionLayout.setVerticalGroup(
            panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoOperacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radioAumentar)
                .addGap(18, 18, 18)
                .addComponent(radioDisminuir)
                .addGap(37, 37, 37)
                .addComponent(existOriginal))
        );

        txtUtilidad.setEditable(false);
        txtUtilidad.setBackground(new java.awt.Color(255, 153, 102));
        txtUtilidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel4.setText("Utilidad:");

        jLabel8.setText("IVA :");

        jLabel9.setText("Ubicación : ");

        jLabel10.setText("Sucursal : ");

        cboSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSucursal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioPublico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMinimoPro))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(5, 5, 5)
                        .addComponent(cboCategoriaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(txtDescripcionPro))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(panTipoOperacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtMinimoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cboCategoriaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioPublico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(panTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document.png"))); // NOI18N
        btnNuevoPro.setText("NUEVO");
        btnNuevoPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarPro.setText("GUARDAR");
        btnGuardarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });

        btnModificarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarPro.setText("MODIFICAR");
        btnModificarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProActionPerformed(evt);
            }
        });

        btnCancelarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarPro.setText("CANCELAR");
        btnCancelarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProActionPerformed(evt);
            }
        });

        btnMostrarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarPro.setText("MOSTRAR");
        btnMostrarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarProActionPerformed(evt);
            }
        });

        btnSalirPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirPro.setText("CERRAR");
        btnSalirPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirProActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel11.setText("DETALLE PRODUCTO");

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarPro.setText("ELIMINAR");
        btnEliminarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        lblUsuario.setText("Usuario : ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarPro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarPro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarPro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarPro)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(107, 107, 107)
                                .addComponent(lblUsuario))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCancelarPro)
                                    .addComponent(btnSalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(479, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnCancelarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnMostrarPro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarPro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarPro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(1221, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void borrar() {
        panTipoOperacion.setVisible(false);
        limpiarCajaTexto();
        activarCajaTexto(false);
        obtenerUltimoId();
        activarBotones(true);
        actualizarBusquedaProveedor();
        cantGlobal = 0;
        obtenerUltimoId();
        txtIva.setText("" + datosEmpresaBean.getUtilidad());
    }
    
    public void muestraUtilidad() {
//        txtUtilidadPro.setText(""+utilidadCategoriaHMap.get(cboCategoriaPro.
//                getSelectedItem()));
        String seleccionCbo = ""+cboCategoriaPro.getSelectedItem();
        if (seleccionCbo.equalsIgnoreCase("GRAVADO") || seleccionCbo.
                equalsIgnoreCase("ESPECIAL") || seleccionCbo.
                equalsIgnoreCase("MEDICAMENTO GRAVADO")) {
//            txtPrecioSinIva.setEnabled(true);
            if (seleccionCbo.equalsIgnoreCase("GRAVADO") || seleccionCbo.
                equalsIgnoreCase("MEDICAMENTO GRAVADO")) {
//                txtPrecioSinIva.requestFocus();
            } else {
//                txtUtilidadPro.requestFocus();
            }
        } else {
            //txtPrecioSinIva.setEnabled(false);
//            txtPrecioCompraPro.requestFocus();
        }
    }

    public void focusPorCategoria() {
//        txtUtilidadPro.setText(""+utilidadCategoriaHMap.get(cboCategoriaPro.
//                getSelectedItem()));
//        String seleccionCategoria = String.valueOf(TblPPC.getModel().
//                getValueAt(TblPPC.getSelectedRow(),3));
//        if (seleccionCategoria.equalsIgnoreCase("GRAVADO") || seleccionCategoria.
//                equalsIgnoreCase("ESPECIAL")) {
//            txtPrecioSinIva.setEnabled(true);
//            if (seleccionCategoria.equalsIgnoreCase("GRAVADO")) {
//                txtPrecioSinIva.requestFocus();
//            } else {
//                txtUtilidadPro.requestFocus();
//            }
//        } else {
//            //txtPrecioSinIva.setEnabled(false);
//            txtPrecioSinIva.setEnabled(false);
//            txtPrecioCompraPro.requestFocus();
//        }
    }
    
    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

    private void cboParametroProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroProActionPerformed
        actualizarBusquedaProducto();
    }//GEN-LAST:event_cboParametroProActionPerformed

    private void buscaDetalleProducto() {
        ArrayList<ProductoBean> resultWS = null;
        hiloInventariosList = new WSInventariosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETINVENTARIOBUSQUEDACODIGO") 
                + String.valueOf(
                        String.valueOf(jtProducto.getModel().getValueAt(
                            jtProducto.getSelectedRow(),0)));
        resultWS = hiloInventariosList.ejecutaWebService(rutaWS,"2");
        ProductoBean p = resultWS.get(0);

        txtCodigoPro.setText(p.getCodigo());
        txtDescripcionPro.setText(p.getDescripcion());
        txtCantidadPro.setText("" + p.getExistencia());
        txtMinimoPro.setText("" + p.getExistenciaMinima());
        cboCategoriaPro.setSelectedItem(util.buscaDescFromIdCat(Principal.categoriasHM, "" + p.getIdCategoria()));
        cboProveedor.setSelectedItem(util.buscaDescFromIdProv(Principal.proveedoresHM, "" + p.getIdProveedor()));
        txtPrecioCosto.setText("" + p.getPrecioCosto());
        txtPrecioPublico.setText("" + p.getPrecioUnitario());
        txtIva.setText("" + p.getPorcentajeImpuesto());
        txtUbicacion.setText("" + p.getUbicacion());
        cboSucursal.setSelectedItem(util.buscaDescFromIdSuc(Principal.sucursalesHM, "" + p.getIdSucursal()));
        double ganancia = Double.parseDouble(txtPrecioPublico.getText());
        ganancia = ganancia - Double.parseDouble(txtPrecioCosto.getText());
        txtUtilidad.setText(String.format("%.2f", ganancia));
        //falta
    }
    
    private void jtProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoMouseClicked
        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoMouseClicked

//    private boolean guardaPrecioEnProdProvCostos() {
//        boolean opOk = true;
//        productosProveedoresCostosBean =
//                new ProductosProveedoresCostosBean();
//        DateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
//        java.util.Date fecha = null; // crea objetos tipo util.Date y sql.Date
//        //java.sql.Date fecha2 = null;
//        java.sql.Timestamp fecha2 = null;
//        //valida que la fecha este establecida
//        if (jDateChooserFechaPro.getDateFormatString().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(null,"No hay fecha establecido.");
//            opOk = false;
//        }         
//        fecha = jDateChooserFechaPro.getDate();
//        java.sql.Timestamp sq = new java.sql.Timestamp(fecha.getTime());
//        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        fecha2 = sq; 
//        productosProveedoresCostosBean.setFecha(fecha2);
//        
//        //valida que el codigo de producto corresponda a un producto
//        ProductoBean temp;
//        try {
//            temp = BDProducto.buscarProducto(txtCodigoPro.getText());
////            if (prodVacio == false) {
////                temp = 
////            }
//            if (temp == null) {
//                JOptionPane.showMessageDialog(null,"No hay producto seleccionado.");
//                opOk = false;
//            } 
//            
////            //valida si es el mismo codigo de producto que el de la tabla
////            if (txtCodigoPro.getText().equalsIgnoreCase(String.valueOf(TblPPC.getModel().
////                getValueAt(TblPPC.getSelectedRow(),2)))) {
////                JOptionPane.showMessageDialog(null,"No es el mismo producto que"
////                        + "el seleccionado en la tabla de proveedores y precios.");
////                return;
////            }
//            productosProveedoresCostosBean.setCodigo(txtCodigoPro.getText());            
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null,ex.getMessage());
//        }
//        
//        //valida que el codigo de proveedor no venga vacio
//        if ((lblCodProv.getText().equalsIgnoreCase("")) && (
//                txtProovedorDescrip.getText().equalsIgnoreCase(""))) {
//            JOptionPane.showMessageDialog(null,"No hay proveedor seleccionado.");
//            opOk = false;
//        }         
//        productosProveedoresCostosBean.setnProvCodigo(Integer.
//                parseInt(lblCodProv.getText()));
//        
//        //valida que el precio costo del producto no venga vacio  txtPrecioVentaPro
//        //si se quiere guardar el precio al publico seria este txtPrecioVentaPro
//        if (txtPrecioCompraPro.getText().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(null,"No hay precio establecido.");
//            opOk = false;
//        } else {
//            productosProveedoresCostosBean.setPrecioCosto(Double.parseDouble(
//                txtPrecioCompraPro.getText()));
//        }
//
//        //valida que el precio venta del producto no venga vacio  
//        if (txtPrecioVentaPro.getText().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(null,"No hay precio establecido.");
//            opOk = false;
//        } else {
//            productosProveedoresCostosBean.setPrecioVenta(Double.parseDouble(
//                    txtPrecioVentaPro.getText()));
//        }
//        
//        if (Ingreso.usuario.getNombre().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(null, "REINICIA EL SISTEMA NO HAY "
//                    + "USUARIO ESTABLECIDO");
//            opOk = false;
//        } else {
//            productosProveedoresCostosBean.setIdUsuario(Ingreso.usuario.getIdUsuario());            
//        }
//        
//        //guarda porcentaje de descuento
//        productosProveedoresCostosBean.setPorcentajeDescuento(Double.parseDouble
//            (txtPorcDesc.getText()));
//        //guarda categoria
//        productosProveedoresCostosBean.setCategoria((String) 
//                cboCategoriaPro.getSelectedItem());
//        //guarda precio max al publico
//        productosProveedoresCostosBean.setPrecioMaxPublico(Double.parseDouble
//                (txtPrecioMaxPub.getText()));
//        //guarda PrecioSinIva
//        if (txtPrecioSinIva.getText().equalsIgnoreCase("")) {
//            productosProveedoresCostosBean.setPrecioSinIva(0);
//        } else {
//            productosProveedoresCostosBean.setPrecioSinIva(Double.parseDouble
//                    (txtPrecioSinIva.getText()));
//        }
//        
//        
//        bdProductosProveedoresCostos = new BDProductosProveedoresCostos();
//        try {
//            if (bdProductosProveedoresCostos.insertarProducto(
//                    productosProveedoresCostosBean)) {
//                bdProductosProveedoresCostos.
//                        buscarProductoCodigoDescripcionEliminaMenor(
//                                productosProveedoresCostosBean);
//                //limpia valores de proveedor
//                lblCodProv.setText("");
//                txtProovedorDescrip.setText("");
//                JOptionPane.showMessageDialog(null,"Precio Guardado");            
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null,ex.getMessage());
//        }
//        borrar();        
//        return opOk;
//    }
    
    private void eliminarProducto() {
        ProductoBean p;
        try {
            p = BDProducto.buscarProducto(txtCodigoPro.getText());
            if (p==null) {
                JOptionPane.showMessageDialog(null, " [ Selecciona un producto "
                        + "de la tabla de productos ]");
                return;
            }
            if (BDProducto.eliminarProducto(p)) {
                JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                actualizarBusquedaProducto();
                borrar();
            } else {
                JOptionPane.showMessageDialog(null, " [ Error al eliminar registro ]");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
        }        
    }
    
    private void eliminarProductoPorCodigo(String codProdAnterior) {
        ProductoBean p;
        try {
            p = BDProducto.buscarProducto(codProdAnterior);
            if (p==null) {
                JOptionPane.showMessageDialog(null, " [ Selecciona un producto "
                        + "de la tabla de productos ]");
                return;
            }
            if (BDProducto.eliminarProducto(p)) {
                JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                actualizarBusquedaProducto();
                borrar();
            } else {
                JOptionPane.showMessageDialog(null, " [ Error al eliminar registro ]");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
        }        
    }
    
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
         lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
    }//GEN-LAST:event_formWindowActivated

    private void guardaProdyPrecio() {
//        //valida que el codigo de proveedor no venga vacio
//        if ((lblCodProv.getText().equalsIgnoreCase("")) && (
//                txtProovedorDescrip.getText().equalsIgnoreCase(""))) {
//            JOptionPane.showMessageDialog(null,"No hay proveedor seleccionado.");
//            return;
//        }         
        //Guarda el producto
        if (txtCantidadPro.getText().compareTo("") != 0 
                && txtDescripcionPro.getText().compareTo("") != 0 
                && txtMinimoPro.getText().compareTo("") != 0 
                && !cboCategoriaPro.getSelectedItem().toString().
                        equalsIgnoreCase("Seleccionar...")
                ) {
            try {
                ProductoBean p = new ProductoBean();
                p.setCodigo(txtCodigoPro.getText());
                p.setDescripcion(txtDescripcionPro.getText());
                p.setCantidad(Integer.parseInt(txtCantidadPro.getText()));
                CategoriaBean c = BDCategoria.buscarCategoriaDescripcion(
                        (String) cboCategoriaPro.getSelectedItem());
                p.setCategoria(c);
                p.setFormula("");
                p.setUbicacion("");
                p.setObservaciones("");
                p.setFactura("");
                p.setMinimo(Integer.parseInt(txtMinimoPro.getText()));
                BDProducto.insertarProducto(p);
                JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                actualizarBusquedaProducto();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");
            return;
        }
        
        //Guarda el precio en prod, prov y costos
//        if (!guardaPrecioEnProdProvCostos()) {
////            int result = JOptionPane.showConfirmDialog(this, "¿Ocurrió un error "
////                    + "al guardar el precio, ¿deseas guardar el solo el producto?"
////                + "?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
////            // VERIFICA si realmente se quierte guardar la venta
////            if (result != JOptionPane.YES_OPTION) {
////                return;
////            }            
//            JOptionPane.showMessageDialog(null, "Error al guardar el precio");
//        }
    }
    
    private void jtProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyTyped
//        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoKeyTyped

    private void jtProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyPressed
    }//GEN-LAST:event_jtProductoKeyPressed

    private void jtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyReleased
        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoKeyReleased

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnSalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProActionPerformed
        this.dispose();
        Inventario inventario = new Inventario();
    }//GEN-LAST:event_btnSalirProActionPerformed

    private void btnMostrarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarProActionPerformed
        JDListaProducto jdListaP = new JDListaProducto(this,true);
        jdListaP.setVisible(true);
    }//GEN-LAST:event_btnMostrarProActionPerformed

    private void btnCancelarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarProActionPerformed

    private void btnModificarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProActionPerformed
        accion = "Actualizar";
        panTipoOperacion.setVisible(true);
        activarCajaTexto(true);
        btnNuevoPro.setEnabled(false);
        btnGuardarPro.setEnabled(true);
        btnModificarPro.setEnabled(false);
        btnCancelarPro.setEnabled(true);
        btnMostrarPro.setEnabled(false);
        txtDescripcionPro.requestFocus();
    }//GEN-LAST:event_btnModificarProActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
//        if (Double.parseDouble(txtCantidadPro.getText())>9999 ||
//            Double.parseDouble(txtMinimoPro.getText())>9999) {
//            JOptionPane.showMessageDialog(null, "Existencia o mínimo inválidos");
//            txtCantidadPro.setText("");
//            txtMinimoPro.setText("0");
//            txtCantidadPro.requestFocus();
//            return;
//        }
        if (util.buscaProdDuplicadoEnSucursal(Principal.productos, 
                txtCodigoPro.getText().trim(), 
                util.buscaIdSuc(Principal.sucursalesHM, "1"))) {
            JOptionPane.showMessageDialog(null, "Producto duplicado en sucursal");
            return;
        }
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtCantidadPro.getText().compareTo("") != 0
                && txtDescripcionPro.getText().compareTo("") != 0
                && txtMinimoPro.getText().compareTo("") != 0 
                && !cboCategoriaPro.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboSucursal.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboProveedor.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            ) {
            ProductoBean p = new ProductoBean();
            p.setCodigo(txtCodigoPro.getText());
            // verificar si codigo ya existe en sucursal si no se permite la alta
            if (txtCodigoPro.getText().equalsIgnoreCase("")) {
            }
//                try {
//                    ProductoBean p = new ProductoBean();
//                    p.setCodigo(txtCodigoPro.getText());
//                    p.setDescripcion(txtDescripcionPro.getText());
//                    p.setCantidad(Integer.parseInt(txtCantidadPro.getText()));
//                    CategoriaBean c = BDCategoria.buscarCategoriaDescripcion(
//                        (String) cboCategoriaPro.getSelectedItem());
//                    p.setCategoria(c);
//                    p.setFormula("");
//                    p.setUbicacion("");
//                    p.setObservaciones("");
//                    p.setFactura("");
//                    p.setMinimo(Integer.parseInt(txtMinimoPro.getText()));
//                    p.setPrecioCosto(Double.parseDouble(txtPrecioCosto.getText()));
//                    p.setPrecioPublico(Double.parseDouble(txtPrecioPublico.getText()));
//                    ProveedorBean prov = BDProveedor.buscarProveedorNombre(
//                        (String) cboProveedor.getSelectedItem());
//                    p.setCodProveedor(prov.getnProvCodigo());
//                    BDProducto.insertarProducto(p);
//                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
//                    obtenerUltimoId();
//                    borrar();
//                    actualizarBusquedaProducto();
//                } catch (SQLException e) {
//                    JOptionPane.showMessageDialog(null, e.getMessage());
//                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");
            }
        }
        if (accion.equalsIgnoreCase("Actualizar")) {
//            //valida que todo este lleno
//            if (txtCantidadPro.getText().compareTo("") != 0
//                && txtDescripcionPro.getText().compareTo("") != 0
//                && txtMinimoPro.getText().compareTo("") != 0
//                && !cboCategoriaPro.getSelectedItem().toString().
//                equalsIgnoreCase("Seleccionar...")
//                && (radioAumentar.isSelected() || radioDisminuir.isSelected())
//            ) {
//                ProductoBean p,prodBuscado;
//                try {
//                    p = BDProducto.buscarProducto(codProdAnterior);
//                    int cantOriginal = p.getCantidad();
//                    existOriginal.setText("Existencia Original: " + cantOriginal);
//                    if (radioAumentar.isSelected()) {
//                        cantOriginal = cantOriginal + Integer.parseInt(txtCantidadPro.getText());
//                    }
//                    if (radioDisminuir.isSelected()) {
//                        cantOriginal = cantOriginal - Integer.parseInt(txtCantidadPro.getText());
//                    }
//                    if (p==null) {
//                        JOptionPane.showMessageDialog(null, "[Debes de "
//                            + "seleccionar un producto, éste código no "
//                            + "existe]");
//                        return;
//                    }
//                    p.setCodigo(txtCodigoPro.getText());
//                    p.setDescripcion(txtDescripcionPro.getText());
//                    p.setCantidad(cantOriginal);
//                    CategoriaBean c = BDCategoria.buscarCategoriaDescripcion(
//                        (String) cboCategoriaPro.getSelectedItem());
//                    p.setCategoria(c);
//                    p.setFormula("");
//                    p.setUbicacion("");
//                    p.setObservaciones("");
//                    p.setFactura("");
//                    p.setMinimo(Integer.parseInt(txtMinimoPro.getText()));
//                    p.setPrecioCosto(Double.parseDouble(txtPrecioCosto.getText()));
//                    p.setPrecioPublico(Double.parseDouble(txtPrecioPublico.getText()));
//                    ProveedorBean prov = BDProveedor.buscarProveedorNombre(
//                        (String) cboProveedor.getSelectedItem());
//                    p.setCodProveedor(prov.getnProvCodigo());
//
//                    if (codProdAnterior.equalsIgnoreCase(txtCodigoPro.getText())) {
//                        // actualiza normal
//                        if (BDProducto.actualizarProducto(p)) {
//                            JOptionPane.showMessageDialog(null, " [ Datos Actualizados ]");
//                            actualizarBusquedaProducto();
//                        } else {
//                            JOptionPane.showMessageDialog(null, " [ Error al actualizar el producto ]");
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(null, " [ Error revisa que concuerden los productos ]");
//                    }
//                    //                    else {
//                        //                        //checar que no exista un producto con el mismo codigo
//                        //                        //JOptionPane.showMessageDialog(null, "nuevo codigo : "+txtCodigoPro.getText());
//                        //                        prodBuscado = BDProducto.buscarProducto(txtCodigoPro.getText());
//                        //                        if (prodBuscado != null) {
//                            //                            JOptionPane.showMessageDialog(null, "El producto ya existe");
//                            //                            return;
//                            //                        }
//                        //                        // borra anterior y crea nuevo
//                        //                        //borra el anterior
//                        //                        eliminarProductoPorCodigo(codProdAnterior);
//                        //                        //guarda nuevo producto
//                        //                        try {
//                            //                            BDProducto.insertarProducto(p);
//                            //                            JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
//                            //                            actualizarBusquedaProducto();
//                            //                        } catch (SQLException e) {
//                            //                            JOptionPane.showMessageDialog(null, e.getMessage());
//                            //                        }
//                        //
//                        //                        //fin guarda nuevo producto
//                        //                    }
//                } catch (SQLException e) {
//                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");
//                return;
//            }
        }
        borrar();
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        panTipoOperacion.setVisible(false);
        limpiarCajaTexto();
        activarCajaTexto(true);
        accion = "Guardar";
        obtenerUltimoId();
        activarBotones(false);
        txtCodigoPro.requestFocus();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void txtPrecioPublicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioPublicoActionPerformed
        btnGuardarPro.requestFocus();
    }//GEN-LAST:event_txtPrecioPublicoActionPerformed

    private void txtPrecioCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCostoActionPerformed
        double utilidadProducto = Double.parseDouble(txtPrecioCosto.getText()) *
        Double.parseDouble(txtUtilidad.getText())/100;
        txtPrecioPublico.setText("" + (Double.parseDouble(txtPrecioCosto.getText()) + utilidadProducto));
        txtPrecioPublico.requestFocus();
    }//GEN-LAST:event_txtPrecioCostoActionPerformed

    private void cboProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboProveedorKeyTyped
        txtPrecioCosto.requestFocus();
    }//GEN-LAST:event_cboProveedorKeyTyped

    private void cboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedorActionPerformed

    }//GEN-LAST:event_cboProveedorActionPerformed

    private void txtMinimoProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinimoProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtMinimoProKeyTyped

    private void txtMinimoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinimoProKeyReleased

    }//GEN-LAST:event_txtMinimoProKeyReleased

    private void txtMinimoProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinimoProFocusLost
        cboCategoriaPro.setFocusable(true);
        cboCategoriaPro.requestFocus(true);
    }//GEN-LAST:event_txtMinimoProFocusLost

    private void txtMinimoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMinimoProActionPerformed
        cboCategoriaPro.setFocusable(true);
        cboCategoriaPro.requestFocus();
    }//GEN-LAST:event_txtMinimoProActionPerformed

    private void txtDescripcionProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProActionPerformed
        txtCantidadPro.requestFocus();
    }//GEN-LAST:event_txtDescripcionProActionPerformed

    private void cboCategoriaProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaProKeyTyped
        //        int key=evt.getKeyCode();
        ////        if(key==KeyEvent.VK_ENTER)
        //        if(key==0)
        //        {
            ////            Toolkit.getDefaultToolkit().beep();
            //            muestraUtilidad();
            //        }
        cboProveedor.setFocusable(true);
        cboProveedor.requestFocus();
    }//GEN-LAST:event_cboCategoriaProKeyTyped

    private void cboCategoriaProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaProKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaProKeyPressed

    private void cboCategoriaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaProActionPerformed
        //        cboProveedor.setFocusable(true);
        //        cboProveedor.requestFocus();
    }//GEN-LAST:event_cboCategoriaProActionPerformed

    private void cboCategoriaProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCategoriaProItemStateChanged

    }//GEN-LAST:event_cboCategoriaProItemStateChanged

    private void txtCantidadProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadProKeyTyped

    private void txtCantidadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProActionPerformed
        //        if (!txtCantidadPro.getText().equalsIgnoreCase("")) {
            //            //SUMA LA CANTIDAD ANTERIOR
            //            txtCantidadPro.setText(""+(Integer.parseInt(txtCantidadPro.getText()) +
                //                   cantGlobal));
        //            txtMinimoPro.requestFocus();
        //        }
        //
        //        if (!txtCantidadPro.getText().equalsIgnoreCase("")) {
            //            if (txtCantidadPro.getText().length()<5) {
                //                //SUMA LA CANTIDAD ANTERIOR
                //                txtCantidadPro.setText(""+(Integer.parseInt(txtCantidadPro.getText()) +
                    //                       cantGlobal));
            //                txtMinimoPro.requestFocus();
            //            } else {
            //                JOptionPane.showMessageDialog(null, "Existencia excesiva");
            //                txtCantidadPro.setText("" + cantGlobal);
            //            }
        //        }
        txtMinimoPro.requestFocus();
    }//GEN-LAST:event_txtCantidadProActionPerformed

    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased

    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        try {
            ProductoBean producto = BDProducto.buscarProducto(txtCodigoPro.getText());
            if (producto != null) {
                codProdAnterior = producto.getCodigo();
                txtCodigoPro.setText(producto.getCodigo());
                txtDescripcionPro.setText(producto.getDescripcion());
                txtCantidadPro.setText(""+producto.getCantidad());
                cantGlobal = Integer.parseInt(txtCantidadPro.getText());
                cboCategoriaPro.setSelectedItem((String)producto.getCategoria().
                    getcCatDescripcion());
                txtMinimoPro.setText("" + producto.getMinimo());
                txtPrecioCosto.setText("" + producto.getPrecioCosto());
                txtPrecioPublico.setText("" + producto.getPrecioPublico());
            } else {
                cantGlobal = 0;
                limpiarCajaTexto();
                activarCajaTexto(true);
                txtCodigoPro.setFocusable(true);
                txtCodigoPro.requestFocus();
                //JOptionPane.showMessageDialog(null,"No existe producto con ese código");
            }
            txtDescripcionPro.requestFocus();
        } catch (SQLException ex) {
            cantGlobal = 0;
            JOptionPane.showMessageDialog(null,"Error Al Seleccionar "
                + "Elemento:" + ex.getMessage());
        }
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void actualizarBusquedaProveedor() {
//        ArrayList<ProveedorBean> result = null;
//        try {
//            result = BDProveedor.mostrarProveedor();
//            DefaultComboBoxModel model = new DefaultComboBoxModel();
//            model.addElement("Seleccionar...");
//            for (ProveedorBean p : result) {
//                model.addElement(p.getcProvNombre());
//            }
//            cboProveedor.setModel(model);
//        } catch (SQLException ex) {
//            Logger.getLogger(FrmProducto.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private ArrayList<ProductoBean> llenaTablaInventario(String buscar, int tipoBusq) {
        ArrayList<ProductoBean> resultWS = new ArrayList<ProductoBean>();
        ProductoBean producto = null;
        for (int i=0; i<jtProducto.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 1 : campoBusq = jtProducto.getModel().getValueAt(
                    i,0).toString();
                    break;
                case 2 : campoBusq = jtProducto.getModel().getValueAt(
                    i,1).toString();
                    break;
                case 3 : campoBusq = jtProducto.getModel().getValueAt(
                    i,5).toString();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                producto = new ProductoBean();                            
                producto.setCodigo(jtProducto.getModel().getValueAt(i,0).toString());
                producto.setDescripcion(jtProducto.getModel().getValueAt(
                    i,1).toString());
                producto.setPrecioCosto(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,2).toString()));
                producto.setPrecioUnitario(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,3).toString()));
                producto.setExistencia(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,4).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM, 
                    jtProducto.getModel().getValueAt(i,5).toString());
                producto.setIdSucursal(idSuc);
                resultWS.add(producto);
            }
        }
        return resultWS;
    }
    
    private void actualizarBusquedaProducto() {
        ArrayList<ProductoBean> resultWS = null;
        ProductoBean producto = null;
        if (String.valueOf(cboParametroPro.getSelectedItem()).
                equalsIgnoreCase("Código")) {
            if (txtBuscarPro.getText().equalsIgnoreCase("")) {
                resultWS = Principal.productos;
            } else {
                resultWS = llenaTablaInventario(
                        txtBuscarPro.getText().trim(),1);
            }
        } else {
            if (String.valueOf(cboParametroPro.getSelectedItem()).
                    equalsIgnoreCase("Nombre")) {
                if (txtBuscarPro.getText().equalsIgnoreCase("")) {
                    resultWS = Principal.productos;
                } else {
                    resultWS = llenaTablaInventario(
                            txtBuscarPro.getText().trim(),2);
                    resultWS = llenaTablaInventario(
                            txtBuscarPro.getText().trim(),2);
                }
            } else {
                if (txtBuscarPro.getText().equalsIgnoreCase("")) {
                    resultWS = Principal.productos;
                } else {
                    resultWS = llenaTablaInventario(
                            txtBuscarPro.getText().trim(),3);
                }
            }
        } 
        recargarTableProductos(resultWS);
    }

    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][6];
        int i = 0;
        for (ProductoBean p : list) {
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getDescripcion();
            datos[i][2] = p.getPrecioCosto();
            datos[i][3] = p.getPrecioUnitario();
            datos[i][4] = p.getExistencia();
            datos[i][5] = util.buscaDescFromIdSuc(Principal.sucursalesHM, "" + p.getIdSucursal());
            NombreProducto.put(p.getCodigo(), p.getDescripcion());
            i++;
        }
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO", "EXIST.", "SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 
    
//    public void recargarTableProductosProveedoresCostos(ArrayList
//            <ProductosProveedoresCostosBean> list) {
//        Object[][] datos = new Object[list.size()][13];
//        int i = 0;
//        DateFormat df = DateFormat.getDateInstance();               
//        for (ProductosProveedoresCostosBean p : list) {
//            datos[i][0] = p.getId();
//            datos[i][1] = p.getnProvCodigo();
//            datos[i][2] = p.getCodigo();
//            
//            //NUEVOS
//            datos[i][3] = p.getCategoria();
//            datos[i][4] = p.getPrecioVenta();
//            datos[i][5] = p.getPrecioMaxPublico();
//            datos[i][6] = p.getPrecioSinIva();
//            datos[i][7] = p.getPorcentajeDescuento();
//            
//            datos[i][8] = NombreProveedor.get(p.getnProvCodigo());
//            datos[i][9] = NombreProducto.get(p.getCodigo());
//            datos[i][10] = p.getPrecioCosto();
//            datos[i][11] = df.format(p.getFecha());
////            datos[i][6] = p.getFecha();
//            datos[i][12] = nombreUsuarios.get(p.getIdUsuario()); 
//            i++;
//        }
//        TblPPC.setModel(new javax.swing.table.DefaultTableModel(
//                datos,
//                new String[]{
//                    "ID", "CPROV", "COD.PROD",
//                    "CATEGORIA", "PRECIOVENTA", "PRECIOMAXPUB","PRECIOSINIVA","PORCDESC",
//                    "PROVEEDOR", "PRODUCTO", "PRECIO COSTO", 
//                    "FECHA", "USUARIO"
//                }) {
//
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        });
//        
//        //columnas para hacer pequeñas
//        TableColumn columna1 = TblPPC.getColumn("ID");
//        //columna1.setPreferredWidth(1);
//        columna1.setMaxWidth(10);
//        TableColumn columna2 = TblPPC.getColumn("CPROV");
//        columna2.setMaxWidth(10);
//        TableColumn columna3 = TblPPC.getColumn("COD.PROD");
//        columna3.setMaxWidth(10);
//        
//        //NUEVOS
//        TableColumn columna4 = TblPPC.getColumn("CATEGORIA");
//        columna4.setMaxWidth(10);
//        TableColumn columna5 = TblPPC.getColumn("PRECIOVENTA");
//        columna5.setMaxWidth(10);
//        TableColumn columna6 = TblPPC.getColumn("PRECIOMAXPUB");
//        columna6.setMaxWidth(10);
//        TableColumn columna7 = TblPPC.getColumn("PRECIOSINIVA");
//        columna7.setMaxWidth(10);
//        TableColumn columna8 = TblPPC.getColumn("PORCDESC");
//        columna8.setMaxWidth(10);
//        
//    } 

    public static void main(String args[]) {
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
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmProducto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarPro;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnModificarPro;
    private javax.swing.JButton btnMostrarPro;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnSalirPro;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboCategoriaPro;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JComboBox cboProveedor;
    private javax.swing.JComboBox cboSucursal;
    private javax.swing.JLabel existOriginal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JTable jtProducto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panTipoOperacion;
    private javax.swing.JRadioButton radioAumentar;
    private javax.swing.JRadioButton radioDisminuir;
    private javax.swing.JTextField txtBuscarPro;
    private javax.swing.JTextField txtCantidadPro;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtMinimoPro;
    private javax.swing.JTextField txtPrecioCosto;
    private javax.swing.JTextField txtPrecioPublico;
    private javax.swing.JTextField txtUbicacion;
    private javax.swing.JTextField txtUtilidad;
    // End of variables declaration//GEN-END:variables
//    private ReporteProductoParametro repProductoP;
}