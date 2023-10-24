/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaCuotaMonitor;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alepd
 */
public class CuotaMonitorTablas {
    private VistaCuotaMonitor vCuotaMonitor = null;
    public DefaultTableModel modeloTablaCuotaMonitor;

    public void inicializarTablaCuotaMonitor(VistaCuotaMonitor vCuotaMonitor) {
        this.modeloTablaCuotaMonitor = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vCuotaMonitor.jTableCuotaMonitor.setModel(modeloTablaCuotaMonitor);
    }
    
    public void dibujarTablaCuotaMonitores(VistaCuotaMonitor vCuotaMonitor) {
        String[] columnasTabla = {"Nombre", "Precio", "Socios"};
        modeloTablaCuotaMonitor.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vCuotaMonitor.jTableCuotaMonitor.getTableHeader().setResizingAllowed(false);
        vCuotaMonitor.jTableCuotaMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vCuotaMonitor.jTableCuotaMonitor.getColumnModel().getColumn(0).setPreferredWidth(200);
        vCuotaMonitor.jTableCuotaMonitor.getColumnModel().getColumn(1).setPreferredWidth(125);
        vCuotaMonitor.jTableCuotaMonitor.getColumnModel().getColumn(2).setPreferredWidth(125);
    }

    public void vaciarTablaCuotaMonitores() {
          while(modeloTablaCuotaMonitor.getRowCount() > 0){
            modeloTablaCuotaMonitor.removeRow(0);
        }
    }

    public void rellenarTablaCuotaMonitores(ArrayList<Object[]> actividades, int[] numeroSocios) {
        Object[] fila = new Object[3];
        int numRegistros = actividades.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = actividades.get(i)[1];
            fila[1] = actividades.get(i)[3];
            fila[2] = numeroSocios[i];
            modeloTablaCuotaMonitor.addRow(fila); 
        } 
    }
}
