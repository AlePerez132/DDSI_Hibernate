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
public class SocioTablas {

    private VistaSocio vSocio = null;
    public DefaultTableModel modeloTablaSocios;

    public void inicializarTablaSocios(VistaSocio vSocio) {
        this.modeloTablaSocios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vSocio.jTableSocios.setModel(modeloTablaSocios);
    }

    public void dibujarTablaSocios(VistaSocio vSocio) {
        String[] columnasTabla = {"NumeroSocio", "Nombre", "DNI", "FechaNacimiento", "Telefono",  "Correo ", "FechaEntrada", "Categoría"};
        modeloTablaSocios.setColumnIdentifiers(columnasTabla);

        //Para no permitir el redimensionamiento de las columnas con el raton
        vSocio.jTableSocios.getTableHeader().setResizingAllowed(false);
        vSocio.jTableSocios.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        //Asi se fija el ancho de las columnas
        vSocio.jTableSocios.getColumnModel().getColumn(0).setPreferredWidth(40);
        vSocio.jTableSocios.getColumnModel().getColumn(1).setPreferredWidth(150);
        vSocio.jTableSocios.getColumnModel().getColumn(2).setPreferredWidth(20);
        vSocio.jTableSocios.getColumnModel().getColumn(3).setPreferredWidth(30);
        vSocio.jTableSocios.getColumnModel().getColumn(4).setPreferredWidth(20);
        vSocio.jTableSocios.getColumnModel().getColumn(5).setPreferredWidth(90);
        vSocio.jTableSocios.getColumnModel().getColumn(6).setPreferredWidth(30);
        vSocio.jTableSocios.getColumnModel().getColumn(7).setPreferredWidth(1);
    }

    public void vaciarTablaSocios() {
          while(modeloTablaSocios.getRowCount() > 0){
            modeloTablaSocios.removeRow(0);
        }
    }

    public void rellenarTablaSocios(ArrayList<Socio> socios) {
        Object[] fila = new Object[8];
        int numRegistros = socios.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = socios.get(i).getNumerosocio();
            fila[1] = socios.get(i).getNombre();
            fila[2] = socios.get(i).getDni();
            fila[3] = socios.get(i).getFechanacimiento();
            fila[4] = socios.get(i).getTelefono();
            fila[5] = socios.get(i).getCorreo();
            fila[6] = socios.get(i).getFechaentrada();
            fila[7] = socios.get(i).getCategoria();
            modeloTablaSocios.addRow(fila); 
        } 
    }
     
}

