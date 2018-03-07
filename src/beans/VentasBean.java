package beans;

import java.sql.Timestamp;

public class VentasBean {
    private int nVenCodigo;
    //**
    private ClienteBean clienteBean;
    private int nCliCodigo;
    //**
    private UsuarioBean usuarioBean;
    private int idUsuario;
    
    private String cVenFecha;
    private double nVenMontoTotal;

    public int getnVenCodigo() {
        return nVenCodigo;
    }

    public void setnVenCodigo(int nVenCodigo) {
        this.nVenCodigo = nVenCodigo;
    }

    public ClienteBean getClienteBean() {
        return clienteBean;
    }

    public void setClienteBean(ClienteBean clienteBean) {
        this.clienteBean = clienteBean;
    }

    public int getnCliCodigo() {
        return nCliCodigo;
    }

    public void setnCliCodigo(int nCliCodigo) {
        this.nCliCodigo = nCliCodigo;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getcVenFecha() {
        return cVenFecha;
    }

    public void setcVenFecha(String cVenFecha) {
        this.cVenFecha = cVenFecha;
    }

    public double getnVenMontoTotal() {
        return nVenMontoTotal;
    }

    public void setnVenMontoTotal(double nVenMontoTotal) {
        this.nVenMontoTotal = nVenMontoTotal;
    }
    
    
    
}
