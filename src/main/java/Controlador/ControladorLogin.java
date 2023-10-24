package Controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Vista.*;
import Modelo.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

public class ControladorLogin implements ActionListener {

    private VistaLogin vLogin;
    private VistaPrincipal vPrincipal;
    private VistaMensaje vMensaje;
    private VistaConsola vConsola;
    private Conexion conexion;
    private Session sesion;

    public ControladorLogin() {
        vLogin = new VistaLogin();
        vMensaje = new VistaMensaje();
        vConsola = new VistaConsola();

        addListeners();

        vLogin.setLocationRelativeTo(null);
        vLogin.setVisible(true);

        vLogin.CampoIP.setText("172.17.20.39");
        vLogin.CampoServidor.setText("etsi");
        vLogin.CampoUsuario.setText("DDSI_011");
        vLogin.CampoContrasena.setText("DDSI_011");
    }

    private void addListeners() {
        vLogin.BotonSalir.addActionListener(this);
        vLogin.BotonConectar.addActionListener(this);
    }

    /*public Conexion conectar() throws SQLException, ClassNotFoundException {
        String server = "";
        String ComboBox = (String) vLogin.ComboBoxServicioBD.getSelectedItem();
        if ("Oracle".equals(ComboBox)) {
            server = "oracle";
        } else if ("MariaDB".equals(ComboBox)) {
            server = "mariadb";
        }
        String ip = vLogin.CampoIP.getText();
        String bd = vLogin.CampoServidor.getText();
        System.out.println(ip);
        System.out.println(server);
        System.out.println(bd);
        String u = vLogin.CampoUsuario.getText(); //"DDSI_011";
        String p = new String(vLogin.CampoContrasena.getPassword());
        System.out.println(server + " " + ip + " " + bd + " " + u + " " + p);
        return new Conexion(server, ip, bd, u, p);
    }*/

    public Session conectar() {
        String server = (String) (vLogin.jComboBoxServidores.getSelectedItem());
        if (server == "MariaDB") {
            server = "mariadb";
        } else if (server == "Oracle") {
            server = "oracle";
        }
        if ("oracle".equals(server)) {
            sesion = HibernateUtilOracle.getSessionFactory().openSession();
        } else if ("mariadb".equals(server)) {
            //sesion = HibernateUtilMariaDB.getSessionFactory().openSession();
        }
        return (sesion);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "BotonConectar":
                sesion = conectar();
                if (sesion != null) {
                    vMensaje.Mensaje(null, "info", "Conexión correcta con Hibernate");
                    vLogin.dispose();
                    ControladorPrinicipal controlador = new ControladorPrinicipal(sesion);
                } else {
                    vMensaje.Mensaje(null, "error", "Error en la conexión. No se ha podido crear una sesión\n");
                }
                break;

            case "BotonSalir":
                vLogin.dispose();
                System.exit(0);
                break;
        }
    }
}


