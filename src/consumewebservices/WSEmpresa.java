package consumewebservices;

import beans.UsuarioBean;
import beans.ConfiguracionBean;
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

public class WSEmpresa {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    ConfiguracionBean configuracionBean = new ConfiguracionBean();
    

//        public usuarioBean ejecutaWebService(String... params) {
//            return 
//        }
    
    
    public ConfiguracionBean  ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        switch (params[1]) { 
            case "1" : configuracionBean = obtieneDatosEmpresaWS(); break;
        }
        return configuracionBean;
    }
 
    public ConfiguracionBean obtieneDatosEmpresaWS() {
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
                        configuracionBean.setNombreEmpresa(datosEmpresaJSON.getJSONObject(i).getString("nombreEmpresa"));
                    }
                }
                else if (resultJSON == 2){
                    configuracionBean.setNombreEmpresa("No hay datos de la empresa registrados");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return configuracionBean;
    }
       
        
}
