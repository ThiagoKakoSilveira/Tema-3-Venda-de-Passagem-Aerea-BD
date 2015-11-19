package dao;

import java.util.List;
import model.Aviao;

public interface AviaoDao {

    public void inserir(Aviao aviao);

    public void deletar(Aviao aviao);

    public void atualizar(Aviao aviao);

    public List<Aviao> listar();

    public Aviao procurarPorCodigo(int cod);

    public Aviao procurarPorNome(String nome);

}
