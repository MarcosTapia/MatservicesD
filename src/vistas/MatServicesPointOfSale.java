package vistas;

import java.awt.Dimension;
import java.awt.Toolkit;
import vistas.Ingreso;

public class MatServicesPointOfSale {

    public static void main(String[] args) {
        Ingreso ingreso = new Ingreso();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        ingreso.setSize(525,378);		
        ingreso.setLocationRelativeTo(null);        
        ingreso.setVisible(true);        
    }
}
