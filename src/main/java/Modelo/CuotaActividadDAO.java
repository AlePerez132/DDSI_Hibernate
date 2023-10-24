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
public class CuotaActividadDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public CuotaActividadDAO(Session sesion){
        this.sesion = sesion;
    }
 
    public Actividad obtenerActividad(String codigo) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        
        String hql = "SELECT DISTINCT * FROM Actividad WHERE idActividad LIKE :search";
        Query consulta = sesion.createNativeQuery(hql, Actividad.class);
        consulta.setParameter("search",codigo);
        ArrayList<Actividad> Actividades = (ArrayList<Actividad>)consulta.list();
        Actividad Actividad = Actividades.get(0);
       
        transaccion.commit();
        return Actividad;
    }
    
    public ArrayList<Object[]> listaSociosPorActividad(String idActividad) throws Exception
    {   
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT S.nombre, S.categoria FROM SOCIO S INNER JOIN REALIZA R ON S.numeroSocio=R.numeroSocio"
                + " WHERE R.idactividad = :idA").setParameter("idA", idActividad);
        
        ArrayList<Object[]> socios = (ArrayList<Object[]>)consulta.list();
        
        transaccion.commit();
        return socios; 
    }
    
    public float obtenerCuotaMensualSin(Actividad actividad, int numeroSocios){
       int CuotaMensual = 0;
       for(int i = 0; i<numeroSocios; i++){
           CuotaMensual += actividad.getPreciobasemes();
       }
       System.out.println("Cuota Mensual SIN DESCUENTO es igual a " + CuotaMensual);
       return CuotaMensual;
    }
    
    public float obtenerCuotaMensualCon(Actividad actividad, int numeroSocios, ArrayList<Object[]> socios){
       int CuotaMensual = 0; 
       String categoria = "";
       float precio = actividad.getPreciobasemes();
       for(int i = 0; i<numeroSocios; i++){
           categoria = socios.get(i)[1].toString();
           switch (categoria){
               case "A":
                  CuotaMensual+= precio; 
               break;
               case "B":
                  CuotaMensual += (precio * 0.9);  
               break;
               case "C":
                   CuotaMensual += (precio * 0.8);
               break;
               case "D":
                   CuotaMensual += (precio * 0.7);
               break;
               case "E":
                   CuotaMensual += (precio * 0.6);
               break;
           }
       }
       System.out.println("Cuota Mensual CON DESCUENTO es igual a " + CuotaMensual);
       return CuotaMensual;
    }  
}
