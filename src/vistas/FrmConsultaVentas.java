package vistas;

import beans.CajaChicaBean;
import beans.DetalleVentaBean;
import beans.MovimientosBean;
import beans.ProductoBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCajaChica;
import consumewebservices.WSCajaChicaList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetalleVentasList;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSVentas;
import consumewebservices.WSVentasList;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.ImageIcon;
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
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    WSMovimientos hiloMovimientos;
    WSCajaChicaList hiloCajaChicaList;
    WSCajaChica hiloCajaChica;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    ArrayList<VentasBean> ventasGlobal = null;
    ArrayList<DetalleVentaBean> detalleVentasGlobal = null;
    ArrayList<ProductoBean> inventario = null;
    
    String suc;

    public FrmConsultaVentas() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        btnFacturar.setVisible(false);
        // Actualizas tbl Ventas
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETVENTAS");
        ventasGlobal = hiloVentasList.ejecutaWebService(rutaWS,"1");
        recargarTableVentas(ventasGlobal);

        inventario = util.getInventario();
        productos = util.getInventario();
        util.llenaMapProductos(productos);
        
        // Actualizas tbl DetalleVentas
        hiloDetalleVentasList = new WSDetalleVentasList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETDETALLEVENTAS");
        detalleVentasGlobal = hiloDetalleVentasList.ejecutaWebService(rutaWS,"1");
        recargarTableDetalleVentas(detalleVentasGlobal);
        
        suc = util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                + Ingreso.usuario.getIdSucursal());
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        lblSucursal.setText(Principal.datosEmpresaBean.getNombreEmpresa() 
                + " Sucursal: " + suc);
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        limpiaTblDetalleVenta();        
    }
    
    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }

    //Para Tabla Ventas
    public void recargarTableVentas(ArrayList<VentasBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (VentasBean p : list) {
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal())
                    || (Ingreso.usuario.getUsuario()
                            .equalsIgnoreCase(constantes
                                    .getProperty("SUPERUSUARIO")))) {
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
        }
        Object[][] datosFinal = new Object[i][8];
        //Para filtrar los registros
        for (int j = 0; j < i; j++) {
            if (datos[j][0] != null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
                datosFinal[j][2] = datos[j][2];
                datosFinal[j][3] = datos[j][3];
                datosFinal[j][4] = datos[j][4];
                datosFinal[j][5] = datos[j][5];
                datosFinal[j][6] = datos[j][6];
                datosFinal[j][7] = datos[j][7];
            }
        }
        //Fin Para filtrar los registros
        tblConsultaVentas.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
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
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal())
                    || (Ingreso.usuario.getUsuario()
                            .equalsIgnoreCase(constantes
                                    .getProperty("SUPERUSUARIO")))) {
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
            }
        }
        Object[][] datosFinal = new Object[i][8];
        //Para filtrar los registros
        for (int j = 0; j < i; j++) {
            if (datos[j][0] != null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
                datosFinal[j][2] = datos[j][2];
                datosFinal[j][3] = datos[j][3];
                datosFinal[j][4] = datos[j][4];
                datosFinal[j][5] = datos[j][5];
                datosFinal[j][6] = datos[j][6];
                datosFinal[j][7] = datos[j][7];
            }
        }
        //Fin Para filtrar los registros
        tblConsultaDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
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
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        btnMostrar = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConsultaVentas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        btnCancelarVenta = new javax.swing.JButton();
        btnConsultas = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        lblSucursal = new javax.swing.JLabel();

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

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha Inicio :");

        jLabel3.setText("Fecha Fin :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrar.setText("MOSTRAR");
        btnMostrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/facturar.png"))); // NOI18N
        btnFacturar.setText("FACTURAR");
        btnFacturar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(btnMostrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        tblConsultaVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        btnCancelarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.png"))); // NOI18N
        btnCancelarVenta.setText("CANCELAR VENTA");
        btnCancelarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarVentaActionPerformed(evt);
            }
        });

        btnConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnConsultas.setText("CONSULTAS");
        btnConsultas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultasActionPerformed(evt);
            }
        });

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnInicio.setText("INICIO");
        btnInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        lblSucursal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSucursal.setText("Negocio:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCancelarVenta)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(jLabel4)
                            .addComponent(lblUsuario)
                            .addComponent(lblSucursal))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblSucursal)
                                .addGap(18, 18, 18)
                                .addComponent(lblUsuario))
                            .addComponent(btnCancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSalir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblConsultaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaVentasMouseClicked
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaVentasMouseClicked

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        limpiaTblDetalleVenta();        
        String fechaIni;
        String fechaFin;
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo "
                    + "menos la fecha de Inicio");
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
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETVENTASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETVENTASPORFECHASFFIN") + fechaFin;
        ventasPorFechas = hiloVentasList.ejecutaWebService(rutaWS,"2");
        recargarTableVentas(ventasPorFechas);
    }//GEN-LAST:event_btnMostrarActionPerformed

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
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblConsultaVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConsultaVentasKeyReleased
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaVentasKeyReleased

    public void procesarFactura() {
        // codigo para despues
    }    
    
    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        if (tblConsultaVentas.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una venta");
            return;
        }
        procesarFactura();
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void rollBackAjusteInventario(ArrayList<DetalleVentaBean> resultWS, 
            int operacion, int registrosRegresar) { 
        //operacion =1 regresoventa, operacion =2 error
        //regresa producto a inventario
        //Dismimuye inventario
            //obtiene articulo para saber su cantidad original
        int i = 0;
        for (DetalleVentaBean detalle : resultWS) {
            if (i < registrosRegresar) {
                double cantidadVendida = detalle.getCantidad();
                ArrayList<ProductoBean> resultWSProd = null;
                hiloInventariosList = new WSInventariosList();
                String rutaWS = constantes.getProperty("IP") 
                        + constantes.getProperty("OBTIENEPRODUCTOPORID") 
                        + String.valueOf(detalle.getIdArticulo());
                resultWSProd = hiloInventariosList.ejecutaWebService
                        (rutaWS,"5");
                ProductoBean p = resultWSProd.get(0);
                    //fin obtiene articulo para saber su cantidad original

                    //disminuye iinventario en cifras no en bd
                double cantidadOriginal = p.getExistencia();
                double cantidadFinal;
                if (operacion == 1) {
                    cantidadFinal = cantidadOriginal 
                            + cantidadVendida;
                } else {
                    cantidadFinal = cantidadOriginal 
                            - cantidadVendida;
                }
                    //fin disminuye iinventario en cifras no en bd

                    //realiza ajuste inventario 
                hiloInventarios = new WSInventarios();
                rutaWS = constantes.getProperty("IP") 
                        + constantes
                             .getProperty("AJUSTAINVENTARIOVENTA");
                ProductoBean ajuste = hiloInventarios
                        .ejecutaWebService(rutaWS,"5"
                        ,String.valueOf(detalle.getIdArticulo())
                        ,"" + cantidadFinal);
            //fin regresa producto a inventario
            }
            i++;
        }
    }

    private CajaChicaBean regresaMovCaja(VentasBean venta, int operacion) {
        //operacion venta = 1; error = 2
        //registra el dinero regresado como movimiento 
        //de caja chica
        CajaChicaBean cajaChica = new CajaChicaBean();
        cajaChica.setFecha(util.obtieneFechaServidor());
        cajaChica.setMonto(venta.getTotal());
        cajaChica.setTipoMov("Gasto");
        cajaChica.setTipoComprobante("Ticket");
        cajaChica.setReferencia("" + venta
                .getIdVenta());
        cajaChica.setIdUsuario(Ingreso.usuario
                .getIdUsuario());
        cajaChica.setIdSucursal(Ingreso.usuario
                .getIdSucursal());
        //busca ultimo registro
        ArrayList<CajaChicaBean> resultWSCajaChica 
                = null;
        hiloCajaChicaList = new WSCajaChicaList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.
                getProperty("GETULTIMOCAJACHICA");
        resultWSCajaChica = hiloCajaChicaList
                .ejecutaWebService(rutaWS,"2");
        CajaChicaBean cajaChicaBean = 
                resultWSCajaChica.get(0);
        String saldoAnterior = "" 
                + cajaChicaBean.getSaldoAnterior();
        String saldoActualA = "" 
                + cajaChicaBean.getSaldoActual();
        cajaChica.setSaldoAnterior(Double
                .parseDouble(saldoActualA));
        double saldoActual = 0;
        saldoActual = Double.parseDouble(saldoActualA)
                - venta.getTotal();
        cajaChica.setSaldoActual(saldoActual);
        hiloCajaChica = new WSCajaChica();
        rutaWS = constantes.getProperty("IP") 
                + constantes
                 .getProperty("GUARDAMOVCAJACHICA");
        CajaChicaBean movCajaInsertada = 
                hiloCajaChica.ejecutaWebService
                (rutaWS,"1"
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
        return movCajaInsertada;
    }    
    
    private VentasBean registraVenta(VentasBean venta, String operacion) {
        //operacion=1 cancela venta normal, operacion=0 error
        //actualiza venta
        VentasBean ventaActualizada = null;
        hiloVentas = new WSVentas();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("MODIFICAVENTA");
        ventaActualizada = hiloVentas.ejecutaWebService
                    (rutaWS,"3"
                    ,"" + venta.getIdVenta()
                    ,"" + venta.getFecha()
                    ,"" + venta.getIdCliente()
                    ,venta.getObservaciones()
                    ,"" + venta.getIdUsuario()
                    ,"" + venta.getIdSucursal()
                    ,"" + venta.getSubtotal()
                    ,"" + venta.getIva()
                    ,"" + venta.getTotal()
                    ,"" + venta.getTipovta()
                    ,operacion
                    ,"" + venta.getFacturada()
                    ,"" + venta.getIdFactura()
                );
        return ventaActualizada;
    }   
    
    private MovimientosBean registraBorraMovimiento(ProductoBean p
            , int operacion, double cantidadVendida) {
        //operacion=1 normal, operacion=2 error
            //Guarda movimiento
        String fecha = util.dateToDateTimeAsString(util
                .obtieneFechaServidor());
        MovimientosBean mov = new MovimientosBean();
        hiloMovimientos = new WSMovimientos();
        String rutaWS = constantes.getProperty("IP") 
           + constantes.getProperty("GUARDAMOVIMIENTO");
        MovimientosBean movimientoInsertado = null;
        if (operacion == 1) {
            movimientoInsertado = hiloMovimientos.ejecutaWebService(rutaWS,"1"
                ,"" + p.getIdArticulo()
                ,"" + Ingreso.usuario.getIdUsuario()
                ,"Venta Cancelada"
                ,"" + cantidadVendida
                ,fecha
                ,"" + Ingreso.usuario.getIdSucursal());
            //Fin Guarda movimiento
        } else {
            //lama a la eliminacion del movimiento
        }
        return movimientoInsertado;
    }
    
    private void btnCancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarVentaActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente "
                + "deseas cancelar la venta?");
        if(dialogResult == JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null, "Se sugiere guardes un documento "
                    + "que sirva de evidencia de ésta operación");
            // Verifica que este seleccionada una venta
            String numV;
            int numVenta;
            try {
                numV = tblConsultaVentas.getValueAt(
                        tblConsultaVentas.getSelectedRow(), 0).toString();
                numVenta = Integer.parseInt(numV);
            } catch(Exception e) {
                numVenta = Integer.parseInt(JOptionPane.showInputDialog("Ingresa"
                        + " el número de Venta"));
            }
            // Fin Verifica que este seleccionada una venta

            VentasBean venta = buscaVenta(numVenta);
            if (venta == null) {
                JOptionPane.showMessageDialog(null, "No existe esa venta");
                return;
            } else {
                //verifica que no este regresada
                if (venta.getCancelada()==1) {
                    JOptionPane.showMessageDialog(null, "Esta venta ya está "
                            + "cancelada");
                    return;
                }
                //fin verifica que no este regresada

                ArrayList<DetalleVentaBean> resultWS = buscaDetaleVenta(numVenta);
                if (resultWS.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existe detalle en esa "
                            + "venta");
                    return;
                } else {
                    VentasBean ventaActualizada = registraVenta(venta,"1");
                    //si ya se actualizo la venta
                    if (ventaActualizada != null) {
                        //Consulta detalle venta
                        String idVenta = "" + venta.getIdVenta();
                        resultWS = llenaTablaDetalleVentas(idVenta.trim(),0);
                        int contDetalle = 0;
                        //recorre el detalle de la venta
                        for (DetalleVentaBean detalle : resultWS) {
                            //regresa producto a inventario
                            //Dismimuye inventario
                                //obtiene articulo para saber su cantidad original
                            double cantidadVendida = detalle.getCantidad();
                            ArrayList<ProductoBean> resultWSProd = null;
                            hiloInventariosList = new WSInventariosList();
                            String rutaWS = constantes.getProperty("IP") 
                                    + constantes.getProperty("OBTIENEPRODUCTOPORID") 
                                    + String.valueOf(detalle.getIdArticulo());
                            resultWSProd = hiloInventariosList.ejecutaWebService
                                    (rutaWS,"5");
                            ProductoBean p = resultWSProd.get(0);
                                //fin obtiene articulo para saber su cantidad original

                                //disminuye iinventario en cifras no en bd
                            double cantidadOriginal = p.getExistencia();
                            double cantidadFinal = cantidadOriginal 
                                    + cantidadVendida;
                                //fin disminuye iinventario en cifras no en bd

                                //realiza ajuste inventario 
                            hiloInventarios = new WSInventarios();
                            rutaWS = constantes.getProperty("IP") 
                                    + constantes
                                         .getProperty("AJUSTAINVENTARIOVENTA");
                            ProductoBean ajuste = hiloInventarios
                                    .ejecutaWebService(rutaWS,"5"
                                    ,String.valueOf(detalle.getIdArticulo())
                                    ,"" + cantidadFinal);
//                                    ,"eroror");
                        //fin regresa producto a inventario
                            //registra movimiento
                            if (ajuste != null) {
//                                    //Guarda movimiento
//                                String fecha = util.dateToDateTimeAsString(util
//                                        .obtieneFechaServidor());
//                                MovimientosBean mov = new MovimientosBean();
//                                hiloMovimientos = new WSMovimientos();
//                                rutaWS = constantes.getProperty("IP") 
//                                   + constantes.getProperty("GUARDAMOVIMIENTO");
//                                MovimientosBean movimientoInsertado = 
//                                        hiloMovimientos.ejecutaWebService
//                                        (rutaWS,"1"
//                                    ,"" + p.getIdArticulo()
//                                    ,"" + Ingreso.usuario.getIdUsuario()
//                                    ,"Venta Cancelada"
//                                    ,"" + cantidadVendida
//                                    ,fecha
//                                    ,"" + Ingreso.usuario.getIdSucursal());
//                                    //Fin Guarda movimiento
                                MovimientosBean movimientoInsertado = 
                                        registraBorraMovimiento(p ,
                                                1, cantidadVendida);
                                if (movimientoInsertado!=null) {
                                    contDetalle++;
                                    CajaChicaBean movCajaInsertada = 
                                            regresaMovCaja(venta, 1);
                                    if (movCajaInsertada != null) {
                                        if (contDetalle == resultWS.size()) {
                                            JOptionPane.showMessageDialog(null, 
                                                    "Regreso de venta exitoso");
                                        }
                                    }                                     
                                    //fin registra el dinero regresado como movimiento de caja chica
                                }
                            } else {
                                //rollbacks venta y ajuste por error
                                ventaActualizada = registraVenta(venta,"0");
                                //rollbackajusteinventario
                                rollBackAjusteInventario(resultWS, 2, contDetalle);                                
                                JOptionPane.showMessageDialog(null, 
                                        "Error al cancelar la venta, inténtalo "
                                                + "mas tarde");
                                return;
                            }
                            //fin registra movimiento
                        }
                        //recorre el detalle de la venta
                    }
                    //fin actualiza venta

                }
                //comprueba si hay detalle regresa a inventario y guarda movimiento
            }
        }    
    }//GEN-LAST:event_btnCancelarVentaActionPerformed

    private void btnConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultasActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmConsultas frmConsultas = new FrmConsultas();
        frmConsultas.setExtendedState(frmConsultas.MAXIMIZED_BOTH);
        frmConsultas.setVisible(true);
    }//GEN-LAST:event_btnConsultasActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnInicioActionPerformed

    private VentasBean buscaVenta(int numVenta) {
        ArrayList<VentasBean> ventas = null;
        VentasBean venta = null;
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETVENTAPORID") 
                + numVenta;
        ventas = hiloVentasList.ejecutaWebService(rutaWS,"3");
        //verifica si encontro venta al ser la lista de ventas mayor que 0
        if (ventas.size() > 0) {
            venta = ventas.get(0);
        } 
        return venta;
    }
    
    private ArrayList<DetalleVentaBean> buscaDetaleVenta(int numVenta) {
        ArrayList<DetalleVentaBean> resultWS = null;
        ProductoBean producto = null;
        String idVenta = tblConsultaVentas.getModel()
                .getValueAt(tblConsultaVentas.getSelectedRow(),0).toString();
        resultWS = llenaTablaDetalleVentas(idVenta.trim(),0);
        return resultWS;
    }
    
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
                venta.setIdVenta(Integer.parseInt(tblConsultaVentas.getModel()
                        .getValueAt(i,0).toString()));
                String fecha = String.valueOf(tblConsultaVentas.getModel()
                        .getValueAt(i,1));
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
                venta.setSubtotal(Double.parseDouble(tblConsultaVentas.getModel()
                        .getValueAt(i,5).toString()));
                venta.setIva(Double.parseDouble(tblConsultaVentas.getModel()
                        .getValueAt(i,6).toString()));
                venta.setTotal(Double.parseDouble(tblConsultaVentas.getModel()
                        .getValueAt(i,7).toString()));
                resultWS.add(venta);
            }
        }
        return resultWS;
    }

    public void actualizarBusquedaDetalleVenta() {
        recargarTableDetalleVentas(detalleVentasGlobal);
        ArrayList<DetalleVentaBean> resultWS = null;
        //ProductoBean producto = null;
        String idVenta = tblConsultaVentas.getModel()
                .getValueAt(tblConsultaVentas.getSelectedRow(),0).toString();
        resultWS = llenaTablaDetalleVentas(idVenta.trim(),0);
        recargarTableDetalleVentas(resultWS);
    }
    
    private ArrayList<DetalleVentaBean> llenaTablaDetalleVentas(String buscar, int tipoBusq) {
        ArrayList<DetalleVentaBean> resultWS = new ArrayList<DetalleVentaBean>();
        DetalleVentaBean detalleVenta = null;
        for (int i=0; i<tblConsultaDetalleVenta.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaDetalleVenta.getModel()
                        .getValueAt(
                    i,1).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                detalleVenta = new DetalleVentaBean();
                detalleVenta.setIdDetalleVenta(Integer.parseInt
                    (tblConsultaDetalleVenta.getModel().getValueAt(i,0)
                            .toString()));
                detalleVenta.setIdVenta(Integer.parseInt
                    (tblConsultaDetalleVenta.getModel().getValueAt(i,1)
                            .toString()));
                detalleVenta.setIdArticulo(util.buscaIdProd(
                        Principal.productosHMID, tblConsultaDetalleVenta
                                .getModel().getValueAt(i,2).toString()));
                detalleVenta.setPrecio(Double.parseDouble(tblConsultaDetalleVenta
                        .getModel().getValueAt(i,3).toString()));
                detalleVenta.setCantidad(Double.parseDouble(tblConsultaDetalleVenta
                        .getModel().getValueAt(i,4).toString()));
                detalleVenta.setDescuento(Double.parseDouble
                    (tblConsultaDetalleVenta.getModel().getValueAt(i,5)
                            .toString()));
                detalleVenta.setUnidadMedida(String.valueOf(tblConsultaDetalleVenta
                        .getModel().getValueAt(i,6).toString()));
                detalleVenta.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , tblConsultaDetalleVenta.getModel().getValueAt(i,7)
                                .toString()));
                resultWS.add(detalleVenta);
            }
        }
        return resultWS;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarVenta;
    private javax.swing.JButton btnConsultas;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboParametroVentas;
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
    private javax.swing.JLabel lblSucursal;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaDetalleVenta;
    private javax.swing.JTable tblConsultaVentas;
    private javax.swing.JTextField txtBuscarVenta;
    // End of variables declaration//GEN-END:variables
}