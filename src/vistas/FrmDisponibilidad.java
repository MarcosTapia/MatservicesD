package vistas;

import beans.UsuarioBean;
import beans.DatosEmpresaBean;
import beans.ProductoBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.security.MessageDigest;
import java.util.List;
import javax.swing.ImageIcon;
import util.Util;
import static vistas.Principal.productos;


public class FrmDisponibilidad extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSUsuariosList hiloUsuariosList;
    WSUsuarios hiloUsuarios;
    //Fin WSUsuarios
    
    private String codigo;
    ArrayList<ProductoBean> inventario = null;
    String sucursalSistema = "";

    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();

    FrmDisponibilidad(String codigo, ArrayList<ProductoBean> inventario) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.codigo = codigo;
        this.inventario = inventario;
        initComponents();
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno()
                + ", Sucursal: " 
                + util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                + Ingreso.usuario.getIdSucursal())        
        );
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        this.setTitle(resultadoWS.getNombreEmpresa());
        setIcon();
        recargarTableProductos(inventario);
        cboParametroPro.setSelectedIndex(1);
        txtBuscarPro.setText(codigo);
        actualizarBusquedaProducto();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarPro = new javax.swing.JTextField();
        cboParametroPro = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProducto = new javax.swing.JTable();
        btnSalirPer = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setText("BUSCAR PRODUCTO :");

        txtBuscarPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProKeyReleased(evt);
            }
        });

        cboParametroPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Descripción", "Código" }));
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
                "Id", "Nombre"
            }
        ));
        jtProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtProducto);

        btnSalirPer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.jpg"))); // NOI18N
        btnSalirPer.setText("REGRESAR");
        btnSalirPer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirPer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPerActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUsuario.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(202, 202, 202)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalirPer, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirPer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    private void buscaProducto() {
        ProductoBean prod = buscarProdPorCodSuc(inventario
                , codigo.trim()
                , Ingreso.usuario.getIdSucursal());
        if (prod == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el producto, "
                    + "debes registrarlo primero");
            txtBuscarPro.requestFocus(true);
            return;
        }
    }
    
    public ProductoBean buscarProdPorCodSuc(ArrayList<ProductoBean> inventario
            , String codigo, int suc) {
        ProductoBean prod = null;
        for (ProductoBean p: inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                prod = p;
                return prod;
            }
        }
        return prod;
    }
    
    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][5];
        int i = 0;
        for (ProductoBean p : list) {
            //filtra por sucursal
//            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
//                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes
//                            .getProperty("SUPERUSUARIO")))) {
            if (Ingreso.usuario.getIdSucursal() != p.getIdSucursal()) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = p.getCodigo();
                datos[i][2] = p.getDescripcion();
                datos[i][3] = p.getExistencia();
                datos[i][4] = util.buscaDescFromIdSuc(Principal.sucursalesHM
                        , "" + p.getIdSucursal());
                i++;
            }
        }
        Object[][] datosFinal = new Object[i][5];
        //Para filtrar los registros
        for (int j=0; j<i; j++) {
            if (datos[j][0]!=null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
                datosFinal[j][2] = datos[j][2];
                datosFinal[j][3] = datos[j][3];
                datosFinal[j][4] = datos[j][4];
            }
        }
        //Fin Para filtrar los registros
        
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
                new String[]{
                    "ID", "CODIGO", "DESCRIPCIÓN", "EXISTENCIA", "SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jtProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtProducto.getColumnModel().getColumn(0).setMaxWidth(0);
    } 
    
    private void btnSalirPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPerActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSalirPerActionPerformed

    private void cboParametroProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroProActionPerformed
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroProActionPerformed

    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

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
                producto.setIdArticulo(Integer.parseInt(jtProducto.getModel()
                        .getValueAt(i,0).toString()));
                producto.setCodigo(jtProducto.getModel().getValueAt(i,1).toString());
                producto.setDescripcion(jtProducto.getModel().getValueAt(
                    i,2).toString());
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM, 
                    jtProducto.getModel().getValueAt(
                    i,4).toString());
                producto.setIdSucursal(idSuc);
                producto.setExistencia(Double.parseDouble(jtProducto.getModel()
                        .getValueAt(i,3).toString()));
                producto.setDescripcion(jtProducto.getModel().getValueAt(
                    i,2).toString());
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
                    equalsIgnoreCase("Descripción")) {
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
    
    private void jtProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoMouseClicked
    }//GEN-LAST:event_jtProductoMouseClicked

    private void actualizarBusqueda() {
//        ArrayList<UsuarioBean> resultWS = null;
//        if (String.valueOf(cboParametroPro.getSelectedItem()).
//                equalsIgnoreCase("Nombre")) {
//            if (txtBuscarProducto.getText().equalsIgnoreCase("")) {
//                hiloUsuariosList = new WSUsuariosList();
//                String rutaWS = constantes.getProperty("IP") + constantes.
//                        getProperty("GETUSUARIOS");
//                resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"1");
//            } else {
//                hiloUsuariosList = new WSUsuariosList();
//                String rutaWS = constantes.getProperty("IP") + constantes.
//                        getProperty("GETUSUARIOBUSQUEDANOMBRE")
//                    + txtBuscarProducto.getText().trim();
//                resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"3");
//            }
//        } else {
//            if (String.valueOf(cboParametroPro.getSelectedItem()).
//                    equalsIgnoreCase("Id")) {
//                if (txtBuscarProducto.getText().equalsIgnoreCase("")) {
//                    hiloUsuariosList = new WSUsuariosList();
//                    String rutaWS = constantes.getProperty("IP") 
//                            + constantes.getProperty("GETUSUARIOS");
//                    resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"1");
//                } else {
//                    hiloUsuariosList = new WSUsuariosList();
//                    String rutaWS = constantes.getProperty("IP") 
//                            + constantes.getProperty("GETUSUARIOBUSQUEDAID") 
//                            + txtBuscarProducto.getText().trim();
//                    resultWS = hiloUsuariosList.ejecutaWebService(rutaWS,"2");
//                }
//            }
//        }        
//        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<UsuarioBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (UsuarioBean p : list) {
            datos[i][0] = p.getIdUsuario();
            datos[i][1] = p.getNombre() + " " + p.getApellido_paterno() 
                    + " " + p.getApellido_materno();
            i++;
        }
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID USUARIO", "NOMBRE"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalirPer;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtProducto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtBuscarPro;
    // End of variables declaration//GEN-END:variables
}