/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


/**
 *
 * @author alepd
 */
public class MonitorDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public MonitorDAO(Session sesion){
        this.sesion = sesion;
    }
 
    public ArrayList<Monitor> listaMonitores() throws SQLException {
        Transaction transaction = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT m FROM Monitor m", Monitor.class);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();
        transaction.commit();
        return monitores;
    }

    public ArrayList <Monitor> listaMonitorPorLetra(String letra) throws SQLException{
        ArrayList listaMonitores;
        listaMonitores = new ArrayList();
        PreparedStatement ps = null;
        
        String consulta = "SELECT * FROM MONITOR WHERE nombre LIKE ?";
        ps = conexion.getConexion().prepareStatement(consulta);
        letra = letra + "%";
        ps.setString(1, letra);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Monitor monitor = new Monitor(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getString(4), rs.getString(5),
                rs.getString(6), rs.getString(7));
            listaMonitores.add(monitor);
        }
        
        return listaMonitores;
    }
    
    public void insertaMonitor(Monitor monitor) throws Exception {
        Transaction transaction = sesion.beginTransaction();
        sesion.save(monitor);
        transaction.commit();
    }
    
    /*public void actualizarMonitor(Monitor m) throws SQLException {
        Transaction transaction = sesion.beginTransaction();
        sesion.save(m); // FALLO
        transaction.commit();
    }*/
    
    public void actualizarMonitor(Monitor m) throws Exception
    {
       Monitor monitor = sesion.get(Monitor.class, m.getCodmonitor());
       monitor.setActividadResponsable(m.getActividadResponsable());
       monitor.setCorreo(m.getCorreo());
       monitor.setDni(m.getDni());
       monitor.setFechaentrada(m.getFechaentrada());
       monitor.setNick(m.getNick());
       monitor.setNombre(m.getNombre());
       monitor.setTelefono(m.getTelefono());
       
       Transaction transaccion = sesion.beginTransaction();
       sesion.save(monitor);
       transaccion.commit();
    }

    public void borrarMonitor(String codMonitor) throws SQLException {
        Transaction transaction = sesion.beginTransaction();
        Monitor monitor = sesion.get(Monitor.class, codMonitor);
        sesion.delete(monitor);
        transaction.commit();
    }
    
    public ArrayList<String> NumeroDeActividadesResponsable(String codigo) throws  Exception 
    {                                                                              
        Query consulta = sesion.createNativeQuery("SELECT count(monitorresponsable) FROM ACTIVIDAD WHERE monitorresponsable = :mR").setParameter("mR", codigo);
        
        ArrayList<String> numero = (ArrayList<String>)consulta.list();
        
        return numero;
    }
}
