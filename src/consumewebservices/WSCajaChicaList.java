package consumewebservices;

import beans.CajaChicaBean;
import beans.CategoriaBean;
import beans.PedidoBean;
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
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSCajaChicaList {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";
    String idMov;

        public ArrayList<CajaChicaBean> ejecutaWebService(String... params) {
            cadena = params[0];
            url = null; // Url de donde queremos obtener información
            devuelve ="";
            ArrayList<CajaChicaBean> cajachicas = new ArrayList();
            switch (params[1]) { 
                case "1" : cajachicas = obtenerCajaChicasWS(); break;
                case "2" : cajachicas = obtenerUltimoMovWS(); break;
                case "3" : cajachicas = buscaMovCajaChicaIdWS(cadena); break;
                case "4" : cajachicas = busquedaMovsCajaPorFechasWS(); break;
        }
        return cajachicas;
    }
        
    public ArrayList<CajaChicaBean> busquedaMovsCajaPorFechasWS(String... params) {
        ArrayList<CajaChicaBean> cajachicas = new ArrayList();
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
                    JSONArray cajaChicasJSON = respuestaJSON.getJSONArray("cajachicas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<cajaChicasJSON.length();i++){
                        CajaChicaBean cajaChica = new CajaChicaBean();
                        String fechaS = cajaChicasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        cajaChica.setFecha(util.stringToDateTime(fechaS));
                        cajaChica.setIdMov(cajaChicasJSON.getJSONObject(i).getInt("idMov"));
                        cajaChica.setIdSucursal(cajaChicasJSON.getJSONObject(i).getInt("idSucursal"));
                        cajaChica.setIdUsuario(cajaChicasJSON.getJSONObject(i).getInt("idUsuario"));
                        cajaChica.setMonto(cajaChicasJSON.getJSONObject(i).getDouble("monto"));
                        cajaChica.setReferencia(cajaChicasJSON.getJSONObject(i).getString("referencia"));
                        cajaChica.setSaldoActual(cajaChicasJSON.getJSONObject(i).getDouble("saldoActual"));
                        cajaChica.setSaldoAnterior(cajaChicasJSON.getJSONObject(i).getDouble("saldoAnterior"));
                        cajaChica.setTipoComprobante(cajaChicasJSON.getJSONObject(i).getString("tipoComprobante"));
                        cajaChica.setTipoMov(cajaChicasJSON.getJSONObject(i).getString("tipoMov"));
                        cajachicas.add(cajaChica);
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
        return cajachicas;
    }
 
    public ArrayList<CajaChicaBean> obtenerCajaChicasWS() {
        ArrayList<CajaChicaBean> cajachicas = new ArrayList();
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
                    JSONArray cajaChicasJSON = respuestaJSON.getJSONArray("cajachicas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<cajaChicasJSON.length();i++){
                        CajaChicaBean cajaChica = new CajaChicaBean();
                        String fechaS = cajaChicasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        cajaChica.setFecha(util.stringToDateTime(fechaS));
                        cajaChica.setIdMov(cajaChicasJSON.getJSONObject(i).getInt("idMov"));
                        cajaChica.setIdSucursal(cajaChicasJSON.getJSONObject(i).getInt("idSucursal"));
                        cajaChica.setIdUsuario(cajaChicasJSON.getJSONObject(i).getInt("idUsuario"));
                        cajaChica.setMonto(cajaChicasJSON.getJSONObject(i).getDouble("monto"));
                        cajaChica.setReferencia(cajaChicasJSON.getJSONObject(i).getString("referencia"));
                        cajaChica.setSaldoActual(cajaChicasJSON.getJSONObject(i).getDouble("saldoActual"));
                        cajaChica.setSaldoAnterior(cajaChicasJSON.getJSONObject(i).getDouble("saldoAnterior"));
                        cajaChica.setTipoComprobante(cajaChicasJSON.getJSONObject(i).getString("tipoComprobante"));
                        cajaChica.setTipoMov(cajaChicasJSON.getJSONObject(i).getString("tipoMov"));
                        cajachicas.add(cajaChica);
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
        return cajachicas;
    }
    
    public ArrayList<CajaChicaBean> obtenerUltimoMovWS() {
        ArrayList<CajaChicaBean> cajachicas = new ArrayList();
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
                    JSONArray cajaChicasJSON = respuestaJSON.getJSONArray("cajachica");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<cajaChicasJSON.length();i++){
                        CajaChicaBean cajaChica = new CajaChicaBean();
                        String fechaS = cajaChicasJSON.getJSONObject(i).get("fecha").toString();
                        Util util = new Util();
                        cajaChica.setFecha(util.stringToDateTime(fechaS));
                        cajaChica.setIdMov(cajaChicasJSON.getJSONObject(i).getInt("idMov"));
                        cajaChica.setIdSucursal(cajaChicasJSON.getJSONObject(i).getInt("idSucursal"));
                        cajaChica.setIdUsuario(cajaChicasJSON.getJSONObject(i).getInt("idUsuario"));
                        cajaChica.setMonto(cajaChicasJSON.getJSONObject(i).getDouble("monto"));
                        cajaChica.setReferencia(cajaChicasJSON.getJSONObject(i).getString("referencia"));
                        cajaChica.setSaldoActual(cajaChicasJSON.getJSONObject(i).getDouble("saldoActual"));
                        cajaChica.setSaldoAnterior(cajaChicasJSON.getJSONObject(i).getDouble("saldoAnterior"));
                        cajaChica.setTipoComprobante(cajaChicasJSON.getJSONObject(i).getString("tipoComprobante"));
                        cajaChica.setTipoMov(cajaChicasJSON.getJSONObject(i).getString("tipoMov"));
                        cajachicas.add(cajaChica);
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
        return cajachicas;
    }
        
    public ArrayList<SucursalBean> busquedaSucursalesPorNombreWS(String rutaWS) {
        ArrayList<SucursalBean> sucursales = new ArrayList();
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
                    JSONArray sucursalesJSON = respuestaJSON.getJSONArray("sucursal");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<sucursalesJSON.length();i++){
                        SucursalBean suc = new SucursalBean();
                        suc.setIdSucursal(sucursalesJSON.getJSONObject(i).getInt("idSucursal"));
                        suc.setDescripcionSucursal(sucursalesJSON.getJSONObject(i).getString("descripcionSucursal"));
                        sucursales.add(suc);
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
        return sucursales;
    }
        
    public ArrayList<SucursalBean> busquedaSucursalesPorIdWS(String rutaWS) {
        ArrayList<SucursalBean> sucursales = new ArrayList();
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
                    JSONArray sucursalesJSON = respuestaJSON.getJSONArray("sucursal");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<sucursalesJSON.length();i++){
                        SucursalBean suc = new SucursalBean();
                        suc.setIdSucursal(sucursalesJSON.getJSONObject(i).getInt("idSucursal"));
                        suc.setDescripcionSucursal(sucursalesJSON.getJSONObject(i).getString("descripcionSucursal"));
                        sucursales.add(suc);
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
        return sucursales;
    }

    public ArrayList<CajaChicaBean> buscaMovCajaChicaIdWS(String rutaWS) {
        ArrayList<CajaChicaBean> cajachicas = new ArrayList();
        try {
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("idMov", idMov);
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
//                    JSONArray cajaChicasJSON = respuestaJSON.getJSONArray("cajachica");   // estado es el nombre del campo en el JSON
//                    for(int i=0;i<cajaChicasJSON.length();i++){
                    
//                    suc.setIdSucursal(respuestaJSON.getJSONObject("sucursal").getInt("idSucursal"));
//                    suc.setDescripcionSucursal(respuestaJSON.getJSONObject("sucursal").getString("descripcionSucursal"));

                    
                        CajaChicaBean cajaChica = new CajaChicaBean();
                        String fechaS = respuestaJSON.getJSONObject("cajachica").getString("fecha");
                        Util util = new Util();
                        cajaChica.setFecha(util.stringToDateTime(fechaS));
                        cajaChica.setIdMov(respuestaJSON.getJSONObject("cajachica").getInt("idMov"));
                        cajaChica.setIdSucursal(respuestaJSON.getJSONObject("cajachica").getInt("idSucursal"));
                        cajaChica.setIdUsuario(respuestaJSON.getJSONObject("cajachica").getInt("idUsuario"));
                        cajaChica.setMonto(respuestaJSON.getJSONObject("cajachica").getDouble("monto"));
                        cajaChica.setReferencia(respuestaJSON.getJSONObject("cajachica").getString("referencia"));
                        cajaChica.setSaldoActual(respuestaJSON.getJSONObject("cajachica").getDouble("saldoActual"));
                        cajaChica.setSaldoAnterior(respuestaJSON.getJSONObject("cajachica").getDouble("saldoAnterior"));
                        cajaChica.setTipoComprobante(respuestaJSON.getJSONObject("cajachica").getString("tipoComprobante"));
                        cajaChica.setTipoMov(respuestaJSON.getJSONObject("cajachica").getString("tipoMov"));
                        cajachicas.add(cajaChica);
//                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cajachicas;
    }
    
}
