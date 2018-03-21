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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static vistas.Ingreso.usuario;

public class WSInventarios {
    String cadena;
    //parametros con valores de producto
    String codigo;
    String descripcion;
    String precioCosto;
    String precioUnitario;
    String porcentajeImpuesto;
    String existencia;
    String existenciaMinima;
    String ubicacion;
    String observaciones;
    String fechaIngreso;
    String idProveedor;
    String idCategoria;
    String fotoProducto;
    String idSucursal;
    String idArticulo;
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public ProductoBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        ProductoBean productoObj = null;
        switch (params[1]) { 
            case "1" : 
                codigo = params[2];
                descripcion = params[3];
                precioCosto = params[4];
                precioUnitario = params[5];
                porcentajeImpuesto = params[6];
                existencia = params[7];
                existenciaMinima = params[8];
                ubicacion = params[9];
                fechaIngreso = params[10];
                idProveedor = params[11];
                idCategoria = params[12];
                idSucursal = params[13];
                fotoProducto = params[14];
                observaciones = params[15];
                productoObj = insertaProductoWS(); 
                break;
            case "2" : 
                idArticulo = params[2];;
                codigo = params[3];
                descripcion = params[4];
                precioCosto = params[5];
                precioUnitario = params[6];
                porcentajeImpuesto = params[7];
                existencia = params[8];
                existenciaMinima = params[9];
                ubicacion = params[10];
                fechaIngreso = params[11];
                idProveedor = params[12];
                idCategoria = params[13];
                idSucursal = params[14];
                fotoProducto = params[15];
                observaciones = params[16];
                productoObj = modificaProductoWS(); 
                break;
            case "3" : 
                idArticulo = params[2];;
                productoObj = eliminaProductoWS(); 
                break;
        }
        return productoObj;
    }
 
    public ProductoBean insertaProductoWS(String... params) {
        ProductoBean inserta = null;
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
            jsonParam.put("codigo", codigo);
            jsonParam.put("descripcion", descripcion);
            jsonParam.put("precioCosto", precioCosto);
            jsonParam.put("precioUnitario", precioUnitario);
            jsonParam.put("porcentajeImpuesto", porcentajeImpuesto);
            jsonParam.put("existencia", existencia);
            jsonParam.put("existenciaMinima", existenciaMinima);
            jsonParam.put("ubicacion", ubicacion);
            jsonParam.put("fechaIngreso", fechaIngreso);
            jsonParam.put("proveedor", idProveedor);
            jsonParam.put("categoria", idCategoria);
            jsonParam.put("sucursal", idSucursal);
            jsonParam.put("nombre_img", fotoProducto);
            jsonParam.put("observaciones", observaciones);
            
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
                    inserta = new ProductoBean();
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

    public ProductoBean modificaProductoWS(String... params) {
        ProductoBean modifica = null;
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
            jsonParam.put("idArticulo", idArticulo);
            jsonParam.put("codigo", codigo);
            jsonParam.put("descripcion", descripcion);
            jsonParam.put("precioCosto", precioCosto);
            jsonParam.put("precioUnitario", precioUnitario);
            jsonParam.put("porcentajeImpuesto", porcentajeImpuesto);
            jsonParam.put("existencia", existencia);
            jsonParam.put("existenciaMinima", existenciaMinima);
            jsonParam.put("ubicacion", ubicacion);
            jsonParam.put("fechaIngreso", fechaIngreso);
            jsonParam.put("proveedor", idProveedor);
            jsonParam.put("categoria", idCategoria);
            jsonParam.put("sucursal", idSucursal);
            jsonParam.put("nombre_img", fotoProducto);
            jsonParam.put("observaciones", observaciones);
            
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
                    modifica = new ProductoBean();
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

    public ProductoBean eliminaProductoWS(String... params) {
        ProductoBean elimina = null;
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
            jsonParam.put("idArticulo", idArticulo);
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
                    elimina = new ProductoBean();
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
