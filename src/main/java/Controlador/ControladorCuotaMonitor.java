/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.CuotaMonitorDAO;
import Modelo.CuotaMonitorTablas;
import Modelo.Monitor;
import Vista.VistaCuotaMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorCuotaMonitor implements ActionListener {
    Conexion conexion = null;
    VistaCuotaMonitor vCuotaMonitor = null;
    CuotaMonitorDAO vCuotaMonitorDAO = null;
    ControladorPrinicipal controlador = null;
    CuotaMonitorTablas cMTablas = null;
    
    public ControladorCuotaMonitor(Conexion conexion, VistaCuotaMonitor vCuotaMonitor, CuotaMonitorDAO vCuotaMonitorDAO, ControladorPrinicipal controlador, CuotaMonitorTablas cMonitorTablas){
        this.conexion = conexion;
        this.vCuotaMonitor = vCuotaMonitor;
        this.vCuotaMonitorDAO = vCuotaMonitorDAO;
        this.controlador = controlador;
        this.cMTablas = cMonitorTablas;
        
        addListeners();
        
        cMTablas.inicializarTablaCuotaMonitor(vCuotaMonitor);
    }
    
    private void addListeners() {
        vCuotaMonitor.botonVerCuota.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VerCuota":
                String codMonitor = vCuotaMonitor.jCampoCodigoMonitor.getText();
                System.out.println("MOSTRANDO CODIGO MONITOR: " + codMonitor);
                Monitor Monitor = null;
                String nombreMonitor = null;
                int numeroSociosTotal = 0;
                ArrayList<Object[]> actividades = null;
                int[] listaNumeroSocios = null;
                int numeroActividades = 0;
                int cuotaMensual = 0;
                
                try {
                    Monitor = vCuotaMonitorDAO.obtenerMonitor(codMonitor);
                    nombreMonitor = Monitor.getNombre();
                    actividades = vCuotaMonitorDAO.listaActividadesPorMonitor(codMonitor);
                    numeroActividades = actividades.size();
                    listaNumeroSocios = new int[numeroActividades];
                    listaNumeroSocios = vCuotaMonitorDAO.obtenerNumSocios(actividades);
                    cuotaMensual = vCuotaMonitorDAO.obtenerCuotaMensual(actividades);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCuotaMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cMTablas.dibujarTablaCuotaMonitores(vCuotaMonitor);
                cMTablas.vaciarTablaCuotaMonitores();
                cMTablas.rellenarTablaCuotaMonitores(actividades, listaNumeroSocios);
                
                vCuotaMonitor.jLabelNombreMonitor.setText(nombreMonitor);
                vCuotaMonitor.jLabelNumActividades.setText(String.valueOf(numeroActividades));
                for(int i = 0; i< numeroActividades; i++)
                {
                    numeroSociosTotal += listaNumeroSocios[i];
                }
                vCuotaMonitor.jLabelNumSocios.setText(String.valueOf(numeroSociosTotal));
                vCuotaMonitor.jLabelCuotaMensual.setText(String.valueOf(cuotaMensual));
                break;
        }
    }
}
