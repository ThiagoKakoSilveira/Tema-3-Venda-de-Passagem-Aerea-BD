package view;

import Menus.RelatoriosMenu;
import util.Console;
import java.util.InputMismatchException;
import model.Passagem;
import servico.PassagemServico;
import servico.VooServico;

public class Relatorio_de_VendaUI {
    
    VooServico servicoV;
    PassagemServico servicoP;

    public Relatorio_de_VendaUI() {
        servicoP = new PassagemServico();
        servicoV = new VooServico();
    }

    public void executar() {
        int opcao = 0;
        do {
            RelatoriosMenu.mostrarMenu();
            try {
                opcao = Console.scanInt("Digite a opção: ");
                //System.out.println("\n");
                switch (opcao) {
                    case RelatoriosMenu.OP_VisualizarPorCliente:
                        visualizarPorCliente();
                        break;
                    case RelatoriosMenu.OP_VisualizarPorDestino:
                        visualizarPorDestino();
                        break;
                    case RelatoriosMenu.OP_VisualizarPorOrigem:
                        visualizarPorOrigem();
                        break;
                    case RelatoriosMenu.OP_VisualizarPorVoo:
                        visualizarPorVoo();
                        break;
                    case RelatoriosMenu.OP_VisualizarPorPeriodoDeVoo:
                        visualizarPorPeriodoDeVoo();
                        break;
                    case RelatoriosMenu.OP_Voltar:
                        System.out.println("Retornando ao Menu Principal");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Coloque apenas dígitos...");
                opcao = 100;
            } catch (Exception ex) {
                System.out.println("Houve algum erro inesperado...");
                opcao = 100;
            }
        } while (opcao != RelatoriosMenu.OP_Voltar);
    }

    private void visualizarPorCliente() {
        String rg = Console.scanString(" Digite o RG da pesquisa: ");
        System.out.println("\n Este cliente comprou a(s) passagem(ns):");
        for (Passagem vendas : servicoP.listarPassagem()) {
            if (vendas.getCliente().getRG().equalsIgnoreCase(rg)) {
                System.out.println(vendas+"\n----------------------------------------------------------------------------");
            }
        }
    }

    private void visualizarPorDestino() {
        servicoP.mostrarDestinosExistentes();
        String dest = Console.scanString("\nConfira os destinos apresentados acima!!!\nEscreva o destino que quer relatório: ");
        System.out.println("\n");
        if (servicoP.existeDestino(dest)) {
            System.out.println("Passagens vendidas com esse destino: \n");
            for (Passagem vendas : servicoP.listarPassagem()) {
                if (vendas.getVoo().getPonte().getDestino().getNome().equalsIgnoreCase(dest)) {
                    System.out.println("--------------------------------------\n");
                    System.out.println(vendas+"\n");
                }
            }
        } else {
            System.out.println("Não teve Vendas para esse destino!");
        }
    }

    private void visualizarPorOrigem() {
        servicoP.mostrarOrigensExistentes();
        String origem = Console.scanString("\nConfira os destinos apresentados acima!!!\nEscreva a origem que deseja gerar um relatório: ");
        System.out.println("\n");
        if (servicoP.existeOrigem(origem)) {
            System.out.println("Passagens vendidas com essa origem: \n\n");
            for (Passagem vendas : servicoP.listarPassagem()) {
                if (vendas.getVoo().getPonte().getOrigem().getNome().equalsIgnoreCase(origem)) {
                    System.out.println(vendas);
                }
            }
        } else {
            System.out.println("Não teve Vendas para esse destino!");
        }
    }

    private void visualizarPorVoo() {
        servicoV.mostrarVoos();
        int idVoo = Console.scanInt("Digite o código de voo que deseja relatório: ");
        System.out.println("\n");
        for (Passagem vendida : servicoP.listarPassagem()) {
            if (vendida.getVoo().getCodigo() == idVoo) {
                System.out.println(vendida);
            }
        }
    }

    private void visualizarPorPeriodoDeVoo() {
        int mes = Console.scanInt("Mês: ");
        int ano = Console.scanInt("Ano: ");
        for (Passagem vendaMensal : servicoP.listaPassagemPorMês(mes, ano)) {
            System.out.println("--------------------------------------\n");
            System.out.println(vendaMensal);
            //System.out.println("\n");
        }
    }
}
