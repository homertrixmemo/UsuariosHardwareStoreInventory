<%-- 
    Document   : perfilesAsociados
    Created on : 24 mar. 2025, 20:11:40
    Author     : homer
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Cliente" %>
<%@ page import="Model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perfiles Asociados</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>

        <div class="contenedor">
            <div class="card-form">
                <h2 style="color: #E67E22; text-align: center;">⚠ No se puede eliminar este perfil</h2>
                <p class="perfil-alert">
                    Hay clientes y/o usuarios asociados a este perfil. Debe reasignarlos o eliminarlos antes de continuar.
                </p>

                <%
                    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientesAsociados");
                    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuariosAsociados");
                %>

                <!-- Mostrar clientes asociados -->
                <% if (clientes != null && !clientes.isEmpty()) { %>
                <h3 class="perfil-title">Clientes Asociados</h3>
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Documento</th>
                                <th>Teléfono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Cliente cliente : clientes) {%>
                            <tr>
                                <td><%= cliente.getIdCliente()%></td>
                                <td><%= cliente.getNombres() + " " + cliente.getPrimerApellido()%></td>
                                <td><%= cliente.getNumeroDocumento()%></td>
                                <td><%= cliente.getTelefonoMovil()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } %>

                <!-- Mostrar usuarios asociados -->
                <% if (usuarios != null && !usuarios.isEmpty()) { %>
                <h3 class="perfil-title">Usuarios Asociados</h3>
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Usuario usuario : usuarios) {%>
                            <tr>
                                <td><%= usuario.getIdUsuario()%></td>
                                <td><%= usuario.getNombreUsuario()%></td>
                                <td><%= usuario.getEmail()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } %>

                <!-- Mensaje si no hay asociaciones -->
                <% if ((clientes == null || clientes.isEmpty()) && (usuarios == null || usuarios.isEmpty())) { %>
                <p class="perfil-alert-warning" style="color: red; text-align: center;">No hay clientes ni usuarios asociados a este perfil.</p>
                <% }%>

                <!-- Botones de acción -->
                <div class="button-group">
                    <a href="${pageContext.request.contextPath}/ClienteController?action=listar&idPerfil=${idPerfil}" 
                       class="boton boton-primario">Gestionar Clientes</a>
                    <a href="${pageContext.request.contextPath}/UsuarioController?action=listar&idPerfil=${idPerfil}" 
                       class="boton boton-secundario">Gestionar Usuarios</a>
                    <a href="${pageContext.request.contextPath}/PerfilController?action=listar" 
                       class="boton boton-terciario">Volver a Perfiles</a>
                </div>

            </div>
        </div>

    </body>
</html>
