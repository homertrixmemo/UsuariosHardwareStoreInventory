<%-- 
    Document   : listarClientes
    Created on : 16 mar 2025, 0:10:46
    Author     : Juan
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Model.Cliente" %>

<%
    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Lista de Clientes</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="contenedor">
            <h2 class="titulo">Lista de Clientes</h2>

            <div class="barra-acciones">
                <a href="index.jsp" class="boton boton-secundario">&#8592; Regresar</a>
                <a href="<%= request.getContextPath()%>/ClienteController?action=nuevo" class="boton boton-primario">➕ Agregar Cliente</a>
            </div>

            <div class="tabla-contenedor">
                <table class="tabla">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tipo Documento</th>
                            <th>Número Documento</th>
                            <th>Nombre</th>
                            <th>Primer Apellido</th>
                            <th>Segundo Apellido</th>
                            <th>Teléfono Móvil</th>
                            <th>Dirección Residencia</th>
                            <th>Contacto Emergencia</th>
                            <th>Teléfono Contacto</th>
                            <th>Perfil Asignado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (listaClientes != null && !listaClientes.isEmpty()) { %>
                        <% for (Cliente cliente : listaClientes) {%>
                        <tr>
                            <td><%= cliente.getIdCliente()%></td>
                            <td><%= cliente.getTipoDocumento()%></td>
                            <td><%= cliente.getNumeroDocumento()%></td>
                            <td><%= cliente.getNombres()%></td>
                            <td><%= cliente.getPrimerApellido()%></td>
                            <td><%= cliente.getSegundoApellido()%></td>
                            <td><%= cliente.getTelefonoMovil()%></td>
                            <td><%= cliente.getDireccionResidencia()%></td>
                            <td><%= cliente.getContactoEmergencia()%></td>
                            <td><%= cliente.getTelefonoContacto()%></td>
                            <td><%= cliente.getNombrePerfil()%></td>
                            <td class="acciones">
                                <a href="ClienteController?action=editar&id=<%= cliente.getIdCliente()%>" class="boton boton-editar">✏ Editar</a>
                                <a href="views/confirmDeleteCliente.jsp?id=<%= cliente.getIdCliente()%>" 
                                   class="boton boton-eliminar" title="Eliminar Cliente" onclick="return confirm('¿Estás seguro de eliminar este cliente?');">🗑 Eliminar</a>
                            </td>
                        </tr>
                        <% } %>
                        <% } else { %>
                        <tr>
                            <td colspan="5" class="mensaje">No hay clientes registrados.</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
