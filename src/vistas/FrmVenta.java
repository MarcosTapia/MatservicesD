package vistas;

import beans.VentasBean;
import beans.ProductoBean;
import beans.DetalleVentaBean;
import beans.*;
import Ticket.Ticket;
import static componenteUtil.NumberToLetterConverter.convertNumberToLetter;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetallePedidos;
import consumewebservices.WSDetalleVentas;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSPedidos;
import consumewebservices.WSVentas;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;

public class FrmVenta extends javax.swing.JFrame {
    public int codigopro;
    public String nombre;
    public String descripcionpro;
    public double preciounitprov;
    public int stock;
    DefaultTableModel ListaProductoV = new DefaultTableModel();

    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    WSVentas hiloVentas;
    WSDetalleVentas hiloDetalleVentas;
    WSPedidos hiloPedidos;
    WSDetallePedidos hiloDetallePedidos;
    WSMovimientos hiloMovimientos;
    //Fin WS
    
    String codProdAnterior = "";
    String accion = "";
    
    PedidoBean pedidoBean = null;
    DetallePedidoBean detallePedidoBean = null;
    List<DetallePedidoBean> detallePedidoProducto = new ArrayList<>();
    
    HashMap<String, String> NombreProducto = new HashMap<String, String>();
    ProductoBean prodParcial = null;    
    VentasBean ventasBean;
    List<DetalleVentaBean> detalleVentaProducto = new ArrayList<>();
    DetalleVentaBean detalleVenta = null;
        //Carga iva de la empresa de ganancia por producto
    double ivaEmpresa = Principal.datosSistemaBean.getIvaEmpresa();
    double ivaGral = Principal.datosSistemaBean.getIvaGral();
    ArrayList<ProductoBean> inventario = null;
    String sucursalSistema = "";
    
    //para garantizar no cargar siempre datos en el form activated
    boolean cargaDatos = false;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FrmVenta() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        java.util.Date fecha = util.obtieneFechaServidor();
        String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
        txtFecha.setText("Fecha: " + a);
        lblUsuario.setText(Principal.datosEmpresaBean.getNombreEmpresa()
                + " Sucursal: " 
                + util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                        + Ingreso.usuario.getIdSucursal()));
        sucursalSistema = util.buscaDescFromIdSuc(Principal.sucursalesHM, "" 
                        + Ingreso.usuario.getIdSucursal());
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        //Muestra max id tabla ventas
        txtNroVenta.setText("" + obtenerUltimoId());
        String titulos[] = {"CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO UND.", 
                            "IMPORTE"};
        ListaProductoV.setColumnIdentifiers(titulos);
        this.setLocationRelativeTo(null);
        
        txtIvaProd.setText("" + ivaEmpresa);
        txtCodigoPro.requestFocus(true);
        txtCodigoPro.setText("Espere...");
        txtVendedorV.setText("" 
                + Ingreso.usuario.getNombre()
                + " " + Ingreso.usuario.getApellido_paterno()
                + " " + Ingreso.usuario.getApellido_materno());
    }

    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    public void imprimeVenta(String tipoTicket){
        try {
            //Primera parte
//            Date date=new Date();
//            SimpleDateFormat fecha=new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat hora=new SimpleDateFormat("hh:mm:ss aa");
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
            if (tipoTicket.equalsIgnoreCase("Venta")) {
                ticket.AddSubCabecera("Venta No: " + 
                        txtNroVenta.getText() + "   " +
                        fechaImpresion);
            } else {
                ticket.AddSubCabecera("Ticket de Pedido: " + fechaImpresion);
            }
            ticket.AddSubCabecera(ticket.DarEspacio());
            ticket.AddSubCabecera("Le atendió: " + Ingreso.usuario.getNombre()
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
            for(DetalleVentaBean detalleVentaProdBean :  detalleVentaProducto) {
               //cantidad de decimales
               NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
               DecimalFormat form = (DecimalFormat)nf;
               form.applyPattern("#,###.00");
               //cantidad
               String cantidad = "" + detalleVentaProdBean.getCantidad();
               if(cantidad.length()<4){
                   int cant = 4 - cantidad.length();
                   String can = "";
                   for(int f=0;f<cant;f++){
                       can+=" ";
                   }
                   cantidad+=can;
               }
                
                //descripcion
               String item = util.buscaDescFromIdProd(Principal.productosHM
                       , "" + detalleVentaProdBean.getIdArticulo());
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
               String precio=""+detalleVentaProdBean.getPrecio();
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
                String total1 = "" + detalleVentaProdBean.getCantidad()
                        * detalleVentaProdBean.getPrecio();
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
            ticket.AddPieLinea("DEVOLUCIÓN SOLO CON TICKET");
            ticket.AddPieLinea(ticket.DarEspacio());     
            ticket.AddPieLinea("                 GRACIAS!");
//            ticket.ImprimirDocumento("LPT1",true);
            ticket.ImprimirDocumento("usb002",true);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            return;
        }     
    }    
    
    public int obtenerUltimoId() {
        int id = 0;
        VentasBean resultWS = null;
        hiloVentas = new WSVentas();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETULTIMOIDVENTAS");
        resultWS = hiloVentas.ejecutaWebService(rutaWS,"1");
        id = resultWS.getIdVenta() + 1;
        return id;
    }
    
    public int obtenerUltimoIdPedido() {
        int id = 0;
        PedidoBean resultWS = null;
        hiloPedidos = new WSPedidos();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETULTIMOIDPEDIDOS");
        resultWS = hiloPedidos.ejecutaWebService(rutaWS,"1");
        id = resultWS.getIdPedido() + 1;
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
        txtProducto.setText("");
        txtIvaProd.setText("" + ivaEmpresa);
        txtCantidadPro.setText("1");
        txtCodigoPro.setText("");
        txtImporte.setText("");
        txtSubTotal.setText("");
        txtVuelto.setText("");
        txtStockPro.setText("");        
//        prodParcial = null;
        txtCodigoPro.requestFocus();
        txtPrecio.setText("");
    }
    
    private void limpiaCaptura() {
        txtCodigoPro.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("0");
        txtCantidadPro.setText("1");
        txtStockPro.setText("");
        txtProducto.setText("");
        txtIvaProd.setText("" + ivaEmpresa);
        txtCodigoPro.requestFocus(true);
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
        btnElimProdVta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        txtProducto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtIvaProd = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
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
        btnClientes = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnCorte = new javax.swing.JButton();
        cboTipoVenta = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        btnGenerarVenta = new javax.swing.JButton();
        btnCancelarV = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        btnGenerarPedido = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();
        btnSalir1 = new javax.swing.JButton();
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
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel3.setBackground(new java.awt.Color(247, 254, 255));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GENERAR NUEVA VENTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setText("TIPO VENTA :");

        jLabel2.setText("Nro VENTA :");

        txtNroVenta.setEditable(false);
        txtNroVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText("VENDEDOR :");

        txtVendedorV.setEditable(false);

        jPanel8.setBackground(new java.awt.Color(247, 254, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("CÓDIGO");

        txtCodigoPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodigoProMouseClicked(evt);
            }
        });
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
        txtStockPro.setBackground(new java.awt.Color(255, 153, 102));
        txtStockPro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtStockPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("CANT. :");

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

        btnDisminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/restar.jpg"))); // NOI18N
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

        btnElimProdVta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Remove.png"))); // NOI18N
        btnElimProdVta.setText("Eliminar");
        btnElimProdVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimProdVtaActionPerformed(evt);
            }
        });

        jLabel10.setText("PRECIO :");

        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        txtProducto.setEditable(false);
        txtProducto.setBackground(new java.awt.Color(255, 153, 102));
        txtProducto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtProducto.setBorder(null);

        jLabel11.setText("PRODUCTO :");

        jLabel12.setText("DESC. $");

        txtDescuento.setText("0");
        txtDescuento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDescuentoMouseClicked(evt);
            }
        });
        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });

        jLabel14.setText("IVA :");

        txtIvaProd.setBackground(new java.awt.Color(255, 153, 102));
        txtIvaProd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.jpg"))); // NOI18N
        btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseEntered(evt);
            }
        });
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIvaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnElimProdVta))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAumentar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDisminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregaProducto)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtCantidadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDisminuir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAumentar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregaProducto))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnElimProdVta)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtStockPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtIvaProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel15.setText("SUBTOTAL:");

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

        cboClientes.setToolTipText("");

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/People.png"))); // NOI18N
        btnClientes.setText("CLIENTES");
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        btnClientes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnClientesFocusLost(evt);
            }
        });

        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnInventario.setText("INVENTARIO");
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnCorte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cortecajaf.jpg"))); // NOI18N
        btnCorte.setText("CORTE");
        btnCorte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCorte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorteActionPerformed(evt);
            }
        });

        cboTipoVenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMAL" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel15))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel18)
                                    .addComponent(txtImporte, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(txtVuelto)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtIva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                        .addComponent(txtSubTotal, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(txtMontoApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVendedorV, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(cboTipoVenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCorte)
                        .addGap(3, 3, 3)
                        .addComponent(btnClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInventario))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(txtNroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTipoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtVendedorV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(cboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnClientes)
                    .addComponent(btnInventario)
                    .addComponent(btnCorte))
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

        btnGenerarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        btnGenerarPedido.setText("PEDIDO");
        btnGenerarPedido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerarPedido.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPedidoActionPerformed(evt);
            }
        });

        txtFecha.setEditable(false);
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtFecha.setBorder(null);

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnSalir1.setText("INICIO");
        btnSalir1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerarPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarV, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir1)
                .addGap(76, 76, 76))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(21, 21, 21)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarV, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProductoKeyReleased(evt);
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscarPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboParametroPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

     private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    
    public ProductoBean buscarProdPorCodSuc(ArrayList<ProductoBean> inventario
            , String codigo, int suc) {
        ProductoBean prod = null;
        for (ProductoBean p: inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
               if (p.getIdSucursal() == suc) {
                   prod = p;
                   return prod;
               }
            }
        }
        return prod;
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
        txtCodigoPro.setText("Espere...");
        int result = JOptionPane.showConfirmDialog(this, "¿Deseas Ejecutar la "
                + "Venta?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
        // VERIFICA si realmente se quierte guardar la venta
        if (result == JOptionPane.YES_OPTION) {
            //VERIFICA SI HAY PRODUCTO A VENDER            
            if (detalleVentaProducto.size()>0) {
                //VERIFICA QUE HAYA PAGO Y CAMBIO EN LA VENTA
                if (!txtImporte.getText().equalsIgnoreCase("") && 
                        !txtVuelto.getText().equalsIgnoreCase("")) {
                    ventasBean = new VentasBean();
                    ventasBean.setIdUsuario(Ingreso.usuario.getIdUsuario());
                    int s = util.buscaIdCliente(Principal.clientesHM
                            , cboClientes.getSelectedItem().toString());
                    ventasBean.setIdCliente(s);
                    //checar despues
                    ventasBean.setObservaciones("");
                    //fin checar despues
                    ventasBean.setIdSucursal(Ingreso.usuario.getIdSucursal());
                    
                    ventasBean.setSubtotal(Double.parseDouble(txtSubTotal.getText()));
                    ventasBean.setIva(Double.parseDouble(txtIva.getText()));
                    ventasBean.setTotal(Double.parseDouble(txtMontoApagar.getText()));
                    ventasBean.setTipovta(cboTipoVenta.getSelectedItem().toString());
                    ventasBean.setCancelada(0);
                    ventasBean.setFacturada(0);
                    ventasBean.setIdFactura(1);

                    //ciclo que garantiza que operacion fue hecha con exito
                    while (ventasBean != null) {
                        //guarda venta
                        hiloVentas = new WSVentas();
                        String rutaWS = constantes.getProperty("IP") 
                                + constantes.getProperty("GUARDAVENTA");
                        VentasBean ventaGuardada = hiloVentas
                                .ejecutaWebService(rutaWS,"2"
                                , "" + ventasBean.getIdCliente()
                                , "" + ventasBean.getObservaciones()
                                , "" + ventasBean.getIdUsuario()
                                , "" + ventasBean.getIdSucursal()
                                , "" + ventasBean.getSubtotal()
                                , "" + ventasBean.getIva()
                                , "" + ventasBean.getTotal()
                                , "VENTA " + ventasBean.getTipovta()
                                , "" + ventasBean.getCancelada()
                                , "" + ventasBean.getFacturada()
                                , "" + ventasBean.getIdFactura()
                                );
                        if (ventaGuardada != null) {
                            //guarda detalle venta
                            for (DetalleVentaBean detVentBeanADisminuir :
                                    detalleVentaProducto) {
                                hiloDetalleVentas = new WSDetalleVentas();
                                rutaWS = constantes.getProperty("IP") 
                                    + constantes.getProperty("GUARDADETALLEVENTA");
//                                boolean detalleGuardado = false;
//                                while (!detalleGuardado) {
                                    // para ajuste inventario                                }
                                    int idArticuloVendido = detVentBeanADisminuir
                                            .getIdArticulo();
                                    double cantidadVendida = detVentBeanADisminuir
                                            .getCantidad();
                                    // fin para ajuste inventario                                }
                                    DetalleVentaBean detalleVentaGuardada = 
                                            hiloDetalleVentas.
                                                    ejecutaWebService(rutaWS
                                                    ,"1"
                                            , "" + Integer.parseInt(txtNroVenta
                                                    .getText().trim())
                                            , "" + detVentBeanADisminuir.getIdArticulo()
                                            , "" + detVentBeanADisminuir.getPrecio()
                                            , "" + detVentBeanADisminuir.getCantidad()
                                            , "" + detVentBeanADisminuir.getDescuento()
                                            , detVentBeanADisminuir.getUnidadMedida()
                                            , "" + Ingreso.usuario.getIdSucursal()
                                                    );
                                    if (detalleVentaGuardada != null) {
                                        //Dismimuye inventario
                                            //obtiene articulo para saber su cantidad 
                                            //original
                                        ArrayList<ProductoBean> resultWS = null;
                                        hiloInventariosList = new WSInventariosList();
                                        rutaWS = constantes.getProperty("IP") 
                                                + constantes
                                            .getProperty("OBTIENEPRODUCTOPORID") 
                                                + String.valueOf(idArticuloVendido);
                                        resultWS = hiloInventariosList
                                                .ejecutaWebService(rutaWS,"5");
                                        ProductoBean p = resultWS.get(0);
                                            //fin obtiene articulo para saber su 
                                            //cantidad original
                                        
                                            //disminuye iinventario en cifras no en bd
                                        double cantidadOriginal = p.getExistencia();
                                        double cantidadFinal = cantidadOriginal 
                                                - cantidadVendida;
                                            //fin disminuye iinventario en cifras no en bd
                                        
                                            //realiza ajuste inventario 
                                        hiloInventarios = new WSInventarios();
                                        rutaWS = constantes.getProperty("IP") 
                                                + constantes
                                          .getProperty("AJUSTAINVENTARIOVENTA");
                                        ProductoBean ajuste = hiloInventarios
                                                .ejecutaWebService(rutaWS,"5"
                                                ,String.valueOf(idArticuloVendido)
                                                ,"" + cantidadFinal);
                                        if (ajuste != null) {
                                                //Guarda movimiento
                                            String fecha = util.dateToDateTimeAsString(util
                                                    .obtieneFechaServidor());
                                            MovimientosBean mov = new MovimientosBean();
                                            hiloMovimientos = new WSMovimientos();
                                            rutaWS = constantes.getProperty("IP") 
                                                    + constantes
                                                     .getProperty("GUARDAMOVIMIENTO");
                                            MovimientosBean movimientoInsertado 
                                                    = hiloMovimientos
                                                   .ejecutaWebService(rutaWS,"1"
                                                ,"" + p.getIdArticulo()
                                                ,"" + Ingreso.usuario.getIdUsuario()
                                                ,"VENTA NORMAL"
                                                ,"" + cantidadVendida
                                                ,fecha
                                                ,"" + Ingreso.usuario.getIdSucursal());
                                                //Fin Guarda movimiento
                                    }
                                }
                            }
                            //fin guarda detalle venta
                            
                            //carga productos actualizados
                            inventario = util.getInventario();
                            //fin carga productos actualizados
                            
                            JOptionPane.showMessageDialog(null, 
                                    "VENTA GUARDADA CORRRECTAMENTE");
//                            detalleVentaProducto.remove(detVentBeanADisminuir);
                            int resultado = JOptionPane.showConfirmDialog(this, 
                                    "¿Deseas "
                                    + "Imprimir la Venta?", "Mensaje..!!"
                                    , JOptionPane.YES_NO_OPTION);
                            if (resultado == JOptionPane.YES_OPTION) {
                                //imprime ticket
                                imprimeVenta("Venta");
                                //fin imprime ticket
                            }
                            borrar();
                            ventasBean = null;
                            //fin guarda detalle venta
                        }                        
                        //fin guarda venta
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "DEBES COMPLETAR LA VENTA "
                            + "PAGANGO EL IMPORTE CORRESPONDIENTE");
                    txtImporte.requestFocus(true);
                }
        } else {
                JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS PARA VENDER");
                return;
            }
        }
        txtCodigoPro.setText("");
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void tblListaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblListaProductosMouseClicked

    private void btnCancelarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarVActionPerformed
        borrar();
    }//GEN-LAST:event_btnCancelarVActionPerformed

    public void cargaClientes() {
        cboClientes.removeAllItems();
        int indiceCliente = 0;
        //CARGA CLIENTES Y ESTABLECE CLIENTE POR DEFECTO
        Iterator it = Principal.clientesHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboClientes.addItem(Principal.clientesHM.get(key));
          if (key.toString().equalsIgnoreCase("1")) {
              cboClientes.setSelectedIndex(indiceCliente);
          }
          indiceCliente++;
        }
    }
    
    private void borrar(){
        cargaClientes();
        txtBuscarPro.setText("");
        actualizarBusquedaProducto();
        txtNroVenta.setText("" + obtenerUltimoId());
        detalleVentaProducto.clear();
//        pedidoBean = null;
//        detallePedidoBean = null;
//        detallePedidoProducto.clear();
        prodParcial = null;    
        recargarTableVentaParcialProductos(detalleVentaProducto);
        limpiarCajaTexto();
        actualizaTotales(detalleVentaProducto);
    }
    
    private void txtCantidadProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProKeyTyped
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadProKeyTyped

    private void txtCodigoProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyReleased

    }//GEN-LAST:event_txtCodigoProKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (!cargaDatos) {
            Principal p = new Principal();
            p.cargaClientes();
            cargaClientes();
            inventario = util.getInventario();
            recargarTableProductos(inventario);
            cargaDatos = true;
            txtCodigoPro.setText("");
        }
    }//GEN-LAST:event_formWindowActivated

    private void txtBuscarProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProKeyReleased
        actualizarBusquedaProducto();
    }//GEN-LAST:event_txtBuscarProKeyReleased

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        ProductoBean prod = buscarProdPorCodSuc(inventario
                , txtCodigoPro.getText().trim()
                , Ingreso.usuario.getIdSucursal());
        if (prod == null) {
            JOptionPane.showMessageDialog(null, "No se encontro el producto, "
                    + "debes regidtrarlo primero");
            txtCodigoPro.setText("");
            txtCodigoPro.requestFocus(true);
            return;
        }
        prodParcial = prod;
        txtCodigoPro.setText(prodParcial.getCodigo());
        txtProducto.setText(prodParcial.getDescripcion());
        txtStockPro.setText("" + prodParcial.getExistencia());
        txtPrecio.setText("" + prodParcial.getPrecioUnitario());
        txtCantidadPro.requestFocus(true);
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void jtProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProductoMouseClicked
        buscaDetalleProducto();
    }//GEN-LAST:event_jtProductoMouseClicked

    private void buscaDetalleProducto() {
        ProductoBean prod = buscarProdPorCodSuc(inventario
                , String.valueOf(jtProducto.getModel().getValueAt(
                            jtProducto.getSelectedRow(),1))
                , Ingreso.usuario.getIdSucursal());
        if (prod != null) {
            prodParcial = prod;
            txtCodigoPro.setText(prodParcial.getCodigo());
            txtProducto.setText(prodParcial.getDescripcion());
            txtStockPro.setText("" + prodParcial.getExistencia());
            txtPrecio.setText("" + prodParcial.getPrecioUnitario());
            txtCantidadPro.setText("1");
            txtDescuento.setText("0");
            txtCantidadPro.requestFocus(true);
        }
    }
    
    private boolean buscarEnCarrito(int idArticulo) {
        boolean existe = false;
        try {
            for (DetalleVentaBean DetalleVenta : detalleVentaProducto) {
                if (DetalleVenta.getIdArticulo() == idArticulo) {
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
    
    private boolean eliminaProdDeCarrito(int idArticulo) {
        boolean existe = false;
        for (DetalleVentaBean DetalleVenta : detalleVentaProducto) {
            if (DetalleVenta.getIdArticulo() == idArticulo) {
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
//                //verifica que el minimo del cuadro de texto sea el minimo del objeto a vender
//                if (Double.parseDouble(txtStockPro.getText()) == prodParcial.getExistenciaMinima()) {
                    //verifica que haya existencia es decir que no rebase al minimo
                    if (Double.parseDouble(txtCantidadPro.getText()) <= prodParcial.getExistencia()) {
                        //Verifica que el producto no este ya agregado en la lista de compras
                        if (buscarEnCarrito(prodParcial.getIdArticulo())) {
                            JOptionPane.showMessageDialog(null, "YA ESTA AGREGADO EL PRODUCTO");
                            //limpia caja texto relacionados con venta
                            txtCodigoPro.setText("");
                            txtPrecio.setText("");
                            txtDescuento.setText("0");
                            txtCantidadPro.setText("1");
                            txtStockPro.setText("");        
                            txtProducto.setText("");
                            txtIvaProd.setText("" + ivaEmpresa);
                            txtCodigoPro.requestFocus(true);
                            //fin limpia caja texto relacionados con venta
                            return;
                        }
                        detalleVenta = new DetalleVentaBean();
                        detalleVenta.setCantidad(Double.parseDouble(txtCantidadPro
                                .getText()));
                        detalleVenta.setDescuento(Double.parseDouble(txtDescuento
                                .getText()));  
                        detalleVenta.setIdArticulo(prodParcial.getIdArticulo());
                        detalleVenta.setIdSucursal(Ingreso.usuario.getIdSucursal());
                        detalleVenta.setIdVenta(Integer.parseInt(txtNroVenta
                                .getText()));
                        
                        if ((txtDescuento.getText().equalsIgnoreCase("0")) ||
                                txtDescuento.getText().equalsIgnoreCase("")) {
                            detalleVenta.setPrecio(Double.parseDouble(txtPrecio
                                    .getText()));  
                        } else {
                            detalleVenta.setPrecio(Double.parseDouble(txtPrecio
                                    .getText()) - Double.parseDouble(txtDescuento
                                            .getText()));  
                        }
                        detalleVenta.setUnidadMedida(prodParcial.getUnidadMedida());
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
                //limpia caja texto relacionados con venta
                txtCodigoPro.setText("");
                txtPrecio.setText("");
                txtDescuento.setText("0");
                txtCantidadPro.setText("1");
                txtStockPro.setText("");        
                txtProducto.setText("");
                txtIvaProd.setText("" + ivaEmpresa);
                //fin limpia caja texto relacionados con venta
                
                prodParcial = null;
            } else {
                JOptionPane.showMessageDialog(null, "No es el mismo producto que"
                        + " consultaste vuelve ha hacer la operación");
                prodParcial = null;
                limpiarCajaTexto();
                return;
            }            
            // Fin Verifico que sea el mismo producto que se consulto contra el 
            //  cuadro de texto codigo
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto a vender");
            limpiarCajaTexto();
            return;
        }
        //Fin Verifica que haya producto seleccionado
    }//GEN-LAST:event_btnAgregaProductoActionPerformed

    private void btnElimProdVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimProdVtaActionPerformed
        int fila = tblListaProductos.getSelectedRow();        
        if (fila >= 0) {
            int idArt = Integer.parseInt(String.valueOf(tblListaProductos
                    .getValueAt(tblListaProductos.getSelectedRow(),0)));
            if (eliminaProdDeCarrito(idArt)) {
                try {
                    ((DefaultTableModel)tblListaProductos
                            .getModel()).removeRow(fila);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                actualizaTotales(detalleVentaProducto);
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                    "DEBES SELECCIONAR UN PRODUCTO");
            return;
        }
    }//GEN-LAST:event_btnElimProdVtaActionPerformed

    private void txtImporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImporteActionPerformed
        if (txtImporte.getText().equalsIgnoreCase("")) {
            txtCodigoPro.requestFocus(true);
            return;
        }
        if (Double.parseDouble(txtImporte.getText()) >= 
                Double.parseDouble(txtMontoApagar.getText())) {
            txtVuelto.setText(""+((Double.parseDouble(txtImporte.getText())-
                    Double.parseDouble(txtMontoApagar.getText()))));   
            DecimalFormat df = new DecimalFormat("#.##");   
            txtVuelto.setText(""+df.format(Double.parseDouble(txtVuelto.getText())));  
            btnGenerarVenta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "EL PAGO DEBE SER MAYOR O "
                    + "IGUAL AL TOTAL A PAGAR ");
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

    private void jtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProductoKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP) {
             buscaDetalleProducto();
        }
    }//GEN-LAST:event_jtProductoKeyReleased

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        cargaDatos = false;
        FrmCliente frmCliente = new FrmCliente(1);
        frmCliente.setVisible(true);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void txtDescuentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescuentoMouseClicked
        txtDescuento.setText("");
    }//GEN-LAST:event_txtDescuentoMouseClicked

    private void btnGenerarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPedidoActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "¿Deseas guardar el "
                + "Pedido?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
        // VERIFICA si realmente se quierte guardar la venta
        if (result == JOptionPane.YES_OPTION) {
            //VERIFICA SI HAY PRODUCTO A PEDIR
            if (detalleVentaProducto.size()>0) {
                //obtiene no. de pedido
                txtCodigoPro.setText("Espere...");
                int noPedido = obtenerUltimoIdPedido();
                ventasBean = new VentasBean();
                ventasBean.setIdUsuario(Ingreso.usuario.getIdUsuario());
                int s = util.buscaIdCliente(Principal.clientesHM
                        , cboClientes.getSelectedItem().toString());
                ventasBean.setIdCliente(s);
                //checar despues
                ventasBean.setObservaciones("");
                //fin checar despues
                ventasBean.setIdSucursal(Ingreso.usuario.getIdSucursal());

                ventasBean.setSubtotal(Double.parseDouble(txtSubTotal.getText()));
                ventasBean.setIva(Double.parseDouble(txtIva.getText()));
                ventasBean.setTotal(Double.parseDouble(txtMontoApagar.getText()));
                ventasBean.setTipovta(cboTipoVenta.getSelectedItem().toString());
                ventasBean.setCancelada(0);
                ventasBean.setFacturada(0);
                ventasBean.setIdFactura(1);
                    
                //ciclo que garantiza que operacion fue hecha con exito
                while (ventasBean != null) {
                    //guarda pedido
                    hiloPedidos = new WSPedidos();
                    String rutaWS = constantes.getProperty("IP") 
                            + constantes.getProperty("GUARDAPEDIDO");
                    PedidoBean pedidoGuardado = hiloPedidos
                            .ejecutaWebService(rutaWS,"2"
                            , "" + ventasBean.getIdCliente()
                            , "" + ventasBean.getObservaciones()
                            , "" + ventasBean.getIdUsuario()
                            , "" + ventasBean.getIdSucursal()
                            , "" + "0"
                            , "" + ventasBean.getSubtotal()
                            , "" + ventasBean.getIva()
                            , "" + ventasBean.getTotal()
                            , "VENTA " + ventasBean.getTipovta()
                            , "" + ventasBean.getCancelada()
                            , "" + ventasBean.getFacturada()
                            , "" + ventasBean.getIdFactura()
                            );
                    if (pedidoGuardado != null) {
                        //guarda detalle venta
                        for (DetalleVentaBean detVentBeanADisminuir :
                                detalleVentaProducto) {
                            hiloDetallePedidos = new WSDetallePedidos();
                            rutaWS = constantes.getProperty("IP") 
                                + constantes.getProperty("GUARDADETALLEPEDIDO");
                            DetallePedidoBean detallePedidoGuardado = null;
                            while (detallePedidoGuardado == null) {
                                detallePedidoGuardado = 
                                        hiloDetallePedidos.ejecutaWebService(rutaWS,"1"
                                        , "" + noPedido
                                        , "" + detVentBeanADisminuir.getIdArticulo()
                                        , "" + detVentBeanADisminuir.getPrecio()
                                        , "" + detVentBeanADisminuir.getCantidad()
                                        , "" + detVentBeanADisminuir.getDescuento()
                                        , detVentBeanADisminuir.getUnidadMedida()
                                        , "" + Ingreso.usuario.getIdSucursal()
                                        );
                            }
                        }
                        //fin guarda detalle pedido
                        JOptionPane.showMessageDialog(null, 
                                "PEDIDO GUARDADO CORRRECTAMENTE");
//                            detalleVentaProducto.remove(detVentBeanADisminuir);
                        int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas "
                                + "Imprimir el Pedido?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
                        if (resultado == JOptionPane.YES_OPTION) {
                            //imprime ticket
                            imprimeVenta("Pedido");
                            //fin imprime ticket
                        }
                        borrar();
                        ventasBean = null;
                    } //fin guarda pedido
                }                        
                //fin guarda venta
            } else {
                JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS PARA VENDER");
                return;
            }
        } //si se acepto el pedido
    }//GEN-LAST:event_btnGenerarPedidoActionPerformed

    private void txtCodigoProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoProMouseClicked
        limpiaCaptura();
    }//GEN-LAST:event_txtCodigoProMouseClicked

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        cargaDatos = false;
        FrmProducto frmProducto = new FrmProducto(1);
        frmProducto.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiaCaptura();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseEntered
        btnLimpiar.setToolTipText("Limpiar");
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnCorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorteActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmCorte frmCorte = new FrmCorte();
        frmCorte.setVisible(true);                    
        frmCorte.setExtendedState(Principal.MAXIMIZED_BOTH);
    }//GEN-LAST:event_btnCorteActionPerformed

    private void btnClientesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnClientesFocusLost
    }//GEN-LAST:event_btnClientesFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        JOptionPane.showMessageDialog(null, "d");
    }//GEN-LAST:event_formFocusGained

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        txtCantidadPro.requestFocus(true);
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        //verifica si hubo cambio de precio
        if (Double.parseDouble(txtPrecio.getText()) != 
                prodParcial.getPrecioUnitario()) {
            int dialogResult = JOptionPane.showConfirmDialog(null, 
                    "¿Realmente deseas cambiar el precio?");
            if(dialogResult == JOptionPane.YES_OPTION){
                txtDescuento.requestFocus(true);
            } else {
                txtPrecio.setText("" + prodParcial.getPrecioUnitario());
            }
        }
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        //valida solo numeros
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        //valida solo numeros
        if (String.valueOf(evt.getKeyChar()).matches("[a-zA-Z]|\\s")) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtDescuentoKeyTyped

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
                    sucursalSistema);
                producto.setIdSucursal(idSuc);
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
                    equalsIgnoreCase("Nombre")) {
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

    //Para Tabla Productos
    public void recargarTableProductos(ArrayList<ProductoBean> list) {
        Object[][] datos = new Object[list.size()][3];
        int i = 0;
        for (ProductoBean p : list) {
            //filtra por sucursal
            if ((Ingreso.usuario.getIdSucursal() == p.getIdSucursal()) ||
                    (Ingreso.usuario.getUsuario().equalsIgnoreCase(constantes
                            .getProperty("SUPERUSUARIO")))) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = p.getCodigo();
                datos[i][2] = p.getDescripcion();
                i++;
            }
        }
        Object[][] datosFinal = new Object[i][3];
        //Para filtrar los registros
        for (int j=0; j<i; j++) {
            if (datos[j][0]!=null) {
                datosFinal[j][0] = datos[j][0];
                datosFinal[j][1] = datos[j][1];
                datosFinal[j][2] = datos[j][2];
            }
        }
        //Fin Para filtrar los registros
        
        jtProducto.setModel(new javax.swing.table.DefaultTableModel(
                datosFinal,
                new String[]{
                    "ID", "CODIGO", "DESCRIPCIÓN"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jtProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtProducto.getColumnModel().getColumn(0).setMaxWidth(0);
    } 
    
    //Para Tabla Venta Parcial
    public void recargarTableVentaParcialProductos(List<DetalleVentaBean> list) {
//        try {
            if (list == null) {
                DefaultTableModel modelo = (DefaultTableModel) tblListaProductos
                        .getModel();
                while(modelo.getRowCount()>0)
                    modelo.removeRow(0);
            }
            Object[][] datos = new Object[list.size()][5];
            int i = 0;
            for (DetalleVentaBean p : list) {
                datos[i][0] = p.getIdArticulo();
                datos[i][1] = util.buscaDescFromIdProd(Principal.productosHMID,"" 
                        + p.getIdArticulo());
                datos[i][2] = p.getCantidad();
                datos[i][3] = String.format("%.2f", p.getPrecio());
                DecimalFormat df = new DecimalFormat("#.##");   
                String importeParcial = df.format(p.getCantidad() * p.getPrecio());  
                datos[i][4] = importeParcial;
                i++;
            }
            tblListaProductos.setModel(new javax.swing.table.DefaultTableModel(
                    datos,
                    new String[]{
                        "id","DESCRIPCION", "CANTIDAD", "PRECIO UND.", "IMPORTE"
                   }) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            tblListaProductos.getColumnModel().getColumn(0).setPreferredWidth(1);
            tblListaProductos.getColumnModel().getColumn(0).setMaxWidth(1);
//        } catch (Exception e) {
//        }
    } 
    
    //Actualiza totales,subtotales, etc de venta
    public void actualizaTotales(List<DetalleVentaBean> list) {
        double subtotal = 0;
        double iva = 0;
        double total;
        int i = 0;
        try {
            for (DetalleVentaBean p : list) {
                subtotal = subtotal + (p.getPrecio() * p.getCantidad());
            }            
        } catch (java.lang.NullPointerException e) {
            subtotal = 0;
        }
        
        txtSubTotal.setText(""+subtotal);        
        //iva = subtotal * configuracionBean.getIva()/100;
        iva = iva + ((ivaGral/100) * subtotal);
        txtIva.setText(""+iva);
        total = iva + subtotal;
        txtMontoApagar.setText(""+total);    
        
        //muestra 2 decimales en porc de descuento
        DecimalFormat df = new DecimalFormat("#.##");   
        txtSubTotal.setText(""+df.format(Double.parseDouble(txtSubTotal
                .getText())));  
        txtIva.setText(""+df.format(Double.parseDouble(txtIva.getText())));  
        txtMontoApagar.setText(""+df.format(Double.parseDouble(txtMontoApagar
                .getText())));  
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
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCorte;
    private javax.swing.JButton btnDisminuir;
    private javax.swing.JButton btnElimProdVta;
    private javax.swing.JButton btnGenerarPedido;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JComboBox cboClientes;
    private javax.swing.JComboBox cboParametroPro;
    private javax.swing.JComboBox cboTipoVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtIvaProd;
    private javax.swing.JTextField txtMontoApagar;
    private javax.swing.JTextField txtNroVenta;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProducto;
    public javax.swing.JTextField txtStockPro;
    private javax.swing.JTextField txtSubTotal;
    public javax.swing.JTextField txtVendedorV;
    private javax.swing.JTextField txtVuelto;
    // End of variables declaration//GEN-END:variables
}