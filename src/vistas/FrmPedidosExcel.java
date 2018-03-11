package vistas;

import ComponenteDatos.BDProducto;
import ComponenteDatos.BDProductosProveedoresCostos;
import ComponenteDatos.BDProveedor;
import ComponenteDatos.ConfiguracionDAO;
import beans.DatosEmpresaBean;
import beans.ProductoBean;
import beans.ProductosProveedoresCostosBean;
import beans.UsuarioBean;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class FrmPedidosExcel extends javax.swing.JFrame {
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();
    
    ProductosProveedoresCostosBean productosProveedoresCostosBean;
    BDProductosProveedoresCostos bdProductosProveedoresCostos;
    HashMap<Integer, String> nombreProveedor = new HashMap<>();
    HashMap<String, String> nombreProducto = new HashMap<>();
    String[] titulosColumnas;
    Object[][] datos;
    int columnas;

    public FrmPedidosExcel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        configuracionBean = configuracionDAO.obtieneConfiguracion(1);
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
        tblPedidos.setCellSelectionEnabled(false);
        
        //Llena HashMap producoto
        ArrayList<ProductoBean> result = null;  
        try {
            result = BDProducto.mostrarProducto();
            for (ProductoBean p : result) {
                nombreProducto.put(p.getCodigo(), p.getDescripcion());
            }
//            recargarTableProductos(result);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        //Llena HashMap proveedores
        try {
            nombreProveedor = BDProveedor.mostrarProveedorHashMap();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        //Crea columnas de la tabla
        columnas = nombreProveedor.size() + 2;
        titulosColumnas = new String[columnas];
        titulosColumnas[0] = "CODPROD";
        titulosColumnas[1] = "DESCRIPCIÓN";
        int numeroColumna = 2;
        Iterator it = nombreProveedor.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            titulosColumnas[numeroColumna] = ""+e.getValue();
            numeroColumna++;
        }
        datos = new String[result.size()][columnas];
        int i = 0;
        
        for (ProductoBean p : result) {
            for (int j=0;j<titulosColumnas.length;j++) {
                datos[i][j] = "";
            }
            i++;
        }
        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
                datos,titulosColumnas));
        //Define ancho fijo columnas precios
//        TableColumn columna;        
//        for (int j=2;j<titulosColumnas.length;j+=2) {
//            columna = tblPedidos.getColumn(titulosColumnas[j]);
//            columna.setMaxWidth(150);
//        }
    }

    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
                datos,titulosColumnas));
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 40)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTOS");

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPedidosMouseClicked(evt);
            }
        });
        tblPedidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblPedidosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblPedidosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblPedidos);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(254, 254, 254))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPedidosMouseClicked
    }//GEN-LAST:event_tblPedidosMouseClicked

    private void tblPedidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPedidosKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            //obtiene nombre de producto
            tblPedidos.setValueAt(nombreProducto.get(
                    String.valueOf(tblPedidos.getModel().getValueAt(tblPedidos.getSelectedRow()-1, 0)))
                    ,tblPedidos.getSelectedRow()-1, 1);
            

            //obtiene ultimo precio de proveedor con ese producto
            ProductosProveedoresCostosBean result = null;

            //recorre titulos de columnas para obtener su key correspondiente y 
            //realizar la busqueda en tabla de prodprovprecios
            int numCol = 0;
            for (String tituloCol : titulosColumnas) {
                Iterator it = nombreProveedor.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry)it.next();
                    if (numCol > 1) {
                        if (tituloCol.equalsIgnoreCase(e.getValue().toString())) {
                            //JOptionPane.showMessageDialog(null, e.getValue()+" "+e.getKey());
                            //realiza busqueda correspondiente
                            try {
                                result = BDProductosProveedoresCostos.listarPPCPorCodProd(
                                        String.valueOf(tblPedidos.getModel().getValueAt(tblPedidos.getSelectedRow()-1, 0))
                                        ,(int) e.getKey());
                                if (result!=null) {
                                    tblPedidos.setValueAt("$"+result.getPrecioCosto()+" -- "+result.getFecha(),
                                            tblPedidos.getSelectedRow()-1, numCol);
                                } else {
                                    tblPedidos.setValueAt("",
                                            tblPedidos.getSelectedRow()-1, numCol);
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        }
                    }
                }
                numCol++;
            }
            
        }
    }//GEN-LAST:event_tblPedidosKeyReleased

    private void tblPedidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPedidosKeyTyped
    }//GEN-LAST:event_tblPedidosKeyTyped

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        Inventario inventario = new Inventario();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void actualizarBusqueda() {
        //ArrayList<ProveedorBean> result = null;
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
            java.util.logging.Logger.getLogger(FrmPedidosExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPedidosExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPedidosExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPedidosExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmPedidosExcel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPedidos;
    // End of variables declaration//GEN-END:variables
}