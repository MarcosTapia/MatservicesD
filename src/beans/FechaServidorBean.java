package beans;

import java.sql.Timestamp;

public class FechaServidorBean {
    private int id;
    private java.sql.Timestamp fechaServidor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Timestamp fechaServidor) {
        this.fechaServidor = fechaServidor;
    }
    
}
