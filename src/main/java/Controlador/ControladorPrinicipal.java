package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.hibernate.Session;

/**
 *
 * @author alepd
 */

public class ControladorPrinicipal implements ActionListener {

    private Conexion conexion = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private MonitorDAO vMonitorDAO = null;
    private MonitorTablas mTablas = null;
    private VistaMonitor vMonitor = null;
    private VistaSocio vSocio = null;
    private jPanelPrincipal vPanel = null;
    private ControladorMonitores cMonitores = null;
    private SocioTablas sTablas = null;
    private SocioDAO vSocioDAO = null;
    private ControladorSocios cSocios = null;
    private ActividadDAO vActividadDAO = null;
    private VistaActividad vActividad = null;
    private ActividadTablas aTablas = null;
    private ControladorActividades cActividades = null;
    private Session sesion = null;
    private VistaCuotaSocio vCuotaSocio = null;
    private CuotaSocioDAO vCuotaSocioDAO = null;
    private ControladorCuotaSocio cCuotaSocio = null;
    private CuotaSocioTablas cSTablas = null;
    private VistaCambiarCategoriaSocio vCambiarCategoriaSocio = null;
    private ControladorCambiarCategoriaSocio cCambiarCategoriaSocio = null;
    private CambiarCategoriaSocioDAO vCambiarCategoriaSocioDAO = null;
    private VistaCuotaMonitor vCuotaMonitor = null;
    private CuotaMonitorTablas cMTablas = null;
    private ControladorCuotaMonitor cCuotaMonitor = null;
    private CuotaMonitorDAO vCuotaMonitorDAO = null;
    
    private VistaCuotaActividad vCuotaActividad = null;
    private ControladorCuotaActividad cCuotaActividad = null;
    private CuotaActividadDAO vCuotaActividadDAO = null;
    private CuotaActividadTablas cATablas = null;
    
    public ControladorPrinicipal(Session sesion) {
        this.sesion=sesion;
        vMensaje = new VistaMensaje();
        vMonitor = new VistaMonitor();
        vPrincipal = new VistaPrincipal();
        vActividad = new VistaActividad();
        mTablas = new MonitorTablas();
        vSocio = new VistaSocio();
        vMonitorDAO = new MonitorDAO(sesion);
        vPanel = new jPanelPrincipal();
        sTablas = new SocioTablas();
        vSocioDAO = new SocioDAO(sesion);
        vActividadDAO = new ActividadDAO(sesion);
        aTablas = new ActividadTablas();
        vCuotaSocio = new VistaCuotaSocio();
        vCuotaSocioDAO = new CuotaSocioDAO(sesion);
        cSTablas = new CuotaSocioTablas();
        vCambiarCategoriaSocio = new VistaCambiarCategoriaSocio();
        vCambiarCategoriaSocioDAO = new CambiarCategoriaSocioDAO(sesion);
        vCuotaMonitor = new VistaCuotaMonitor();
        cMTablas = new CuotaMonitorTablas();
        vCuotaMonitorDAO = new CuotaMonitorDAO(sesion);
        
        vCuotaActividad = new VistaCuotaActividad();
        vCuotaActividadDAO = new CuotaActividadDAO(sesion);
        cATablas = new CuotaActividadTablas();
        
        addListeners();

        vPrincipal.setLocationRelativeTo(null);
        vPrincipal.setVisible(true);

        vPrincipal.getContentPane().setLayout(new CardLayout());
        vPrincipal.add(vPanel);
        vPrincipal.add(vMonitor);
        vPrincipal.add(vSocio);
        vPrincipal.add(vActividad);
        vPrincipal.add(vCuotaSocio);
        vPrincipal.add(vCambiarCategoriaSocio);
        vPrincipal.add(vCuotaMonitor);
        vPrincipal.add(vCuotaActividad);


        mTablas.inicializarTablaMonitores(vMonitor);
        sTablas.inicializarTablaSocios(vSocio);
        aTablas.inicializarTablaActividades(vActividad);
        cSTablas.inicializarTablaCuotaSocios(vCuotaSocio);
        cMTablas.inicializarTablaCuotaMonitor(vCuotaMonitor);
        cATablas.inicializarTablaCuotaActividad(vCuotaActividad);
    }

    private void addListeners() {
        vPrincipal.jMenuItemSalir.addActionListener(this);
        vPrincipal.jMenuItemSocios.addActionListener(this);
        vPrincipal.jMenuItemMonitores.addActionListener(this);
        vPrincipal.jMenuItemActividades.addActionListener(this);
        vPrincipal.jMenuItemCuotaSocios.addActionListener(this);
        vPrincipal.jMenuItemCambiarCategoriaSocio.addActionListener(this);
        vPrincipal.jMenuItemCuotaMonitor.addActionListener(this);
        vPrincipal.jMenuItemCuotaActividad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cerrar":
                vPrincipal.dispose();
                System.exit(0);
                break;
            case "GestionMonitores":
                muestraPanel(vMonitor);
                mTablas.dibujarTablaMonitores(vMonitor);
                try {
                    pideMonitores();
                    cMonitores = new ControladorMonitores(conexion, vMonitor, vMonitorDAO, this);
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la petición\n"
                            + ex.getMessage());
                }
                break;
                
            case "CuotaMonitor":{
                muestraPanel(vCuotaMonitor);
                cCuotaMonitor = new ControladorCuotaMonitor(conexion, vCuotaMonitor, vCuotaMonitorDAO, this, cMTablas);
            }
                break;
            case "GestionSocios":
                muestraPanel(vSocio);
                sTablas.dibujarTablaSocios(vSocio);
                try {
                    pideSocios();
                    cSocios = new ControladorSocios(conexion, vSocio, vSocioDAO, this, sTablas, sesion);
                } catch (SQLException ex) {
                    vMensaje.Mensaje(null, "error", "Error en la petición\n"
                            + ex.getMessage());
                } catch (Exception ex) {
                Logger.getLogger(ControladorPrinicipal.class.getName()).log(Level.SEVERE, null, ex);
            }
                break;

            case "GestionActividades":
                muestraPanel(vActividad);
                aTablas.dibujarTablaActividades(vActividad);
                cActividades = new ControladorActividades(conexion, vActividad, vActividadDAO, this, aTablas);
                break;
            
            case "CuotaActividad":{
                muestraPanel(vCuotaActividad);
                cCuotaActividad = new ControladorCuotaActividad(conexion, vCuotaActividad, vCuotaActividadDAO, this, cATablas);
            }
                break;
            case "CuotaSocio":
                muestraPanel(vCuotaSocio);
                cCuotaSocio = new ControladorCuotaSocio(conexion, vCuotaSocio, vCuotaSocioDAO, this, cSTablas);
                break;
               
            case "CambiarCategoriaSocio":
                muestraPanel(vCambiarCategoriaSocio);
                cCambiarCategoriaSocio = new ControladorCambiarCategoriaSocio(conexion, vCambiarCategoriaSocio, vCambiarCategoriaSocioDAO, this);
                break;
        }
    }
    
    public void pideMonitores() throws SQLException {
        ArrayList<Monitor> lMonitores = vMonitorDAO.listaMonitores();
        mTablas.vaciarTablaMonitores();
        mTablas.rellenarTablaMonitores(lMonitores);
    }

    public void pideSocios() throws SQLException, Exception {
        ArrayList<Socio> lSocios = vSocioDAO.listaSocios();
        sTablas.vaciarTablaSocios();
        sTablas.rellenarTablaSocios(lSocios);
    }
    
    public void pideActividades() throws SQLException, Exception{
       ArrayList<Object[]> lActividades = vActividadDAO.listaDeSociosPorActividad("AC01");
        aTablas.vaciarTablaActividades();
        aTablas.rellenarTablaActividades(lActividades);
    }

    private void muestraPanel(JPanel panel) {
        vPanel.setVisible(false);
        vMonitor.setVisible(false);
        vSocio.setVisible(false);
        vActividad.setVisible(false);
        vCuotaSocio.setVisible(false);
        vCambiarCategoriaSocio.setVisible(false);
        vCuotaMonitor.setVisible(false);
        vCuotaActividad.setVisible(false);
        
        panel.setVisible(true);
    }
}
