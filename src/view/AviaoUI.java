package view;

import java.text.ParseException;
import servico.AviaoServico;
import util.Console;
import util.DateUtil;
import Menus.AvioesMenu;
import java.util.InputMismatchException;
import model.Aviao;
import java.util.Scanner;

public class AviaoUI {

    AviaoServico servicoA;

    public AviaoUI() {
        servicoA = new AviaoServico();
    }

    public void executar() {
        int opcao = 0;
        do {
            AvioesMenu.mostrarMenu();
            try {
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case AvioesMenu.OP_CADASTRAR:
                        cadastrarAvioes();
                        break;
                    case AvioesMenu.OP_LISTAR:
                        servicoA.mostrarAvioes();
                        break;
                    case AvioesMenu.OP_EDITAR:
                        editarAviao();
                        break;
                    case AvioesMenu.OP_DELETAR:
                        deletarAviao();
                        break;
                    case AvioesMenu.OP_VOLTAR:
                        System.out.println("Retornando ao menu principal..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                System.out.println("\nColoque apenas dígitos...");
                opcao = 100;
            } catch (Exception ex) {
                System.out.println("\nHouve algum erro inesperado...");
                opcao = 100;
            }
        } while (opcao != AvioesMenu.OP_VOLTAR);
    }

    private void cadastrarAvioes() {
        String nome = Console.scanString("Nome: ");
        if (servicoA.aviaoExiste(nome)) {
            System.out.println("Avião já existente no cadastro");
        } else if (nome.matches("\\s*")) {
            System.out.println("Erro: NOME VAZIO!!!");
        } else {
            servicoA.addAviao(new Aviao(nome));
            System.out.println("Avião " + nome + " cadastrado com sucesso!");
        }
    }

    private void editarAviao() {
        servicoA.mostrarAvioes();
        int idUpdate = Console.scanInt("Digite o ID do Avião a ser editado: ");
        if (servicoA.aviaoExiste(idUpdate)) {
            String nome = Console.scanString("Nome: ");
            if (servicoA.aviaoExiste(nome)) {
                System.out.println("Avião já existente no cadastro");
            } else if (nome.matches("\\s*")) {
                System.out.println("Erro: NOME VAZIO!!!");
            } else {
                servicoA.atualizaAviao(new Aviao(idUpdate, nome));
                System.out.println("Avião " + nome + " atualizado com sucesso!");
            }
        }
    }

    private void deletarAviao() {
        Scanner ler = new Scanner(System.in);
        servicoA.mostrarAvioes();
        int idAviaoDel = Console.scanInt("Digite o ID do Avião a ser deletado: ");
        String resposta = "p";
        if (servicoA.aviaoExiste(idAviaoDel)) {
            Aviao aviaoDel = servicoA.entregaAviao(idAviaoDel);
            System.out.println(aviaoDel);
            System.out.println("Confira bem os dados!!!");
            while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
                System.out.println("Escreva apenas \"S\" ou \"N\"");
                System.out.println("Tem certeza que quer deletar o avião: " + aviaoDel.getNome() + "?");
                resposta = ler.next();
            }
            if (resposta.equalsIgnoreCase("s")) {
                servicoA.deletarAviao(aviaoDel);
                System.out.println("Avião deletado com sucesso...");
            } else if (resposta.equalsIgnoreCase("n")) {
                System.out.println("Retornando para o Menu de avião...");
            }
        } else {
            System.out.println("Não foi encontrado avião com esse ID!!!");
        }
    }
}
