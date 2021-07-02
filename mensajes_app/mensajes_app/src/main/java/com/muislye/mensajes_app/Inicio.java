package com.muislye.mensajes_app;

import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author fco_g
 */
public class Inicio {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int opcion=0;
        
        do{
            System.out.println("------------------------");
            System.out.println("Aplicacion de mensajes");
            System.out.println("1. Crear un mensaje");
            System.out.println("2. Listar mensajes");
            System.out.println("3. Eliminar mensaje");
            System.out.println("4. Editar mensaje");
            System.out.println("5. Salir");
            //leemos la opcion del usuario
            
            opcion = sc.nextInt();
            
            switch(opcion){
                    case 1:
                        mensajesService.crearMensaje();
                    break;
                    case 2:
                        mensajesService.listarMensajes();
                        break;
                    case 3:
                        mensajesService.borrarMensaje();
                        break;
                    case 4:
                        mensajesService.editarMensaje();
                        break;
                    case 5:
                        break;
            }
        }while(opcion != 5);
        
        Conexion conexion = new Conexion();
        
        
        try(Connection cnx = conexion.get_connection()){
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
}
