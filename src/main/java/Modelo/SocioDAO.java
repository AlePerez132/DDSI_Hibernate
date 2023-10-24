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
public class SocioDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public SocioDAO(Session sesion){
        this.sesion = sesion;
    }
 
    public ArrayList<Socio> listaSocios() throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        
        Query consulta = sesion.createNativeQuery("SELECT * FROM socio", Socio.class);
        ArrayList<Socio> socios = (ArrayList<Socio>)consulta.list();
       
        transaccion.commit();
        return socios;
    }
    
    public ArrayList <Socio> listaSocioPorLetra(String letra) throws SQLException{
        Transaction transaccion = sesion.beginTransaction();
        
        String hql = "SELECT * FROM socio WHERE nombre LIKE :search";
        Query consulta = sesion.createNativeQuery(hql, Socio.class);
        consulta.setParameter("search", "%" + letra + "%");
        ArrayList<Socio> socios = (ArrayList<Socio>)consulta.list();
       
        transaccion.commit();
        return socios;
    }

    
    public boolean insertarSocio(Socio S) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        boolean insertado = false;
        try 
        {
            sesion.save(S);
            transaccion.commit();
            insertado = true;
        } 
        catch (Exception e) 
        {
            transaccion.rollback();
            System.out.println("Excepcion al insertar socio");
            //JOptionPane.showMessageDialog(null, "Error al insertar el socio.\n\n" + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
        return insertado;
    }
    
    public void actualizarSocio(Socio S) throws Exception
    {
       Socio socio = sesion.get(Socio.class, S.getNumerosocio());
       socio.setActividades(S.getActividades());
       socio.setCategoria(S.getCategoria());
       socio.setCorreo(S.getCorreo());
       socio.setDni(S.getDni());
       socio.setFechaentrada(S.getFechaentrada());
       socio.setFechanacimiento(S.getFechanacimiento());
       socio.setNombre(S.getNombre());
       socio.setNumerosocio(S.getNumerosocio());
       socio.setTelefono(S.getTelefono()); 
       
       Transaction transaccion = sesion.beginTransaction();
       sesion.save(socio);
       transaccion.commit();
    }

    public boolean eliminarSocio(String numSocio) throws Exception
    {
        Transaction transaccion = sesion.beginTransaction();
        boolean eliminado = false;
        try 
        {
            Socio socio = sesion.get(Socio.class, numSocio);
            sesion.delete(socio);
            transaccion.commit();
            eliminado = true;
        } 
        catch (Exception e) 
        {
            transaccion.rollback();
            System.out.println("Excepcion al eliminar socio");
            //JOptionPane.showMessageDialog(null, "Error al eliminar el socio.\n\n" + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
        return eliminado;
    }
}
