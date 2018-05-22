package vistas;

import ComponenteConsulta.JDListaCorteDia;
import beans.ProveedorBean;
import ComponenteConsulta.JDListaProveedor;
import beans.ComprasBean;
import beans.DatosEmpresaBean;
import beans.DetalleVentaBean;
import beans.FechaServidorBean;
import beans.MovimientosBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSComprasList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetalleVentasList;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSMovimientosList;
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
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.productos;

public class FrmConsultaMovimientos extends javax.swing.JFrame {
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSVentas hiloVentas;
    WSMovimientosList hiloMovimientosList;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    ArrayList<MovimientosBean> movimientosGlobal = null;
    ArrayList<ProductoBean> inventario = null;

    public FrmConsultaMovimientos() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        inventario = util.getMapProductos();
        productos = util.getMapProductos();
        util.llenaMapProductos(productos);
        
        // Actualizas tbl Ventas
        hiloMovimientosList = new WSMovimientosList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETMOVIMIENTOS");
        movimientosGlobal = hiloMovimientosList.ejecutaWebService(rutaWS,"1");
        recargarTableMovimientos(movimientosGlobal);

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
    }
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("..\\img\\matserviceslogo.png")));
    }

    //Para Tabla Ventas
    public void recargarTableMovimientos(ArrayList<MovimientosBean> list) {
        Object[][] datos = new Object[list.size()][7];
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//System.out.println(dateFormat.format(new Date()));        
        for (MovimientosBean p : list) {
            datos[i][0] = p.getIdMovimiento();
            datos[i][1] = dateFormat.format(p.getFechaOperacion());
//            datos[i][1] = p.getFecha();
            datos[i][2] = util.buscaDescFromIdProd(Principal.productosHMID
                    , "" + p.getIdArticulo());
            datos[i][3] = p.getCantidad();
            datos[i][4] = util.buscaDescFromIdSuc(Principal.sucursalesHM 
                    , "" + p.getIdSucursal());
            datos[i][5] = util.buscaDescFromIdUsu(Principal.usuariosHM 
                    , "" + p.getIdUsuario());
            datos[i][6] = p.getTipoOperacion();
            i++;
        }
        tblConsultaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "No. MOVIMIENTO", "FECHA MOV.", "PRODUCTO", "CANTIDAD"
                        , "SUCURSAL", "USUARIO", "OPERACIÓN"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarMovimiento = new javax.swing.JTextField();
        cboParametroVentas = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConsultaMovimientos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
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

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("CONSULTA DE MOVIMIENTOS");

        txtBuscarMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarMovimientoActionPerformed(evt);
            }
        });
        txtBuscarMovimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMovimientoKeyReleased(evt);
            }
        });

        cboParametroVentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No. Movimiento", "Producto", "Sucursal", "Usuario", "Operación" }));
        cboParametroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroVentasActionPerformed(evt);
            }
        });

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
        jButton4.setText("CORTE CAJA");
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

        tblConsultaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConsultaMovimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaMovimientosMouseClicked(evt);
            }
        });
        tblConsultaMovimientos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblConsultaMovimientosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblConsultaMovimientos);

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel4.setText("MOVIMIENTO");

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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(878, 878, 878))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(txtBuscarMovimiento)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(lblUsuario)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton3)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(93, 93, 93))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(lblUsuario)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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

    private void txtBuscarMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarMovimientoActionPerformed
    }//GEN-LAST:event_txtBuscarMovimientoActionPerformed

    private void txtBuscarMovimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMovimientoKeyReleased
        actualizarBusquedaMovimiento();
    }//GEN-LAST:event_txtBuscarMovimientoKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmInventario inventario = new FrmInventario();
//        inventario.setExtendedState(inventario.MAXIMIZED_BOTH);
//        inventario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblConsultaMovimientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaMovimientosMouseClicked
    }//GEN-LAST:event_tblConsultaMovimientosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
        ArrayList<MovimientosBean> movimientosPorFechas = null;
        hiloMovimientosList = new WSMovimientosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETMOVIMIENTOSPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETMOVIMIENTOSPORFECHASFFIN") + fechaFin;
        movimientosPorFechas = hiloMovimientosList.ejecutaWebService(rutaWS,"3");
        recargarTableMovimientos(movimientosPorFechas);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void borrar() {
        //LIMPIA TXT BUSQUEDA VENTAS
        txtBuscarMovimiento.setText("");
        actualizarBusquedaMovimiento();
        
        //limpia jcalendars
        jCalFechaIni.setDate(null);
        jCalFechaFin.setDate(null);           
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        borrar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblConsultaMovimientosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConsultaMovimientosKeyReleased
    }//GEN-LAST:event_tblConsultaMovimientosKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());            
            fechaSqlDateIni = date;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        
        JDListaCorteDia jdListaCorteDia = new JDListaCorteDia(this, true, fechaSqlDateIni, fechaSqlDateFin);
        jdListaCorteDia.setVisible(true);        
    }//GEN-LAST:event_jButton4ActionPerformed

    public void actualizarBusquedaMovimiento() {
        ArrayList<MovimientosBean> resultWS = null;
        ProductoBean producto = null;
        //No. Venta, Cliente, Sucursal, Usuario
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("No. Movimiento")) {
            if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
                resultWS = movimientosGlobal;
            } else {
                resultWS = llenaTablaMovimientos(
                        txtBuscarMovimiento.getText().trim(),0);
            }
        }
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Producto")) {
            if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
                resultWS = movimientosGlobal;
            } else {
                resultWS = llenaTablaMovimientos(
                        txtBuscarMovimiento.getText().trim(),4);
            }
        }
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Sucursal")) {
            if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
                resultWS = movimientosGlobal;
            } else {
                resultWS = llenaTablaMovimientos(
                        txtBuscarMovimiento.getText().trim(),2);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Usuario")) {
            if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
                resultWS = movimientosGlobal;
            } else {
                resultWS = llenaTablaMovimientos(
                        txtBuscarMovimiento.getText().trim(),3);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Operación")) {
            if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
                resultWS = movimientosGlobal;
            } else {
                resultWS = llenaTablaMovimientos(
                        txtBuscarMovimiento.getText().trim(),5);
            }
        } 
        if (txtBuscarMovimiento.getText().equalsIgnoreCase("")) {
            resultWS = movimientosGlobal;
        }
        recargarTableMovimientos(resultWS);
    }
    
    private ArrayList<MovimientosBean> llenaTablaMovimientos(String buscar, int tipoBusq) {
        ArrayList<MovimientosBean> resultWS = new ArrayList<MovimientosBean>();
        MovimientosBean movimiento = null;
        for (int i=0; i<tblConsultaMovimientos.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaMovimientos.getModel().getValueAt(
                    i,0).toString();
                    break;
                case 2 : campoBusq = tblConsultaMovimientos.getModel().getValueAt(
                    i,4).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = tblConsultaMovimientos.getModel().getValueAt(
                    i,5).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 4 : campoBusq = tblConsultaMovimientos.getModel().getValueAt(
                    i,2).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 5 : campoBusq = tblConsultaMovimientos.getModel().getValueAt(
                    i,6).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                movimiento = new MovimientosBean();
                movimiento.setIdMovimiento(Integer.parseInt(
                        tblConsultaMovimientos.getModel().getValueAt(i,0).toString()));
                String fecha = String.valueOf(tblConsultaMovimientos.getModel().getValueAt(i,1));
                movimiento.setFechaOperacion(util.stringToDate(fecha));
                movimiento.setIdArticulo(util.buscaIdProd(Principal.productosHMID
                        , tblConsultaMovimientos.getModel().getValueAt(i,2).toString()));
                movimiento.setCantidad(Double.parseDouble(
                        tblConsultaMovimientos.getModel().getValueAt(i,3).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM
                        , "" + tblConsultaMovimientos
                                .getModel().getValueAt(i,4).toString());
                movimiento.setIdSucursal(idSuc);
                movimiento.setIdUsuario(util.buscaIdUsuario(Principal.usuariosHM
                        , "" + tblConsultaMovimientos
                                .getModel().getValueAt(i,5).toString()));
                movimiento.setTipoOperacion(tblConsultaMovimientos.getModel().getValueAt(i,6).toString());
                resultWS.add(movimiento);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaMovimientos;
    private javax.swing.JTextField txtBuscarMovimiento;
    // End of variables declaration//GEN-END:variables
}