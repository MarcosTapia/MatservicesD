package consumewebservices;

import beans.UsuarioBean;
import beans.DatosEmpresaBean;
import beans.SucursalBean;
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
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static vistas.Ingreso.usuario;

public class WSDatosEmpresa {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    DatosEmpresaBean datosEmpresaBean = new DatosEmpresaBean();
    
    String nombreEmpresa;
    String rfcEmpresa;
    String emailEmpresa;
    String direccionEmpresa;
    String cpEmpresa;
    String telEmpresa;
    String estadoEmpresa;
    String ciudadEmpresa;
    String paisEmpresa;
    
    public DatosEmpresaBean  ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        switch (params[1]) { 
            case "1" : datosEmpresaBean = obtieneDatosEmpresaWS(); break;
            case "2" : 
                nombreEmpresa = params[2];
                rfcEmpresa = params[3];
                emailEmpresa = params[4];
                direccionEmpresa = params[5];
                cpEmpresa = params[6];
                telEmpresa = params[7];
                estadoEmpresa = params[8];
                ciudadEmpresa = params[9];
                paisEmpresa = params[10];
                datosEmpresaBean = modificaDatosEmpresaWS(); break;
        }
        return datosEmpresaBean;
    }
 
    public DatosEmpresaBean obtieneDatosEmpresaWS() {
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
                if (resultJSON == 1) {      // hay alumnos a mostrar
                    JSONArray datosEmpresaJSON = respuestaJSON.getJSONArray("datosEmpresas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<datosEmpresaJSON.length();i++){
                        datosEmpresaBean.setIdEmpresa(datosEmpresaJSON.getJSONObject(i).getInt("idEmpresa"));
                        datosEmpresaBean.setNombreEmpresa(datosEmpresaJSON.getJSONObject(i).getString("nombreEmpresa"));
                        datosEmpresaBean.setRfcEmpresa(datosEmpresaJSON.getJSONObject(i).getString("rfcEmpresa"));
                        datosEmpresaBean.setDireccionEmpresa(datosEmpresaJSON.getJSONObject(i).getString("direccionEmpresa"));
                        datosEmpresaBean.setEmailEmpresa(datosEmpresaJSON.getJSONObject(i).getString("emailEmpresa"));
                        datosEmpresaBean.setTelEmpresa(datosEmpresaJSON.getJSONObject(i).getString("telEmpresa"));
                        datosEmpresaBean.setCpEmpresa(datosEmpresaJSON.getJSONObject(i).getString("cpEmpresa"));
                        datosEmpresaBean.setCiudadEmpresa(datosEmpresaJSON.getJSONObject(i).getString("ciudadEmpresa"));
                        datosEmpresaBean.setEstadoEmpresa(datosEmpresaJSON.getJSONObject(i).getString("estadoEmpresa"));
                        datosEmpresaBean.setPaisEmpresa(datosEmpresaJSON.getJSONObject(i).getString("paisEmpresa"));
                    }     
                }
                else if (resultJSON == 2){
                    datosEmpresaBean.setNombreEmpresa("No hay datos de la empresa registrados");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datosEmpresaBean;
    }
       
    public DatosEmpresaBean modificaDatosEmpresaWS(String... params) {
        DatosEmpresaBean modifica = null;
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
            jsonParam.put("idEmpresa","1");
            jsonParam.put("nombreEmpresa",nombreEmpresa);
            jsonParam.put("rfcEmpresa",rfcEmpresa);
            jsonParam.put("direccionEmpresa",direccionEmpresa);
            jsonParam.put("emailEmpresa",emailEmpresa);
            jsonParam.put("telEmpresa",telEmpresa);
            jsonParam.put("cpEmpresa",cpEmpresa);
            jsonParam.put("ciudadEmpresa",ciudadEmpresa);
            jsonParam.put("estadoEmpresa",estadoEmpresa);
            jsonParam.put("paisEmpresa",paisEmpresa);
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
                    modifica = new DatosEmpresaBean();
                } else if (resultJSON == 2) {
//                    devuelve = "La sucursal no pudo actualizarse";
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
        
}
