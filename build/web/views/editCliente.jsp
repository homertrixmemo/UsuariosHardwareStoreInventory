<%-- 
    Document   : editCliente
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
        <title>Editar Cliente</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos optimizado -->
    </head>
    <body>
        <div class="container">
            <!-- Contenedor Principal -->
            <div class="card-form">
                <div class="form-header">
                    <h3>✏️ Editar Cliente</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/ClienteController?action=actualizar" method="post">
                        <input type="hidden" name="idCliente" value="${cliente.idCliente}">

                        <!-- Campo Tipo de Documento -->
                        <div class="form-group">
                            <label for="tipoDocumento">Tipo de Documento:</label>
                            <select id="tipoDocumento" name="tipoDocumento" class="input-field" required>
                                <option value="DNI" ${cliente.tipoDocumento == 'DNI' ? 'selected' : ''}>DNI</option>
                                <option value="Pasaporte" ${cliente.tipoDocumento == 'Pasaporte' ? 'selected' : ''}>Pasaporte</option>
                                <option value="Cédula" ${cliente.tipoDocumento == 'Cédula' ? 'selected' : ''}>Cédula</option>
                            </select>
                        </div>

                        <!-- Campo Número de Documento -->
                        <div class="form-group">
                            <label for="numeroDocumento">Número de Documento:</label>
                            <input type="text" id="numeroDocumento" name="numeroDocumento" class="input-field" maxlength="20" value="${cliente.numeroDocumento}" required>
                        </div>

                        <!-- Campo Nombres -->
                        <div class="form-group">
                            <label for="nombres">Nombres:</label>
                            <input type="text" id="nombres" name="nombres" class="input-field" maxlength="50" value="${cliente.nombres}" required>
                        </div>

                        <!-- Campo Primer Apellido -->
                        <div class="form-group">
                            <label for="primerApellido">Primer Apellido:</label>
                            <input type="text" id="primerApellido" name="primerApellido" class="input-field" maxlength="50" value="${cliente.primerApellido}" required>
                        </div>

                        <!-- Campo Segundo Apellido -->
                        <div class="form-group">
                            <label for="segundoApellido">Segundo Apellido:</label>
                            <input type="text" id="segundoApellido" name="segundoApellido" class="input-field" maxlength="50" value="${cliente.segundoApellido}">
                        </div>

                        <!-- Campo Teléfono Móvil -->
                        <div class="form-group">
                            <label for="telefonoMovil">Teléfono Móvil:</label>
                            <input type="tel" id="telefonoMovil" name="telefonoMovil" class="input-field" maxlength="15" value="${cliente.telefonoMovil}" required>
                        </div>

                        <!-- Campo Dirección de Residencia -->
                        <div class="form-group">
                            <label for="direccionResidencia">Dirección de Residencia:</label>
                            <input type="text" id="direccionResidencia" name="direccionResidencia" class="input-field" maxlength="100" value="${cliente.direccionResidencia}" required>
                        </div>

                        <!-- Campo Contacto de Emergencia -->
                        <div class="form-group">
                            <label for="contactoEmergencia">Contacto de Emergencia:</label>
                            <input type="text" id="contactoEmergencia" name="contactoEmergencia" class="input-field" maxlength="50" value="${cliente.contactoEmergencia}" required>
                        </div>

                        <!-- Campo Teléfono de Contacto -->
                        <div class="form-group">
                            <label for="telefonoContacto">Teléfono de Contacto:</label>
                            <input type="tel" id="telefonoContacto" name="telefonoContacto" class="input-field" maxlength="15" value="${cliente.telefonoContacto}" required>
                        </div>

                        <!-- Campo Perfil -->
                        <div class="form-group">
                            <label for="perfil">Perfil:</label>
                            <select id="perfil" name="perfilIdPerfil" class="input-field" required>
                                <option value="">Seleccione un perfil</option>
                                <c:forEach var="perfil" items="${listaPerfiles}">
                                    <option value="${perfil.idPerfil}" ${cliente.perfilIdPerfil eq perfil.idPerfil ? 'selected' : ''}>${perfil.nombrePerfil}</option>
                                </c:forEach>
                            </select>
                        </div>


                        <!-- Botones de Acción -->
                        <div class="button-group">
                            <button type="submit" class="btn-save">💾 Guardar Cambios</button>
                            <a href="${pageContext.request.contextPath}/ClienteController?action=listar" class="btn-cancel">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
