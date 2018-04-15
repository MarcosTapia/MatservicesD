package consumewebservices;

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

public class WSClientes {
    String cadena;
    //parametros con valores de usuario
    String usuario;
    String password;
    String permisos;
    String nombre;
    String apellido_paterno;
    String apellido_materno;
    String telefono_casa;
    String telefono_celular;
    String idSucursal;
    String idUsuario;
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public UsuarioBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        UsuarioBean usuarioObj = null;
        switch (params[1]) { 
            case "1" : devuelve = mostrarUsuariosWS(); break;
            case "2" : usuarioObj = verificaUsuarioWS(); break;
            case "3" : 
                usuario = params[2];
                password = params[3];
                permisos = params[4];
                nombre = params[5];
                apellido_paterno = params[6];
                apellido_materno = params[7];
                telefono_casa = params[8];
                telefono_celular = params[9];
                idSucursal = params[10];
                usuarioObj = insertaUsuarioWS(); break;
            case "4" : 
                idUsuario = params[2];
                usuario = params[3];
                password = params[4];
                permisos = params[5];
                nombre = params[6];
                apellido_paterno = params[7];
                apellido_materno = params[8];
                telefono_casa = params[9];
                telefono_celular = params[10];
                idSucursal = params[11];
                usuarioObj = modificaUsuarioWS(); break;
            case "5" : 
                idUsuario = params[2];
                usuarioObj = eliminaUsuarioWS(); break;
        }
        return usuarioObj;
    }
 
    public String mostrarUsuariosWS() {
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
                    JSONArray usuariosJSON = respuestaJSON.getJSONArray("usuarios");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<usuariosJSON.length();i++){
//                                devuelve = devuelve + usuariosJSON.getJSONObject(i).getString("idusuario") + " " +
//                                        usuariosJSON.getJSONObject(i).getString("nombre") + " " +
//                                        usuariosJSON.getJSONObject(i).getString("usuario") + "\n";
                        devuelve = devuelve + usuariosJSON.getJSONObject(i).getString("nombre") + "\n";
                    }
                }
                else if (resultJSON == 2){
                    devuelve = "No hay usuarios";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }
        
    public UsuarioBean verificaUsuarioWS(String... params) {
        UsuarioBean usuario = null;
        cadena = params[0];
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
                    usuario = new UsuarioBean();
                    usuario.setIdUsuario(respuestaJSON.getJSONObject("usuario").getInt("idUsuario"));
                    usuario.setNombre(respuestaJSON.getJSONObject("usuario").getString("nombre"));
                    usuario.setUsuario(respuestaJSON.getJSONObject("usuario").getString("usuario"));
                    usuario.setApellido_materno(respuestaJSON.getJSONObject("usuario").getString("apellido_materno"));
                    usuario.setApellido_paterno(respuestaJSON.getJSONObject("usuario").getString("apellido_paterno"));
                    usuario.setPermisos(respuestaJSON.getJSONObject("usuario").getString("permisos"));
                    usuario.setClase(respuestaJSON.getJSONObject("usuario").getString("permisos"));
                    usuario.setIdSucursal(respuestaJSON.getJSONObject("usuario").getInt("idSucursal"));
                    usuario.setTelefono_casa(respuestaJSON.getJSONObject("usuario").getString("telefono_casa"));
                    usuario.setTelefono_celular(respuestaJSON.getJSONObject("usuario").getString("telefono_celular"));
                    usuario.setPassword(respuestaJSON.getJSONObject("usuario").getString("clave"));
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
        return usuario;
    }
        
    public UsuarioBean insertaUsuarioWS(String... params) {
        UsuarioBean inserta = null;
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
            jsonParam.put("usuario", usuario);
            jsonParam.put("clave", password);
            jsonParam.put("permisos", permisos);
            jsonParam.put("nombre", nombre);
            jsonParam.put("apellido_paterno", apellido_paterno);
            jsonParam.put("apellido_materno", apellido_materno);
            jsonParam.put("telefono_casa", telefono_casa);
            jsonParam.put("telefono_celular", telefono_celular);
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
                    inserta = new UsuarioBean();
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

    public UsuarioBean modificaUsuarioWS(String... params) {
        UsuarioBean modifica = null;
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
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("usuario", usuario);
            jsonParam.put("clave", password);
            jsonParam.put("permisos", permisos);
            jsonParam.put("nombre", nombre);
            jsonParam.put("apellido_paterno", apellido_paterno);
            jsonParam.put("apellido_materno", apellido_materno);
            jsonParam.put("telefono_casa", telefono_casa);
            jsonParam.put("telefono_celular", telefono_celular);
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
                    modifica = new UsuarioBean();
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

    public UsuarioBean eliminaUsuarioWS(String... params) {
        UsuarioBean elimina = null;
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
                if (resultJSON == 1) {      // hay un alumno que mostrar
                    elimina = new UsuarioBean();
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
