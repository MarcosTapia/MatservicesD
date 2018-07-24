package ComponenteConsulta;

import beans.DatosEmpresaBean;
import beans.ProductoBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import util.Util;
import vistas.BarraProgreso;
import vistas.FrmConsultas;
import vistas.FrmProducto;
import vistas.Ingreso;
import vistas.Principal;

public class JDListaAlertas extends javax.swing.JDialog {
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    DefaultTableModel LProducto = new DefaultTableModel();
    String empresa = "";
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSInventariosList hiloInventariosList;
    WSInventarios hiloInventarios;
    
    private String sucursal;

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public JDListaAlertas(java.awt.Frame parent, boolean modal
            , Map<String,String> sucursalesHMCons
            , Map<String,String> categoriasHMCons
            , Map<String,String> proveedoresHMCons) {
        super(parent, modal);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException 
                | InstantiationException | IllegalAccessException 
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
        
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        this.empresa = resultadoWS.getNombreEmpresa();
        
        ArrayList<ProductoBean> resultWSArray = null;
        hiloInventariosList = new WSInventariosList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETINVENTARIOS");
        resultWSArray = hiloInventariosList.ejecutaWebService(rutaWS,"1");
        
        Util util = new Util();
        FrmProducto frmproductos = new FrmProducto(0);
        String titulos[] = {"ID", "CODIGO", "DESCRIPCIÓN", "$ COSTO"
                , "$ PÚBLICO", "EXIST.", "EXIST. MIN","SUCURSAL", "CATEGORÍA"
                , "PROVEEDOR"};
        LProducto.setColumnIdentifiers(titulos);
        for (ProductoBean p : resultWSArray) {
            String sucursal = util.buscaDescFromIdSuc(sucursalesHMCons, "" 
                    + p.getIdSucursal());
            String categoria = util.buscaDescFromIdCat(categoriasHMCons, "" 
                    + p.getIdCategoria());
            String proveedor = util.buscaDescFromIdProv(proveedoresHMCons, "" 
                    + p.getIdProveedor());
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) 
                    || (Ingreso.usuario.getUsuario().equalsIgnoreCase
                    (constantes.getProperty("SUPERUSUARIO"))) 
                    ) {
                if ((p.getExistencia() <= p.getExistenciaMinima())) {
                    String Datos[] = {""+p.getIdArticulo()
                            , p.getCodigo()
                            , p.getDescripcion()
                            , "" + p.getPrecioCosto()
                            , "" + p.getPrecioUnitario()
                            , "" + p.getExistencia()
                            , "" + p.getExistenciaMinima()
                            , sucursal
                            , categoria
                            , proveedor};
                    LProducto.addRow(Datos);
                }
            }
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaProductos = new javax.swing.JTable();
        btnImprimir = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnConsultas = new javax.swing.JButton();
        lblNegocio = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DE PRODUCTOS POR SURTIR");

        tblConsultaProductos.setModel(LProducto);
        jScrollPane1.setViewportView(tblConsultaProductos);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Print.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
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

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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

        lblNegocio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNegocio.setText("Negocio:");

        lblUsuario.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblNegocio, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnConsultas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNegocio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        List Resultados = new ArrayList();
        int fila = 0;
        String datos ="";
        ProductoBean tipo;
        //recorrer la tabla
        for (fila=0;fila<tblConsultaProductos.getRowCount();fila++) {
            tipo = new ProductoBean();
            tipo.setDescripcion(String.valueOf(tblConsultaProductos
                    .getValueAt(fila, 2)));
            tipo.setPrecioCosto(Double.parseDouble(String.valueOf
                (tblConsultaProductos.getValueAt(fila, 3))));
            tipo.setExistencia(Double.parseDouble(String.valueOf
                (tblConsultaProductos.getValueAt(fila, 5))));
            //lo uso para sucursal para mostrarlo como string
            tipo.setObservaciones(String.valueOf(tblConsultaProductos
                    .getValueAt(fila, 7)));
            Resultados.add(tipo);
        }
        
        Map map = new HashMap();
        JasperPrint jPrint;
        JDialog reporte = new JDialog();
        reporte.setSize(900,700);
        reporte.setLocationRelativeTo(null);
        reporte.setTitle(this.empresa);
        
        map.put("empresa", this.empresa);
        java.util.Date fecha = new Date();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        map.put("fecha", "Fecha: " + a);
        try {
            this.dispose();
            jPrint = JasperFillManager.fillReport(this.getClass().
                    getClassLoader().getResourceAsStream
                    ("ComponenteReportes/reporteAlertas.jasper")
                    , map, new JRBeanCollectionDataSource(Resultados));
            JRViewer jv = new JRViewer(jPrint);
            reporte.getContentPane().add(jv);
            reporte.setVisible(true);
            reporte.requestFocus();
        } catch (JRException ex) {
            Logger.getLogger(JDListaAlertas.class.getName()).log(Level.SEVERE
                    , null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultasActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmConsultas frmConsultas = new FrmConsultas();
        frmConsultas.setExtendedState(frmConsultas.MAXIMIZED_BOTH);
        frmConsultas.setVisible(true);
    }//GEN-LAST:event_btnConsultasActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        lblNegocio.setText(Principal.datosEmpresaBean.getNombreEmpresa()
                + " Sucursal: " 
                + this.getSucursal());
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno()
        );
    }//GEN-LAST:event_formWindowActivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultas;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNegocio;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaProductos;
    // End of variables declaration//GEN-END:variables
}