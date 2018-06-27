package ComponenteConsulta;

import beans.DatosEmpresaBean;
import beans.MovimientosBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSMovimientosList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import util.Util;

public class JDListaMovimientos extends javax.swing.JDialog {
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    DefaultTableModel LMovimiento = new DefaultTableModel();
    
    //WS
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    Util util = new Util();
    WSInventarios hiloInventarios;
    WSMovimientosList hiloMovimientosList;
    //Fin WS
    
    Map<String,String> sucursalesHMCons = new HashMap();
    Map<String,String> proveedoresHMCons = new HashMap();
    Map<String,String> categoriasHMCons = new HashMap();
    Map<String,String> usuariosHMCons = new HashMap();
    Map<String,String> productosHMCons = new HashMap();
    Map<String,String> productosHMIDCons = new HashMap();

    /** Creates new form JDListaPersonal */
    public JDListaMovimientos(java.awt.Frame parent, boolean modal
            , Map<String,String> sucursalesHMCons
            , Map<String,String> productosHMCons
            , Map<String,String> productosHMIDCons
            , Map<String,String> usuariosHMCons
            , Map<String,String> proveedoresHMCons
            , String idArticulo) {        
        super(parent, modal);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());

        ArrayList<MovimientosBean> resultWSArray = null;
        hiloMovimientosList = new WSMovimientosList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETMOVIMIENTOBUSQUEDAID") 
                + idArticulo.trim();
        resultWSArray = hiloMovimientosList.ejecutaWebService(rutaWS,"2");
        Util util = new Util();
        //FrmProducto frmproductos = new FrmProducto();
        String titulos[] = {"ID MOVIMIENTO","PRODUCTO","USUARIO","MOVIMIENTO"
                ,"CANTIDAD","FECHA", "SUCURSAL"};
        LMovimiento.setColumnIdentifiers(titulos);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-yyyy");
        for (MovimientosBean movs : resultWSArray) {
            String producto = util.buscaDescFromCodProd(productosHMIDCons, "" 
                    + movs.getIdArticulo());
            String usuario = util.buscaDescFromIdUsu(usuariosHMCons, "" 
                    + movs.getIdUsuario());
            String sucursal = util.buscaDescFromIdSuc(sucursalesHMCons, "" 
                    + movs.getIdSucursal());
            String Datos[] = {"" + movs.getIdMovimiento()
                , producto
                , usuario
                , movs.getTipoOperacion()
                , "" + movs.getCantidad()
                , dateFormat.format(movs.getFechaOperacion())
                , sucursal};
            LMovimiento.addRow(Datos);
        }
        initComponents();
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
        jtListaMovimientos = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LISTA DE MOVIMIENTOS");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jtListaMovimientos.setModel(LMovimiento);
        jScrollPane1.setViewportView(jtListaMovimientos);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton2.setText("SALIR");
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(67, 67, 67))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtListaMovimientos;
    // End of variables declaration//GEN-END:variables
}
