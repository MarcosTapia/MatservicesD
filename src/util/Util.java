package util;

import beans.SucursalBean;
import constantes.ConstantesProperties;
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
    private Map<String,String> sucursalesHM = new HashMap();

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

    
    public Map<String, String> getSucursalesHM() {
        return sucursalesHM;
    }

    public void setSucursalesHM(Map<String, String> sucursalesHM) {
        this.sucursalesHM = sucursalesHM;
    }
}
