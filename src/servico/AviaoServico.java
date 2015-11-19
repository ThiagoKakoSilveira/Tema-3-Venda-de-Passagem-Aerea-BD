package servico;

import dao.AviaoDao;
import dao.AviaoDaoBd;
import java.util.List;
import model.Aviao;

public class AviaoServico {

    public boolean aviaoExiste(String nome) {
        AviaoDao dao = new AviaoDaoBd();
        Aviao aviao = dao.procurarPorNome(nome);
        return (aviao != null);
    }

    public boolean aviaoExiste(int id) {
        AviaoDao dao = new AviaoDaoBd();
        Aviao aviao = dao.procurarPorCodigo(id);
        return (aviao != null);
    }

    public void addAviao(Aviao a) {
        new AviaoDaoBd().inserir(a);
    }

    public List<Aviao> listarAviao() {
        return (new AviaoDaoBd().listar());
    }

    public void atualizaAviao(Aviao aviao) {
        new AviaoDaoBd().atualizar(aviao);
    }

    public Aviao entregaAviao(int codigo) {
        return (new AviaoDaoBd().procurarPorCodigo(codigo));
    }

    public void deletarAviao(Aviao aviao) {
        new AviaoDaoBd().deletar(aviao);
    }

    public void mostrarAvioes() {
        System.out.println("-----------------------------\n");
        System.out.println(String.format("%-10s", "CÓDIGO") + "\t"
                + String.format("%-20s", "|NOME"));
        for (Aviao aviao : listarAviao()) {
            System.out.println(String.format("%-10s", aviao.getCodigo()) + "\t"
                    + String.format("%-20s", "|" + aviao.getNome()));
        }
    }

}
