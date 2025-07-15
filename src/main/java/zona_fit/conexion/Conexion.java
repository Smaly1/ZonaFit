package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion(){
        // Conectar a DB de MySql
        Connection conexion = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "admin";

        try {
            // Esto se conoce como la Clase de connexion a la DB.
            Class.forName("com.mysql.cj.jdbc.Driver");
            // aqui establesco la conexion a mysql
            conexion = DriverManager.getConnection(url, usuario, password);
        }catch (Exception e){
            System.out.println("Error al conectar a la DB: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        var conexion = Conexion.getConexion();
        if (conexion != null){
            System.out.println("Conexion exitosa a DB: " + conexion);
        }else{
            System.out.println("Error al conectarse");
        }
    }
}
