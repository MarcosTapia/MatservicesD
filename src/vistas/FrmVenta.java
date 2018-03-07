package vistas;
import beans.VentasBean;
import beans.ProductoBean;
import beans.DetalleVentaBean;
import beans.*;
import ComponenteConsulta.*;
import ComponenteDatos.*;
import ComponenteReportes.ReporteGVenta;
import ComponenteDatos.ConfiguracionDAO;
import Ticket.Ticket;
import static componenteUtil.NumberToLetterConverter.convertNumberToLetter;
import java.awt.Toolkit;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class FrmVenta extends javax.swing.JFrame {
    ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();
    ConfiguracionBean configuracionBean = new ConfiguracionBean();
    BDFechaServidor bdFechaServidor = new BDFechaServidor();
    FechaServidorBean fechaServidorBean;
    HashMap<String, String> NombreProducto = new HashMap<String, String>();
    /****** temp ****/
    HashMap<String, String> CategoriaProducto = new HashMap<String, String>();
    HashMap<String, Integer> ClientesHM = new HashMap<String, Integer>();
    
    //VENTA DE PRODUCTO
    BDProductosProveedoresCostos bdProductosProveedoresCostos = new 
        BDProductosProveedoresCostos();

    ProductoBean prodParcial = null;    
    ProductoBean prodADisminuir = null;    
    
    BDVentas bdVentas = null;
    VentasBean ventasBean;
    
    BDDetalleVenta bdDetalleVenta;
    List<DetalleVentaBean> detalleVentaProducto = new ArrayList<>();
    DetalleVentaBean detalleVenta = null;

    ClienteBean clienteBean;
    BDCliente bdCliente = new BDCliente();
    
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
    Date fecha = null;
    double subTotal = 0.0, importe = 0.0, vuelto = 0.0, totalIGV = 
            0.0, montoTotal = 0.0, montoCuota = 0.0;
    int nTipoDocumento = 0, nVenta = 0,nCliente=0;
    
    double ivaEmpresa = 0;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FrmVenta() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        lblUsuario.setText("Usuario : "+Ingreso.usuario.getNombre());
        
        this.setTitle("Ventas");
        
        // Actualizas tbl producto
        ArrayList<ClienteBean> resultClientes;  
        try {
            resultClientes = BDCliente.mostrarCliente();
            for (ClienteBean cli : resultClientes) {
                ClientesHM.put(cli.getcCliNombre(),cli.getnCliCodigo());
                cboClientes.addItem(cli.getcCliNombre());
            }
            cboClientes.setSelectedItem("PÚBLICO");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        configuracionBean = configuracionDAO.obtieneConfiguracion(1);
        this.setTitle(configuracionBean.getNombreEmpresa());
        ivaEmpresa = configuracionBean.getIva();
        
//        //muestra fecha servidor **comentado porque acceso es local
//        try {
//            if (BDFechaServidor.actualizarFecha()) {
//                fechaServidorBean = BDFechaServidor.mostrarFechaServidor();
//                String a = DateFormat.getDateInstance(DateFormat.LONG).
//                        format(fechaServidorBean.getFechaServidor());
//                txtFecha.setText(a);
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        //muestra fecha servidor

        //solo para acceso local
        java.util.Date fechaLocal = new Date();
        String a = DateFormat.getDateInstance(DateFormat.LONG).
                format(fechaLocal);
        txtFecha.setText(a);
        //fin solo para acceso local
        
        //Muestra max id tabla ventas
        txtNroVenta.setText("" + obtenerUltimoId());

        // Actualizas tbl producto
        ArrayList<ProductoBean> result;  
        try {
            result = BDProducto.mostrarProducto();
            recargarTableProductos(result);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        String titulos[] = {"CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO UND.", 
                            "IMPORTE"};
        ListaProductoV.setColumnIdentifiers(titulos);
        this.setLocationRelativeTo(null);
    }

    public void imprimir(VentasBean ventaTitulos){
        try{
            //Primera parte
//            Date date=new Date();
//            SimpleDateFormat fecha=new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat hora=new SimpleDateFormat("hh:mm:ss aa");
            Ticket ticket = new Ticket();
            ticket.AddCabecera("" + configuracionBean.getNombreEmpresa());
//            ticket.AddCabecera(ticket.DarEspacio());
//            ticket.AddCabecera("         GUSTAVO PARAMO FIGUEROA");
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("CALLE DEL NEGOCIO");
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("COL. DEL NEGOCIO");
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("RFC DEL NEGOCIO");
            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddCabecera("TEL. DEL NEGOCIO");
            ticket.AddCabecera(ticket.DarEspacio());
            
//            ticket.AddCabecera("     tlf: 222222  r.u.c: 22222222222");
//            ticket.AddCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));

            //Segunda parte
            ticket.AddSubCabecera(ticket.DarEspacio());
//            SimpleDateFormat fecha=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
//            String fechaImpresion = fecha.format(ventaTitulos.getcVenFecha());
            String fechaImpresion = ventaTitulos.getcVenFecha();
            ticket.AddSubCabecera("Venta No:'" + 
                    ventaTitulos.getnVenCodigo()+"'   " +
                    fechaImpresion);
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));
            
            //tercera parte
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera("CANT   DESCRIPCION         P.U   IMPORTE");
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera(ticket.DibujarLinea(40));
            
            //cuarta parte detalle detalleVentaProducto
            ticket.AddSubCabecera(ticket.DarEspacio());
            for(DetalleVentaBean detalleVentaProdBean :  detalleVentaProducto) {
               //cantidad de decimales
               NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
               DecimalFormat form = (DecimalFormat)nf;
               form.applyPattern("#,###.00");
               //cantidad
               String cantidad = ""+detalleVentaProdBean.getCantidad();
               if(cantidad.length()<4){
                   int cant=4-cantidad.length();
                   String can="";
                   for(int f=0;f<cant;f++){
                       can+=" ";
                   }
                   cantidad+=can;
               }
                
                //descripcion
                String item = NombreProducto.get(detalleVentaProdBean.getCodigo());
                if(item.length()>17) {
                    item=item.substring(0,16)+".";
                } else {
                    int c=17-item.length();String comple="";
                    for(int y1=0;y1<c;y1++) {
                        comple+=" ";
                    }
                    item+=comple;
                }
                
                //precio unitario
                String precio=""+detalleVentaProdBean.getPrecioUnitario();
                double pre1=Double.parseDouble(precio);
                precio=form.format(pre1);
                if(precio.length()<8){
                    int p=8-precio.length();String pre="";
                    for(int y1=0;y1<p;y1++){
                        pre+=" ";
                    }
                    precio=pre+precio;
                }
                
                //total
                String total1 = "" + detalleVentaProdBean.getSubTotalParcial();
                total1 = form.format(Double.parseDouble(total1));
                if (total1.length()<8) {
                    int t=8-total1.length();String tota="";
                    for(int y1=0;y1<t;y1++){
                        tota+=" ";
                    }
                    total1=tota+total1;
                }
                //agrego los items al detalle
                ticket.AddItem(cantidad,item,precio,total1);
                //ticket.AddItem("","","",ticket.DarEspacio());
            }
            ticket.AddItem(ticket.DibujarLinea(40),"","","");
            
            //Quinta parte totales
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("SUBTOTAL                ",txtSubTotal.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("IVA                     ",txtIva.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("TOTAL                   ",txtMontoApagar.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("SU PAGO                 ",txtImporte.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("SU CAMBIO               ",txtVuelto.getText());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("",ticket.DarEspacio());
            
            //para cantidad con letra
            String numEnLetra = convertNumberToLetter(txtMontoApagar.getText());
            ticket.AddTotal("",numEnLetra.trim());
            ticket.AddPieLinea(ticket.DarEspacio());     
            ticket.AddPieLinea("                 GRACIAS!");
//            ticket.ImprimirDocumento("LPT1",true);
            ticket.ImprimirDocumento("usb002",true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "\nerror "+e.getMessage());
        }     
    }    
    
    public int obtenerUltimoId() {
        int id = 0;
        try {
            Connection con = BD.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(nVenCodigo) from ventas");
            while (rs.next()) {
                int lastID = rs.getInt(1);                
                id = lastID + 1;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null,error);
            id = 0;
        }
        return id;
    }

    public int getCodigopro() {
        return codigopro;
    }

    public void setCodigopro(int codigopro) {
        this.codigopro = codigopro;
    }

    public String getDescripcionpro() {
        return descripcionpro;
    }

    public void setDescripcionpro(String descripcionpro) {
        this.descripcionpro = descripcionpro;
    }

    public double getPreciounitprov() {
        return preciounitprov;
    }

    public void setPreciounitprov(double preciounitprov) {
        this.preciounitprov = preciounitprov;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void limpiarCajaTexto() {
        txtCantidadPro.setText("1");
        txtCodigoPro.setText("");
        txtImporte.setText("");
        txtSubTotal.setText("");
        txtVuelto.setText("");
        txtStockPro.setText("");        
        prodParcial = null;
        txtCodigoPro.requestFocus();
    }

    public void activarCajaTexto() {
        txtImporte.setText("0.0");
        txtSubTotal.setText("0.0");
        txtMontoApagar.setText("0.0");
        txtIva.setText("0.0");
        txtVuelto.setText("0.0");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNroVenta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtVendedorV = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtStockPro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCantidadPro = new javax.swing.JTextField();
        btnAumentar = new javax.swing.JButton();
        btnDisminuir = new javax.swing.JButton();
        btnAgregaProducto = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaProductos = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMontoApagar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtVuelto = new javax.swing.JTextField();
        cboClientes = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        btnGenerarVenta = new javax.swing.JButton();
        btnCancelarV = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarPro = new javax.swing.JTextField();
        cboParametroPro = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtProducto = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GENERAR NUEVA VENTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setText("FECHA :");

        txtFecha.setEditable(false);
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        jLabel2.setText("Nro VENTA :");

        txtNroVenta.setEditable(false);
        txtNroVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText("VENDEDOR :");

        txtVendedorV.setEditable(false);

        jPanel8.setBackground(new java.awt.Color(247, 254, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("PRODUCTO :");

        txtCodigoPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });
        txtCodigoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoProKeyReleased(evt);
            }
        });

        jLabel4.setText("STOCK :");

        txtStockPro.setEditable(false);
        txtStockPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("CANTIDAD :");

        txtCantidadPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidadPro.setText("1");
        txtCantidadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadProActionPerformed(evt);
            }
        });
        txtCantidadPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProKeyTyped(evt);
            }
        });

        btnAumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Create.png"))); // NOI18N
        btnAumentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentarActionPerformed(evt);
            }
        });

        btnDisminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Remove.png"))); // NOI18N
        btnDisminuir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDisminuir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDisminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisminuirActionPerformed(evt);
            }
        });

        btnAgregaProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addVenta.png"))); // NOI18N
        btnAgregaProducto.setText("Agregar");
        btnAgregaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregaProductoActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrarVenta.png"))); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAumentar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(btnAgregaProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAgregaProducto)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDisminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAumentar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jLabel13.setText("CLIENTE :");

        tblListaProductos.setModel(ListaProductoV);
        tblListaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListaProductos);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel15.setText("SUB. TOTAL:");

        txtSubTotal.setEditable(false);
        txtSubTotal.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtSubTotal.setForeground(new java.awt.Color(51, 51, 255));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtIva.setEditable(false);
        txtIva.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtIva.setForeground(new java.awt.Color(51, 51, 255));
        txtIva.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel5.setText("IVA :");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel19.setText("TOTAL :");

        txtMontoApagar.setEditable(false);
        txtMontoApagar.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtMontoApagar.setForeground(new java.awt.Color(51, 51, 255));
        txtMontoApagar.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel6.setText("PAGA CON :");

        txtImporte.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtImporte.setForeground(new java.awt.Color(51, 51, 255));
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImporteActionPerformed(evt);
            }
        });
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel18.setText("CAMBIO :");

        txtVuelto.setEditable(false);
        txtVuelto.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtVuelto.setForeground(new java.awt.Color(51, 51, 255));
        txtVuelto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVendedorV, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(177, 177, 177))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel15))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel19)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMontoApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel18)
                                            .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtVendedorV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addGap(3, 3, 3)
                        .addComponent(txtMontoApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(551, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(247, 254, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGenerarVenta.setText("GENERAR VENTA");
        btnGenerarVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerarVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        btnCancelarV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        btnCancelarV.setText("CANCELAR");
        btnCancelarV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelarV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarVActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario)
                .addGap(258, 258, 258)
                .addComponent(btnGenerarVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarV, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel9.setText("BUSCAR PRODUCTO ");

        txtBuscarPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProKeyReleased(evt);
            }
        });

        cboParametroPro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Código" }));

        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Codigo", "Descripción"
            }
        ));
        jtProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProductoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtProducto);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtBuscarPro)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        Ventas ventas = new Ventas();
//        ventas.setExtendedState(ventas.MAXIMIZED_BOTH);
////        ventas.setVisible(true);
    }//GEN-LAST:event_btnSalirActionPerformed

     private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    
    public void buscarProd() {
        try {
            prodParcial = BDProducto.buscarProductoCodigoVentas(txtCodigoPro.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"" + e.getMessage());
        }
        if (prodParcial != null) {
            txtStockPro.setText(String.valueOf(prodParcial.getCantidad()));
            txtCantidadPro.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "NO EXISTE EL PRODUCTO EN EL INVENTARIO");
            prodParcial = null;
        }
    }

    private void btnAumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentarActionPerformed
        int temp = Integer.parseInt(txtCantidadPro.getText())+1;        
        txtCantidadPro.setText(""+temp);
    }//GEN-LAST:event_btnAumentarActionPerformed

    private void btnDisminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisminuirActionPerformed
        int temp = Integer.parseInt(txtCantidadPro.getText())-1;        
        txtCantidadPro.setText(""+temp);
    }//GEN-LAST:event_btnDisminuirActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "¿Deseas Ejecutar la "
                + "Venta?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
        // VERIFICA si realmente se quierte guardar la venta
        if (result == JOptionPane.YES_OPTION) {
            //VERIFICA SI HAY PRODUCTO A VENDER
            if (detalleVentaProducto.size()>0) {
                //VERIFICA QUE HAYA PAGO Y CAMBIO EN LA VENTA
                if (!txtImporte.getText().equalsIgnoreCase("") && 
                        !txtVuelto.getText().equalsIgnoreCase("")) {
                    //Guarda bean venta
                    ventasBean = new VentasBean();
                    ventasBean.setIdUsuario(Ingreso.usuario.getIdUsuario());
                    
                    //adecuacion para sqlite
                    java.util.Date utilDate = new java.util.Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(utilDate);
                    cal.set(Calendar.MILLISECOND, 0);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                    Date date = new Date();
                    //Fin adecuacion para sqlite
                    //JOptionPane.showMessageDialog(null,""+(new java.sql.Timestamp(utilDate.getTime())));
                    ventasBean.setcVenFecha(dateFormat.format(date));
                    
                    ClienteBean clienteBean = new ClienteBean();
                    try {
                        clienteBean = bdCliente.buscarClienteCodigo(
                            ClientesHM.get(cboClientes.getSelectedItem()));
                        //txtCodCliente.setText(clienteBean.getcCliNombre());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    
                    
                    ventasBean.setnCliCodigo(clienteBean.getnCliCodigo());
                    ventasBean.setnVenCodigo(Integer.parseInt(txtNroVenta.getText()));
                    ventasBean.setnVenMontoTotal(Double.parseDouble(txtMontoApagar.getText()));
                    try {
                        //guarda venta
                        bdVentas = new BDVentas();                        
                        bdVentas.guardaVenta(ventasBean);
                        //guarda detalle venta
                        bdDetalleVenta = new BDDetalleVenta();
                        for (DetalleVentaBean detVentBean :detalleVentaProducto) {
                            bdDetalleVenta.guardarDetalleVenta(detVentBean);                            
                        }
                        //disminuye de inventario
                        for (DetalleVentaBean detVentBeanADisminuir :detalleVentaProducto) {
                            prodADisminuir = BDProducto.buscarProducto(
                                    detVentBeanADisminuir.getCodigo(), prodADisminuir);
                            prodADisminuir.setCantidad(prodADisminuir.getCantidad()
                                    -detVentBeanADisminuir.getCantidad());
                            BDProducto.actualizarProducto(prodADisminuir);
                        }
                        
                        //imprime ticket
                        int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas "
                                + "Imprimir la Venta?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
                        if (resultado == JOptionPane.YES_OPTION) {
                            imprimir(ventasBean);
//                            JOptionPane.showMessageDialog(null, "Se imprime el ticket");                             
                        }

                        borrar();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "DEBES COMPLETAR LA VENTA PAGANGO EL "
                            + "IMPORTE CORRESPONDIENTE");                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS PARA VENDER");
                return;
            }
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void tblListaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblListaProductosMouseClicked

    private void btnCancelarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarVActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarVActionPerformed

    private void borrar(){
        txtBuscarPro.setText("");
        actualizarBusquedaProducto();
        txtNroVenta.setText("" + obtenerUltimoId());
        detalleVentaProducto.clear();
        prodParcial = null;    
        recargarTableVentaParcialProductos(detalleVentaProducto);
        limpiarCajaTexto();
        actualizaTotales(detalleVentaProducto);        
    }
    
    private void txtCantidadProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProKeyTyped
        // TODO add your handling code here:
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadProKeyTyped

    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased

    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (Ingreso.usuario == null) {
            JOptionPane.showMessageDialog(null, "Error en el sistema vuelve a iniciar");
            this.dispose();
        } else {
            txtVendedorV.setText(""+Ingreso.usuario.getNombre());
        }
    }//GEN-LAST:event_formWindowActivated

    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        buscarProd();
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void jtProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoMouseClicked
        try {
            prodParcial = BDProducto.buscarProducto(
                    String.valueOf(jtProducto.getModel().getValueAt(jtProducto.getSelectedRow(),0)));
            txtCodigoPro.setText(prodParcial.getCodigo());
            txtStockPro.setText(""+prodParcial.getCantidad());
            txtCantidadPro.setText("1");
            txtCantidadPro.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error Al Seleccionar Elemento:" + ex.getMessage());
            prodParcial = null;
        }
    }//GEN-LAST:event_jtProductoMouseClicked

    private boolean buscarEnCarrito(String codigo) {
        boolean existe = false;
        try {
            for (DetalleVentaBean DetalleVenta : detalleVentaProducto) {
                if (DetalleVenta.getCodigo().equalsIgnoreCase(codigo)) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                }
            }            
        } catch(Exception e) {            
            existe = false;
        } finally {
            return existe;            
        }
    }
    
    private boolean eliminaProdDeCarrito(String codigo) {
        boolean existe = false;
        for (DetalleVentaBean DetalleVenta : detalleVentaProducto) {
            if (DetalleVenta.getCodigo().equalsIgnoreCase(codigo)) {
                detalleVentaProducto.remove(DetalleVenta);
                existe = true;
                break;
            } else {
                existe = false;
            }
        }
        return existe;
    }
    
    private void btnAgregaProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregaProductoActionPerformed
        //Verifica que haya producto seleccionado
        if ((prodParcial != null) && (!txtCodigoPro.getText().equalsIgnoreCase(""))) {
            // Verifico que sea el mismo producto que se consulto contra el cuadro de texto codigo
            if (txtCodigoPro.getText().equalsIgnoreCase(prodParcial.getCodigo())) {
                //verifica que el minimo del cuadro de texto sea el minimo del objeto a vender
                if (Integer.parseInt(txtStockPro.getText()) == prodParcial.getCantidad()) {
                    //verifica que haya existencia es decir que no rebase al minimo
                    if (Integer.parseInt(txtCantidadPro.getText()) <= prodParcial.getCantidad()) {
                        //Verifica que el producto no este ya agregado en la lista de compras
                        if (buscarEnCarrito(prodParcial.getCodigo())) {
                            JOptionPane.showMessageDialog(null, "YA ESTA AGREGADO EL PRODUCTO");
                            //limpiarCajaTexto();
                            return;
                        }
                        detalleVenta = new DetalleVentaBean();
                        int cantidad = Integer.parseInt(txtCantidadPro.getText());
                        detalleVenta.setnVenCodigo(Integer.parseInt(txtNroVenta.getText()));
                        detalleVenta.setCodigo(prodParcial.getCodigo());
                        detalleVenta.setCantidad(cantidad);
                        double precioUnitario = 0;
                        double precioSinIva = 0;
                        double IvaParcial = 0;
                        String categoriaTemp = "";
                        try {
                            //precio unitario de la fecha mas actual de los precios de los proveedores
                            //PrecioCompuestoBean pcb = new PrecioCompuestoBean();
                            //pcb = bdProductosProveedoresCostos.obtienePrecioMasActual(prodParcial.getCodigo());
                            ProductoBean pb = new ProductoBean();
                            pb = BDProducto.buscarProducto(prodParcial.getCodigo());
                            precioUnitario = pb.getPrecioPublico();
                            //JOptionPane.showMessageDialog(null, "num sin redondear"+precioUnitario);
                            precioUnitario = Math.floor(precioUnitario);
                            //precioUnitario = precioUnitario + 1;
//                            JOptionPane.showMessageDialog(null, "num con redondear round"+Math.round(precioUnitario));
//                            Math.floor(precioUnitario);
                            //JOptionPane.showMessageDialog(null, "num con redondear con floor"+precioUnitario);
                            
                            
                            //categoriaTemp = pcb.getCategoria();
                            //precioSinIva = pcb.getPrecioSinIva();
                            //JOptionPane.showMessageDialog(null, ""+precioUnitario);
                            if (precioUnitario==0) {
                                JOptionPane.showMessageDialog(null, "NO HAY PRECIO REGISTRADO DE ESTE PRODUCTO");
                                limpiarCajaTexto();
                                return;
                            }
//                            if (categoriaTemp.equalsIgnoreCase("GRAVADO")) {
//                                double precioUnitTemp = precioUnitario;
//                                //JOptionPane.showMessageDialog(null,"precioUnitario: "+precioUnitario );
//                                //precioUnitario = precioUnitario - (precioUnitario * 0.16);
//                                precioUnitario = precioUnitario / 1.16;
//                                //JOptionPane.showMessageDialog(null,"precioUnitario - iva: "+precioUnitario);
//                                IvaParcial = precioUnitTemp - precioUnitario;
//                            }
                            
                            detalleVenta.setPrecioUnitario(precioUnitario);
                            //FIN precio unitario de la fecha mas actual de los precios de los proveedores
                        } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                limpiarCajaTexto();
                                return;
                        }
                        detalleVenta.setSubTotalParcial(cantidad*precioUnitario);
                        //JOptionPane.showMessageDialog(null,"precioUnitario: "+precioUnitario );
                        IvaParcial = detalleVenta.getSubTotalParcial()*ivaEmpresa/100;
                        detalleVenta.setIvaParcial(IvaParcial);

                        //agrego producto a vender a lista parcial de venta
                        detalleVentaProducto.add(detalleVenta);
                        //actualizo tabla de venta
                        recargarTableVentaParcialProductos(detalleVentaProducto);
                        
                        //Actualiza Precios, totales y subtotales
                        actualizaTotales(detalleVentaProducto);
                        txtImporte.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe suficiente cantidad de producto para realizar la venta");
                        limpiarCajaTexto();
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No es el mismo producto repite la operación");
                    limpiarCajaTexto();
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No es el mismo producto que consultaste vuelve ha hacer la operación");
                prodParcial = null;
                limpiarCajaTexto();
                return;
            }            
            // Fin Verifico que sea el mismo producto que se consulto contra el cuadro de texto codigo
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto a vender");
            limpiarCajaTexto();
            return;
        }
        //Fin Verifica que haya producto seleccionado
    }//GEN-LAST:event_btnAgregaProductoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila = tblListaProductos.getSelectedRow();        
        if (fila >= 0) {
            String codigo = String.valueOf(tblListaProductos.getModel().getValueAt(tblListaProductos.getSelectedRow(),0));
            if (eliminaProdDeCarrito(codigo)) {
                try {
                    ((DefaultTableModel)tblListaProductos.getModel()).removeRow(fila);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                actualizaTotales(detalleVentaProducto);
            }
        } else {
            JOptionPane.showMessageDialog(null, "DEBES SELECCIONAR UN PRODUCTO");
            return;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtImporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImporteActionPerformed
        if (Double.parseDouble(txtImporte.getText()) >= 
                Double.parseDouble(txtMontoApagar.getText())) {
            txtVuelto.setText(""+((Double.parseDouble(txtImporte.getText())-
                    Double.parseDouble(txtMontoApagar.getText()))));   
            
            DecimalFormat df = new DecimalFormat("#.##");   
            txtVuelto.setText(""+df.format(Double.parseDouble(txtVuelto.getText())));  
            
            btnGenerarVenta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "EL PAGO DEBE SER MAYOR O IGUAL AL TOTAL A PAGAR ");
        }
    }//GEN-LAST:event_txtImporteActionPerformed

    private void txtCantidadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProActionPerformed
        btnAgregaProducto.requestFocus();
    }//GEN-LAST:event_txtCantidadProActionPerformed

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtImporteKeyTyped

    private void actualizarBusquedaProducto() {
        ArrayList<ProductoBean> result = null;
        if (String.valueOf(cboParametroPro.getSelectedItem()).equalsIgnoreCase("Código")) {
            result = BDProducto.listarProductoPorCodigo(txtBuscarPro.getText());
        } else {
            if (String.valueOf(cboParametroPro.getSelectedItem()).equalsIgnoreCase("Nombre")) {
                //if (!txtBuscarPro.getText().equalsIgnoreCase("")) {
                    result = BDProducto.listarProductoPorDescripcion(txtBuscarPro.getText());                
                //}
            }
        } 
        recargarTableProductos(result);
    }

    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][2];
        int i = 0;
        for (ProductoBean p : list) {
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getDescripcion();
            NombreProducto.put(p.getCodigo(), p.getDescripcion());
            i++;
        }
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "CODIGO", "DESCRIPCIÓN"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });
    } 
    
    //Para Tabla Productos
    public void recargarTableVentaParcialProductos(List<DetalleVentaBean> list) {
        try {
            if (list == null) {
                DefaultTableModel modelo = (DefaultTableModel) tblListaProductos.getModel();
                while(modelo.getRowCount()>0)
                    modelo.removeRow(0);
            }
            Object[][] datos = new Object[list.size()][5];
            int i = 0;
            for (DetalleVentaBean p : list) {
                datos[i][0] = p.getCodigo();
                datos[i][1] = NombreProducto.get(p.getCodigo());
                datos[i][2] = p.getCantidad();            
                
                //muestra 2 decimales en porc de descuento
                DecimalFormat df = new DecimalFormat("#.##");   
                String precioUnitario = df.format(p.getPrecioUnitario());  
                String importeParcial = df.format(p.getSubTotalParcial());  

                
//                datos[i][3] = p.getPrecioUnitario();
                datos[i][3] = precioUnitario;
                
                //datos[i][4] = p.getSubTotalParcial();
                datos[i][4] = importeParcial;
                i++;
            }
            tblListaProductos.setModel(new javax.swing.table.DefaultTableModel(
                    datos,
                    new String[]{
                        "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO UND.", "IMPORTE"
                   }) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch (Exception e) {
        }
    } 
    
    //Actualiza totales,subtotales, etc de venta
    public void actualizaTotales(List<DetalleVentaBean> list) {
        double subtotal = 0;
        double iva = 0;
        double total;
        int i = 0;
        try {
            for (DetalleVentaBean p : list) {
                subtotal = subtotal + p.getSubTotalParcial();
                iva = iva + p.getIvaParcial() * p.getCantidad();
            }            
        } catch (java.lang.NullPointerException e) {
            subtotal = 0;
        }
        
        txtSubTotal.setText(""+subtotal);        
        configuracionBean = configuracionDAO.obtieneConfiguracion(1);        
        //iva = subtotal * configuracionBean.getIva()/100;
        txtIva.setText(""+iva);
        total = iva + subtotal;
        txtMontoApagar.setText(""+total);    
        
        //muestra 2 decimales en porc de descuento
        DecimalFormat df = new DecimalFormat("#.##");   
        txtSubTotal.setText(""+df.format(Double.parseDouble(txtSubTotal.getText())));  
        txtIva.setText(""+df.format(Double.parseDouble(txtIva.getText())));  
        txtMontoApagar.setText(""+df.format(Double.parseDouble(txtMontoApagar.getText())));  

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
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FrmVenta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregaProducto;
    private javax.swing.JButton btnAumentar;
    private javax.swing.JButton btnCancelarV;
    private javax.swing.JButton btnDisminuir;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboClientes;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtProducto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblListaProductos;
    private javax.swing.JTextField txtBuscarPro;
    private javax.swing.JTextField txtCantidadPro;
    public javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtMontoApagar;
    private javax.swing.JTextField txtNroVenta;
    public javax.swing.JTextField txtStockPro;
    private javax.swing.JTextField txtSubTotal;
    public javax.swing.JTextField txtVendedorV;
    private javax.swing.JTextField txtVuelto;
    // End of variables declaration//GEN-END:variables
    public int codigopro;
    public String nombre;
    public String descripcionpro;
    public double preciounitprov;
    public int stock;
    DefaultTableModel ListaProductoV = new DefaultTableModel();
    private ReporteGVenta rGVenta;
}