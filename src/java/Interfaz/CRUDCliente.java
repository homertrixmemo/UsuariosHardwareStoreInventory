package Interfaz;

import Model.Cliente;
import java.util.List;

public interface CRUDCliente {

    public List<Cliente> listar();

    public Cliente obtener(int id);

    public boolean agregar(Cliente cliente);

    public boolean actualizar(Cliente cliente);

    public boolean eliminar(int id);
}
