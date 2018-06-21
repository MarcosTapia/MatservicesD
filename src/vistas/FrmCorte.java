package vistas;

import Ticket.Ticket;
import beans.CajaChicaBean;
import beans.ComprasBean;
import beans.CorteCajaBean;
import beans.DatosEmpresaBean;
import beans.VentasBean;
import static componenteUtil.NumberToLetterConverter.convertNumberToLetter;
import constantes.ConstantesProperties;
import consumewebservices.WSCajaChicaList;
import consumewebservices.WSComprasList;
import consumewebservices.WSCorteCaja;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import consumewebservices.WSVentasList;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import util.Util;

public class FrmCorte extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    WSVentasList hiloVentasList;
    WSComprasList hiloComprasList;
    WSCajaChicaList hiloCajaChicaList;
    WSCorteCaja hiloCorteCaja;
    //Fin WSUsuarios
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    CorteCajaBean corteCaja = null;
    ArrayList<CorteCajaBean> corteCajaHoy = new ArrayList<>();
    Date fechaServidor = null;
    ArrayList<CorteCajaBean> cortePorFechasRegistradoHoy = null;

    String accion = "";

    public FrmCorte() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre() + " "
            + Ingreso.usuario.getApellido_paterno() + " " 
            + Ingreso.usuario.getApellido_materno());
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        configuracionBean = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(configuracionBean.getNombreEmpresa());
        
        java.util.Date fecha = util.obtieneFechaServidor();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        txtFecha.setText("Fecha: " + a);
        
        
        jCalFechaIni.setVisible(false);
        jCalFechaFin.setVisible(false);
        fechaServidor = util.obtieneFechaServidor();
        
        cargaCorteHoy();
        cargaVentasHoy();
        cargaComprasHoy();
        cargaMovsCajaChicaHoy();
        recargarTable(corteCajaHoy);
        obtieneTotal();
        this.setIcon();
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnGuardarPer = new javax.swing.JButton();
        btnSalirPer = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtTotalVentas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotalCompras = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTotalIngresos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTotalGastos = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel2.setText("CORTE DEL DÍA");

        btnGuardarPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarPer.setText("PROCESAR CORTE");
        btnGuardarPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPerActionPerformed(evt);
            }
        });

        btnSalirPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirPer.setText("SALIR");
        btnSalirPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPerActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("MOVIMIENTOS NO ENTREGADOS :");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("VENTAS :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("TOTAL :");

        txtTotalVentas.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("COMPRAS :");

        txtTotalCompras.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("INGRESOS :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("GASTOS :");

        txtTotalGastos.setEditable(false);

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalFocusGained(evt);
            }
        });

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtFecha.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTotalGastos)
                                        .addComponent(txtTotalIngresos)
                                        .addComponent(txtTotalCompras)
                                        .addComponent(txtTotalVentas)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel5))))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))))
                                .addGap(39, 39, 39)))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalirPer, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarPer))
                .addGap(54, 54, 54))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCalFechaIni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCalFechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnGuardarPer, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalirPer, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPerActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnSalirPerActionPerformed

    private void cargaVentasHoy() {
        hiloVentasList = new WSVentasList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
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
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos "
                    + "la fecha de Inicio");
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
        
        //iguala beans venta y cortecaja
        for (VentasBean vta : ventasPorFechas) {
            if (vta.getIdUsuario() == Ingreso.usuario.getIdUsuario()) {
                if (!verificaRegistroEnCorte(vta.getIdVenta())) {
                    corteCaja = new CorteCajaBean();
                    corteCaja.setIdMov(vta.getIdVenta());
                    corteCaja.setFecha(vta.getFecha());
                    corteCaja.setIdUsuario(vta.getIdUsuario());
                    corteCaja.setIdSucursal(vta.getIdSucursal());
                    corteCaja.setTotal(vta.getTotal());
                    corteCaja.setTipoMov(vta.getTipovta());
                    corteCajaHoy.add(corteCaja);
                }
            }
        }
    }
    
    public boolean verificaRegistroEnCorte(int idMovVtaCpaCaja) {
        boolean existe = false;
        for (CorteCajaBean corte : cortePorFechasRegistradoHoy) {
            if (corte.getIdMov() == idMovVtaCpaCaja) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    private void cargaCorteHoy() {
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
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
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos "
                    + "la fecha de Inicio");
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
        hiloCorteCaja = new WSCorteCaja();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETCORTEPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETCORTEPORFECHASFFIN") + fechaFin;
        cortePorFechasRegistradoHoy = hiloCorteCaja
                .ejecutaWebServiceObtieneCortes(rutaWS,"2");
    }
    
    private void obtieneTotal(){
        double totalVentas = 0;
        double totalCompras = 0;
        double totalIngresos = 0;
        double totalGastos = 0;
        double total = 0;
        
        for (int i=0;i<tblUsuarios.getRowCount();i++) {
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("VENTA NORMAL")) {
                totalVentas = totalVentas + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("COMPRA NORMAL")) {
                totalCompras = totalCompras + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("INGRESO")) {
                totalIngresos = totalIngresos + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
            if (tblUsuarios.getValueAt(i, 5).toString()
                    .equalsIgnoreCase("GASTO")) {
                totalGastos = totalGastos + Double.parseDouble(tblUsuarios
                        .getValueAt(i, 4).toString());
            }
        }
        total = total + totalVentas - totalCompras + totalIngresos - totalGastos;
        txtTotal.setText(String.format("%.2f",total));
        txtTotalVentas.setText(String.format("%.2f", totalVentas));
        txtTotalCompras.setText(String.format("%.2f", totalCompras));
        txtTotalIngresos.setText(String.format("%.2f", totalIngresos));
        txtTotalGastos.setText(String.format("%.2f", totalGastos));
    }
    
    private void cargaComprasHoy() {
        hiloVentasList = new WSVentasList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
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
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos "
                    + "la fecha de Inicio");
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
        ArrayList<ComprasBean> comprasPorFechas = null;
        hiloComprasList = new WSComprasList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETCOMPRASPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETCOMPRASPORFECHASFFIN") + fechaFin;
        comprasPorFechas = hiloComprasList.ejecutaWebService(rutaWS,"2");
        //iguala beans venta y cortecaja
        for (ComprasBean compra : comprasPorFechas) {
            if (compra.getIdUsuario() == Ingreso.usuario.getIdUsuario()) {
                if (!verificaRegistroEnCorte(compra.getIdCompra())) {
                    corteCaja = new CorteCajaBean();
                    corteCaja.setIdMov(compra.getIdCompra());
                    corteCaja.setFecha(compra.getFecha());
                    corteCaja.setIdUsuario(compra.getIdUsuario());
                    corteCaja.setIdSucursal(compra.getIdSucursal());
                    corteCaja.setTotal(compra.getTotal());
                    corteCaja.setTipoMov(compra.getTipocompra());
                    corteCajaHoy.add(corteCaja);
                }
            }
        }
    }
    
    private void cargaMovsCajaChicaHoy() {
        hiloCajaChicaList = new WSCajaChicaList();
        jCalFechaIni.setDate(fechaServidor);
        jCalFechaFin.setDate(fechaServidor);
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
        ArrayList<CajaChicaBean> movsCajaChicaPorFechas = null;
        hiloCajaChicaList = new WSCajaChicaList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETMOVSCAJACHICAPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETMOVSCAJACHICAPORFECHASFFIN") 
                + fechaFin;
        movsCajaChicaPorFechas = hiloCajaChicaList.ejecutaWebService(rutaWS,"4");
        //iguala beans venta y cortecaja
        for (CajaChicaBean movCajaChica : movsCajaChicaPorFechas) {
            if (movCajaChica.getIdUsuario() == Ingreso.usuario.getIdUsuario()) {
                if (!verificaRegistroEnCorte(movCajaChica.getIdMov())) {
                    corteCaja = new CorteCajaBean();
                    corteCaja.setIdMov(movCajaChica.getIdMov());
                    corteCaja.setFecha(movCajaChica.getFecha());
                    corteCaja.setIdUsuario(movCajaChica.getIdUsuario());
                    corteCaja.setIdSucursal(movCajaChica.getIdSucursal());
                    corteCaja.setTotal(movCajaChica.getMonto());
                    corteCaja.setTipoMov(movCajaChica.getTipoMov());
                    corteCajaHoy.add(corteCaja);
                }
            }
        }
    }

    public void imprimeCorte(){
        try {
            //Primera parte
            Ticket ticket = new Ticket();
            ticket.AddCabecera("" + Principal.datosEmpresaBean.getNombreEmpresa());
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("Sucursal: " + util
                    .buscaDescFromIdSuc(Principal.sucursalesHM, 
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
            ticket.AddSubCabecera("Corte del Día : " +
                    fechaImpresion);
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera("Corte de: " + Ingreso.usuario.getNombre()
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
            for(CorteCajaBean detalleVentaProdBean : corteCajaHoy) {
               //cantidad de decimales
               NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
               DecimalFormat form = (DecimalFormat)nf;
               form.applyPattern("#,###.00");
               //idMov
               String idMov = "" + detalleVentaProdBean.getIdMov()+ " ";
                
                //total
               String totalMov = "" + detalleVentaProdBean.getTotal() + " ";
                
                //tipo mov
               String tipoMov = detalleVentaProdBean.getTipoMov();
                
                //total
                String extra = "";
                //agrego los items al detalle
                ticket.AddItem(idMov,totalMov,tipoMov,extra);
                //ticket.AddItem("","","",ticket.DarEspacio());
            }
            ticket.AddItem(ticket.DibujarLinea(40),"","","");
            
            //Quinta parte totales
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("TOTAL                ",txtTotal.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("VENTAS               ",txtTotalVentas.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("COMPRAS              ",txtTotalCompras.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("INGRESOS             ",txtTotalIngresos.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("GASTOS              ",txtTotalGastos.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("",ticket.DarEspacio());
            
            //para idMov con letra
            String numEnLetra = convertNumberToLetter(txtTotal.getText());
            ticket.AddTotal("",numEnLetra.trim());
            ticket.AddPieLinea(ticket.DarEspacio());     
//            ticket.ImprimirDocumento("LPT1",true);
            ticket.ImprimirDocumento("usb002",true);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
        }     
    }    
    
    private void btnGuardarPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPerActionPerformed
        if (corteCajaHoy.size() > 0) {
            hiloCorteCaja = new WSCorteCaja();
            String rutaWS = constantes.getProperty("IP") 
                    + constantes.getProperty("GUARDAMOVCORTECAJA");

            for(CorteCajaBean corteCajaBean : corteCajaHoy) {
                CorteCajaBean movCorteCajaInsertado = null;
                while (movCorteCajaInsertado == null) {
                    movCorteCajaInsertado = hiloCorteCaja
                            .ejecutaWebService(rutaWS,"1"
                    , "" + corteCajaBean.getIdMov()
                    , corteCajaBean.getFecha().toLocaleString()
                    , "" + corteCajaBean.getIdUsuario()
                    , "" + corteCajaBean.getIdSucursal()
                    , "" + corteCajaBean.getTotal()
                    , corteCajaBean.getTipoMov()
                    , "1"
                        );
                }
            }
            //Arma correo
                //fecha
            java.util.Date fecha = util.obtieneFechaServidor();
            String fechaS = DateFormat.getDateInstance(DateFormat.LONG)
                    .format(fecha);        
            String hrs = String.valueOf(fecha.getHours());
            String min = String.valueOf(fecha.getMinutes());
            String sec = String.valueOf(fecha.getSeconds());
            if (hrs.length()==1) {
                hrs = "0" + hrs;
            }
            if (min.length()==1) {
                min = "0" + min;
            }
            if (sec.length()==1) {
                sec = "0" + sec;
            }
            
            fechaS = fechaS + " Hora : " + hrs
                    + " : " + min + " : " + sec;
//            JOptionPane.showMessageDialog(null, fechaS);
                //arma mensaje
            String sucursal = util.buscaDescFromIdSuc
                    (Principal.sucursalesHM, 
                     String.valueOf(Ingreso.usuario.getIdSucursal()));
            sucursal = sucursal.replace("á", "a");
            sucursal = sucursal.replace("é", "e");
            sucursal = sucursal.replace("í", "i");
            sucursal = sucursal.replace("ó", "o");
            sucursal = sucursal.replace("ú", "u");
            
            sucursal = sucursal.replace("Á", "A");
            sucursal = sucursal.replace("É", "E");
            sucursal = sucursal.replace("Í", "I");
            sucursal = sucursal.replace("Ó", "O");
            sucursal = sucursal.replace("Ú", "U");
            
            
            String encabezadoMensaje = "<html><body><b><p style='font-size:22px;'>"
                    + "Fecha : "
                    + fechaS + "</p></b><br>";
            String cuerpoMensaje = "<b><p style='font-size:22px;'>Empresa : " 
                    + this.getTitle() 
                    + " Sucursal : " + sucursal
                    + "<br> Usuario : " + Ingreso.usuario.getNombre()
                    + " " + Ingreso.usuario.getApellido_paterno()
                    + " " + Ingreso.usuario.getApellido_materno()
                    + "</p></b><br><br>";
            cuerpoMensaje = cuerpoMensaje 
                    + "<p style='font-size:20px;'>Ventas: " + txtTotalVentas
                            .getText()
                    + "<br> Compras: " + txtTotalCompras.getText()
                    + "<br> Ingresos Varios: " + txtTotalIngresos.getText()
                    + "<br> Gastos Varios: " + txtTotalGastos.getText()
                    + "<br>Total: " + txtTotal.getText()
                    + "</p><br>";
            String finMensaje = "</body></html>";
            String mensaje = encabezadoMensaje + cuerpoMensaje + finMensaje;
//            String mensaje = "Hola";
                //fin arma mensaje
            
                //remitente
            String remitente = "matservices07@gmail.com";
                //fin remitente
            
                //destinatario
            String destinatario = configuracionBean.getEmailEmpresa();
                //fin destinatario
            
                //titulo
            String titulo = "Corte de Caja Sucursal : " + sucursal;
                //fin titulo
                    
                //negocio
            String negocio = this.getTitle() + " " 
                    + sucursal;
                //fin negocio
            boolean enviado = util.enviaCorreo(mensaje,remitente,destinatario
                ,titulo,negocio);
            int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas "
                    + "Imprimir el Corte?", "Mensaje..!!", 
                    JOptionPane.YES_NO_OPTION);
            if (resultado == JOptionPane.YES_OPTION) {
                //imprime ticket
                imprimeCorte();
                //fin imprime ticket
            }
            //fin envia correo
            corteCajaHoy.clear();
            recargarTable(corteCajaHoy);
            JOptionPane.showMessageDialog(null, "CORTE DE CAJA PROCESADO");
            this.setVisible(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "DEBES TENER OPERACIONES PARA "
                    + "REGISTRAR EL CORTE");
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_btnGuardarPerActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void txtTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalFocusGained
    }//GEN-LAST:event_txtTotalFocusGained

    public void recargarTable(ArrayList<CorteCajaBean> list) {
        Object[][] datos = new Object[list.size()][6];
        int i = 0;
        for (CorteCajaBean p : list) {
            datos[i][0] = p.getIdMov();
            datos[i][1] = p.getFecha();
            datos[i][2] = p.getIdUsuario();
            datos[i][3] = p.getIdSucursal();
            datos[i][4] = p.getTotal();
            datos[i][5] = p.getTipoMov();
            i++;
        }
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID MOV", "FECHA", "USUARIO", "SUCURSAL", "TOTAL", "TIPO MOV"
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
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmCorte().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarPer;
    private javax.swing.JButton btnSalirPer;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalCompras;
    private javax.swing.JTextField txtTotalGastos;
    private javax.swing.JTextField txtTotalIngresos;
    private javax.swing.JTextField txtTotalVentas;
    // End of variables declaration//GEN-END:variables
}