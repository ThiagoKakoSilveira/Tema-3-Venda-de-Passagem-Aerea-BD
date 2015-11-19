package view;

import servico.VooServico;
import servico.AviaoServico;
import model.Aviao;
import model.Ponte_Aerea;
import model.Voo;
import java.util.Date;
import util.Console;
import util.DateUtil;
import Menus.VoosMenu;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VooUI {

    private VooServico servicoV;
    private AviaoServico servicoA;

    public VooUI() {
        servicoV = new VooServico();
        servicoA = new AviaoServico();
    }

    public void executar() {
        int opcao;
        do {
            VoosMenu.mostrarMenu();
            try {
                opcao = Console.scanInt("Digite a sua opção: ");
                System.out.println("\n");
                switch (opcao) {
                    case VoosMenu.OP_CADASTRAR:
                        cadastrarVoo();
                        break;
                    case VoosMenu.OP_LISTAR:
                        servicoV.mostrarVoos();
                        break;
                    case VoosMenu.OP_EDITAR:
                        editarVoo();
                        break;
                    case VoosMenu.OP_DELETAR:
                        deletarVoo();
                        break;
                    case VoosMenu.OP_VOLTAR:
                        System.out.println("Retornando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida...");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Coloque apenas dígitos...");
                opcao = 100;
            } catch (Exception ex) {
                System.out.println("Houve algum erro inesperado...");
                opcao = 100;
            }
        } while (opcao != VoosMenu.OP_VOLTAR);
    }

    public void cadastrarVoo() {
        System.out.println("Selecione o Avião para realizar esse Vôo: ");
        servicoA.mostrarAvioes();
        int codigo = Console.scanInt("Digite o CODIGO do avião que voará:");
        if (servicoA.aviaoExiste(codigo)) {
            Aviao aviao = servicoA.entregaAviao(codigo);
            Date horaDoVoo = null;
            boolean dataValida;
            do {
                try {
                    String dataHora = Console.scanString("Digite a data e o horário do Vôo neste formato (dd/mm/aaaa hh:mm):");
                    horaDoVoo = DateUtil.stringToDateHour(dataHora);
                    dataValida = true;
                } catch (ParseException ex) {
                    System.out.println("Data e hora no formato inválido!");
                    dataValida = false;
                }
            } while (!dataValida);
            servicoV.mostrarPontes();

            int opPonte = Console.scanInt("Selecione o ID da ponte aérea q deseja cadastrar:");
            if (servicoV.ponteExiste(opPonte)) {
                Ponte_Aerea ponteSelecionada = servicoV.entregaPonte(opPonte);
                servicoV.addVoo(new Voo(ponteSelecionada, horaDoVoo, aviao));
                System.out.println("Voo cadastrado com sucesso!!!");
            } else {
                System.out.println("Não Existe uma ponte aérea com esse código!!!\n Voo não cadastrado.");
            }
        } else {
            System.out.println("Não existe esse avião!!!\nVoo não cadastrado.");
        }
    }

    private void editarVoo() {
        servicoV.mostrarVoos();
        int idVooUpdate = Console.scanInt("Digite o CÓDIGO do VOO a ser atualizado: ");
        if (servicoV.vooExiste(idVooUpdate)) {
            System.out.println("Selecione um novo Avião para realizar esse Vôo: ");
            servicoA.mostrarAvioes();
            int codigo = Console.scanInt("Digite o CODIGO do novo avião: ");
            if (servicoA.aviaoExiste(codigo)) {
                Aviao aviao = servicoA.entregaAviao(codigo);
                Date horaDoVoo = null;
                boolean dataValida;
                do {
                    try {
                        String dataHora = Console.scanString("Digite a data e o horário do Vôo neste formato (dd/mm/aaaa hh:mm):");
                        horaDoVoo = DateUtil.stringToDateHour(dataHora);
                        dataValida = true;
                    } catch (ParseException ex) {
                        System.out.println("Data e hora no formato inválido!");
                        dataValida = false;
                    }
                } while (!dataValida);
                servicoV.mostrarPontes();

                int opPonte = Console.scanInt("Selecione o ID da nova ponte aérea: ");
                if (servicoV.ponteExiste(opPonte)) {
                    Ponte_Aerea ponteSelecionada = servicoV.entregaPonte(opPonte);

                    servicoV.atualizaVoo(new Voo(idVooUpdate, ponteSelecionada, horaDoVoo, aviao));
                    System.out.println("Voo atualizado com sucesso!!!");
                } else {
                    System.out.println("Não Existe uma ponte aérea com esse código!!!\n Voo não atualizado.");
                }
            } else {
                System.out.println("Não existe esse avião!!!\nVoo não atualizado.");
            }
        }
    }

    private void deletarVoo() {
        Scanner ler = new Scanner(System.in);
        servicoV.mostrarVoos();
        int idVooDel = Console.scanInt("Digite o ID do VOO a ser deletado: ");
        String resposta = "p";
        if (servicoV.vooExiste(idVooDel)) {
            Voo vooDel = servicoV.entregaVoo(idVooDel);
            System.out.println("\n"+vooDel+"\n");
            System.out.println("Confira bem os dados!!!");
            while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
                System.out.println("Escreva apenas \"S\" ou \"N\"");
                System.out.println("Tem certeza que quer deletar o voo: " + vooDel.getCodigo() + " com destino: " + vooDel.getPonte().getDestino().getNome() + "?");
                resposta = ler.next();
            }
            if (resposta.equalsIgnoreCase("s")) {
                servicoV.deletarVoo(vooDel);
                System.out.println("Voo deletado com sucesso...");
            } else if (resposta.equalsIgnoreCase("n")) {
                System.out.println("Retornando para o Menu de avião...");
            }
        } else {
            System.out.println("Não foi encontrado avião com esse ID!!!");
        }

    }

}
