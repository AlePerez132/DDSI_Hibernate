/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.*;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alepd
 */
public class MonitorTablas {

    private VistaMonitor vMonitor = null;
    public DefaultTableModel modeloTablaMonitores;

    public void inicializarTablaMonitores(VistaMonitor vMonitor) {
        this.modeloTablaMonitores = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vMonitor.jTableMonitores.setModel(modeloTablaMonitores);
    }

    public void dibujarTablaMonitores(VistaMonitor vMonitor) {
        String[] columnasTabla = {"Codigo", "Nombre", "DNI", "Telefono", "Correo", "Fecha Incorporacion", "Nick"};
        modeloTablaMonitores.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vMonitor.jTableMonitores.getTableHeader().setResizingAllowed(false);
        vMonitor.jTableMonitores.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vMonitor.jTableMonitores.getColumnModel().getColumn(0).setPreferredWidth(40);
        vMonitor.jTableMonitores.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMonitor.jTableMonitores.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMonitor.jTableMonitores.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMonitor.jTableMonitores.getColumnModel().getColumn(4).setPreferredWidth(200);
        vMonitor.jTableMonitores.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.jTableMonitores.getColumnModel().getColumn(6).setPreferredWidth(60);
    }

    public void vaciarTablaMonitores() {
          while(modeloTablaMonitores.getRowCount() > 0){
            modeloTablaMonitores.removeRow(0);
        }
    }

    public void rellenarTablaMonitores(ArrayList<Monitor> monitores) {
        Object[] fila = new Object[7];
        int numRegistros = monitores.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = monitores.get(i).getCodmonitor();
            fila[1] = monitores.get(i).getNombre();
            fila[2] = monitores.get(i).getDni();
            fila[3] = monitores.get(i).getTelefono();
            fila[4] = monitores.get(i).getCorreo();
            fila[5] = monitores.get(i).getFechaentrada();
            fila[6] = monitores.get(i).getNick();
            modeloTablaMonitores.addRow(fila); 
        } 
    }
     
}
