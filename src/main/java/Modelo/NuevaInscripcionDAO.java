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
public class NuevaInscripcionDAO {

    private Conexion conexion;
    private Session sesion;
    private HibernateUtilOracle HibernateUtilOracle;

    public NuevaInscripcionDAO(Session sesion) {
        this.sesion = sesion;
        HibernateUtilOracle = new HibernateUtilOracle();
    }
    
    public ArrayList<Object[]> listaNuevasInscripciones(String idSocio) throws Exception{
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT A.idactividad, A.nombre, A.descripcion, A.preciobasemes, A.monitorresponsable "
        + "FROM actividad A "
        + "WHERE A.idactividad NOT IN ("
        + "    SELECT R.idactividad"
        + "    FROM realiza R"
        + "    WHERE R.numerosocio = :idSocio)").setParameter("idSocio", idSocio);
        ArrayList<Object[]> actividades = (ArrayList<Object[]>)consulta.list();
        
        transaccion.commit();
        return actividades;
    }
    
    public boolean insertarActividadDeSocio(String idSocio, String idActividad) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        boolean insertado=false;
        try{
            Socio socio = sesion.get(Socio.class, idSocio);
            Actividad actividad = sesion.get(Actividad.class, idActividad);
            actividad.addSocio(socio);
        // una vez asignadas las relaciones en los dos sentidos, se puede realizar
        // una operación save() de cualquiera de los dos objetos para que
        // se almacene la tupla en la tabla intermedia
            sesion.save(actividad); // también podríamos haber usado sesion.save(socio)
            transaccion.commit();
        }catch(Exception e){
            transaccion.rollback();
            System.out.println("Excepcion al insertar socio");
        }
        
        return insertado;
    }
}