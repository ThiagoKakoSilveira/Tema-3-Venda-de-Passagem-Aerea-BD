package servico;

import dao.PassagemDaoBd;
import dao.PassagemDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Passagem;

public class PassagemServico {

    public boolean passagemExite(int cod) {
        PassagemDao dao = new PassagemDaoBd();
        Passagem pass = dao.procurarPorCodigo(cod);
        return (pass != null);
    }

    public Passagem entregaPassagem(int cod) {
        return (new PassagemDaoBd().procurarPorCodigo(cod));
    }

    public void addPassagem(Passagem c) {
        new PassagemDaoBd().inserir(c);
    }

    public List<Passagem> listarPassagem() {
        return (new PassagemDaoBd().listar());
    }

    public void mostrarPassagensVendidas() {
        for (Passagem vp : listarPassagem()) {           
            System.out.println(vp);
            System.out.println("--------------------------------------\n");
        }
    }

    public void atualizaPassagem(Passagem pass) {
        new PassagemDaoBd().atualizar(pass);
    }

    public void deletarPassagem(Passagem pass) {
        new PassagemDaoBd().deletar(pass);
    }

    public void mostrarOrigensExistentes() {
        String apresentado = "";
        for (Passagem passagem_Sel : listarPassagem()) {
            apresentado += " " + passagem_Sel.getVoo().getPonte().getOrigem().getNome()+",";
        }
        System.out.println("Origens existentes nas rotas disponíveis são as:\n\n" + apresentado);
    }

    public void mostrarDestinosExistentes() {
        String apresentado = "";
        for (Passagem passagem_Sel : listarPassagem()) {
            apresentado += " " + passagem_Sel.getVoo().getPonte().getDestino().getNome()+",";
        }
        System.out.println("Destinos existentes nas rotas disponíveis são os:\n\n" + apresentado);
    }

    public boolean existeDestino(String destino) {
        for (Passagem passagem_Existe : listarPassagem()) {
            if (passagem_Existe.getVoo().getPonte().getDestino().getNome().equalsIgnoreCase(destino)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeOrigem(String origem) {
        for (Passagem passagem_Existe : listarPassagem()) {
            if (passagem_Existe.getVoo().getPonte().getOrigem().getNome().equalsIgnoreCase(origem)) {
                return true;
            }
        }
        return false;
    }

    public List<Passagem> listaPassagemPorMês(int mes, int ano) {
        List<Passagem> passagemMes = new ArrayList<>();
        Calendar calendario = Calendar.getInstance();

        for (Passagem pass : listarPassagem()) {
            calendario.setTime(pass.getHoraVenda());
            if (calendario.get(Calendar.MONTH) == (mes - 1) && calendario.get(Calendar.YEAR) == (ano)) {                
                passagemMes.add(pass);                
            }
        }
        return passagemMes;
    }
}
