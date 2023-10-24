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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorMonitores implements ActionListener {

    private Conexion conexion = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private MonitorDAO vMonitorDAO = null;
    private MonitorTablas mTablas = null;
    private VistaMonitor vMonitor = null;
    private VistaNuevoMonitor vNuevoMonitor = null;
    private VistaActualizarMonitor vActualizarMonitor = null;
    private VistaAvisoBajaMonitor vAvisoBajaMonitor = null;
    private ControladorPrinicipal controlador = null;
    SimpleDateFormat formatoFecha = null;

    public ControladorMonitores(Conexion conexion, VistaMonitor vMonitor, MonitorDAO vMonitorDAO, ControladorPrinicipal controlador) {
        this.conexion = conexion;
        this.vMonitor = vMonitor;
        this.vMonitorDAO = vMonitorDAO;
        this.controlador = controlador;
        
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        vAvisoBajaMonitor = new VistaAvisoBajaMonitor();
        vNuevoMonitor = new VistaNuevoMonitor();
        vActualizarMonitor = new VistaActualizarMonitor();

        addListeners();

        vNuevoMonitor.setLocationRelativeTo(vMonitor);
        vNuevoMonitor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        vAvisoBajaMonitor.setLocationRelativeTo(vMonitor);
        vAvisoBajaMonitor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        vActualizarMonitor.setLocationRelativeTo(vMonitor);
        vActualizarMonitor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    private void addListeners() {
        vMonitor.botonNuevoMonitor.addActionListener(this);
        vMonitor.botonBajaMonitor.addActionListener(this);
        vMonitor.botonActualizarMonitor.addActionListener(this);

        vNuevoMonitor.botonCancelarMonitor.addActionListener(this);
        vNuevoMonitor.botonInsertarNuevoMonitor.addActionListener(this);

        vAvisoBajaMonitor.botonCancelarBajaMonitor.addActionListener(this);
        vAvisoBajaMonitor.botonConfirmarBajaMonitor.addActionListener(this);

        vActualizarMonitor.botonActualizarMonitor.addActionListener(this);
        vActualizarMonitor.botonCancelarMonitor.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VentanaNuevoMonitor":
                rellenaCampoCodigoNuevoMonitor();
                muestraDialog(vNuevoMonitor);
                break;
            case "BajaMonitor":
                muestraDialog(vAvisoBajaMonitor);
                break;
            case "VentanaActualizarMonitor":
                rellenaCamposActualizarMonitor();
                muestraDialog(vActualizarMonitor);
                break;
            case "BotonCancelarMonitor":
                vNuevoMonitor.setVisible(false);
                vActualizarMonitor.setVisible(false);
                vAvisoBajaMonitor.setVisible(false);
                break;
            case "BotonInsertarNuevoMonitor": 
                try {
                    insertarNuevoMonitor();
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                } catch (Exception ex) {
                Logger.getLogger(ControladorMonitores.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "BotonBajaMonitor":
                try {
                    bajaMonitor();
                } catch (SQLException ex) {
                vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                }
            break;
            case "BotonActualizarMonitor":
                try {
                    actualizarMonitor();
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la peticion\n");
                } catch (Exception ex) {
                Logger.getLogger(ControladorMonitores.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

        }
    }

    public void muestraDialog(JDialog dialog) {
        vNuevoMonitor.setVisible(false);
        vAvisoBajaMonitor.setVisible(false);
        vActualizarMonitor.setVisible(false);

        dialog.setVisible(true);
    }

    public void rellenaCampoCodigoNuevoMonitor() {
        String codigo = "";
        int numero;
        numero = vMonitor.jTableMonitores.getRowCount();
        numero++;
        if (numero < 10 && numero >= 0) {
            codigo = "M" + "00" + numero;
        } else if (numero < 100) {
            codigo = "M" + "0" + numero;
        } else if (numero < 1000) {
            codigo = "M" + numero;
        } else {
            System.out.println("Error en el número de monitores");
        }
        vNuevoMonitor.campoCodigoNuevoMonitor.setText(codigo);
    }

    public void rellenaCamposActualizarMonitor() {
        int fila = vMonitor.jTableMonitores.getSelectedRow();
        vActualizarMonitor.campoCodigoActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 0).toString());
        vActualizarMonitor.campoNombreActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 1).toString());
        vActualizarMonitor.campoDniActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 2).toString());
        vActualizarMonitor.campoTelefonoActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 3).toString());
        vActualizarMonitor.campoCorreoActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 4).toString());
        try{
            Date fecha = formatoFecha.parse(vMonitor.jTableMonitores.getValueAt(fila, 5).toString());
            vActualizarMonitor.campoFechaActualizarMonitor.setDate(fecha);
        }catch(ParseException ex){
            
        }
        vActualizarMonitor.campoNickActualizarMonitor.setText(vMonitor.jTableMonitores.getValueAt(fila, 6).toString());
    }

    public void insertarNuevoMonitor() throws SQLException, Exception {
        Monitor monitor = null;
        String codMonitor = vNuevoMonitor.campoCodigoNuevoMonitor.getText();
        String nom = vNuevoMonitor.campoNombreNuevoMonitor.getText();
        String dni = vNuevoMonitor.campoDniNuevoMonitor.getText();
        String telefono = vNuevoMonitor.campoTelefonoNuevoMonitor.getText();
        String correo = vNuevoMonitor.campoCorreoNuevoMonitor.getText();
        Date fechaDate = vNuevoMonitor.campoFechaNuevoMonitor.getDate();
        String nick = vNuevoMonitor.campoNickNuevoMonitor.getText();
        
        String fechaString = null;
        if(fechaDate != null){
            fechaString = formatoFecha.format(fechaDate);
        }
        monitor = new Monitor(codMonitor, nom, dni, telefono, correo, fechaString, nick);
        vMonitorDAO.insertaMonitor(monitor);
        vNuevoMonitor.dispose();
        controlador.pideMonitores();
    }
    
    public void bajaMonitor() throws SQLException {
        int fila = vMonitor.jTableMonitores.getSelectedRow();
        String codigoMonitor = (vMonitor.jTableMonitores.getValueAt(fila, 0)).toString();
        try {
            vMonitorDAO.borrarMonitor(codigoMonitor);
        } catch (SQLException ex) {
            vMensaje.Mensaje(vMonitor, "error", "Error en la peticion\n");
        }
        vAvisoBajaMonitor.dispose();
        controlador.pideMonitores();
    }

    public void actualizarMonitor() throws SQLException, Exception {
        Monitor monitor = null;
        String codMonitor = vActualizarMonitor.campoCodigoActualizarMonitor.getText();
        String nom = vActualizarMonitor.campoNombreActualizarMonitor.getText();
        String dni = vActualizarMonitor.campoDniActualizarMonitor.getText();
        String telefono = vActualizarMonitor.campoTelefonoActualizarMonitor.getText();
        String correo = vActualizarMonitor.campoCorreoActualizarMonitor.getText();
        Date fechaDate = vActualizarMonitor.campoFechaActualizarMonitor.getDate();
        String nick = vActualizarMonitor.campoNickActualizarMonitor.getText();

        String fechaString = null;
        if(fechaDate != null){
            fechaString = formatoFecha.format(fechaDate);
        }
        
        monitor = new Monitor(codMonitor, nom, dni, telefono, correo, fechaString, nick);
        try{
            vMonitorDAO.actualizarMonitor(monitor);
        }catch(SQLException ex){
            System.out.println("Excepcion en actualizar");
        }
        vActualizarMonitor.dispose();
        controlador.pideMonitores();
    }
}
