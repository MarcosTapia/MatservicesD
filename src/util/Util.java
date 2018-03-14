package util;

import beans.CategoriaBean;
import beans.ProductoBean;
import beans.ProveedorBean;
import beans.SucursalBean;
import constantes.ConstantesProperties;
import consumewebservices.WSCategoriasList;
import consumewebservices.WSInventariosList;
import consumewebservices.WSProveedoresList;
import consumewebservices.WSSucursalesList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import vistas.Ingreso;
import vistas.Principal;

public class Util {
    Properties constantes = new ConstantesProperties().getProperties();
    WSSucursalesList hiloSucursalesList;
    WSCategoriasList hiloCategoriasList;
    WSProveedoresList hiloProveedoresList;
    WSInventariosList hiloInventariosList;
    private Map<String,String> sucursalesHM = new HashMap();
    private Map<String,String> categoriasHM = new HashMap();
    private Map<String,String> proveedoresHM = new HashMap();
    private Map<String,String> productosHM = new HashMap();
    
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
    

    //********* PRODUCTOD
    /**
     * Metodo para cargar proveedores
     * @return Hash proveedores
     */
    public ArrayList<ProductoBean> getMapProductos() {
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
        }
    }
    
    public int buscaIdProd(Map<String,String> productosHM, String descripcionProd) {
        Iterator it = productosHM.keySet().iterator();
        int idProd = 0;
        while(it.hasNext()){
          Object key = it.next();
          if (descripcionProd.equalsIgnoreCase(productosHM.get(key))) {
              idProd = Integer.parseInt(key.toString());
              break;
          }
        }        
        return idProd;
    }
    
    public String buscaDescFromIdProd(Map<String,String> productosHM, String idProd) {
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
    //********* FIN PRODUCTOD
    
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

    public void setProductosHM(Map<String, String> productosHM) {
        this.productosHM = productosHM;
    }
    
    
}
