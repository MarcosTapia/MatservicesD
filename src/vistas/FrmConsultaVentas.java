package vistas;

import ComponenteConsulta.JDListaCorteDia;
import beans.ProveedorBean;
import ComponenteConsulta.JDListaProveedor;
import beans.ComprasBean;
import beans.DatosEmpresaBean;
import beans.DetallePedidoBean;
import beans.DetalleVentaBean;
import beans.FechaServidorBean;
import beans.MovimientosBean;
import beans.PedidoBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSComprasList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetalleVentas;
import consumewebservices.WSDetalleVentasList;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSPedidos;
import consumewebservices.WSPedidosList;
import consumewebservices.WSVentas;
import consumewebservices.WSVentasList;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.productos;

public class FrmConsultaVentas extends javax.swing.JFrame {
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSVentas hiloVentas;
    WSVentasList hiloVentasList;
    WSDetalleVentasList hiloDetalleVentasList;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    ArrayList<VentasBean> ventasGlobal = null;
    ArrayList<DetalleVentaBean> detalleVentasGlobal = null;
    ArrayList<ProductoBean> inventario = null;

    public FrmConsultaVentas() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        // Actualizas tbl Ventas
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETVENTAS");
        ventasGlobal = hiloVentasList.ejecutaWebService(rutaWS,"1");
        recargarTableVentas(ventasGlobal);

        inventario = util.getMapProductos();
        productos = util.getMapProductos();
        util.llenaMapProductos(productos);
        
        // Actualizas tbl DetalleVentas
        hiloDetalleVentasList = new WSDetalleVentasList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETDETALLEVENTAS");
        detalleVentasGlobal = hiloDetalleVentasList.ejecutaWebService(rutaWS,"1");
        recargarTableDetalleVentas(detalleVentasGlobal);
        
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        
//        //inhabilita combos
//        cboSucursal.setEnabled(false);
//        cboProveedor.setEnabled(false);
//        cboCategoriaPro.setEnabled(false);
//
//        //cambia formato de fecha a tipo datetime xq asi esta en bd remota
//        jCalFechaIngresoProd.setDate(new Date());
////        jCalFechaIngresoProd.setDateFormatString("yyyy-MM-dd HH:mm:ss");
//        
//        txtIdArticulo.setVisible(false);
//        btnGuardarPro.setEnabled(false);
        
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        
////        if (this.getLlamadoVentaInventario() == 1) {
////            btnNuevoPro.setVisible(true);
////            btnGuardarPro.setEnabled(true);
////            accion = "Guardar";
////            btnModificarPro.setVisible(false);
////            btnEliminarPro.setVisible(false);
////        }
//        
        limpiaTblDetalleVenta();        
    }
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("..\\img\\matserviceslogo.png")));
    }

    //Para Tabla Ventas
    public void recargarTableVentas(ArrayList<VentasBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//System.out.println(dateFormat.format(new Date()));        
        for (VentasBean p : list) {
            datos[i][0] = p.getIdVenta();
            datos[i][1] = dateFormat.format(p.getFecha());
//            datos[i][1] = p.getFecha();
            datos[i][2] = util.buscaDescFromIdCli(Principal.clientesHM
                    , "" + p.getIdCliente());
            datos[i][3] = util.buscaDescFromIdSuc(Principal.sucursalesHM 
                    , "" + p.getIdSucursal());
            datos[i][4] = util.buscaDescFromIdUsu(Principal.usuariosHM 
                    , "" + p.getIdUsuario());
            datos[i][5] = p.getSubtotal();
            datos[i][6] = p.getIva();
            datos[i][7] = p.getTotal();
            i++;
        }
        tblConsultaVentas.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "No. VENTA", "FECHA VENTA","CLIENTE","SUCURSAL","USUARIO"
                        ,"SUBTOTAL","IVA","TOTAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 

    //Para Tabla DetalleVenta
    public void recargarTableDetalleVentas(ArrayList<DetalleVentaBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        for (DetalleVentaBean p : list) {
//            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
//                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes.getProperty("SUPERUSUARIO")))) {
                datos[i][0] = p.getIdDetalleVenta();
                datos[i][1] = p.getIdVenta();
                datos[i][2] = util.buscaDescFromIdProd(Principal.productosHMID, 
                        "" + p.getIdArticulo());
                datos[i][3] = p.getPrecio();
                datos[i][4] = p.getCantidad();
                datos[i][5] = p.getDescuento();
                datos[i][6] = p.getUnidadMedida();
                datos[i][7] = util.buscaDescFromIdSuc(Principal.sucursalesHM
                        , "" + p.getIdSucursal());
                i++;
//            }
        }
//        Object[][] datosFinal = new Object[i][7];
//        //Para filtrar los registros
//        for (int j=0; j<i; j++) {
//            if (datos[j][0]!=null) {
//                datosFinal[j][0] = datos[j][0];
//                datosFinal[j][1] = datos[j][1];
//                datosFinal[j][2] = datos[j][2];
//                datosFinal[j][3] = datos[j][3];
//                datosFinal[j][4] = datos[j][4];
//                datosFinal[j][5] = datos[j][5];
//                datosFinal[j][6] = datos[j][6];
//            }
//        }
        //Fin Para filtrar los registros
        tblConsultaDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{ 
                    "ID","No. VENTA", "PRODUCTO","PRECIO","CANTIDAD","DESCUENTO"
                        ,"UNIDAD","SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblConsultaDetalleVenta.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblConsultaDetalleVenta.getColumnModel().getColumn(0).setMaxWidth(0);
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarVenta = new javax.swing.JTextField();
        cboParametroVentas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaDetalleVenta = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConsultaVentas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
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

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("CONSULTA DE VENTAS");

        txtBuscarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarVentaActionPerformed(evt);
            }
        });
        txtBuscarVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarVentaKeyReleased(evt);
            }
        });

        cboParametroVentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No. Venta", "Cliente", "Sucursal", "Usuario" }));
        cboParametroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroVentasActionPerformed(evt);
            }
        });

        tblConsultaDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConsultaDetalleVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaDetalleVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblConsultaDetalleVenta);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha Inicio :");

        jLabel3.setText("Fecha Fin :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        jButton2.setText("MOSTRAR");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/report2.png"))); // NOI18N
        jButton4.setText("FACTURAR");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tblConsultaVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConsultaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaVentasMouseClicked(evt);
            }
        });
        tblConsultaVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblConsultaVentasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblConsultaVentas);

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel4.setText("VENTA");

        jLabel5.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel5.setText("DETALLE DE LA VENTA");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(114, 114, 114))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(15, 15, 15)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblUsuario))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboParametroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroVentasActionPerformed

    }//GEN-LAST:event_cboParametroVentasActionPerformed

    private void txtBuscarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarVentaActionPerformed
    }//GEN-LAST:event_txtBuscarVentaActionPerformed

    private void txtBuscarVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaKeyReleased
        actualizarBusquedaVenta();
    }//GEN-LAST:event_txtBuscarVentaKeyReleased

    private void tblConsultaDetalleVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaDetalleVentaMouseClicked
    }//GEN-LAST:event_tblConsultaDetalleVentaMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmInventario inventario = new FrmInventario();
//        inventario.setExtendedState(inventario.MAXIMIZED_BOTH);
//        inventario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblConsultaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaVentasMouseClicked
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaVentasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiaTblDetalleVenta();        
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        // Actualizas tbl Ventas
        ArrayList<VentasBean> ventasPorFechas = null;
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETVENTASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETVENTASPORFECHASFFIN") + fechaFin;
        ventasPorFechas = hiloVentasList.ejecutaWebService(rutaWS,"2");
        recargarTableVentas(ventasPorFechas);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void limpiaTblDetalleVenta() {
        recargarTableDetalleVentas(detalleVentasGlobal);
    }
    
    public void borrar() {
        limpiaTblDetalleVenta();        
        //LIMPIA TXT BUSQUEDA VENTAS
        txtBuscarVenta.setText("");
        actualizarBusquedaVenta();
        
        //limpia jcalendars
        jCalFechaIni.setDate(null);
        jCalFechaFin.setDate(null);           
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        borrar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblConsultaVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConsultaVentasKeyReleased
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaVentasKeyReleased

    public void procesarFactura() {
//        int idPedido = 0;
//        List<DetalleVentaBean> detalleVentaProducto = new ArrayList<>();
//        DetalleVentaBean detalleVentaObj = null;
//        ArrayList<DetallePedidoBean> detallePedido = null;
//        //List<DetallePedidoBean> detallePedido = new ArrayList<>();
//        PedidoBean pedidoBean = null;
//        int result = JOptionPane.showConfirmDialog(this, "¿Deseas procesar el "
//                + "Pedido?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
//        // VERIFICA si realmente se quierte guardar la ventasBean
//        if (result == JOptionPane.YES_OPTION) {
//            pedidoBean = new PedidoBean();
//            //busca pedido
//                //obtiene pedido para guardarlo como ventasBean
//            ArrayList<PedidoBean> resultWS = null;
//            hiloPedidosList = new WSPedidosList();
//            idPedido = Integer.parseInt(
//                    tblConsultaVentas
//                            .getValueAt(tblConsultaVentas.getSelectedRow(), 0).toString()
//                    );
//            String rutaWS = constantes.getProperty("IP") 
//                    + constantes.getProperty("OBTIENEPEDIDOPORID") 
//                    + String.valueOf(idPedido);
//            resultWS = hiloPedidosList.ejecutaWebService(rutaWS,"3");
//            PedidoBean pedido = resultWS.get(0);
//                //fin obtiene pedido para guardarlo como ventasBean
//
//                //convierto pedido a ventasBean para guardarlo como ventasBean
//            VentasBean ventasBean = new VentasBean();
//            ventasBean.setFecha(pedido.getFecha());
//            ventasBean.setIdCliente(pedido.getIdCliente());
//            ventasBean.setIdSucursal(pedido.getIdSucursal());
//            ventasBean.setIdUsuario(pedido.getIdUsuario());
//            int idVenta = obtenerUltimoIdVenta();
//            ventasBean.setIdVenta(idVenta);
//            ventasBean.setObservaciones(pedido.getObservaciones());
//                //fin convierto pedido a ventasBean para guardarlo como ventasBean
//            
//                //convierto detallepedido a detalleventa para guardarlo como detalleventa
//                    //llena tabla para hacer la busqueda de detalle sin usar ws
//            recargarTableDetallePedidos(detallePedidosGlobal);
//                    //fin llena tabla para hacer la busqueda de detalle sin usar ws
//            detallePedido = llenaTablaDetallePedidos(String.valueOf(idPedido).trim(),0);
//            for (DetallePedidoBean detallePedidoTemp :
//                    detallePedido) {
//                detalleVentaObj = new DetalleVentaBean();
//                detalleVentaObj.setCantidad(detallePedidoTemp.getCantidad());
//                detalleVentaObj.setDescuento(detallePedidoTemp.getDescuento());
//                detalleVentaObj.setIdArticulo(detallePedidoTemp.getIdArticulo());
//                detalleVentaObj.setIdSucursal(detallePedidoTemp.getIdSucursal());
//                detalleVentaObj.setIdVenta(idVenta);
//                detalleVentaObj.setPrecio(detallePedidoTemp.getPrecio());
//                detalleVentaProducto.add(detalleVentaObj);
//            }
//                    //regresa tabla originalmente
//            recargarTableDetallePedidos(detallePedido);
//                    //fin regresa tabla originalmente
//                //fin convierto detallepedido a detalleventa para guardarlo como detalleventa
////            JOptionPane.showMessageDialog(null, "num ventasBean det: " + detalleVentaProducto.get(0).getIdVenta());
////            JOptionPane.showMessageDialog(null, "Fecha ventasBean: " + ventasBean.getFecha() + "num ventasBean: " + idVenta);
//            
//
//            //ciclo que garantiza que operacion fue hecha con exito
//            while (ventasBean != null) {
//                //guarda ventasBean
//                hiloVentas = new WSVentas();
//                rutaWS = constantes.getProperty("IP") 
//                        + constantes.getProperty("GUARDAVENTA");
//                VentasBean ventaGuardada = hiloVentas
//                        .ejecutaWebService(rutaWS,"2"
//                        , "" + ventasBean.getIdCliente()
//                        , "" + ventasBean.getObservaciones()
//                        , "" + ventasBean.getIdUsuario()
//                        , "" + ventasBean.getIdSucursal());
//                if (ventaGuardada != null) {
//                    //guarda detalle ventasBean
//                    for (DetalleVentaBean detVentBeanADisminuir :
//                            detalleVentaProducto) {
//                        hiloDetalleVentas = new WSDetalleVentas();
//                        rutaWS = constantes.getProperty("IP") 
//                            + constantes.getProperty("GUARDADETALLEVENTA");
////                                boolean detalleGuardado = false;
////                                while (!detalleGuardado) {
//                            // para ajuste inventario                                }
//                            int idArticuloVendido = detVentBeanADisminuir.getIdArticulo();
//                            double cantidadVendida = detVentBeanADisminuir.getCantidad();
//                            // fin para ajuste inventario                                }
//                            DetalleVentaBean detalleVentaGuardada = 
//                                    hiloDetalleVentas.
//                                            ejecutaWebService(rutaWS
//                                            ,"1"
//                                    , "" + idVenta
//                                    , "" + detVentBeanADisminuir.getIdArticulo()
//                                    , "" + detVentBeanADisminuir.getPrecio()
//                                    , "" + detVentBeanADisminuir.getCantidad()
//                                    , "" + detVentBeanADisminuir.getDescuento()
//                                    , "" + Ingreso.usuario.getIdSucursal());
//                            if (detalleVentaGuardada != null) {
//                                //Dismimuye inventario
//                                    //obtiene articulo para saber su cantidad original
//                                ArrayList<ProductoBean> resultWSP = null;
//                                hiloInventariosList = new WSInventariosList();
//                                rutaWS = constantes.getProperty("IP") 
//                                        + constantes.getProperty("OBTIENEPRODUCTOPORID") 
//                                        + String.valueOf(idArticuloVendido);
//                                resultWSP = hiloInventariosList.ejecutaWebService(rutaWS,"5");
//                                ProductoBean p = resultWSP.get(0);
//                                    //fin obtiene articulo para saber su cantidad original
//
//                                    //disminuye iinventario en cifras no en bd
//                                double cantidadOriginal = p.getExistencia();
//                                double cantidadFinal = cantidadOriginal 
//                                        - cantidadVendida;
//                                    //fin disminuye iinventario en cifras no en bd
//
//                                    //realiza ajuste inventario 
//                                hiloInventarios = new WSInventarios();
//                                rutaWS = constantes.getProperty("IP") 
//                                        + constantes.getProperty("AJUSTAINVENTARIOVENTA");
//                                ProductoBean ajuste = hiloInventarios
//                                        .ejecutaWebService(rutaWS,"5"
//                                        ,String.valueOf(idArticuloVendido)
//                                        ,"" + cantidadFinal);
//                                if (ajuste != null) {
//                                        //Guarda movimiento
//                                    String fecha = util.dateToDateTimeAsString(new java.util.Date());
//                                    MovimientosBean mov = new MovimientosBean();
//                                    hiloMovimientos = new WSMovimientos();
//                                    rutaWS = constantes.getProperty("IP") + constantes.getProperty("GUARDAMOVIMIENTO");
//                                    MovimientosBean movimientoInsertado = hiloMovimientos.ejecutaWebService(rutaWS,"1"
//                                        ,"" + p.getIdArticulo()
//                                        ,"" + Ingreso.usuario.getIdUsuario()
//                                        ,"Venta"
//                                        ,"" + cantidadVendida
//                                        ,fecha
//                                        ,"" + Ingreso.usuario.getIdSucursal());
//                                        //Fin Guarda movimiento
////                                            if (movimientoInsertado != null) {
////                                                detalleGuardado = true;
//////                                                detalleVentaProducto.remove(detVentBeanADisminuir);
//////                                            }
//                                 } // fin realiza pregunta si se ajusto inentario
////                                    } else {
////                                        detalleGuardado = false;
//                            } // pregunta si se guardo un una fila del detalle venta
//                    }
//                    //fin guarda detalle ventasBean
//
//                    //carga productos actualizados
//                    productos = util.getMapProductos();
//                    util.llenaMapProductos(productos);
//                    //fin carga productos actualizados
//
//                    JOptionPane.showMessageDialog(null, 
//                            "VENTA GUARDADA CORRRECTAMENTE");
////                            detalleVentaProducto.remove(detVentBeanADisminuir);
//                    ventasBean = null;
//                    
//                    // Borra pedido
//                    PedidoBean p = new PedidoBean();
//                    hiloPedidos = new WSPedidos();
//                    rutaWS = constantes.getProperty("IP") + constantes.getProperty("ELIMINAPEDIDO");
//                    PedidoBean pedidoEliminar = hiloPedidos.ejecutaWebService(rutaWS,"3"
//                            , "" + idPedido);
//                    borrar();
//                    cargaDatos();
//                    int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas "
//                            + "Imprimir la Venta?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
//                    if (resultado == JOptionPane.YES_OPTION) {
//                        //imprime ticket
////                                                    imprimir(ventasBean);
////                            JOptionPane.showMessageDialog(null, "Se imprime el ticket");                             
//                        //fin imprime ticket
//                    }
//                } // condicion que verifica que se guardo la venta
//            }                        
//            //fin ciclo guarda ventasBean
//        }
    }    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (tblConsultaVentas.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una venta");
            return;
        }
        procesarFactura();
    }//GEN-LAST:event_jButton4ActionPerformed

    public void actualizarBusquedaVenta() {
        ArrayList<VentasBean> resultWS = null;
        ProductoBean producto = null;
        //No. Venta, Cliente, Sucursal, Usuario
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("No. Venta")) {
            if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
                resultWS = ventasGlobal;
                recargarTableDetalleVentas(detalleVentasGlobal);
            } else {
                resultWS = llenaTablaVentas(
                        txtBuscarVenta.getText().trim(),0);
            }
        }
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Cliente")) {
            if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
                resultWS = ventasGlobal;
                recargarTableDetalleVentas(detalleVentasGlobal);
            } else {
                resultWS = llenaTablaVentas(
                        txtBuscarVenta.getText().trim(),1);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Sucursal")) {
            if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
                resultWS = ventasGlobal;
                recargarTableDetalleVentas(detalleVentasGlobal);
            } else {
                resultWS = llenaTablaVentas(
                        txtBuscarVenta.getText().trim(),2);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Usuario")) {
            if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
                resultWS = ventasGlobal;
                recargarTableDetalleVentas(detalleVentasGlobal);
            } else {
                resultWS = llenaTablaVentas(
                        txtBuscarVenta.getText().trim(),3);
            }
        } 
        if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
            resultWS = ventasGlobal;
            recargarTableDetalleVentas(detalleVentasGlobal);
        } else {
//                    resultWS = llenaTablaVentas(
//                            txtBuscarVenta.getText().trim(),3);
        }
        recargarTableVentas(resultWS);
    }
    
    private ArrayList<VentasBean> llenaTablaVentas(String buscar, int tipoBusq) {
        ArrayList<VentasBean> resultWS = new ArrayList<VentasBean>();
        VentasBean venta = null;
        for (int i=0; i<tblConsultaVentas.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaVentas.getModel().getValueAt(
                    i,0).toString();
                    break;
                case 1 : campoBusq = tblConsultaVentas.getModel().getValueAt(
                    i,2).toString();
                    break;
                case 2 : campoBusq = tblConsultaVentas.getModel().getValueAt(
                    i,3).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = tblConsultaVentas.getModel().getValueAt(
                    i,4).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                venta = new VentasBean();
                venta.setIdVenta(Integer.parseInt(tblConsultaVentas.getModel().getValueAt(i,0).toString()));
                
                String fecha = String.valueOf(tblConsultaVentas.getModel().getValueAt(i,1));
                venta.setFecha(util.stringToDate(fecha));
                venta.setIdCliente(util.buscaIdCliente(Principal.clientesHM
                        , tblConsultaVentas.getModel().getValueAt(i,2).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM
                        , "" + tblConsultaVentas
                                .getModel().getValueAt(i,3).toString());
                venta.setIdSucursal(idSuc);
                venta.setIdUsuario(util.buscaIdUsuario(Principal.usuariosHM
                        , "" + tblConsultaVentas
                                .getModel().getValueAt(i,4).toString()));
                venta.setSubtotal(Double.parseDouble(tblConsultaVentas.getModel().getValueAt(i,5).toString()));
                venta.setIva(Double.parseDouble(tblConsultaVentas.getModel().getValueAt(i,6).toString()));
                venta.setTotal(Double.parseDouble(tblConsultaVentas.getModel().getValueAt(i,7).toString()));
                resultWS.add(venta);
            }
        }
        return resultWS;
    }

    public void actualizarBusquedaDetalleVenta() {
        recargarTableDetalleVentas(detalleVentasGlobal);
        ArrayList<DetalleVentaBean> resultWS = null;
        ProductoBean producto = null;
        String idVenta = tblConsultaVentas.getModel()
                .getValueAt(tblConsultaVentas.getSelectedRow(),0).toString();
        resultWS = llenaTablaDetalleVentas(idVenta.trim(),0);
//        if (txtBuscarVenta.getText().equalsIgnoreCase("")) {
//            resultWS = detalleVentasGlobal;
//        }
        recargarTableDetalleVentas(resultWS);
    }
    
    private ArrayList<DetalleVentaBean> llenaTablaDetalleVentas(String buscar, int tipoBusq) {
        ArrayList<DetalleVentaBean> resultWS = new ArrayList<DetalleVentaBean>();
        DetalleVentaBean detalleVenta = null;
        for (int i=0; i<tblConsultaDetalleVenta.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaDetalleVenta.getModel().getValueAt(
                    i,1).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                detalleVenta = new DetalleVentaBean();
                detalleVenta.setIdDetalleVenta(Integer.parseInt(tblConsultaDetalleVenta.getModel().getValueAt(i,0).toString()));
                detalleVenta.setIdVenta(Integer.parseInt(tblConsultaDetalleVenta.getModel().getValueAt(i,1).toString()));
                detalleVenta.setIdArticulo(util.buscaIdProd(
                        Principal.productosHM, tblConsultaDetalleVenta.getModel().getValueAt(i,2).toString()));
                detalleVenta.setPrecio(Double.parseDouble(tblConsultaDetalleVenta.getModel().getValueAt(i,3).toString()));
                detalleVenta.setCantidad(Double.parseDouble(tblConsultaDetalleVenta.getModel().getValueAt(i,4).toString()));
                detalleVenta.setDescuento(Double.parseDouble(tblConsultaDetalleVenta.getModel().getValueAt(i,5).toString()));
                detalleVenta.setUnidadMedida(String.valueOf(tblConsultaDetalleVenta.getModel().getValueAt(i,6).toString()));
                detalleVenta.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , tblConsultaDetalleVenta.getModel().getValueAt(i,7).toString()));
                resultWS.add(detalleVenta);
            }
        }
        return resultWS;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboParametroVentas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaDetalleVenta;
    private javax.swing.JTable tblConsultaVentas;
    private javax.swing.JTextField txtBuscarVenta;
    // End of variables declaration//GEN-END:variables
}