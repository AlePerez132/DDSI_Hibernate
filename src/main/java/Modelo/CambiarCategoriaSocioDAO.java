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
public class CambiarCategoriaSocioDAO {
    Conexion conexion = null;
    Session sesion = null;
    
    public CambiarCategoriaSocioDAO(Session sesion){
        this.sesion = sesion;
    }
    
    public Socio buscarSocio(String codigo){
        Transaction transaccion = sesion.beginTransaction();
        
        String hql = "SELECT * FROM socio WHERE numerosocio LIKE :search";
        Query consulta = sesion.createNativeQuery(hql, Socio.class);
        consulta.setParameter("search", codigo);
        ArrayList<Socio> socios = (ArrayList<Socio>)consulta.list();
        Socio socio = socios.get(0);
        
        transaccion.commit();
        return socio;
    }
    
    public void actualizarCategoriaSocio(Socio S) throws Exception
    {
       Socio socio = sesion.get(Socio.class, S.getNumerosocio());
       
       socio.setCategoria(S.getCategoria());
       
       Transaction transaccion = sesion.beginTransaction();
       sesion.save(socio);
       transaccion.commit();
    }
}
