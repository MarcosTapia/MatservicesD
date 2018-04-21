package consumewebservices;

import beans.ClienteBean;
import beans.ProveedorBean;
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
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static vistas.Ingreso.usuario;

public class WSProveedores {
    String cadena;
    //parametros con valores de usuario
    String empresa;
    String nombre;
    String apellidos;
    String telefono_casa;
    String telefono_celular;
    String direccion1;
    String direccion2;
    String rfc;
    String email;
    String ciudad;
    String estado;
    String cp;
    String pais;
    String comentarios;
    String noCuenta;
    String idProveedor;
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public ProveedorBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ProveedorBean proveedorObj = null;
        switch (params[1]) { 
            case "1" : 
                empresa = params[2];
                nombre = params[3];
                apellidos = params[4];
                telefono_casa = params[5];
                telefono_celular = params[6];
                direccion1 = params[7];
                direccion2 = params[8];
                rfc = params[9];
                email = params[10];
                ciudad = params[11];
                estado = params[12];
                cp = params[13];
                pais = params[14];
                comentarios = params[15];
                noCuenta = params[16];
                proveedorObj = insertaProveedorWS(); break;
            case "2" : 
                idProveedor = params[2];
                empresa = params[3];
                nombre = params[4];
                apellidos = params[5];
                telefono_casa = params[6];
                telefono_celular = params[7];
                direccion1 = params[8];
                direccion2 = params[9];
                rfc = params[10];
                email = params[11];
                ciudad = params[12];
                estado = params[13];
                cp = params[14];
                pais = params[15];
                comentarios = params[16];
                noCuenta = params[17];
                proveedorObj = modificaProveedorWS(); break;
            case "3" : 
                idProveedor = params[2];
                proveedorObj = eliminaProveedorWS(); break;
        }
        return proveedorObj;
    }
 
    public ProveedorBean insertaProveedorWS(String... params) {
        ProveedorBean inserta = null;
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
            jsonParam.put("empresa",empresa);
            jsonParam.put("nombre",nombre);
            jsonParam.put("apellidos",apellidos);
            jsonParam.put("telefono_casa",telefono_casa);
            jsonParam.put("telefono_celular",telefono_celular);
            jsonParam.put("direccion1",direccion1);
            jsonParam.put("direccion2",direccion2);
            jsonParam.put("rfc",rfc);
            jsonParam.put("email",email);
            jsonParam.put("ciudad",ciudad);
            jsonParam.put("estado",estado);
            jsonParam.put("cp",cp);
            jsonParam.put("pais",pais);
            jsonParam.put("comentarios",comentarios);
            jsonParam.put("noCuenta",noCuenta);
            
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
                    inserta = new ProveedorBean();
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

    public ProveedorBean modificaProveedorWS(String... params) {
        ProveedorBean modifica = null;
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
            jsonParam.put("idProveedor",idProveedor);
            jsonParam.put("empresa",empresa);
            jsonParam.put("nombre",nombre);
            jsonParam.put("apellidos",apellidos);
            jsonParam.put("telefono_casa",telefono_casa);
            jsonParam.put("telefono_celular",telefono_celular);
            jsonParam.put("direccion1",direccion1);
            jsonParam.put("direccion2",direccion2);
            jsonParam.put("rfc",rfc);
            jsonParam.put("email",email);
            jsonParam.put("ciudad",ciudad);
            jsonParam.put("estado",estado);
            jsonParam.put("cp",cp);
            jsonParam.put("pais",pais);
            jsonParam.put("comentarios",comentarios);
            jsonParam.put("noCuenta",noCuenta);
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
                    modifica = new ProveedorBean();
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

    public ProveedorBean eliminaProveedorWS(String... params) {
        ProveedorBean elimina = null;
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
            jsonParam.put("idProveedor", idProveedor);
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
                    elimina = new ProveedorBean();
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
