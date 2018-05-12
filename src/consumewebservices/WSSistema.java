package consumewebservices;

import beans.UsuarioBean;
import beans.DatosEmpresaBean;
import beans.SistemaBean;
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

public class WSSistema {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    SistemaBean sistemaBean = new SistemaBean();
    String ivaEmpresa;
    String historicoProveedores;
    String criterioHistoricoProveedores;
    String camposInventario;
    String camposVentas;
    String camposCompras;
    String camposConsultas;
    String camposProveedores;
    String camposClientes;
    String camposEmpleados;
    String camposEmpresa;
    String ivaGral;
    
    public SistemaBean  ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        switch (params[1]) { 
            case "1" : sistemaBean = obtieneDatosSistemaWS(); break;
            case "2" : 
                ivaEmpresa = params[2];
                historicoProveedores = params[3];
                criterioHistoricoProveedores = params[4];
                camposInventario = params[5];
                camposVentas = params[6];
                camposCompras = params[7];
                camposConsultas = params[8];
                camposProveedores = params[9];
                camposClientes = params[10];
                camposEmpleados = params[11];
                camposEmpresa = params[12];
                ivaGral = params[13];
                sistemaBean = modificaDatosSistemaWS(); break;
        }
        return sistemaBean;
    }
 
    public SistemaBean obtieneDatosSistemaWS() {
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
                    JSONArray datosSistemaJSON = respuestaJSON.getJSONArray("sistemas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<datosSistemaJSON.length();i++){
                        sistemaBean.setIdSistema(datosSistemaJSON.getJSONObject(i).getInt("idSistema"));
                        sistemaBean.setIvaEmpresa(datosSistemaJSON.getJSONObject(i).getDouble("ivaEmpresa"));
                        sistemaBean.setHistoricoProveedores(datosSistemaJSON.getJSONObject(i).getString("historicoProveedores"));
                        sistemaBean.setCriterioHistoricoProveedores(datosSistemaJSON.getJSONObject(i).getString("criterioHistoricoProveedores"));
                        sistemaBean.setCamposInventario(datosSistemaJSON.getJSONObject(i).getString("camposInventario"));
                        sistemaBean.setCamposVentas(datosSistemaJSON.getJSONObject(i).getString("camposVentas"));
                        sistemaBean.setCamposCompras(datosSistemaJSON.getJSONObject(i).getString("camposCompras"));
                        sistemaBean.setCamposConsultas(datosSistemaJSON.getJSONObject(i).getString("camposConsultas"));
                        sistemaBean.setCamposProveedores(datosSistemaJSON.getJSONObject(i).getString("camposProveedores"));
                        sistemaBean.setCamposClientes(datosSistemaJSON.getJSONObject(i).getString("camposClientes"));
                        sistemaBean.setCamposEmpleados(datosSistemaJSON.getJSONObject(i).getString("camposEmpleados"));
                        sistemaBean.setCamposEmpresa(datosSistemaJSON.getJSONObject(i).getString("camposEmpresa"));
                        sistemaBean.setIvaGral(datosSistemaJSON.getJSONObject(i).getDouble("ivaGral"));
                    }     
                }
                else if (resultJSON == 2){
                    sistemaBean.setCamposEmpresa("No hay datos de la empresa registrados");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sistemaBean;
    }
       
    public SistemaBean modificaDatosSistemaWS(String... params) {
        SistemaBean modifica = null;
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
            jsonParam.put("idSistema","1");
            jsonParam.put("ivaEmpresa",ivaEmpresa);
            jsonParam.put("historicoProveedores",historicoProveedores);
            jsonParam.put("criterioHistoricoProveedores",criterioHistoricoProveedores);
            jsonParam.put("camposInventario",camposInventario);
            jsonParam.put("camposVentas",camposVentas);
            jsonParam.put("camposCompras",camposCompras);
            jsonParam.put("camposConsultas",camposConsultas);
            jsonParam.put("camposProveedores",camposProveedores);
            jsonParam.put("camposClientes",camposClientes);
            jsonParam.put("camposEmpleados",camposEmpleados);
            jsonParam.put("camposEmpresa",camposEmpresa);
            jsonParam.put("ivaGral",ivaGral);
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
                    modifica = new SistemaBean();
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
