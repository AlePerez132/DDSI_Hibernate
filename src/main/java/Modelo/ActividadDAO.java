/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.HibernateUtilOracle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author alepd
 */
public class ActividadDAO {

    private Conexion conexion;
    private Session sesion;
    private HibernateUtilOracle HibernateUtilOracle;

    public ActividadDAO(Session sesion) {
        this.sesion = sesion;
        HibernateUtilOracle = new HibernateUtilOracle();
    }

    public ArrayList<Object[]> listaDeSociosPorActividad(String idActividad) throws Exception //Esta vez no se usa un procedimiento almacenado
    {   
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT S.nombre, S.correo FROM SOCIO S INNER JOIN REALIZA R ON S.numeroSocio=R.numeroSocio"
                + " WHERE R.idactividad = :idA").setParameter("idA", idActividad);
        
        ArrayList<Object[]> socios = (ArrayList<Object[]>)consulta.list();
        
        transaccion.commit();
        return socios; 
    }
    
    /*public void actualizarMonitorResponsableAGenerico(String codigo) throws Exception
    {
        Session sesion = HibernateUtilOracle.getSessionFactory().openSession();
        Transaction transaccion = sesion.beginTransaction();
        
        Monitor monitor = sesion.get(Monitor.class, codigo);
        Monitor mGen = sesion.get(Monitor.class, "M999");
        
        if(monitor == null)
            System.out.println("\n\nEl monitor no existe en la BD\n");
        else
        {
            Set<Actividad> actividades = monitor.getActividadResponsable();
            for(Actividad actividad : actividades)
            {
                actividad.setMonitorresponsable(mGen);
                sesion.save(actividad);
            }
        }
        transaccion.commit();
    }*/
}
