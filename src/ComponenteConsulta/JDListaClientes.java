package ComponenteConsulta;

import beans.ClienteBean;
import beans.DatosEmpresaBean;
import constantes.ConstantesProperties;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import util.Util;
import vistas.Ingreso;

public class JDListaClientes extends javax.swing.JDialog {
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    DefaultTableModel LClientes = new DefaultTableModel();
    
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSClientesList hiloClientesList;
    WSClientes hiloClientes;
    
    /** Creates new form JDListaClientes */
    public JDListaClientes(java.awt.Frame parent, boolean modal
        , Map<String, String> municipios, Map<String, String> estados) {
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
        
        ArrayList<ClienteBean> resultWSArray = null;
        hiloClientesList = new WSClientesList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETCLIENTES");
        resultWSArray = hiloClientesList.ejecutaWebService(rutaWS,"1");
        
        Util util = new Util();
        String titulos[] = {"ID", "EMPRESA", "NOMBRE", "TEL. CASA", "CELULAR"
            , "DIRECCIÓN 1", "RFC", "EMAIL"
            , "NO. CUENTA", "CIUDAD", "ESTADO"};
        LClientes.setColumnIdentifiers(titulos);
        for (ClienteBean p : resultWSArray) {
            String Datos[] = {"" + p.getIdCliente()
                    , p.getEmpresa()
                    , p.getNombre() + " " + p.getApellidos()
                    , p.getTelefono_casa()
                    , p.getTelefono_celular()
                    , p.getDireccion1()
                    , p.getRfc()
                    , p.getEmail()
                    , p.getNoCuenta()
                    , util.buscaDescFromIdMun(municipios, "" + p.getCiudad())
                    , util.buscaDescFromIdEdo(estados, p.getEstado())
            };
            LClientes.addRow(Datos);
        }
        initComponents();
        this.setIcon();

        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        
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
        jtListaClientes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DE CLIENTES");

        jtListaClientes.setModel(LClientes);
        jScrollPane1.setViewportView(jtListaClientes);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(78, 78, 78))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtListaClientes;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}