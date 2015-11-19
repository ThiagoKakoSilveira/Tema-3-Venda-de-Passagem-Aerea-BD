package servico;

import dao.ClienteDao;
import dao.ClienteDaoBd;
import java.util.List;
import model.Cliente;

public class ClienteServico {

    public boolean clienteExiste(String rg) {
        ClienteDao dao = new ClienteDaoBd();
        Cliente cliente = dao.procurarPorRg(rg);
        return (cliente != null);
    }

    public boolean nomeClienteExiste(String nome) {
        ClienteDao dao = new ClienteDaoBd();
        List<Cliente> cliente = dao.procurarPorNome(nome);
        return (cliente != null);
    }

    public boolean clienteExistePorTelefone(String tel) {
        ClienteDao dao = new ClienteDaoBd();
        List<Cliente> cliente = dao.procurarPorTelefone(tel);
        return (cliente != null);
    }

    public boolean clienteExiste(int id) {
        ClienteDao dao = new ClienteDaoBd();
        Cliente cliente = dao.procurarPorId(id);
        return (cliente != null);
    }

    public void addCliente(Cliente c) {
        new ClienteDaoBd().inserir(c);
    }

    public void atualizaCliente(Cliente c) {
        new ClienteDaoBd().atualizar(c);
    }

    public void deletarCliente(Cliente clienteDel) {
        new ClienteDaoBd().deletar(clienteDel);
    }

    public List<Cliente> listarCliente() {
        return (new ClienteDaoBd().listar());
    }

    public void mostrarClientes() {
        System.out.println("-----------------------------\n");
        System.out.println(String.format("%-4s", "ID") + "\t"
                + String.format("%-27s", "|NOME") + "\t"
                + String.format("%-10s", "|RG") + "\t"
                + String.format("%-10s", "|TELEFONE"));
        for (Cliente Cliente : listarCliente()) {
            System.out.println(String.format("%-4s", Cliente.getId()) + "\t"
                    + String.format("%-27s", Cliente.getNome()) + "\t"
                    + String.format("%-10s", "|" + Cliente.getRG()) + "\t"
                    + String.format("%-10s", "|" + Cliente.getTelefone()));
        }
        System.out.println("\n");
    }

    public void mostrarClientes(List<Cliente> compradores) {
        System.out.println("-----------------------------\n");
        System.out.println("Esses foram os nomes encontrados");
        System.out.println(String.format("%-4s", "ID") + "\t"
                + String.format("%-27s", "|NOME") + "\t"
                + String.format("%-10s", "|RG") + "\t"
                + String.format("%-10s", "|TELEFONE"));
        for (Cliente Cliente : compradores) {
            System.out.println(String.format("%-4s", Cliente.getId()) + "\t"
                    + String.format("%-27s", Cliente.getNome()) + "\t"
                    + String.format("%-10s", "|" + Cliente.getRG()) + "\t"
                    + String.format("%-10s", "|" + Cliente.getTelefone()));
        }
        System.out.println("\n");
    }

    public Cliente devolveCliente(String rg) {
        return (new ClienteDaoBd().procurarPorRg(rg));
    }

    public Cliente devolveCliente(int id) {
        return (new ClienteDaoBd().procurarPorId(id));
    }

    public List<Cliente> devolveClientePorNome(String nome) {
        return (new ClienteDaoBd().procurarPorNome(nome));
    }

    public List<Cliente> devolveClientePorTelefone(String tel) {
        return (new ClienteDaoBd().procurarPorTelefone(tel));
    }
}
