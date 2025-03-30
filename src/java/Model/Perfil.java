package Model;

public class Perfil {

    private int idPerfil;
    private String nombrePerfil;
    private String descripcion;

    // Constructor vacío
    public Perfil() {
    }

    // Constructor con ID
    public Perfil(int idPerfil, String nombrePerfil, String descripcion) {
        this.idPerfil = idPerfil;
        this.setNombrePerfil(nombrePerfil);
        this.setDescripcion(descripcion);
    }

    // Constructor sin ID (para inserciones)
    public Perfil(String nombrePerfil, String descripcion) {
        this.setNombrePerfil(nombrePerfil);
        this.setDescripcion(descripcion);
    }

    public Perfil(int idPerfil, String nombrePerfil) {
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
    }

    // Getters y Setters
    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        if (idPerfil > 0) {
            this.idPerfil = idPerfil;
        }
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        if (nombrePerfil != null && !nombrePerfil.trim().isEmpty()) {
            this.nombrePerfil = nombrePerfil;
        } else {
            throw new IllegalArgumentException("El nombre del perfil no puede estar vacío.");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            this.descripcion = descripcion;
        } else {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "Perfil{"
                + "idPerfil=" + idPerfil
                + ", nombrePerfil='" + nombrePerfil + '\''
                + ", descripcion='" + descripcion + '\''
                + '}';
    }
}
