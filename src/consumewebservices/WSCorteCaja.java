package consumewebservices;

import beans.CajaChicaBean;
import beans.CategoriaBean;
import beans.ClienteBean;
import beans.CorteCajaBean;
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

public class WSCorteCaja {
    Util util = new Util();
    String cadena;
    //parametros con valores de usuario
    String fecha;
    String idUsuario;
    String idSucursal;
    String total;
    String tipoMov;
    String entregado;
    String idMov;
    
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public CorteCajaBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        CorteCajaBean corteCajaBean = null;
        switch (params[1]) { 
            case "1" : 
                idMov = params[2];
                fecha = params[3];
                idUsuario = params[4];
                idSucursal = params[5];
                total = params[6];
                tipoMov = params[7];
                entregado = params[8];
                corteCajaBean = insertaMovCorteCajaWS(); break;
        }
        return corteCajaBean;
    }
    
    public ArrayList<CorteCajaBean> ejecutaWebServiceObtieneCortes(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ArrayList<CorteCajaBean> cortesCaja = null;
        switch (params[1]) { 
            case "1" : 
                cortesCaja = obtieneCortesCajaWS(cadena); break;
            case "2" : 
                cortesCaja = obtieneCortesCajaPorFechasWS(cadena); break;
        }
        return cortesCaja;
    }
    
    
 
    public CorteCajaBean insertaMovCorteCajaWS(String... params) {
        CorteCajaBean inserta = null;
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
            jsonParam.put("idMov",idMov);
            if (fecha.length()==21) {
                // agrega 0 inicial si falta
                fecha = "0" + fecha;
            }
            fecha = fecha.substring(0, 19);
            fecha = util.cambiaFormatoFecha(fecha);
            Date a = util.stringToDateTime(fecha);
            String b = util.dateToDateTimeAsString(a);
            jsonParam.put("fecha", b);
            
//            jsonParam.put("fecha",fecha);
            jsonParam.put("idUsuario",idUsuario);
            jsonParam.put("idSucursal",idSucursal);
            jsonParam.put("total",total);
            jsonParam.put("tipoMov",tipoMov);
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
                    inserta = new CorteCajaBean();
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
    
    public ArrayList<CorteCajaBean> obtieneCortesCajaWS(String rutaWS) {
        ArrayList<CorteCajaBean> cortesCaja = new ArrayList();
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
                    JSONArray cortesCajaJSON = respuestaJSON.getJSONArray("cortesCaja");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<cortesCajaJSON.length();i++){
                        CorteCajaBean corteCaja = new CorteCajaBean();
                        //convierte fecha String a Date
                        String fechaS = cortesCajaJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        corteCaja.setFecha(util.stringToDateTime(fechaS));
                        
                        corteCaja.setIdCorte(cortesCajaJSON.getJSONObject(i)
                                .getInt("idCorte"));
                        corteCaja.setIdMov(cortesCajaJSON.getJSONObject(i)
                                .getInt("idMov"));
                        corteCaja.setIdSucursal(cortesCajaJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        corteCaja.setIdUsuario(cortesCajaJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        corteCaja.setTotal(cortesCajaJSON.getJSONObject(i)
                                .getDouble("total"));
                        corteCaja.setTipoMov(cortesCajaJSON.getJSONObject(i)
                                .getString("tipoMov"));
                        corteCaja.setEntregado(cortesCajaJSON.getJSONObject(i)
                                .getInt("entregado"));
                        cortesCaja.add(corteCaja);
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
        return cortesCaja;
    }
    
    public ArrayList<CorteCajaBean> obtieneCortesCajaPorFechasWS(String rutaWS) {
        ArrayList<CorteCajaBean> cortesCaja = new ArrayList();
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
                    JSONArray cortesCajaJSON = respuestaJSON.getJSONArray("cortesCaja");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<cortesCajaJSON.length();i++){
                        CorteCajaBean corteCaja = new CorteCajaBean();
                        //convierte fecha String a Date
                        String fechaS = cortesCajaJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        corteCaja.setFecha(util.stringToDateTime(fechaS));
                        
                        corteCaja.setIdCorte(cortesCajaJSON.getJSONObject(i)
                                .getInt("idCorte"));
                        corteCaja.setIdMov(cortesCajaJSON.getJSONObject(i)
                                .getInt("idMov"));
                        corteCaja.setIdSucursal(cortesCajaJSON.getJSONObject(i)
                                .getInt("idSucursal"));
                        corteCaja.setIdUsuario(cortesCajaJSON.getJSONObject(i)
                                .getInt("idUsuario"));
                        corteCaja.setTotal(cortesCajaJSON.getJSONObject(i)
                                .getDouble("total"));
                        corteCaja.setTipoMov(cortesCajaJSON.getJSONObject(i)
                                .getString("tipoMov"));
                        corteCaja.setEntregado(cortesCajaJSON.getJSONObject(i)
                                .getInt("entregado"));
                        cortesCaja.add(corteCaja);
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
        return cortesCaja;
    }

}
