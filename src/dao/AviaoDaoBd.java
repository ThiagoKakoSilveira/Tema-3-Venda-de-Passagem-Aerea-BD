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
import model.Aviao;

public class AviaoDaoBd implements AviaoDao {

    private Connection conexao;
    private PreparedStatement comando;

    @Override
    public void inserir(Aviao aviao) {
        int id = 0;
        try {
            String sql = "INSERT INTO aviao (nome) "
                    + "VALUES (?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, aviao.getNome());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                aviao.setCodigo(id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

    }

    @Override
    public void deletar(Aviao aviao) {
        try {
            String sql = "DELETE FROM aviao WHERE id = ?";

            conectar(sql);
            comando.setInt(1, aviao.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Aviao aviao) {
        try {
            String sql = "UPDATE aviao SET id=?, nome=?"
                    + "WHERE id=?";

            conectar(sql);
            comando.setInt(1, aviao.getCodigo());
            comando.setString(2, aviao.getNome());
            comando.setInt(3, aviao.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Aviao> listar() {
        List<Aviao> listaAviao = new ArrayList<>();

        String sql = "SELECT * FROM aviao order by id";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");

                Aviao aviao = new Aviao(id, nome);

                listaAviao.add(aviao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AviaoDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
        return (listaAviao);
    }

    @Override
    public Aviao procurarPorCodigo(int cod) {
        String sql = "SELECT id, nome FROM aviao WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, cod);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int idAviao = resultado.getInt(1);
                String nome = resultado.getString(2);

                Aviao aviao = new Aviao(idAviao, nome);

                return aviao;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Aviao procurarPorNome(String nome) {
        String sql = "SELECT * FROM aviao WHERE nome = ?";

        try {
            conectar(sql);
            comando.setString(1, nome);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nomeT = resultado.getString("nome");

                Aviao aviao = new Aviao(nomeT);

                return aviao;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);

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
