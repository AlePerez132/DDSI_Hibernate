/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaCuotaActividad;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alepd
 */
public class CuotaActividadTablas {
    private VistaCuotaActividad vCuotaActividad = null;
    public DefaultTableModel modeloTablaCuotaActividad;

    public void inicializarTablaCuotaActividad(VistaCuotaActividad vCuotaActividad) {
        this.modeloTablaCuotaActividad = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vCuotaActividad.jTableCuotaActividad.setModel(modeloTablaCuotaActividad);
    }
    
     public void dibujarTablaCuotaActividades(VistaCuotaActividad vCuotaActividad) {
        String[] columnasTabla = {"Nombre", "Categoria"};
        modeloTablaCuotaActividad.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vCuotaActividad.jTableCuotaActividad.getTableHeader().setResizingAllowed(false);
        vCuotaActividad.jTableCuotaActividad.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vCuotaActividad.jTableCuotaActividad.getColumnModel().getColumn(0).setPreferredWidth(200);
        vCuotaActividad.jTableCuotaActividad.getColumnModel().getColumn(1).setPreferredWidth(125);
    }

    public void vaciarTablaCuotaActividades() {
          while(modeloTablaCuotaActividad.getRowCount() > 0){
            modeloTablaCuotaActividad.removeRow(0);
        }
    }

    public void rellenarTablaCuotaActividades(ArrayList<Object[]> socios) {
        Object[] fila = new Object[2];
        int numRegistros = socios.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = socios.get(i)[0];
            fila[1] = socios.get(i)[1];
            modeloTablaCuotaActividad.addRow(fila); 
        } 
    }
}
