package servico;

import dao.VooDao;
import dao.VooDaoBd;
import java.util.List;
import model.Ponte_Aerea;
import model.Voo;

public class VooServico {

    public boolean ponteExiste(int id) {
        VooDao dao = new VooDaoBd();
        Ponte_Aerea ponte = dao.pontePorId(id);
        return (ponte != null);
    }

    public Ponte_Aerea entregaPonte(int id) {
        return (new VooDaoBd().pontePorId(id));
    }

    public boolean vooExiste(int cod) {
        VooDao dao = new VooDaoBd();
        Voo voo = dao.procurarPorCodigo(cod);
        return (voo != null);
    }

    public Voo entregaVoo(int cod) {
        return (new VooDaoBd().procurarPorCodigo(cod));
    }

    public void addVoo(Voo c) {
        new VooDaoBd().inserir(c);
    }

    public List<Voo> listarVoo() {
        return (new VooDaoBd().listar());
    }

    public List<Ponte_Aerea> listarPontes() {
        return (new VooDaoBd().listarPontes());
    }

    public void mostrarPontes() {
        for (Ponte_Aerea Ponte_Sel : listarPontes()) {
            System.out.println("--------------------------------------\n");
            System.out.println(Ponte_Sel);
            System.out.println("\n");
        }
    }

    public void mostrarVoos() {
        for (Voo apresentado : listarVoo()) {
            //System.out.println("--------------------------------------");
            System.out.println(apresentado);
            System.out.println("--------------------------------------\n");
        }
    }

    public void atualizaVoo(Voo voo) {
        new VooDaoBd().atualizar(voo);
    }

    public void deletarVoo(Voo voo) {
        new VooDaoBd().deletar(voo);
    }
}
