
package Modelo;

import java.sql.*;
import static java.sql.DriverManager.getConnection;
import Vista.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class Conexion {

    private Connection conexion;
    private String cadena;
    private VistaConsola vista;
    private VistaMensaje mensaje;

    public Conexion(String sgbd, String ip, String servicio_bd, String usuario, String password) throws SQLException{
        vista = new VistaConsola();  mensaje = new VistaMensaje();
        try {
            //conexion = getConnection(cadena);
            if (sgbd.equals("oracle")) {
            System.out.println(servicio_bd);
            cadena = "jdbc:oracle:thin:@" + ip + ":1521:" + servicio_bd;
            System.out.println(cadena);
            conexion = DriverManager.getConnection(cadena, usuario, password);
            
        } else if(sgbd.equals("mariadb")) {
            cadena = "jdbc:mariadb://" + ip + ":3306"  + "/" + usuario;
        }
            vista.mensajeConsola("Se ha conectado exitosamente a la Base de Datos");
            mensaje.exito(); 
        }       

            catch (SQLException e) {
            vista.mensajeConsola("Error al conectarse");
            mensaje.error(e);
        } 

    }
    
    public void desconexion(){
         try {
            conexion.close();
             vista.mensajeConsola("Se ha desconectado exitosamente de la Base de Datos");
         }
         catch (SQLException e) {
             vista.mensajeConsola("Error al desconectarse");
         }  
    }
    
    public Connection getConexion(){
        return this.conexion;
    }
    
    public DatabaseMetaData informacionBD() throws SQLException{
            return conexion.getMetaData();
    }
   
}
