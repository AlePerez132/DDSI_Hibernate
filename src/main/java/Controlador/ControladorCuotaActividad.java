/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Conexion;
import Modelo.CuotaActividadDAO;
import Modelo.CuotaActividadTablas;
import Vista.VistaCuotaActividad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorCuotaActividad implements ActionListener {
    Conexion conexion = null;
    VistaCuotaActividad vCuotaActividad = null;
    CuotaActividadDAO vCuotaActividadDAO = null;
    ControladorPrinicipal controlador = null;
    CuotaActividadTablas cATablas = null;
    
    public ControladorCuotaActividad(Conexion conexion, VistaCuotaActividad vCuotaActividad, CuotaActividadDAO vCuotaActividadDAO, ControladorPrinicipal controlador, CuotaActividadTablas cActividadTablas){
        this.conexion = conexion;
        this.vCuotaActividad = vCuotaActividad;
        this.vCuotaActividadDAO = vCuotaActividadDAO;
        this.controlador = controlador;
        this.cATablas = cActividadTablas;
        
        addListeners();
        
        cATablas.inicializarTablaCuotaActividad(vCuotaActividad);
    }
    
    private void addListeners() {
        vCuotaActividad.botonVerCuota.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VerCuota":
                String codActividad = vCuotaActividad.jCampoCodigoActividad.getText();
                System.out.println("MOSTRANDO CODIGO ACTIVIDAD: " + codActividad);
                Actividad actividad = null;
                int numeroSocios = 0;
                int precioActividad = 0;
                float cuotaMensualSin = 0;
                float cuotaMensualCon = 0;
                ArrayList<Object[]> socios = null;
                try {
                    actividad = vCuotaActividadDAO.obtenerActividad(codActividad);
                    socios = vCuotaActividadDAO.listaSociosPorActividad(codActividad);
                    precioActividad = actividad.getPreciobasemes();
                    System.out.println("EL PRECIO DE LA ACTIVIDAD ES " + String.valueOf(precioActividad));
                    numeroSocios=socios.size();
                    System.out.println("EL NUMERO DE SOCIOS DE LA ACTIVIDAD ES " + String.valueOf(numeroSocios));
                    cuotaMensualSin = vCuotaActividadDAO.obtenerCuotaMensualSin(actividad, numeroSocios);
                    cuotaMensualCon = vCuotaActividadDAO.obtenerCuotaMensualCon(actividad, numeroSocios, socios);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCuotaActividad.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cATablas.dibujarTablaCuotaActividades(vCuotaActividad);
                cATablas.vaciarTablaCuotaActividades();
                cATablas.rellenarTablaCuotaActividades(socios);
                
                vCuotaActividad.jLabelNumeroSocios.setText(String.valueOf(numeroSocios));
                vCuotaActividad.jLabelPrecioActividad.setText(String.valueOf(precioActividad));
                vCuotaActividad.jLabelCuotaMensualSin.setText(String.valueOf(cuotaMensualSin));
                vCuotaActividad.jLabelCuotaMensualCon.setText(String.valueOf(cuotaMensualCon));
                break;
        }
    }
}

