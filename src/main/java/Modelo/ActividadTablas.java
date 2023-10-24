/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alepd
 */
public class ActividadTablas {
    VistaActividad vActividad = null;
    DefaultTableModel modeloTablaActividades = null;
    
    public void inicializarTablaActividades(VistaActividad vActividad){
        this.modeloTablaActividades = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vActividad.jTableActividades.setModel(modeloTablaActividades);
    }
    
    public void dibujarTablaActividades(VistaActividad vActividad) {
        String[] columnasTabla = {"ID", "Nombre"};
        modeloTablaActividades.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vActividad.jTableActividades.getTableHeader().setResizingAllowed(false);
        vActividad.jTableActividades.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vActividad.jTableActividades.getColumnModel().getColumn(0).setPreferredWidth(500);
        vActividad.jTableActividades.getColumnModel().getColumn(1).setPreferredWidth(500);
    }
    
    public void vaciarTablaActividades() {
          while(modeloTablaActividades.getRowCount() > 0){
            modeloTablaActividades.removeRow(0);
        }
    }
    
    public void rellenarTablaActividades(ArrayList<Object[]> lActividades) throws SQLException{
        Object[] fila = new Object[2];
        for(int i = 0; i < lActividades.size(); i++){
            fila[0] = lActividades.get(i)[0];
            fila[1] = lActividades.get(i)[1];
            modeloTablaActividades.addRow(fila);
        }
    }
}