<%-- 
    Document   : addCliente
    Created on : 16 mar 2025, 0:11:57
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Cliente</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos -->
    </head>
    <body>
        <div class="container">
            <div class="card-form">
                <div class="form-header">
                    <h3>🧑 Agregar Nuevo Cliente</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/ClienteController" method="post">
                        <input type="hidden" name="action" value="guardar">

                        <!-- Tipo de Documento -->
                        <div class="form-group">
                            <label for="tipoDocumento">Tipo de Documento:</label>
                            <select id="tipoDocumento" name="tipoDocumento" class="input-field" required>
                                <option value="">Seleccione un tipo</option>
                                <option value="DNI">DNI</option>
                                <option value="Pasaporte">Pasaporte</option>
                                <option value="CC">Cédula de Ciudadanía</option>
                            </select>
                        </div>

                        <!-- Número de Documento -->
                        <div class="form-group">
                            <label for="numeroDocumento">Número de Documento:</label>
                            <input type="text" id="numeroDocumento" name="numeroDocumento" class="input-field" maxlength="20" required>
                        </div>

                        <!-- Nombres -->
                        <div class="form-group">
                            <label for="nombres">Nombres:</label>
                            <input type="text" id="nombres" name="nombres" class="input-field" maxlength="50" required>
                        </div>

                        <!-- Primer Apellido -->
                        <div class="form-group">
                            <label for="primerApellido">Primer Apellido:</label>
                            <input type="text" id="primerApellido" name="primerApellido" class="input-field" maxlength="50" required>
                        </div>

                        <!-- Segundo Apellido -->
                        <div class="form-group">
                            <label for="segundoApellido">Segundo Apellido:</label>
                            <input type="text" id="segundoApellido" name="segundoApellido" class="input-field" maxlength="50">
                        </div>

                        <!-- Teléfono Móvil -->
                        <div class="form-group">
                            <label for="telefonoMovil">Teléfono Móvil:</label>
                            <input type="text" id="telefonoMovil" name="telefonoMovil" class="input-field" maxlength="15" required>
                        </div>

                        <!-- Dirección de Residencia -->
                        <div class="form-group">
                            <label for="direccionResidencia">Dirección de Residencia:</label>
                            <input type="text" id="direccionResidencia" name="direccionResidencia" class="input-field" maxlength="100" required>
                        </div>

                        <!-- Contacto de Emergencia -->
                        <div class="form-group">
                            <label for="contactoEmergencia">Contacto de Emergencia:</label>
                            <input type="text" id="contactoEmergencia" name="contactoEmergencia" class="input-field" maxlength="50">
                        </div>

                        <!-- Teléfono de Contacto de Emergencia -->
                        <div class="form-group">
                            <label for="telefonoContacto">Teléfono Contacto Emergencia:</label>
                            <input type="text" id="telefonoContacto" name="telefonoContacto" class="input-field" maxlength="15">
                        </div>

                        <!-- Perfil -->
                        <div class="form-group">
                            <label for="perfil">Perfil:</label>
                            <select id="perfil" name="perfil" class="input-field" required>
                                <option value="">Seleccione un perfil</option>
                                <c:forEach var="perfil" items="${listaPerfiles}">
                                    <option value="${perfil.idPerfil}">${perfil.nombrePerfil}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Botones -->
                        <div class="button-group">
                            <button type="submit" class="btn-save">💾 Guardar</button>
                            <a href="${pageContext.request.contextPath}/ClienteController?action=listar" class="btn-cancel">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
