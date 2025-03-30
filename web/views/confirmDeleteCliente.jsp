<%-- 
    Document   : confirmDeleteCliente
    Created on : 18 mar 2025, 21:43:32
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Confirmar Eliminación de Cliente</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
    </head>       
    <body class="delete-bg">
        <div class="delete-container">
            <div class="delete-card">
                <div class="delete-header">
                    <h2>❗ ¿Eliminar Cliente?</h2>
                </div>
                <div class="delete-body">
                    <p>Esta acción no se puede deshacer. ¿Estás seguro?</p>
                    <c:choose>
                        <c:when test="${not empty param.id}"> 
                            <form action="${pageContext.request.contextPath}/ClienteController" method="post" 
                                  onsubmit="console.log('✔ Formulario enviado correctamente');">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="idCliente" value="${param.id}">
                                <button type="submit" class="delete-btn delete-btn-danger">🗑️ Sí, eliminar</button>
                            </form>
                            <a href="${pageContext.request.contextPath}/ClienteController?action=listar" 
                               class="delete-btn delete-btn-cancel">❌ Cancelar</a>
                        </c:when>
                        <c:otherwise>
                            <div class="delete-error">
                                Error: Cliente no válido.
                            </div>
                            <a href="${pageContext.request.contextPath}/ClienteController?action=listar" 
                               class="delete-btn delete-btn-cancel">🔙 Regresar</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
