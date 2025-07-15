package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp(){
        boolean salir = false;                      // Variable de control del bucle principal
        Scanner consola = new Scanner(System.in);   // Objeto para entrada de usuario
        IClienteDAO clienteDao = new ClienteDAO();  // Objeto DAO para operaciones con datos
        while (!salir){                             // Se ejecuta mientras salir == false
            try {
                int opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);
            }catch (Exception e){
                System.out.println("Error al ejecutar las opciones: " + e.getMessage());
            }
            System.out.println();
        }
    }

    // metodos de la clase.
    private static int mostrarMenu(Scanner consola) {
        System.out.println("--- Zona Fit (GYM)---\n");
        System.out.print("""
                    Selecciona la opcion:
                    1. Listar clientes
                    2. Buscar cliente por ID.
                    3. Agregar cliente.
                    4. Modificar cliente.
                    5. Eliminar cliente.
                    6. Salir
                    Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDao) {
        var salir = false;
        switch (opcion){ // Hermoso pla pla
            // llamada metodo listarClientes de clase ClienteDAO.
            case 1 -> listarClientes(clienteDao);
            // Llamada al metodo buscar por ID.
            case 2 -> buscarClientePorId(consola, clienteDao);
            // Agregar cliente
            case 3 -> agregarCliente(consola, clienteDao);
            // Modificar cliente
            case 4 -> modificarCliente(consola, clienteDao);
        }
        return salir;
    }



    // Listar clientes
    private static void listarClientes(IClienteDAO clienteDao){
        System.out.println("--- Listar Clientes ---");
        var clientes = clienteDao.listarClientes();
        if (clientes.isEmpty()){
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    // Buscar cliente por ID
    private static void buscarClientePorId(Scanner consola, IClienteDAO clienteDAO){
        System.out.print("Ingrese el ID: ");
        int idCliente =  -1;
        try{
            idCliente = consola.nextInt();
            consola.nextLine();
        } catch (Exception e) {
            System.out.println("Error: EL ID debe ser un numero.");
            consola.nextLine();
        }
        if (idCliente <= 0 ){
            System.out.println("ID invalido Cancelando busqueda.");
            return;
        }
        var cliente = new Cliente(idCliente);
        var encontrado =  clienteDAO.buscarClientesPorId(cliente);

        if (encontrado){
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("No se encontro cliente: " + cliente.getId());
        }

    }

    // Agregar cliente
    private static  void agregarCliente(Scanner consola, IClienteDAO clienteDao){
        System.out.println("*** Ingrese los datos del nuevo cliente ***");

        String nombreCli = leerTextoValido(consola, "Nombre: ");

        String apellidoCli = leerTextoValido(consola, "Apellido: ");

        int membresiaCli = leerEnteroPositivo(consola, "Membresia: ");

        // Creamos un nuevo objeto cliente y agregamos los datos del nuevo cliente
        var nuevoCliente = new Cliente(nombreCli, apellidoCli, membresiaCli);
        // si el cliente se agrego correctamente nos devuelte un True y si no un False.
        boolean agregado = clienteDao.agregarCliente(nuevoCliente);

        // Si devuelve true el cliente se agrego
        if (agregado){
            System.out.println("Cliente agregado: " + nuevoCliente);
        } else {
            System.out.println("Error: No se agrego el cliente: " + nuevoCliente);
        }
    }

    // Modificar cliente
    private static void modificarCliente(Scanner consola, IClienteDAO clienteDao){
        System.out.println("*** Ingrese las modificaciones al cliente ***");

        int idCli = leerEnteroPositivo(consola, "ID Cliente: ");
        String nuevoNombreCli = leerTextoValido(consola, "Nombre: ");
        String nuevoApellidoCli = leerTextoValido(consola, "Apellido: ");
        int nuevoMembresiaCli = leerEnteroPositivo(consola, "Membresía: ");

        // Creo una nueva instancia(Nuevo Objeto) de la clase cliente(plantilla)
        var modificarCliente = new Cliente(idCli, nuevoNombreCli, nuevoApellidoCli, nuevoMembresiaCli);
        // Es como decir:
        //“Toma este cliente con ID 5, cambiale el nombre, apellido y membresía, y dime si lo pudiste modificar correctamente”.
        var modificado = clienteDao.modificarCliente(modificarCliente);

        if (modificado){
            System.out.println("Cliente modificado: " + modificarCliente);
        } else {
            System.out.println("Error al modificar el cliente: " + modificarCliente);
        }

    }




    //Metodo para Leer texto correctamente.
    private static String leerTextoValido(Scanner consola, String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = consola.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("El valor no puede estar vacío.");
                continue;
            }

            // Validamos que no sea un numero (int o float)
            if (texto.matches("\\d+(\\.\\d+)?")) {
                System.out.println("El valor no puede ser un número. Intenta con un nombre.");
                continue;
            }

            break; // texto válido

        } while (true);

        return texto;
    }

    // Metodo para Leer Numero Entero Positivo correctamente.
    private static int leerEnteroPositivo(Scanner consola, String mensaje){ // mensaje es para que el metodo sea reutilizable para otros propositos.
        int numero;
        while (true){
            System.out.print(mensaje);
            String entrada = consola.nextLine().trim();

            try {
                numero = Integer.parseInt(entrada);
                if (numero <= 0){
                    System.out.println("El numero debe de ser mayor a cero.");
                } else {
                    break; // valido
                }
            } catch (NumberFormatException e){
                System.out.println("Entrada invalida. Debes ingresar un numero en entero.");
            }
        }
        return numero;
    }


}
