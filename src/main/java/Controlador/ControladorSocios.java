/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author alepd
 */
public class ControladorSocios implements ActionListener {

    private Conexion conexion = null;
    private Session sesion = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private SocioDAO vSocioDAO = null;
    private SocioTablas sTablas = null;
    private VistaSocio vSocio = null;
    private VistaNuevoSocio vNuevoSocio = null;
    private VistaActualizarSocio vActualizarSocio = null;
    private VistaAvisoBajaSocio vAvisoBajaSocio = null;
    private VistaBuscarSocio vBuscarSocio = null;
    private ControladorPrinicipal controlador = null;
    private VistaInscripcionesSocio vInscripcionesSocio = null;
    private InscripcionesTablas insTablas = null;
    private InscripcionesDAO vInscripcionesSocioDAO = null;
    private VistaNuevaInscripcion vNuevaInscripcion = null;
    private NuevaInscripcionTablas nuevaInsTablas = null;
    private NuevaInscripcionDAO vNuevaInscripcionDAO = null;
    SimpleDateFormat formatoFecha = null;

    public ControladorSocios(Conexion conexion, VistaSocio vSocio, SocioDAO vSocioDAO, ControladorPrinicipal controlador, SocioTablas sTablas, Session sesion) {
        this.conexion = conexion;
        this.vSocio = vSocio;
        this.vSocioDAO = vSocioDAO;
        this.controlador = controlador;
        this.sTablas = sTablas;
        this.sesion = sesion;
        this.insTablas = new InscripcionesTablas();
        this.vInscripcionesSocioDAO = new InscripcionesDAO(sesion);
        this.nuevaInsTablas = new NuevaInscripcionTablas();
        this.vNuevaInscripcionDAO = new NuevaInscripcionDAO(sesion);
        
        
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        vAvisoBajaSocio = new VistaAvisoBajaSocio();
        vNuevoSocio = new VistaNuevoSocio();
        vActualizarSocio = new VistaActualizarSocio();
        vBuscarSocio = new VistaBuscarSocio();
        vInscripcionesSocio = new VistaInscripcionesSocio();
        vNuevaInscripcion = new VistaNuevaInscripcion();
        
        insTablas.inicializarTablaInscripciones(vInscripcionesSocio);
        nuevaInsTablas.inicializarTablaInscripciones(vNuevaInscripcion);

        addListeners();

        vNuevoSocio.setLocationRelativeTo(vSocio);
        vNuevoSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        vAvisoBajaSocio.setLocationRelativeTo(vSocio);
        vAvisoBajaSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        vActualizarSocio.setLocationRelativeTo(vSocio);
        vActualizarSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        vBuscarSocio.setLocationRelativeTo(vSocio);
        vBuscarSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        vInscripcionesSocio.setLocationRelativeTo(vSocio);
        vInscripcionesSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        vNuevaInscripcion.setLocationRelativeTo(vSocio);
        vNuevaInscripcion.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    private void addListeners() {
        vSocio.botonNuevoSocio.addActionListener(this);
        vSocio.botonBajaSocio.addActionListener(this);
        vSocio.botonActualizarSocio.addActionListener(this);
        vSocio.botonBuscarSocio.addActionListener(this);
        vSocio.botonInscripcionesSocio.addActionListener(this);
        vSocio.botonNuevaInscripcion.addActionListener(this);

        vNuevoSocio.botonCancelarSocio.addActionListener(this);
        vNuevoSocio.botonInsertarSocio.addActionListener(this);

        vAvisoBajaSocio.botonCancelarBajaSocio.addActionListener(this);
        vAvisoBajaSocio.botonConfirmarBajaSocio.addActionListener(this);

        vActualizarSocio.botonActualizarSocio.addActionListener(this);
        vActualizarSocio.botonCancelarSocio.addActionListener(this);
        
        vBuscarSocio.botonBuscarSocio.addActionListener(this);
        
        vInscripcionesSocio.botonQuitarActividad.addActionListener(this);
        
        vNuevaInscripcion.botonAnadirActividad.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VentanaNuevoSocio":
                rellenaCampoCodigoNuevoSocio();
                muestraDialog(vNuevoSocio);
                break;
            case "BajaSocio":
                muestraDialog(vAvisoBajaSocio);
                break;
            case "VentanaActualizarSocio":
                rellenaCamposActualizarSocio();
                muestraDialog(vActualizarSocio);
                break;
            case "VentanaBuscarSocio":
                muestraDialog(vBuscarSocio);
                break;
            case "VentanaInscripcionesSocio":{
                int fila = vSocio.jTableSocios.getSelectedRow();
                String codigo = rellenaCamposInscripcionesSocio(fila);
                
                insTablas.dibujarTablaInscripciones(vInscripcionesSocio);
            {
                try {
                    pideInscripciones(codigo);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            muestraDialog(vInscripcionesSocio);
                break;
            }
            case "VentanaNuevaInscripcion":{
                int fila = vSocio.jTableSocios.getSelectedRow();
                String codigo = rellenaCamposNuevaInscripcion(fila);
                
                nuevaInsTablas.dibujarTablaInscripciones(vNuevaInscripcion);
            {
                try {
                    pideNuevaInscripcion(codigo);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            muestraDialog(vNuevaInscripcion);
                break;
            }
            case "BotonCancelarSocio":
                vNuevoSocio.setVisible(false);
                vActualizarSocio.setVisible(false);
                vAvisoBajaSocio.setVisible(false);
                break;
            case "BotonInsertarNuevoSocio": 
                try {
                    insertarNuevoSocio();
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                } catch (Exception ex) {
                Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "BotonBajaSocio":
                try {
                    bajaSocio();
                } catch (SQLException ex) {
                vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                } catch (Exception ex) {
                Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "BotonActualizarSocio":
                try {
                    actualizarSocio();
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                } catch (Exception ex) {
                Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            
            case "BotonBuscarSocio":{
                String nombre = "";
                nombre = vBuscarSocio.campoNombre.getText(); 
                try {
                    pideSociosFiltrado(nombre);
                } catch (Exception ex) {
                    vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                }
                vBuscarSocio.dispose();
            break;
            }
            case "AnadirActividad":{
                String codigoSocio = vNuevaInscripcion.jCampoCodigoSocio.getText();
                int fila = vNuevaInscripcion.jTableInscripcionesSocio.getSelectedRow();
                String codigoActividad = vNuevaInscripcion.jTableInscripcionesSocio.getValueAt(fila, 0).toString();
                vNuevaInscripcion.dispose();
                try {
                    vNuevaInscripcionDAO.insertarActividadDeSocio(codigoSocio, codigoActividad);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "QuitarActividad":{
                String codigoSocio = vInscripcionesSocio.jCampoCodigoSocio.getText();
                int fila = vInscripcionesSocio.jTableInscripcionesSocio.getSelectedRow();
                String codigoActividad = vInscripcionesSocio.jTableInscripcionesSocio.getValueAt(fila, 0).toString();
                vInscripcionesSocio.dispose();
                try {
                    vInscripcionesSocioDAO.eliminarActividadDeSocio(codigoSocio, codigoActividad);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
    }

    public void muestraDialog(JDialog dialog) {
        vNuevoSocio.setVisible(false);
        vAvisoBajaSocio.setVisible(false);
        vActualizarSocio.setVisible(false);
        vBuscarSocio.setVisible(false);
        vInscripcionesSocio.setVisible(false);
        vNuevaInscripcion.setVisible(false);

        dialog.setVisible(true);
    }

    public void rellenaCampoCodigoNuevoSocio() {
        String clave = "";
        int numero;
        numero = vSocio.jTableSocios.getRowCount();
        numero++;
        if (numero < 10 && numero >= 0) {
            clave = "S" + "00" + numero;
        } else if (numero < 100) {
            clave = "S" + "0" + numero;
        } else if (numero < 1000) {
            clave = "S" + numero;
        } else {
            System.out.println("Error en el número de Socios");
        }
        vNuevoSocio.campoNumeroNuevoSocio.setText(clave);
    }

    public void rellenaCamposActualizarSocio() {
        int fila = vSocio.jTableSocios.getSelectedRow();
        vActualizarSocio.campoNumeroActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 0).toString());
        vActualizarSocio.campoNombreActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 1).toString());
        vActualizarSocio.campoDniActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 2).toString());
        try{
            Date fecha = formatoFecha.parse(vSocio.jTableSocios.getValueAt(fila, 3).toString());
            vActualizarSocio.campoFechaNacimientoActualizarSocio.setDate(fecha);
        }catch(ParseException ex){ 
            
        }
        vActualizarSocio.campoTelefonoActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 4).toString());
        vActualizarSocio.campoCorreoActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 5).toString());
        try{
            Date fecha = formatoFecha.parse(vSocio.jTableSocios.getValueAt(fila, 6).toString());
            vActualizarSocio.campoFechaEntradaActualizarSocio.setDate(fecha);
        }catch(ParseException ex){ 
            
        }
        vActualizarSocio.campoCategoriaActualizarSocio.setText(vSocio.jTableSocios.getValueAt(fila, 7).toString());
    }
    
    public String rellenaCamposInscripcionesSocio(int fila){
        vInscripcionesSocio.jCampoCodigoSocio.setText(vSocio.jTableSocios.getValueAt(fila, 0).toString());
        vInscripcionesSocio.jCampoNombreSocio.setText(vSocio.jTableSocios.getValueAt(fila, 1).toString()); 
        
        return vSocio.jTableSocios.getValueAt(fila, 0).toString();
    }
    
    public String rellenaCamposNuevaInscripcion(int fila){
        vNuevaInscripcion.jCampoCodigoSocio.setText(vSocio.jTableSocios.getValueAt(fila, 0).toString());
        vNuevaInscripcion.jCampoNombreSocio.setText(vSocio.jTableSocios.getValueAt(fila, 1).toString()); 
        
        return vSocio.jTableSocios.getValueAt(fila, 0).toString();
    }

    public void insertarNuevoSocio() throws SQLException, Exception {
        Socio Socio = null;
        String numSocio = vNuevoSocio.campoNumeroNuevoSocio.getText();
        String nom = vNuevoSocio.campoNombreNuevoSocio.getText();
        String dni = vNuevoSocio.campoDniNuevoSocio.getText();
        String telefono = vNuevoSocio.campoTelefonoNuevoSocio.getText();
        String correo = vNuevoSocio.campoCorreoNuevoSocio.getText();
        Date fechaDateEntrada = vNuevoSocio.campoFechaEntradaNuevoSocio.getDate();
        Date fechaDateNacimiento = vNuevoSocio.campoFechaNacimientoNuevoSocio.getDate();
        Character categoria = vNuevoSocio.campoCategoriaNuevoSocio.getText().charAt(0);
        
        String fechaEntrada = null;
        String fechaNacimiento = null;

        if(fechaDateEntrada != null){
            fechaEntrada = formatoFecha.format(fechaDateEntrada);
        }
        if(fechaDateNacimiento != null){
            fechaNacimiento = formatoFecha.format(fechaDateNacimiento);
        }
        Socio = new Socio(numSocio, nom, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);        
        vSocioDAO.insertarSocio(Socio);
        System.out.println("He insertado el socio");
        vNuevoSocio.dispose();
        controlador.pideSocios();
    }
    
    public void bajaSocio() throws SQLException, Exception {
        int fila = vSocio.jTableSocios.getSelectedRow();
        String codigoSocio = (vSocio.jTableSocios.getValueAt(fila, 0)).toString();
        boolean eliminado = vSocioDAO.eliminarSocio(codigoSocio);
        vAvisoBajaSocio.dispose();
        controlador.pideSocios();
    }

    public void actualizarSocio() throws SQLException, Exception {
        Socio Socio = null;
        String numSocio = vActualizarSocio.campoNumeroActualizarSocio.getText();
        String nom = vActualizarSocio.campoNombreActualizarSocio.getText();
        String dni = vActualizarSocio.campoDniActualizarSocio.getText();
        String telefono = vActualizarSocio.campoTelefonoActualizarSocio.getText();
        String correo = vActualizarSocio.campoCorreoActualizarSocio.getText();
        Date fechaDateEntrada = vActualizarSocio.campoFechaEntradaActualizarSocio.getDate();
        Date fechaDateNacimiento = vActualizarSocio.campoFechaNacimientoActualizarSocio.getDate();
        Character categoria = vActualizarSocio.campoCategoriaActualizarSocio.getText().charAt(0);

        String fechaEntrada = null;
        String fechaNacimiento = null;

        if(fechaDateEntrada != null){
            fechaEntrada = formatoFecha.format(fechaDateEntrada);
        }
        if(fechaDateNacimiento != null){
            fechaNacimiento = formatoFecha.format(fechaDateNacimiento);
        }
        Socio = new Socio(numSocio, nom, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);
        try{
            vSocioDAO.actualizarSocio(Socio);
        }catch(SQLException ex){
            System.out.println("Excepcion en actualizar");
        }
        vActualizarSocio.dispose();
        controlador.pideSocios();
    }
    
    public void pideSociosFiltrado(String nombre) throws SQLException, Exception {
        ArrayList<Socio> lSocios = vSocioDAO.listaSocioPorLetra(nombre);
        sTablas.vaciarTablaSocios();
        sTablas.rellenarTablaSocios(lSocios);
    }
    
    public void pideInscripciones(String codigoSocio)throws SQLException, Exception{
        ArrayList<Object[]> listaInscripciones = vInscripcionesSocioDAO.listaDeActividadesPorSocio(codigoSocio);
        
            Object[] fila = new Object[5];
            for(int i = 0; i < listaInscripciones.size(); i++){
                fila[0] = listaInscripciones.get(i)[0];
                System.out.println("Id de la actividad " + i + " es " + fila[0]);
                fila[1] = listaInscripciones.get(i)[1];
                System.out.println("Nombre de la actividad " + i + " es " + fila[1]);
                fila[2] = listaInscripciones.get(i)[2];
                System.out.println("Descripcion de la actividad " + i + " es " + fila[2]);
                fila[3] = listaInscripciones.get(i)[3];
                System.out.println("Precio base al mes de la actividad " + i + " es " + fila[3]);
                fila[4] = listaInscripciones.get(i)[4];
                System.out.println("Monitor responsable de la actividad " + i + " es " + fila[4]);
            }
            
        insTablas.vaciarTablaInscripciones();
        insTablas.rellenarTablaInscripciones(listaInscripciones);
    }
    
    public void pideNuevaInscripcion(String codigoSocio) throws SQLException, Exception{
        ArrayList<Object[]> listaInscripciones = vNuevaInscripcionDAO.listaNuevasInscripciones(codigoSocio);
        
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
            }
            
        nuevaInsTablas.vaciarTablaInscripciones();
        nuevaInsTablas.rellenarTablaInscripciones(listaInscripciones);
    }
}
