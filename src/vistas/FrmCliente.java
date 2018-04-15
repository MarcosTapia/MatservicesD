package vistas;

import beans.ClienteBean;
import ComponenteConsulta.JDListaClientes;
import ComponenteDatos.*;
import beans.DatosEmpresaBean;
import beans.UsuarioBean;
import constantes.ConstantesProperties;
import consumewebservices.WSClientes;
import consumewebservices.WSClientesList;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;

public class FrmCliente extends javax.swing.JFrame {
    //WSUsuarios
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    //WSUsuarios
    WSClientesList hiloClientesList;
    WSClientes hiloClientes;
    //Fin WSUsuarios
    
    
    DatosEmpresaBean configuracionBean = new DatosEmpresaBean();
    ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();

    String accion = "";

    public FrmCliente() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
        hiloEmpresa = new WSDatosEmpresa();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty(""
                + "GETDATOSEMPRESA");
        DatosEmpresaBean resultadoWS = hiloEmpresa.
                ejecutaWebService(rutaWS,"1");
        
        actualizarBusqueda();
        activarBotones(true);
        
        lblUsuario.setText("Usuario : " 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
        configuracionBean = configuracionDAO.obtieneConfiguracion(1);
        this.setTitle(configuracionBean.getNombreEmpresa());
        this.setLocationRelativeTo(null);
    }

    public void obtenerUltimoId() {
        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(nCliCodigo) as Codigo from Cliente");
            while (rs.next()) {
                int lastID = rs.getInt(1);
                txtidCliente.setText(String.valueOf(lastID + 1));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void limpiarCajatexto() {
        txtidCliente.setText("");
        txtApellidos.setText("");
        txtRFC.setText("");
        txtNombreCli.setText("");
        txtDireccion1.setText("");
        cboTipoTelefonoCli.setSelectedItem("Seleccionar...");
        txtNroTelefonoCli.setText("");
        txtNroFaxCli.setText("");
        txtEmailCli.setText("");
        txtOtrosCli.setText("");
    }

    public void activarCajatexto(boolean b) {
        txtidCliente.setEditable(!b);
        txtApellidos.setEditable(b);
        txtRFC.setEditable(b);
        txtNombreCli.setEditable(b);
        txtDireccion1.setEditable(b);
        txtNroTelefonoCli.setEditable(b);
        txtNroFaxCli.setEditable(b);
        txtEmailCli.setEditable(b);
        txtOtrosCli.setEditable(b);
    }
    
    public void activarBotones(boolean b){
        btnNuevoCli.setEnabled(b);
        btnGuardarCli.setEnabled(!b);
        btnModificarCli.setEnabled(b);
        //btnCancelarCli.setEnabled(!b);
        btnMostrarCli.setEnabled(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarCli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboParametroCli = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtCliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoCli = new javax.swing.JButton();
        btnGuardarCli = new javax.swing.JButton();
        btnModificarCli = new javax.swing.JButton();
        btnCancelarCli = new javax.swing.JButton();
        btnMostrarCli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtidCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNombreCli = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNroTelefonoCli = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNroFaxCli = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmailCli = new javax.swing.JTextField();
        btnSalirCli = new javax.swing.JButton();
        btnEliminarCli = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        txtBuscarCli.setForeground(new java.awt.Color(255, 0, 0));
        txtBuscarCli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarCliMouseClicked(evt);
            }
        });
        txtBuscarCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCliKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 12)); // NOI18N
        jLabel3.setText("BUSCAR CLIENTE");

        cboParametroCli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Id", "Nombre" }));
        cboParametroCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroCliActionPerformed(evt);
            }
        });

        jtCliente.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null}
            },
            new String [] {
                "APELLIDOS", "NOMBRE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtClienteMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jtCliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboParametroCli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParametroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 20)); // NOI18N
        jLabel4.setText("REGISTRAR CLIENTE");

        btnNuevoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/New document_1.png"))); // NOI18N
        btnNuevoCli.setText("NUEVO");
        btnNuevoCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        btnGuardarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGuardarCli.setText("GUARDAR");
        btnGuardarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCliActionPerformed(evt);
            }
        });

        btnModificarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Modify.png"))); // NOI18N
        btnModificarCli.setText("MODIFICAR");
        btnModificarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCliActionPerformed(evt);
            }
        });

        btnCancelarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarCli.setText("CANCELAR");
        btnCancelarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCliActionPerformed(evt);
            }
        });

        btnMostrarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnMostrarCli.setText("MOSTRAR");
        btnMostrarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMostrarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMostrarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCliActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresar los datos del Cliente"));

        jLabel2.setText("Codigo :");

        txtidCliente.setEditable(false);

        jLabel5.setText("Apellidos (*):");

        txtApellidos.setEditable(false);
        txtApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidosActionPerformed(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        jLabel6.setText("RFC :");

        txtRFC.setEditable(false);
        txtRFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRFCActionPerformed(evt);
            }
        });
        txtRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRFCKeyTyped(evt);
            }
        });

        jLabel7.setText("Nombres (*):");

        txtNombreCli.setEditable(false);
        txtNombreCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCliActionPerformed(evt);
            }
        });

        jLabel8.setText("Dirección 1 :");

        txtDireccion1.setEditable(false);
        txtDireccion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Teléfono Casa :");

        txtNroTelefonoCli.setEditable(false);
        txtNroTelefonoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroTelefonoCliActionPerformed(evt);
            }
        });
        txtNroTelefonoCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroTelefonoCliKeyTyped(evt);
            }
        });

        jLabel9.setText("Teléfono Celular : ");

        txtNroFaxCli.setEditable(false);
        txtNroFaxCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFaxCliActionPerformed(evt);
            }
        });
        txtNroFaxCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroFaxCliKeyTyped(evt);
            }
        });

        jLabel10.setText("Email :");

        txtEmailCli.setEditable(false);
        txtEmailCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtNombreCli))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDireccion1))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNroFaxCli, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmailCli, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNroTelefonoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGap(14, 14, 14)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addComponent(jLabel6))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel10))
                .addContainerGap(333, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNroTelefonoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNroFaxCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnSalirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalirCli.setText("CERRAR");
        btnSalirCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalirCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCliActionPerformed(evt);
            }
        });

        btnEliminarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminarCli.setText("ELIMINAR");
        btnEliminarCli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminarCli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCliActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMostrarCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalirCli))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(162, 162, 162)
                        .addComponent(lblUsuario))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalirCli, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btnGuardarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btnModificarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btnCancelarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btnMostrarCli, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btnEliminarCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarCliMouseClicked
    }//GEN-LAST:event_txtBuscarCliMouseClicked

    private void btnSalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCliActionPerformed
        this.dispose();
        Configuracion operaciones = new Configuracion();
    }//GEN-LAST:event_btnSalirCliActionPerformed

    private void btnMostrarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCliActionPerformed
        JDListaClientes jdlCliente = new JDListaClientes(this,true);
        jdlCliente.setVisible(true);
    }//GEN-LAST:event_btnMostrarCliActionPerformed

    private void txtBuscarCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCliKeyReleased
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_txtBuscarCliKeyReleased

    private void cboParametroCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroCliActionPerformed
        // TODO add your handling code here:
        actualizarBusqueda();
    }//GEN-LAST:event_cboParametroCliActionPerformed

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(true);
        activarBotones(false);
        obtenerUltimoId();
        accion = "Guardar";
        txtRFC.requestFocus();
    }//GEN-LAST:event_btnNuevoCliActionPerformed

    private void btnCancelarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCliActionPerformed
        limpiarCajatexto();
        activarCajatexto(false);
        activarBotones(true);
        obtenerUltimoId();
    }//GEN-LAST:event_btnCancelarCliActionPerformed

    private void btnGuardarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCliActionPerformed
//        if (accion.equalsIgnoreCase("Guardar")) {
//            if (txtNombreCli.getText().compareTo("") != 0 &&
//                    txtApellidos.getText().compareTo("") != 0) {
//                try {
//                    ClienteBean cli = new ClienteBean();
//                    cli.setcCliNit(txtApellidos.getText());
//                    cli.setcCliCi(txtCiCli.getText());
//                    cli.setcCliNombre(txtNombreCli.getText());
//                    cli.setcCliDireccion(txtDireccionCli.getText());
//                    cli.setcCliEmail(txtEmailCli.getText());
//                    cli.setcCliNroFax(txtNroFaxCli.getText());
//                    cli.setcCliTipoTelefono((String) cboTipoTelefonoCli.getSelectedItem());
//                    cli.setcCliNumTelefono(txtNroTelefonoCli.getText());
//                    cli.setcCliOtros(txtOtrosCli.getText());
//                    BDCliente.insertarCliente(cli);
//                    JOptionPane.showMessageDialog(null, "[ Datos Agregados ]");
//                    //CODIGO MIO
//                    actualizarBusqueda();
//                    limpiarCajatexto();
//                    activarCajatexto(false);
//                    activarBotones(true);
//                    obtenerUltimoId();
//                    //FIN CODIGO MIO
//                } catch (SQLException e) {
//                    JOptionPane.showMessageDialog(null, e.getMessage());
//                }
//                limpiarCajatexto();
//                obtenerUltimoId();
//            } else {
//                JOptionPane.showMessageDialog(null, "Llena los campos requeridos!!");
//            }
//        }
//        if (accion.equalsIgnoreCase("Actualizar")) {
//            ClienteBean cli;
//            try {
//                cli = BDCliente.buscarClienteCodigo(Integer.parseInt(txtCodigoCli.getText()));
//                if (cli == null) {
//                    JOptionPane.showMessageDialog(null, "Selecciona el registro a modificar de la tabla de la izquierda");
//                    return;
//                }
//                cli.setcCliNit(txtApellidos.getText());
//                cli.setcCliCi(txtCiCli.getText());
//                cli.setcCliNombre(txtNombreCli.getText());
//                cli.setcCliDireccion(txtDireccionCli.getText());
//                cli.setcCliEmail(txtEmailCli.getText());
//                cli.setcCliNroFax(txtNroFaxCli.getText());
//                cli.setcCliTipoTelefono((String) cboTipoTelefonoCli.getSelectedItem());
//                cli.setcCliNumTelefono(txtNroTelefonoCli.getText());
//                cli.setcCliOtros(txtOtrosCli.getText());
//                BDCliente.actualizarCliente(cli);
//                JOptionPane.showMessageDialog(null, " [ Datos Actualizados ]");
//                //CODIGO MIO
//                actualizarBusqueda();
//                limpiarCajatexto();
//                activarCajatexto(false);
//                activarBotones(true);
//                obtenerUltimoId();
//                //FIN CODIGO MIO
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage());
//            }
//        }
    }//GEN-LAST:event_btnGuardarCliActionPerformed

    private void btnModificarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCliActionPerformed
        activarCajatexto(true);
        btnNuevoCli.setEnabled(false);
        btnGuardarCli.setEnabled(true);
        btnModificarCli.setEnabled(false);
        btnCancelarCli.setEnabled(true);
        btnMostrarCli.setEnabled(false);
        accion = "Actualizar";
    }//GEN-LAST:event_btnModificarCliActionPerformed

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        // TODO add your handling code here:
//        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
//            Toolkit.getDefaultToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFCKeyTyped
//        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
//            Toolkit.getDefaultToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_txtRFCKeyTyped

    private void txtNroTelefonoCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTelefonoCliKeyTyped
        // TODO add your handling code here:
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtNroTelefonoCliKeyTyped

    private void jtClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteMouseClicked
        ArrayList<ClienteBean> resultWS = null;
        hiloClientesList = new WSClientesList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCLIENTEPORID")
                + String.valueOf(jtCliente.getModel().getValueAt(jtCliente.getSelectedRow(), 0)).trim();
        resultWS = hiloClientesList.ejecutaWebService(rutaWS,"2");
        ClienteBean cli = resultWS.get(0);
        txtidCliente.setText(String.valueOf(cli.getIdCliente()));
        txtApellidos.setText(cli.getApellidos());
        txtRFC.setText(cli.getRfc());
        txtNombreCli.setText(cli.getNombre());
        txtDireccion1.setText(cli.getDireccion1());
//        txtEmailCli.setText(cli.getcCliEmail());
//        txtNroFaxCli.setText(cli.getcCliNroFax());
//        cboTipoTelefonoCli.setSelectedItem((String)cli.getcCliTipoTelefono());
//        txtNroTelefonoCli.setText(cli.getcCliNumTelefono());
//        txtOtrosCli.setText(cli.getcCliOtros());            
    }//GEN-LAST:event_jtClienteMouseClicked

    private void jtClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtClienteMouseEntered

    private void btnEliminarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCliActionPerformed
//            ClienteBean cli;
//            try {
//                cli = BDCliente.buscarClienteCodigo(Integer.parseInt(txtCodigoCli.getText()));
//                if (cli == null) {
//                    JOptionPane.showMessageDialog(null, "Selecciona el registro a eliminar de la tabla de la izquierda");
//                    return;
//                }
//                cli.setcCliNit(txtApellidos.getText());
//                cli.setcCliCi(txtCiCli.getText());
//                cli.setcCliNombre(txtNombreCli.getText());
//                cli.setcCliDireccion(txtDireccionCli.getText());
//                cli.setcCliEmail(txtEmailCli.getText());
//                cli.setcCliNroFax(txtNroFaxCli.getText());
//                cli.setcCliTipoTelefono((String) cboTipoTelefonoCli.getSelectedItem());
//                cli.setcCliNumTelefono(txtNroTelefonoCli.getText());
//                cli.setcCliOtros(txtOtrosCli.getText());
//                if (BDCliente.eliminarCliente(cli)) {
//                    limpiarCajatexto();
//                    activarCajatexto(false);
//                    activarBotones(true);
//                    obtenerUltimoId();
//                    JOptionPane.showMessageDialog(null, " [ Registro Eliminado ]");
//                    actualizarBusqueda();                
//                } else {
//                    JOptionPane.showMessageDialog(null, " [ ERROR ]");                
//                }
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage());
//            }
    }//GEN-LAST:event_btnEliminarCliActionPerformed

    private void txtNroFaxCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFaxCliKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtNroFaxCliKeyTyped

    private void txtRFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRFCActionPerformed
        txtApellidos.requestFocus();
    }//GEN-LAST:event_txtRFCActionPerformed

    private void txtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidosActionPerformed
        txtNombreCli.requestFocus();
    }//GEN-LAST:event_txtApellidosActionPerformed

    private void txtNombreCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCliActionPerformed
        txtDireccion1.requestFocus();
    }//GEN-LAST:event_txtNombreCliActionPerformed

    private void txtDireccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion1ActionPerformed
        cboTipoTelefonoCli.requestFocus();
    }//GEN-LAST:event_txtDireccion1ActionPerformed

    private void txtNroTelefonoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroTelefonoCliActionPerformed
        txtNroFaxCli.requestFocus();
    }//GEN-LAST:event_txtNroTelefonoCliActionPerformed

    private void txtNroFaxCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFaxCliActionPerformed
        txtEmailCli.requestFocus();
    }//GEN-LAST:event_txtNroFaxCliActionPerformed

    private void txtEmailCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailCliActionPerformed
        txtOtrosCli.requestFocus();
    }//GEN-LAST:event_txtEmailCliActionPerformed

    private void actualizarBusqueda() {
        ArrayList<ClienteBean> resultWS = null;
        if (String.valueOf(cboParametroCli.getSelectedItem()).
                equalsIgnoreCase("Nombre")) {
            if (txtBuscarCli.getText().equalsIgnoreCase("")) {
                hiloClientesList = new WSClientesList();
                String rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETCLIENTES");
                resultWS = hiloClientesList.ejecutaWebService(rutaWS,"1");
            } else {
                hiloClientesList = new WSClientesList();
                String rutaWS = constantes.getProperty("IP") + constantes.
                        getProperty("GETUSUARIOBUSQUEDANOMBRE")
                    + txtBuscarCli.getText().trim();
                resultWS = hiloClientesList.ejecutaWebService(rutaWS,"3");
            }
        } else {
            if (String.valueOf(cboParametroCli.getSelectedItem()).
                    equalsIgnoreCase("Id")) {
                if (txtBuscarCli.getText().equalsIgnoreCase("")) {
                    hiloClientesList = new WSClientesList();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETCLIENTES");
                    resultWS = hiloClientesList.ejecutaWebService(rutaWS,"1");
                } else {
                    hiloClientesList = new WSClientesList();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GETUSUARIOBUSQUEDAID") 
                            + txtBuscarCli.getText().trim();
                    resultWS = hiloClientesList.ejecutaWebService(rutaWS,"2");
                }
            }
        }        
        recargarTable(resultWS);
    }

    public void recargarTable(ArrayList<ClienteBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (ClienteBean p : list) {
            datos[i][0] = p.getIdCliente();
            datos[i][1] = p.getNombre() + " " + p.getApellidos();
            i++;
        }
        jtCliente.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "ID CLIENTE", "NOMBRE"
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
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmCliente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCli;
    private javax.swing.JButton btnEliminarCli;
    private javax.swing.JButton btnGuardarCli;
    private javax.swing.JButton btnModificarCli;
    private javax.swing.JButton btnMostrarCli;
    private javax.swing.JButton btnNuevoCli;
    private javax.swing.JButton btnSalirCli;
    private javax.swing.JComboBox cboParametroCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtCliente;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarCli;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtEmailCli;
    private javax.swing.JTextField txtNombreCli;
    private javax.swing.JTextField txtNroFaxCli;
    private javax.swing.JTextField txtNroTelefonoCli;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtidCliente;
    // End of variables declaration//GEN-END:variables
}