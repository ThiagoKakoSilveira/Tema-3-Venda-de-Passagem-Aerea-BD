package dao;

import java.util.List;
import model.Cliente;

public interface ClienteDao {

    public void inserir(Cliente cliente);

    public void deletar(Cliente cliente);

    public void atualizar(Cliente cliente);

    public List<Cliente> listar();

    public Cliente procurarPorId(int id);

    public Cliente procurarPorRg(String rg);

    public List<Cliente> procurarPorNome(String nome);
    
    public List<Cliente> procurarPorTelefone(String telefoneP);
}
