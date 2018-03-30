package consumewebservices;

import beans.ProductoBean;
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

public class WSInventariosList {
    String cadena;
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

        public ArrayList<ProductoBean> ejecutaWebService(String... params) {
            cadena = params[0];
            url = null; // Url de donde queremos obtener información
            devuelve ="";
            ArrayList<ProductoBean> productos = new ArrayList();
            switch (params[1]) { 
                case "1" : productos = mostrarProductosWS(); break;
                case "2" : productos = buscaProductosPorCodigoWS(cadena); break;
                case "3" : productos = buscaProductoCodigoWS(cadena); break;
                case "4" : productos = buscaProductoNombreWS(cadena); break;
                case "5" : productos = buscaProductoIdWS(cadena); break;
            }
            return productos;
        }
 
    public ArrayList<ProductoBean> mostrarProductosWS() {
        ArrayList<ProductoBean> productos = new ArrayList();
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
                    JSONArray productosJSON = respuestaJSON.getJSONArray("inventarios");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<productosJSON.length();i++){
                        ProductoBean prod = new ProductoBean();
                        prod.setIdArticulo(productosJSON.getJSONObject(i).getInt("idArticulo"));
                        prod.setCodigo(productosJSON.getJSONObject(i).getString("codigo"));
                        prod.setDescripcion(productosJSON.getJSONObject(i).getString("descripcion"));
                        prod.setPrecioCosto(productosJSON.getJSONObject(i).getDouble("precioCosto"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPorcentajeImpuesto(productosJSON.getJSONObject(i).getDouble("porcentajeImpuesto"));
                        prod.setExistencia(productosJSON.getJSONObject(i).getDouble("existencia"));
                        prod.setExistenciaMinima(productosJSON.getJSONObject(i).getDouble("existenciaMinima"));
                        prod.setUbicacion(productosJSON.getJSONObject(i).getString("ubicacion"));
                        prod.setObservaciones(productosJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = productosJSON.getJSONObject(i).get("fechaIngreso").toString();
                        Util util = new Util();
                        prod.setFechaIngreso(util.stringToDate(fechaS));
                        prod.setIdProveedor(productosJSON.getJSONObject(i).getInt("idProveedor"));
                        prod.setIdCategoria(productosJSON.getJSONObject(i).getInt("idCategoria"));
                        prod.setFotoProducto(productosJSON.getJSONObject(i).getString("fotoProducto"));
                        prod.setIdSucursal(productosJSON.getJSONObject(i).getInt("idSucursal"));
                        productos.add(prod);
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
        return productos;
    }
        
        
    public ArrayList<ProductoBean> buscaProductosPorCodigoWS(String rutaWS) {
        ArrayList<ProductoBean> productos = new ArrayList();
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
                    JSONArray productosJSON = respuestaJSON.getJSONArray("inventario");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<productosJSON.length();i++){
                        ProductoBean prod = new ProductoBean();
                        prod.setIdArticulo(productosJSON.getJSONObject(i).getInt("idArticulo"));
                        prod.setCodigo(productosJSON.getJSONObject(i).getString("codigo"));
                        prod.setDescripcion(productosJSON.getJSONObject(i).getString("descripcion"));
                        prod.setPrecioCosto(productosJSON.getJSONObject(i).getDouble("precioCosto"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPorcentajeImpuesto(productosJSON.getJSONObject(i).getDouble("porcentajeImpuesto"));
                        prod.setExistencia(productosJSON.getJSONObject(i).getDouble("existencia"));
                        prod.setExistenciaMinima(productosJSON.getJSONObject(i).getDouble("existenciaMinima"));
                        prod.setUbicacion(productosJSON.getJSONObject(i).getString("ubicacion"));
                        prod.setObservaciones(productosJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = (String) productosJSON.getJSONObject(i).get("fechaIngreso");
                        Util util = new Util();
                        prod.setFechaIngreso(util.stringToDateTime(fechaS));
                        
                        prod.setIdProveedor(productosJSON.getJSONObject(i).getInt("idProveedor"));
                        prod.setIdCategoria(productosJSON.getJSONObject(i).getInt("idCategoria"));
                        prod.setFotoProducto(productosJSON.getJSONObject(i).getString("fotoProducto"));
                        prod.setIdSucursal(productosJSON.getJSONObject(i).getInt("idSucursal"));
                        productos.add(prod);
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
        return productos;
    }
    
    public ArrayList<ProductoBean> buscaProductoCodigoWS(String rutaWS) {
        ArrayList<ProductoBean> productos = new ArrayList();
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
                    JSONArray productosJSON = respuestaJSON.getJSONArray("inventario");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<productosJSON.length();i++){
                        ProductoBean prod = new ProductoBean();
                        prod.setIdArticulo(productosJSON.getJSONObject(i).getInt("idArticulo"));
                        prod.setCodigo(productosJSON.getJSONObject(i).getString("codigo"));
                        prod.setDescripcion(productosJSON.getJSONObject(i).getString("descripcion"));
                        prod.setPrecioCosto(productosJSON.getJSONObject(i).getDouble("precioCosto"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPorcentajeImpuesto(productosJSON.getJSONObject(i).getDouble("porcentajeImpuesto"));
                        prod.setExistencia(productosJSON.getJSONObject(i).getDouble("existencia"));
                        prod.setExistenciaMinima(productosJSON.getJSONObject(i).getDouble("existenciaMinima"));
                        prod.setUbicacion(productosJSON.getJSONObject(i).getString("ubicacion"));
                        prod.setObservaciones(productosJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = productosJSON.getJSONObject(i).get("fechaIngreso").toString();
                        Util util = new Util();
                        prod.setFechaIngreso(util.stringToDate(fechaS));
                        prod.setIdProveedor(productosJSON.getJSONObject(i).getInt("idProveedor"));
                        prod.setIdCategoria(productosJSON.getJSONObject(i).getInt("idCategoria"));
                        prod.setFotoProducto(productosJSON.getJSONObject(i).getString("fotoProducto"));
                        prod.setIdSucursal(productosJSON.getJSONObject(i).getInt("idSucursal"));
                        productos.add(prod);
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
        return productos;
    }

    public ArrayList<ProductoBean> buscaProductoNombreWS(String rutaWS) {
        ArrayList<ProductoBean> productos = new ArrayList();
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
                    JSONArray productosJSON = respuestaJSON.getJSONArray("inventario");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<productosJSON.length();i++){
                        ProductoBean prod = new ProductoBean();
                        prod.setIdArticulo(productosJSON.getJSONObject(i).getInt("idArticulo"));
                        prod.setCodigo(productosJSON.getJSONObject(i).getString("codigo"));
                        prod.setDescripcion(productosJSON.getJSONObject(i).getString("descripcion"));
                        prod.setPrecioCosto(productosJSON.getJSONObject(i).getDouble("precioCosto"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPorcentajeImpuesto(productosJSON.getJSONObject(i).getDouble("porcentajeImpuesto"));
                        prod.setExistencia(productosJSON.getJSONObject(i).getDouble("existencia"));
                        prod.setExistenciaMinima(productosJSON.getJSONObject(i).getDouble("existenciaMinima"));
                        prod.setUbicacion(productosJSON.getJSONObject(i).getString("ubicacion"));
                        prod.setObservaciones(productosJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = productosJSON.getJSONObject(i).get("fechaIngreso").toString();
                        Util util = new Util();
                        prod.setFechaIngreso(util.stringToDate(fechaS));
                        prod.setIdProveedor(productosJSON.getJSONObject(i).getInt("idProveedor"));
                        prod.setIdCategoria(productosJSON.getJSONObject(i).getInt("idCategoria"));
                        prod.setFotoProducto(productosJSON.getJSONObject(i).getString("fotoProducto"));
                        prod.setIdSucursal(productosJSON.getJSONObject(i).getInt("idSucursal"));
                        productos.add(prod);
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
        return productos;
    }
    
    public ArrayList<ProductoBean> buscaProductoIdWS(String rutaWS) {
        ArrayList<ProductoBean> productos = new ArrayList();
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
                    JSONArray productosJSON = respuestaJSON.getJSONArray("inventario");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<productosJSON.length();i++){
                        ProductoBean prod = new ProductoBean();
                        prod.setIdArticulo(productosJSON.getJSONObject(i).getInt("idArticulo"));
                        prod.setCodigo(productosJSON.getJSONObject(i).getString("codigo"));
                        prod.setDescripcion(productosJSON.getJSONObject(i).getString("descripcion"));
                        prod.setPrecioCosto(productosJSON.getJSONObject(i).getDouble("precioCosto"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPrecioUnitario(productosJSON.getJSONObject(i).getDouble("precioUnitario"));
                        prod.setPorcentajeImpuesto(productosJSON.getJSONObject(i).getDouble("porcentajeImpuesto"));
                        prod.setExistencia(productosJSON.getJSONObject(i).getDouble("existencia"));
                        prod.setExistenciaMinima(productosJSON.getJSONObject(i).getDouble("existenciaMinima"));
                        prod.setUbicacion(productosJSON.getJSONObject(i).getString("ubicacion"));
                        prod.setObservaciones(productosJSON.getJSONObject(i).getString("observaciones"));

                        //convierte fecha String a Date
                        String fechaS = productosJSON.getJSONObject(i).get("fechaIngreso").toString();
                        Util util = new Util();
                        prod.setFechaIngreso(util.stringToDateTime(fechaS));
                        prod.setIdProveedor(productosJSON.getJSONObject(i).getInt("idProveedor"));
                        prod.setIdCategoria(productosJSON.getJSONObject(i).getInt("idCategoria"));
                        prod.setFotoProducto(productosJSON.getJSONObject(i).getString("fotoProducto"));
                        prod.setIdSucursal(productosJSON.getJSONObject(i).getInt("idSucursal"));
                        productos.add(prod);
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
        return productos;
    }
    
}
