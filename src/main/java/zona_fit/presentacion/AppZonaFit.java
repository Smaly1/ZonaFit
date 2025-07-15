package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class AppZonaFit {
    public static void main(String[] args) {
        IClienteDAO clienteDao = new ClienteDAO();
        Scanner sC = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("--- Zona Fit ---\n");
            System.out.println("""
                    Selecciona la opcion:
                    1. Buscar cliente por ID.
                    2. Agregar cliente.
                    3. Modificar cliente.
                    4. Eliminar cliente.
                    5. Salir
                    """);
            System.out.print("Opcion: ");

            int option;
            try {
                option = sC.nextInt();
                sC.nextLine(); // Limpieza de buffer
            } catch (Exception e) {
                System.out.println("Error: Debes ingresar un número.");
                sC.nextLine(); // Limpia el buffer si hay error
                continue;
            }

            switch (option){
                case 1 -> {
                    // Buscar clientes por ID
                    System.out.print("Ingrese el ID: ");
                    int idCliente;
                    try {
                        idCliente = sC.nextInt();
                        sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El ID debe ser un número.");
                        sC.nextLine();
                        continue;
                    }

                    var cliente1 = new Cliente(idCliente);
                    //System.out.println("Cliente antes de la busqueda: " + cliente1);
                    var encontrado = clienteDao.buscarClientesPorId(cliente1);

                    if (encontrado) {
                        System.out.println("Cliente encontrado: " + cliente1);
                    } else {
                        System.out.println("No se encontro cliente: " + cliente1.getId());
                    }

                }

                case 2 -> {
                    // Agregar cliente
                    System.out.println("** Ingrese los datos del nuevo cliente **");

                    System.out.print("Nombre: ");
                    String nombreCli;
                    try {
                        nombreCli = sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El nombre debe ser un String.");
                        continue;
                    }

                    System.out.print("Apellido: ");
                    String apellidoCli;
                    try {
                        apellidoCli = sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El apellido debe ser un String.");
                        sC.nextLine(); // 🔴 Limpia el buffer después de nextInt()
                        continue;
                    }

                    System.out.print("Membresía: ");
                    int membresiaCli;
                    try {
                        membresiaCli = sC.nextInt();
                    } catch (Exception e) {
                        System.out.println("Error: La membresía debe ser un Numero.");
                        continue;
                    }

                    var nuevoCliente = new Cliente(nombreCli, apellidoCli, membresiaCli);
                    var agregado  = clienteDao.agregarCliente(nuevoCliente);
                    if (agregado){
                        System.out.println("Cliente agregado: " + nuevoCliente);
                    }else{
                        System.out.println("Error. No se agrego el cliente: " + nuevoCliente);
                    }
                }

                case 3 -> {
                    // Modificar cliente
                    System.out.println("** Ingrese las modificaciones al cliente **");

                    sC.nextLine(); // 🔴 SOLUCIÓN: Limpia el buffer antes de leer el nombre

                    System.out.print("Id: ");
                    int idCli;
                    try {
                        idCli = sC.nextInt();
                        sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El ID debe ser un número entero.");
                        sC.nextLine();
                        continue;
                    }

                    System.out.print("Nombre: ");
                    String nombreCli;
                    try {
                        nombreCli = sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El nombre debe ser un String.");
                        continue;
                    }

                    System.out.print("Apellido: ");
                    String apellidoCli;
                    try {
                        apellidoCli = sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: El apellido debe ser un String.");
                        continue;
                    }

                    System.out.print("Membresía: ");
                    int membresiaCli;
                    try {
                        membresiaCli = sC.nextInt();
                        sC.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: La membresía debe ser un número entero.");
                        sC.nextLine();
                        continue;
                    }

                    sC.nextLine(); // 🔴 Limpia el buffer después de nextInt()

                    var modificarCliente = new Cliente(idCli, nombreCli, apellidoCli, membresiaCli);
                    var modificado = clienteDao.modificarCliente(modificarCliente);
                    if (modificado){
                        System.out.println("Cliente modificado: " + modificarCliente);
                    }else {
                        System.out.println("Error al modificar cliente: " + modificarCliente);
                    }

                }

                case 4 -> {
                    // Eliminar Cliente/s.
                    // Listar clientes jajaja esto se está descontrolando, hay agua en mi mente.
                    System.out.println("\n--- listar clientes ---");
                    var clientes = clienteDao.listarClientes();
                    clientes.forEach(System.out::println);

                    System.out.print("\n¿Cuántos clientes desea eliminar? ");
                    int cantidad;
                    try {
                        cantidad = sC.nextInt();
                        sC.nextLine(); // Limpiar el buffer después de nextInt()
                    } catch (Exception e) {
                        System.out.println("Error: La cantidad debe ser un número entero.");
                        sC.nextLine(); // Limpiar el buffer en caso de error
                        continue; // Saltar a la siguiente iteración
                    }

                    // Iterar para eliminar cada cliente
                    for (int i = 0; i < cantidad; i++) {
                        System.out.print("Ingresa el Id del cliente a eliminar: ");
                        int idCli;
                        try {
                            idCli = sC.nextInt();
                            sC.nextLine(); // Limpiar el buffer
                        } catch (Exception e) {
                            System.out.println("Error: El ID debe ser un número entero.");
                            sC.nextLine();
                            i--; // Se decrementa 'i' para repetir la iteración actual
                            continue;
                        }

                        var clienteEliminar = new Cliente(idCli);
                        boolean eliminado;
                        try {
                            eliminado = clienteDao.eliminarCliente(clienteEliminar);
                            if (eliminado) {
                                System.out.println("Cliente eliminado: " + clienteEliminar);
                            } else {
                                System.out.println("Error al eliminar cliente: " + clienteEliminar);
                            }
                        } catch (Exception e) {
                            System.out.println("Error al procesar la eliminación del cliente: " + e.getMessage());
                        }
                    }


                }

                case 5 -> {
                    System.out.println("** Saliendo del sistema **");
                    salir = true;
                }
                default -> System.out.println("Error. Opcion seleccionada incorrecta: " + option);

            }
            if (!salir){
                // Listar clientes.
                System.out.println("\n--- listar clientes ---");
                var clientes = clienteDao.listarClientes();
                clientes.forEach(System.out::println);
            }

        }while (!salir);

        System.out.println("   Hasta luego!");
        // Profe disculpe que le meti los limpiadores de buffer en en muchos lugares innecesarios, lo se es que un error relacionado con eso casi me pone loco. jajaja
    }
}
