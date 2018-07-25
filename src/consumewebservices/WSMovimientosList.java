package consumewebservices;

import beans.MovimientosBean;
import beans.ProductoBean;
import beans.UsuarioBean;
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
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSMovimientosList {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

        public ArrayList<MovimientosBean> ejecutaWebService(String... params) {
            cadena = params[0];
            url = null; // Url de donde queremos obtener información
            devuelve ="";
            ArrayList<MovimientosBean> movimientos = new ArrayList();
            switch (params[1]) { 
                case "1" : movimientos = mostrarMovimientosWS(); break;
                case "2" : movimientos = obtieneMovPorIdCodigoWS(cadena); break;
                case "3" : movimientos = obtieneMovPorFechasWS(); break;
                case "4" : movimientos = obtieneMovPorIdArticuloWS(cadena); break;
//                case "4" : productos = buscaProductoNombreWS(cadena); break;
//                case "5" : productos = buscaProductoIdWS(cadena); break;
            }
            return movimientos;
        }
 
    public ArrayList<MovimientosBean> mostrarMovimientosWS() {
        ArrayList<MovimientosBean> movimientos = new ArrayList();
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
                    JSONArray movimientosJSON = respuestaJSON.getJSONArray("movimientos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<movimientosJSON.length();i++){
                        MovimientosBean movimiento = new MovimientosBean();
                        movimiento.setIdMovimiento(movimientosJSON.getJSONObject(i).getInt("idMovimiento"));
                        movimiento.setIdArticulo(movimientosJSON.getJSONObject(i).getInt("idArticulo"));
                        movimiento.setIdUsuario(movimientosJSON.getJSONObject(i).getInt("idUsuario"));
                        movimiento.setTipoOperacion(movimientosJSON.getJSONObject(i).getString("tipoOperacion"));
                        movimiento.setCantidad(movimientosJSON.getJSONObject(i).getDouble("cantidad"));

                        //convierte fecha String a Date
                        String fechaS = movimientosJSON.getJSONObject(i).get("fechaOperacion").toString();
                        Util util = new Util();
                        Date fech = util.stringToDateTime(fechaS);
                        movimiento.setFechaOperacion(fech);
                        movimiento.setIdSucursal(movimientosJSON.getJSONObject(i).getInt("idSucursal"));
                        movimiento.setExistenciaAnterior(movimientosJSON.getJSONObject(i).getDouble("existenciaAnterior"));
                        movimiento.setExistenciaActual(movimientosJSON.getJSONObject(i).getDouble("existenciaActual"));
                        movimientos.add(movimiento);
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
        return movimientos;
    }
        
        
    public ArrayList<MovimientosBean> obtieneMovPorIdCodigoWS(String rutaWS) {
        ArrayList<MovimientosBean> movimientos = new ArrayList();
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
                    JSONArray movimientosJSON = respuestaJSON.getJSONArray("movimientos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<movimientosJSON.length();i++){
                        MovimientosBean movimiento = new MovimientosBean();
                        movimiento.setIdMovimiento(movimientosJSON.getJSONObject(i).getInt("idMovimiento"));
                        movimiento.setIdArticulo(movimientosJSON.getJSONObject(i).getInt("idArticulo"));
                        movimiento.setIdUsuario(movimientosJSON.getJSONObject(i).getInt("idUsuario"));
                        movimiento.setTipoOperacion(movimientosJSON.getJSONObject(i).getString("tipoOperacion"));
                        movimiento.setCantidad(movimientosJSON.getJSONObject(i).getDouble("cantidad"));

                        //convierte fecha String a Date
                        String fechaS = movimientosJSON.getJSONObject(i).get("fechaOperacion").toString();
                        Util util = new Util();
                        movimiento.setFechaOperacion(util.stringToDate(fechaS));
                        movimiento.setIdSucursal(movimientosJSON.getJSONObject(i).getInt("idSucursal"));
                        movimiento.setExistenciaAnterior(movimientosJSON.getJSONObject(i).getDouble("existenciaAnterior"));
                        movimiento.setExistenciaActual(movimientosJSON.getJSONObject(i).getDouble("existenciaActual"));
                        movimientos.add(movimiento);
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
        return movimientos;
    }

    public ArrayList<MovimientosBean> obtieneMovPorFechasWS(String... params) {
        ArrayList<MovimientosBean> movimientos = new ArrayList();
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
                    JSONArray movimientosJSON = respuestaJSON.getJSONArray("movimientos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<movimientosJSON.length();i++){
                        MovimientosBean movimiento = new MovimientosBean();
                        movimiento.setIdMovimiento(movimientosJSON.getJSONObject(i).getInt("idMovimiento"));
                        movimiento.setIdArticulo(movimientosJSON.getJSONObject(i).getInt("idArticulo"));
                        movimiento.setIdUsuario(movimientosJSON.getJSONObject(i).getInt("idUsuario"));
                        movimiento.setTipoOperacion(movimientosJSON.getJSONObject(i).getString("tipoOperacion"));
                        movimiento.setCantidad(movimientosJSON.getJSONObject(i).getDouble("cantidad"));

                        //convierte fecha String a Date
                        String fechaS = movimientosJSON.getJSONObject(i).get("fechaOperacion").toString();
                        Util util = new Util();
                        movimiento.setFechaOperacion(util.stringToDateTime(fechaS));
                        movimiento.setIdSucursal(movimientosJSON.getJSONObject(i).getInt("idSucursal"));
                        movimiento.setExistenciaAnterior(movimientosJSON.getJSONObject(i).getDouble("existenciaAnterior"));
                        movimiento.setExistenciaActual(movimientosJSON.getJSONObject(i).getDouble("existenciaActual"));
                        movimientos.add(movimiento);
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
        return movimientos;
    }
    
    public ArrayList<MovimientosBean> obtieneMovPorIdArticuloWS(String rutaWS) {
        ArrayList<MovimientosBean> movimientos = new ArrayList();
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
                    JSONArray movimientosJSON = respuestaJSON.getJSONArray("movimientos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<movimientosJSON.length();i++){
                        MovimientosBean movimiento = new MovimientosBean();
                        movimiento.setIdMovimiento(movimientosJSON.getJSONObject(i).getInt("idMovimiento"));
                        movimiento.setIdArticulo(movimientosJSON.getJSONObject(i).getInt("idArticulo"));
                        movimiento.setIdUsuario(movimientosJSON.getJSONObject(i).getInt("idUsuario"));
                        movimiento.setTipoOperacion(movimientosJSON.getJSONObject(i).getString("tipoOperacion"));
                        movimiento.setCantidad(movimientosJSON.getJSONObject(i).getDouble("cantidad"));

                        //convierte fecha String a Date
                        String fechaS = movimientosJSON.getJSONObject(i).get("fechaOperacion").toString();
                        Util util = new Util();
                        movimiento.setFechaOperacion(util.stringToDate(fechaS));
                        movimiento.setIdSucursal(movimientosJSON.getJSONObject(i).getInt("idSucursal"));
                        movimiento.setExistenciaAnterior(movimientosJSON.getJSONObject(i).getDouble("existenciaAnterior"));
                        movimiento.setExistenciaActual(movimientosJSON.getJSONObject(i).getDouble("existenciaActual"));
                        movimientos.add(movimiento);
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
        return movimientos;
    }
    
}
