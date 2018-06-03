package consumewebservices;

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
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSVentas {
    Util util = new Util();
    String cadena;
    //parametros con valores de usuario
    String fecha;
    String idCliente;
    String observaciones;
    String idUsuario;
    String idSucursal;    
    String subtotal;
    String iva;
    String total;
    String tipovta;
    String cancelada;
    String facturada;
    String idFactura;
    String idVenta;
    
    //parametros con valores de venta
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public VentasBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        VentasBean ventaObj = null;
        switch (params[1]) { 
            case "1" : 
                ventaObj = obtieneUltimoIdVentaWS(); break;
            case "2" : 
                idCliente = params[2];
                observaciones = params[3];
                idUsuario = params[4];
                idSucursal = params[5];
                subtotal = params[6];
                iva = params[7];
                total = params[8];
                tipovta = params[9];
                cancelada = params[10];
                facturada = params[11];
                idFactura = params[12];
                ventaObj = guardaVentaWS(); break;
            case "3" : 
                idVenta = params[2];
                fecha = params[3];
                idCliente = params[4];
                observaciones = params[5];
                idUsuario = params[6];
                idSucursal = params[7];
                subtotal = params[8];
                iva = params[9];
                total = params[10];
                tipovta = params[11];
                cancelada = params[12];
                facturada = params[13];
                idFactura = params[14];
                ventaObj = modificaVentaWS(); break;
        }
        return ventaObj;
    }
 
    public VentasBean obtieneUltimoIdVentaWS(String... params) {
        VentasBean venta = null;
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
                        venta = new VentasBean();
                        venta.setIdCliente(ventasJSON.getJSONObject(i).getInt("idCliente"));
                        venta.setIdSucursal(ventasJSON.getJSONObject(i).getInt("idSucursal"));
                        venta.setIdUsuario(ventasJSON.getJSONObject(i).getInt("idUsuario"));
                        venta.setIdVenta(ventasJSON.getJSONObject(i).getInt("idVenta"));
                        venta.setObservaciones(ventasJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = ventasJSON.getJSONObject(i).getString("fecha");
                        Util util = new Util();
                        venta.setFecha(util.stringToDateTime(fechaS));
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
        return venta;
    }
        
    public VentasBean guardaVentaWS(String... params) {
        VentasBean inserta = null;
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
            
//        java.util.Date dt = new java.util.Date();
//        java.text.SimpleDateFormat sdf = 
//            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentTime = sdf.format(dt);
            String fecha = util.dateToDateTimeAsString(util.obtieneFechaServidor());
            jsonParam.put("fecha", fecha);
            jsonParam.put("codigoCliente", idCliente);
            jsonParam.put("observaciones", observaciones);
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("idSucursal", idSucursal);
            
            jsonParam.put("subtotal", subtotal);
            jsonParam.put("iva", iva);
            jsonParam.put("total", total);
            jsonParam.put("tipovta", tipovta);
            jsonParam.put("cancelada", cancelada);
            jsonParam.put("facturada", facturada);
            jsonParam.put("idFactura", idFactura);
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
                    inserta = new VentasBean();
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

    public VentasBean modificaVentaWS(String... params) {
        VentasBean modifica = null;
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
            jsonParam.put("idVenta", idVenta);
            String fecha = util.dateToDateTimeAsString(new java.util.Date());
            jsonParam.put("fecha", fecha);
            jsonParam.put("idCliente", idCliente);
            jsonParam.put("observaciones", observaciones);
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("idSucursal", idSucursal);
            jsonParam.put("subtotal", subtotal);
            jsonParam.put("iva", iva);
            jsonParam.put("total", total);
            jsonParam.put("tipovta", tipovta);
            jsonParam.put("cancelada", cancelada);
            jsonParam.put("facturada", facturada);
            jsonParam.put("idFactura", idFactura);
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
                    modifica = new VentasBean();
                } else if (resultJSON == 2) {
//                    devuelve = "El alumno no pudo actualizarse";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modifica;
    }

//    public UsuarioBean eliminaUsuarioWS(String... params) {
//        UsuarioBean elimina = null;
//        try {
//            HttpURLConnection urlConn;
//            DataOutputStream printout;
//            DataInputStream input;
//            url = new URL(cadena);
//            urlConn = (HttpURLConnection) url.openConnection();
//            urlConn.setDoInput(true);
//            urlConn.setDoOutput(true);
//            urlConn.setUseCaches(false);
//            urlConn.setRequestProperty("Content-Type", "application/json");
//            urlConn.setRequestProperty("Accept", "application/json");
//            urlConn.connect();
//            //Creo el Objeto JSON
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("idUsuario", idUsuario);
//            // Envio los parámetros post.
//           OutputStream os = urlConn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(jsonParam.toString());
//            writer.flush();
//            writer.close();
//            int respuesta = urlConn.getResponseCode();
//            StringBuilder result = new StringBuilder();
//            if (respuesta == HttpURLConnection.HTTP_OK) {
//                String line;
//                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//                while ((line=br.readLine()) != null) {
//                    result.append(line);
//                    //response+=line;
//                }
//                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
//                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
//                //Accedemos al vector de resultados
//                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
//                if (resultJSON == 1) {      // hay un alumno que mostrar
//                    elimina = new UsuarioBean();
//                } else if (resultJSON == 2) {
//                    devuelve = "No hay alumnos";
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return elimina;
//    }
    
}
