package vistas;

import ComponenteConsulta.JDListaMovimientos;
import beans.ProductoBean;
import ComponenteConsulta.JDListaProducto;
import beans.CategoriaBean;
import beans.DatosEmpresaBean;
import beans.FechaServidorBean;
import beans.MovimientosBean;
import beans.ProductosProveedoresCostosBean;
import beans.ProveedorBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumn;
import util.Util;
import static vistas.Principal.productos;

public class FrmProducto extends javax.swing.JFrame {
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    WSMovimientos hiloMovimientos;
    //Fin WS
    double stock = 0;    
    double precioGlobal = 0;
    
    String codProdAnterior = "";
    FechaServidorBean fechaServidorBean;

    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    
    //cantidad global
    int cantGlobal = 0;

    private int llamadoVentaInventario;

    public int getLlamadoVentaInventario() {
        return llamadoVentaInventario;
    }

    public void setLlamadoVentaInventario(int llamadoVentaInventario) {
        this.llamadoVentaInventario = llamadoVentaInventario;
    }
    
    public FrmProducto(int llamadoVenta) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        initComponents();
        this.setLlamadoVentaInventario(llamadoVenta);
        
        buttonGroup1.add(radioAumentar);
        buttonGroup1.add(radioDisminuir);
        buttonGroup1.add(radioNinguno);
        panTipoOperacion.setVisible(false);
        
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
        
        cargaSucursales();        
        
        //Carga iva de la empresa de ganancia por producto
        txtIva.setText("" + Principal.datosSistemaBean.getIvaEmpresa());
        
        //inhabilita combos
        cboSucursal.setEnabled(false);
        cboProveedor.setEnabled(false);
        cboCategoriaPro.setEnabled(false);

        //cambia formato de fecha a tipo datetime xq asi esta en bd remota
        jCalFechaIngresoProd.setDate(new Date());
//        jCalFechaIngresoProd.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        
        txtIdArticulo.setVisible(false);
        btnGuardarPro.setEnabled(false);
        
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        
        if (this.getLlamadoVentaInventario() == 1) {
            btnNuevoPro.setVisible(true);
            btnGuardarPro.setEnabled(true);
            accion = "Guardar";
            btnModificarPro.setVisible(false);
            btnEliminarPro.setVisible(false);
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("..\\img\\matserviceslogo.png")));
    }
    
    private void cargaSucursales() {
        int idSucursal = Ingreso.usuario.getIdSucursal();
        cboSucursal.removeAllItems();
        int indiceSucursales = 0;
        //CARGA CLIENTES Y ESTABLECE CLIENTE POR DEFECTO
        Iterator it = Principal.sucursalesHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          if ((Integer.parseInt(key.toString()) == idSucursal) ||
                  (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes.getProperty("SUPERUSUARIO")))){
              cboSucursal.addItem(Principal.sucursalesHM.get(key));
              indiceSucursales++;
              cboSucursal.setSelectedIndex(indiceSucursales-1);
          }
        }
    }

    public void limpiarCajaTexto() {
        cboUMedida.setSelectedItem("Seleccionar...");
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
        panTipoOperacion.setVisible(false);
        java.util.Date fechaLocal = new Date();
        jCalFechaIngresoProd.setDate(fechaLocal);
        jCalFechaCaducidadProd.setDate(fechaLocal);
        
        txtBuscarPro.setText("");
        txtObserProd.setText("");
        txtIdArticulo.setText("");
        codProdAnterior = "";
        cboSucursal.requestFocus(true);
        stock = 0;
        precioGlobal = 0;
    }

    public void activarCajaTexto(boolean b) {
        cboUMedida.setEnabled(b);
        cboSucursal.setEnabled(b);
        cboCategoriaPro.setEnabled(b);
        cboProveedor.setEnabled(b);
        txtCantidadPro.setEditable(b);
        txtDescripcionPro.setEditable(b);
        
        txtCodigoPro.setEditable(b);
        txtMinimoPro.setEditable(b);
        txtIva.setEditable(b);
        txtUbicacion.setEditable(b);
        txtPrecioCosto.setEditable(b);
        txtPrecioPublico.setEditable(b);
        txtUtilidad.setEditable(b);
        txtObserProd.setEditable(b);
        codProdAnterior = ""; 
        radioAumentar.setEnabled(true);
        radioDisminuir.setEnabled(true);
        radioNinguno.setEnabled(true);
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
        btnActualizaInvent = new javax.swing.JButton();
        radioNinguno = new javax.swing.JRadioButton();
        txtUtilidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUbicacion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboSucursal = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIdArticulo = new javax.swing.JTextField();
        jCalFechaIngresoProd = new com.toedter.calendar.JDateChooser();
        txtObserProd = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jCalFechaCaducidadProd = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        cboUMedida = new javax.swing.JComboBox();
        btnNuevoPro = new javax.swing.JButton();
        btnGuardarPro = new javax.swing.JButton();
        btnModificarPro = new javax.swing.JButton();
        btnCancelarPro = new javax.swing.JButton();
        btnMostrarPro = new javax.swing.JButton();
        btnSalirPro = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnEliminarPro = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        btnSalirPro1 = new javax.swing.JButton();
        btnMovimientos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
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
        jtProducto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        txtCodigoPro.setEditable(false);
        txtCodigoPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });
        txtCodigoPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoProFocusGained(evt);
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
        txtCantidadPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCantidadProMouseClicked(evt);
            }
        });
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

        txtDescripcionPro.setEditable(false);
        txtDescripcionPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProActionPerformed(evt);
            }
        });

        jLabel15.setText("Mínimo :");

        txtMinimoPro.setEditable(false);
        txtMinimoPro.setText("0");
        txtMinimoPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMinimoProMouseClicked(evt);
            }
        });
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

        txtPrecioCosto.setEditable(false);
        txtPrecioCosto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecioCostoMouseClicked(evt);
            }
        });
        txtPrecioCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioCostoActionPerformed(evt);
            }
        });
        txtPrecioCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecioCostoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecioCostoFocusLost(evt);
            }
        });
        txtPrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCostoKeyTyped(evt);
            }
        });

        jLabel5.setText("Precio Público:");

        txtPrecioPublico.setEditable(false);
        txtPrecioPublico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioPublicoActionPerformed(evt);
            }
        });

        panTipoOperacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Operación"));

        radioAumentar.setText("Aumentar cantidad al Inventario");

        radioDisminuir.setText("Disminuir cantidad al Inventario");

        existOriginal.setBackground(new java.awt.Color(255, 153, 153));
        existOriginal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        existOriginal.setText("Existencia Actual: ");
        existOriginal.setOpaque(true);

        btnActualizaInvent.setBackground(new java.awt.Color(153, 153, 153));
        btnActualizaInvent.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnActualizaInvent.setText("Actualizar Existencia");
        btnActualizaInvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizaInventActionPerformed(evt);
            }
        });

        radioNinguno.setSelected(true);
        radioNinguno.setText("Ninguno");

        javax.swing.GroupLayout panTipoOperacionLayout = new javax.swing.GroupLayout(panTipoOperacion);
        panTipoOperacion.setLayout(panTipoOperacionLayout);
        panTipoOperacionLayout.setHorizontalGroup(
            panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoOperacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioNinguno)
                    .addComponent(radioAumentar)
                    .addGroup(panTipoOperacionLayout.createSequentialGroup()
                        .addComponent(radioDisminuir)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizaInvent, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(existOriginal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panTipoOperacionLayout.setVerticalGroup(
            panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoOperacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radioAumentar)
                .addGap(3, 3, 3)
                .addGroup(panTipoOperacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizaInvent)
                    .addComponent(radioDisminuir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioNinguno)
                .addGap(18, 18, 18)
                .addComponent(existOriginal)
                .addGap(7, 7, 7))
        );

        txtUtilidad.setEditable(false);
        txtUtilidad.setBackground(new java.awt.Color(255, 153, 102));
        txtUtilidad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel4.setText("Utilidad:");

        jLabel8.setText("IVA :");

        txtIva.setEditable(false);
        txtIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIvaActionPerformed(evt);
            }
        });

        jLabel9.setText("Ubicación : ");

        txtUbicacion.setEditable(false);
        txtUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUbicacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Sucursal : ");

        cboSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSucursalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cboSucursalMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cboSucursalMousePressed(evt);
            }
        });
        cboSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSucursalItemStateChanged(evt);
            }
        });
        cboSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSucursalActionPerformed(evt);
            }
        });
        cboSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSucursalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboSucursalKeyTyped(evt);
            }
        });

        jLabel12.setText("Fecha Ingreso :");

        jLabel14.setText("Observaciones : ");

        jLabel16.setText("F.Caducidad :");

        jLabel18.setText("U.Medida");

        cboUMedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Pieza", "Elemento (Pieza) unidad de medida Inglesa", "Unidad de servicio", "Kilogramo", "Gramo", "Tarífa", "Metro", "Pulgada", "Pie", "Yarda", "Milla (milla estatal)", "Metro cuadrado", "Centímetro cuadrado", "Metro cúbico", "Litro", "Galón (UK)", "Galón (EUA)", "Hora", "Día", "Año", "Uno", "Batch", "Paquete a granel", "Lote [unidad de adquisición]", "Lote", "Hora de trabajo", "Variedad", "Cabeza", "Personas", "Número de paquetes", "Conjunto", "Mutuamente definido", "Caja", "Kit (Conjunto de piezas)", "Bloque" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panTipoOperacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(17, 17, 17)
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtMinimoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(5, 5, 5)
                                    .addComponent(cboCategoriaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(17, 17, 17)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPrecioPublico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboUMedida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCalFechaIngresoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCalFechaCaducidadProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(384, 384, 384)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObserProd, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioPublico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jCalFechaIngresoProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jCalFechaCaducidadProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cboUMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtObserProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(panTipoOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        btnSalirPro.setText("SALIR");
        btnSalirPro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 51, 0), 1, true));
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

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xlsx.png"))); // NOI18N
        btnExcel.setText("EXCEL");
        btnExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        btnSalirPro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cod_barras.png"))); // NOI18N
        btnSalirPro1.setText("CREAR");
        btnSalirPro1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPro1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPro1ActionPerformed(evt);
            }
        });

        btnMovimientos.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnMovimientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/User group.png"))); // NOI18N
        btnMovimientos.setText("MOVIMIENTOS");
        btnMovimientos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMovimientos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientosActionPerformed(evt);
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
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSalirPro1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnCancelarPro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMovimientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(359, Short.MAX_VALUE))))
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
                        .addGap(20, 20, 20)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirPro1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnCancelarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addContainerGap(153, Short.MAX_VALUE))
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
                .addContainerGap(1360, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void borrar() {
        stock = 0;
        precioGlobal = 0;
        panTipoOperacion.setVisible(false);
        limpiarCajaTexto();
        activarCajaTexto(false);
        activarBotones(true);
        actualizarBusquedaProveedor();
        cantGlobal = 0;
        txtIva.setText("" + Principal.datosSistemaBean.getIvaEmpresa());
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
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("OBTIENEPRODUCTOPORID") 
                + String.valueOf(jtProducto.getModel().getValueAt(
                            jtProducto.getSelectedRow(),0));
        resultWS = hiloInventariosList.ejecutaWebService(rutaWS,"5");
        ProductoBean p = resultWS.get(0);

        txtIdArticulo.setText("" + p.getIdArticulo());
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
        jCalFechaIngresoProd.setDate(null);
        jCalFechaIngresoProd.setDate(p.getFechaIngreso());
        txtObserProd.setText(p.getObservaciones());
        existOriginal.setText("Existencia Actual: " + 
                Double.parseDouble(txtCantidadPro.getText()));
        stock = Double.parseDouble(txtCantidadPro.getText());
        precioGlobal = Double.parseDouble(txtPrecioPublico.getText());
        cboUMedida.setSelectedItem(p.getUnidadMedida());
        jCalFechaCaducidadProd.setDate(null);
        jCalFechaCaducidadProd.setDate(p.getFechaCaducidad());
        jtProducto.requestFocus(true);
    }
    
    private void jtProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoMouseClicked
        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoMouseClicked

    private void eliminarProducto() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (txtIdArticulo.getText().compareTo("") != 0) {
                hiloInventarios = new WSInventarios();
                String rutaWS = constantes.getProperty("IP") + constantes.getProperty("ELIMINAPRODUCTO");
                ProductoBean productoEliminar = hiloInventarios.ejecutaWebService(rutaWS,"3"
                        ,txtIdArticulo.getText().trim());
                if (productoEliminar != null) {
                    JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
                    //Carga productos
                    productos = util.getMapProductos();
                    util.llenaMapProductos(productos);
                    limpiarCajaTexto();
                    actualizarBusquedaProducto();
                    activarBotones(true);
                } else {
                    JOptionPane optionPane = new JOptionPane("No es posible eliminar el "
                            + "producto existen movimientos que lo relacionan", JOptionPane.ERROR_MESSAGE);    
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay producto para eliminar");
            }
        }
    }
    
    private void eliminarProductoPorCodigo(String codProdAnterior) {
//        ProductoBean p;
//        try {
//            p = BDProducto.buscarProducto(codProdAnterior);
//            if (p==null) {
//                JOptionPane.showMessageDialog(null, " [ Selecciona un producto "
//                        + "de la tabla de productos ]");
//                return;
//            }
//            if (BDProducto.eliminarProducto(p)) {
//                JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
//                actualizarBusquedaProducto();
//                borrar();
//            } else {
//                JOptionPane.showMessageDialog(null, " [ Error al eliminar registro ]");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
//        }        
    }
    
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        productos = util.getMapProductos();
        util.llenaMapProductos(productos);
        Principal.productosHM = util.getProductosHM();
        Principal.productosHMID = util.getProductosHMID();
        //FrmProducto frmProducto = new FrmProducto();
        this.actualizarBusquedaProducto(); 
        this.recargarTableProductos(productos);
    }//GEN-LAST:event_formWindowActivated

    private void jtProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyTyped
    }//GEN-LAST:event_jtProductoKeyTyped

    private void jtProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyPressed
    }//GEN-LAST:event_jtProductoKeyPressed

    private void jtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaDetalleProducto();
        }
    }//GEN-LAST:event_jtProductoKeyReleased

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnSalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProActionPerformed
        this.setLlamadoVentaInventario(0);
        this.dispose();
//        Inventario inventario = new Inventario();
//        inventario.setExtendedState(inventario.MAXIMIZED_BOTH);
//        inventario.setVisible(true);
//        System.exit(0);
    }//GEN-LAST:event_btnSalirProActionPerformed

    private void btnMostrarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarProActionPerformed
        JDListaProducto jdListaP = new JDListaProducto(this,true
                , Principal.sucursalesHM
                , Principal.categoriasHM
                , Principal.proveedoresHM);
        jdListaP.setVisible(true);
    }//GEN-LAST:event_btnMostrarProActionPerformed

    private void btnCancelarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarProActionPerformed

    private void btnModificarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProActionPerformed
        if (!txtIdArticulo.getText().equalsIgnoreCase("")) {
            jtProducto.setEnabled(false);
            accion = "Actualizar";
            btnActualizaInvent.setEnabled(true);
            panTipoOperacion.setVisible(true);
            activarCajaTexto(true);
            btnNuevoPro.setEnabled(false);
            btnGuardarPro.setEnabled(true);
            btnModificarPro.setEnabled(false);
            btnCancelarPro.setEnabled(true);
            btnMostrarPro.setEnabled(false);
            existOriginal.setText("Existencia Actual: " + 
                    Double.parseDouble(txtCantidadPro.getText()));
            JOptionPane.showMessageDialog(null, "Presiona el botón Guardar "
                    + "cuando tus cambios estén listos");
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un producto");
            return;
        }
    }//GEN-LAST:event_btnModificarProActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        if (accion.equalsIgnoreCase("Guardar")) {
            if (txtCantidadPro.getText().compareTo("") != 0
                && txtCodigoPro.getText().compareTo("") != 0
                && txtDescripcionPro.getText().compareTo("") != 0
                && txtMinimoPro.getText().compareTo("") != 0 
                && !cboCategoriaPro.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboSucursal.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboProveedor.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboUMedida.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            ) {
                //verifica si el producto se encuentra registrado en la sucursal
                if (util.buscaProdDuplicadoEnSucursal(Principal.productos, 
                        txtCodigoPro.getText().trim(), 
                        util.buscaIdSuc(Principal.sucursalesHM, 
                                cboSucursal.getSelectedItem().toString()))) {
                    JOptionPane.showMessageDialog(null, "Producto duplicado en sucursal");
                    return;
                }
                //fin verifica si el producto se encuentra registrado en la sucursal
                ProductoBean p = new ProductoBean();
                p.setCodigo(txtCodigoPro.getText());
                p.setDescripcion(txtDescripcionPro.getText());
                p.setExistencia(Double.parseDouble(txtCantidadPro.getText()));
                p.setExistenciaMinima(Double.parseDouble(txtMinimoPro.getText()));

                //jCalFechaIngresoProd.setDateFormatString("yyyy/MM/dd HH:mm:ss");
                p.setFechaIngreso(jCalFechaIngresoProd.getDate());
                p.setFechaCaducidad(jCalFechaCaducidadProd.getDate());
                //cambia formato para enviarla como string a ws
                //String fecha = util.cambiaFormatoFecha(p.getFechaIngreso().toLocaleString());
                
                p.setIdCategoria(util.buscaIdCat(Principal.categoriasHM, cboCategoriaPro.getSelectedItem().toString()));
                p.setIdProveedor(util.buscaIdProv(Principal.proveedoresHM, cboProveedor.getSelectedItem().toString()));
                p.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM, cboSucursal.getSelectedItem().toString()));
                p.setObservaciones(txtObserProd.getText());
                p.setPorcentajeImpuesto(Double.parseDouble(txtIva.getText()));
                p.setPrecioCosto(Double.parseDouble(txtPrecioCosto.getText()));
                p.setPrecioUnitario(Double.parseDouble(txtPrecioPublico.getText()));
                p.setUbicacion(txtUbicacion.getText());
                p.setUnidadMedida(cboUMedida.getSelectedItem().toString());
                
                //huardar producto
                hiloInventarios = new WSInventarios();
                String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GUARDAPRODUCTO");
                ProductoBean productoInsertado = hiloInventarios.ejecutaWebService(rutaWS,"1"
                        ,p.getCodigo()
                        ,p.getDescripcion()
                        ,"" + p.getPrecioCosto()
                        ,"" + p.getPrecioUnitario()
                        ,"" + p.getPorcentajeImpuesto()
                        ,"" + p.getExistencia()
                        ,"" + p.getExistenciaMinima()
                        ,p.getUbicacion()
                        ,p.getFechaIngreso().toLocaleString()
                        ,"" + p.getIdProveedor()
                        ,"" + p.getIdCategoria()
                        ,"" + p.getIdSucursal()
                        ,""
                        ,p.getObservaciones()
                        ,p.getUnidadMedida()
                        ,p.getFechaCaducidad().toLocaleString()
                );
                if (productoInsertado != null) {
                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
                    //Carga productos
                    productos = util.getMapProductos();
                    util.llenaMapProductos(productos);
                    actualizarBusquedaProducto();
                    activarBotones(true);
                    limpiarCajaTexto();
                    cboSucursal.requestFocus(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");
            }
        }
        if (accion.equalsIgnoreCase("Actualizar")) {
            if (txtCantidadPro.getText().compareTo("") != 0
                && txtIdArticulo.getText().compareTo("") != 0
                && txtCodigoPro.getText().compareTo("") != 0
                && txtDescripcionPro.getText().compareTo("") != 0
                && txtMinimoPro.getText().compareTo("") != 0 
                && !cboCategoriaPro.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboSucursal.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboProveedor.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
                && !cboUMedida.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            ) {
                //Verifica que ya se haya actualizado el stock
                if (btnActualizaInvent.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "Actualiza tu inventario"
                            + " antes de Guardar");
                    return;
                }
                //Fin Verifica que ya se haya actualizado el stock
                
                ProductoBean p = new ProductoBean();
                p.setIdArticulo(Integer.parseInt(txtIdArticulo.getText()));
                p.setCodigo(txtCodigoPro.getText());
                p.setDescripcion(txtDescripcionPro.getText());
                p.setExistencia(Double.parseDouble(txtCantidadPro.getText()));
                p.setExistenciaMinima(Double.parseDouble(txtMinimoPro.getText()));
                existOriginal.setText("Existencia Actual: " + p.getExistencia());
                //jCalFechaIngresoProd.setDateFormatString("yyyy/MM/dd HH:mm:ss");
                p.setFechaIngreso(jCalFechaIngresoProd.getDate());
                p.setFechaCaducidad(jCalFechaCaducidadProd.getDate());
                //cambia formato para enviarla como string a ws
                String fecha = util.cambiaFormatoFecha(p.getFechaIngreso().toLocaleString());
                String fechaCad = util.cambiaFormatoFecha(p.getFechaCaducidad().toLocaleString());
                
                p.setIdCategoria(util.buscaIdCat(Principal.categoriasHM, cboCategoriaPro.getSelectedItem().toString()));
                p.setIdProveedor(util.buscaIdProv(Principal.proveedoresHM, cboProveedor.getSelectedItem().toString()));
                p.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM, cboSucursal.getSelectedItem().toString()));
                p.setObservaciones(txtObserProd.getText());
                p.setPorcentajeImpuesto(Double.parseDouble(txtIva.getText()));
                p.setPrecioCosto(Double.parseDouble(txtPrecioCosto.getText()));
                p.setPrecioUnitario(Double.parseDouble(txtPrecioPublico.getText()));
                p.setUbicacion(txtUbicacion.getText());
                p.setUnidadMedida(cboUMedida.getSelectedItem().toString());
                
                //huardar producto
                hiloInventarios = new WSInventarios();
                String rutaWS = constantes.getProperty("IP") + constantes.getProperty("MODIFICAPRODUCTO");
                ProductoBean productoModificado = hiloInventarios.ejecutaWebService(rutaWS,"2"
                        ,"" + p.getIdArticulo()
                        ,p.getCodigo()
                        ,p.getDescripcion()
                        ,"" + p.getPrecioCosto()
                        ,"" + p.getPrecioUnitario()
                        ,"" + p.getPorcentajeImpuesto()
                        ,"" + p.getExistencia()
                        ,"" + p.getExistenciaMinima()
                        ,p.getUbicacion()
                        ,fecha
                        ,"" + p.getIdProveedor()
                        ,"" + p.getIdCategoria()
                        ,"" + p.getIdSucursal()
                        ,""
                        ,p.getObservaciones()
                        ,p.getUnidadMedida()
                        ,fechaCad
                );
                if (productoModificado != null) {
                    //insertar movimiento
  
                    String tipoOperacion = "";
                    if (radioNinguno.isSelected()) {
                        if ((Double.parseDouble(txtPrecioPublico.getText()) != precioGlobal)
                            && (precioGlobal) != 0){
                            tipoOperacion = "Cambio Precio Publico";
                        }
                    }
                    if (radioAumentar.isSelected()) {
                        tipoOperacion = "Incremento Inventario Manual";
                        if ((Double.parseDouble(txtPrecioPublico.getText()) != precioGlobal)
                            && (precioGlobal) != 0){
                            tipoOperacion = tipoOperacion + " y Cambio Precio Publico";
                        }
                    }
                    if (radioDisminuir.isSelected()) {
                        tipoOperacion = "Decremento Inventario Manual";
                        if ((Double.parseDouble(txtPrecioPublico.getText()) != precioGlobal)
                            && (precioGlobal) != 0){
                            tipoOperacion = tipoOperacion + " y Cambio Precio Publico";
                        }
                    }
                    radioNinguno.setSelected(true);
                    
                    MovimientosBean mov = new MovimientosBean();
                    hiloMovimientos = new WSMovimientos();
                    rutaWS = constantes.getProperty("IP") + constantes.getProperty("GUARDAMOVIMIENTO");
                    MovimientosBean movimientoInsertado = hiloMovimientos.ejecutaWebService(rutaWS,"1"
                        ,"" + p.getIdArticulo()
                        ,""+Ingreso.usuario.getIdUsuario()
                        ,tipoOperacion
                        ,"" + p.getExistencia()
                        ,fecha
                        ,"" + p.getIdSucursal());
                    //
                
                    JOptionPane.showMessageDialog(null, "[ Registro Actualizado ]");
                    //Carga productos
                    productos = util.getMapProductos();
                    util.llenaMapProductos(productos);
                    actualizarBusquedaProducto();
                    activarBotones(true);
                    limpiarCajaTexto();
                    jtProducto.setEnabled(true);
                    cboSucursal.requestFocus(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene Todos Los Campos..!!");
            }
        }
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        panTipoOperacion.setVisible(false);
        limpiarCajaTexto();
        activarCajaTexto(true);
        accion = "Guardar";
        activarBotones(false);
        cboSucursal.requestFocus();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void txtPrecioPublicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioPublicoActionPerformed
        double utilidad = Double.parseDouble(txtPrecioCosto.getText());
        utilidad = Double.parseDouble(txtPrecioPublico.getText()) - utilidad;
        txtUtilidad.setText("" + String.format("%.2f", utilidad));
        txtObserProd.requestFocus();
    }//GEN-LAST:event_txtPrecioPublicoActionPerformed

    private void txtPrecioCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCostoActionPerformed
        //calcula precio publico a partir de precio costo
        double precioPub = Double.parseDouble(txtPrecioCosto.getText());
        double ivaProd = Double.parseDouble(txtIva.getText());
        precioPub = precioPub + (precioPub * (ivaProd/100));
        txtPrecioPublico.setText("" + precioPub);
        //fin calcula precio publico a partir de precio costo
        double utilidad = Double.parseDouble(txtPrecioCosto.getText());
        utilidad = Double.parseDouble(txtPrecioPublico.getText()) - utilidad;
        txtUtilidad.setText("" + String.format("%.2f", utilidad));
        txtPrecioPublico.requestFocus(true);
    }//GEN-LAST:event_txtPrecioCostoActionPerformed

    private void cboProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboProveedorKeyTyped
        txtUbicacion.requestFocus();
    }//GEN-LAST:event_cboProveedorKeyTyped

    private void cboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedorActionPerformed

    }//GEN-LAST:event_cboProveedorActionPerformed

    private void txtMinimoProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinimoProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
        txtMinimoPro.requestFocus(true);
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
        txtIva.requestFocus();
//        cboProveedor.setFocusable(true);
//        cboProveedor.requestFocus();
    }//GEN-LAST:event_cboCategoriaProKeyTyped

    private void cboCategoriaProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaProKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaProKeyPressed

    private void cboCategoriaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaProActionPerformed
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
        txtMinimoPro.requestFocus();
    }//GEN-LAST:event_txtCantidadProActionPerformed

    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased

    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        //verifica si se selecciono sucursal
        if (cboSucursal.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una sucursal");
            cboSucursal.requestFocus(true);
            return;
        } 
        //fin verifica si se selecciono sucursal
        
        //verifica si el producto se encuentra registrado en la sucursal
        if (util.buscaProdDuplicadoEnSucursal(Principal.productos, 
                txtCodigoPro.getText().trim(), 
                util.buscaIdSuc(Principal.sucursalesHM, 
                        cboSucursal.getSelectedItem().toString()))) {
            JOptionPane.showMessageDialog(null, "Producto duplicado en sucursal");
            txtCodigoPro.requestFocus(true);
            return;
        } else {
            txtDescripcionPro.requestFocus(true);
        }
        //fin verifica si el producto se encuentra registrado en la sucursal
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtPrecioCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioCostoFocusLost
        //calcula precio publico a partir de precio costo
        double precioPub = Double.parseDouble(txtPrecioCosto.getText());
        double ivaProd = Double.parseDouble(txtIva.getText());
        precioPub = precioPub + (precioPub * (ivaProd/100));
        txtPrecioPublico.setText("" + precioPub);
        //fin calcula precio publico a partir de precio costo
    }//GEN-LAST:event_txtPrecioCostoFocusLost

    private void txtCodigoProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProFocusGained
    }//GEN-LAST:event_txtCodigoProFocusGained

    private void cboSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSucursalActionPerformed
    }//GEN-LAST:event_cboSucursalActionPerformed

    private void cboSucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSucursalKeyReleased
    }//GEN-LAST:event_cboSucursalKeyReleased

    private void cboSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSucursalKeyTyped
        txtCodigoPro.requestFocus(true);
    }//GEN-LAST:event_cboSucursalKeyTyped

    private void cboSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMouseClicked
    }//GEN-LAST:event_cboSucursalMouseClicked

    private void cboSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMouseEntered
    }//GEN-LAST:event_cboSucursalMouseEntered

    private void cboSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSucursalItemStateChanged
    }//GEN-LAST:event_cboSucursalItemStateChanged

    private void cboSucursalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMousePressed
    }//GEN-LAST:event_cboSucursalMousePressed

    private void txtIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIvaActionPerformed
        cboProveedor.requestFocus(true);
    }//GEN-LAST:event_txtIvaActionPerformed

    private void txtUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUbicacionActionPerformed
        txtPrecioCosto.requestFocus();
    }//GEN-LAST:event_txtUbicacionActionPerformed

    private void txtPrecioCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCostoKeyTyped
        //valida solo numeros
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioCostoKeyTyped

    private void btnActualizaInventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizaInventActionPerformed
        int dialogResult = -2;
        if (radioAumentar.isSelected()) {
            dialogResult = JOptionPane.showConfirmDialog(null, 
                    "¿Realmente deseas aumentar " + 
                    txtCantidadPro.getText() + " al stock?");
            if (dialogResult == JOptionPane.YES_OPTION) {
                stock = stock + Double.parseDouble(txtCantidadPro.getText());
                existOriginal.setText("Existencia Actual: " + stock);
                txtCantidadPro.setText("" + stock);
                btnActualizaInvent.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Presiona el botón Guardar"
                        + " para registrar los cambios");
            } 
        } else {
            if (radioDisminuir.isSelected()) {
                dialogResult = JOptionPane.showConfirmDialog(null, 
                        "¿Realmente deseas disminuir " + 
                        txtCantidadPro.getText() + " al stock?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    stock = stock - Double.parseDouble(txtCantidadPro.getText());
                    existOriginal.setText("Existencia Actual: " + stock);
                    txtCantidadPro.setText("" + stock);
                    btnActualizaInvent.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Presiona el botón Guardar"
                            + " para registrar los cambios");
                } 
            }
            if (radioNinguno.isSelected()) {
                btnActualizaInvent.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Presiona el botón Guardar"
                        + " para registrar los cambios");
            }
        }
        radioAumentar.setEnabled(false);
        radioDisminuir.setEnabled(false);
        radioNinguno.setEnabled(false);
    }//GEN-LAST:event_btnActualizaInventActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        VistaExcel vistaExcel = new VistaExcel();
        vistaExcel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        vistaExcel.setVisible(true);
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnSalirPro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPro1ActionPerformed
        FrmCodBarras frmCodBarras = new FrmCodBarras();
        frmCodBarras.setVisible(true);
    }//GEN-LAST:event_btnSalirPro1ActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
    }//GEN-LAST:event_formFocusGained

    private void btnMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimientosActionPerformed
        if (txtIdArticulo.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un registro");
            return;
        }
        JDListaMovimientos jdListaMovs = new JDListaMovimientos(this,true
                , Principal.sucursalesHM
                , Principal.productosHM
                , Principal.productosHMID
                , Principal.usuariosHM
                , Principal.proveedoresHM
                , txtIdArticulo.getText());
        jdListaMovs.setVisible(true);
    }//GEN-LAST:event_btnMovimientosActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    }//GEN-LAST:event_formWindowOpened

    private void txtPrecioCostoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecioCostoFocusGained
    }//GEN-LAST:event_txtPrecioCostoFocusGained

    private void txtPrecioCostoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecioCostoMouseClicked
        txtPrecioCosto.setToolTipText("Presiona enter al terminar para calcular"
                + " precio al público");
    }//GEN-LAST:event_txtPrecioCostoMouseClicked

    private void txtCantidadProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCantidadProMouseClicked
        txtCantidadPro.setText("");
    }//GEN-LAST:event_txtCantidadProMouseClicked

    private void txtMinimoProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMinimoProMouseClicked
        txtMinimoPro.setText("");
    }//GEN-LAST:event_txtMinimoProMouseClicked

    private void actualizarBusquedaProveedor() {
    }

    private ArrayList<ProductoBean> llenaTablaInventario(String buscar, int tipoBusq) {
        ArrayList<ProductoBean> resultWS = new ArrayList<ProductoBean>();
        ProductoBean producto = null;
        for (int i=0; i<jtProducto.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 1 : campoBusq = jtProducto.getModel().getValueAt(
                    i,1).toString();
                    break;
                case 2 : campoBusq = jtProducto.getModel().getValueAt(
                    i,2).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = jtProducto.getModel().getValueAt(
                    i,6).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                producto = new ProductoBean();
                producto.setIdArticulo(Integer.parseInt(jtProducto.getModel().getValueAt(i,0).toString()));
                producto.setCodigo(jtProducto.getModel().getValueAt(i,1).toString());
                producto.setDescripcion(jtProducto.getModel().getValueAt(
                    i,2).toString());
                producto.setPrecioCosto(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,3).toString()));
                producto.setPrecioUnitario(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,4).toString()));
                producto.setExistencia(Double.parseDouble(jtProducto.getModel().getValueAt(
                    i,5).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM, 
                    jtProducto.getModel().getValueAt(i,6).toString());
                producto.setIdSucursal(idSuc);
                resultWS.add(producto);
            }
        }
        return resultWS;
    }
    
    public void actualizarBusquedaProducto() {
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
        Object[][] datos = new Object[list.size()][7];
        int i = 0;
        for (ProductoBean p : list) {
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes.getProperty("SUPERUSUARIO")))) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = p.getCodigo();
                datos[i][2] = p.getDescripcion();
                datos[i][3] = p.getPrecioCosto();
                datos[i][4] = p.getPrecioUnitario();
                datos[i][5] = p.getExistencia();
                datos[i][6] = util.buscaDescFromIdSuc(Principal.sucursalesHM, "" + p.getIdSucursal());
//                NombreProducto.put(p.getCodigo(), p.getDescripcion());
                i++;
            }
        }
        Object[][] datosFinal = new Object[i][7];
        //Para filtrar los registros
        for (int j=0; j<i; j++) {
            if (datos[j][0]!=null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
                datosFinal[j][2] = datos[j][2];
                datosFinal[j][3] = datos[j][3];
                datosFinal[j][4] = datos[j][4];
                datosFinal[j][5] = datos[j][5];
                datosFinal[j][6] = datos[j][6];
            }
        }
        //Fin Para filtrar los registros
        
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
                new String[]{
                    "ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO", "EXIST.", "SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jtProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtProducto.getColumnModel().getColumn(0).setMaxWidth(0);
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

//    public static void main(String args[]) {
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        } catch (InstantiationException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        } catch (IllegalAccessException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new FrmProducto().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizaInvent;
    private javax.swing.JButton btnCancelarPro;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnModificarPro;
    private javax.swing.JButton btnMostrarPro;
    private javax.swing.JButton btnMovimientos;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnSalirPro;
    private javax.swing.JButton btnSalirPro1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboCategoriaPro;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JComboBox cboProveedor;
    private javax.swing.JComboBox cboSucursal;
    private javax.swing.JComboBox cboUMedida;
    private javax.swing.JLabel existOriginal;
    private com.toedter.calendar.JDateChooser jCalFechaCaducidadProd;
    private com.toedter.calendar.JDateChooser jCalFechaIngresoProd;
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
    private javax.swing.JRadioButton radioNinguno;
    private javax.swing.JTextField txtBuscarPro;
    private javax.swing.JTextField txtCantidadPro;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtIdArticulo;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtMinimoPro;
    private javax.swing.JTextField txtObserProd;
    private javax.swing.JTextField txtPrecioCosto;
    private javax.swing.JTextField txtPrecioPublico;
    private javax.swing.JTextField txtUbicacion;
    private javax.swing.JTextField txtUtilidad;
    // End of variables declaration//GEN-END:variables
//    private ReporteProductoParametro repProductoP;

}