/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author alepd
 */
public class CuotaMonitorDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public CuotaMonitorDAO(Session sesion){
        this.sesion = sesion;
    }
 
    public Monitor obtenerMonitor(String codigo) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        
        String hql = "SELECT * FROM monitor WHERE codmonitor LIKE :search";
        Query consulta = sesion.createNativeQuery(hql, Monitor.class);
        consulta.setParameter("search",codigo);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>)consulta.list();
        Monitor monitor = monitores.get(0);
       
        transaccion.commit();
        return monitor;
    }
    
    public ArrayList<Object[]> listaActividadesPorMonitor(String codMonitor) throws Exception{
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT A.idactividad, A.nombre, A.descripcion, A.preciobasemes, A.monitorresponsable"
                + " FROM actividad A INNER JOIN monitor M ON A.monitorresponsable=M.codmonitor"
                + " WHERE M.codmonitor = :codMonitor").setParameter("codMonitor", codMonitor);
        ArrayList<Object[]> actividades = (ArrayList<Object[]>)consulta.list();
        /*
        for(int i = 0; i < actividades.size(); i++)
        {
            System.out.println("Id de la actividad " + i + " es " + actividades.get(i)[0].toString());
            System.out.println("Nombre de la actividad " + i + " es " + actividades.get(i)[1].toString());
            System.out.println("Descripcion de la actividad " + i + " es " + actividades.get(i)[2].toString());
            System.out.println("Precio de la actividad " + i + " es " + actividades.get(i)[3]);
        }
        */
        transaccion.commit();
        return actividades;
    }
    
    public int[] obtenerNumSocios(ArrayList<Object[]> actividades){
        Transaction transaccion = sesion.beginTransaction();
        int numActividades = actividades.size();
        int[] numeroSocios = new int[numActividades];
        Query consulta;
        for(int i = 0; i < numActividades; i++)
        {
            String codActividad = actividades.get(i)[0].toString();
            System.out.println("EL CODIGO DE ACTIVIDAD ES " + codActividad);
            consulta = sesion.createNativeQuery("SELECT DISTINCT numerosocio FROM realiza R INNER JOIN actividad A ON R.idactividad=A.idactividad WHERE A.idactividad = :codActividad").setParameter("codActividad", codActividad);
            ArrayList<Object[]> socios = (ArrayList<Object[]>)consulta.list(); 
            numeroSocios[i] = socios.size();
        }
        for(int i= 0; i < numActividades; i++)
        {
            System.out.println("En la actividad " + i + " hay un total de " + numeroSocios[i] + " socios");
        }
        transaccion.commit();
        return numeroSocios;
    }
    
    public int obtenerCuotaMensual(ArrayList<Object[]> actividades){
       int CuotaMensual = 0; 
       int numRegistros = actividades.size();
       for(int i = 0; i<numRegistros; i++){
           CuotaMensual += Integer.parseInt(actividades.get(i)[3].toString());
           System.out.println("Cuota Mensual es igual a " + CuotaMensual);
       }
       
       return CuotaMensual;
    }
    
    
}