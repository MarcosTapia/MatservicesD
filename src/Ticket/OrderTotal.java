package Ticket;
public class OrderTotal{
    char[] temp=new char[] { ' ' };
    public OrderTotal(char delimit){temp=new char[]{delimit};}
    public String GetTotalNombre(String totalItem){
        String[] delimitado=totalItem.split(""+temp);
        return delimitado[0];
    }
    public String GetTotalCantidad(String totalItem){
        String[] delimitado=totalItem.split(""+temp);
        return delimitado[1];
    }
    public String GeneraTotal(String Nombre, String precio){
        return Nombre+temp[0]+temp[0]+temp[0]+temp[0]+precio;
    }
}


/*

LUEGO DE ESTO SOLO TIENE Q CREAR UNA INSTANCIA DE LA PRIMERA CLASE

Ticket ticket=new Ticket();

aki les pongo una porcion de codigo para imprimir un ticket con la informacion de un JTable

el codigo esta sencillo para q lo aprendan y entiendad para q es cada metodo de la clase Ticket
*/