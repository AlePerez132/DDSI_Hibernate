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
public class InscripcionesTablas {
    VistaInscripcionesSocio vInscripcionesSocio = null;
    DefaultTableModel modeloTablaInscripciones = null;
    
    public void inicializarTablaInscripciones(VistaInscripcionesSocio vInscripcionesSocio){
        this.modeloTablaInscripciones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vInscripcionesSocio.jTableInscripcionesSocio.setModel(modeloTablaInscripciones);
    }
    
    public void dibujarTablaInscripciones(VistaInscripcionesSocio vInscripcionesSocio) {
        String[] columnasTabla = {"ID", "Nombre", "Descripcion", "PrecioBaseMes", "MonitorResponsable"};
        modeloTablaInscripciones.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vInscripcionesSocio.jTableInscripcionesSocio.getTableHeader().setResizingAllowed(false);
        vInscripcionesSocio.jTableInscripcionesSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        
        vInscripcionesSocio.jTableInscripcionesSocio.getColumnModel().getColumn(0).setPreferredWidth(150);
        vInscripcionesSocio.jTableInscripcionesSocio.getColumnModel().getColumn(1).setPreferredWidth(250);
        vInscripcionesSocio.jTableInscripcionesSocio.getColumnModel().getColumn(2).setPreferredWidth(800);
        vInscripcionesSocio.jTableInscripcionesSocio.getColumnModel().getColumn(3).setPreferredWidth(150);
        vInscripcionesSocio.jTableInscripcionesSocio.getColumnModel().getColumn(4).setPreferredWidth(150);        
    }
    
    public void vaciarTablaInscripciones() {
          while(modeloTablaInscripciones.getRowCount() > 0){
            modeloTablaInscripciones.removeRow(0);
        }
    }
    
    public void rellenarTablaInscripciones(ArrayList<Object[]> listaInscripciones) throws SQLException{
        Object[] fila = new Object[5];
            for(int i = 0; i < listaInscripciones.size(); i++){
                fila[0] = listaInscripciones.get(i)[0];
                System.out.println("Elemento 0 de la tupla " + i + "es " + fila[0]);
                fila[1] = listaInscripciones.get(i)[1];
                System.out.println("Elemento 1 de la tupla " + i + "es " + fila[1]);
                fila[2] = listaInscripciones.get(i)[2];
                System.out.println("Elemento 2 de la tupla " + i + "es " + fila[2]);
                fila[3] = listaInscripciones.get(i)[3];
                System.out.println("Elemento 3 de la tupla " + i + "es " + fila[3]);
                fila[4] = listaInscripciones.get(i)[4];
                System.out.println("Elemento 4 de la tupla " + i + "es " + fila[4]);
                modeloTablaInscripciones.addRow(fila);
            }
    }
}