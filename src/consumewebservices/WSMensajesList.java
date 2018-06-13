package consumewebservices;

import beans.CategoriaBean;
import beans.ClienteBean;
import beans.CorteCajaBean;
import beans.MensajeBean;
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
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSMensajesList {
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

    String mensaje;
    String remitente;
    String destinatario;
    String titulo;
    String negocio;
    
    
    public ArrayList<MensajeBean> ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ArrayList<MensajeBean> mensajes = new ArrayList();
        switch (params[1]) { 
            case "1" : 
                mensajes = obtenerMensajesWS(); break;
            case "2" : 
                mensajes = busquedaMensajesPorFechasWS(); break;
            case "3" : 
                mensajes = buscaMensajeIdWS(cadena); break;
            case "4" : 
                mensajes = obtenerFechaServidorWS(); break;
//            case "2" : mensajesJSON = buscaCategoriaIdWS(cadena); break;
//            case "3" : mensajesJSON = busquedaMensajesPorFechasWS(cadena); break;
//            case "4" : mensajes = busquedaCategoriasPorNombreWS(cadena); break;//            case "2" : mensajes = buscaCategoriaIdWS(cadena); break;
//            case "3" : mensajes = busquedaMensajesPorFechasWS(cadena); break;
//            case "4" : mensajesJSON = busquedaCategoriasPorNombreWS(cadena); break;
        }
        return mensajes;
    }
    
    public boolean ejecutaWebServiceEnviaCorreo(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        boolean enviado = false;
        switch (params[1]) { 
            case "1" : 
                mensaje = params[2];
                remitente = params[3];
                destinatario = params[4];
                titulo = params[5];
                negocio = params[6];
                enviado = enviaCorreoWS(); break;
        }
        return enviado;
    }
    
 
    public ArrayList<MensajeBean> obtenerMensajesWS() {
        ArrayList<MensajeBean> mensajes = new ArrayList();
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
                    JSONArray mensajesJSON = respuestaJSON.getJSONArray("mensajes");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<mensajesJSON.length();i++){
                        MensajeBean mensaje = new MensajeBean();
                        //convierte fecha String a Date
                        String fechaS = mensajesJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        mensaje.setFecha(util.stringToDateTime(fechaS));
                        mensaje.setIdMensaje(mensajesJSON.getJSONObject(i)
                                .getInt("idMensaje"));
                        mensaje.setMensaje(mensajesJSON.getJSONObject(i)
                                .getString("mensaje"));
                        mensajes.add(mensaje);
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
        return mensajes;
    }
        
//    public ArrayList<CategoriaBean> busquedaCategoriasPorNombreWS(String rutaWS) {
//        ArrayList<CategoriaBean> mensajesJSON = new ArrayList();
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
//                        mensajesJSON.add(cat);
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
//        return mensajesJSON;
//    }
        
    public ArrayList<MensajeBean> busquedaMensajesPorFechasWS(String... params) {
        ArrayList<MensajeBean> mensajes = new ArrayList();
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
                    JSONArray mensajesJSON = respuestaJSON.getJSONArray("mensajes");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<mensajesJSON.length();i++){
                        MensajeBean mensaje = new MensajeBean();
                        //convierte fecha String a Date
                        String fechaS = mensajesJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        mensaje.setFecha(util.stringToDateTime(fechaS));
                        mensaje.setIdMensaje(mensajesJSON.getJSONObject(i)
                                .getInt("idMensaje"));
                        mensaje.setMensaje(mensajesJSON.getJSONObject(i)
                                .getString("mensaje"));
                        mensajes.add(mensaje);
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
        return mensajes;
    }

    public ArrayList<MensajeBean> buscaMensajeIdWS(String rutaWS) {
        ArrayList<MensajeBean> mensajes = new ArrayList();
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
                    MensajeBean mensaje = new MensajeBean();
                    mensaje.setIdMensaje(respuestaJSON.getJSONObject("mensaje").getInt("idMensaje"));
                    mensaje.setMensaje(respuestaJSON.getJSONObject("mensaje").getString("mensaje"));
                    String fechaS = respuestaJSON.getJSONObject("mensaje").get("fecha").toString();
                    Util util = new Util();
                    mensaje.setFecha(util.stringToDateTime(fechaS));
                    mensajes.add(mensaje);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public ArrayList<MensajeBean> obtenerFechaServidorWS() {
        ArrayList<MensajeBean> mensajes = new ArrayList();
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
                    JSONArray mensajesJSON = respuestaJSON.getJSONArray("fechaServidor");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<mensajesJSON.length();i++){
                        MensajeBean mensaje = new MensajeBean();
                        //convierte fecha String a Date
                        String fechaS = mensajesJSON.getJSONObject(i).get("SYSDATE()").toString();
                        Util util = new Util();
                        mensaje.setFecha(util.stringToDateTime(fechaS));
                        mensajes.add(mensaje);
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
        return mensajes;
    }

    public boolean enviaCorreoWS() {
        boolean enviado = false;
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
            jsonParam.put("mensaje",mensaje);
            jsonParam.put("remitente",remitente);
            jsonParam.put("destinatario",destinatario);
            jsonParam.put("titulo",titulo);
            jsonParam.put("negocio",negocio);
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
                if (resultJSON == 1) {      // hay sucursal que mostrar
                    enviado = true;
                } else if (resultJSON == 2) {
                    enviado = false;
                }
            } else {
                enviado = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return enviado;
    }
    
    
}
