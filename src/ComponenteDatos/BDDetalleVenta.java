package ComponenteDatos;

import beans.DetalleVentaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BDDetalleVenta {
    
    public void guardarDetalleVenta(DetalleVentaBean detalleVentaBean) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("insert into detalleventa "
                + "(nVenCodigo,codigo,cantidad,precioUnitario,subTotalParcial) "
                + "values (?,?,?,?,?)");
        ps.setInt(1, detalleVentaBean.getnVenCodigo());
        ps.setString(2, detalleVentaBean.getCodigo());
        ps.setInt(3, detalleVentaBean.getCantidad());
        ps.setDouble(4, detalleVentaBean.getPrecioUnitario());
        ps.setDouble(5, detalleVentaBean.getSubTotalParcial());
        ps.executeUpdate();            
        ps.close();
        cnn.close();
    }
    
    public ArrayList<DetalleVentaBean> mostrarDetalleVentaPorCodigo(int noVenta) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps;
        ArrayList<DetalleVentaBean> lista = new ArrayList<>();
        ps = cnn.prepareStatement("select * from detalleventa where nVenCodigo = '" + noVenta + "'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DetalleVentaBean detVenta = new DetalleVentaBean() {
            };
            detVenta.setnVenCodigo(rs.getInt("nVenCodigo"));
            detVenta.setCodigo(rs.getString("codigo"));
            detVenta.setCantidad(rs.getInt("cantidad"));
            detVenta.setPrecioUnitario(rs.getDouble("precioUnitario"));
            detVenta.setSubTotalParcial(rs.getDouble("subTotalParcial"));
            lista.add(detVenta);
        }
        ps.close();
        cnn.close();
        return lista;
    }
    
}