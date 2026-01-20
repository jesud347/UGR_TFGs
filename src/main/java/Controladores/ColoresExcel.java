package Controladores;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColoresExcel extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Obtenemos el valor de la columna del estado 
        Object estadoValue = table.getValueAt(row, 3); 

        if (estadoValue != null && estadoValue.toString().equalsIgnoreCase("Incompleto")) {
            // Ponemos fondo rojo si el estado es Incompleto
            c.setBackground(Color.RED); 
            c.setForeground(Color.WHITE);
        } else {
            // Restauramos colores normales si no está seleccionado
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
        }

        return c;
    }
}