package consumewebservices;

import beans.CategoriaBean;
import beans.ClienteBean;
import beans.SucursalBean;
import beans.UsuarioBean;
import beans.VentasBean;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSVentasList {
    String cadena;
    String fechaIni;
    String fechaFin;
    
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";
/*
idVenta	int(11)
    fecha	datetime
    idCliente	int(11)
    observaciones	varchar(100)
    idUsuario	int(11)
    idSucursal    
*/
    public ArrayList<VentasBean> ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ArrayList<VentasBean> ventas = new ArrayList();
        switch (params[1]) { 
            case "1" : ventas = obtenerVentasWS(); break;
            case "2" : 
                ventas = busquedaVentasPorFechasWS(); break;
//            case "2" : ventas = buscaCategoriaIdWS(cadena); break;
//            case "3" : ventas = busquedaVentasPorFechasWS(cadena); break;
//            case "4" : ventas = busquedaCategoriasPorNombreWS(cadena); break;
        }
        return ventas;
    }
 
    public ArrayList<VentasBean> obtenerVentasWS() {
        ArrayList<VentasBean> ventas = new ArrayList();
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                int resultJSON = respuestaJSON.getInt("estado");
                if (resultJSON == 1) {
                    JSONArray ventasJSON = respuestaJSON.getJSONArray("ventas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<ventasJSON.length();i++){
                        VentasBean venta = new VentasBean();
                        //convierte fecha String a Date
                        String fechaS = ventasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        venta.setFecha(util.stringToDateTime(fechaS));
                        venta.setIdCliente(ventasJSON.getJSONObject(i)
                                .getInt("idCliente"));
                        venta.setIdSucursal(ventasJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        venta.setIdUsuario(ventasJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        venta.setIdVenta(ventasJSON.getJSONObject(i)
                                .getInt("idVenta"));
                        venta.setObservaciones(ventasJSON.getJSONObject(i)
                                .getString("observaciones"));
                        
                        venta.setSubtotal(ventasJSON.getJSONObject(i)
                                .getDouble("subtotal"));
                        venta.setIva(ventasJSON.getJSONObject(i)
                                .getDouble("iva"));
                        venta.setTotal(ventasJSON.getJSONObject(i)
                                .getDouble("total"));
                        venta.setCancelada(ventasJSON.getJSONObject(i)
                                .getInt("cancelada"));
                        venta.setFacturada(ventasJSON.getJSONObject(i)
                                .getInt("facturada"));
                        venta.setIdFactura(ventasJSON.getJSONObject(i)
                                .getInt("idFactura"));
                        venta.setTipovta(ventasJSON.getJSONObject(i)
                                .getString("tipovta"));
                        ventas.add(venta);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ventas;
    }
        
//    public ArrayList<CategoriaBean> busquedaCategoriasPorNombreWS(String rutaWS) {
//        ArrayList<CategoriaBean> ventas = new ArrayList();
//        try {
//            url = new URL(cadena);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
//                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
//            //connection.setHeader("content-type", "application/json");
//            int respuesta = connection.getResponseCode();
//            StringBuilder result = new StringBuilder();
//            if (respuesta == HttpURLConnection.HTTP_OK){
//                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
//                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
//                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
//                // StringBuilder.
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);        // Paso toda la entrada al StringBuilder
//                }
//                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
//                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
//                //Accedemos al vector de resultados
//                int resultJSON = respuestaJSON.getInt("estado");
//                if (resultJSON == 1) {
//                    JSONArray categoriasJSON = respuestaJSON.getJSONArray("categoria");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<categoriasJSON.length();i++){
//                        CategoriaBean cat = new CategoriaBean();
//                        cat.setIdCategoria(categoriasJSON.getJSONObject(i).getInt("idCategoria"));
//                        cat.setDescripcionCategoria(categoriasJSON.getJSONObject(i).getString("descripcionCategoria"));
//                        ventas.add(cat);
//                    }
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return ventas;
//    }
        
    public ArrayList<VentasBean> busquedaVentasPorFechasWS(String... params) {
        ArrayList<VentasBean> ventas = new ArrayList();
//        VentasBean usuario = null;
        //cadena = params[0];
        url = null; // Url de donde queremos obtener información
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON == 1){
                    JSONArray ventasJSON = respuestaJSON.getJSONArray("ventas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<ventasJSON.length();i++){
                        VentasBean venta = new VentasBean();
                        //convierte fecha String a Date
                        String fechaS = ventasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        venta.setFecha(util.stringToDateTime(fechaS));
                        venta.setIdCliente(ventasJSON.getJSONObject(i)
                                .getInt("idCliente"));
                        venta.setIdSucursal(ventasJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        venta.setIdUsuario(ventasJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        venta.setIdVenta(ventasJSON.getJSONObject(i)
                                .getInt("idVenta"));
                        venta.setObservaciones(ventasJSON.getJSONObject(i)
                                .getString("observaciones"));
                        venta.setSubtotal(ventasJSON.getJSONObject(i)
                                .getDouble("subtotal"));
                        venta.setIva(ventasJSON.getJSONObject(i)
                                .getDouble("iva"));
                        venta.setTotal(ventasJSON.getJSONObject(i)
                                .getDouble("total"));
                        venta.setCancelada(ventasJSON.getJSONObject(i)
                                .getInt("cancelada"));
                        venta.setFacturada(ventasJSON.getJSONObject(i)
                                .getInt("facturada"));
                        venta.setIdFactura(ventasJSON.getJSONObject(i)
                                .getInt("idFactura"));
                        venta.setTipovta(ventasJSON.getJSONObject(i)
                                .getString("tipovta"));
                        ventas.add(venta);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No hay conexión al servidor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ventas;
    }

//    public ArrayList<CategoriaBean> buscaCategoriaIdWS(String rutaWS) {
//        ArrayList<CategoriaBean> ventas = new ArrayList();
//        try {
//            url = new URL(cadena);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
//                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
//            //connection.setHeader("content-type", "application/json");
//            int respuesta = connection.getResponseCode();
//            StringBuilder result = new StringBuilder();
//            if (respuesta == HttpURLConnection.HTTP_OK){
//                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
//                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
//                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
//                // StringBuilder.
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);        // Paso toda la entrada al StringBuilder
//                }
//                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
//                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
//                //Accedemos al vector de resultados
//                int resultJSON = respuestaJSON.getInt("estado");
//                if (resultJSON == 1) {
//                    CategoriaBean cat = new CategoriaBean();
//                    cat.setIdCategoria(respuestaJSON.getJSONObject("categoria").getInt("idCategoria"));
//                    cat.setDescripcionCategoria(respuestaJSON.getJSONObject("categoria").getString("descripcionCategoria"));
//                    ventas.add(cat);
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return ventas;
//    }
    
}
