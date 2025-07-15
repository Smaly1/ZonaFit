package zona_fit.datos;

import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// DAO (Data Access Object):
// El patrón DAO es un patrón de diseño que separa la lógica de acceso a datos de la
// lógica de negocio de una aplicación.
// Esto significa que la clase ClienteDAO no contendrá código que manipule directamente
// la base de datos, sino que encapsulará esa lógica en métodos específicos para la entidad "Cliente".

import static zona_fit.conexion.Conexion.getConexion;


public class ClienteDAO implements IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }

        return clientes;

    }

    @Override
    public boolean buscarClientesPorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs; // Recibe el resultado de la Base De Datos
        var con =getConexion(); // Obteniendo un Obteto de tipo connection
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch (Exception e){
            System.out.println("Error al recuperar cliente por id: " + getConexion());
        }

        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar coneccion: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO cliente(nombre, apellido, membresia) " + " VALUES(?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }

        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conecxion: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? " + " WHERE id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }

        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE id = ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }

        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }

        return false;
    }


}
