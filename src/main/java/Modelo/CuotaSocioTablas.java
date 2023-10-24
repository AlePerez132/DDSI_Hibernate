/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaCuotaSocio;
import Vista.VistaSocio;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alepd
 */
public class CuotaSocioTablas {

    private VistaCuotaSocio vCuotaSocio = null;
    public DefaultTableModel modeloTablaCuotaSocios;

    public void inicializarTablaCuotaSocios(VistaCuotaSocio vCuotaSocio) {
        this.modeloTablaCuotaSocios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vCuotaSocio.jTableCuotaSocio.setModel(modeloTablaCuotaSocios);
    }

    public void dibujarTablaCuotaSocios(VistaCuotaSocio vCuotaSocio) {
        String[] columnasTabla = {"Nombre", "Precio"};
        modeloTablaCuotaSocios.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vCuotaSocio.jTableCuotaSocio.getTableHeader().setResizingAllowed(false);
        vCuotaSocio.jTableCuotaSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vCuotaSocio.jTableCuotaSocio.getColumnModel().getColumn(0).setPreferredWidth(200);
        vCuotaSocio.jTableCuotaSocio.getColumnModel().getColumn(1).setPreferredWidth(125);
    }

    public void vaciarTablaCuotaSocios() {
          while(modeloTablaCuotaSocios.getRowCount() > 0){
            modeloTablaCuotaSocios.removeRow(0);
        }
    }

    public void rellenarTablaCuotaSocios(ArrayList<Object[]> actividades) {
        Object[] fila = new Object[2];
        int numRegistros = actividades.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = actividades.get(i)[1];
            fila[1] = actividades.get(i)[3];
            modeloTablaCuotaSocios.addRow(fila); 
        } 
    }
     
}

