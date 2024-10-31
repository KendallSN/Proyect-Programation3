
package cr.ac.una.sigeceuna.model;

import java.time.LocalDate;

/**
 *
 * @author IK
 */
public class gestionPrueba {
    private String descripcion;
    private LocalDate fecha;
    private String nombre;
    private String estado;

    // Constructor
    public gestionPrueba(String descripcion, LocalDate fecha, String nombre) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.nombre = nombre;
    }   
     public gestionPrueba(String nombre,String estado) {
        this.nombre = nombre;
        this.estado=estado;
    }
    // Getters
    public String getDescripcion() { return descripcion; }
    public LocalDate getFecha() { return fecha; }
    public String getNombre() { return nombre; }
    public String getEstado() { return estado; }
    public void setNombre(String nombre){this.nombre=nombre;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}
    public void setEstado(String estado){this.estado=estado;}
}
