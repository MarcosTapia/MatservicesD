package vistas;

import beans.ProductoBean;
import Ticket.Ticket;
import beans.ComprasBean;
import beans.DetalleCompraBean;
import beans.MovimientosBean;
import static componenteUtil.NumberToLetterConverter.convertNumberToLetter;
import constantes.ConstantesProperties;
import consumewebservices.WSCompras;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetalleCompras;
import consumewebservices.WSDetallePedidos;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSPedidos;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import util.Util;
import static vistas.Principal.productos;

public class FrmCompras extends javax.swing.JFrame {
    
    DefaultTableModel ListaProductoC = new DefaultTableModel();
    
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    WSCompras hiloCompras;
    WSDetalleCompras hiloDetalleCompras;
    WSPedidos hiloPedidos;
    WSDetallePedidos hiloDetallePedidos;
    WSMovimientos hiloMovimientos;
    //Fin WS
    String codProdAnterior = "";
    String accion = "";
    
    HashMap<String, String> NombreProducto = new HashMap<String, String>();
    ProductoBean prodParcial = null;    
    //Variable que guarda el numero de compra
    int noCompraGral;
    ArrayList<ProductoBean> inventario = null;
    String sucursalSistema = "";
    
    DetalleCompraBean detalleCompraBean = null;
    ArrayList<DetalleCompraBean> detalleCompraTotal = new ArrayList<>();
    ArrayList<DetalleCompraBean> detalleCompraTotalTemp = new ArrayList<>();

    //Carga iva de la empresa de ganancia por producto
    double ivaEmpresa = Principal.datosSistemaBean.getIvaEmpresa();
    double ivaGral = Principal.datosSistemaBean.getIvaGral();

    boolean cargaDatos = false;
    
    public FrmCompras() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        java.util.Date fecha = util.obtieneFechaServidor();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
//        txtFecha.setText("Fecha: " + a);
        cargaProveedores();
        lblUsuario.setText(Principal.datosEmpresaBean.getNombreEmpresa()
                + " Sucursal: " 
                + util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                        + Ingreso.usuario.getIdSucursal()));
        sucursalSistema = util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                        + Ingreso.usuario.getIdSucursal());
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        String titulos[] = {"CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO UND.", 
                            "IMPORTE"};
        ListaProductoC.setColumnIdentifiers(titulos);
        this.setLocationRelativeTo(null);
        inventario = util.getInventario();
        productos = util.getInventario();
        util.llenaMapProductos(productos);
        jDateChooserFechaCompra.setDate(util.obtieneFechaServidor());
        txtNoCompra.setText("" + obtenerUltimoId());
        txtCodigoPro.setText("Espere...");
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    public void cargaProveedores() {
        cboProveedor.removeAllItems();
        int indiceProveedor = 0;
        //CARGA CLIENTES Y ESTABLECE CLIENTE POR DEFECTO
        Iterator it = Principal.proveedoresHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboProveedor.addItem(Principal.proveedoresHM.get(key));
          if (key.toString().equalsIgnoreCase("1")) {
              cboProveedor.setSelectedIndex(indiceProveedor);
          }
          indiceProveedor++;
        }
    }
    
    public void muestraUtilidad() {
        txtIva.requestFocus();
    }

    public int obtenerUltimoId() {
        int id = 0;
        ComprasBean resultWS = null;
        hiloCompras = new WSCompras();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETULTIMOIDCOMPRAS");
        resultWS = hiloCompras.ejecutaWebService(rutaWS,"1");
        id = resultWS.getIdCompra() + 1;
        return id;
    }
    
    public void limpiarCajaTexto(String factura, String noCompra, int indice) {
        txtCodigoPro.setText("");
        txtDescripcionPro.setText("");
        txtCantidadPro.setText("1");
        txtPrecioCompraPro.setText("");
        txtIva.setText("" + +ivaEmpresa);
        txtObservacionesPro.setText("");
        txtPrecioVentaPro.setText("");
        txtUtilidad.setText("");
        txtFacturaPro.setText(factura);
        txtNoCompra.setText(noCompra);
        cboProveedor.setSelectedIndex(indice);
        lblIdProd.setText("");
    }

    //Actualiza totales,subtotales, etc de venta
    public void actualizaTotales(List<DetalleCompraBean> list) {
        double subtotal = 0;
        double iva = 0;
        double total;
        int i = 0;
        try {
            for (DetalleCompraBean p : list) {
                subtotal = subtotal + (p.getPrecioCosto() * p.getCantidad());
            }            
        } catch (java.lang.NullPointerException e) {
            subtotal = 0;
        }
        
        txtSubTotal.setText(""+subtotal);        
        iva = subtotal * Double.parseDouble(txtIvaCompra.getText())/100;
        //iva = iva + ((iva/100) * subtotal);
        txtIvaPago.setText(""+iva);
        total = iva + subtotal;
        txtMontoApagar.setText(""+total);    
        
        //muestra 2 decimales en porc de descuento
        DecimalFormat df = new DecimalFormat("#.##");   
        txtSubTotal.setText("" + df.format(Double.parseDouble(txtSubTotal.getText())));  
        txtIvaPago.setText("" + df.format(Double.parseDouble(txtIvaPago.getText())));  
        txtMontoApagar.setText("" + df.format(Double.parseDouble(txtMontoApagar.getText())));  
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtBuscarPro = new javax.swing.JTextField();
        cboParametroPro = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtProductoCompras = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidadPro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDescripcionPro = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtObservacionesPro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtFacturaPro = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TblCompras = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPrecioCompraPro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioVentaPro = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboProveedor = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jDateChooserFechaCompra = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();
        btnGuardarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnCancelarPro = new javax.swing.JButton();
        btnSalirPro = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnBorrarActual = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblIdProd = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUtilidad = new javax.swing.JTextField();
        btnSalirPro1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNoCompra = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtIvaPago = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtMontoApagar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtIvaCompra = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cboUMedida = new javax.swing.JComboBox();
        btnSalirPro2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
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

        cboParametroPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Código" }));
        cboParametroPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroProActionPerformed(evt);
            }
        });

        jtProductoCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtProductoCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductoComprasMouseClicked(evt);
            }
        });
        jtProductoCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProductoComprasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtProductoCompras);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(81, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtBuscarPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel6.setText("Cantidad :");

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

        jLabel7.setText("Descripciòn :");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDescripcionPro, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel7, org.jdesktop.beansbinding.BeanProperty.create("labelFor"));
        bindingGroup.addBinding(binding);

        txtDescripcionPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProActionPerformed(evt);
            }
        });

        jLabel14.setText("Observaciones :");

        txtObservacionesPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionesProActionPerformed(evt);
            }
        });

        jLabel12.setText("Factura :");

        txtFacturaPro.setBackground(new java.awt.Color(153, 153, 255));
        txtFacturaPro.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        txtFacturaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacturaProActionPerformed(evt);
            }
        });

        TblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblComprasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TblCompras);

        jLabel18.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel18.setText("DETALLE DE COMPRA :");

        jLabel8.setText("$ Costo :");

        txtPrecioCompraPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioCompraPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioCompraProActionPerformed(evt);
            }
        });
        txtPrecioCompraPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraProKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("$ Venta :");

        txtPrecioVentaPro.setEditable(false);
        txtPrecioVentaPro.setBackground(new java.awt.Color(153, 153, 153));
        txtPrecioVentaPro.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtPrecioVentaPro.setForeground(new java.awt.Color(0, 102, 204));
        txtPrecioVentaPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioVentaPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioVentaProKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaProKeyTyped(evt);
            }
        });

        jLabel16.setText("F. Adquisición :");

        cboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboProveedorKeyTyped(evt);
            }
        });

        jLabel4.setText("Proveedor :");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jDateChooserFechaCompra, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel4, org.jdesktop.beansbinding.BeanProperty.create("labelFor"));
        bindingGroup.addBinding(binding);

        jDateChooserFechaCompra.setDateFormatString("dd/MM/yyyy");

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addVenta.png"))); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 51)));
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        btnAgregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnAgregarKeyReleased(evt);
            }
        });

        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarPro.setText("GUARDAR");
        btnGuardarPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 51)));
        btnGuardarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarPro.setText("ELIMINAR PROD.");
        btnEliminarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        btnCancelarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarPro.setText("CANCELAR COMPRA");
        btnCancelarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProActionPerformed(evt);
            }
        });

        btnSalirPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirPro.setText("SALIR");
        btnSalirPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirProActionPerformed(evt);
            }
        });

        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addVenta.png"))); // NOI18N
        btnInventario.setText("INVENTARIO");
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnBorrarActual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnBorrarActual.setText("LIMPIAR");
        btnBorrarActual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrarActual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrarActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActualActionPerformed(evt);
            }
        });

        jLabel5.setText("IVA :");

        txtIva.setBackground(new java.awt.Color(255, 153, 102));
        txtIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIva.setText("0");
        txtIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIvaActionPerformed(evt);
            }
        });
        txtIva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIvaFocusGained(evt);
            }
        });

        lblIdProd.setForeground(new java.awt.Color(240, 240, 240));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Utilidad :");

        txtUtilidad.setEditable(false);
        txtUtilidad.setBackground(new java.awt.Color(153, 153, 153));
        txtUtilidad.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        btnSalirPro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/User group.png"))); // NOI18N
        btnSalirPro1.setText("PROVEEDORES");
        btnSalirPro1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPro1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPro1ActionPerformed(evt);
            }
        });

        jLabel3.setText("No. Compra :");

        txtNoCompra.setEditable(false);
        txtNoCompra.setBackground(new java.awt.Color(153, 153, 255));
        txtNoCompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel10.setText("SUBTOTAL:");

        txtSubTotal.setEditable(false);
        txtSubTotal.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtSubTotal.setForeground(new java.awt.Color(51, 51, 255));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel13.setText("IVA :");

        txtIvaPago.setEditable(false);
        txtIvaPago.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtIvaPago.setForeground(new java.awt.Color(51, 51, 255));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel19.setText("TOTAL :");

        txtMontoApagar.setEditable(false);
        txtMontoApagar.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtMontoApagar.setForeground(new java.awt.Color(51, 51, 255));

        jLabel15.setText("IVA Compra % :");

        txtIvaCompra.setBackground(new java.awt.Color(153, 153, 255));
        txtIvaCompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtIvaCompra.setText("16");

        jLabel20.setText("Unidad :");

        cboUMedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Pieza", "Elemento (Pieza) unidad de medida Inglesa", "Unidad de servicio", "Kilogramo", "Gramo", "Tarífa", "Metro", "Pulgada", "Pie", "Yarda", "Milla (milla estatal)", "Metro cuadrado", "Centímetro cuadrado", "Metro cúbico", "Litro", "Galón (UK)", "Galón (EUA)", "Hora", "Día", "Año", "Uno", "Batch", "Paquete a granel", "Lote [unidad de adquisición]", "Lote", "Hora de trabajo", "Variedad", "Cabeza", "Personas", "Número de paquetes", "Conjunto", "Mutuamente definido", "Caja", "Kit (Conjunto de piezas)", "Bloque" }));

        btnSalirPro2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnSalirPro2.setText("INICIO");
        btnSalirPro2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 51)));
        btnSalirPro2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPro2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPro2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPro2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblIdProd)
                .addGap(321, 321, 321))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFacturaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtObservacionesPro, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cboProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtIvaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPrecioCompraPro, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboUMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jDateChooserFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIvaPago)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel19))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtMontoApagar)
                                    .addComponent(txtSubTotal)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarPro)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBorrarActual)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGuardarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelarPro)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSalirPro1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSalirPro2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(62, 62, 62))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtDescripcionPro)
                    .addComponent(jLabel12)
                    .addComponent(txtFacturaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPrecioCompraPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel20)
                            .addComponent(cboUMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooserFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtNoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtObservacionesPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIvaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalirPro2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(lblIdProd)
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrarActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalirPro1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalirPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIvaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMontoApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        jDateChooserFechaCompra.getAccessibleContext().setAccessibleName("");

        jLabel11.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel11.setText("REGISTRAR COMPRA");

        lblUsuario.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        lblUsuario.setText("Usuario : ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(186, 186, 186))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
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

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnSalirProActionPerformed

    private void borrar() {
        limpiarCajaTexto("","",1);
    }
    
    private void btnCancelarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente deseas "
                + "borrar la compra?");
        if(dialogResult == JOptionPane.YES_OPTION){
            borrar();
            txtFacturaPro.setText("");
            txtNoCompra.setText("");
            cboProveedor.setSelectedIndex(0);
            lblIdProd.setText("");
            detalleCompraTotal.clear();
            //actualizo tabla de compra
            recargarTableCompraParcialProductos(detalleCompraTotal);
            txtNoCompra.setText("" + obtenerUltimoId());
            txtSubTotal.setText("");
            txtIvaPago.setText("");
            txtMontoApagar.setText("");
            txtCodigoPro.requestFocus(true);
        }
    }//GEN-LAST:event_btnCancelarProActionPerformed

    public void imprimeCompra(){
        try {
            //Primera parte
//            Date date=new Date();
//            SimpleDateFormat fecha=new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat hora=new SimpleDateFormat("hh:mm:ss aa");
            Ticket ticket = new Ticket();
            ticket.AddCabecera("" + Principal.datosEmpresaBean.getNombreEmpresa());
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("Sucursal: " + util.buscaDescFromIdSuc(Principal.sucursalesHM, 
                    "" + Ingreso.usuario.getIdSucursal()));
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera(Principal.datosEmpresaBean.getDireccionEmpresa());
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera(Principal.datosEmpresaBean.getCiudadEmpresa());
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera(Principal.datosEmpresaBean.getTelEmpresa());
            ticket.AddCabecera(ticket.DarEspacio());
            
//            ticket.AddCabecera("     tlf: 222222  r.u.c: 22222222222");
//            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));

            //Segunda parte
            ticket.AddSubCabecera(ticket.DarEspacio());
//            SimpleDateFormat fecha=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
//            String fechaImpresion = fecha.format(ventaTitulos.getcVenFecha());
            java.util.Date fecha = util.obtieneFechaServidor();
            String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
            String fechaImpresion = a;
            ticket.AddSubCabecera("Compra No: " + 
                    txtNoCompra.getText() + "   " +
                    fechaImpresion);
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera("Compró: " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno() 
                + Ingreso.usuario.getApellido_materno());
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));
            
            //tercera parte
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera("CANT   DESCRIPCION         P.U   IMPORTE");
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));
            
            //cuarta parte detalle detalleVentaProducto
            ticket.AddSubCabecera(ticket.DarEspacio());
            for(DetalleCompraBean detalleCompraBean : detalleCompraTotal) {
               //cantidad de decimales
               NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
               DecimalFormat form = (DecimalFormat)nf;
               form.applyPattern("#,###.00");
               //cantidad
               String cantidad = "" + detalleCompraBean.getCantidad();
               if(cantidad.length()<4){
                   int cant = 4 - cantidad.length();
                   String can = "";
                   for(int f=0;f<cant;f++){
                       can+=" ";
                   }
                   cantidad+=can;
               }
                
                //descripcion
               String item = util.buscaDescFromIdProd(Principal.productosHM
                       , "" + detalleCompraBean.getIdArticulo());
               if(item.length()>17) {
                   item=item.substring(0,16)+".";
               } else {
                    int c=17-item.length();String comple="";
                    for(int y1=0;y1<c;y1++) {
                        comple+=" ";
                    }
                    item+=comple;
               }
                
                //precio unitario
               String precio=""+detalleCompraBean.getPrecioCosto();
               double pre1=Double.parseDouble(precio);
               precio=form.format(pre1);
               if(precio.length()<8){
                    int p=8-precio.length();String pre="";
                    for(int y1=0;y1<p;y1++){
                        pre+=" ";
                    }
                    precio=pre+precio;
               }
                
                //total
                String total1 = "" + detalleCompraBean.getCantidad()
                        * detalleCompraBean.getPrecioCosto();
                total1 = form.format(Double.parseDouble(total1));
                if (total1.length()<8) {
                    int t=8-total1.length();String tota="";
                    for(int y1=0;y1<t;y1++){
                        tota+=" ";
                    }
                    total1=tota+total1;
                }
                //agrego los items al detalle
                ticket.AddItem(cantidad,item,precio,total1);
                //ticket.AddItem("","","",ticket.DarEspacio());
            }
            ticket.AddItem(ticket.DibujarLinea(40),"","","");
            
            //Quinta parte totales
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("SUBTOTAL                ",txtSubTotal.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("IVA                     ",txtIva.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("TOTAL                   ",txtMontoApagar.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("",ticket.DarEspacio());
            
            //para cantidad con letra
            String numEnLetra = convertNumberToLetter(txtMontoApagar.getText());
            ticket.AddTotal("",numEnLetra.trim());
            ticket.AddPieLinea(ticket.DarEspacio());     
//            ticket.ImprimirDocumento("LPT1",true);
            ticket.ImprimirDocumento("usb002",true);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
        }     
    }    
    
    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        txtCodigoPro.setText("Espere...");
        //lleno el objeto compra
        ComprasBean compra = new ComprasBean();
        compra.setFactura(txtFacturaPro.getText());
        //compra.setFecha(jDateChooserFechaCompra.getDate());
        compra.setIdProveedor(util.buscaIdProv(Principal.proveedoresHM, 
                cboProveedor.getSelectedItem().toString()));
        compra.setIdSucursal(Ingreso.usuario.getIdSucursal());
        compra.setIdUsuario(Ingreso.usuario.getIdUsuario());
        compra.setObservaciones(txtObservacionesPro.getText());
        //JOptionPane.showMessageDialog(null, jDateChooserFechaCompra.getDate());
        compra.setFecha(jDateChooserFechaCompra.getDate());
        compra.setSubtotal(Double.parseDouble(txtSubTotal.getText()));
        compra.setIva(Double.parseDouble(txtIvaPago.getText()));
        compra.setTotal(Double.parseDouble(txtMontoApagar.getText()));
        compra.setTipocompra("COMPRA NORMAL");
        compra.setCancelada(0);
        
        while (compra != null) {
            //guarda compra
            hiloCompras = new WSCompras();
            String rutaWS = constantes.getProperty("IP") 
                    + constantes.getProperty("GUARDACOMPRA");
            //cambia formato para enviarla como string a ws
            //String fecha = util.cambiaFormatoFecha(compra.getFecha().toLocaleString());
            ComprasBean compraGuardada = hiloCompras
                    .ejecutaWebService(rutaWS,"2"
                    , "" + compra.getIdProveedor()
                    , "" + compra.getObservaciones()
                    , "" + compra.getIdUsuario()
                    , "" + compra.getFactura()
                    , "" + compra.getIdSucursal()
                    , "" + compra.getFecha().toLocaleString()
                    
                    , "" + compra.getSubtotal()
                    , "" + compra.getIva()
                    , "" + compra.getTotal()
                    , compra.getTipocompra()
                    , "" + compra.getCancelada()
                    );
            if (compraGuardada != null) {
                //guarda detalle compra
                for (DetalleCompraBean detalleCompra :
                        detalleCompraTotal) {
                    hiloDetalleCompras = new WSDetalleCompras();
                    rutaWS = constantes.getProperty("IP") 
                        + constantes.getProperty("GUARDADETALLECOMPRA");
//                                boolean detalleGuardado = false;
//                                while (!detalleGuardado) {
                    // para ajuste inventario                                }
                    int idArticuloComprado = detalleCompra.getIdArticulo();
                    double cantidadComprada = detalleCompra.getCantidad();
                    // fin para ajuste inventario        
                    DetalleCompraBean detalleCompraGuardada = 
                            hiloDetalleCompras.
                                    ejecutaWebService(rutaWS
                                    ,"1"
                            , "" + Integer.parseInt(txtNoCompra.getText().trim())
                            , "" + detalleCompra.getIdArticulo()
                            , "" + detalleCompra.getPrecioPublico()
                            , "" + detalleCompra.getPrecioCosto()
                            , "" + detalleCompra.getCantidad()
                            , "" + detalleCompra.getDescuento()
                            , detalleCompra.getUnidadMedida()
                            , "" + Ingreso.usuario.getIdSucursal());
                    if (detalleCompraGuardada != null) {
                            //Aumenta inventario
                                //obtiene articulo para saber su cantidad original
                            ArrayList<ProductoBean> resultWS = null;
                            hiloInventariosList = new WSInventariosList();
                            rutaWS = constantes.getProperty("IP") 
                                    + constantes.getProperty("OBTIENEPRODUCTOPORID") 
                                    + String.valueOf(idArticuloComprado);
                            resultWS = hiloInventariosList.ejecutaWebService(rutaWS
                                    ,"5");
                            ProductoBean p = resultWS.get(0);
                                //fin obtiene articulo para saber su cantidad original

                                //aumenta iinventario en cifras no en bd
                            double cantidadOriginal = p.getExistencia();
                            double cantidadFinal = cantidadOriginal 
                                    + cantidadComprada;
                                //fin aumenta iinventario en cifras no en bd

                                //realiza ajuste inventario 
                            hiloInventarios = new WSInventarios();
                            rutaWS = constantes.getProperty("IP") 
                                    + constantes.getProperty("AJUSTAINVENTARIOCOMPRA");
                            ProductoBean ajuste = hiloInventarios
                                    .ejecutaWebService(rutaWS,"6"
                                    ,String.valueOf(idArticuloComprado)
                                    ,"" + cantidadFinal
                                    ,"" + detalleCompra.getPrecioCosto()        
                                    ,"" + detalleCompra.getDescuento()
                                    ,"" + detalleCompra.getPrecioPublico());
                            if (ajuste != null) {
                                    //Guarda movimiento
                                String fecha = util.dateToDateTimeAsString(util
                                        .obtieneFechaServidor());
                                MovimientosBean mov = new MovimientosBean();
                                hiloMovimientos = new WSMovimientos();
                                rutaWS = constantes.getProperty("IP") + constantes
                                        .getProperty("GUARDAMOVIMIENTO");
                                MovimientosBean movimientoInsertado = hiloMovimientos
                                        .ejecutaWebService(rutaWS,"1"
                                    ,"" + p.getIdArticulo()
                                    ,"" + Ingreso.usuario.getIdUsuario()
                                    ,"COMPRA NORMAL"
                                    ,"" + cantidadComprada
                                    ,fecha
                                    ,"" + Ingreso.usuario.getIdSucursal());
                                    //Fin Guarda movimiento
                        }
                    }
                }
                //fin guarda detalle compra

                //carga productos actualizados
                productos = util.getInventario();
                util.llenaMapProductos(productos);
                //fin carga productos actualizados

                JOptionPane.showMessageDialog(null, 
                        "COMPRA GUARDADA CORRRECTAMENTE");
                //detalleCompraTotal.remove(detalleCompra);
                int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas "
                        + "Imprimir la Compra?", "Mensaje..!!"
                        , JOptionPane.YES_NO_OPTION);
                if (resultado == JOptionPane.YES_OPTION) {
                    //imprime ticket
                    imprimeCompra();
                    //fin imprime ticket
                }
                compra = null;
                //fin guarda detalle venta
            }                        
            //fin guarda compra
        }
        limpiarCajaTexto("","",0);
        txtSubTotal.setText("");
        txtIvaPago.setText("");
        txtMontoApagar.setText("");
        txtIva.setText("");
        jDateChooserFechaCompra.setEnabled(true);
        jDateChooserFechaCompra.setDate(new Date());
        detalleCompraBean = null;
        detalleCompraTotal.clear();
        //actualizo tabla de compra
        recargarTableCompraParcialProductos(detalleCompraTotal);
        txtNoCompra.setText("" + obtenerUltimoId());
        txtCodigoPro.requestFocus(true);
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void txtCantidadProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadProKeyTyped

    private void txtPrecioCompraProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioCompraProKeyTyped
   
    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

    private void cboParametroProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroProActionPerformed
        actualizarBusquedaProducto();
    }//GEN-LAST:event_cboParametroProActionPerformed

    private void buscaDetalleProducto() {
        txtCantidadPro.setText("1");
        ArrayList<ProductoBean> resultWS;
        hiloInventariosList = new WSInventariosList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("OBTIENEPRODUCTOPORID") 
                + String.valueOf(jtProductoCompras.getModel().getValueAt(
                            jtProductoCompras.getSelectedRow(),0));
        resultWS = hiloInventariosList.ejecutaWebService(rutaWS,"5");
        prodParcial = resultWS.get(0);
        txtCodigoPro.setText(prodParcial.getCodigo());
        txtDescripcionPro.setText(prodParcial.getDescripcion());
        lblIdProd.setText(String.valueOf(prodParcial.getIdArticulo()).trim());
        txtIva.setText("" + prodParcial.getPorcentajeImpuesto());
        cboUMedida.setSelectedItem(prodParcial.getUnidadMedida());
        txtCantidadPro.requestFocus(true);
    }
    
    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased
    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void TblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblComprasMouseClicked
    }//GEN-LAST:event_TblComprasMouseClicked

    private void txtPrecioCompraProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCompraProActionPerformed
        txtIva.requestFocus();
    }//GEN-LAST:event_txtPrecioCompraProActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (!cargaDatos) {
            Principal p = new Principal();
            p.cargaProveedores();
            cargaProveedores();
            
            ArrayList<ProductoBean> resultWS = null;
            hiloInventariosList = new WSInventariosList();
            String rutaWS = constantes.getProperty("IP") 
                    + constantes.getProperty("GETINVENTARIOS");
            resultWS = hiloInventariosList.ejecutaWebService(rutaWS,"1");
            recargarTableProductos(resultWS);
            inventario = util.getInventario();
            productos = util.getInventario();
            util.llenaMapProductos(productos);
            txtCodigoPro.setText("");
            txtCodigoPro.requestFocus(true);
            // fin refresca inventario
            cargaDatos = true;
        }
    }//GEN-LAST:event_formWindowActivated

    private void txtPrecioVentaProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioVentaProKeyTyped

    private void txtPrecioVentaProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaProKeyReleased
        DecimalFormat formateador = new DecimalFormat("#.#");
        double precioVenta = 0.0, precioCompra = 0.0, Utilidad = 0.0;
        //System.out.print(""+txtPrecioVentaPro.getText());
        if (txtPrecioVentaPro.getText().compareTo("") != 0) {
            precioVenta = Double.parseDouble(txtPrecioVentaPro.getText());
            precioCompra = Double.parseDouble(txtPrecioCompraPro.getText());
            Utilidad = (precioVenta * 100) / precioCompra;
            Utilidad = Utilidad - 100;
        }
//        txtUtilidadPro.setText(String.valueOf(formateador.format(Utilidad)));
    }//GEN-LAST:event_txtPrecioVentaProKeyReleased

    private void txtCantidadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProActionPerformed
        txtPrecioCompraPro.requestFocus();
    }//GEN-LAST:event_txtCantidadProActionPerformed

    public void agregaCompraParcial() {
        // ********* Validaciones
        //para codigo y descripcion
        if (txtCodigoPro.getText().equalsIgnoreCase("") || 
                (txtDescripcionPro.getText().equalsIgnoreCase(""))
                || lblIdProd.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "DEBE EXISTIR UN "
                    + "PRODUCTO SELECCIONADO");
            return;
        }
        //para precio de venta
        if (txtPrecioVentaPro.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "DEBE EXISTIR UN PRECIO "
                    + "DE VENTA ESTABLECIDO");
            return;
        }
        //para precio de Costo o compra
        if (txtPrecioCompraPro.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "DEBE EXISTIR UN PRECIO "
                    + "DE COMPRA ESTABLECIDO");
            return;
        }
        //valida que exista usuario
        if (Ingreso.usuario.getNombre().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Reinicia el sistema porque "
                    + "no hay usuario selecionado");
            return;            
        }
        //valida que exista factura
        if (txtFacturaPro.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Debe existir una factura que "
                    + "sustente esta compra");
            txtFacturaPro.requestFocus(true);
            return;            
        }
        if (cboUMedida.getSelectedItem().toString().equalsIgnoreCase("Seleccionar...")) {
            JOptionPane.showMessageDialog(null, "Debe existir una unidad de medida");
            return;            
        }
        // fin ********* Validaciones
        
        //agrega detalle compra en tabla y en memoria
            //llena objeto detalle compra
        detalleCompraBean = new DetalleCompraBean();
        detalleCompraBean.setCantidad(Double.parseDouble(txtCantidadPro.getText()));
        detalleCompraBean.setIdArticulo(Integer.parseInt(lblIdProd.getText()));
        detalleCompraBean.setIdCompra(Integer.parseInt(txtNoCompra.getText()));
        detalleCompraBean.setIdSucursal(Ingreso.usuario.getIdSucursal());
        detalleCompraBean.setPrecioCosto(Double.parseDouble(txtPrecioCompraPro.getText()));
        detalleCompraBean.setPrecioPublico(Double.parseDouble(txtPrecioVentaPro.getText()));
        detalleCompraBean.setDescuento(Double.parseDouble(txtIva.getText()));
        detalleCompraBean.setUnidadMedida(cboUMedida.getSelectedItem().toString());
            //fin llena objeto detalle compra

        if (buscaRepetido(detalleCompraBean)) {
            JOptionPane.showMessageDialog(null, "YA ESTA AGREGADO EL PRODUCTO");
            return;
        }
        //agrego producto a vender a lista parcial de venta
        detalleCompraTotal.add(detalleCompraBean);
        //actualizo tabla de compra
        recargarTableCompraParcialProductos(detalleCompraTotal);

        //Actualiza Precios, totales y subtotales
        jDateChooserFechaCompra.setEnabled(false);
        actualizaTotales(detalleCompraTotal);
        limpiarCajaTexto(txtFacturaPro.getText(), txtNoCompra.getText()
                ,cboProveedor.getSelectedIndex());
        txtCodigoPro.requestFocus();
    }

    private boolean buscaRepetido(DetalleCompraBean articulo) {
        boolean existe = false;
        for (DetalleCompraBean compraParcial : detalleCompraTotal) {
            if (articulo.getIdArticulo() == compraParcial.getIdArticulo()) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregaCompraParcial();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
    }//GEN-LAST:event_btnAgregarKeyPressed

    private void cboProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboProveedorKeyTyped
        btnAgregar.requestFocus();
    }//GEN-LAST:event_cboProveedorKeyTyped

    private boolean eliminaProdDeCompra(int idArticulo) {
        boolean existe = false;
        for (DetalleCompraBean detalleCompra : detalleCompraTotal) {
            if (detalleCompra.getIdArticulo()==idArticulo) {
                detalleCompraTotal.remove(detalleCompra);
                existe = true;
                break;
            } else {
                existe = false;
            }
        }
        return existe;
    }
    
    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        int fila = TblCompras.getSelectedRow();        
        if (fila >= 0) {
            String idArticulo = String.valueOf(TblCompras.getModel().getValueAt
                (TblCompras.getSelectedRow(),0));
            if (eliminaProdDeCompra(Integer.parseInt(idArticulo))) {
                try {
                    ((DefaultTableModel)TblCompras.getModel()).removeRow(fila);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                actualizaTotales(detalleCompraTotal);
            }
        } else {
            JOptionPane.showMessageDialog(null, "DEBES SELECCIONAR UN PRODUCTO");
            return;
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void txtFacturaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacturaProActionPerformed
        txtCantidadPro.requestFocus();
    }//GEN-LAST:event_txtFacturaProActionPerformed

    private void btnAgregarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyReleased
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            agregaCompraParcial();            
        }
    }//GEN-LAST:event_btnAgregarKeyReleased

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        cargaDatos = false;
        FrmProducto frmProducto = new FrmProducto(1);
        frmProducto.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    public ProductoBean buscarProdPorCodSuc(ArrayList<ProductoBean> inventario
            , String codigo, int suc) {
        ProductoBean prod = null;
        for (ProductoBean p: inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
               if (p.getIdSucursal() == suc) {
                   prod = p;
                   return prod;
               }
            }
        }
        return prod;
    }
    
    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        ProductoBean prod = buscarProdPorCodSuc(inventario
                , txtCodigoPro.getText().trim()
                , Ingreso.usuario.getIdSucursal());
        if (prod == null) {
            limpiarCajaTexto("","",1);
            btnInventario.requestFocus();
            JOptionPane.showMessageDialog(null, "No se encontro el producto, "
                    + "debes regidtrarlo primero");
            txtCodigoPro.requestFocus(true);
            return;
        }
        prodParcial = prod;
        txtCodigoPro.setText(prodParcial.getCodigo());
        txtDescripcionPro.setText(prodParcial.getDescripcion());
        lblIdProd.setText(String.valueOf(prodParcial.getIdArticulo()).trim());
        txtIva.setText("" + prodParcial.getPorcentajeImpuesto());
        cboUMedida.setSelectedItem(prodParcial.getUnidadMedida());
        //selecciona renglon en tabla productos
        for (int i=0; i<jtProductoCompras.getRowCount();i++) {
            String idProd = String.valueOf(jtProductoCompras.getModel()
                    .getValueAt(i,0));
            if (idProd.equalsIgnoreCase(lblIdProd.getText().trim())) {
                jtProductoCompras.setRowSelectionInterval(i, i);
            }
        }
        //selecciona renglon en tabla productos
        txtCantidadPro.requestFocus(true);        
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtDescripcionProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProActionPerformed
        txtFacturaPro.requestFocus();
    }//GEN-LAST:event_txtDescripcionProActionPerformed

    private void txtObservacionesProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionesProActionPerformed
        cboProveedor.requestFocus();
    }//GEN-LAST:event_txtObservacionesProActionPerformed

    private void btnBorrarActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActualActionPerformed
        limpiarCajaTexto(txtFacturaPro.getText(), txtNoCompra.getText(),
                cboProveedor.getSelectedIndex());
        txtCodigoPro.requestFocus();
    }//GEN-LAST:event_btnBorrarActualActionPerformed

    private void jtProductoComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoComprasMouseClicked
        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoComprasMouseClicked

    private void jtProductoComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoComprasKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaDetalleProducto();
        }
    }//GEN-LAST:event_jtProductoComprasKeyReleased

    private void txtIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIvaActionPerformed
        //calcula precio publico
        double precioCompra;
        double PrecioPublico;
        try {
            precioCompra = Double.parseDouble(txtPrecioCompraPro.getText());
        } catch (java.lang.NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debes registrar "
                    + "un precio de compra");
            txtPrecioCompraPro.requestFocus(true);
            return;
        }
        double iva = Double.parseDouble(txtIva.getText());
        PrecioPublico = precioCompra + ((iva/100) * precioCompra);
        txtPrecioVentaPro.setText("" + PrecioPublico);
        
        double ganancia = PrecioPublico - precioCompra;
        txtUtilidad.setText(String.format("%.2f", ganancia));
        
        txtObservacionesPro.requestFocus();
    }//GEN-LAST:event_txtIvaActionPerformed

    private void btnSalirPro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPro1ActionPerformed
        cargaDatos = false;
        FrmProveedor frmProveedor = new FrmProveedor(1);
        frmProveedor.setVisible(true);
    }//GEN-LAST:event_btnSalirPro1ActionPerformed

    private void txtIvaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIvaFocusGained
        if (detalleCompraTotal.size()<1) {
            txtIva.setToolTipText("Ingresa el número de Factura");
        }
    }//GEN-LAST:event_txtIvaFocusGained

    private void btnSalirPro2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPro2ActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnSalirPro2ActionPerformed

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
    
    private ArrayList<ProductoBean> llenaTablaInventario(String buscar, int tipoBusq) {
        ArrayList<ProductoBean> resultWS = new ArrayList<ProductoBean>();
        ProductoBean producto = null;
        for (int i=0; i<jtProductoCompras.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 1 : campoBusq = jtProductoCompras.getModel().getValueAt(
                    i,1).toString();
                    break;
                case 2 : campoBusq = jtProductoCompras.getModel().getValueAt(
                    i,2).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = jtProductoCompras.getModel().getValueAt(
                    i,6).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                producto = new ProductoBean();
                producto.setIdArticulo(Integer.parseInt(jtProductoCompras
                        .getModel().getValueAt(i,0).toString()));
                producto.setCodigo(jtProductoCompras.getModel()
                        .getValueAt(i,1).toString());
                producto.setDescripcion(jtProductoCompras.getModel().getValueAt(
                    i,2).toString());
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM, 
                    sucursalSistema);
                producto.setIdSucursal(idSuc);
                producto.setPrecioCosto(Double.parseDouble(jtProductoCompras
                        .getModel().getValueAt(i,3).toString()));
                producto.setPrecioUnitario(Double.parseDouble(jtProductoCompras
                        .getModel().getValueAt(i,4).toString()));
                producto.setExistencia(Double.parseDouble(jtProductoCompras
                        .getModel().getValueAt(i,5).toString()));
                resultWS.add(producto);
            }
        }
        return resultWS;
    }

    public void recargarTable(ArrayList<ComprasBean> list) {
        try {
            if (list.size() == 0) {
                DefaultTableModel modelo = (DefaultTableModel) TblCompras.getModel();
                while(modelo.getRowCount()>0)
                    modelo.removeRow(0);
            }       
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (ComprasBean p : list) {
            datos[i][0] = p.getIdCompra();
            datos[i][1] = p.getFactura();
            i++;
        }
        jtProductoCompras.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "No.Compra", "Factura"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        } catch(NullPointerException e) {
        }   
    }
    
    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][6];
        int i = 0;
        for (ProductoBean p : list) {
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes
                            .getProperty("SUPERUSUARIO")))) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = p.getCodigo();
                datos[i][2] = p.getDescripcion();
                datos[i][3] = p.getPrecioCosto();
                datos[i][4] = p.getPrecioUnitario();
                datos[i][5] = p.getExistencia();
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
            }
        }
        //Fin Para filtrar los registros
        
        jtProductoCompras.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
                new String[]{
                    "ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO", "$ PÚBLICO", "EXIST."
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jtProductoCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtProductoCompras.getColumnModel().getColumn(0).setMaxWidth(0);
        jtProductoCompras.getColumnModel().getColumn(1).setPreferredWidth(0);
        jtProductoCompras.getColumnModel().getColumn(1).setMaxWidth(0);
    } 
    
    //Para Tabla Venta Parcial
    public void recargarTableCompraParcialProductos(List<DetalleCompraBean> list) {
//        try {
            if (list == null) {
                DefaultTableModel modelo = (DefaultTableModel) TblCompras.getModel();
                while(modelo.getRowCount()>0)
                    modelo.removeRow(0);
            }
            Object[][] datos = new Object[list.size()][5];
            int i = 0;
            for (DetalleCompraBean p : list) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = util.buscaDescFromIdProd(Principal.productosHMID,"" 
                        + p.getIdArticulo());
                datos[i][2] = p.getCantidad();            
                datos[i][3] = p.getPrecioCosto();
                
                datos[i][4] = p.getPrecioPublico();
                i++;
            }
            TblCompras.setModel(new javax.swing.table.DefaultTableModel(
                    datos,
                    new String[]{
                        "ID", "DESCRIPCION", "CANTIDAD", "$ COSTO", "$ VENTA"
                   }) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            TblCompras.getColumnModel().getColumn(0).setPreferredWidth(1);
            TblCompras.getColumnModel().getColumn(0).setMaxWidth(1);
    } 

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
//            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new FrmCompras().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblCompras;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrarActual;
    private javax.swing.JButton btnCancelarPro;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnSalirPro;
    private javax.swing.JButton btnSalirPro1;
    private javax.swing.JButton btnSalirPro2;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JComboBox cboProveedor;
    private javax.swing.JComboBox cboUMedida;
    private com.toedter.calendar.JDateChooser jDateChooserFechaCompra;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtProductoCompras;
    private javax.swing.JLabel lblIdProd;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarPro;
    private javax.swing.JTextField txtCantidadPro;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtFacturaPro;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtIvaCompra;
    private javax.swing.JTextField txtIvaPago;
    private javax.swing.JTextField txtMontoApagar;
    private javax.swing.JTextField txtNoCompra;
    private javax.swing.JTextField txtObservacionesPro;
    private javax.swing.JTextField txtPrecioCompraPro;
    private javax.swing.JTextField txtPrecioVentaPro;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtUtilidad;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}