/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Aplicacion;

import Controlador.*;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author alepd
 */
public class Aplicacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        ControladorLogin l = new ControladorLogin();
    }
    
}

/*

CONSULTA QUE DEVUELVE UN VALOR ÚNICO

public String ultimoMonitor() throws Exception {
Transaction transaction = sesion.beginTransaction();
Query consulta = sesion.createQuery("SELECT MAX(m.codmonitor) FROM Monitor m");
String codUltimoMonitor = consulta.getSingleResult().toString();
transaction.commit();
return codUltimoMonitor;
}

CREACION DE UNA NUEVA ACTIVIDAD ASIGNANDOLE UN MONITOR RESPONSABLE

Transaction transaction = sesion.beginTransaction();
Monitor monitor = sesion.get(Monitor.class, "M010");
if (monitor == null) {
System.out.println("Monitor no existe en la BD");
} else {
Actividad actividad = new Actividad("AC99", "Nueva Actividad","Muy dura",30);
actividad.setMonitorresponsable(monitor);
monitor.getActividadesResponsable().add(actividad);
sesion.save(actividad);
}
transaction.commit();

ACTUALIZACION DEL RESPONSABLE DE UNA ACTIVIDAD

Transaction transaction = sesion.beginTransaction();
Monitor monitor = sesion.get(Monitor.class, "M010");
if (monitor == null) {
System.out.println("Monitor no existe en la BD");
} else {
Actividad actividad = sesion.get(Actividad.class, "AC01");
actividad.setMonitorresponsable(monitor);
monitor.getActividadesResponsable().add(actividad);
sesion.save(actividad);
}
transaction.commit();

LISTADO DEL NOMBRE DE LAS ACTIVIDADES JUNTO CON EL NOMBRE Y EL CORREO ELECTRONICO DEL MONITOR RESPONSABLE

Transaction transaction = sesion.beginTransaction();
Query consulta = sesion.createQuery("SELECT a FROM Actividad a", Actividad.class);
List<Actividad> actividades = consulta.list();
for (Actividad actividad : actividades) {
System.out.println(actividad.getNombre() + "\t"
+ actividad.getMonitorresponsable().getNombre() + "\t"
+ actividad.getMonitorresponsable().getCorreo());
}
transaction.commit();

LISTADO DEL NOMBRE DE LOS MONITORES Y LAS ACTIVIDADES DE LAS QUE SON RESPONSABLES

Transaction transaction = sesion.beginTransaction();
Query consulta = sesion.createQuery("SELECT m FROM Monitor m", Monitor.class);
List<Monitor> monitores = consulta.list();
for (Monitor monitor : monitores) {
System.out.println(monitor.getNombre());
Set<Actividad> actividades = monitor.getActividadesResponsable();
for (Actividad actividad : actividades)
System.out.println(actividad.getNombre());
}
transaction.commit();

CREA UN NUEVO SOCIO, LE ASIGNA UNA ACTIVIDAD "AC01" Y LO INSERTA EN LA BD

Transaction transaction = sesion.beginTransaction();
Socio socioNuevo = new Socio("S999", "Nuevo Socio", "11222333F", "20/10/2022", 'A');
Actividad actividad = sesion.get(Actividad.class, "AC01");
actividad.addSocio(socioNuevo);
sesion.save(socioNuevo); // es necesario porque es un nuevo socio en la BD
sesion.save(actividad);
transaction.commit();

ASIGNA UNA ACTIVIDAD QUE YA EXISTE A UN SOCIO QUE YA EXISTE

Transaction transaction = sesion.beginTransaction();
Socio socio = sesion.get(Socio.class, "S999");
Actividad actividad = sesion.get(Actividad.class, "AC03");
actividad.addSocio(socio);
// una vez asignadas las relaciones en los dos sentidos, se puede realizar
// una operación save() de cualquiera de los dos objetos para que
// se almacene la tupla en la tabla intermedia
sesion.save(actividad); // también podríamos haber usado sesion.save(socio)
transaction.commit();

ELIMINA A UN SOCIO, Y DE CAMINO UN SOCIO DE UNA ACTIVIDAD

Transaction transaction = sesion.beginTransaction();
Socio socio = sesion.get(Socio.class, "S999");
Actividad actividad = sesion.get(Actividad.class, "AC03");
actividad.eliminaSocio(socio);
sesion.save(actividad);
transaction.commit();

LISTADO DEL NOMBRE DE LOS SOCIOS JUNTO CON EL NOMBRE DE LAS ACTIVIDADES QUE REALIZA

Transaction transaction = sesion.beginTransaction();
Query consulta = sesion.createNativeQuery("SELECT * FROM SOCIO S", Socio.class);
List<Socio> socios = consulta.list();
for (Socio socio : socios) {
System.out.println(socio.getNombre());
Set<Actividad> actividades = socio.getActividades();
for (Actividad actividad : actividades )
System.out.println(actividad.getNombre());
}
transaction.commit();    

*/