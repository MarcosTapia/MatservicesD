package vistas;

import Ticket.Ticket;
import beans.DetallePedidoBean;
import beans.DetalleVentaBean;
import beans.MovimientosBean;
import beans.PedidoBean;
import beans.ProductoBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSDetallePedidos;
import consumewebservices.WSDetallePedidosList;
import consumewebservices.WSDetalleVentas;
import consumewebservices.WSInventarios;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSPedidos;
import consumewebservices.WSPedidosList;
import consumewebservices.WSVentas;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.Util;
import static vistas.Principal.productos;

public class FrmConsultaPedidos extends javax.swing.JFrame {
    //WS
    Util util = new Util();
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSPedidos hiloPedidos;
    WSPedidosList hiloPedidosList;
    WSDetallePedidosList hiloDetallePedidosList;
    WSDetallePedidos hiloDetallePedidos;
    WSVentas hiloVentas;
    WSDetalleVentas hiloDetalleVentas;
    WSInventarios hiloInventarios;
    WSInventariosList hiloInventariosList;
    WSMovimientos hiloMovimientos;
    //Fin WS
    DateFormat fecha = DateFormat.getDateInstance();
    String accion = "";
    ArrayList<PedidoBean> PedidosGlobal = null;
    ArrayList<DetallePedidoBean> detallePedidosGlobal = null;
    ArrayList<ProductoBean> inventario = null;

    public FrmConsultaPedidos() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        cargaDatos();
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno());
        this.setTitle(Principal.datosEmpresaBean.getNombreEmpresa());
        this.setIcon();
        limpiaTblDetallePedido();        
    }
    
    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }
    
    public int obtenerUltimoIdVenta() {
        int id = 0;
        VentasBean resultWS = null;
        hiloVentas = new WSVentas();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETULTIMOIDVENTAS");
        resultWS = hiloVentas.ejecutaWebService(rutaWS,"1");
        id = resultWS.getIdVenta() + 1;
        return id;
    }
    
    
    private void cargaDatos() {
        hiloPedidosList = new WSPedidosList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETPEDIDOS");
        PedidosGlobal = hiloPedidosList.ejecutaWebService(rutaWS,"1");
        recargarTablePedidos(PedidosGlobal);

//        inventario = util.getInventario();
//        productos = util.getInventario();
//        util.llenaMapProductos(productos);
        
        // Actualizas tbl DetalleVentas
        hiloDetallePedidosList = new WSDetallePedidosList();
        rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETDETALLEPEDIDOS");
        detallePedidosGlobal = hiloDetallePedidosList
                .ejecutaWebService(rutaWS,"1");
        recargarTableDetallePedidos(detallePedidosGlobal);
    }

    private void obtieneTotales() {
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
        double precio = 0;
        double cantidad = 0;
        double descuento = 0;
        double subTotal = 0;
        double iva = 0;
        double total = 0;
        //iva empresa iva de carga a los articulos, ivagral iva para ventasBean
        double ivaEmpresa = Principal.datosSistemaBean.getIvaGral();
        for (int i=0;i<tblConsultaDetallePedido.getRowCount();i++) {
            precio = Double.parseDouble(tblConsultaDetallePedido
                    .getValueAt(i, 3).toString());
            cantidad = Double.parseDouble(tblConsultaDetallePedido
                    .getValueAt(i, 4).toString());
            descuento = Double.parseDouble(tblConsultaDetallePedido
                    .getValueAt(i, 5).toString());
            // precio con descuento
            precio = precio - (precio * (descuento / 100));
            subTotal = subTotal + (precio * cantidad);
        }
        iva = subTotal * (ivaEmpresa/100);
        total = iva + subTotal;
        
        // se asignan valores a campos de texto
        DecimalFormat df = new DecimalFormat("#.##");   
        txtSubtotal.setText("" + df.format(subTotal));
        txtIva.setText("" + df.format(iva));
        txtTotal.setText("" + df.format(total));
    }
    
    public void imprimeVenta(String tipoTicket,int idVenta,VentasBean ventasBean
        , List<DetalleVentaBean> detalleVentaProducto){
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
            java.util.Date fecha = util.obtieneFechaServidor();
            String a = DateFormat.getDateInstance(DateFormat.LONG).format(fecha);        
            String fechaImpresion = a;
            if (tipoTicket.equalsIgnoreCase("Venta")) {
                ticket.AddSubCabecera("Venta No: " + 
                        idVenta + "   " +
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
            ticket.AddTotal("SUBTOTAL                ","" 
                    + ventasBean.getSubtotal());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("IVA                     ","" 
                    + ventasBean.getIva());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("TOTAL                   ","" 
                    + ventasBean.getTotal());
            ticket.AddTotal("",ticket.DarEspacio());
            ticket.AddTotal("",ticket.DarEspacio());
            
            //para cantidad con letra
            ticket.AddPieLinea("DEVOLUCIÓN SOLO CON TICKET");
            ticket.AddPieLinea(ticket.DarEspacio());     
            ticket.AddPieLinea("                 GRACIAS!");
//            ticket.ImprimirDocumento("LPT1",true);
            ticket.ImprimirDocumento("usb002",true);
        } catch(Exception e){
            ventasBean = null;
            borrar();
            cargaDatos();
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
        }     
    }    
    
    public void procesarPedido() {
        int idPedido = 0;
        List<DetalleVentaBean> detalleVentaProducto = new ArrayList<>();
        DetalleVentaBean detalleVentaObj = null;
        ArrayList<DetallePedidoBean> detallePedido = null;
        //List<DetallePedidoBean> detallePedido = new ArrayList<>();
        PedidoBean pedidoBean = null;
        int result = JOptionPane.showConfirmDialog(this, "¿Deseas procesar el "
                + "Pedido?", "Mensaje..!!", JOptionPane.YES_NO_OPTION);
        // VERIFICA si realmente se quierte guardar la ventasBean
        if (result == JOptionPane.YES_OPTION) {
            pedidoBean = new PedidoBean();
            //busca pedido
                //obtiene pedido para guardarlo como ventasBean
            ArrayList<PedidoBean> resultWS = null;
            hiloPedidosList = new WSPedidosList();
            idPedido = Integer.parseInt(
                    tblConsultaPedidos
                            .getValueAt(tblConsultaPedidos.getSelectedRow(), 0)
                            .toString()
                    );
            String rutaWS = constantes.getProperty("IP") 
                    + constantes.getProperty("OBTIENEPEDIDOPORID") 
                    + String.valueOf(idPedido);
            resultWS = hiloPedidosList.ejecutaWebService(rutaWS,"3");
            PedidoBean pedido = resultWS.get(0);
                //fin obtiene pedido para guardarlo como ventasBean

                //convierto pedido a ventasBean para guardarlo como ventasBean
            VentasBean ventasBean = new VentasBean();
            ventasBean.setFecha(pedido.getFecha());
            ventasBean.setIdCliente(pedido.getIdCliente());
            ventasBean.setIdSucursal(pedido.getIdSucursal());
            ventasBean.setIdUsuario(pedido.getIdUsuario());
            int idVenta = obtenerUltimoIdVenta();
            ventasBean.setIdVenta(idVenta);
            ventasBean.setObservaciones(pedido.getObservaciones());
            
            ventasBean.setSubtotal(pedido.getSubtotal());
            ventasBean.setIva(pedido.getIva());
            ventasBean.setTotal(pedido.getTotal());
            ventasBean.setTipovta(pedido.getTipovta());
            ventasBean.setCancelada(pedido.getCancelada());
            ventasBean.setFacturada(pedido.getFacturada());
            ventasBean.setIdFactura(pedido.getIdFactura());
            
                //fin convierto pedido a ventasBean para guardarlo como ventasBean
            
                //convierto detallepedido a detalleventa para guardarlo como detalleventa
                    //llena tabla para hacer la busqueda de detalle sin usar ws
            recargarTableDetallePedidos(detallePedidosGlobal);
                    //fin llena tabla para hacer la busqueda de detalle sin usar ws
            detallePedido = llenaTablaDetallePedidos(String.valueOf(idPedido).trim(),0);
            for (DetallePedidoBean detallePedidoTemp :
                    detallePedido) {
                detalleVentaObj = new DetalleVentaBean();
                detalleVentaObj.setCantidad(detallePedidoTemp.getCantidad());
                detalleVentaObj.setDescuento(detallePedidoTemp.getDescuento());
                detalleVentaObj.setIdArticulo(detallePedidoTemp.getIdArticulo());
                detalleVentaObj.setIdSucursal(detallePedidoTemp.getIdSucursal());
                detalleVentaObj.setIdVenta(idVenta);
                detalleVentaObj.setPrecio(detallePedidoTemp.getPrecio());
                detalleVentaObj.setUnidadMedida(detallePedidoTemp.getUnidadMedida());
                detalleVentaProducto.add(detalleVentaObj);
            }
                    //regresa tabla originalmente
            recargarTableDetallePedidos(detallePedido);
                    //fin regresa tabla originalmente
                //fin convierto detallepedido a detalleventa para guardarlo como detalleventa
            //ciclo que garantiza que operacion fue hecha con exito
            while (ventasBean != null) {
                //guarda ventasBean
                hiloVentas = new WSVentas();
                rutaWS = constantes.getProperty("IP") 
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
                        , "" + ventasBean.getTipovta()
                        , "" + ventasBean.getCancelada()
                        , "" + ventasBean.getFacturada()
                        , "" + ventasBean.getIdFactura()
                        );
                if (ventaGuardada != null) {
                    //guarda detalle ventasBean
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
                                    , "" + idVenta
                                    , "" + detVentBeanADisminuir.getIdArticulo()
                                    , "" + detVentBeanADisminuir.getPrecio()
                                    , "" + detVentBeanADisminuir.getCantidad()
                                    , "" + detVentBeanADisminuir.getDescuento()
                                    , detVentBeanADisminuir.getUnidadMedida()
                                    , "" + Ingreso.usuario.getIdSucursal());
                            if (detalleVentaGuardada != null) {
                                //Dismimuye inventario
                                    //obtiene articulo para saber su cantidad original
                                ArrayList<ProductoBean> resultWSP = null;
                                hiloInventariosList = new WSInventariosList();
                                rutaWS = constantes.getProperty("IP") 
                                        + constantes
                                            .getProperty("OBTIENEPRODUCTOPORID") 
                                        + String.valueOf(idArticuloVendido);
                                resultWSP = hiloInventariosList
                                        .ejecutaWebService(rutaWS,"5");
                                ProductoBean p = resultWSP.get(0);
                                    //fin obtiene articulo para saber su cantidad original

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
                                    String fecha = util
                                         .dateToDateTimeAsString(util
                                                 .obtieneFechaServidor());
                                    MovimientosBean mov = new MovimientosBean();
                                    hiloMovimientos = new WSMovimientos();
                                    rutaWS = constantes.getProperty("IP") 
                                            + constantes.getProperty(
                                                    "GUARDAMOVIMIENTO");
                                    MovimientosBean movimientoInsertado = 
                                            hiloMovimientos.ejecutaWebService(rutaWS
                                                    ,"1"
                                        ,"" + p.getIdArticulo()
                                        ,"" + Ingreso.usuario.getIdUsuario()
                                        ,"Venta desde Pedido"
                                        ,"" + cantidadVendida
                                        ,fecha
                                        ,"" + Ingreso.usuario.getIdSucursal());
                                        //Fin Guarda movimiento
                                 } // fin realiza pregunta si se ajusto inentario
                            } // pregunta si se guardo un una fila del detalle venta
                    }
                    //fin guarda detalle ventasBean

                    //carga productos actualizados
//                    productos = util.getInventario();
//                    util.llenaMapProductos(productos);
                    //fin carga productos actualizados

                    JOptionPane.showMessageDialog(null, 
                            "VENTA GUARDADA CORRRECTAMENTE");
                    
                    // Borra pedido
                    borraPedido(idPedido);
                    int resultado = JOptionPane.showConfirmDialog(this, "¿Deseas"
                            + " "
                            + "Imprimir la Venta?", "Mensaje..!!", JOptionPane
                                    .YES_NO_OPTION);
                    if (resultado == JOptionPane.YES_OPTION) {
                        //imprime ticket
                        imprimeVenta("Venta",idVenta,ventasBean
                                ,detalleVentaProducto);
                        //fin imprime ticket
                    }
                    ventasBean = null;
                    borrar();
                    cargaDatos();
                } // condicion que verifica que se guardo la venta
            }                        
            //fin ciclo guarda ventasBean
        }
    }    
    
    /*
    Metodo para borrar pedido
    **/
    public void borraPedido(int idPedido){
        hiloDetallePedidos = new WSDetallePedidos();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("ELIMINADETALLEPEDIDO");
        DetallePedidoBean detallePedidoEliminar = 
                hiloDetallePedidos.ejecutaWebService(rutaWS
                ,"2"
                , "" + idPedido);
        if (detallePedidoEliminar != null) {
            hiloPedidos = new WSPedidos();
            rutaWS = constantes.getProperty("IP") + constantes
                    .getProperty("ELIMINAPEDIDO");
            PedidoBean pedidoEliminar = hiloPedidos.ejecutaWebService(rutaWS
                    ,"3"
                    , "" + idPedido);
            if (pedidoEliminar != null) {
                JOptionPane.showMessageDialog(null, " [ Pedido Eliminado ]");
                borrar();
                cargaDatos();
            }
        }
    }
    
    //Para Tabla Pedidos
    public void recargarTablePedidos(ArrayList<PedidoBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (PedidoBean p : list) {
            datos[i][0] = p.getIdPedido();
            datos[i][1] = dateFormat.format(p.getFecha());
//            datos[i][1] = p.getFecha();
            datos[i][2] = util.buscaDescFromIdCli(Principal.clientesHM
                    , "" + p.getIdCliente());
            datos[i][3] = util.buscaDescFromIdSuc(Principal.sucursalesHM 
                    , "" + p.getIdSucursal());
            datos[i][4] = util.buscaDescFromIdUsu(Principal.usuariosHM 
                    , "" + p.getIdUsuario());
            datos[i][5] = p.getSubtotal();
            datos[i][6] = p.getIva();
            datos[i][7] = p.getTotal();
            i++;
        }
        tblConsultaPedidos.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{
                    "No. PEDIDO", "FECHA PEDIDO","CLIENTE","SUCURSAL","USUARIO"
                        ,"SUBTOTAL","IVA","TOTAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    } 

    //Para Tabla DetalleVenta
    public void recargarTableDetallePedidos(ArrayList<DetallePedidoBean> list) {
        Object[][] datos = new Object[list.size()][8];
        int i = 0;
        for (DetallePedidoBean p : list) {
            datos[i][0] = p.getIdDetallePedido();
            datos[i][1] = p.getIdPedido();
            datos[i][2] = util.buscaDescFromIdProd(Principal.productosHMID, 
                    "" + p.getIdArticulo());
            datos[i][3] = p.getPrecio();
            datos[i][4] = p.getCantidad();
            datos[i][5] = p.getDescuento();
            datos[i][6] = p.getUnidadMedida();
            datos[i][7] = util.buscaDescFromIdSuc(Principal.sucursalesHM
                    , "" + p.getIdSucursal());
            i++;
        }
        //Fin Para filtrar los registros
        tblConsultaDetallePedido.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                new String[]{ 
                    "ID","No. PED.", "PRODUCTO","PRECIO VTA","CANT."
                        ,"DESC APLICADO"
                        ,"UNIDAD","SUCURSAL"
                }) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblConsultaDetallePedido.getColumnModel().getColumn(0).setPreferredWidth(0);
        //para fijar la columna
        tblConsultaDetallePedido.getColumnModel().getColumn(0).setMaxWidth(0);

        tblConsultaDetallePedido.getColumnModel().getColumn(1).setPreferredWidth(52);
        tblConsultaDetallePedido.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblConsultaDetallePedido.getColumnModel().getColumn(4).setPreferredWidth(40);
        obtieneTotales();
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsultaDetallePedido = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        btnProcesarPedido = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnConsultas = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConsultaPedidos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBuscarPedido = new javax.swing.JTextField();
        cboParametroVentas = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCalFechaIni = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jCalFechaFin = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(70, 99, 138));

        jPanel2.setBackground(new java.awt.Color(247, 254, 255));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("CONSULTA DE PEDIDOS");

        tblConsultaDetallePedido.setModel(new javax.swing.table.DefaultTableModel(
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
        ));
        tblConsultaDetallePedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaDetallePedidoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblConsultaDetallePedido);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnProcesarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addVenta.png"))); // NOI18N
        btnProcesarPedido.setText("PROCESAR PEDIDO");
        btnProcesarPedido.setBorder(new javax.swing.border.MatteBorder(null));
        btnProcesarPedido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProcesarPedido.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProcesarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarPedidoActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("SubTotal :");

        txtSubtotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("IVA :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Total :");

        txtIva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cancel.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        btnConsultas.setText("CONSULTAS");
        btnConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultasActionPerformed(evt);
            }
        });

        btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inicio.png"))); // NOI18N
        btnInicio.setText("INICIO");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblUsuario)))
                .addGap(18, 18, 18)
                .addComponent(btnProcesarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addComponent(txtIva)
                    .addComponent(txtTotal))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblUsuario)
                                .addGap(16, 16, 16)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnConsultas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProcesarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tblConsultaPedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        ));
        tblConsultaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaPedidosMouseClicked(evt);
            }
        });
        tblConsultaPedidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblConsultaPedidosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblConsultaPedidos);

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel4.setText("PEDIDO");

        jLabel5.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel5.setText("DETALLE DEL PEDIDO");

        txtBuscarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarPedidoActionPerformed(evt);
            }
        });
        txtBuscarPedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPedidoKeyReleased(evt);
            }
        });

        cboParametroVentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No. Pedido", "Cliente", "Sucursal", "Usuario" }));
        cboParametroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParametroVentasActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Buscar :");

        jLabel2.setText("Fecha Inicio :");

        jCalFechaIni.setDateFormatString("yyyy-MM-d");

        jLabel3.setText("Fecha Fin :");

        jCalFechaFin.setDateFormatString("yyyy-MM-d");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/List.png"))); // NOI18N
        jButton2.setText("CONSULTAR");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Erase.png"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBuscarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3))))
                        .addGap(13, 13, 13)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtBuscarPedido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cboParametroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jCalFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jCalFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 6, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboParametroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParametroVentasActionPerformed

    }//GEN-LAST:event_cboParametroVentasActionPerformed

    private void txtBuscarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarPedidoActionPerformed
    }//GEN-LAST:event_txtBuscarPedidoActionPerformed

    private void txtBuscarPedidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPedidoKeyReleased
        actualizarBusquedaPedido();
    }//GEN-LAST:event_txtBuscarPedidoKeyReleased

    private void tblConsultaDetallePedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaDetallePedidoMouseClicked
    }//GEN-LAST:event_tblConsultaDetallePedidoMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblConsultaPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaPedidosMouseClicked
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaPedidosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiaTblDetallePedido();        
        String fechaIni = "";
        String fechaFin = "";
        //Tomamos las dos fechas y las convierto a java.sql.date
        java.util.Date fechaUtilDateIni = jCalFechaIni.getDate();
        java.util.Date fechaUtilDateFin = jCalFechaFin.getDate();
        java.sql.Date fechaSqlDateIni;
        java.sql.Date fechaSqlDateFin;
        try {
            fechaSqlDateIni = new java.sql.Date(fechaUtilDateIni.getTime());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar por lo menos "
                    + "la fecha de Inicio");
            return;
        }
        try {
            fechaSqlDateFin = new java.sql.Date(fechaUtilDateFin.getTime());
        } catch (Exception e) {
            fechaSqlDateFin = fechaSqlDateIni;
        }
        fechaIni = fechaSqlDateIni.toString();
        fechaFin = fechaSqlDateFin.toString();
        if (fechaSqlDateIni.getTime() > fechaSqlDateFin.getTime()) {
            JOptionPane.showMessageDialog(null, "Fechas Incorrectas");
            return;
        }
        // Actualizas tbl Ventas
        ArrayList<PedidoBean> pedidosPorFechas = null;
        hiloPedidosList = new WSPedidosList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETPEDIDOSPORFECHASFINI") + fechaIni +
                constantes.getProperty("GETPEDIDOSPORFECHASFFIN") + fechaFin;
        pedidosPorFechas = hiloPedidosList.ejecutaWebService(rutaWS,"2");
        recargarTablePedidos(pedidosPorFechas);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void limpiaTblDetallePedido() {
        recargarTableDetallePedidos(detallePedidosGlobal);
    }
    
    public void borrar() {
        limpiaTblDetallePedido();        
        //LIMPIA TXT BUSQUEDA VENTAS
        txtBuscarPedido.setText("");
        actualizarBusquedaPedido();
        
        //limpia jcalendars
        jCalFechaIni.setDate(null);
        jCalFechaFin.setDate(null);           
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        borrar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblConsultaPedidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblConsultaPedidosKeyReleased
        actualizarBusquedaDetalleVenta();
    }//GEN-LAST:event_tblConsultaPedidosKeyReleased

    private void btnProcesarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarPedidoActionPerformed
        if (tblConsultaPedidos.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar un pedido");
            return;
        }
        procesarPedido();
    }//GEN-LAST:event_btnProcesarPedidoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        obtieneTotales();
    }//GEN-LAST:event_formWindowOpened

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Realmente "
                + "deseas borrar el registro?");
        if(dialogResult == JOptionPane.YES_OPTION){
            if (tblConsultaPedidos.getSelectedRow() >= 0) {
                //PedidoBean p = new PedidoBean();
                //hiloPedidos = new WSPedidos();
                int idPedido = Integer.parseInt(
                        tblConsultaPedidos
                                .getValueAt(tblConsultaPedidos.getSelectedRow()
                                        , 0).toString()
                        );
                borraPedido(idPedido);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona el pedido para "
                        + "eliminar");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultasActionPerformed
        this.setVisible(false);
        this.dispose();
        FrmConsultas frmConsultas = new FrmConsultas();
        frmConsultas.setExtendedState(frmConsultas.MAXIMIZED_BOTH);
        frmConsultas.setVisible(true);
    }//GEN-LAST:event_btnConsultasActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        this.setVisible(false);
        this.dispose();
        BarraProgreso barraProgreso = new BarraProgreso();
        barraProgreso.setProceso(1);
        barraProgreso.setVisible(true);
    }//GEN-LAST:event_btnInicioActionPerformed

    public void actualizarBusquedaPedido() {
        ArrayList<PedidoBean> resultWS = null;
        ProductoBean producto = null;
        //No. Venta, Cliente, Sucursal, Usuario
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("No. Pedido")) {
            if (txtBuscarPedido.getText().equalsIgnoreCase("")) {
                resultWS = PedidosGlobal;
                recargarTableDetallePedidos(detallePedidosGlobal);
            } else {
                resultWS = llenaTablaPedidos(
                        txtBuscarPedido.getText().trim(),0);
            }
        }
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Cliente")) {
            if (txtBuscarPedido.getText().equalsIgnoreCase("")) {
                resultWS = PedidosGlobal;
                recargarTableDetallePedidos(detallePedidosGlobal);
            } else {
                resultWS = llenaTablaPedidos(
                        txtBuscarPedido.getText().trim(),1);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Sucursal")) {
            if (txtBuscarPedido.getText().equalsIgnoreCase("")) {
                resultWS = PedidosGlobal;
                recargarTableDetallePedidos(detallePedidosGlobal);
            } else {
                resultWS = llenaTablaPedidos(
                        txtBuscarPedido.getText().trim(),2);
            }
        } 
        if (String.valueOf(cboParametroVentas.getSelectedItem()).
                equalsIgnoreCase("Usuario")) {
            if (txtBuscarPedido.getText().equalsIgnoreCase("")) {
                resultWS = PedidosGlobal;
                recargarTableDetallePedidos(detallePedidosGlobal);
            } else {
                resultWS = llenaTablaPedidos(
                        txtBuscarPedido.getText().trim(),3);
            }
        } 
        if (txtBuscarPedido.getText().equalsIgnoreCase("")) {
            resultWS = PedidosGlobal;
            recargarTableDetallePedidos(detallePedidosGlobal);
        } 
        recargarTablePedidos(resultWS);
    }
    
    private ArrayList<PedidoBean> llenaTablaPedidos(String buscar, int tipoBusq) {
        ArrayList<PedidoBean> resultWS = new ArrayList<PedidoBean>();
        PedidoBean pedido = null;
        for (int i=0; i<tblConsultaPedidos.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaPedidos.getModel().getValueAt(
                    i,0).toString();
                    break;
                case 1 : campoBusq = tblConsultaPedidos.getModel().getValueAt(
                    i,2).toString();
                    break;
                case 2 : campoBusq = tblConsultaPedidos.getModel().getValueAt(
                    i,3).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
                case 3 : campoBusq = tblConsultaPedidos.getModel().getValueAt(
                    i,4).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                pedido = new PedidoBean();
                pedido.setIdPedido(Integer.parseInt(tblConsultaPedidos.getModel()
                        .getValueAt(i,0).toString()));
                
                String fecha = String.valueOf(tblConsultaPedidos.getModel()
                        .getValueAt(i,1));
                pedido.setFecha(util.stringToDate(fecha));
                pedido.setIdCliente(util.buscaIdCliente(Principal.clientesHM
                        , tblConsultaPedidos.getModel().getValueAt(i,2).toString()));
                int idSuc = util.buscaIdSuc(Principal.sucursalesHM
                        , "" + tblConsultaPedidos
                                .getModel().getValueAt(i,3).toString());
                pedido.setIdSucursal(idSuc);
                pedido.setIdUsuario(util.buscaIdUsuario(Principal.usuariosHM
                        , "" + tblConsultaPedidos
                                .getModel().getValueAt(i,4).toString()));
                pedido.setSubtotal(Double.parseDouble(tblConsultaPedidos
                                .getModel().getValueAt(i,5).toString()));
                pedido.setIva(Double.parseDouble(tblConsultaPedidos
                                .getModel().getValueAt(i,6).toString()));
                pedido.setTotal(Double.parseDouble(tblConsultaPedidos
                                .getModel().getValueAt(i,7).toString()));
                resultWS.add(pedido);
            }
        }
        return resultWS;
    }

    public void actualizarBusquedaDetalleVenta() {
        recargarTableDetallePedidos(detallePedidosGlobal);
        ArrayList<DetallePedidoBean> resultWS;
        //ProductoBean producto = null;
        String idPedido = tblConsultaPedidos.getModel()
                .getValueAt(tblConsultaPedidos.getSelectedRow(),0).toString();
        resultWS = llenaTablaDetallePedidos(idPedido.trim(),0);
        recargarTableDetallePedidos(resultWS);
    }
    
    private ArrayList<DetallePedidoBean> llenaTablaDetallePedidos(String buscar
            , int tipoBusq) {
        ArrayList<DetallePedidoBean> resultWS = new ArrayList<DetallePedidoBean>();
        DetallePedidoBean detallePedido = null;
        for (int i=0; i<tblConsultaDetallePedido.getModel().getRowCount(); i++) {
            String campoBusq = "";
            switch (tipoBusq) {
                case 0 : campoBusq = tblConsultaDetallePedido.getModel()
                        .getValueAt(
                    i,1).toString().toLowerCase();
                    buscar = buscar.toLowerCase();
                    break;
            }
            if (campoBusq.indexOf(buscar)>=0) {
                detallePedido = new DetallePedidoBean();
                detallePedido.setIdDetallePedido(Integer.parseInt
                    (tblConsultaDetallePedido.getModel().getValueAt(i,0)
                            .toString()));
                detallePedido.setIdPedido(Integer.parseInt
                    (tblConsultaDetallePedido.getModel().getValueAt(i,1)
                            .toString()));
                int idArt = util.buscaIdProd(
                        Principal.productosHMID, tblConsultaDetallePedido
                                .getModel().getValueAt(i,2).toString());
                detallePedido.setIdArticulo(idArt);
                detallePedido.setPrecio(Double.parseDouble(tblConsultaDetallePedido
                        .getModel().getValueAt(i,3).toString()));
                detallePedido.setCantidad(Double.parseDouble(tblConsultaDetallePedido
                        .getModel().getValueAt(i,4).toString()));
                detallePedido.setDescuento(Double.parseDouble(tblConsultaDetallePedido
                        .getModel().getValueAt(i,5).toString()));
                detallePedido.setUnidadMedida(String.valueOf(tblConsultaDetallePedido
                        .getModel().getValueAt(i,6).toString()));
                detallePedido.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM
                        , tblConsultaDetallePedido.getModel().getValueAt(i,7)
                                .toString()));
                resultWS.add(detallePedido);
            }
        }
        return resultWS;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultas;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnProcesarPedido;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboParametroVentas;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jCalFechaFin;
    private com.toedter.calendar.JDateChooser jCalFechaIni;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblConsultaDetallePedido;
    private javax.swing.JTable tblConsultaPedidos;
    private javax.swing.JTextField txtBuscarPedido;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}