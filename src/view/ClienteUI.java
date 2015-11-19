package view;

import servico.ClienteServico;
import util.Console;
import Menus.ClienteMenu;
import Menus.MenuClienteCRUD;
import java.util.InputMismatchException;
import java.util.List;
import model.Cliente;
import java.util.Scanner;

public class ClienteUI {

    private ClienteServico servicoC;

    public ClienteUI() {
        servicoC = new ClienteServico();
    }

    public void executar() {
        int opcao;
        do {
            ClienteMenu.MostrarMenuCliente();
            try {
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case ClienteMenu.OP_CADASTRAR:
                        cadastrarCliente();
                        break;
                    case ClienteMenu.OP_LISTAR:
                        servicoC.mostrarClientes();
                        break;
                    case ClienteMenu.OP_EDITAR:
                        editarCliente();
                        break;
                    case ClienteMenu.OP_DELETAR:
                        deletarCliente();
                        break;
                    case ClienteMenu.OP_VOLTAR:
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
        } while (opcao != ClienteMenu.OP_VOLTAR);
    }

    public void cadastrarCliente() {
        String rg = Console.scanString("RG: ");
        if (servicoC.clienteExiste(rg)) {
            System.out.println("\nRG já existente no banco");
        } else if (rg.replace(" ", "").isEmpty() /*testando se é vazio*/) {
            System.out.println("\nErro: RG VAZIO");
        } else if (rg.matches("\\d{10,10}")) {
            String nome = Console.scanString("Escreva seu nome completo: ");
            if (nome.matches("\\s*")) {//testar com \s para ver se é vazio
                System.out.println(" \nErro: NOME VAZIO");
            } else if (nome.matches("[A-Za-z]+(\\s[A-Za-z]+)*")) {
                String telefone = Console.scanString("Telefone para Contato: (xxxx-xxxx 4 números separado por hífem)");
                if (!telefone.matches("\\d{4,4}-\\d{4,4}")) {
                    System.out.println("\nErro: Telefone digitado diferente do formato indicado!!!");
                } else {
                    servicoC.addCliente(new Cliente(nome, rg, telefone));
                    System.out.println("\nCliente " + nome + " \ncadastrado com sucesso!\n");
                }
            } else {
                System.out.println("\nErro: O nome não pode conter números, caracteres especiais, ou começa em espaço ou ter mais de um espaço");
            }
        } else {
            System.out.println("\nErro:O RG não pode conter letras, espaços e no máximo 10 dígitos");
        }
    }

    private void deletarCliente() {
        int opcao, utilInt;
        Cliente clienteDel;
        String resposta = "p", utilString;
        List<Cliente> listaDel;
        Scanner ler = new Scanner(System.in);
        do {
            MenuClienteCRUD.MostrarMenuClienteCRUD();
            try {
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case MenuClienteCRUD.OP_ID:
                        utilInt = Console.scanInt("Digite o id do cliente que deseja deletar: ");
                        if (servicoC.clienteExiste(utilInt)) {
                            clienteDel = servicoC.devolveCliente(utilInt);
                            System.out.println(clienteDel);
                            System.out.println("Confira bem os dados!!!");
                            while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
                                System.out.println("\nTem certeza que quer deletar o cliente: " + clienteDel.getNome() + "?\nEscreva apenas \"S\" ou \"N\"\n");
                                resposta = ler.next();
                            }
                            if (resposta.equalsIgnoreCase("s")) {
                                servicoC.deletarCliente(clienteDel);
                                System.out.println("Cliente deletado com sucesso...");
                                System.out.println("Retornando para o Menu Cliente...");
                                return;
                            } else if (resposta.equalsIgnoreCase("n")) {
                                System.out.println("Retornando para o Menu Deletar Cliente...");
                            }
                        } else {
                            System.out.println("Não foi encontrado cliente com esse ID!!!");
                        }
                        break;
                    case MenuClienteCRUD.OP_NOME:
                        utilString = Console.scanString("Digite o NOME do cliente que deseja deletar:");
                        if (servicoC.nomeClienteExiste(utilString)) {
                            listaDel = servicoC.devolveClientePorNome(utilString);
                            servicoC.mostrarClientes(listaDel);
                            System.out.println("Confira bem os dados na lista!!!");
                            do {
                                System.out.println("Escreva apenas \"S\" ou \"N\"");
                                resposta = Console.scanString("Encontrou o cliente que quer deletar?");
                            } while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n"));
                            if (resposta.equalsIgnoreCase("s")) {
                                utilInt = Console.scanInt("\nEntão confira os dados do cliente que excluirá e digite o id aqui: \n");
                                if (servicoC.clienteExiste(utilInt)) {
                                    clienteDel = servicoC.devolveCliente(utilInt);
                                    servicoC.deletarCliente(clienteDel);
                                    System.out.println("Cliente deletado com sucesso...\nRetornando para o Menu Clientes...");
                                    return;
                                } else {
                                    System.out.println("Houve algum erro pois o id não existe!!!");
                                }
                            } else if (resposta.equalsIgnoreCase("n")) {
                                System.out.println("Retornando para o Menu Deletar Clientes...");
                            }
                        } else {
                            System.out.println("Não foi encontrado cliente com esse NOME!!!");
                        }
                        break;
                    case MenuClienteCRUD.OP_RG:
                        utilString = Console.scanString("Digite o RG que deseja deletar: ");
                        if (servicoC.clienteExiste(utilString)) {
                            clienteDel = servicoC.devolveCliente(utilString);
                            System.out.println("\n"+clienteDel+"\n");
                            System.out.println("Confira bem os dados!!!");
                            do {
                                System.out.println("Escreva apenas \"S\" ou \"N\"");
                                System.out.println("\nTem certeza que quer deletar o cliente " + clienteDel.getNome() + "?\n");
                                resposta = ler.next();
                            } while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n"));
                            if (resposta.equalsIgnoreCase("s")) {
                                servicoC.deletarCliente(clienteDel);
                                System.out.println("Cliente deletado com sucesso...\nRetornando para o Menu Clientes...");
                                return;
                            } else if (resposta.equalsIgnoreCase("n")) {
                                System.out.println("Retornando para o Menu Deletar Clientes...");
                            }
                        } else {
                            System.out.println("Não foi encontrado cliente com esse RG!!!");
                        }
                        break;
                    case MenuClienteCRUD.OP_TELEFONE:
                        do {
                            utilString = Console.scanString("Digite o TELEFONE: (xxxx-xxxx 4 números separado por hífem)");
                            if (!utilString.matches("\\d{4,4}-\\d{4,4}")) {
                                System.out.println("\nErro: Telefone digitado diferente do formato indicado!!!");
                            }
                        } while (!utilString.matches("\\d{4,4}-\\d{4,4}"));
                        if (servicoC.clienteExistePorTelefone(utilString)) {
                            listaDel = servicoC.devolveClientePorTelefone(utilString);
                            servicoC.mostrarClientes(listaDel);
                            System.out.println("Confira bem os dados na lista!!!");
                            while (!resposta.equalsIgnoreCase("s") && !resposta.equalsIgnoreCase("n")) {
                                System.out.println("Escreva apenas \"S\" ou \"N\"");
                                resposta = Console.scanString("Encontrou o cliente que quer deletar?");
                            }
                            if (resposta.equalsIgnoreCase("s")) {
                                utilInt = Console.scanInt("Então confira se os dados são realmente do cliente a deletar e digite o id do mesmo aqui: ");
                                if (servicoC.clienteExiste(utilInt)) {
                                    clienteDel = servicoC.devolveCliente(utilInt);
                                    servicoC.deletarCliente(clienteDel);
                                    System.out.println("Cliente deletado com sucesso...\nRetornando para o Menu Clientes...");
                                    return;
                                } else {
                                    System.out.println("Houve algum erro pois o id não existe!!!");
                                }
                            } else if (resposta.equalsIgnoreCase("n")) {
                                System.out.println("Retornando para o Menu Deletar Clientes...");
                            }
                        } else {
                            System.out.println("Não foi encontrado cliente com esse TELEFONE!!!");
                        }
                        break;
                    case ClienteMenu.OP_VOLTAR:
                        System.out.println("Retornando ao menu clientes..");
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
        } while (opcao != MenuClienteCRUD.OP_VOLTAR);
    }

    private void editarCliente() {
        servicoC.mostrarClientes();
        int idClienteUpdate = Console.scanInt("Digite o ID do cliente a ser atualizado: ");
        if (servicoC.clienteExiste(idClienteUpdate)) {
            String rg = Console.scanString("RG (novo): ");
            if (rg.replace(" ", "").isEmpty() /*testando se é vazio*/) {
                System.out.println("\nErro: RG VAZIO");
            } else if (rg.matches("\\d{10,10}")) {
                String nome = Console.scanString("Escreva seu nome completo (novo): ");
                if (nome.matches("\\s*")) {//testar com \s para ver se é vazio
                    System.out.println(" \nErro: NOME VAZIO");
                } else if (nome.matches("[A-Za-z]+(\\s[A-Za-z]+)*")) {
                    String telefone = Console.scanString("Telefone para Contato (novo): (xxxx-xxxx 4 números separado por hífem)");
                    if (!telefone.matches("\\d{4,4}-\\d{4,4}")) {
                        System.out.println("\nErro: Telefone digitado diferente do formato indicado!!!");
                    } else {
                        servicoC.atualizaCliente(new Cliente(idClienteUpdate, nome, rg, telefone));
                        System.out.println("\nCliente " + nome + " atualizado com sucesso!\n");
                    }
                } else {
                    System.out.println("\nErro: O nome não pode conter números, caracteres especiais, ou começa em espaço ou ter mais de um espaço");
                }
            } else {
                System.out.println("\nErro:O RG não pode conter letras, espaços e no máximo 10 dígitos");
            }
        }
    }
}
