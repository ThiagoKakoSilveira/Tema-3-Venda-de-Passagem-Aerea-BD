package dao;

import banco.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Ponte_Aerea;
import model.Voo;
import model.Cidade;
import model.Aviao;

public class VooDaoBd implements VooDao {

    private Connection conexao;
    private PreparedStatement comando;

    @Override
    public void inserir(Voo v) {
        int id;
        try {
            String sql = "INSERT INTO voo (ponte, aviao, horario_voo) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setInt(1, v.getPonte().getId());
            comando.setInt(2, v.getAviao().getCodigo());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            //java.sql.Date dataSql = new java.sql.Date(cliente.getDataNascimento().getTime());
            java.sql.Timestamp dataSql = new java.sql.Timestamp(v.getHorarioDoVoo().getTime());
            //String dataTeste = util.DateUtil.dateHourToString(v.getHorarioDoVoo());
            //String dataDoBD = dataTeste.replace("/", "-");
            comando.setTimestamp(3, dataSql);//No teste aqui foi alterado para String 
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                v.setCodigo(id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Voo v) {
        try {
            String sql = "DELETE FROM voo WHERE id = ?";
            conectar(sql);
            comando.setInt(1, v.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Voo v) {
        try {
            String sql = "UPDATE voo SET id=?, aviao=?, ponte=?, horario_voo=?"
                    + "WHERE id=?";

            conectar(sql);
            comando.setInt(1, v.getCodigo());
            comando.setInt(2, v.getAviao().getCodigo());
            java.sql.Timestamp dataSql = new java.sql.Timestamp(v.getHorarioDoVoo().getTime());
            comando.setTimestamp(4, dataSql);
            comando.setInt(3, v.getPonte().getId());
            comando.setInt(5, v.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Voo> listar() {
        List<Voo> listaVoos = new ArrayList<>();

        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome\n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "order by voo.id";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                //criação das cidades
                int idOrigem = resultado.getInt(1);
                String nomeOrigem = resultado.getString(2);
                String ufOrigem = resultado.getString(3);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(4);
                String nomeDestino = resultado.getString(5);
                String ufDestino = resultado.getString(6);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);

                //(conferindo as cidades) System.out.println(cidadeDestino + "\n" + cidadeOrigem);
                //criação da ponte
                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                //(conferindo a ponte)System.out.println(ponte);
                //criação do voo
                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);
                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                //(conferindo o voo)System.out.println(voo);
                listaVoos.add(voo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaVoos);
    }

    @Override
    public Voo procurarPorCodigo(int cod) {
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome\n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "where voo.id = ?";
        try {
            conectar(sql);
            comando.setInt(1, cod);

            ResultSet resultado = comando.executeQuery();;

            if (resultado.next()) {
                int idOrigem = resultado.getInt(1);
                String nomeOrigem = resultado.getString(2);
                String ufOrigem = resultado.getString(3);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(4);
                String nomeDestino = resultado.getString(5);
                String ufDestino = resultado.getString(6);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);

                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);
                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);
                return voo;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
        return null;
    }

    @Override
    public List<Voo> procurarPorOrigem(int origem) {
        List<Voo> listaVoos = new ArrayList<>();
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome\n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id"
                + "where p.origem = ?";
        try {
            conectar(sql);
            comando.setInt(1, origem);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {

                int idOrigem = resultado.getInt(1);
                String nomeOrigem = resultado.getString(2);
                String ufOrigem = resultado.getString(3);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(4);
                String nomeDestino = resultado.getString(5);
                String ufDestino = resultado.getString(6);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);
                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);
                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);
                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);
                listaVoos.add(voo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaVoos);

    }

    @Override
    public List<Voo> procurarPorDestino(int destino) {
        List<Voo> listaVoos = new ArrayList<>();
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome\n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id"
                + "where p.destino = ?";
        try {
            conectar(sql);
            comando.setInt(1, destino);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {

                int idOrigem = resultado.getInt(1);
                String nomeOrigem = resultado.getString(2);
                String ufOrigem = resultado.getString(3);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(4);
                String nomeDestino = resultado.getString(5);
                String ufDestino = resultado.getString(6);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);
                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);
                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);
                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);
                listaVoos.add(voo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaVoos);
    }

    @Override
    public List<Voo> procurarPorHorario(Date data) {
        List<Voo> listaVoos = new ArrayList<>();
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome\n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id"
                + "where v.horario_voo = ?";
        try {
            conectar(sql);
            java.sql.Timestamp injectionSql = new java.sql.Timestamp(data.getTime());
            comando.setTimestamp(1, injectionSql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {

                int idOrigem = resultado.getInt(1);
                String nomeOrigem = resultado.getString(2);
                String ufOrigem = resultado.getString(3);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(4);
                String nomeDestino = resultado.getString(5);
                String ufDestino = resultado.getString(6);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);
                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);
                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);
                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);
                listaVoos.add(voo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaVoos);
    }

    @Override
    public Ponte_Aerea pontePorId(int id) {
        String sql = "SELECT d.id, d.nome, d.uf,o.id, o.nome, o.uf, p.id FROM cidades as o \n"
                + "inner join pontes_aereas as p\n"
                + "on p.origem = o.id\n"
                + "inner join cidades as d\n"
                + "on p.destino = d.id\n"
                + "WHERE p.id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int idOrigem = resultado.getInt(4);
                String nomeOrigem = resultado.getString(5);
                String ufOrigem = resultado.getString(6);
                Cidade cidadeOrigem = new Cidade(idOrigem, nomeOrigem, ufOrigem);
                int idDestino = resultado.getInt(1);
                String nomeDestino = resultado.getString(2);
                String ufDestino = resultado.getString(3);
                Cidade cidadeDestino = new Cidade(idDestino, nomeDestino, ufDestino);
                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);
                return ponte;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);

    }

    @Override
    public List<Ponte_Aerea> listarPontes() {
        List<Ponte_Aerea> pontes = new ArrayList<>();

        String sql = "SELECT d.id, d.nome, d.uf,o.id, o.nome, o.uf, p.id FROM cidades as o \n"
                + "inner join pontes_aereas as p\n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int idDestino = resultado.getInt(1);
                String nomeDestino = resultado.getString(2);
                String ufDestino = resultado.getString(3);

                Cidade destino = new Cidade(idDestino, nomeDestino, ufDestino);

                int idOrigem = resultado.getInt(4);
                String nomeOrigem = resultado.getString(5);
                String ufOrigem = resultado.getString(6);

                Cidade origem = new Cidade(idOrigem, nomeOrigem, ufOrigem);

                int idPonte = resultado.getInt(7);
                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, destino, origem);
                pontes.add(ponte);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AviaoDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
        return (pontes);
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
