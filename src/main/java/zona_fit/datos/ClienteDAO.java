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
        String sql = "SELECT * FROM cliente ORDER BY id";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public boolean buscarClientesPorId(Cliente cliente) {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cliente.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setMembresia(rs.getInt("membresia"));
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al recuperar cliente por ID: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre, apellido, membresia) VALUES (?, ?, ?)";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, membresia = ? WHERE id = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }

        return false;
    }


}
