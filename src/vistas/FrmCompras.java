package vistas;

import ComponenteConsulta.JDListaCompras;
import beans.ProductoBean;
import ComponenteConsulta.JDListaProducto;
import ComponenteDatos.*;
import ComponenteReportes.ReporteProductoParametro;
import ComponenteDatos.ConfiguracionDAO;
import beans.CategoriaBean;
import beans.ComprasBean;
import beans.DatosEmpresaBean;
import beans.FechaServidorBean;
import beans.ProductosProveedoresCostosBean;
import beans.ProveedorBean;
import beans.UsuarioBean;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class FrmCompras extends javax.swing.JFrame {
    FechaServidorBean fechaServidorBean;

    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    int codigoProveedor;
    DatosEmpresaBean configuracionBean;
    ConfiguracionDAO configuracionDAO;
    ProductosProveedoresCostosBean productosProveedoresCostosBean;
    BDProductosProveedoresCostos bdProductosProveedoresCostos;
    
    HashMap<Integer, String> NombreProveedor = new HashMap<Integer, String>();
    HashMap<String, String> NombreProducto = new HashMap<String, String>();
    HashMap<String, String> utilidadCategoriaHMap = new HashMap<>();
    
    //Compras
    BDCompras bdDetalleCompra;
    ComprasBean comprasBean = new ComprasBean();
    ArrayList<ComprasBean> detalleCompraBean = new ArrayList<>();

    //Variable que guarda el numero de compra
    int noCompraGral;
    
    public FrmCompras() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        jtProductoCompras.requestFocusInWindow();
        jtProductoCompras.changeSelection(0, 0, false, false);        
        
//        txtUtilidadPro.setEnabled(false);
        txtCodigoPro.requestFocus();
        //txtUtilidadPro.setText("");        
        
        jDateChooserFechaCompra.setLocation(jDateChooserFechaCompra.getX(), jDateChooserFechaCompra.getY()+20);
        
        // Actualizas tbl proveedor
        actualizarBusquedaCompras();
        // Actualizas tbl producto
        ArrayList<ProductoBean> result;  
        try {
            result = BDProducto.mostrarProducto();
            recargarTableProductos(result);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        //Carga HashMap de Provvedores
        try {
            NombreProveedor = BDProveedor.mostrarProveedorHashMap();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        // Para cargar la lista de Categorias al combobox
        // y carga hashmap de precios
        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select cCatDescripcion,utilidad from categoria");
            while (rs.next()) {
                cboCategoriaPro.addItem(rs.getObject(1));
                utilidadCategoriaHMap.put(""+rs.getObject(1),""+rs.getObject(2));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
        
        // Para cargar la lista de Proveedores al combobox
        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select cProvNombre,nProvCodigo from proveedor");
            while (rs.next()) {
                cboProovedorDescrip.addItem(rs.getObject(1));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        
        //Carga tabla compras parciales
        ArrayList<ComprasBean> resultCompras = new ArrayList<>();
        try {
            resultCompras = BDCompras.mostrarCompra(-1);
            recargarTablaCompraParcial(resultCompras);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
//        String titulos[] = {"DESCRIPCIÓN", "CANTIDAD", "OBSERVACIONES", "PRECIO VENTA", "PROVEEDOR", "FECHA COMPRA"};
//        TblCompras.setColumnModel(titulos);        
        
        //obtiene numero de compra
        noCompraGral = BDCompras.obtenerUltimoId();
                
        this.setLocationRelativeTo(null);
        configuracionBean = new DatosEmpresaBean();
        configuracionDAO = new ConfiguracionDAO();
        configuracionBean = configuracionDAO.obtieneConfiguracion(1);
        //txtUtilidadPro.setText(""+configuracionBean.getUtilidad());
        //txtIVA.setText(""+configuracionBean.getIva());
        this.setTitle("Compras");
        
        /* Se cambio por hora del servidor
        jDateChooserFechaPro.setDate(new Date());
        */
        //muestra fecha servidor
//        try {
//            if (BDFechaServidor.actualizarFecha()) {
//                fechaServidorBean = BDFechaServidor.mostrarFechaServidor();
////                String a = DateFormat.getDateInstance(DateFormat.MEDIUM).format(fechaServidorBean.getFechaServidor());
//                jDateChooserFechaCompra.setDateFormatString("d/MM/yyyy");
//                jDateChooserFechaCompra.setDate(fechaServidorBean.getFechaServidor());
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        cboCategoriaPro.setVisible(false);
//        lblCodProv.setVisible(false);
        
        //solo para acceso local
        java.util.Date fechaLocal = new Date();
//        String a = DateFormat.getDateInstance(DateFormat.LONG).
//                format(fechaLocal);
        jDateChooserFechaCompra.setDateFormatString("d/MM/yyyy");
        jDateChooserFechaCompra.setDate(fechaLocal);
        //fin solo para acceso local
        
    }

    public void muestraUtilidad() {
        txtUtilidad.requestFocus();
    }
    
    public void limpiarCajaTexto() {
//        txtPorcDesc.setText("");
        txtCodigoPro.setText("");
        cboCategoriaPro.setSelectedItem("Seleccionar...");
        cboProovedorDescrip.setSelectedItem("Seleccionar...");
        txtCantidadPro.setText("");
        txtPrecioCompraPro.setText("");
        txtPrecioVentaPro.setText("");
        txtDescripcionPro.setText("");
        txtObservacionesPro.setText("");
//        txtPrecioSinIva.setText("");
//        txtPrecioMaxPub.setText("");
//        txtUtilidadPro.setText(""+configuracionBean.getUtilidad());
        //txtUtilidadPro.setText("");
    }

    public void activarCajaTexto(boolean b) {
//        txtDescripcionPro.setEditable(b);
//        txtPrecioVentaPro.setEditable(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCompras = new javax.swing.JTable();
        txtBuscarCompra = new javax.swing.JTextField();
        cboParametroCompras = new javax.swing.JComboBox();
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
        cboCategoriaPro = new javax.swing.JComboBox();
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
        cboProovedorDescrip = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jDateChooserFechaCompra = new com.toedter.calendar.JDateChooser();
        btnNuevoPro = new javax.swing.JButton();
        btnGuardarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnCancelarPro = new javax.swing.JButton();
        btnMostrarPro = new javax.swing.JButton();
        btnSalirPro = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnBorrarActual = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtUtilidad = new javax.swing.JTextField();
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

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel1.setText("BUSCAR COMPRA ");

        jtCompras.setModel(new javax.swing.table.DefaultTableModel(
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
                "No. Compra", "Factura", "Edo. Venta"
            }
        ));
        jtCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtComprasMouseClicked(evt);
            }
        });
        jtCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtComprasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtCompras);

        txtBuscarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCompraActionPerformed(evt);
            }
        });
        txtBuscarCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCompraKeyReleased(evt);
            }
        });

        cboParametroCompras.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No. Compra", "Factura" }));
        cboParametroCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroComprasActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel17.setText("BUSCAR PRODUCTO ");

        txtBuscarPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProKeyReleased(evt);
            }
        });

        cboParametroPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Nombre" }));
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtBuscarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboParametroCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(47, 47, 47)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(47, 47, 47)))
                    .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

        cboCategoriaPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboCategoriaPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboCategoriaProMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cboCategoriaProMouseEntered(evt);
            }
        });
        cboCategoriaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaProActionPerformed(evt);
            }
        });
        cboCategoriaPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCategoriaProKeyReleased(evt);
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

        jLabel8.setText("Precio Costo :");

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
        jLabel9.setText("Precio Venta :");

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

        jLabel16.setText("Proveedor :");

        cboProovedorDescrip.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboProovedorDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboProovedorDescripKeyTyped(evt);
            }
        });

        jLabel4.setText("F. Adquisición");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jDateChooserFechaCompra, org.jdesktop.beansbinding.ObjectProperty.create(), jLabel4, org.jdesktop.beansbinding.BeanProperty.create("labelFor"));
        bindingGroup.addBinding(binding);

        jDateChooserFechaCompra.setDateFormatString("yyyy-MM-d");

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addVenta.png"))); // NOI18N
        btnNuevoPro.setText("AGREGAR");
        btnNuevoPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });
        btnNuevoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoProKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnNuevoProKeyReleased(evt);
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

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarPro.setText("ELIMINAR");
        btnEliminarPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
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

        jLabel3.setText("Categoría");

        btnBorrarActual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnBorrarActual.setText("LIMPIAR");
        btnBorrarActual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBorrarActual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrarActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActualActionPerformed(evt);
            }
        });

        jLabel5.setText("Utilidad :");

        txtUtilidad.setBackground(new java.awt.Color(255, 153, 102));
        txtUtilidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUtilidad.setText("0");
        txtUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUtilidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDescripcionPro, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFacturaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnNuevoPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarPro)
                                .addGap(7, 7, 7)
                                .addComponent(btnBorrarActual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardarPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelarPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMostrarPro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(5, 5, 5)
                                .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtObservacionesPro, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCategoriaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPrecioCompraPro, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooserFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cboProovedorDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(txtFacturaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtObservacionesPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCategoriaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtPrecioCompraPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(cboProovedorDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(jDateChooserFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnGuardarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalirPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMostrarPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnBorrarActual))
                .addGap(28, 28, 28)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario)
                        .addGap(186, 186, 186))))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 679, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 258, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProActionPerformed
        this.dispose();
        Ventas ventas = new Ventas();
    }//GEN-LAST:event_btnSalirProActionPerformed

    private void borrar() {
        limpiarCajaTexto();
        activarCajaTexto(false);
        
        //REINICIA TABLAS
        // Actualizas tbl proveedor
        actualizarBusquedaCompras();
        // Actualizas tbl producto
        ArrayList<ProductoBean> result;  
        ArrayList<ComprasBean> result1 = null;
        try {
            result = BDProducto.mostrarProducto();
            recargarTableProductos(result);
            //LIMPIA TABLA prodprovcost
            result1 = BDCompras.mostrarCompra(-1);
            recargarTablaCompraParcial(result1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        txtCodigoPro.requestFocus();
        detalleCompraBean.clear();
    }
    
    private void btnCancelarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarProActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        if (detalleCompraBean.size() > 0) {
            try {
                //aumento o alta de producto en inventario
                ProductoBean prodAAumentar = null;    
                for (ComprasBean compraParcial :detalleCompraBean) {
                    prodAAumentar = BDProducto.buscarProducto(compraParcial.getCodigo(), prodAAumentar);
                    if (prodAAumentar != null) {
                        prodAAumentar.setCantidad(prodAAumentar.getCantidad()
                                + compraParcial.getCantidad());
                        //TAMBIEN AGREGO PRECIO PUBLICO
                        prodAAumentar.setPrecioPublico(compraParcial.getPrecioVenta());
                        prodAAumentar.setPrecioCosto(compraParcial.getPrecioCosto());
                        BDProducto.actualizarProducto(prodAAumentar);
                    } else {
                        //Nuevo
                        //Crea y Llena objeto producto para insertarlo
                        ProductoBean nvoProd = new ProductoBean();
                        nvoProd.setCodigo(compraParcial.getCodigo());
                        nvoProd.setDescripcion(compraParcial.getDescripcion());
                        nvoProd.setCantidad(compraParcial.getCantidad());
                        CategoriaBean c = BDCategoria.buscarCategoriaDescripcion(
                                compraParcial.getCategoria());
                        nvoProd.setCategoria(c);
                        nvoProd.setFormula("");
                        nvoProd.setUbicacion("");
                        nvoProd.setObservaciones("");
                        nvoProd.setFactura("");
                        nvoProd.setMinimo(0);
                        nvoProd.setPrecioPublico(compraParcial.getPrecioVenta());
                        nvoProd.setPrecioCosto(compraParcial.getPrecioCosto());
                        BDProducto.insertarProducto(nvoProd);
                    }
                }                    
                //Fin aumento de producto en inventario
                
                
                ComprasBean p = new ComprasBean();
                if (BDCompras.insertarCompra(detalleCompraBean)) {
                    txtFacturaPro.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "[ Error ]");                        
                }
                actualizarBusquedaCompras();

                //Guarda precio en prodprovcostos
                for (ComprasBean compraParcial : detalleCompraBean) {
                    productosProveedoresCostosBean = new 
                                        ProductosProveedoresCostosBean();
                    productosProveedoresCostosBean.setnProvCodigo(
                            compraParcial.getnProvCodigo());
                    productosProveedoresCostosBean.setCodigo(
                            compraParcial.getCodigo());
                    productosProveedoresCostosBean.setPrecioCosto(
                            compraParcial.getPrecioVenta());
//                    productosProveedoresCostosBean.setFecha(
//                            compraParcial.getFechaCompra());
                    productosProveedoresCostosBean.setIdUsuario(
                            compraParcial.getIdUsuario());
                    
                    //nuevos
                    productosProveedoresCostosBean.setCategoria(compraParcial.
                            getCategoria());
                    productosProveedoresCostosBean.setPrecioCosto(compraParcial.
                            getPrecioCosto());
                    productosProveedoresCostosBean.setPrecioMaxPublico(compraParcial.
                            getPrecioMaxPublico());
                    productosProveedoresCostosBean.setPrecioSinIva(compraParcial.
                            getPrecioSinIva());
                    productosProveedoresCostosBean.setPorcentajeDescuento(compraParcial.
                            getPorcentajeDescuento());
                    productosProveedoresCostosBean.setPrecioVenta(compraParcial.getPrecioVenta());
                    
                    //realiza el guardado en la bd
                    bdProductosProveedoresCostos = new BDProductosProveedoresCostos();
                    try {
                        if (bdProductosProveedoresCostos.insertarProducto(
                                productosProveedoresCostosBean)) {
                            bdProductosProveedoresCostos.
                                    buscarProductoCodigoDescripcionEliminaMenor(
                                            productosProveedoresCostosBean);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                    
                }                    
                //Fin Guarda precio en prodprovcostos
                JOptionPane.showMessageDialog(null,"[ Datos Agregados ]");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null,"NO HAY COMPRAS POR GUARDAR");
        }
        noCompraGral = BDCompras.obtenerUltimoId();
        borrar();    
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void cboParametroComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroComprasActionPerformed
        actualizarBusquedaCompras();
    }//GEN-LAST:event_cboParametroComprasActionPerformed

    private void txtBuscarCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCompraKeyReleased
        actualizarBusquedaCompras();
    }//GEN-LAST:event_txtBuscarCompraKeyReleased

    private void buscaComprasTbl() {
        ArrayList<ComprasBean> result = null;
        try {
            result = BDCompras.buscarCompraPorCodigoGeneral(
                (Integer)(jtCompras.getModel().getValueAt(jtCompras.getSelectedRow(),0))                );
            recargarTablaCompraParcial(result);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    private void jtComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtComprasMouseClicked
        buscaComprasTbl();
    }//GEN-LAST:event_jtComprasMouseClicked

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

    private void btnMostrarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarProActionPerformed
        JDListaCompras jdListaC = new JDListaCompras(this, true);
        jdListaC.setVisible(true);
    }//GEN-LAST:event_btnMostrarProActionPerformed
   
    private void cboCategoriaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaProActionPerformed
        muestraUtilidad();
    }//GEN-LAST:event_cboCategoriaProActionPerformed

    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

    private void cboParametroProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroProActionPerformed
        actualizarBusquedaProducto();
    }//GEN-LAST:event_cboParametroProActionPerformed

    private void buscaDatosProducto() {
        try {
            ProductoBean producto = BDProducto.buscarProducto(
                    String.valueOf(jtProductoCompras.getModel().getValueAt(jtProductoCompras.getSelectedRow(),0)));
            txtCodigoPro.setText(producto.getCodigo());
            txtDescripcionPro.setText(producto.getDescripcion());
            cboCategoriaPro.setSelectedItem((String)producto.getCategoria().getcCatDescripcion());
            cboProovedorDescrip.setSelectedItem(NombreProveedor.get(producto.getCodProveedor()));
            txtObservacionesPro.setText(producto.getObservaciones());
//        txtUtilidadPro.setText(""+utilidadCategoriaHMap.get(cboCategoriaPro.
//                getSelectedItem()));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error Al Seleccionar Elemento:" + ex.getMessage());
        }
        //txtFacturaPro.requestFocus();
    }
    
    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased
    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void TblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblComprasMouseClicked
    }//GEN-LAST:event_TblComprasMouseClicked

    private void cboCategoriaProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaProKeyTyped
//        muestraUtilidad();
        int key=evt.getKeyCode();
        if(key==0){ 
            muestraUtilidad();
            txtUtilidad.requestFocus();        
        }
    }//GEN-LAST:event_cboCategoriaProKeyTyped

    private void txtPrecioCompraProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCompraProActionPerformed
        txtPrecioVentaPro.setText(""+
                ((Double.parseDouble(txtPrecioCompraPro.getText())+
        (Double.parseDouble(txtPrecioCompraPro.getText())*(
                Double.parseDouble(txtUtilidad.getText())/100)))));
        //muestra 2 decimales en porc de descuento
        DecimalFormat df = new DecimalFormat("#.##");   
        txtPrecioVentaPro.setText(""+df.format(Double.parseDouble(txtPrecioVentaPro.getText())));        
        cboProovedorDescrip.requestFocus();
//        txtPrecioMaxPub.requestFocus();
    }//GEN-LAST:event_txtPrecioCompraProActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
         lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
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
        txtObservacionesPro.requestFocus();
    }//GEN-LAST:event_txtCantidadProActionPerformed

    public void agregaCompraParcial() {
        // ********* Validaciones
        //para codigo y descripcion
        if (txtCodigoPro.getText().equalsIgnoreCase("") || 
                (txtDescripcionPro.getText().equalsIgnoreCase(""))) {
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
//        //para precio max al publico
//        if (txtPrecioMaxPub.getText().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(null, "DEBE EXISTIR UN PRECIO "
//                    + "MAX. AL PÚBLICO ESTABLECIDO");
//            return;
//        }
        //para factura
        if (txtFacturaPro.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "DEBE EXISTIR UN NÚMERO "
                    + "DE FACTURA");
            return;
        }
        //para cod de proveedor
        if (String.valueOf(
                    cboProovedorDescrip.getSelectedItem()).equalsIgnoreCase
                        ("Seleccionar...")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un "
                    + "proveedor");
            cboProovedorDescrip.requestFocus();
            return;
        }
        //valida que la fecha este establecida
        if (jDateChooserFechaCompra.getDateFormatString().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null,"No hay fecha establecido.");
            return;
        }         
        //valida que exista usuario
        if (Ingreso.usuario.getNombre().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Reinicia el sistema porque "
                    + "no hay usuario selecionado");
            return;            
        }
        //valida que haya categoria seleccionada
        if (String.valueOf(
                    cboCategoriaPro.getSelectedItem()).equalsIgnoreCase
                        ("Seleccionar...")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una "
                    + "Categoría");
            cboCategoriaPro.requestFocus();
            return;
        }        
        
        // fin ********* Validaciones
        
        //lleno el objeto compra
        ComprasBean compraParcial = new ComprasBean();
        compraParcial.setNoCompra(noCompraGral);
        compraParcial.setCodigo(txtCodigoPro.getText());
        compraParcial.setCantidad(Integer.parseInt(txtCantidadPro.getText()));
        compraParcial.setObservaciones(txtObservacionesPro.getText());
        compraParcial.setFactura(txtFacturaPro.getText());            
        compraParcial.setPrecioVenta(Double.parseDouble(txtPrecioVentaPro.getText()));
        
        try {
            ProveedorBean provBeanTemp = new ProveedorBean();
            provBeanTemp = BDProveedor.mostrarProveedorPorNombre(String.valueOf(
                    cboProovedorDescrip.getSelectedItem()));
            compraParcial.setnProvCodigo(provBeanTemp.getnProvCodigo());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }            
        //para fecha
        DateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
        java.util.Date fecha = null; // crea objetos tipo util.Date y sql.Date
        java.sql.Timestamp fecha2 = null;
        fecha = jDateChooserFechaCompra.getDate();
        java.sql.Timestamp sq = new java.sql.Timestamp(fecha.getTime());
        fecha2 = sq; 
        compraParcial.setFechaCompra(fecha2.toString());
        compraParcial.setEdoVenta(true);
        compraParcial.setIdUsuario(Ingreso.usuario.getIdUsuario()); 
        
        //verifica que la compra no este en la lisra
//        if (buscaRepetido(compraParcial)) {
//            JOptionPane.showMessageDialog(null, "Ya existe el pproducto en la lista");
//        }

        //nuevos 
        compraParcial.setCategoria(String.valueOf(cboCategoriaPro.getSelectedItem()));
        compraParcial.setPrecioCosto(Double.parseDouble(txtPrecioCompraPro.getText()));
//        compraParcial.setPrecioMaxPublico(Double.parseDouble(txtPrecioMaxPub.getText()));
//        if (txtPrecioSinIva.getText().equalsIgnoreCase("")) {
//            compraParcial.setPrecioSinIva(0);
//        } else {
//            compraParcial.setPrecioSinIva(Double.parseDouble(txtPrecioSinIva.getText()));
//        }
//        if (txtPorcDesc.getText().equalsIgnoreCase("")) {
//            compraParcial.setPorcentajeDescuento(0);
//        } else {
//            compraParcial.setPorcentajeDescuento(Double.parseDouble(txtPorcDesc.getText()));
//        }
        
        //nuevo 2
        compraParcial.setDescripcion(txtDescripcionPro.getText());
        compraParcial.setPrecioVenta(Double.parseDouble(txtPrecioVentaPro.getText()));
        
        //agrega compra a la lista
        detalleCompraBean.add(compraParcial);
        recargarTablaCompraParcial(detalleCompraBean);
        
        //prepara para agregar nueva compra
        limpiarCajaTexto();
        activarCajaTexto(false);
        cboProovedorDescrip.setSelectedIndex(0);
        txtCodigoPro.requestFocus();
    }

    private boolean buscaRepetido(ComprasBean compraParcialP) {
        boolean existe = false;
        for (ComprasBean compraParcial : detalleCompraBean) {
            if (compraParcialP.getCodigo().equalsIgnoreCase(compraParcial.getCodigo())) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        agregaCompraParcial();
        txtCodigoPro.requestFocus();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnNuevoProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoProKeyPressed
    }//GEN-LAST:event_btnNuevoProKeyPressed

    private void cboProovedorDescripKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboProovedorDescripKeyTyped
        btnNuevoPro.requestFocus();
    }//GEN-LAST:event_cboProovedorDescripKeyTyped

    private boolean eliminaProdDeCompra(String codigo) {
        boolean existe = false;
        for (ComprasBean detalleCompra : detalleCompraBean) {
            if (detalleCompra.getCodigo().equalsIgnoreCase(codigo)) {
                detalleCompraBean.remove(detalleCompra);
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
            String codigo = String.valueOf(TblCompras.getModel().getValueAt(TblCompras.getSelectedRow(),0));
            if (eliminaProdDeCompra(codigo)) {
                try {
                    ((DefaultTableModel)TblCompras.getModel()).removeRow(fila);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
//                actualizaTotales(detalleVentaProducto);
            }
        } else {
            JOptionPane.showMessageDialog(null, "DEBES SELECCIONAR UN PRODUCTO");
            return;
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void txtBuscarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCompraActionPerformed

    private void txtFacturaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacturaProActionPerformed
        txtCantidadPro.requestFocus();
    }//GEN-LAST:event_txtFacturaProActionPerformed

    private void btnNuevoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoProKeyReleased
         if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            agregaCompraParcial();            
            //btnNuevoProActionPerformed(null); 
        }
    }//GEN-LAST:event_btnNuevoProKeyReleased

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        FrmProducto frmP = new FrmProducto();
        frmP.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        try {   
            // si esta el producto lo pone
            ProductoBean producto = BDProducto.buscarProducto(txtCodigoPro.getText());
            if (producto != null) {
                txtCodigoPro.setText(producto.getCodigo());
                txtDescripcionPro.setText(producto.getDescripcion());
                cboCategoriaPro.setSelectedItem((String)producto.getCategoria().getcCatDescripcion());
                muestraUtilidad();

                txtFacturaPro.requestFocus();        
            } else {
//                limpiarCajaTexto();
//                activarCajaTexto(false);
                txtDescripcionPro.requestFocus();
//                btnInventario.requestFocus();
//                JOptionPane.showMessageDialog(null,"NO SE ENCUENTRA EL PRODUCTO "
//                        + "EN INVENTARIO DEBES REGISTRARLO PRIMERO");
            }
            //si no esta el porducto continua
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error Al Seleccionar Elemento:" + ex.getMessage());
        }
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtDescripcionProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProActionPerformed
        txtFacturaPro.requestFocus();
    }//GEN-LAST:event_txtDescripcionProActionPerformed

    private void txtObservacionesProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionesProActionPerformed
        muestraUtilidad(); 
        cboCategoriaPro.requestFocus();
    }//GEN-LAST:event_txtObservacionesProActionPerformed

    private void btnBorrarActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActualActionPerformed
        ArrayList<ComprasBean> detalleCompraBeanTemp = new ArrayList<>();
        recargarTablaCompraParcial(detalleCompraBeanTemp);        
        limpiarCajaTexto();
        txtCodigoPro.requestFocus();
    }//GEN-LAST:event_btnBorrarActualActionPerformed

    private void jtComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtComprasKeyReleased
        buscaComprasTbl();
    }//GEN-LAST:event_jtComprasKeyReleased

    private void jtProductoComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoComprasMouseClicked
        buscaDatosProducto();
        jtProductoCompras.requestFocus();
    }//GEN-LAST:event_jtProductoComprasMouseClicked

    private void jtProductoComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoComprasKeyReleased
        buscaDatosProducto();
    }//GEN-LAST:event_jtProductoComprasKeyReleased

    private void cboCategoriaProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaProKeyReleased
    }//GEN-LAST:event_cboCategoriaProKeyReleased

    private void cboCategoriaProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboCategoriaProMouseClicked
    }//GEN-LAST:event_cboCategoriaProMouseClicked

    private void cboCategoriaProMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboCategoriaProMouseEntered
    }//GEN-LAST:event_cboCategoriaProMouseEntered

    private void txtUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUtilidadActionPerformed
        txtPrecioCompraPro.requestFocus();
    }//GEN-LAST:event_txtUtilidadActionPerformed

    private void actualizarBusquedaCompras() {
        ArrayList<ComprasBean> result = null;
        if ((String.valueOf(cboParametroCompras.getSelectedItem()).equalsIgnoreCase("No. Compra")) 
              && (!txtBuscarCompra.getText().equalsIgnoreCase(""))) {
            try {
                result = BDCompras.buscarCompraPorCodigo(Integer.parseInt(txtBuscarCompra.getText()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            if (String.valueOf(cboParametroCompras.getSelectedItem()).equalsIgnoreCase("Factura")) {
                if (!txtBuscarCompra.getText().equalsIgnoreCase("")) {
                    try {
                        result = BDCompras.buscarCompraPorFactura(txtBuscarCompra.getText());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    try {
                        result = BDCompras.mostrarCompras();
                    } catch (SQLException ex) {
                        Logger.getLogger(FrmCompras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                try {
                    result = BDCompras.mostrarCompras();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }            
        } 
        recargarTable(result);
    }
    
    private void actualizarBusquedaProducto() {
        ArrayList<ProductoBean> result = null;
        if (String.valueOf(cboParametroPro.getSelectedItem()).equalsIgnoreCase("Código")) {
            result = BDProducto.listarProductoPorCodigo(txtBuscarPro.getText());
        } else {
            if (String.valueOf(cboParametroPro.getSelectedItem()).equalsIgnoreCase("Nombre")) {
                //if (!txtBuscarPro.getText().equalsIgnoreCase("")) {
                    result = BDProducto.listarProductoPorDescripcion(txtBuscarPro.getText());                
                //}
            }
        } 
        recargarTableProductos(result);
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
            datos[i][0] = p.getNoCompra();
            datos[i][1] = p.getFactura();
            i++;
        }
        jtCompras.setModel(new javax.swing.table.DefaultTableModel(
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
//        TableColumn columna1 = TblCompras.getColumn("ID");
//        //columna1.setPreferredWidth(1);
//        columna1.setMaxWidth(10);
//        TableColumn columna2 = TblCompras.getColumn("CPROV");
//        //columna2.setPreferredWidth(1);
//        columna2.setMaxWidth(10);
//        TableColumn columna3 = TblCompras.getColumn("COD.PROD");
//        columna3.setPreferredWidth(10);
    }
    
    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (ProductoBean p : list) {
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getDescripcion();
            NombreProducto.put(p.getCodigo(), p.getDescripcion());
            i++;
        }
        jtProductoCompras.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "CODIGO", "DESCRIPCIÓN"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 
    
    public void recargarTablaCompraParcial(ArrayList<ComprasBean> list) {
        try {
            if (list.size() == 0) {
                DefaultTableModel modelo = (DefaultTableModel) TblCompras.getModel();
                while(modelo.getRowCount()>0)
                    modelo.removeRow(0);
            }       
            Object[][] datos = new Object[list.size()][7];
            int i = 0;
            for (ComprasBean p : list) {
                datos[i][0] = p.getCodigo();
//                datos[i][1] = NombreProducto.get(p.getCodigo());
                datos[i][1] = p.getDescripcion();
                datos[i][2] = p.getCantidad();
                datos[i][3] = p.getObservaciones(); 
                datos[i][4] = p.getPrecioCosto();
                datos[i][5] = NombreProveedor.get(p.getnProvCodigo());
                datos[i][6] = p.getFechaCompra();
                i++;
            }
            TblCompras.setModel(new javax.swing.table.DefaultTableModel(
                    datos,
                    new String[]{
                        "CÓDIGO","DESCRIPCIÓN", "CANTIDAD", "OBSERVACIONES", "PRECIO COSTO", "PROVEEDOR", "FECHA COMPRA"
                    }) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch(NullPointerException e) {
        }   
    } 

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
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmCompras().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblCompras;
    private javax.swing.JButton btnBorrarActual;
    private javax.swing.JButton btnCancelarPro;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnMostrarPro;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnSalirPro;
    private javax.swing.JComboBox cboCategoriaPro;
    private javax.swing.JComboBox cboParametroCompras;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JComboBox cboProovedorDescrip;
    private com.toedter.calendar.JDateChooser jDateChooserFechaCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtCompras;
    private javax.swing.JTable jtProductoCompras;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarCompra;
    private javax.swing.JTextField txtBuscarPro;
    private javax.swing.JTextField txtCantidadPro;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtFacturaPro;
    private javax.swing.JTextField txtObservacionesPro;
    private javax.swing.JTextField txtPrecioCompraPro;
    private javax.swing.JTextField txtPrecioVentaPro;
    private javax.swing.JTextField txtUtilidad;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private ReporteProductoParametro repProductoP;
}