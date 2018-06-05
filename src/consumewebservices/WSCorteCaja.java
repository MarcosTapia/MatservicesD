package consumewebservices;

import beans.CajaChicaBean;
import beans.CategoriaBean;
import beans.ClienteBean;
import beans.CorteCajaBean;
import beans.SucursalBean;
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
                fecha = params[2];
                idUsuario = params[3];
                idSucursal = params[4];
                total = params[5];
                tipoMov = params[6];
                corteCajaBean = insertaMovCorteCajaWS(); break;
        }
        return corteCajaBean;
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

}
