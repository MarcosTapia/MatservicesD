package codigo;

import java.awt.Dimension;

public class Principal extends javax.swing.JFrame {
     private Dimension dim;
     
    public Principal() {
        initComponents();
        //con esto obtienes en tamano en en x y y de tu monitor
        dim=super.getToolkit().getScreenSize();
        this.setSize(dim);
        //this.setUndecorated(true);
        //this.setMaximumSize(this.setMaximizedBounds(BOTH));
        //this.setVisible(true);        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnInicio = new javax.swing.JToggleButton();
        btnInventario = new javax.swing.JToggleButton();
        btnVentas = new javax.swing.JToggleButton();
        btnCompras = new javax.swing.JToggleButton();
        btnConsultas = new javax.swing.JToggleButton();
        btnProveedores = new javax.swing.JToggleButton();
        btnClientes = new javax.swing.JToggleButton();
        btnEmpleados = new javax.swing.JToggleButton();
        btnConfiguracion = new javax.swing.JToggleButton();
        btnSalir = new javax.swing.JToggleButton();
        panelInicio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-inicio.png"))); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.setFocusable(false);
        btnInicio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInicio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnInicio);

        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-almacen.png"))); // NOI18N
        btnInventario.setText("Inventario");
        btnInventario.setFocusable(false);
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnInventario);

        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-producto.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setFocusable(false);
        btnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVentas);

        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-compra.png"))); // NOI18N
        btnCompras.setText("Compras");
        btnCompras.setFocusable(false);
        btnCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompras);

        btnConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-reporte.png"))); // NOI18N
        btnConsultas.setText("Consultas");
        btnConsultas.setFocusable(false);
        btnConsultas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsultas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultasActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConsultas);

        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-empleado.png"))); // NOI18N
        btnProveedores.setText("Proveedores");
        btnProveedores.setFocusable(false);
        btnProveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProveedores);

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setFocusable(false);
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClientes);

        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-usuario.png"))); // NOI18N
        btnEmpleados.setText("Empleados");
        btnEmpleados.setFocusable(false);
        btnEmpleados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmpleados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEmpleados);

        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-config2.png"))); // NOI18N
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.setFocusable(false);
        btnConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfiguracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConfiguracion);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/x48-salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setFocusable(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSalir);

        panelInicio.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuPrincipal/paisaje.jpg"))); // NOI18N

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel1)
                .addContainerGap(469, Short.MAX_VALUE))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void quitarSeleccion(int opcion) {
        btnInicio.setSelected(false);
        btnInventario.setSelected(false);
        btnVentas.setSelected(false);
        btnCompras.setSelected(false);
        btnConsultas.setSelected(false);
        btnProveedores.setSelected(false);
        btnClientes.setSelected(false);
        btnEmpleados.setSelected(false);
        btnConfiguracion.setSelected(false);
        btnSalir.setSelected(false);
        switch (opcion) {
            case 1: btnInicio.setSelected(true); break;
            case 2: btnInventario.setSelected(true); break;
            case 3: btnVentas.setSelected(true); break;
            case 4: btnCompras.setSelected(true); break;
            case 5: btnConsultas.setSelected(true); break;
            case 6: btnProveedores.setSelected(true); break;
            case 7: btnClientes.setSelected(true); break;
            case 8: btnEmpleados.setSelected(true); break;
            case 9: btnConfiguracion.setSelected(true); break;
            case 10: btnSalir.setSelected(true); break;
        }
        
    }
    
    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        quitarSeleccion(1);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        quitarSeleccion(2);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        quitarSeleccion(3);
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        quitarSeleccion(4);
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultasActionPerformed
        quitarSeleccion(5);
    }//GEN-LAST:event_btnConsultasActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        quitarSeleccion(6);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        quitarSeleccion(7);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        quitarSeleccion(8);
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        quitarSeleccion(9);
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String permisos = "1111111111";
        //dasactiva botones segun los permisos
        if (permisos.charAt(1) == '0') {
            btnInventario.setVisible(false);
        }
        if (permisos.charAt(2) == '0') {
            btnVentas.setVisible(false);
        }
        if (permisos.charAt(3) == '0') {
            btnCompras.setVisible(false);
        }
        if (permisos.charAt(4) == '0') {
            btnConsultas.setVisible(false);
        }
        if (permisos.charAt(5) == '0') {
            btnProveedores.setVisible(false);
        }
        if (permisos.charAt(6) == '0') {
            btnClientes.setVisible(false);
        }
        if (permisos.charAt(7) == '0') {
            btnEmpleados.setVisible(false);
        }
        if (permisos.charAt(8) == '0') {
            btnConfiguracion.setVisible(false);
        }
        
        //fin desactiva botones segun los permisos
    }//GEN-LAST:event_formWindowOpened

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnClientes;
    private javax.swing.JToggleButton btnCompras;
    private javax.swing.JToggleButton btnConfiguracion;
    private javax.swing.JToggleButton btnConsultas;
    private javax.swing.JToggleButton btnEmpleados;
    private javax.swing.JToggleButton btnInicio;
    private javax.swing.JToggleButton btnInventario;
    private javax.swing.JToggleButton btnProveedores;
    private javax.swing.JToggleButton btnSalir;
    private javax.swing.JToggleButton btnVentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panelInicio;
    // End of variables declaration//GEN-END:variables
}
