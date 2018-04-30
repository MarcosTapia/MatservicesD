package consumewebservices;

import beans.ComprasBean;
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

public class WSCompras {
    Util util = new Util();
    String cadena;
    //parametros con valores de usuario
    String fecha;
    String idProveedor;
    String observaciones;
    String idUsuario;
    String factura;
    String idSucursal;    
    String idCompra;
    
    //parametros con valores de compra
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public ComprasBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        ComprasBean compraObj = null;
        switch (params[1]) { 
            case "1" : 
                compraObj = obtieneUltimoIdCompraWS(); break;
            case "2" : 
                idCompra = params[2];
                observaciones = params[3];
                idUsuario = params[4];
                idSucursal = params[5];
                compraObj = guardaCompraWS(); break;
        }
        return compraObj;
    }
 
    public ComprasBean obtieneUltimoIdCompraWS(String... params) {
        ComprasBean compra = null;
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
                        compra = new ComprasBean();
                        compra.setIdProveedor(comprasJSON.getJSONObject(i).getInt("idCliente"));
                        compra.setIdSucursal(comprasJSON.getJSONObject(i).getInt("idSucursal"));
                        compra.setIdUsuario(comprasJSON.getJSONObject(i).getInt("idUsuario"));
                        compra.setIdCompra(comprasJSON.getJSONObject(i).getInt("idVenta"));
                        compra.setObservaciones(comprasJSON.getJSONObject(i).getString("observaciones"));
                        compra.setFactura(comprasJSON.getJSONObject(i).getString("factura"));
                        
                        //convierte fecha String a Date
                        String fechaS = comprasJSON.getJSONObject(i).getString("fecha");
                        Util util = new Util();
                        compra.setFecha(util.stringToDateTime(fechaS));
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
        return compra;
    }
        
    public ComprasBean guardaCompraWS(String... params) {
        ComprasBean inserta = null;
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
            String fecha = util.dateToDateTimeAsString(new java.util.Date());
            jsonParam.put("fecha", fecha);
            jsonParam.put("codigoCliente", idProveedor);
            jsonParam.put("observaciones", observaciones);
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("factura", factura);
            jsonParam.put("idSucursal", idSucursal);
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
                    inserta = new ComprasBean();
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

//    public UsuarioBean modificaUsuarioWS(String... params) {
//        UsuarioBean modifica = null;
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
//            jsonParam.put("usuario", usuario);
//            jsonParam.put("clave", password);
//            jsonParam.put("permisos", permisos);
//            jsonParam.put("nombre", nombre);
//            jsonParam.put("apellido_paterno", apellido_paterno);
//            jsonParam.put("apellido_materno", apellido_materno);
//            jsonParam.put("telefono_casa", telefono_casa);
//            jsonParam.put("telefono_celular", telefono_celular);
//            jsonParam.put("idSucursal", idSucursal);
//            // Envio los parámetros post.
//            OutputStream os = urlConn.getOutputStream();
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
//                    modifica = new UsuarioBean();
//                } else if (resultJSON == 2) {
////                    devuelve = "El alumno no pudo actualizarse";
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return modifica;
//    }
//
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
