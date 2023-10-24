/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Component;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author alepd
 */
public class VistaMensaje extends JOptionPane {
    
    public VistaMensaje(){
        
    }
    public void exito(){
        JOptionPane.showMessageDialog(null, "Conexión realizada con éxito.","Información",JOptionPane.INFORMATION_MESSAGE);
    }
    public void error(SQLException sqle){
        JOptionPane.showMessageDialog(null, "Error en la conexión.\n"+sqle.getMessage(),"Información",JOptionPane.ERROR_MESSAGE);
    }
    
    public void Mensaje(Component C, String tipoMensaje, String texto) {
        switch(tipoMensaje) {
        case "info":
            JOptionPane.showMessageDialog(C, texto, null, JOptionPane.INFORMATION_MESSAGE);
        break;
        }
    }
    
}
