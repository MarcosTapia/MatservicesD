package beans;

public class CategoriaBean {

    private int nCatCodigo;
    private String cCatDescripcion;
    private int utilidad;

    public int getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }
    
    public String getcCatDescripcion() {
        return cCatDescripcion;
    }

    public void setcCatDescripcion(String cCatDescripcion) {
        this.cCatDescripcion = cCatDescripcion;
    }

    public int getnCatCodigo() {
        return nCatCodigo;
    }

    public void setnCatCodigo(int nCatCodigo) {
        this.nCatCodigo = nCatCodigo;
    }
}
