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
public class InscripcionesDAO {

    private Conexion conexion;
    private Session sesion;
    private HibernateUtilOracle HibernateUtilOracle;

    public InscripcionesDAO(Session sesion) {
        this.sesion = sesion;
        HibernateUtilOracle = new HibernateUtilOracle();
    }
    
    public ArrayList<Object[]> listaDeActividadesPorSocio(String idSocio) throws Exception{
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT A.idactividad, A.nombre, A.descripcion, A.preciobasemes, A.monitorresponsable"
                + " FROM actividad A INNER JOIN REALIZA R ON A.idactividad=R.idactividad"
                + " WHERE R.numerosocio = :idSocio").setParameter("idSocio", idSocio);
        ArrayList<Object[]> actividades = (ArrayList<Object[]>)consulta.list();
        
        transaccion.commit();
        return actividades;
    }
    
    public boolean eliminarActividadDeSocio(String idSocio, String idActividad) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        boolean eliminado = false;
        try 
        {
            Socio socio = sesion.get(Socio.class, idSocio);
            Actividad actividad = sesion.get(Actividad.class, idActividad);
            actividad.removeSocio(socio);
            sesion.save(actividad);
            transaccion.commit();
            eliminado = true;
        } 
        catch (Exception e) 
        {
            transaccion.rollback();
            System.out.println("Excepcion al eliminar la actividad " + idActividad + "del socio " + idSocio);
        }
        return eliminado;
    }
    
}