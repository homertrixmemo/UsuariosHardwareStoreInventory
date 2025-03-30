package Model;

public class Cliente {

    private int idCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String telefonoMovil;
    private String direccionResidencia;
    private String contactoEmergencia;
    private String telefonoContacto;
    private Integer perfilIdPerfil; // <--- Nombre corregido para ser uniforme

    private String nombrePerfil; // Nuevo atributo para almacenar el nombre del perfil

    // Constructor vacío
    public Cliente() {
    }

    // Constructor con todos los parámetros (incluyendo ID)
    public Cliente(int idCliente, String tipoDocumento, String numeroDocumento, String nombres,
            String primerApellido, String segundoApellido, String telefonoMovil,
            String direccionResidencia, String contactoEmergencia, String telefonoContacto,
            Integer perfilIdPerfil) {
        this.idCliente = idCliente;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
        this.telefonoContacto = telefonoContacto;
        this.perfilIdPerfil = perfilIdPerfil;
    }

    // Constructor para inserción (sin ID)
    public Cliente(String tipoDocumento, String numeroDocumento, String nombres,
            String primerApellido, String segundoApellido, String telefonoMovil,
            String direccionResidencia, String contactoEmergencia,
            String telefonoContacto, int perfilIdPerfil) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
        this.telefonoContacto = telefonoContacto;
        this.perfilIdPerfil = perfilIdPerfil; // <--- Se usa el nombre correcto
    }

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Integer getPerfilIdPerfil() {
        return perfilIdPerfil;
    }

    public void setPerfilIdPerfil(Integer perfilIdPerfil) {
        this.perfilIdPerfil = perfilIdPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
}
