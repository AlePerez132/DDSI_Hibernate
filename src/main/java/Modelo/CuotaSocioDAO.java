/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author alepd
 */
public class CuotaSocioDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public CuotaSocioDAO(Session sesion){
        this.sesion = sesion;
    }
 
    public Socio obtenerSocio(String codigo) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        
        String hql = "SELECT * FROM socio WHERE numerosocio LIKE :search";
        Query consulta = sesion.createNativeQuery(hql, Socio.class);
        consulta.setParameter("search",codigo);
        ArrayList<Socio> socios = (ArrayList<Socio>)consulta.list();
        Socio socio = socios.get(0);
       
        transaccion.commit();
        return socio;
    }
    
    /*public ArrayList<Actividad> obtenerActividadPorCodigoSocio(String codigo) throws SQLException{
        Transaction transaccion = sesion.beginTransaction();
        
        Query consulta = sesion.createNativeQuery("SELECT * FROM ACTIVIDAD A INNER JOIN REALIZA R ON A.idactividad=R.idactividad"
                + " WHERE R.numerosocio = :codigoSocio").setParameter("codigoSocio", codigo);
        ArrayList<Actividad> actividades = (ArrayList<Actividad>)consulta.list();
                
        transaccion.commit();
        return actividades;
    }*/
    
    public ArrayList<Object[]> listaActividadesPorSocio(String idSocio) throws Exception{
        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNativeQuery("SELECT DISTINCT A.idactividad, A.nombre, A.descripcion, A.preciobasemes, A.monitorresponsable"
                + " FROM actividad A INNER JOIN REALIZA R ON A.idactividad=R.idactividad"
                + " WHERE R.numerosocio = :idSocio").setParameter("idSocio", idSocio);
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
    
    
    public int obtenerCuotaMensual(ArrayList<Object[]> actividades){
       int CuotaMensual = 0; 
       int numRegistros = actividades.size();
       for(int i = 0; i<numRegistros; i++){
           CuotaMensual += Integer.parseInt(actividades.get(i)[3].toString());
           System.out.println("Cuota Mensual es igual a " + CuotaMensual);
       }
       
       return CuotaMensual;
    }
    
    public float obtenerTotalConDescuento(int CuotaMensual, Character categoria){
        float TotalConDescuento = CuotaMensual;
        switch(categoria){
            case 'B':
                TotalConDescuento = (float) (TotalConDescuento * 0.9);
                break;
            case 'C':
                TotalConDescuento = (float) (TotalConDescuento * 0.8);
                break;
            case 'D':
                TotalConDescuento = (float) (TotalConDescuento * 0.7);
                break;
            case 'E':
                TotalConDescuento = (float) (TotalConDescuento * 0.6);
                break;
        }
        return TotalConDescuento;
    }
}

