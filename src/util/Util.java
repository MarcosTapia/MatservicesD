package util;

import beans.CategoriaBean;
import beans.ClienteBean;
import beans.ComprasBean;
import beans.EdoMunBean;
import beans.EstadoBean;
import beans.MensajeBean;
import beans.MovimientosBean;
import beans.MunicipioBean;
import beans.ProductoBean;
import beans.ProveedorBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import beans.VentasBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCategoriasList;
import consumewebservices.WSClientesList;
import consumewebservices.WSEstadosList;
import consumewebservices.WSInventariosList;
import consumewebservices.WSMensajesList;
import consumewebservices.WSMovimientos;
import consumewebservices.WSProveedoresList;
import consumewebservices.WSSucursalesList;
import consumewebservices.WSUsuarios;
import consumewebservices.WSUsuariosList;
import consumewebservices.WSVentasList;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import vistas.Ingreso;
import vistas.Principal;

public class Util {
    Properties constantes = new ConstantesProperties().getProperties();
    WSVentasList hiloVentasList;
    //WSComprasList hiloComprasList;
    WSMovimientos hiloMovimientos;
    WSEstadosList hiloEstadosList;
    WSSucursalesList hiloSucursalesList;
    WSCategoriasList hiloCategoriasList;
    WSProveedoresList hiloProveedoresList;
    WSInventariosList hiloInventariosList;
    WSUsuariosList hiloUsuariosList;
    WSClientesList hiloClientesList;
    private Map<String,String> ventasHM = new HashMap();
//    private Map<String,String> ventasHM = new HashMap();
    private Map<String,String> municipiosHM = new HashMap();
    private Map<String,String> estadosMunHM = new HashMap();
    private Map<String,String> estadosHM = new HashMap();
    private Map<String,String> sucursalesHM = new HashMap();
    private Map<String,String> categoriasHM = new HashMap();
    private Map<String,String> proveedoresHM = new HashMap();
    private Map<String,String> productosHM = new HashMap();
    private Map<String,String> productosHMID = new HashMap();
    private Map<String,String> usuariosHM = new HashMap();
    private Map<String,String> clientesHM = new HashMap();
    
    /**
     * Metodo para convertir a numero
     */
    public boolean esNumero(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }    
    
    /**
     * Metodo para mostrar mensaje de error
     */
    public void errorEliminar() {
        JOptionPane optionPane = new JOptionPane("No es posible eliminar el "
                + "producto", JOptionPane.ERROR_MESSAGE);    
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);                    
    }

    /**
     * Metodo para mostrar mensaje de error
     */
    public void registroDuplicado(String msgConcepto) {
        String msg = "El registro ya Existe";
        if (!"".equalsIgnoreCase(msgConcepto)) {
            msg = "El " + msgConcepto + " ya Existe";
        } 
        JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);    
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);                    
    }
    
    /**
     * Metodo para mostrar mensaje de error de seleccion
     * de elemento
     */
    public void errorSeleccion() {
        String msg = "Selecciona todos los elementos";
        JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);    
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);                    
    }
    
    /**
     * Metodo para convertir formato de fecha 
     * de dd/mm/yyyy hh:mm:seg am(pm) a 
     * yyyy-mm-seg hh:min:seg"
     * @param fecha La fecha en string
     * @return El string de fecha
     */
    public String cambiaFormatoFecha(String fecha) {
        String dia = fecha.substring(0, 2);
        String mes = fecha.substring(3, 5);
        String anio = fecha.substring(6, 10);

        String hr = fecha.substring(11, 13);
        String min = fecha.substring(14, 16);
        String seg = fecha.substring(17, 19);

        fecha =  anio + "-" + 
                mes + "-" + dia + " " +
                hr + ":" + min + ":" + seg;
        return fecha;
    }
    
    
    /**
     * Metodo para convertir fecha a string "dd/MM/yyyy"
     * @param fecha La fecha en date dd/MM/yyyy
     * @return El string de fecha
     */
    public String dateToString(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "MX"));
        String ret = "";
        ret = sdf.format(fecha);
        return ret;
    }

    /**
     * Metodo para convertir string a fecha
     * @param fecha La fecha en forma de string dd/MM/yyyy
     * @return El objeto de fecha
    */
    public Date stringToDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "MX"));
        Date ret = null;
        if(!"".equals(fecha)){
            try {
                    ret = sdf.parse(fecha);
            } catch (ParseException ex) {
                    ret = new Date();
            }
        }
        return ret;
    }
    
    /**
     * Metodo para convertir string a fecha datetime
     * @param fecha La fecha en forma de string yyyy-mm-dd hh:min:seg
     * @return El objeto de fecha
    */
    public Date stringToDateTime(String fecha) {
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = null;    
        try{    
            date = sdf1.parse(fecha);
            String string=sdf1.format(date);
        } catch (ParseException ex) {
            date = null;
        }
        return date;
    }
    
    /**
     * Metodo para convertir date a datetime
     * @param date La fecha en forma util.date
     * @return El String de fecha
    */
    public String dateToDateTimeAsString(Date date) {
        //java.util.Date dt = new java.util.Date();
        java.util.Date dt = date;
        java.text.SimpleDateFormat sdf = 
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        return currentTime;
    }

    //********* SUCURSALES
    /**
     * Metodo para cargar sucursales
     * @return Hash sucursales
     */
    public ArrayList<SucursalBean> getMapSucursales() {
        ArrayList<SucursalBean> sucursales = new ArrayList();
        hiloSucursalesList = new WSSucursalesList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETSUCURSALES");
        sucursales = hiloSucursalesList.ejecutaWebService(rutaWS,"1");
        return sucursales;
    }
    
    public void llenaMapSucursales(ArrayList<SucursalBean> sucursales) {
        //carga hashmap de sucurales
        for (SucursalBean s : sucursales) {
            this.sucursalesHM.put(""+s.getIdSucursal(), s.getDescripcionSucursal());
        }
    }
    
    public int buscaIdSuc(Map<String,String> sucursalesHM, String descripcionSuc) {
        Iterator it = sucursalesHM.keySet().iterator();
        int idSuc = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionSuc.equalsIgnoreCase(sucursalesHM.get(key))) {
              idSuc = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idSuc;
    }
    
    public String buscaDescFromIdSuc(Map<String,String> sucursalesHM, String idSuc) {
        Iterator it = sucursalesHM.keySet().iterator();
        String descripSuc = "";
        while(it.hasNext()){
          Object key = it.next();
          //JOptionPane.showMessageDialog(null, key.toString());
          String t = key.toString();
          if (t.equalsIgnoreCase(idSuc)) {
              descripSuc = sucursalesHM.get(key).toString();
              break;
          }
        }        
        return descripSuc;
    }
    //********* FIN SUCURSALES

    //********* CATEGORIAS
    /**
     * Metodo para cargar categorias
     * @return Hash categorias
     */
    public ArrayList<CategoriaBean> getMapCategorias() {
        ArrayList<CategoriaBean> categorias = new ArrayList();
        hiloCategoriasList = new WSCategoriasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCATEGORIAS");
        categorias = hiloCategoriasList.ejecutaWebService(rutaWS,"1");
        return categorias;
    }
    
    public void llenaMapCategorias(ArrayList<CategoriaBean> categorias) {
        //carga hashmap de sucurales
        for (CategoriaBean s : categorias) {
            this.categoriasHM.put(""+s.getIdCategoria(), s.getDescripcionCategoria());
        }
    }
    
    public int buscaIdCat(Map<String,String> categoriasHM, String descripcionCat) {
        Iterator it = categoriasHM.keySet().iterator();
        int idCat = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionCat.equalsIgnoreCase(categoriasHM.get(key))) {
              idCat = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idCat;
    }
    
    public String buscaDescFromIdCat(Map<String,String> categoriasHM, String idCat) {
        Iterator it = categoriasHM.keySet().iterator();
        String descripCat = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idCat)) {
              descripCat = categoriasHM.get(key).toString();
              break;
          }
        }        
        return descripCat;
    }
    //********* FIN CATEGORIAS

    
    //********* PROVEEDORES
    /**
     * Metodo para cargar proveedores
     * @return Hash proveedores
     */
    public ArrayList<ProveedorBean> getMapProveedores() {
        ArrayList<ProveedorBean> proveedores = new ArrayList();
        hiloProveedoresList = new WSProveedoresList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETPROVEEDORES");
        proveedores = hiloProveedoresList.ejecutaWebService(rutaWS,"1");
        return proveedores;
    }
    
    public void llenaMapProveedores(ArrayList<ProveedorBean> proveedores) {
        //carga hashmap de proveedores
        for (ProveedorBean s : proveedores) {
            this.proveedoresHM.put(""+s.getIdProveedor(), s.getEmpresa());
        }
    }
    
    public int buscaIdProv(Map<String,String> proveedoresHM, String descripcionProv) {
        Iterator it = proveedoresHM.keySet().iterator();
        int idProv = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionProv.equalsIgnoreCase(proveedoresHM.get(key))) {
              idProv = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idProv;
    }
    
    public String buscaDescFromIdProv(Map<String,String> proveedoresHM, String idProv) {
        Iterator it = proveedoresHM.keySet().iterator();
        String descripProv = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idProv)) {
              descripProv = proveedoresHM.get(key).toString();
              break;
          }
        }        
        return descripProv;
    }
    //********* FIN PROVEEDORES
    

    //********* PRODUCTOS
    /**
     * Metodo para cargar proveedores
     * @return Hash proveedores
     */
    public ArrayList<ProductoBean> getInventario() {
        ArrayList<ProductoBean> productos = new ArrayList();
        hiloInventariosList = new WSInventariosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETINVENTARIOS");
        productos = hiloInventariosList.ejecutaWebService(rutaWS,"1");
        return productos;
    }
    
    public void llenaMapProductos(ArrayList<ProductoBean> productos) {
        //carga hashmap de productos
        for (ProductoBean s : productos) {
            this.productosHM.put("" + s.getCodigo(), s.getDescripcion());
            this.productosHMID.put("" + s.getIdArticulo(), s.getDescripcion());
        }
    }
    
    public String buscaCodProd(Map<String,String> productosHM, String descripcionProd) {
        Iterator it = productosHM.keySet().iterator();
        String codProd = "";
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionProd.equalsIgnoreCase(productosHM.get(key))) {
              codProd = key.toString();
              break;
          }
        }        
        return codProd;
    }
    
    public int buscaIdProd(Map<String,String> productosHMID, String descripcionProd) {
        Iterator it = productosHMID.keySet().iterator();
        int idProd = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionProd.equalsIgnoreCase(productosHMID.get(key))) {
              idProd = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idProd;
    }
    
    public String buscaDescFromCodProd(Map<String,String> productosHM, String idProd) {
        Iterator it = productosHM.keySet().iterator();
        String descripProd = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idProd)) {
              descripProd = productosHM.get(key).toString();
              break;
          }
        }        
        return descripProd;
    }
    
    public boolean buscaProdDuplicadoEnSucursal(ArrayList<ProductoBean> 
            productos, String codigo, int idSucursal) {
        boolean existe = false;
        for (ProductoBean s : productos) {
            if ((codigo.equalsIgnoreCase(s.getCodigo()) &&
                    (idSucursal == s.getIdSucursal()))) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    public String buscaDescFromIdProd(Map<String,String> productosHMID, String idArt) {
        Iterator it = productosHMID.keySet().iterator();
        String descripProd = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idArt)) {
              descripProd = productosHMID.get(key).toString();
              break;
          }
        }        
        return descripProd;
    }
    
//    public int buscaIdFromCodSucProd(Map<String, String> productosHM, 
//            String codigoProd, String idSuc) {
//        Iterator it = productosHMID.keySet().iterator();
//        int idProd = 0;
//        while(it.hasNext()){
//          Object key = it.next();
//          if (descripcionProd.equalsIgnoreCase(productosHMID.get(key))) {
//              idProd = Integer.parseInt(key.toString());
//              break;
//          }
//        }        
//        return idProd;
//    }
    
    //********* FIN PRODUCTOS
    
    //********* USUARIOS
    /**
     * Metodo para cargar usuarios
     * @return Hash usuarios
     */
    public ArrayList<UsuarioBean> getMapUsuarios() {
        ArrayList<UsuarioBean> usuarios = new ArrayList();
        hiloUsuariosList = new WSUsuariosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETUSUARIOS");
        usuarios = hiloUsuariosList.ejecutaWebService(rutaWS,"1");
        return usuarios;
    }
    
    public void llenaMapUsuarios(ArrayList<UsuarioBean> usuarios) {
        //carga hashmap de usuarios
        for (UsuarioBean s : usuarios) {
            this.usuariosHM.put(""+s.getIdUsuario(), s.getNombre() 
            + " " + s.getApellido_paterno()
            + " " + s.getApellido_materno());
        }
    }
    
    public int buscaIdUsuario(Map<String,String> usuariosHM, String nombre) {
        Iterator it = usuariosHM.keySet().iterator();
        int idUsu = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (nombre.equalsIgnoreCase(usuariosHM.get(key))) {
              idUsu = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idUsu;
    }
    
    public String buscaDescFromIdUsu(Map<String,String> usuariosHM, String idUsu) {
        Iterator it = usuariosHM.keySet().iterator();
        String nombre = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idUsu)) {
              nombre = usuariosHM.get(key).toString();
              break;
          }
        }        
        return nombre;
    }
    //********* FIN USUARIOS
    
    //********* CLIENTES
    /**
     * Metodo para cargar clientes
     * @return Hash clientes
     */
    public ArrayList<ClienteBean> getMapClientes() {
        ArrayList<ClienteBean> clientes = new ArrayList();
        hiloClientesList = new WSClientesList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETCLIENTES");
        clientes = hiloClientesList.ejecutaWebService(rutaWS,"1");
        return clientes;
    }
    
    public void llenaMapClientes(ArrayList<ClienteBean> clientes) {
        //carga hashmap de clientes
        for (ClienteBean s : clientes) {
            String indice = String.valueOf(s.getIdCliente());
            String nombreCompleto = s.getNombre() + " " + s.getApellidos();
            this.clientesHM.put(indice, nombreCompleto);
        }
    }
    
    public int buscaIdCliente(Map<String,String> clientesHM, String nombre) {
        Iterator it = clientesHM.keySet().iterator();
        int idCli = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (nombre.equalsIgnoreCase(clientesHM.get(key))) {
              idCli = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idCli;
    }
    
    public String buscaDescFromIdCli(Map<String,String> clientesHM, String idCli) {
        Iterator it = clientesHM.keySet().iterator();
        String nombre = "";
        while(it.hasNext()){
          Object key = it.next();
          String t = key.toString();
          if (t.equalsIgnoreCase(idCli)) {
              nombre = clientesHM.get(key).toString();
              break;
          }
        }        
        return nombre;
    }
    //********* FIN CLIENTES
    
    //********* ESTADOS Y MUNICIPIOS
    /**
     * Metodo para cargar estados
     * @return Hash estados
     */
    public ArrayList<EstadoBean> getMapEstados() {
        ArrayList<EstadoBean> estados = new ArrayList();
        hiloEstadosList = new WSEstadosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETESTADOS");
        estados = hiloEstadosList.ejecutaWebService(rutaWS,"1");
        return estados;
    }
    
    /**
     * Metodo para cargar estados y municipios (claves)
     * @return Hash estados y municipios
     */
    public ArrayList<EdoMunBean> getMapEstadosMun() {
        ArrayList<EdoMunBean> estadosMun = new ArrayList();
        hiloEstadosList = new WSEstadosList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETESTADOSMUNICIPIOS");
        estadosMun = hiloEstadosList.ejecutaWebService2(rutaWS,"1");
        return estadosMun;
    }
    
    /**
     * Metodo para cargar municipios
     * @return Hash estados y municipios
     */
    public ArrayList<MunicipioBean> getMapMunicipios() {
        ArrayList<MunicipioBean> municipios = new ArrayList();
        hiloEstadosList = new WSEstadosList();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETMUNICIPIOS");
        municipios = hiloEstadosList.ejecutaWebService3(rutaWS,"1");
        return municipios;
    }
    
    public void llenaMapEstados(ArrayList<EstadoBean> estados) {
        //carga hashmap de estados
        for (EstadoBean s : estados) {
            this.estadosHM.put(String.valueOf(s.getIdEstado()), s.getEstado());
        }
        
        //ordena hashmap por key muy bueno
//        Map mapOrdenado = new TreeMap(this.estadosHM);
//        Set ref = mapOrdenado.keySet();
//        Iterator it = ref.iterator();
//        while (it.hasNext()) {
//            System.out.println((String)it.next());
//        }
//        this.estadosHM.clear();
//        this.estadosHM = mapOrdenado;
        
        //ordena hashmap por valor muy bueno
        HashMap mapResultado = new LinkedHashMap();
        List misMapKeys = new ArrayList(this.estadosHM.keySet());
        List misMapValues = new ArrayList(this.estadosHM.values());
        TreeSet conjuntoOrdenado = new TreeSet(misMapValues);
        Object[] arrayOrdenado = conjuntoOrdenado.toArray();
        int size = arrayOrdenado.length;
        for (int i=0; i<size; i++) {
        mapResultado.put(misMapKeys.get(misMapValues.indexOf(arrayOrdenado[i]))
                ,arrayOrdenado[i]);
        }
//        Iterator it1 = mapResultado.values().iterator();
//        while (it1.hasNext()) {
//            System.out.println((String)it1.next());
//        }        
        this.estadosHM.clear();
        this.estadosHM = mapResultado;
    }
    
    public void llenaMapMunicipios(ArrayList<MunicipioBean> municipios) {
        //carga hashmap de estados
        for (MunicipioBean s : municipios) {
            this.municipiosHM.put(String.valueOf(s.getIdMunicipio())
                    , s.getMunicipio());
//            if (String.valueOf(s.getIdMunicipio()).equalsIgnoreCase("35"))
//                JOptionPane.showMessageDialog(null, "encontre");
        }
        
//        //ordena hashmap por key muy bueno
////        Map mapOrdenado = new TreeMap(this.estadosHM);
////        Set ref = mapOrdenado.keySet();
////        Iterator it = ref.iterator();
////        while (it.hasNext()) {
////            System.out.println((String)it.next());
////        }
////        this.estadosHM.clear();
////        this.estadosHM = mapOrdenado;
//        
//        //ordena hashmap por valor muy bueno
//        HashMap mapResultado = new LinkedHashMap();
//        List misMapKeys = new ArrayList(this.municipiosHM.keySet());
//        List misMapValues = new ArrayList(this.municipiosHM.values());
//        TreeSet conjuntoOrdenado = new TreeSet(misMapValues);
//        Object[] arrayOrdenado = conjuntoOrdenado.toArray();
//        int size = arrayOrdenado.length;
//        for (int i=0; i<size; i++) {
//        mapResultado.put(misMapKeys.get(misMapValues.indexOf(arrayOrdenado[i]))
//                ,arrayOrdenado[i]);
//        }
////        Iterator it1 = mapResultado.values().iterator();
////        while (it1.hasNext()) {
////            System.out.println((String)it1.next());
////        }        
//        this.municipiosHM.clear();
//        this.municipiosHM = mapResultado;
    }
    
    public Map<String,String> ordenaMunicipios(Map<String,String> municipiosHM) {
        //ordena hashmap por valor muy bueno
        HashMap mapResultado = new LinkedHashMap();
        List misMapKeys = new ArrayList(municipiosHM.keySet());
        List misMapValues = new ArrayList(municipiosHM.values());
        TreeSet conjuntoOrdenado = new TreeSet(misMapValues);
        Object[] arrayOrdenado = conjuntoOrdenado.toArray();
        int size = arrayOrdenado.length;
        for (int i=0; i<size; i++) {
            mapResultado.put(misMapKeys.get(misMapValues.indexOf(arrayOrdenado[i]))
                ,arrayOrdenado[i]);
        }
        return mapResultado;
    }
    
    public int buscaIdEdo(Map<String,String> estadosHM, String descripcionEdo) {
        Iterator it = estadosHM.keySet().iterator();
        int idEdo = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionEdo.equalsIgnoreCase(estadosHM.get(key))) {
              idEdo = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idEdo;
    }
    
    public int buscaIdMun(Map<String,String> municipiosHM
            , String descripcionMun) {
        Iterator it = municipiosHM.keySet().iterator();
        int idMun = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionMun.equalsIgnoreCase(municipiosHM.get(key))) {
              idMun = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idMun;
    }
    
    public String buscaDescFromIdEdo(Map<String,String> estadosHM, String idEdo) {
        Iterator it = estadosHM.keySet().iterator();
        String descripEdo = "";
        while(it.hasNext()){
          Object key = it.next();
          //JOptionPane.showMessageDialog(null, key.toString());
          String t = key.toString();
          if (t.equalsIgnoreCase(idEdo)) {
              descripEdo = estadosHM.get(key).toString();
              break;
          }
        }        
        return descripEdo;
    }
    
    public String buscaDescFromIdMun(Map<String,String> municipiosHM
            , String idMun) {
        Iterator it = municipiosHM.keySet().iterator();
        String descripMun = "";
        while(it.hasNext()){
          Object key = it.next();
          //JOptionPane.showMessageDialog(null, key.toString());
          String t = key.toString().trim();
//          if (t.equalsIgnoreCase("34"))
//              JOptionPane.showMessageDialog(null, "encontre");
          if (t.equalsIgnoreCase(idMun)) {
              descripMun = municipiosHM.get(key).toString();
              break;
          }
        }        
        return descripMun;
    }
    //********* FIN ESTADOS Y MUNICIPIOS

    //********* VENTAS
    /**
     * Metodo para cargar ventas
     * @return Hash ventas
     */
    public ArrayList<VentasBean> cargaVentas() {
        ArrayList<VentasBean> ventas = new ArrayList();
        hiloVentasList = new WSVentasList();
        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETVENTAS");
        ventas = hiloVentasList.ejecutaWebService(rutaWS,"1");
        return ventas;
    }
    //********* FIN VENTAS
    
    //********* COMPRAS
    /**
     * Metodo para cargar compras
     * @return Hash compras
     */
//    public ArrayList<ComprasBean> cargaCompras() {
//        ArrayList<VentasBean> ventas = new ArrayList();
//        hiloComprasList = new WSComprasList();
//        String rutaWS = constantes.getProperty("IP") + constantes.getProperty("GETVENTAS");
//        ventas = hiloVentasList.ejecutaWebService(rutaWS,"1");
//        return ventas;
//    }
    //********* FIN COMPRAS
    
    //********* OBTIENE FECHA DEL SERVIDOR
    public Date obtieneFechaServidor() {
        ArrayList<MensajeBean> fechaServidorArray = null;
        MensajeBean fechaServidorObj = null;
        Properties constantes = new ConstantesProperties().getProperties();
        WSMensajesList hiloMensajesList;
        hiloMensajesList = new WSMensajesList();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("OBTIENEFECHASERVIDOR");
        fechaServidorArray = hiloMensajesList.ejecutaWebService(rutaWS,"4");
        fechaServidorObj = fechaServidorArray.get(0);
        return fechaServidorObj.getFecha();
    }
    //********* FIN OBTIENE FECHA DEL SERVIDOR
    
    //********* ENVIA CORREO
    
    //ENVIACORREOMENSAJE = util/envia_correo.php?mensaje=
//ENVIACORREOREMITENTE = &remitente=
//ENVIACORREODESTINATARIO = &destinatario=
//ENVIACORREOTITULO = &titulo=
//ENVIACORREONEGOCIO = &negocio=            

    
    public boolean enviaCorreo(String mensaje, String remitente, String destinatario
        , String titulo, String negocio) {
        boolean enviado = false;
        Properties constantes = new ConstantesProperties().getProperties();        
        WSMensajesList hiloMensajesList;
        hiloMensajesList = new WSMensajesList();
//        String rutaWS = constantes.getProperty("IP") 
//                + constantes.getProperty("ENVIACORREOMENSAJE")
//                + mensaje 
//                + constantes.getProperty("ENVIACORREOREMITENTE")
//                + remitente 
//                + constantes.getProperty("ENVIACORREODESTINATARIO")
//                + destinatario 
//                + constantes.getProperty("ENVIACORREOTITULO")
//                + titulo 
//                + constantes.getProperty("ENVIACORREONEGOCIO")
//                + negocio 
//                ;
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("ENVIACORREOMENSAJE");
        enviado = hiloMensajesList.ejecutaWebServiceEnviaCorreo(rutaWS,"1"
            , mensaje
            , remitente
            , destinatario
            , titulo
            , negocio);
        return enviado;
    }
    //********* FIN ENVIA CORREO
    
    //OPERACIONES COMUNES
    public int obtenerUltimoIdMovimientos() {
        int id;
        MovimientosBean resultWS;
        hiloMovimientos = new WSMovimientos();
        String rutaWS = constantes.getProperty("IP") 
                + constantes.getProperty("GETULTIMOIDMOVIMIENTOS");
        resultWS = hiloMovimientos.ejecutaWebService(rutaWS,"2");
        id = resultWS.getIdMovimiento() + 1;
        return id;
    }
    
    
    
    
    //FIN OPERACIONES COMUNES
    
    public Map<String, String> getSucursalesHM() {
        return sucursalesHM;
    }

    public void setSucursalesHM(Map<String, String> sucursalesHM) {
        this.sucursalesHM = sucursalesHM;
    }

    public Map<String, String> getCategoriasHM() {
        return categoriasHM;
    }

    public void setCategoriasHM(Map<String, String> categoriasHM) {
        this.categoriasHM = categoriasHM;
    }

    public Map<String, String> getProveedoresHM() {
        return proveedoresHM;
    }

    public void setProveedoresHM(Map<String, String> proveedoresHM) {
        this.proveedoresHM = proveedoresHM;
    }

    public Map<String, String> getProductosHM() {
        return productosHM;
    }

    public Map<String, String> getProductosHMID() {
        return productosHMID;
    }

    public void setProductosHMID(Map<String, String> productosHMID) {
        this.productosHMID = productosHMID;
    }

    public void setProductosHM(Map<String, String> productosHM) {
        this.productosHM = productosHM;
    }

    public Map<String, String> getClientesHM() {
        return clientesHM;
    }

    public void setClientesHM(Map<String, String> clientesHM) {
        this.clientesHM = clientesHM;
    }

    public Map<String, String> getUsuariosHM() {
        return usuariosHM;
    }

    public void setUsuariosHM(Map<String, String> usuariosHM) {
        this.usuariosHM = usuariosHM;
    }

    public Map<String, String> getEstadosHM() {
        return estadosHM;
    }

    public void setEstadosHM(Map<String, String> estadosHM) {
        this.estadosHM = estadosHM;
    }

    public Map<String, String> getEstadosMunHM() {
        return estadosMunHM;
    }

    public void setEstadosMunHM(Map<String, String> estadosMunHM) {
        this.estadosMunHM = estadosMunHM;
    }

    public Map<String, String> getMunicipiosHM() {
        return municipiosHM;
    }

    public void setMunicipiosHM(Map<String, String> municipiosHM) {
        this.municipiosHM = municipiosHM;
    }
    
}
