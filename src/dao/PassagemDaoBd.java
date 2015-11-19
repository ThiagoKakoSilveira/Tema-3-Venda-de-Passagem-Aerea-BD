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
import model.Aviao;
import model.Cidade;
import model.Passagem;
import model.Ponte_Aerea;
import model.Voo;
import model.Cliente;

public class PassagemDaoBd implements PassagemDao {

    private Connection conexao;
    private PreparedStatement comando;

    @Override
    public void inserir(Passagem p) {
        int id;
        try {
            String sql = "INSERT INTO passagem (cliente, voo, horario_compra) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setInt(1, p.getCliente().getId());
            comando.setInt(2, p.getVoo().getCodigo());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            //java.sql.Date dataSql = new java.sql.Date(cliente.getDataNascimento().getTime());
            java.sql.Timestamp dataSql = new java.sql.Timestamp(p.getHoraVenda().getTime());
            //String dataTeste = util.DateUtil.dateHourToString(v.getHorarioDoVoo());
            //String dataDoBD = dataTeste.replace("/", "-");
            comando.setTimestamp(3, dataSql);//No teste aqui foi alterado para String 
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                p.setCodigo(id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Passagem p) {
        try {
            String sql = "DELETE FROM passagem WHERE id = ?";
            conectar(sql);
            comando.setInt(1, p.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Passagem p) {
        try {
            String sql = "UPDATE passagem SET id=?, cliente=?, voo=?, horario_compra=?"
                    + "WHERE id=?";

            conectar(sql);
            comando.setInt(1, p.getCodigo());
            comando.setInt(2, p.getCliente().getId());
            java.sql.Timestamp dataSql = new java.sql.Timestamp(p.getHoraVenda().getTime());
            comando.setTimestamp(4, dataSql);
            comando.setInt(3, p.getVoo().getCodigo());
            comando.setInt(5, p.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Passagem> listar() {
        List<Passagem> listaPassagem = new ArrayList<>();
                             //1      2       3    4     5        6     7      8           9           10        11       12       13                  14            15        16       17           
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG \n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "inner join passagem as pass\n"
                + "on pass.voo = voo.id\n"
                + "inner join clientes as c\n"
                + "on c.id = pass.cliente\n"
                + "order by pass.id";

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

                int idPonte = resultado.getInt(7);

                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);

                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                int idCliente = resultado.getInt(13);
                String nomeCliente = resultado.getString(15);
                String telefoneCliente = resultado.getString(16);
                String rgCliente = resultado.getString(17);

                Cliente comprador = new Cliente(idCliente, nomeCliente, rgCliente, telefoneCliente);

                int idPass = resultado.getInt(12);
                dataSql = resultado.getTimestamp(14);
                dataUtil = new java.util.Date(dataSql.getTime());

                Passagem pass = new Passagem(idPass, comprador, voo, dataUtil);

                listaPassagem.add(pass);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaPassagem);
    }

    @Override
    public Passagem procurarPorCodigo(int cod) {
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG \n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "inner join passagem as pass\n"
                + "on pass.id = voo.id\n"
                + "inner join clientes as c\n"
                + "on c.id = pass.cliente\n"
                + "where pass.id = ?";
        try {
            conectar(sql);
            comando.setInt(1, cod);

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

                int idPonte = resultado.getInt(7);

                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);

                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                int idCliente = resultado.getInt(13);
                String nomeCliente = resultado.getString(15);
                String telefoneCliente = resultado.getString(16);
                String rgCliente = resultado.getString(17);

                Cliente comprador = new Cliente(idCliente, nomeCliente, rgCliente, telefoneCliente);

                int idPass = resultado.getInt(12);
                dataSql = resultado.getTimestamp(14);
                dataUtil = new java.util.Date(dataSql.getTime());

                return new Passagem(idPass, comprador, voo, dataUtil);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (null);

    }

    @Override
    public List<Passagem> procurarPorVoo(int cod) {
        List<Passagem> listaPassagem = new ArrayList<>();
        //1      2       3    4     5        6     7      8           9           10        11       12       13                  14            15        16       17           
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG \n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "inner join passagem as pass\n"
                + "on pass.id = voo.id\n"
                + "inner join clientes as c\n"
                + "on c.id = pass.cliente\n"
                + "where voo.id = ?";

        try {
            conectar(sql);
            comando.setInt(1, cod);

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

                int idPonte = resultado.getInt(7);

                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);

                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                int idCliente = resultado.getInt(13);
                String nomeCliente = resultado.getString(15);
                String telefoneCliente = resultado.getString(16);
                String rgCliente = resultado.getString(17);

                Cliente comprador = new Cliente(idCliente, nomeCliente, rgCliente, telefoneCliente);

                int idPass = resultado.getInt(12);
                dataSql = resultado.getTimestamp(14);
                dataUtil = new java.util.Date(dataSql.getTime());

                Passagem pass = new Passagem(idPass, comprador, voo, dataUtil);

                listaPassagem.add(pass);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaPassagem);
    }

    @Override
    public List<Passagem> procurarPorCliente(int cod) {
        List<Passagem> listaPassagem = new ArrayList<>();
        //1      2       3    4     5        6     7      8           9           10        11       12       13                  14            15        16       17           
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG \n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "inner join passagem as pass\n"
                + "on pass.id = voo.id\n"
                + "inner join clientes as c\n"
                + "on c.id = pass.cliente\n"
                + "where pass.cliente = ?";

        try {
            conectar(sql);
            comando.setInt(1, cod);

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

                int idPonte = resultado.getInt(7);

                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                java.sql.Timestamp dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);

                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                int idCliente = resultado.getInt(13);
                String nomeCliente = resultado.getString(15);
                String telefoneCliente = resultado.getString(16);
                String rgCliente = resultado.getString(17);

                Cliente comprador = new Cliente(idCliente, nomeCliente, rgCliente, telefoneCliente);

                int idPass = resultado.getInt(12);
                dataSql = resultado.getTimestamp(14);
                dataUtil = new java.util.Date(dataSql.getTime());

                Passagem pass = new Passagem(idPass, comprador, voo, dataUtil);

                listaPassagem.add(pass);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaPassagem);
    }

    @Override
    public List<Passagem> procurarPorHoraVenda(Date data) {
        List<Passagem> listaPassagem = new ArrayList<>();
        //1      2       3    4     5        6     7      8           9           10        11       12       13                  14            15        16       17           
        String sql = "select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG \n"
                + "from cidades as o\n"
                + "inner join pontes_aereas as p \n"
                + "on o.id = p.origem\n"
                + "inner join cidades as d\n"
                + "on d.id = p.destino\n"
                + "inner join voo\n"
                + "on p.id = voo.ponte\n"
                + "inner join aviao as a\n"
                + "on voo.aviao = a.id\n"
                + "inner join passagem as pass\n"
                + "on pass.id = voo.id\n"
                + "inner join clientes as c\n"
                + "on c.id = pass.cliente\n"
                + "where pass.horario_compra = ?";

        try {
            conectar(sql);
            java.sql.Timestamp dataSql = new java.sql.Timestamp(data.getTime());
            comando.setTimestamp(1, dataSql);

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

                int idPonte = resultado.getInt(7);

                Ponte_Aerea ponte = new Ponte_Aerea(idPonte, cidadeDestino, cidadeOrigem);

                int idVoo = resultado.getInt(8);
                dataSql = resultado.getTimestamp(9);
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());
                int idAviao = resultado.getInt(10);
                String nomeAviao = resultado.getString(11);

                Aviao aviao = new Aviao(idAviao, nomeAviao);

                Voo voo = new Voo(idVoo, ponte, dataUtil, aviao);

                int idCliente = resultado.getInt(13);
                String nomeCliente = resultado.getString(15);
                String telefoneCliente = resultado.getString(16);
                String rgCliente = resultado.getString(17);

                Cliente comprador = new Cliente(idCliente, nomeCliente, rgCliente, telefoneCliente);

                int idPass = resultado.getInt(12);
                dataSql = resultado.getTimestamp(14);
                dataUtil = new java.util.Date(dataSql.getTime());

                Passagem pass = new Passagem(idPass, comprador, voo, dataUtil);

                listaPassagem.add(pass);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoBd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao();
        }

        return (listaPassagem);
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
