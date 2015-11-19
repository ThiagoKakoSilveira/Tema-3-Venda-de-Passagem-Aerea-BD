package view;

import java.util.Date;
import util.Console;
import Menus.PassagensMenu;
import Menus.MenuClienteInexistente;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Passagem;
import model.Voo;
import servico.VooServico;
import servico.ClienteServico;
import servico.PassagemServico;

public class PassagemUI {

    private VooServico servicoV;
    private ClienteServico servicoC;
    private PassagemServico servicoP;

    public PassagemUI() {
        servicoC = new ClienteServico();
        servicoP = new PassagemServico();
        servicoV = new VooServico();
    }

    public void executar() {
        int opcao;
        do {
            PassagensMenu.mostrarMenu();
            try {
                opcao = Console.scanInt("Digite a sua opção:");
                System.out.println("\n");
                switch (opcao) {
                    case PassagensMenu.OP_CADASTRAR:
                        cadastrarPassagem();
                        break;
                    case PassagensMenu.OP_LISTAR:
                        servicoP.mostrarPassagensVendidas();
                        break;
                    case PassagensMenu.OP_EDITAR:
                        editarPassaem();
                        break;
                    case PassagensMenu.OP_DELETAR:
                        deletarPassagem();
                        break;
                    case PassagensMenu.OP_VOLTAR:
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
        } while (opcao != PassagensMenu.OP_VOLTAR);
    }

    public void cadastrarPassagem() {
        String rgComprador = Console.scanString("Digite o RG do passageiro: ");
        Cliente comprador = null;
        if (servicoC.clienteExiste(rgComprador)) {
            comprador = servicoC.devolveCliente(rgComprador);
            System.out.println("Cliente selecionado com sucesso...");
        } else {
            System.out.println("Cliente não encotrado...");
            MenuClienteInexistente.mostrarMenu();
            int opcaoClienteInexistente = Console.scanInt("Digite o número da ação desejada: ");
            switch (opcaoClienteInexistente) {
                case MenuClienteInexistente.OP_CADASTRAR:
                    new ClienteUI().cadastrarCliente();
                    rgComprador = Console.scanString("\nDigite novamente o RG do cliente recém cadastrado: ");
                    if (servicoC.clienteExiste(rgComprador)) {
                        comprador = servicoC.devolveCliente(rgComprador);
                        System.out.println("Cliente Selecionado com sucesso...");
                    } else {
                        System.out.println("\nErro na Seleção do Cliente\n");
                        return;
                    }
                    break;
                case MenuClienteInexistente.OP_BUSCARNOME:
                    String nome = Console.scanString("Digite o nome que deseja procurar: ");
                    List<Cliente> compradores = servicoC.devolveClientePorNome(nome);
                    if (compradores == null) {
                        System.out.println("Cliente não encontrado...\n Sistema retornando para o menu de Venda de Passagem");
                        return;
                    } else {
                        String resposta;
                        servicoC.mostrarClientes(compradores);
                        do {
                            System.out.println("Escreva apenas \"S\" ou \"N\"");
                            resposta = Console.scanString("Achou o cliente que estava procurando?");
                        } while (!resposta.equalsIgnoreCase("s") || !resposta.equalsIgnoreCase("n"));
                        if (resposta.equalsIgnoreCase("s")) {
                            int idComprador = Console.scanInt("Então confira se os dados são realmente do cliente desejado e cole o id do mesmo aqui: ");
                            if (servicoC.clienteExiste(idComprador)) {
                                comprador = servicoC.devolveCliente(idComprador);
                                System.out.println("Cliente selecionado com sucesso...");
                            } else {
                                System.out.println("Houve algum erro pois o id não existe!!!");
                            }
                        } else if (resposta.equalsIgnoreCase("n")) {
                            System.out.println("Então Cadastre este novo Cliente\n Retornando para o Menu Venda de Passagens...");
                            return;
                        }
                    }
                    break;
                case MenuClienteInexistente.OP_VOLTAR:
                    System.out.println("Retornando para o menu Vendas de Passagem...");
                    break;
                default:
                    System.out.println("Opção inválida...");
            }
        }
        servicoV.mostrarVoos();
        Voo vooSel;
        int idVoo = Console.scanInt("Digite o código do voo: ");
        if (servicoV.vooExiste(idVoo)) {
            vooSel = servicoV.entregaVoo(idVoo);
            Date atual = new Date();
            servicoP.addPassagem(new Passagem(comprador, vooSel, atual));
            System.out.println("Venda cadastrada com sucesso...");
        } else {
            System.out.println("Voo não encontrado!!!");
        }
    }

    private void editarPassaem() {
        servicoP.mostrarPassagensVendidas();
        int idPassUpdate = Console.scanInt("Digite o ID da passagem a ser editado: ");
        if (servicoP.passagemExite(idPassUpdate)) {
            String rgComprador = Console.scanString("Digite o RG do passageiro (novo): ");
            Cliente comprador = null;
            if (servicoC.clienteExiste(rgComprador)) {
                comprador = servicoC.devolveCliente(rgComprador);
                System.out.println("\nCliente selecionado com sucesso...\n");
            } else {
                System.out.println("Cliente não encontrado\nRetornando para o Menu Venda de Passagem");
                return;
            }
            servicoV.mostrarVoos();
            Voo vooSel;
            int idVoo = Console.scanInt("Digite o código do voo: ");
            if (servicoV.vooExiste(idVoo)) {
                vooSel = servicoV.entregaVoo(idVoo);
                Date atual = new Date();
                servicoP.atualizaPassagem(new Passagem(idPassUpdate, comprador, vooSel, atual));
                System.out.println("Venda atualizada com sucesso...");
            } else {
                System.out.println("Voo não encontrado!!!");
            }
        }
    }

    private void deletarPassagem() {
        Scanner ler = new Scanner(System.in);
        servicoP.mostrarPassagensVendidas();
        int idPassagemDel = Console.scanInt("Digite o ID da passagem a ser deletado: ");
        String resposta = "p";
        if (servicoP.passagemExite(idPassagemDel)) {
            Passagem passagemDel = servicoP.entregaPassagem(idPassagemDel);
            System.out.println("\n"+passagemDel+"\n");
            System.out.println("Confira bem os dados!!!");
            while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
                System.out.println("Escreva apenas \"S\" ou \"N\"");
                System.out.println("Tem certeza que quer deletar a passagem com código " + passagemDel.getCodigo() + " vendida para o cliente " + passagemDel.getCliente().getNome() + "?");
                resposta = ler.next();
            }
            if (resposta.equalsIgnoreCase("s")) {
                servicoP.deletarPassagem(passagemDel);
                System.out.println("Passagem deletado com sucesso...");
            } else if (resposta.equalsIgnoreCase("n")) {
                System.out.println("Retornando para o Menu de venda de passagens...");
            }
        } else {
            System.out.println("Não foi encontrado venda de passagem com esse ID!!!");
        }
    }
}
