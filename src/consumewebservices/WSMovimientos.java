package consumewebservices;

import beans.MovimientosBean;
import beans.ProductoBean;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSMovimientos {
    String cadena;
    //parametros con valores de producto
    String idArticulo;
    String idUsuario;
    String tipoOperacion;
    String cantidad;
    String fechaIngreso;
    String idMovimiento;
    String idSucursal;
    String existenciaAnterior;
    String existenciaActual;
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public MovimientosBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        MovimientosBean movimientoObj = null;
        switch (params[1]) { 
            case "1" : 
                idArticulo = params[2];
                idUsuario = params[3];
                tipoOperacion = params[4];
                cantidad = params[5];
                fechaIngreso = params[6];
                idSucursal = params[7];
                existenciaAnterior = params[8];
                existenciaActual = params[9];
                movimientoObj = insertaMovimientoWS(); 
                break;
            case "2" : 
                movimientoObj = obtieneUltimoIdMovimientosWS(); 
                break;
            case "3" : 
                idMovimiento = params[2];
                idUsuario = params[3];
                movimientoObj = eliminaMovimientoPorUsuarioWS(); 
                break;
//            case "4" : 
//                productoObj = buscaProdPorCodigoWS(cadena); 
//                break;
        }
        return movimientoObj;
    }
 
    public MovimientosBean insertaMovimientoWS(String... params) {
        MovimientosBean inserta = null;
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idArticulo", idArticulo);
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("tipoOperacion", tipoOperacion);
            jsonParam.put("cantidad", cantidad);
            jsonParam.put("fechaOperacion", fechaIngreso);
            jsonParam.put("idSucursal", idSucursal);
            jsonParam.put("existenciaAnterior", existenciaAnterior);
            jsonParam.put("existenciaActual", existenciaActual);
            
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                    //response+=line;
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON == 1) {      // hay un alumno que mostrar
                    inserta = new MovimientosBean();
                } else if (resultJSON == 2) {
//                    inserta = false;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inserta;
    }

    public MovimientosBean obtieneUltimoIdMovimientosWS(String... params) {
        MovimientosBean movimiento = null;
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
                        movimiento = new MovimientosBean();
                        movimiento.setCantidad(movimientosJSON.getJSONObject(i).getDouble("cantidad"));
                        //convierte fecha String a Date
                        String fechaS = movimientosJSON.getJSONObject(i).getString("fechaOperacion");
                        Util util = new Util();
                        movimiento.setFechaOperacion(util.stringToDateTime(fechaS));
                        movimiento.setIdArticulo(movimientosJSON.getJSONObject(i).getInt("idArticulo"));
                        movimiento.setIdMovimiento(movimientosJSON.getJSONObject(i).getInt("idMovimiento"));
                        //movimiento.setIdProveedor(movimientosJSON.getJSONObject(i).getInt("idProveedor"));
                        movimiento.setIdSucursal(movimientosJSON.getJSONObject(i).getInt("idSucursal"));
                        movimiento.setIdUsuario(movimientosJSON.getJSONObject(i).getInt("idUsuario"));
                        movimiento.setTipoOperacion(movimientosJSON.getJSONObject(i).getString("tipoOperacion"));
                        break;
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
        return movimiento;
    }
    
    public MovimientosBean eliminaMovimientoPorUsuarioWS(String... params) {
        MovimientosBean elimina = null;
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idMovimiento", idMovimiento);
            jsonParam.put("idUsuario", idUsuario);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                    //response+=line;
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON == 1) {      // hay una venta que mostrar
                    elimina = new MovimientosBean();
                } else if (resultJSON == 2) {
                    devuelve = "No hay alumnos";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return elimina;
    }
    
    
}
