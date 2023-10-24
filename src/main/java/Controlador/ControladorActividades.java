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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorActividades implements ActionListener {

    private Conexion conexion = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private ActividadDAO vActividadDAO = null;
    private ActividadTablas mTablas = null;
    private VistaActividad vActividad = null;
    private ControladorPrinicipal controlador = null;
    private ActividadTablas aTablas = null;

    public ControladorActividades(Conexion conexion, VistaActividad vActividad, ActividadDAO vActividadDAO, ControladorPrinicipal controlador, ActividadTablas aTablas) {
        this.conexion = conexion;
        this.vActividad = vActividad;
        this.vActividadDAO = vActividadDAO;
        this.controlador = controlador;

        addListeners();
    }

    private void addListeners() {
        vActividad.botonSociosInscritos.addActionListener(this);
        vActividad.botonSalir.addActionListener(this);
        vActividad.botonVaciarTabla.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VaciarTabla":
                aTablas.vaciarTablaActividades();
                break;
            case "SociosInscritos":
                String idActividad = (vActividad.jComboBoxActividad.getSelectedItem()).toString();
                 {
                    try {
                        controlador.pideActividades();
                    } catch (SQLException ex) {
                        System.out.println("Error, excepcion al pedir actividades");
                    } catch (Exception ex) {
                Logger.getLogger(ControladorActividades.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                break;
            case "Salir":
                vActividad.setVisible(false);
                break;
        }
    }
}
