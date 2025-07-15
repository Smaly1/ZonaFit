package zona_fit.datos;

import zona_fit.dominio.Cliente;

import java.util.List;

public interface IClienteDAO {

    List<Cliente> listarClientes();
    boolean buscarClientesPorId(Cliente cliente); // R_ead
    boolean agregarCliente(Cliente cliente);      // C_reate
    boolean modificarCliente(Cliente cliente);    // U_pdate
    boolean eliminarCliente(Cliente cliente);     // D_elete
}
