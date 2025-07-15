package zona_fit.dominio;

import java.util.Objects;

// Objeto Tipo Cliente. Este es el Objeto Cliente.
public class Cliente {

    // Atributos
    private int id;
    private String nombre;
    private String apellido;
    private int membresia;

    // Constructores
    public Cliente(){}

    public Cliente(int id){
        this.id = id;
    }

    public Cliente(String nombre, String apellido, int membresia){
        this.nombre = nombre;
        this.apellido = apellido;
        this.membresia = membresia;
    }

    public Cliente(int id, String nombre, String apellido, int membresia){
        this(nombre, apellido, membresia); // se debe llamar al metodo primero. esto simplemte para no repetir codigo.
        this.id = id;
    }


    // Getter & Setter, Obtener & Modificar
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getMembresia() {
        return membresia;
    }

    public void setMembresia(int membresia) {
        this.membresia = membresia;
    }

    // Metodo ToString
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", membresia=" + membresia +
                '}';
    }

    //
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id && membresia == cliente.membresia && Objects.equals(nombre, cliente.nombre) && Objects.equals(apellido, cliente.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, membresia);
    }

}
