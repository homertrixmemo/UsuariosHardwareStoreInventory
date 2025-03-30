package Model;

public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private String email;
    private String password;
    private int idPerfil;
    private String nombrePerfil;
    private int idRol;
    private String nombreRol;

    // 🔹 Constructor vacío
    public Usuario() {
        this.nombrePerfil = "";
        this.nombreRol = "";
    }

    // 🔹 Constructor principal con todos los atributos
    public Usuario(int idUsuario, String nombreUsuario, String email, String password,
            int idPerfil, String nombrePerfil, int idRol, String nombreRol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    // 🔹 Constructor sin idRol y nombreRol (para ciertos casos)
    public Usuario(int idUsuario, String nombreUsuario, String email, String password, int idPerfil, String nombrePerfil) {
        this(idUsuario, nombreUsuario, email, password, idPerfil, nombrePerfil, 0, "");
    }

    public Usuario(int idUsuario, String nombreUsuario, String email, String password, int idPerfil, String nombrePerfil, String nombreRol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
        this.nombreRol = nombreRol;
    }

    public Usuario(int idUsuario, String nombreUsuario, String email, String password, int idPerfil) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.idPerfil = idPerfil;
        this.nombrePerfil = ""; // Inicializa vacío para evitar errores
        this.nombreRol = "";
    }

    public Usuario(int idUsuario, String nombreUsuario, String email, String password, int idPerfil, int idRol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.idPerfil = idPerfil;
        this.idRol = idRol;
    }

    // 🔹 Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
