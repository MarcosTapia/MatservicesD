package consumewebservices;

import beans.ClienteBean;
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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static vistas.Ingreso.usuario;

public class WSClientesList {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

        public ArrayList<ClienteBean> ejecutaWebService(String... params) {
            cadena = params[0];
            url = null; // Url de donde queremos obtener información
            devuelve ="";
            ArrayList<ClienteBean> clientes = new ArrayList();
            switch (params[1]) { 
                case "1" : clientes = mostrarClientesWS(); break;
//                case "2" : usuarios = buscaUsuariosIdWS(cadena); break;
//                case "3" : usuarios = buscaUsuariosNombreWS(cadena); break;
            }
            return clientes;
        }
 
    public ArrayList<ClienteBean> mostrarClientesWS() {
        ArrayList<ClienteBean> clientes = new ArrayList();
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
                    JSONArray clientesJSON = respuestaJSON.getJSONArray("clientes");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<clientesJSON.length();i++){
                        ClienteBean cli = new ClienteBean();
                        cli.setApellidos(clientesJSON.getJSONObject(i).getString("apellidos"));
                        cli.setCiudad(clientesJSON.getJSONObject(i).getString("ciudad"));
                        cli.setComentarios(clientesJSON.getJSONObject(i).getString("comentarios"));
                        cli.setCp(clientesJSON.getJSONObject(i).getString("cp"));
                        cli.setDireccion1(clientesJSON.getJSONObject(i).getString("direccion1"));
                        cli.setDireccion2(clientesJSON.getJSONObject(i).getString("direccion2"));
                        cli.setEmail(clientesJSON.getJSONObject(i).getString("email"));
                        cli.setEmpresa(clientesJSON.getJSONObject(i).getString("empresa"));
                        cli.setEstado(clientesJSON.getJSONObject(i).getString("estado"));
                        cli.setIdCliente(clientesJSON.getJSONObject(i).getInt("idCliente"));
                        cli.setNoCuenta(clientesJSON.getJSONObject(i).getString("noCuenta"));
                        cli.setNombre(clientesJSON.getJSONObject(i).getString("nombre"));
                        cli.setPais(clientesJSON.getJSONObject(i).getString("pais"));
                        cli.setRfc(clientesJSON.getJSONObject(i).getString("rfc"));
                        cli.setTelefono_casa(clientesJSON.getJSONObject(i).getString("telefono_casa"));
                        cli.setTelefono_celular(clientesJSON.getJSONObject(i).getString("telefono_celular"));
                        clientes.add(cli);
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
        return clientes;
    }
        
//    public ArrayList<UsuarioBean> buscaUsuariosIdWS(String rutaWS) {
//        ArrayList<UsuarioBean> usuarios = new ArrayList();
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
//                    JSONArray usuariosJSON = respuestaJSON.getJSONArray("usuario");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<usuariosJSON.length();i++){
//                        UsuarioBean usu = new UsuarioBean();
//                        usu.setIdUsuario(usuariosJSON.getJSONObject(i).getInt("idUsuario"));
//                        usu.setUsuario(usuariosJSON.getJSONObject(i).getString("usuario"));
//                        usu.setPassword(usuariosJSON.getJSONObject(i).getString("clave"));
//                        usu.setPermisos(usuariosJSON.getJSONObject(i).getString("permisos"));
//                        usu.setNombre(usuariosJSON.getJSONObject(i).getString("nombre"));
//                        usu.setApellido_paterno(usuariosJSON.getJSONObject(i).getString("apellido_paterno"));
//                        usu.setApellido_materno(usuariosJSON.getJSONObject(i).getString("apellido_materno"));
//                        usu.setTelefono_casa(usuariosJSON.getJSONObject(i).getString("telefono_casa"));
//                        usu.setTelefono_celular(usuariosJSON.getJSONObject(i).getString("telefono_celular"));
//                        usu.setIdSucursal(usuariosJSON.getJSONObject(i).getInt("idSucursal"));
//                        usuarios.add(usu);
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
//        return usuarios;
//    }
//    
//    public ArrayList<UsuarioBean> buscaUsuariosNombreWS(String rutaWS) {
//        ArrayList<UsuarioBean> usuarios = new ArrayList();
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
//                    JSONArray usuariosJSON = respuestaJSON.getJSONArray("usuario");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<usuariosJSON.length();i++){
//                        UsuarioBean usu = new UsuarioBean();
//                        usu.setIdUsuario(usuariosJSON.getJSONObject(i).getInt("idUsuario"));
//                        usu.setUsuario(usuariosJSON.getJSONObject(i).getString("usuario"));
//                        usu.setPassword(usuariosJSON.getJSONObject(i).getString("clave"));
//                        usu.setPermisos(usuariosJSON.getJSONObject(i).getString("permisos"));
//                        usu.setNombre(usuariosJSON.getJSONObject(i).getString("nombre"));
//                        usu.setApellido_paterno(usuariosJSON.getJSONObject(i).getString("apellido_paterno"));
//                        usu.setApellido_materno(usuariosJSON.getJSONObject(i).getString("apellido_materno"));
//                        usu.setTelefono_casa(usuariosJSON.getJSONObject(i).getString("telefono_casa"));
//                        usu.setTelefono_celular(usuariosJSON.getJSONObject(i).getString("telefono_celular"));
//                        usu.setIdSucursal(usuariosJSON.getJSONObject(i).getInt("idSucursal"));
//                        usuarios.add(usu);
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
//        return usuarios;
//    }
        
}
