package dao;

import banco.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;

public class ClienteDaoBd implements ClienteDao {

    private Connection conexao;
    private PreparedStatement comando;

    @Override
    public void inserir(Cliente cliente) {
        int id = 0;
        try {
            String sql = "INSERT INTO clientes (rg, nome, telefone) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, cliente.getRG());
            comando.setString(2, cliente.getNome());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            //java.sql.Date dataSql = new java.sql.Date(cliente.getDataNascimento().getTime());
            comando.setString(3, cliente.getTelefone());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                cliente.setId(id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        //Caso queira retornar id:
        //return (id);        
    }

    @Override
    public void deletar(Cliente cliente) {
        try {
            String sql = "DELETE FROM clientes WHERE id = ?";

            conectar(sql);
            comando.setInt(1, cliente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            String sql = "UPDATE clientes SET rg=?, nome=?, telefone=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, cliente.getRG());
            comando.setString(2, cliente.getNome());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            //java.sql.Date dataSql = new java.sql.Date(cliente.getDataNascimento().getTime());
            comando.setString(3, cliente.getTelefone());
            comando.setInt(4, cliente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> listaClientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes ";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                //java.sql.Date dataSql = resultado.getDate("datanascimento");
                //java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Cliente cli = new Cliente(id, nome, rg, telefone);

                listaClientes.add(cli);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }

    @Override
    public Cliente procurarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                //java.sql.Date dataSql = resultado.getDate("datanascimento");
                //java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Cliente cli = new Cliente(id, nome, rg, telefone);

                return cli;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Cliente procurarPorRg(String rg) {
        String sql = "SELECT * FROM clientes WHERE rg = ?";

        try {
            conectar(sql);
            comando.setString(1, rg);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                //java.sql.Date dataSql = resultado.getDate("datanascimento");
                //java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Cliente cli = new Cliente(id, nome, rg, telefone);

                return cli;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public List<Cliente> procurarPorNome(String nome) {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + nome + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("rg");
                String nomeX = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                //java.sql.Date dataSql = resultado.getDate("datanascimento");
                //java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Cliente cli = new Cliente(id, nomeX, rg, telefone);

                listaClientes.add(cli);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }

    @Override
    public List<Cliente> procurarPorTelefone(String telefoneP) {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE telefone = ?";

        try {
            conectar(sql);
            comando.setString(1, telefoneP);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("rg");
                String nomeX = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                //java.sql.Date dataSql = resultado.getDate("datanascimento");
                //java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Cliente cli = new Cliente(id, nomeX, rg, telefone);

                listaClientes.add(cli);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaClientes);
    }

    public void conectar(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
    }

    public void conectarObtendoId(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public void fecharConexao() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
