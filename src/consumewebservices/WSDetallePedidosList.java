package consumewebservices;

import beans.DetallePedidoBean;
import beans.DetalleVentaBean;
import beans.EdoMunBean;
import beans.EstadoBean;
import beans.MunicipioBean;
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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static vistas.Ingreso.usuario;

public class WSDetallePedidosList {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

        public ArrayList<DetallePedidoBean> ejecutaWebService(String... params) {
            cadena = params[0];
            url = null; // Url de donde queremos obtener información
            devuelve ="";
            ArrayList<DetallePedidoBean> detallePedidos = new ArrayList();
            switch (params[1]) { 
                case "1" : detallePedidos = obtenerDetallePedidosWS(); break;
            }
            return detallePedidos;
        }
        
//        public ArrayList<EdoMunBean> ejecutaWebService2(String... params) {
//            cadena = params[0];
//            url = null; // Url de donde queremos obtener información
//            devuelve ="";
//            ArrayList<EdoMunBean> estadosMun = new ArrayList();
//            switch (params[1]) { 
//                case "1" : estadosMun = obtenerEstadosMunWS(); break;
//            }
//            return estadosMun;
//        }
//
//        public ArrayList<MunicipioBean> ejecutaWebService3(String... params) {
//            cadena = params[0];
//            url = null; // Url de donde queremos obtener información
//            devuelve ="";
//            ArrayList<MunicipioBean> municipios = new ArrayList();
//            switch (params[1]) { 
//                case "1" : municipios = obtenerMunicipiosWS(); break;
//            }
//            return municipios;
//        }
        
    public ArrayList<DetallePedidoBean> obtenerDetallePedidosWS() {
        ArrayList<DetallePedidoBean> detallePedidos = new ArrayList();
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
                    JSONArray detallePedidosJSON = respuestaJSON.getJSONArray("detallePedidos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<detallePedidosJSON.length();i++){
                        DetallePedidoBean detallePedido = new DetallePedidoBean();
                        detallePedido.setIdDetallePedido(detallePedidosJSON.getJSONObject(i).getInt("idDetallePedido"));
                        detallePedido.setIdPedido(detallePedidosJSON.getJSONObject(i).getInt("idPedido"));
                        detallePedido.setIdArticulo(detallePedidosJSON.getJSONObject(i).getInt("idArticulo"));
                        detallePedido.setPrecio(detallePedidosJSON.getJSONObject(i).getDouble("precio"));
                        detallePedido.setCantidad(detallePedidosJSON.getJSONObject(i).getDouble("cantidad"));
                        detallePedido.setDescuento(detallePedidosJSON.getJSONObject(i).getDouble("descuento"));
                        detallePedido.setIdSucursal(detallePedidosJSON.getJSONObject(i).getInt("idSucursal"));
                        detallePedidos.add(detallePedido);
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
        return detallePedidos;
    }

//    public ArrayList<EdoMunBean> obtenerEstadosMunWS() {
//        ArrayList<EdoMunBean> estadosMun = new ArrayList();
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
//                    JSONArray estadosMunJSON = respuestaJSON.getJSONArray("estadosmun");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<estadosMunJSON.length();i++){
//                        EdoMunBean edoMun = new EdoMunBean();
//                        edoMun.setIdEstado(estadosMunJSON.getJSONObject(i).getInt("estados_id"));
//                        edoMun.setIdMunicipio(estadosMunJSON.getJSONObject(i).getInt("municipios_id"));
//                        estadosMun.add(edoMun);
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
//        return estadosMun;
//    }
//    
//    public ArrayList<MunicipioBean> obtenerMunicipiosWS() {
//        ArrayList<MunicipioBean> municipios = new ArrayList();
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
//                    JSONArray municipiosJSON = respuestaJSON.getJSONArray("municipios");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<municipiosJSON.length();i++){
//                        MunicipioBean municipio = new MunicipioBean();
//                        municipio.setIdMunicipio(municipiosJSON.getJSONObject(i).getInt("id"));
//                        municipio.setMunicipio(municipiosJSON.getJSONObject(i).getString("municipio"));
//                        municipios.add(municipio);
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
//        return municipios;
//    }
//        
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
    
        
}
