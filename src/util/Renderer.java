package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Unknown
 */
public class Renderer extends DefaultTableCellRenderer{
    private Map<Integer,Integer> errores;

    public Map<Integer, Integer> getErrores() {
        return errores;
    }

    public void setErrores(Map<Integer, Integer> errores) {
        this.errores = errores;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (verificaError(row + 1, column)) {
            setBackground(Color.green);
            setForeground(Color.RED);
        } else {
            setBackground(new Color(255,255,255));
            setForeground(Color.black);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean verificaError(int renglon,int columna) {
        boolean seleccion = false;
        Iterator it = errores.keySet().iterator();
        int renglonTemp = 0;
        int columnaTemp = 0;
        while(it.hasNext()){
            Object key = it.next();
            renglonTemp = Integer.parseInt(key.toString());
            columnaTemp = errores.get(key);
            if (renglon == renglonTemp) {
                if (columna == columnaTemp) {
                    seleccion = true;
                    break;
                }
            } 
        }
        return seleccion;
    }
}
