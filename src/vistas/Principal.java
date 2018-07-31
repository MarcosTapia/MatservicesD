package vistas;

import beans.CategoriaBean;
import beans.ClienteBean;
import beans.DatosEmpresaBean;
import beans.EdoMunBean;
import beans.EstadoBean;
import beans.MunicipioBean;
import beans.ProductoBean;
import beans.ProveedorBean;
import beans.SistemaBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSSistema;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import util.Util;
import javax.swing.ImageIcon;

public class Principal extends javax.swing.JFrame {

    public static DatosEmpresaBean datosEmpresaBean;
    public static SistemaBean datosSistemaBean;
    static Map<String, String> municipiosHM = new HashMap();
    static Map<String, String> estadosMunHM = new HashMap();
    static Map<String, String> estadosHM = new HashMap();
    static Map<String, String> sucursalesHM = new HashMap();
    static Map<String, String> categoriasHM = new HashMap();
    static Map<String, String> usuariosHM = new HashMap();
    static Map<String, String> proveedoresHM = new HashMap();
    static Map<String, String> productosHM = new HashMap();
    static Map<String, String> productosHMID = new HashMap();
    static Map<String, String> clientesHM = new HashMap();

    //Globales 
    static ArrayList<MunicipioBean> municipios = new ArrayList();
    static ArrayList<EdoMunBean> estadosMun = new ArrayList();
    static ArrayList<EstadoBean> estados = new ArrayList();
    static ArrayList<SucursalBean> sucursales = new ArrayList();
    static ArrayList<CategoriaBean> categorias = new ArrayList();
    static ArrayList<UsuarioBean> usuarios = new ArrayList();
    static ArrayList<ProveedorBean> proveedores = new ArrayList();
    static ArrayList<ProductoBean> productos = new ArrayList();
    static ArrayList<ClienteBean> clientes = new ArrayList();
    
    //Bandera que decide si se cargan datos o no
    static boolean cargaDatosBand = true;

    Util util = new Util();

    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSSistema hiloSistema;

    String suc;
    
    public void cargaProductos() {
        //Carga productos
        productos = util.getInventario();
        util.llenaMapProductos(productos);
        productosHM = util.getProductosHM();
        productosHMID = util.getProductosHMID();
    }

    public void cargaUsuarios() {
        //Carga usuarios
        usuarios = util.getMapUsuarios();
        util.llenaMapUsuarios(usuarios);
        usuariosHM = util.getUsuariosHM();
    }

    public void cargaClientes() {
        //Carga clientes
        clientes = util.getMapClientes();
        util.llenaMapClientes(clientes);
        clientesHM = util.getClientesHM();
    }

    public void cargaProveedores() {
        //Carga proveedores
        proveedores = util.getMapProveedores();
        util.llenaMapProveedores(proveedores);
        proveedoresHM = util.getProveedoresHM();
    }
    
    public void cargaSucursales() {
        //Carga proveedores
        sucursales = util.getMapSucursales();
        util.llenaMapSucursales(sucursales);
        sucursalesHM = util.getSucursalesHM();
    }

    public Principal() {
        initComponents();
        this.setExtendedState(Principal.MAXIMIZED_BOTH);
        
        if (cargaDatosBand) {
            //Carga estados
            municipios = util.getMapMunicipios();
            util.llenaMapMunicipios(municipios);
            municipiosHM = util.getMunicipiosHM();

            //Carga estados y municipios(claves)
            estadosMun = util.getMapEstadosMun();

            //Carga estados
            estados = util.getMapEstados();
            util.llenaMapEstados(estados);
            estadosHM = util.getEstadosHM();

            //Carga sucursales
            sucursales = util.getMapSucursales();
            util.llenaMapSucursales(sucursales);
            sucursalesHM = util.getSucursalesHM();

            //Carga categorias
            categorias = util.getMapCategorias();
            util.llenaMapCategorias(categorias);
            categoriasHM = util.getCategoriasHM();

            //Carga usuarios
            usuarios = util.getMapUsuarios();
            util.llenaMapUsuarios(usuarios);
            usuariosHM = util.getUsuariosHM();

            //Carga proveedores
            proveedores = util.getMapProveedores();
            util.llenaMapProveedores(proveedores);
            proveedoresHM = util.getProveedoresHM();

            //Carga productos
            productos = util.getInventario();
            util.llenaMapProductos(productos);
            productosHM = util.getProductosHM();
            productosHMID = util.getProductosHMID();

            //Carga clientes
            cargaClientes();
            clientes = util.getMapClientes();
            util.llenaMapClientes(clientes);
            clientesHM = util.getClientesHM();

            //Carga proveedores
            cargaProveedores();
            proveedores = util.getMapProveedores();
            util.llenaMapProveedores(proveedores);
            proveedoresHM = util.getProveedoresHM();

            cargaDatosBand = false;
        }
        this.setExtendedState(Principal.MAXIMIZED_BOTH);
        this.setIcon();
        lblUsuario.setText("Bienvenido: " + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        
        suc = util.buscaDescFromIdSuc(sucursalesHM, "" 
                + Ingreso.usuario.getIdSucursal());
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }

    public void muestraPanel(int panel) {
        switch (panel) {
            case 1:
                this.setVisible(false);
                this.dispose();
                //ANTES
//                FrmInventario inventario = new FrmInventario();
//                inventario.setExtendedState(inventario.MAXIMIZED_BOTH);
//                inventario.setVisible(true);
                FrmConsultas frmConsultas = new FrmConsultas();
                frmConsultas.setExtendedState(frmConsultas.MAXIMIZED_BOTH);
                frmConsultas.setVisible(true);
                break;
            case 2:
                this.setVisible(false);
                this.dispose();
                FrmOperaciones ventas = new FrmOperaciones();
                ventas.setExtendedState(ventas.MAXIMIZED_BOTH);
                ventas.setVisible(true);
                break;
            case 3:
                this.setVisible(false);
                this.dispose();
                FrmConfiguracion operaciones = new FrmConfiguracion();
                operaciones.setExtendedState(operaciones.MAXIMIZED_BOTH);
                operaciones.setVisible(true);
                break;
            case 4:
                //no muestra frame solo acomoda menu principal
                lblTituloNegocio.setLocation(200, 100);
                panelMenuPrincipal.setBounds(200, 180, 810, 280);
                lblCerrarSesion.setLocation(200, 480);
                lblSalir.setLocation(700, 480);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelOpciones = new javax.swing.JPanel();
        panelMenuPrincipal = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        lblTituloNegocio = new javax.swing.JLabel();
        lblCerrarSesion = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        panelOpciones.setBackground(new java.awt.Color(247, 254, 255));
        panelOpciones.setBorder(javax.swing.BorderFactory.createMatteBorder(60, 20, 5, 5, new java.awt.Color(70, 99, 138)));
        panelOpciones.setLayout(null);

        panelMenuPrincipal.setBackground(new java.awt.Color(102, 102, 102));
        panelMenuPrincipal.setLayout(null);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ventas.jpg"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 102, 102)));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        panelMenuPrincipal.add(jLabel9);
        jLabel9.setBounds(290, 10, 250, 210);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/operations.jpg"))); // NOI18N
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 102, 102)));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        panelMenuPrincipal.add(jLabel11);
        jLabel11.setBounds(550, 10, 240, 210);

        jLabel12.setFont(new java.awt.Font("Baskerville Old Face", 2, 24)); // NOI18N
        jLabel12.setText("CONFIGURACIÓN");
        panelMenuPrincipal.add(jLabel12);
        jLabel12.setBounds(560, 230, 270, 30);

        jLabel10.setFont(new java.awt.Font("Baskerville Old Face", 2, 24)); // NOI18N
        jLabel10.setText("OPERACIONES");
        panelMenuPrincipal.add(jLabel10);
        jLabel10.setBounds(320, 230, 180, 30);

        jLabel8.setFont(new java.awt.Font("Baskerville Old Face", 2, 24)); // NOI18N
        jLabel8.setText("ADMINISTRACIÓN ");
        panelMenuPrincipal.add(jLabel8);
        jLabel8.setBounds(40, 230, 230, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inventory.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 102, 102)));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        panelMenuPrincipal.add(jLabel1);
        jLabel1.setBounds(30, 10, 240, 210);

        panelOpciones.add(panelMenuPrincipal);
        panelMenuPrincipal.setBounds(960, 140, 820, 290);

        lblSalir.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(102, 102, 255));
        lblSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir2.png"))); // NOI18N
        lblSalir.setText("SALIR");
        lblSalir.setToolTipText("SALIR DEL SISTEMA");
        lblSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
        });
        panelOpciones.add(lblSalir);
        lblSalir.setBounds(700, 490, 270, 150);

        lblTituloNegocio.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTituloNegocio.setForeground(new java.awt.Color(0, 102, 255));
        lblTituloNegocio.setText("Título del Negocio");
        panelOpciones.add(lblTituloNegocio);
        lblTituloNegocio.setBounds(170, 50, 1640, 60);

        lblCerrarSesion.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(102, 102, 255));
        lblCerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        lblCerrarSesion.setText("CERRAR SESIÓN");
        lblCerrarSesion.setToolTipText("CERRAR SESIÓN");
        lblCerrarSesion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
        });
        panelOpciones.add(lblCerrarSesion);
        lblCerrarSesion.setBounds(210, 490, 470, 150);

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        lblUsuario.setText("jLabel1");
        panelOpciones.add(lblUsuario);
        lblUsuario.setBounds(540, 20, 360, 29);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("© 2018 Matservices. Todos los derechos reservados.");
        panelOpciones.add(jLabel2);
        jLabel2.setBounds(770, 650, 330, 14);

        getContentPane().add(panelOpciones);
        panelOpciones.setBounds(-150, 0, 1830, 750);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Carga datos de la empresa
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETDATOSEMPRESA");
        datosEmpresaBean = hiloEmpresa.ejecutaWebService(rutaWS, "1");
        // Fin Carga datos de la empresa

        // Carga datos de la empresa
        hiloSistema = new WSSistema();
        rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETDATOSSISTEMA");
        datosSistemaBean = hiloSistema.ejecutaWebService(rutaWS, "1");
        // Fin Carga datos de la empresa

        this.setTitle(datosEmpresaBean.getNombreEmpresa());
        lblTituloNegocio.setText(datosEmpresaBean.getNombreEmpresa()
                + " Sucursal: " + suc);
        muestraPanel(4);
    }//GEN-LAST:event_formWindowOpened

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        muestraPanel(3);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        muestraPanel(2);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        this.dispose();
        Ingreso ingreso = new Ingreso();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        ingreso.setSize(525, 378);
        ingreso.setLocationRelativeTo(null);
        ingreso.setVisible(true);
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        muestraPanel(1);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTituloNegocio;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelMenuPrincipal;
    private javax.swing.JPanel panelOpciones;
    // End of variables declaration//GEN-END:variables
}
