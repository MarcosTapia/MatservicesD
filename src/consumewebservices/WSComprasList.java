package consumewebservices;

import beans.CategoriaBean;
import beans.ClienteBean;
import beans.ComprasBean;
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

public class WSComprasList {
    String cadena;
    String fechaIni;
    String fechaFin;
    
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public ArrayList<ComprasBean> ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ArrayList<ComprasBean> compras = new ArrayList();
        switch (params[1]) { 
            case "1" : compras = obtenerComprasWS(); break;
            case "2" : 
                compras = busquedaComprasPorFechasWS(); break;
//            case "2" : compras = buscaCategoriaIdWS(cadena); break;
//            case "3" : compras = busquedaComprasPorFechasWS(cadena); break;
//            case "4" : compras = busquedaCategoriasPorNombreWS(cadena); break;//            case "2" : compras = buscaCategoriaIdWS(cadena); break;
//            case "3" : compras = busquedaComprasPorFechasWS(cadena); break;
//            case "4" : compras = busquedaCategoriasPorNombreWS(cadena); break;
        }
        return compras;
    }
 
    public ArrayList<ComprasBean> obtenerComprasWS() {
        ArrayList<ComprasBean> compras = new ArrayList();
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
                    JSONArray comprasJSON = respuestaJSON.getJSONArray("compras");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<comprasJSON.length();i++){
                        ComprasBean compra = new ComprasBean();
                        //convierte fecha String a Date
                        String fechaS = comprasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        compra.setFecha(util.stringToDateTime(fechaS));
                        compra.setIdProveedor(comprasJSON.getJSONObject(i)
                                .getInt("idProveedor"));
                        compra.setIdSucursal(comprasJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        compra.setIdUsuario(comprasJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        compra.setIdCompra(comprasJSON.getJSONObject(i)
                                .getInt("idCompra"));
                        compra.setObservaciones(comprasJSON.getJSONObject(i)
                                .getString("observaciones"));
                        compra.setFactura(comprasJSON.getJSONObject(i)
                                .getString("factura"));
                        compras.add(compra);
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
        return compras;
    }
        
//    public ArrayList<CategoriaBean> busquedaCategoriasPorNombreWS(String rutaWS) {
//        ArrayList<CategoriaBean> compras = new ArrayList();
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
//                        compras.add(cat);
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
//        return compras;
//    }
        
    public ArrayList<ComprasBean> busquedaComprasPorFechasWS(String... params) {
        ArrayList<ComprasBean> compras = new ArrayList();
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
                    JSONArray comprasJSON = respuestaJSON.getJSONArray("compras");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<comprasJSON.length();i++){
                        ComprasBean compra = new ComprasBean();
                        //convierte fecha String a Date
                        String fechaS = comprasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        compra.setFecha(util.stringToDateTime(fechaS));
                        compra.setIdProveedor(comprasJSON.getJSONObject(i)
                                .getInt("idProveedor"));
                        compra.setIdSucursal(comprasJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        compra.setIdUsuario(comprasJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        compra.setIdCompra(comprasJSON.getJSONObject(i)
                                .getInt("idCompra"));
                        compra.setObservaciones(comprasJSON.getJSONObject(i)
                                .getString("observaciones"));
                        compra.setFactura(comprasJSON.getJSONObject(i)
                                .getString("factura"));
                        compras.add(compra);
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
        return compras;
    }

//    public ArrayList<CategoriaBean> buscaCategoriaIdWS(String rutaWS) {
//        ArrayList<CategoriaBean> compras = new ArrayList();
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
//                    compras.add(cat);
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return compras;
//    }
    
}
