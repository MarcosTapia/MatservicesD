package consumewebservices;

import beans.MovimientosBean;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
import static vistas.Ingreso.usuario;

public class WSMovimientos {
    String cadena;
    //parametros con valores de producto
    String idArticulo;
    String idUsuario;
    String tipoOperacion;
    String cantidad;
    String fechaIngreso;
    String idSucursal;
    //parametros con valores de usuario
    URL url = null; // Url de donde queremos obtener información
    String devuelve ="";

    public MovimientosBean ejecutaWebService(String... params) {
        cadena = params[0];
        url = null; // Url de donde queremos obtener información
        devuelve ="";
        MovimientosBean movimientoObj = null;
        switch (params[1]) { 
            case "1" : 
                //idMovimiento
                //idArticulo
                //idUsuario
                //tipoOperacion
                //cantidad
                //fechaOperacion
                //idSucursal              

                //$body['idArticulo'],
                //$body['idUsuario'],
                //$body['tipoOperacion'],
                //$body['cantidad'],
                //$body['fechaOperacion']
                //$body['idSucursal']

                /* Notas de Tipo de Movimiento
                Venta Normal
                Venta Pedido
                Incremento Inventario Manual
                Decremento Inventario Manual        
                Compra Normal
                otro
                cambio Presio publico
                Notas de Tipo de Movimiento */

                idArticulo = params[2];
                idUsuario = params[3];
                tipoOperacion = params[4];
                cantidad = params[5];
                fechaIngreso = params[6];
                idSucursal = params[7];
                movimientoObj = insertaMovimientoWS(); 
                break;
//            case "2" : 
//                idArticulo = params[2];;
//                codigo = params[3];
//                descripcion = params[4];
//                precioCosto = params[5];
//                precioUnitario = params[6];
//                porcentajeImpuesto = params[7];
//                existencia = params[8];
//                existenciaMinima = params[9];
//                ubicacion = params[10];
//                fechaIngreso = params[11];
//                idProveedor = params[12];
//                idCategoria = params[13];
//                idSucursal = params[14];
//                fotoProducto = params[15];
//                observaciones = params[16];
//                productoObj = modificaProductoWS(); 
//                break;
//            case "3" : 
//                idArticulo = params[2];
//                productoObj = eliminaProductoWS(); 
//                break;
//            case "4" : 
//                productoObj = buscaProdPorCodigoWS(cadena); 
//                break;
        }
        return movimientoObj;
    }
 
    public MovimientosBean insertaMovimientoWS(String... params) {
        MovimientosBean inserta = null;
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
            jsonParam.put("idUsuario", idUsuario);
            jsonParam.put("tipoOperacion", tipoOperacion);
            jsonParam.put("cantidad", cantidad);
            jsonParam.put("fechaOperacion", fechaIngreso);
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
                    inserta = new MovimientosBean();
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

//    public ProductoBean modificaProductoWS(String... params) {
//        ProductoBean modifica = null;
//        try {
//            HttpURLConnection urlConn;
//            DataOutputStream printout;
//            DataInputStream input;
//            url = new URL(cadena);
//            urlConn = (HttpURLConnection) url.openConnection();
//            urlConn.setDoInput(true);
//            urlConn.setDoOutput(true);
//            urlConn.setUseCaches(false);
//            urlConn.setRequestProperty("Content-Type", "application/json");
//            urlConn.setRequestProperty("Accept", "application/json");
//            urlConn.connect();
//            //Creo el Objeto JSON
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("idArticulo", idArticulo);
//            jsonParam.put("codigo", codigo);
//            jsonParam.put("descripcion", descripcion);
//            jsonParam.put("precioCosto", precioCosto);
//            jsonParam.put("precioUnitario", precioUnitario);
//            jsonParam.put("porcentajeImpuesto", porcentajeImpuesto);
//            jsonParam.put("existencia", existencia);
//            jsonParam.put("existenciaMinima", existenciaMinima);
//            jsonParam.put("ubicacion", ubicacion);
//            jsonParam.put("fechaIngreso", fechaIngreso);
//            jsonParam.put("proveedor", idProveedor);
//            jsonParam.put("categoria", idCategoria);
//            jsonParam.put("sucursal", idSucursal);
//            jsonParam.put("nombre_img", fotoProducto);
//            jsonParam.put("observaciones", observaciones);
//            
//            // Envio los parámetros post.
//            OutputStream os = urlConn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(jsonParam.toString());
//            writer.flush();
//            writer.close();
//            int respuesta = urlConn.getResponseCode();
//            StringBuilder result = new StringBuilder();
//            if (respuesta == HttpURLConnection.HTTP_OK) {
//                String line;
//                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//                while ((line=br.readLine()) != null) {
//                    result.append(line);
//                    //response+=line;
//                }
//                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
//                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
//                //Accedemos al vector de resultados
//                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
//                if (resultJSON == 1) {      // hay un alumno que mostrar
//                    modifica = new ProductoBean();
//                } else if (resultJSON == 2) {
////                    devuelve = "El alumno no pudo actualizarse";
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return modifica;
//    }
//
//    public ProductoBean eliminaProductoWS(String... params) {
//        ProductoBean elimina = null;
//        try {
//            HttpURLConnection urlConn;
//            DataOutputStream printout;
//            DataInputStream input;
//            url = new URL(cadena);
//            urlConn = (HttpURLConnection) url.openConnection();
//            urlConn.setDoInput(true);
//            urlConn.setDoOutput(true);
//            urlConn.setUseCaches(false);
//            urlConn.setRequestProperty("Content-Type", "application/json");
//            urlConn.setRequestProperty("Accept", "application/json");
//            urlConn.connect();
//            //Creo el Objeto JSON
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("idArticulo", idArticulo);
//            // Envio los parámetros post.
//            OutputStream os = urlConn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(jsonParam.toString());
//            writer.flush();
//            writer.close();
//            int respuesta = urlConn.getResponseCode();
//            StringBuilder result = new StringBuilder();
//            if (respuesta == HttpURLConnection.HTTP_OK) {
//                String line;
//                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//                while ((line=br.readLine()) != null) {
//                    result.append(line);
//                    //response+=line;
//                }
//                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
//                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
//                //Accedemos al vector de resultados
//                int resultJSON = respuestaJSON.getInt("estado");   // estado es el nombre del campo en el JSON
//                if (resultJSON == 1) {      // hay un alumno que mostrar
//                    elimina = new ProductoBean();
//                } else if (resultJSON == 2) {
//                    devuelve = "No hay alumnos";
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return elimina;
//    }
//
//    public ProductoBean buscaProdPorCodigoWS(String rutaWS) {
//        ProductoBean prod = null;
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
//                    prod = new ProductoBean();
//                    prod.setIdArticulo(respuestaJSON.getJSONObject("inventario").getInt("idArticulo"));
//                    prod.setCodigo(respuestaJSON.getJSONObject("inventario").getString("codigo"));
//                    prod.setDescripcion(respuestaJSON.getJSONObject("inventario").getString("descripcion"));    
//                    prod.setPrecioCosto(respuestaJSON.getJSONObject("inventario").getDouble("precioCosto"));
//                    prod.setPrecioUnitario(respuestaJSON.getJSONObject("inventario").getDouble("precioUnitario"));
//                    prod.setPorcentajeImpuesto(respuestaJSON.getJSONObject("inventario").getDouble("porcentajeImpuesto"));
//                    prod.setExistencia(respuestaJSON.getJSONObject("inventario").getDouble("existencia"));
//                    prod.setExistenciaMinima(respuestaJSON.getJSONObject("inventario").getDouble("existenciaMinima"));
//                    prod.setUbicacion(respuestaJSON.getJSONObject("inventario").getString("ubicacion"));
//                    prod.setObservaciones(respuestaJSON.getJSONObject("inventario").getString("observaciones"));
//                    //convierte fecha String a Date
//                    String fechaS = respuestaJSON.getJSONObject("inventario").getString("fechaIngreso");
//                    Util util = new Util();
//                    prod.setFechaIngreso(util.stringToDateTime(fechaS));
//                    prod.setIdProveedor(respuestaJSON.getJSONObject("inventario").getInt("idProveedor"));
//                    prod.setIdCategoria(respuestaJSON.getJSONObject("inventario").getInt("idCategoria"));
//                    prod.setFotoProducto(respuestaJSON.getJSONObject("inventario").getString("fotoProducto"));
//                    prod.setIdSucursal(respuestaJSON.getJSONObject("inventario").getInt("idSucursal"));
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return prod;
//    }
    
}
