/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Conexion;
import Modelo.CuotaSocioDAO;
import Modelo.CuotaSocioTablas;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.SocioTablas;
import Vista.VistaCuotaSocio;
import Vista.VistaSocio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class ControladorCuotaSocio implements ActionListener {
    Conexion conexion = null;
    VistaCuotaSocio vCuotaSocio = null;
    CuotaSocioDAO vCuotaSocioDAO = null;
    ControladorPrinicipal controlador = null;
    CuotaSocioTablas cSTablas = null;
    
    public ControladorCuotaSocio(Conexion conexion, VistaCuotaSocio vCuotaSocio, CuotaSocioDAO vCuotaSocioDAO, ControladorPrinicipal controlador, CuotaSocioTablas cSocioTablas){
        this.conexion = conexion;
        this.vCuotaSocio = vCuotaSocio;
        this.vCuotaSocioDAO = vCuotaSocioDAO;
        this.controlador = controlador;
        this.cSTablas = cSocioTablas;
        
        addListeners();
        
        cSTablas.inicializarTablaCuotaSocios(vCuotaSocio);
    }
    
    private void addListeners() {
        vCuotaSocio.botonVerCuota.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VerCuota":
                String numeroSocio = vCuotaSocio.jCampoCodigoSocio.getText();
                System.out.println("MOSTRANDO NUMERO SOCIO: " + numeroSocio);
                Socio socio = null;
                Character categoria = null;
                ArrayList<Object[]> actividades = null;
                int numeroActividades = 0;
                int CuotaMensual = 0;
                float TotalConDescuento = 0;
                
                try {
                    socio = vCuotaSocioDAO.obtenerSocio(numeroSocio);
                    categoria = socio.getCategoria();
                    actividades = vCuotaSocioDAO.listaActividadesPorSocio(numeroSocio);
                    numeroActividades = actividades.size();
                    CuotaMensual = vCuotaSocioDAO.obtenerCuotaMensual(actividades);
                    TotalConDescuento = vCuotaSocioDAO.obtenerTotalConDescuento(CuotaMensual, categoria);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCuotaSocio.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cSTablas.dibujarTablaCuotaSocios(vCuotaSocio);
                cSTablas.vaciarTablaCuotaSocios();
                cSTablas.rellenarTablaCuotaSocios(actividades);
                     
                vCuotaSocio.jLabelCategoria.setText(categoria.toString());
                vCuotaSocio.jLabelNumActividades.setText(String.valueOf(numeroActividades));
                vCuotaSocio.jLabelCuotaMensual.setText(String.valueOf(CuotaMensual));
                vCuotaSocio.jLabelTotalConDescuento.setText(String.valueOf(TotalConDescuento));
                break;


        }
    }
}
