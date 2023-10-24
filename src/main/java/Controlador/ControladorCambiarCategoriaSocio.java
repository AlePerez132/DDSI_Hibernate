/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.SocioDAO;
import Modelo.CambiarCategoriaSocioDAO;
import Modelo.Socio;
import Vista.VistaMensaje;
import Vista.VistaSocio;
import Vista.VistaCambiarCategoriaSocio;
import Vista.VistaPrincipal;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JDialog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorCambiarCategoriaSocio implements ActionListener {
    private Conexion conexion = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private SocioDAO vSocioDAO = null;
    private CambiarCategoriaSocioDAO vCambiarCategoriaSocioDAO = null;
    private VistaSocio vSocio = null;
    private VistaCambiarCategoriaSocio vCambiarCategoriaSocio = null;
    private ControladorPrinicipal controlador = null;
    private Socio socio = null;
    SimpleDateFormat formatoFecha = null;
    
    public ControladorCambiarCategoriaSocio(Conexion conexion, VistaCambiarCategoriaSocio vCambiarCategoriaSocio, CambiarCategoriaSocioDAO vCambiarCateogriaSocioDAO, ControladorPrinicipal controlador) {
        this.conexion = conexion;
        this.vCambiarCategoriaSocio = vCambiarCategoriaSocio;
        this.vCambiarCategoriaSocioDAO = vCambiarCateogriaSocioDAO;
        this.controlador = controlador;
        
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        addListeners();
    }
    
    private void addListeners() {
        vCambiarCategoriaSocio.botonBuscar.addActionListener(this);
        vCambiarCategoriaSocio.botonActualizarCategoria.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Buscar":
                muestraInfoSocio();
                break;
            case "ActualizarCategoria":
            {
                try {
                    actualizarCategoriaSocio();
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCambiarCategoriaSocio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

        }
    }
    
    public void muestraInfoSocio(){
        String codigo = vCambiarCategoriaSocio.jCampoCodigoSocio.getText();
        socio = vCambiarCategoriaSocioDAO.buscarSocio(codigo);
        
        vCambiarCategoriaSocio.jLabelNombre.setText(socio.getNombre());        
        vCambiarCategoriaSocio.jLabelCategoriaActual.setText(socio.getCategoria().toString());
    }
    
    public void actualizarCategoriaSocio() throws Exception{
        Character categoria = StringToChar(vCambiarCategoriaSocio.jCampoNuevaCategoria.getText());
        socio.setCategoria(categoria);
        vCambiarCategoriaSocioDAO.actualizarCategoriaSocio(socio);
        vCambiarCategoriaSocio.jCampoNuevaCategoria.setText("");
        
        System.out.println("SE HA ACTUALIZADO LA CATEGORIA DEL SOCIO " + socio.getCategoria() + " CORRECTAMENTE");
    }
    
    public static char StringToChar(String s) {
        return s.charAt(0);
    }
}
