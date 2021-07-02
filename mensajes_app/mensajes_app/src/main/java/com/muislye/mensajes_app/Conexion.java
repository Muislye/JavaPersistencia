
package com.muislye.mensajes_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fco_g
 */
public class Conexion {
    public Connection get_connection(){
        Connection conection = null;
        try {
            conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mensajes_app","root","");
        }catch(SQLException e){
            System.out.println(e);
        }
        return conection;
    }
}
